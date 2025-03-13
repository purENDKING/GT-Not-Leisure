package com.science.gtnl.common.block.blocks.playerDoll;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

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

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.properties.Property;
import com.science.gtnl.config.MainConfig;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockPlayerDollRenderer extends TileEntitySpecialRenderer {

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

            if (MainConfig.enableCustomPlayerDoll) {
                // 获取 TileEntity 的 NBT 数据
                NBTTagCompound nbt = new NBTTagCompound();
                tileEntity.writeToNBT(nbt); // 将 TileEntity 数据写入 NBT
                GameProfile gameprofile = tileEntityPlayerDoll.getSkullOwner();

                if (nbt.hasKey("SkullOwner", 8)) { // 8 表示 NBTTagString
                    // SkullOwner 是字符串，直接获取玩家名称
                    String playerName = nbt.getString("SkullOwner");
                    gameprofile = new GameProfile(null, playerName);
                } else if (nbt.hasKey("SkullOwner", 10)) { // 10 表示 NBTTagCompound
                    // SkullOwner 是复合标签，使用 NBTUtil 解析 GameProfile
                    NBTTagCompound ownerTag = nbt.getCompoundTag("SkullOwner");
                    gameprofile = NBTUtil.func_152459_a(ownerTag);
                }

                if (gameprofile != null) {
                    gameprofile = getGameProfile(gameprofile); // 获取完整的 GameProfile
                } else {
                    // 如果没有 SkullOwner 数据，使用默认玩家（当前玩家）
                    String playerName = Minecraft.getMinecraft().thePlayer.getCommandSenderName();
                    gameprofile = new GameProfile(null, playerName);
                    gameprofile = getGameProfile(gameprofile); // 获取完整的 GameProfile
                }

                // 如果 GameProfile 有效，加载皮肤和披风纹理
                if (gameprofile != null) {
                    Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> textureMap = minecraft.func_152342_ad()
                        .func_152788_a(gameprofile);

                    if (textureMap.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                        MinecraftProfileTexture skin = textureMap.get(MinecraftProfileTexture.Type.SKIN);
                        downloadTexture(skin, MinecraftProfileTexture.Type.SKIN); // 下载皮肤
                        skinTexture = getLocalTexture(skin, MinecraftProfileTexture.Type.SKIN, DEFAULT_SKIN); // 获取本地纹理
                    }
                    if (textureMap.containsKey(MinecraftProfileTexture.Type.CAPE)) {
                        MinecraftProfileTexture cape = textureMap.get(MinecraftProfileTexture.Type.CAPE);
                        downloadTexture(cape, MinecraftProfileTexture.Type.CAPE); // 下载披风
                        capeTexture = getLocalTexture(cape, MinecraftProfileTexture.Type.CAPE, DEFAULT_CAPE); // 获取本地纹理
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
        }
    }

    /**
     * 下载皮肤或披风文件到本地
     */
    private void downloadTexture(MinecraftProfileTexture texture, MinecraftProfileTexture.Type type) {
        File targetDir = type == MinecraftProfileTexture.Type.SKIN ? SKIN_DIR : CAPE_DIR;
        File targetFile = new File(targetDir, texture.getHash() + ".png");

        if (!targetFile.exists()) {
            try (InputStream in = new URL(texture.getUrl()).openStream();
                FileOutputStream out = new FileOutputStream(targetFile)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 检查本地是否存在对应的皮肤或披风文件，并上传到 OpenGL
     */
    private ResourceLocation getLocalTexture(MinecraftProfileTexture texture, MinecraftProfileTexture.Type type,
        ResourceLocation defaultTexture) {
        File targetDir = type == MinecraftProfileTexture.Type.SKIN ? SKIN_DIR : CAPE_DIR;
        File targetFile = new File(targetDir, texture.getHash() + ".png");

        if (targetFile.exists()) {
            try {
                BufferedImage image = ImageIO.read(targetFile);
                if (image != null) {
                    int textureId = TextureUtil.uploadTextureImage(GL11.glGenTextures(), image); // 上传纹理到 OpenGL
                    return new ResourceLocation("custom", "texture_" + textureId); // 返回自定义 ResourceLocation
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    private GameProfile getGameProfile(GameProfile profile) {
        if (profile != null && !StringUtils.isNullOrEmpty(profile.getName())) {
            if (!profile.isComplete() || !profile.getProperties()
                .containsKey("textures")) {
                GameProfile gameprofile = MinecraftServer.getServer()
                    .func_152358_ax()
                    .func_152655_a(profile.getName());

                if (gameprofile != null) {
                    Property property = (Property) Iterables.getFirst(
                        gameprofile.getProperties()
                            .get("textures"),
                        (Object) null);

                    if (property == null) {
                        gameprofile = MinecraftServer.getServer()
                            .func_147130_as()
                            .fillProfileProperties(gameprofile, true);
                    }

                    return gameprofile;
                }
            }
        }
        return profile;
    }

    private void renderModelParts(String... partNames) {
        for (String partName : partNames) {
            modelCustom.renderPart(partName);
        }
    }
}
