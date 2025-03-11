package com.science.gtnl.common.block.blocks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ItemPlayerDollRenderer implements IItemRenderer {

    private static final ResourceLocation DEFAULT_SKIN = new ResourceLocation("sciencenotleisure:model/skin.png");
    private static final ResourceLocation DEFAULT_CAPE = new ResourceLocation("sciencenotleisure:model/cape.png");

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
        GL11.glScalef(0.8f, 0.8f, 0.8f);
        GL11.glTranslatef(0f, 0f, 0f);

        String playerName = null;
        if (item.hasTagCompound() && item.getTagCompound()
            .hasKey("PlayerName")) {
            playerName = item.getTagCompound()
                .getString("PlayerName");
        }

        ResourceLocation skinTexture = DEFAULT_SKIN;
        ResourceLocation capeTexture = DEFAULT_CAPE;
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
            capeTexture = player.getLocationCape();

            if (skinTexture == null) {
                skinTexture = DEFAULT_SKIN;
            }
            if (capeTexture == null) {
                capeTexture = DEFAULT_CAPE;
            }
        }

        Minecraft.getMinecraft().renderEngine.bindTexture(skinTexture);
        Minecraft.getMinecraft().renderEngine.bindTexture(capeTexture);

        PlayerDollRenderer.model.renderAll();

        GL11.glPopMatrix();
    }
}
