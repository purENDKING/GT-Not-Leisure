package com.science.gtnl.common.block.blocks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PlayerDollRenderer extends TileEntitySpecialRenderer {

    private static final ResourceLocation DEFAULT_SKIN = new ResourceLocation("sciencenotleisure:model/skin.png");
    private static final ResourceLocation DEFAULT_CAPE = new ResourceLocation("sciencenotleisure:model/cape.png");

    private ResourceLocation skinTexture = DEFAULT_SKIN;
    private ResourceLocation capeTexture = DEFAULT_CAPE;
    public static final ModelPlayerDoll model = new ModelPlayerDoll();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        if (tileEntity instanceof PlayerDollTileEntity playerDollTileEntity) {
            String playerName = playerDollTileEntity.getSkullOwner();

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
                capeTexture = player.getLocationCape();

                // 检查是否有自定义的皮肤和披风
                if (skinTexture == null) {
                    skinTexture = DEFAULT_SKIN;
                }
                if (capeTexture == null) {
                    capeTexture = DEFAULT_CAPE;
                }
            }

            // 渲染逻辑
            GL11.glPushMatrix();
            GL11.glTranslated(x + 0.5, y, z + 0.5); // 将模型中心移动到方块中心

            // 绑定皮肤和披风纹理
            Minecraft.getMinecraft().renderEngine.bindTexture(skinTexture);
            Minecraft.getMinecraft().renderEngine.bindTexture(capeTexture);

            // 渲染模型
            model.renderAll();

            GL11.glPopMatrix();
        }
    }
}
