package com.science.gtnl.common.block.blocks.playerDoll;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.event.world.WorldEvent;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.properties.Property;
import com.science.gtnl.config.MainConfig;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockPlayerDollRenderer extends TileEntitySpecialRenderer {

    private static boolean offlineMode = false;
    private static final Set<String> BLACKLISTED_GAMEPROFILE = Sets.newConcurrentHashSet();
    private final Map<String, ResourceLocation> textureCache = new HashMap<>();
    public static final ResourceLocation DEFAULT_SKIN = new ResourceLocation("sciencenotleisure:model/skin.png");
    public static final ResourceLocation DEFAULT_CAPE = new ResourceLocation("sciencenotleisure:model/cape.png");
    public static final ResourceLocation MODEL_RESOURCE = new ResourceLocation(
        "sciencenotleisure:model/PlayerDoll.obj");
    public static final IModelCustom modelCustom = AdvancedModelLoader.loadModel(MODEL_RESOURCE);

    private static final File SKIN_DIR = new File("config/GTNotLeisure/skin");
    private static final File CAPE_DIR = new File("config/GTNotLeisure/cape");

    static {
        // 确保目录存在
        if (!SKIN_DIR.exists()) SKIN_DIR.mkdirs();
        if (!CAPE_DIR.exists()) CAPE_DIR.mkdirs();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        if (tileEntity instanceof TileEntityPlayerDoll tileEntityPlayerDoll) {
            Minecraft minecraft = Minecraft.getMinecraft();
            GL11.glPushMatrix();
            GL11.glTranslated(x + 0.5, y, z + 0.5);

            // 处理方块朝向
            int orientation = tileEntity.getBlockMetadata();
            switch (orientation) {
                case 4:
                    GL11.glRotatef(90, 0, 1, 0);
                    break;
                case 5:
                    GL11.glRotatef(-90, 0, 1, 0);
                    break;
                case 3:
                    GL11.glRotatef(180, 0, 1, 0);
                    break;
                default:
                    break;
            }
            GL11.glRotatef(180, 0, 1, 0); // 默认旋转

            // 初始化纹理
            ResourceLocation skinTexture = DEFAULT_SKIN;
            ResourceLocation capeTexture = DEFAULT_CAPE;

            if (MainConfig.enableCustomPlayerDoll && !offlineMode) {
                // 获取 TileEntity 的 NBT 数据
                NBTTagCompound nbt = new NBTTagCompound();
                tileEntity.writeToNBT(nbt);
                GameProfile gameprofile = tileEntityPlayerDoll.getSkullOwner();

                if (nbt.hasKey("SkullOwner", 8)) {
                    String playerName = nbt.getString("SkullOwner");
                    if (playerName == null || playerName.isEmpty()) {
                        playerName = Minecraft.getMinecraft().thePlayer.getCommandSenderName();
                    }
                    gameprofile = new GameProfile(null, playerName);
                } else if (nbt.hasKey("SkullOwner", 10)) {
                    NBTTagCompound ownerTag = nbt.getCompoundTag("SkullOwner");
                    gameprofile = NBTUtil.func_152459_a(ownerTag);

                    if (gameprofile != null && (gameprofile.getName() == null || gameprofile.getName()
                        .isEmpty()) && gameprofile.getId() == null) {
                        gameprofile = new GameProfile(null, Minecraft.getMinecraft().thePlayer.getCommandSenderName());
                    }
                }

                if (gameprofile != null) {
                    gameprofile = getGameProfile(gameprofile, tileEntity);
                } else {
                    gameprofile = new GameProfile(null, Minecraft.getMinecraft().thePlayer.getCommandSenderName());
                    gameprofile = getGameProfile(gameprofile, tileEntity);
                }

                if (gameprofile != null) {
                    Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> textureMap = minecraft.func_152342_ad()
                        .func_152788_a(gameprofile);

                    if (textureMap.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                        MinecraftProfileTexture skin = textureMap.get(MinecraftProfileTexture.Type.SKIN);
                        downloadTexture(skin, MinecraftProfileTexture.Type.SKIN);
                        skinTexture = getLocalTexture(skin, MinecraftProfileTexture.Type.SKIN, DEFAULT_SKIN);
                    }
                    if (textureMap.containsKey(MinecraftProfileTexture.Type.CAPE)) {
                        MinecraftProfileTexture cape = textureMap.get(MinecraftProfileTexture.Type.CAPE);
                        downloadTexture(cape, MinecraftProfileTexture.Type.CAPE);
                        capeTexture = getLocalTexture(cape, MinecraftProfileTexture.Type.CAPE, DEFAULT_CAPE);
                    }
                }
            }

            // 绑定皮肤纹理并渲染模型
            bindTexture(skinTexture);
            renderModelParts(
                "Body",
                "Body_Layer",
                "Hat",
                "Hat_Layer",
                "Head",
                "Left_Arm",
                "Left_Arm_Layer",
                "Left_Leg",
                "Left_Leg_Layer",
                "Right_Arm",
                "Right_Arm_Layer",
                "Right_Leg",
                "Right_Leg_Layer");

            // 绑定披风纹理并渲染披风部分
            bindTexture(capeTexture);
            modelCustom.renderPart("cape");

            GL11.glPopMatrix();
            GL11.glEnable(GL11.GL_BLEND);
        }
    }

    /**
     * 下载皮肤或披风文件到本地，并检查玩家名和纹理哈希值的有效性
     */
    private void downloadTexture(MinecraftProfileTexture texture, MinecraftProfileTexture.Type type) {
        if (texture == null || StringUtils.isNullOrEmpty(texture.getHash())) {
            return;
        }

        File targetDir = type == MinecraftProfileTexture.Type.SKIN ? SKIN_DIR : CAPE_DIR;
        File targetFile = new File(targetDir, texture.getHash() + ".png");

        if (targetFile.exists()) {
            return;
        }

        int maxRetries = 10;
        int retryCount = 0;

        while (retryCount < maxRetries) {
            try (InputStream in = new URL(texture.getUrl()).openStream();
                FileOutputStream out = new FileOutputStream(targetFile)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                return; // 下载成功
            } catch (IOException e) {
                retryCount++;
                if (retryCount >= maxRetries) {
                    System.err
                        .println("Failed to download texture after " + maxRetries + " attempts: " + texture.getUrl());
                    offlineMode = true; // 设置为离线模式
                    return;
                }
                try {
                    Thread.sleep(1000); // 等待 1 秒后重试
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * 检查本地是否存在对应的皮肤或披风文件，并上传到 OpenGL（仅上传一次）
     * 如果纹理无效，返回默认纹理
     */
    private ResourceLocation getLocalTexture(MinecraftProfileTexture texture, MinecraftProfileTexture.Type type,
        ResourceLocation defaultTexture) {
        // 检查纹理和玩家名是否有效
        if (texture == null || StringUtils.isNullOrEmpty(texture.getHash())) {
            return defaultTexture;
        }

        // 获取纹理的哈希值作为缓存的键
        String textureHash = texture.getHash();

        // 如果缓存中已存在该纹理的 ResourceLocation，直接返回
        if (textureCache.containsKey(textureHash)) {
            return textureCache.get(textureHash);
        }

        // 检查本地文件是否存在
        File targetDir = type == MinecraftProfileTexture.Type.SKIN ? SKIN_DIR : CAPE_DIR;
        File targetFile = new File(targetDir, textureHash + ".png");

        if (targetFile.exists()) {
            try {
                // 读取本地文件
                BufferedImage image = ImageIO.read(targetFile);
                if (image != null) {
                    // 上传纹理到 OpenGL
                    int textureId = TextureUtil.uploadTextureImage(GL11.glGenTextures(), image);

                    // 创建自定义 ResourceLocation
                    ResourceLocation textureLocation = new ResourceLocation("custom", "texture_" + textureId);

                    // 将纹理 ID 缓存起来
                    textureCache.put(textureHash, textureLocation);

                    // 返回缓存的 ResourceLocation
                    return textureLocation;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 如果文件不存在或读取失败，返回默认纹理
        return defaultTexture;
    }

    /**
     * 绑定纹理
     */
    public void bindTexture(ResourceLocation texture) {
        if (texture.getResourceDomain()
            .equals("custom")) {
            // 如果是自定义纹理，直接绑定纹理 ID
            int textureId = Integer.parseInt(
                texture.getResourcePath()
                    .replace("texture_", ""));
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
        } else {
            // 如果是默认纹理，使用 Minecraft 的渲染引擎绑定
            Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        }
    }

    /**
     * 获取完整的 GameProfile 信息，并检查玩家名的有效性
     * 如果捕获异常，清空 TileEntity 的 NBT 数据并将玩家名加入黑名单
     */
    private GameProfile getGameProfile(GameProfile profile, TileEntity tileEntity) {
        // 检查玩家名是否有效
        if (profile == null || !isValidUsername(profile.getName())) {
            return profile; // 如果玩家名无效，直接返回原始 profile
        }

        // 检查玩家名是否在黑名单中
        if (BLACKLISTED_GAMEPROFILE.contains(profile.getName())) {
            return profile; // 如果玩家名在黑名单中，直接返回原始 profile
        }

        // 检查 profile 是否完整，或者是否包含 textures 属性
        if (!profile.isComplete() || !profile.getProperties()
            .containsKey("textures")) {
            try {
                // 通过玩家名获取 GameProfile
                GameProfile gameprofile = MinecraftServer.getServer()
                    .func_152358_ax()
                    .func_152655_a(profile.getName());

                // 检查获取的 GameProfile 是否有效
                if (gameprofile != null) {
                    // 获取 textures 属性
                    Property property = (Property) Iterables.getFirst(
                        gameprofile.getProperties()
                            .get("textures"),
                        (Object) null);

                    // 如果 textures 属性为空，尝试填充 profile 属性
                    if (property == null) {
                        gameprofile = MinecraftServer.getServer()
                            .func_147130_as()
                            .fillProfileProperties(gameprofile, true);
                    }

                    return gameprofile;
                }
            } catch (Exception e) {
                // 捕获异常，将玩家名加入黑名单
                BLACKLISTED_GAMEPROFILE.add(profile.getName());

                // 清空 TileEntity 的 NBT 数据
                if (tileEntity != null) {
                    NBTTagCompound nbt = new NBTTagCompound();
                    tileEntity.writeToNBT(nbt); // 将当前 NBT 数据写入临时变量
                    nbt.removeTag("SkullOwner"); // 移除 SkullOwner 标签
                    tileEntity.readFromNBT(nbt); // 将修改后的 NBT 数据写回 TileEntity
                }

                if (MainConfig.enableDebugMode) {
                    // 记录日志
                    System.err.println("Failed to fetch GameProfile for username: " + profile.getName());
                    e.printStackTrace();
                }

                // 返回默认的 GameProfile（当前玩家）
                String playerName = Minecraft.getMinecraft().thePlayer.getCommandSenderName();
                return new GameProfile(null, playerName);
            }
        }

        // 如果 profile 已经完整或不需要更新，直接返回原始 profile
        return profile;
    }

    /**
     * 检查玩家名是否有效
     */
    private boolean isValidUsername(String username) {
        if (username == null || username.length() < 3 || username.length() > 16) {
            return false; // 长度不符合要求
        }
        // 使用正则表达式检查合法字符
        return username.matches("^[a-zA-Z0-9_\\-]+$");
    }

    private void renderModelParts(String... partNames) {
        for (String partName : partNames) {
            modelCustom.renderPart(partName);
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        offlineMode = false; // 重置离线模式
    }
}
