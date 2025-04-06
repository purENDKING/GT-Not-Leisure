package com.science.gtnl.common.block.blocks.playerDoll;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.event.world.WorldEvent;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.science.gtnl.config.MainConfig;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockPlayerDollRenderer extends TileEntitySpecialRenderer {

    private static boolean offlineMode = false;
    private static boolean isSteveModel = false;
    private static final Set<String> BLACKLISTED_UUIDS = Sets.newConcurrentHashSet();
    private static final Set<String> BLACKLISTED_SKIN_URLS = Sets.newConcurrentHashSet();
    private static final Set<String> BLACKLISTED_CAPE_URLS = Sets.newConcurrentHashSet();
    private static final Map<String, String> UUID_CACHE = new HashMap<>();
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
        if (!SKIN_DIR.exists()) SKIN_DIR.mkdirs();
        if (!CAPE_DIR.exists()) CAPE_DIR.mkdirs();
        if (!CUSTOM_SKIN_DIR.exists()) CUSTOM_SKIN_DIR.mkdirs();
        if (!CUSTOM_CAPE_DIR.exists()) CUSTOM_CAPE_DIR.mkdirs();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        if (tileEntity instanceof TileEntityPlayerDoll) {
            GL11.glPushMatrix();
            GL11.glTranslated(x + 0.5, y, z + 0.5);

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
            GL11.glRotatef(180, 0, 1, 0);

            ResourceLocation skinTexture = DEFAULT_SKIN;
            ResourceLocation capeTexture = DEFAULT_CAPE;

            NBTTagCompound nbt = new NBTTagCompound();
            tileEntity.writeToNBT(nbt);

            if (nbt.hasKey("SkinHttp", 8)) {
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
                    skinTexture = downloadAndCacheCustomSkin(skinHttp);
                    if (skinTexture == null) {
                        skinTexture = DEFAULT_SKIN;
                    }
                    renderModel(skinTexture, capeTexture, enableElytra);
                    GL11.glPopMatrix();
                    return;

                }
            }

            if (MainConfig.enableCustomPlayerDoll && !offlineMode) {
                String ownerUUID = null;
                String playerName = null;

                if (nbt.hasKey("SkullOwner", 8)) {
                    playerName = nbt.getString("SkullOwner");
                    if (playerName == null || playerName.isEmpty()) {
                        playerName = Minecraft.getMinecraft().thePlayer.getCommandSenderName();
                    }
                    if (!isValidUsername(playerName)) {
                        playerName = Minecraft.getMinecraft().thePlayer.getCommandSenderName();
                    }

                    String cachedUUID = UUID_CACHE.get(playerName.toLowerCase());
                    if (nbt.hasKey("OwnerUUID", 8)) {
                        ownerUUID = nbt.getString("OwnerUUID");

                        if (cachedUUID != null && cachedUUID.equals(ownerUUID)) {
                            ownerUUID = cachedUUID;
                            nbt.setString("OwnerUUID", ownerUUID);
                        } else {
                            String freshUUID = fetchUUID(playerName);
                            if (freshUUID != null) {
                                ownerUUID = freshUUID;
                                nbt.setString("OwnerUUID", ownerUUID);
                            }
                        }
                    } else {
                        if (cachedUUID != null) {
                            ownerUUID = cachedUUID;
                            nbt.setString("OwnerUUID", ownerUUID);
                        } else {
                            ownerUUID = fetchUUID(playerName);
                            if (ownerUUID != null) {
                                nbt.setString("OwnerUUID", ownerUUID);
                            }
                        }
                    }
                } else if (nbt.hasKey("OwnerUUID", 8)) {
                    ownerUUID = nbt.getString("OwnerUUID");
                }

                if (ownerUUID != null && !BLACKLISTED_UUIDS.contains(ownerUUID)) {
                    File cachedFile = new File(SKIN_DIR, ownerUUID + ".png");
                    if (cachedFile.exists()) {
                        skinTexture = getLocalTextureFromFile(cachedFile);
                    } else {
                        String skinUrl = fetchSkinUrl(ownerUUID);
                        if (skinUrl != null) {
                            skinTexture = downloadAndCacheSkinFromUrl(skinUrl, ownerUUID);
                        }
                    }
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
            }

            boolean enableElytra = false;
            if (nbt.hasKey("enableElytra")) {
                enableElytra = nbt.getBoolean("enableElytra");
            }

            if (skinTexture == null) {
                skinTexture = DEFAULT_SKIN;
            }

            renderModel(skinTexture, capeTexture, enableElytra);
            GL11.glPopMatrix();
        }
    }

    private void renderModel(ResourceLocation skinTexture, ResourceLocation capeTexture, boolean enableElytra) {
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
    }

    private String fetchUUID(String username) {
        // 检查缓存
        if (UUID_CACHE.containsKey(username.toLowerCase())) {
            return UUID_CACHE.get(username.toLowerCase());
        }

        if (BLACKLISTED_UUIDS.contains(username.toLowerCase())) {
            return null;
        }

        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);

            if (connection.getResponseCode() == 204) {
                BLACKLISTED_UUIDS.add(username.toLowerCase());
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            String json = response.toString();
            if (json.contains("\"errorMessage\"")) {
                BLACKLISTED_UUIDS.add(username.toLowerCase());
                return null;
            }

            JsonObject jsonObject = new JsonParser().parse(json)
                .getAsJsonObject();
            String uuid = jsonObject.get("id")
                .getAsString();

            UUID_CACHE.put(username.toLowerCase(), uuid);
            return uuid;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String fetchSkinUrl(String uuid) {
        try {
            URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);

            if (connection.getResponseCode() != 200) {
                BLACKLISTED_UUIDS.add(uuid);
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            String json = response.toString();
            if (json.contains("\"errorMessage\"")) {
                BLACKLISTED_UUIDS.add(uuid);
                return null;
            }

            JsonObject profileJson = new JsonParser().parse(json)
                .getAsJsonObject();
            String value = profileJson.getAsJsonArray("properties")
                .get(0)
                .getAsJsonObject()
                .get("value")
                .getAsString();
            String decoded = new String(
                Base64.getDecoder()
                    .decode(value));
            JsonObject texturesJson = new JsonParser().parse(decoded)
                .getAsJsonObject();
            return texturesJson.getAsJsonObject("textures")
                .getAsJsonObject("SKIN")
                .get("url")
                .getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            BLACKLISTED_UUIDS.add(uuid);
            return null;
        }
    }

    private ResourceLocation downloadAndCacheSkinFromUrl(String url, String uuid) {
        if (BLACKLISTED_SKIN_URLS.contains(url)) {
            return DEFAULT_SKIN;
        }

        File targetFile = new File(SKIN_DIR, uuid + ".png");

        if (targetFile.exists()) {
            return getLocalTextureFromFile(targetFile);
        }

        int maxRetries = 3;
        int retryCount = 0;

        while (retryCount < maxRetries) {
            try (InputStream in = new URL(url).openStream(); FileOutputStream out = new FileOutputStream(targetFile)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }

                if (isValidImage(targetFile)) {
                    return getLocalTextureFromFile(targetFile);
                } else {
                    targetFile.delete();
                    BLACKLISTED_SKIN_URLS.add(url);
                    return DEFAULT_SKIN;
                }
            } catch (IOException e) {
                retryCount++;
                if (retryCount >= maxRetries) {
                    BLACKLISTED_SKIN_URLS.add(url);
                    offlineMode = true;
                    return DEFAULT_SKIN;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return DEFAULT_SKIN;
    }

    private ResourceLocation downloadAndCacheCustomSkin(String skinHttp) {
        String fileName = Integer.toHexString(skinHttp.hashCode()) + ".png";
        File targetFile = new File(CUSTOM_SKIN_DIR, fileName);

        if (targetFile.exists()) {
            return getLocalTextureFromFile(targetFile);
        }

        if (BLACKLISTED_SKIN_URLS.contains(skinHttp)) {
            return null;
        }

        if (skinHttp == null || skinHttp.trim()
            .isEmpty()) {
            return null;
        }

        if (!skinHttp.trim()
            .toLowerCase()
            .startsWith("http")) {
            return null;
        }

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

                if (isValidImage(targetFile)) {
                    return getLocalTextureFromFile(targetFile);
                } else {
                    targetFile.delete();
                    BLACKLISTED_SKIN_URLS.add(skinHttp);
                    return null;
                }
            } catch (IOException e) {
                retryCount++;
                if (retryCount >= maxRetries) {
                    BLACKLISTED_SKIN_URLS.add(skinHttp);
                    return null;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }

    private ResourceLocation downloadAndCacheCustomCape(String capeHttp) {
        String fileName = Integer.toHexString(capeHttp.hashCode()) + ".png";
        File targetFile = new File(CUSTOM_CAPE_DIR, fileName);

        if (targetFile.exists()) {
            return getLocalTextureFromFile(targetFile);
        }

        if (BLACKLISTED_CAPE_URLS.contains(capeHttp)) {
            return null;
        }

        if (capeHttp == null || capeHttp.trim()
            .isEmpty()) {
            return null;
        }

        if (!capeHttp.trim()
            .toLowerCase()
            .startsWith("http")) {
            return null;
        }

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

                if (isValidImage(targetFile)) {
                    return getLocalTextureFromFile(targetFile);
                } else {
                    targetFile.delete();
                    BLACKLISTED_CAPE_URLS.add(capeHttp);
                    return null;
                }
            } catch (IOException e) {
                retryCount++;
                if (retryCount >= maxRetries) {
                    BLACKLISTED_CAPE_URLS.add(capeHttp);
                    return null;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }

    private boolean isValidImage(File file) {
        try (InputStream in = new FileInputStream(file)) {
            byte[] header = new byte[8];
            if (in.read(header) != 8) {
                return false;
            }

            boolean isPng = header[0] == (byte) 0x89 && header[1] == (byte) 0x50
                && header[2] == (byte) 0x4E
                && header[3] == (byte) 0x47
                && header[4] == (byte) 0x0D
                && header[5] == (byte) 0x0A
                && header[6] == (byte) 0x1A
                && header[7] == (byte) 0x0A;

            boolean isJpg = header[0] == (byte) 0xFF && header[1] == (byte) 0xD8 && header[2] == (byte) 0xFF;

            if (!isPng && !isJpg) {
                return false;
            }

            BufferedImage image = ImageIO.read(file);
            if (image == null) {
                return false;
            }

            int width = image.getWidth();
            int height = image.getHeight();

            if (width > 1024 || height > 1024) {
                return false;
            }

            int pixel = image.getRGB(54, 20);
            boolean isTransparent = (pixel >> 24) == 0x00;
            isSteveModel = !isTransparent;

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private ResourceLocation getLocalTextureFromFile(File file) {
        if (file == null || !file.exists()) {
            return null;
        }

        String fileName = file.getName();

        if (textureCache.containsKey(fileName)) {
            return textureCache.get(fileName);
        }

        try {
            BufferedImage image = ImageIO.read(file);
            if (image != null) {
                int textureId = TextureUtil.uploadTextureImage(GL11.glGenTextures(), image);
                ResourceLocation textureLocation = new ResourceLocation("custom", "texture_" + textureId);
                textureCache.put(fileName, textureLocation);
                return textureLocation;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void bindTexture(ResourceLocation texture) {
        if (texture.getResourceDomain()
            .equals("custom")) {
            int textureId = Integer.parseInt(
                texture.getResourcePath()
                    .replace("texture_", ""));
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
        } else {
            Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        }
    }

    private boolean isValidUsername(String username) {
        if (username == null || username.length() < 3 || username.length() > 16) {
            return false;
        }
        return username.matches("^[a-zA-Z0-9_\\-]+$");
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        offlineMode = false;
    }
}
