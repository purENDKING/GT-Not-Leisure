package com.science.gtnl.common.block.blocks.playerDoll;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ItemPlayerDollRenderer implements IItemRenderer {

    private static final ResourceLocation DEFAULT_SKIN = new ResourceLocation("sciencenotleisure:model/skin.png");

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
        GL11.glPushMatrix();

        // 根据渲染类型调整模型的角度和位置
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

        // 获取玩家名称
        String playerName = null;
        if (item.hasTagCompound() && item.getTagCompound()
            .hasKey("PlayerName")) {
            playerName = item.getTagCompound()
                .getString("PlayerName");
        }

        // 获取玩家皮肤纹理
        ResourceLocation skinTexture = getResourceLocation(playerName);

        // 绑定纹理并渲染模型
        Minecraft.getMinecraft().renderEngine.bindTexture(skinTexture);
        PlayerDollRenderer.model.renderAll();

        GL11.glPopMatrix();
    }

    private static @NotNull ResourceLocation getResourceLocation(String playerName) {
        ResourceLocation skinTexture = DEFAULT_SKIN;
        if (playerName != null && !playerName.isEmpty()) {
            GameProfile profile = new GameProfile(null, playerName);
            AbstractClientPlayer player = new AbstractClientPlayer(Minecraft.getMinecraft().theWorld, profile) {

                @Override
                public void addChatMessage(IChatComponent message) {}

                @Override
                public boolean canCommandSenderUseCommand(int permissionLevel, String command) {
                    return false;
                }

                @Override
                public ChunkCoordinates getPlayerCoordinates() {
                    return null;
                }
            };

            skinTexture = player.getLocationSkin();

            if (skinTexture == null) {
                skinTexture = DEFAULT_SKIN;
            }
        }
        return skinTexture;
    }
}
