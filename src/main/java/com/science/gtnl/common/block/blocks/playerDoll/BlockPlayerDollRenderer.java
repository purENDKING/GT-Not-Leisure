package com.science.gtnl.common.block.blocks.playerDoll;

import java.util.Map;

import net.minecraft.client.Minecraft;
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
                        skinTexture = minecraft.func_152342_ad()
                            .func_152792_a(
                                textureMap.get(MinecraftProfileTexture.Type.SKIN),
                                MinecraftProfileTexture.Type.SKIN);
                    }
                    if (textureMap.containsKey(MinecraftProfileTexture.Type.CAPE)) {
                        capeTexture = minecraft.func_152342_ad()
                            .func_152792_a(
                                textureMap.get(MinecraftProfileTexture.Type.CAPE),
                                MinecraftProfileTexture.Type.CAPE);
                    }
                }
            }

            // 绑定皮肤纹理并渲染模型
            Minecraft.getMinecraft().renderEngine.bindTexture(skinTexture);
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
            Minecraft.getMinecraft().renderEngine.bindTexture(capeTexture);
            modelCustom.renderPart("cape");

            GL11.glPopMatrix();
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
