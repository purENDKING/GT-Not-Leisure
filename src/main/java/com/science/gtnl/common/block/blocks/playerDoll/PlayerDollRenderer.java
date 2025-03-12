package com.science.gtnl.common.block.blocks.playerDoll;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PlayerDollRenderer extends TileEntitySpecialRenderer {

    public PlayerDollRenderer() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlayerDoll.class, this);
    }

    private static final ResourceLocation DEFAULT_SKIN = new ResourceLocation("sciencenotleisure:model/skin.png");

    private ResourceLocation skinTexture = DEFAULT_SKIN;
    public static final ModelPlayerDoll model = new ModelPlayerDoll();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        if (tileEntity instanceof TileEntityPlayerDoll tileEntityPlayerDoll) {
            String playerName = tileEntityPlayerDoll.getSkullOwner();
            GL11.glPushMatrix();
            GL11.glTranslated(x + 0.5, y, z + 0.5);

            // 如果玩家名称有效，加载皮肤和披风纹理
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

            int orientation = tileEntity.getBlockMetadata();
            if (orientation == 4) {
                GL11.glRotatef(90, 0, 1, 0);
            } else if (orientation == 5) {
                GL11.glRotatef(-90, 0, 1, 0);
            } else if (orientation == 3) {
                GL11.glRotatef(180, 0, 1, 0);
            }
            GL11.glRotatef(180, 0, 1, 0);
            Minecraft.getMinecraft().renderEngine.bindTexture(skinTexture);
            model.renderAll();
            GL11.glPopMatrix();
        }
    }
}
