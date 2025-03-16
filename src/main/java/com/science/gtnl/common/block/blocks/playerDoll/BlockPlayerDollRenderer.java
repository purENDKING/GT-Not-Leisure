package com.science.gtnl.common.block.blocks.playerDoll;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
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
    private static boolean isSteveModel = false; // 默认为 false，表示使用 Alex 模型
    private static final Set<String> BLACKLISTED_GAMEPROFILE = Sets.newConcurrentHashSet();
    private static final Set<String> BLACKLISTED_SKIN_URLS = Sets.newConcurrentHashSet();
    private static final Set<String> BLACKLISTED_CAPE_URLS = Sets.newConcurrentHashSet();
    private final Map<String, ResourceLocation> textureCache = new HashMap<>();
    public static final ResourceLocation DEFAULT_SKIN = new ResourceLocation("sciencenotleisure:model/skin.png");
    public static final ResourceLocation DEFAULT_CAPE = new ResourceLocation("sciencenotleisure:model/cape.png");
    public static final ResourceLocation MODEL_STEVE = new ResourceLocation(
        "sciencenotleisure:model/PlayerDollSteve.obj");
    public static final ResourceLocation MODEL_ALEX = new ResourceLocation(
        "sciencenotleisure:model/PlayerDollAlex.obj");
    public static final ResourceLocation MODEL_STEVE_ELYTRA = new ResourceLocation(
        "sciencenotleisure:model/PlayerDollSteve.obj");
    public static final ResourceLocation MODEL_ALEX_ELYTRA = new ResourceLocation(
        "sciencenotleisure:model/PlayerDollAlexElytra.obj");
    public static final IModelCustom modelSteve = AdvancedModelLoader.loadModel(MODEL_STEVE);
    public static final IModelCustom modelAlex = AdvancedModelLoader.loadModel(MODEL_ALEX);
    public static final IModelCustom modelSteveElytra = AdvancedModelLoader.loadModel(MODEL_STEVE_ELYTRA);
    public static final IModelCustom modelAlexElytra = AdvancedModelLoader.loadModel(MODEL_ALEX_ELYTRA);

    private static final File SKIN_DIR = new File("config/GTNotLeisure/skin");
    private static final File CAPE_DIR = new File("config/GTNotLeisure/cape");
    private static final File CUSTOM_SKIN_DIR = new File("config/GTNotLeisure/custom_skin");
    private static final File CUSTOM_CAPE_DIR = new File("config/GTNotLeisure/custom_cape");

    static {
        // 确保目录存在
        if (!SKIN_DIR.exists()) SKIN_DIR.mkdirs();
        if (!CAPE_DIR.exists()) CAPE_DIR.mkdirs();
        if (!CUSTOM_SKIN_DIR.exists()) CUSTOM_SKIN_DIR.mkdirs();
        if (!CUSTOM_CAPE_DIR.exists()) CUSTOM_CAPE_DIR.mkdirs();
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

            // 获取 TileEntity 的 NBT 数据
            NBTTagCompound nbt = new NBTTagCompound();
            tileEntity.writeToNBT(nbt);

            // 检查是否存在 SkinHttp 字符串
            if (nbt.hasKey("SkinHttp", 8)) { // 8 表示 NBTTagString
                String skinHttp = nbt.getString("SkinHttp");
                boolean enableElytra = false;
                if (nbt.hasKey("enableElytra")) {
                    enableElytra = nbt.getBoolean("enableElytra");
                }
                if (nbt.hasKey("CapeHttp", 8)) {
                    String capeHttp = nbt.getString("CapeHttp");
                    if (!StringUtils.isNullOrEmpty(capeHttp)) {
                        capeTexture = downloadAndCacheCustomCape(capeHttp);
                    }
                    if (capeTexture == null) {
                        capeTexture = DEFAULT_CAPE;
                    }
                }
                if (!StringUtils.isNullOrEmpty(skinHttp)) {
                    // 尝试下载并缓存皮肤
                    skinTexture = downloadAndCacheCustomSkin(skinHttp);
                    if (skinTexture != null) {
                        // 如果下载成功，绑定纹理并渲染模型
                        bindTexture(skinTexture);
                        if (isSteveModel) {
                            if (enableElytra) {
                                modelSteveElytra.renderPart("Body");
                                modelSteveElytra.renderPart("Body_Layer");
                                modelSteveElytra.renderPart("Hat");
                                modelSteveElytra.renderPart("Hat_Layer");
                                modelSteveElytra.renderPart("Head");
                                modelSteveElytra.renderPart("Left_Arm");
                                modelSteveElytra.renderPart("Left_Arm_Layer");
                                modelSteveElytra.renderPart("Left_Leg");
                                modelSteveElytra.renderPart("Left_Leg_Layer");
                                modelSteveElytra.renderPart("Right_Arm");
                                modelSteveElytra.renderPart("Right_Arm_Layer");
                                modelSteveElytra.renderPart("Right_Leg");
                                modelSteveElytra.renderPart("Right_Leg_Layer");
                                bindTexture(capeTexture);
                                modelSteveElytra.renderPart("ElytraRight");
                                modelSteveElytra.renderPart("ElytraLeft");
                            } else {
                                modelSteve.renderPart("Body");
                                modelSteve.renderPart("Body_Layer");
                                modelSteve.renderPart("Hat");
                                modelSteve.renderPart("Hat_Layer");
                                modelSteve.renderPart("Head");
                                modelSteve.renderPart("Left_Arm");
                                modelSteve.renderPart("Left_Arm_Layer");
                                modelSteve.renderPart("Left_Leg");
                                modelSteve.renderPart("Left_Leg_Layer");
                                modelSteve.renderPart("Right_Arm");
                                modelSteve.renderPart("Right_Arm_Layer");
                                modelSteve.renderPart("Right_Leg");
                                modelSteve.renderPart("Right_Leg_Layer");
                                bindTexture(capeTexture);
                                modelSteve.renderPart("cape");
                            }
                        } else {
                            if (enableElytra) {
                                modelAlexElytra.renderPart("Body");
                                modelAlexElytra.renderPart("Body_Layer");
                                modelAlexElytra.renderPart("Hat");
                                modelAlexElytra.renderPart("Hat_Layer");
                                modelAlexElytra.renderPart("Head");
                                modelAlexElytra.renderPart("Left_Arm");
                                modelAlexElytra.renderPart("Left_Arm_Layer");
                                modelAlexElytra.renderPart("Left_Leg");
                                modelAlexElytra.renderPart("Left_Leg_Layer");
                                modelAlexElytra.renderPart("Right_Arm");
                                modelAlexElytra.renderPart("Right_Arm_Layer");
                                modelAlexElytra.renderPart("Right_Leg");
                                modelAlexElytra.renderPart("Right_Leg_Layer");
                                bindTexture(capeTexture);
                                modelAlexElytra.renderPart("ElytraRight");
                                modelAlexElytra.renderPart("ElytraLeft");
                            } else {
                                modelAlex.renderPart("Body");
                                modelAlex.renderPart("Body_Layer");
                                modelAlex.renderPart("Hat");
                                modelAlex.renderPart("Hat_Layer");
                                modelAlex.renderPart("Head");
                                modelAlex.renderPart("Left_Arm");
                                modelAlex.renderPart("Left_Arm_Layer");
                                modelAlex.renderPart("Left_Leg");
                                modelAlex.renderPart("Left_Leg_Layer");
                                modelAlex.renderPart("Right_Arm");
                                modelAlex.renderPart("Right_Arm_Layer");
                                modelAlex.renderPart("Right_Leg");
                                modelAlex.renderPart("Right_Leg_Layer");
                                bindTexture(capeTexture);
                                modelAlex.renderPart("cape");
                            }
                        }
                        GL11.glPopMatrix();
                        return; // 直接返回，不再执行后续逻辑
                    }
                }
            }

            if (MainConfig.enableCustomPlayerDoll && !offlineMode) {
                GameProfile gameprofile = tileEntityPlayerDoll.getSkullOwner();

                if (nbt.hasKey("SkullOwner", 8)) { // 8 表示 NBTTagString
                    String playerName = nbt.getString("SkullOwner");
                    if (playerName == null || playerName.isEmpty()) {
                        playerName = Minecraft.getMinecraft().thePlayer.getCommandSenderName();
                    }
                    // 检查玩家名的合法性
                    if (!isValidUsername(playerName)) {
                        playerName = Minecraft.getMinecraft().thePlayer.getCommandSenderName();
                    }
                    gameprofile = new GameProfile(null, playerName);
                } else if (nbt.hasKey("SkullOwner", 10)) { // 10 表示 NBTTagCompound
                    NBTTagCompound ownerTag = nbt.getCompoundTag("SkullOwner");
                    gameprofile = NBTUtil.func_152459_a(ownerTag);
                    // 检查 GameProfile 的合法性
                    if (gameprofile == null || (gameprofile.getName() == null || gameprofile.getName()
                        .isEmpty()) && gameprofile.getId() == null) {
                        String playerName = Minecraft.getMinecraft().thePlayer.getCommandSenderName();
                        // 检查玩家名的合法性
                        if (!isValidUsername(playerName)) {
                            playerName = Minecraft.getMinecraft().thePlayer.getCommandSenderName();
                        }
                        gameprofile = new GameProfile(null, playerName);
                    } else if (!isValidUsername(gameprofile.getName())) {
                        // 如果玩家名不合法，使用默认值
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

            boolean enableElytra = false;
            if (nbt.hasKey("enableElytra")) {
                enableElytra = nbt.getBoolean("enableElytra");
            }

            if (nbt.hasKey("CapeHttp", 8)) {
                String capeHttp = nbt.getString("CapeHttp");
                if (!StringUtils.isNullOrEmpty(capeHttp)) {
                    capeTexture = downloadAndCacheCustomCape(capeHttp);
                }
                if (capeTexture == null) {
                    capeTexture = DEFAULT_CAPE;
                }
            }

            // 绑定皮肤纹理并渲染模型
            bindTexture(skinTexture);
            if (isSteveModel) {
                if (enableElytra) {
                    modelSteveElytra.renderPart("Body");
                    modelSteveElytra.renderPart("Body_Layer");
                    modelSteveElytra.renderPart("Hat");
                    modelSteveElytra.renderPart("Hat_Layer");
                    modelSteveElytra.renderPart("Head");
                    modelSteveElytra.renderPart("Left_Arm");
                    modelSteveElytra.renderPart("Left_Arm_Layer");
                    modelSteveElytra.renderPart("Left_Leg");
                    modelSteveElytra.renderPart("Left_Leg_Layer");
                    modelSteveElytra.renderPart("Right_Arm");
                    modelSteveElytra.renderPart("Right_Arm_Layer");
                    modelSteveElytra.renderPart("Right_Leg");
                    modelSteveElytra.renderPart("Right_Leg_Layer");
                    bindTexture(capeTexture);
                    modelSteveElytra.renderPart("ElytraRight");
                    modelSteveElytra.renderPart("ElytraLeft");
                } else {
                    modelSteve.renderPart("Body");
                    modelSteve.renderPart("Body_Layer");
                    modelSteve.renderPart("Hat");
                    modelSteve.renderPart("Hat_Layer");
                    modelSteve.renderPart("Head");
                    modelSteve.renderPart("Left_Arm");
                    modelSteve.renderPart("Left_Arm_Layer");
                    modelSteve.renderPart("Left_Leg");
                    modelSteve.renderPart("Left_Leg_Layer");
                    modelSteve.renderPart("Right_Arm");
                    modelSteve.renderPart("Right_Arm_Layer");
                    modelSteve.renderPart("Right_Leg");
                    modelSteve.renderPart("Right_Leg_Layer");
                    bindTexture(capeTexture);
                    modelSteve.renderPart("cape");
                }
            } else {
                if (enableElytra) {
                    modelAlexElytra.renderPart("Body");
                    modelAlexElytra.renderPart("Body_Layer");
                    modelAlexElytra.renderPart("Hat");
                    modelAlexElytra.renderPart("Hat_Layer");
                    modelAlexElytra.renderPart("Head");
                    modelAlexElytra.renderPart("Left_Arm");
                    modelAlexElytra.renderPart("Left_Arm_Layer");
                    modelAlexElytra.renderPart("Left_Leg");
                    modelAlexElytra.renderPart("Left_Leg_Layer");
                    modelAlexElytra.renderPart("Right_Arm");
                    modelAlexElytra.renderPart("Right_Arm_Layer");
                    modelAlexElytra.renderPart("Right_Leg");
                    modelAlexElytra.renderPart("Right_Leg_Layer");
                    bindTexture(capeTexture);
                    modelAlexElytra.renderPart("ElytraRight");
                    modelAlexElytra.renderPart("ElytraLeft");
                } else {
                    modelAlex.renderPart("Body");
                    modelAlex.renderPart("Body_Layer");
                    modelAlex.renderPart("Hat");
                    modelAlex.renderPart("Hat_Layer");
                    modelAlex.renderPart("Head");
                    modelAlex.renderPart("Left_Arm");
                    modelAlex.renderPart("Left_Arm_Layer");
                    modelAlex.renderPart("Left_Leg");
                    modelAlex.renderPart("Left_Leg_Layer");
                    modelAlex.renderPart("Right_Arm");
                    modelAlex.renderPart("Right_Arm_Layer");
                    modelAlex.renderPart("Right_Leg");
                    modelAlex.renderPart("Right_Leg_Layer");
                    bindTexture(capeTexture);
                    modelAlex.renderPart("cape");
                }
            }
            GL11.glPopMatrix();
        }
    }

    /**
     * 下载并缓存自定义皮肤
     */
    private ResourceLocation downloadAndCacheCustomSkin(String skinHttp) {

        // 生成唯一的文件名（使用 URL 的哈希值）
        String fileName = Integer.toHexString(skinHttp.hashCode()) + ".png";
        File targetFile = new File(CUSTOM_SKIN_DIR, fileName);

        // 如果文件已经存在，直接返回缓存的纹理
        if (targetFile.exists()) {
            return getLocalTextureFromFile(targetFile);
        }

        if (BLACKLISTED_SKIN_URLS.contains(skinHttp)) {
            return null;
        }

        // 检查 skinHttp 是否为空或空白
        if (skinHttp == null || skinHttp.trim()
            .isEmpty()) {
            return null;
        }

        // 检查 skinHttp 是否以 "http" 开头（不区分大小写）
        if (!skinHttp.trim()
            .toLowerCase()
            .startsWith("http")) {
            return null;
        }

        // 尝试下载皮肤
        int maxRetries = 10;
        int retryCount = 0;

        while (retryCount < maxRetries) {
            try (InputStream in = new URL(skinHttp).openStream();
                FileOutputStream out = new FileOutputStream(targetFile)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }

                // 检查下载的文件是否为有效的 JPG 或 PNG 图片
                if (isValidImage(targetFile)) {
                    return getLocalTextureFromFile(targetFile); // 下载成功，返回纹理
                } else {
                    // 如果不是有效的图片，删除文件并将链接加入黑名单
                    targetFile.delete();
                    BLACKLISTED_SKIN_URLS.add(skinHttp); // 加入黑名单
                    return null;
                }
            } catch (IOException e) {
                retryCount++;
                if (retryCount >= maxRetries) {
                    // 下载失败，将链接加入黑名单
                    BLACKLISTED_SKIN_URLS.add(skinHttp);
                    return null; // 下载失败，返回默认皮肤
                }
                try {
                    Thread.sleep(1000); // 等待 1 秒后重试
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return null; // 下载失败，返回默认皮肤
    }

    /**
     * 下载并缓存自定义披风
     */
    private ResourceLocation downloadAndCacheCustomCape(String capeHttp) {
        // 生成唯一的文件名（使用 URL 的哈希值）
        String fileName = Integer.toHexString(capeHttp.hashCode()) + ".png";
        File targetFile = new File(CUSTOM_CAPE_DIR, fileName);

        // 如果文件已经存在，直接返回缓存的纹理
        if (targetFile.exists()) {
            return getLocalTextureFromFile(targetFile);
        }

        if (BLACKLISTED_CAPE_URLS.contains(capeHttp)) {
            return null;
        }

        // 检查 capeHttp 是否为空或空白
        if (capeHttp == null || capeHttp.trim()
            .isEmpty()) {
            return null;
        }

        // 检查 capeHttp 是否以 "http" 开头（不区分大小写）
        if (!capeHttp.trim()
            .toLowerCase()
            .startsWith("http")) {
            return null;
        }

        // 尝试下载披风
        int maxRetries = 10;
        int retryCount = 0;

        while (retryCount < maxRetries) {
            try (InputStream in = new URL(capeHttp).openStream();
                FileOutputStream out = new FileOutputStream(targetFile)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }

                // 检查下载的文件是否为有效的 JPG 或 PNG 图片
                if (isValidImage(targetFile)) {
                    return getLocalTextureFromFile(targetFile); // 下载成功，返回纹理
                } else {
                    // 如果不是有效的图片，删除文件并将链接加入黑名单
                    targetFile.delete();
                    BLACKLISTED_CAPE_URLS.add(capeHttp); // 加入黑名单
                    return null;
                }
            } catch (IOException e) {
                retryCount++;
                if (retryCount >= maxRetries) {
                    // 下载失败，将链接加入黑名单
                    BLACKLISTED_CAPE_URLS.add(capeHttp);
                    return null; // 下载失败，返回默认披风
                }
                try {
                    Thread.sleep(1000); // 等待 1 秒后重试
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return null; // 下载失败，返回默认披风
    }

    /**
     * 检查文件是否为有效的 JPG 或 PNG 图片，并且分辨率不超过 1024x1024
     * 同时检查图片的从左到右54像素，从上到下20像素处是否有颜色，如果为透明使用Alex模型，否则使用Steve模型
     */
    private boolean isValidImage(File file) {
        try (InputStream in = new FileInputStream(file)) {
            // 读取文件的前 8 个字节（用于检查文件头）
            byte[] header = new byte[8];
            if (in.read(header) != 8) {
                return false; // 文件太小，无法判断
            }

            // 检查 PNG 文件头
            boolean isPng = header[0] == (byte) 0x89 && header[1] == (byte) 0x50
                && header[2] == (byte) 0x4E
                && header[3] == (byte) 0x47
                && header[4] == (byte) 0x0D
                && header[5] == (byte) 0x0A
                && header[6] == (byte) 0x1A
                && header[7] == (byte) 0x0A;

            // 检查 JPG 文件头
            boolean isJpg = header[0] == (byte) 0xFF && header[1] == (byte) 0xD8 && header[2] == (byte) 0xFF;

            // 如果不是有效的图片文件，直接返回 false
            if (!isPng && !isJpg) {
                return false;
            }

            // 读取图片的分辨率
            BufferedImage image = ImageIO.read(file);
            if (image == null) {
                return false; // 无法读取图片
            }

            int width = image.getWidth();
            int height = image.getHeight();

            // 检查分辨率是否超过 1024x1024
            if (width > 1024 || height > 1024) {
                return false;
            }

            // 检查图片的从左到右54像素，从上到下20像素处是否有颜色
            int pixel = image.getRGB(54, 20);
            boolean isTransparent = (pixel >> 24) == 0x00;

            // 根据透明度决定使用 Alex 或 Steve 模型
            isSteveModel = !isTransparent; // 如果不透明，使用 Steve 模型；否则使用 Alex 模型

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 从本地文件获取纹理，并上传到 OpenGL（仅上传一次）
     * 如果纹理无效，返回 null
     */
    private ResourceLocation getLocalTextureFromFile(File file) {
        // 检查文件是否有效
        if (file == null || !file.exists()) {
            return null;
        }

        // 获取文件名作为缓存的键
        String fileName = file.getName();

        // 如果缓存中已存在该纹理的 ResourceLocation，直接返回
        if (textureCache.containsKey(fileName)) {
            return textureCache.get(fileName);
        }

        try {
            // 读取本地文件
            BufferedImage image = ImageIO.read(file);
            if (image != null) {
                // 上传纹理到 OpenGL
                int textureId = TextureUtil.uploadTextureImage(GL11.glGenTextures(), image);

                // 创建自定义 ResourceLocation
                ResourceLocation textureLocation = new ResourceLocation("custom", "texture_" + textureId);

                // 将纹理 ID 缓存起来
                textureCache.put(fileName, textureLocation);

                // 返回缓存的 ResourceLocation
                return textureLocation;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 如果文件不存在或读取失败，返回 null
        return null;
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

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        offlineMode = false; // 重置离线模式
    }
}
