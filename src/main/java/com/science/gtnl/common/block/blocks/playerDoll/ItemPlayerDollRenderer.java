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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.IItemRenderer;
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
public class ItemPlayerDollRenderer implements IItemRenderer {

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
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        Minecraft minecraft = Minecraft.getMinecraft();
        GL11.glPushMatrix();

        // 根据不同渲染类型设置变换
        switch (type) {
            case EQUIPPED: // 第三人称手持时的渲染
                GL11.glScalef(1f, 1f, 1f);
                GL11.glTranslatef(0.6f, 0f, 0.6f);
                GL11.glRotatef(0, 0f, 1f, 0f);
                break;
            case EQUIPPED_FIRST_PERSON: // 第一人称视角手持时的渲染
                GL11.glScalef(1.5f, 1.5f, 1.5f);
                GL11.glTranslatef(0.3f, -0.1f, 0.3f);
                GL11.glRotatef(-90, 0f, 1f, 0f);
                break;
            case ENTITY: // 掉落物的渲染
                GL11.glScalef(1f, 1f, 1f);
                GL11.glTranslatef(0f, -0.5f, 0f);
                GL11.glRotatef(90, 0f, 1f, 0f);
                break;
            case INVENTORY: // 物品栏中的渲染
                GL11.glScalef(1f, 1f, 1f);
                GL11.glTranslatef(0f, -0.6f, 0f);
                GL11.glRotatef(90, 0f, 1f, 0f);
                break;
            default:
                break;
        }

        // 初始化纹理
        ResourceLocation skinTexture = DEFAULT_SKIN;
        ResourceLocation capeTexture = DEFAULT_CAPE;

        if (MainConfig.enableCustomPlayerDoll) {
            GameProfile skullOwner = null;

            if (item.hasTagCompound()) {
                NBTTagCompound nbt = item.getTagCompound();

                // 检查 SkullOwner 数据
                if (nbt.hasKey("SkullOwner", 8)) { // 8 表示 NBTTagString
                    // SkullOwner 是字符串，直接获取玩家名称
                    String playerName = nbt.getString("SkullOwner");
                    skullOwner = new GameProfile(null, playerName);
                } else if (nbt.hasKey("SkullOwner", 10)) { // 10 表示 NBTTagCompound
                    // SkullOwner 是复合标签，使用 NBTUtil 解析 GameProfile
                    NBTTagCompound ownerTag = nbt.getCompoundTag("SkullOwner");
                    skullOwner = NBTUtil.func_152459_a(ownerTag);
                }
            }

            if (skullOwner != null) {
                skullOwner = getGameProfile(skullOwner); // 获取完整的 GameProfile
            } else {
                // 如果没有 SkullOwner 数据，使用默认玩家（当前玩家）
                String playerName = Minecraft.getMinecraft().thePlayer.getCommandSenderName();
                skullOwner = new GameProfile(null, playerName);
                skullOwner = getGameProfile(skullOwner); // 获取完整的 GameProfile
            }

            // 如果 GameProfile 有效，加载皮肤和披风纹理
            if (skullOwner != null) {
                Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> textureMap = minecraft.func_152342_ad()
                    .func_152788_a(skullOwner);

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
    private void bindTexture(ResourceLocation texture) {
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
