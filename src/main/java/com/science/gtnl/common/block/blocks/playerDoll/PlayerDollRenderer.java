package com.science.gtnl.common.block.blocks.playerDoll;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.science.gtnl.config.MainConfig;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PlayerDollRenderer extends TileEntitySpecialRenderer {

    public PlayerDollRenderer() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlayerDoll.class, this);
    }

    public static final ResourceLocation DEFAULT_SKIN = new ResourceLocation("sciencenotleisure:model/skin.png");
    public static final ResourceLocation DEFAULT_CAPE = new ResourceLocation("sciencenotleisure:model/cape.png");
    public static final ResourceLocation MODEL_RESOURCE = new ResourceLocation(
        "sciencenotleisure:model/PlayerDoll.obj");
    public static final IModelCustom modelCustom = AdvancedModelLoader.loadModel(MODEL_RESOURCE);

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        if (!(tileEntity instanceof TileEntityPlayerDoll tileEntityPlayerDoll)) {
            return; // 如果不是 TileEntityPlayerDoll，直接返回
        }

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
            // 获取 GameProfile
            GameProfile profile = tileEntityPlayerDoll.getSkullOwner();

            // 如果 GameProfile 有效，加载皮肤和披风纹理
            if (profile != null) {
                Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> textureMap = minecraft.func_152342_ad()
                    .func_152788_a(profile);

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
            } else {
                // 如果 GameProfile 无效，使用默认纹理
                skinTexture = minecraft.thePlayer.getLocationSkin();
                capeTexture = minecraft.thePlayer.getLocationCape();
            }

            // 确保皮肤和披风纹理不为 null
            if (skinTexture == null) {
                skinTexture = DEFAULT_SKIN;
            }
            if (capeTexture == null) {
                capeTexture = DEFAULT_CAPE;
            }
        }

        // 绑定皮肤纹理并渲染模型
        minecraft.renderEngine.bindTexture(skinTexture);
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
        minecraft.renderEngine.bindTexture(capeTexture);
        modelCustom.renderPart("cape");

        GL11.glPopMatrix();
    }

    private void renderModelParts(String... partNames) {
        for (String partName : partNames) {
            modelCustom.renderPart(partName);
        }
    }
}
