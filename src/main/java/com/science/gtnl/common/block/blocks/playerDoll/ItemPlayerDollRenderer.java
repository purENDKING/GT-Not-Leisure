package com.science.gtnl.common.block.blocks.playerDoll;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
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

            // 从 ItemStack 中获取 SkullOwner 的 GameProfile
            GameProfile profile = null;
            if (item.hasTagCompound()) {
                NBTTagCompound nbt = item.getTagCompound();
                if (nbt.hasKey("SkullOwner", 8)) { // 8 表示 NBTTagString
                    // SkullOwner 是字符串，获取玩家名称并通过 SessionService 获取完整的 GameProfile
                    String playerName = nbt.getString("SkullOwner");
                    profile = MinecraftServer.getServer()
                        .func_152358_ax() // 获取 GameProfile 缓存
                        .func_152655_a(playerName); // 从 Mojang API 或缓存中获取完整的 GameProfile
                } else if (nbt.hasKey("SkullOwner", 10)) { // 10 表示 NBTTagCompound
                    // SkullOwner 是复合标签，使用 NBTUtil 解析 GameProfile
                    String playerName = nbt.getString("SkullOwner");
                    profile = MinecraftServer.getServer()
                        .func_152358_ax() // 获取 GameProfile 缓存
                        .func_152655_a(playerName); // 从 Mojang API 或缓存中获取完整的 GameProfile
                }
            }

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
                // 如果 SkullOwner 无效，使用默认皮肤和披风
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

    private void renderModelParts(String... partNames) {
        for (String partName : partNames) {
            modelCustom.renderPart(partName);
        }
    }
}
