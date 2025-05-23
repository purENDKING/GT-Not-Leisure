package com.science.gtnl.Utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;

import com.science.gtnl.Mods;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.client.ElectricJukeboxSound;

@SideOnly(Side.CLIENT)
public class ClientUtils {

    public static String haloNoiseIconTexture = Mods.ScienceNotLeisure.resourceDomain + ":halonoise";
    public static IIcon haloNoiseIcon;

    public static void registerAllIcons(net.minecraft.client.renderer.texture.IIconRegister ir) {
        haloNoiseIcon = ir.registerIcon(haloNoiseIconTexture);
    }

    public static final Map<String, ElectricJukeboxSound> PLAYING_SOUNDS = new ConcurrentHashMap<>();

    @SubscribeEvent
    public void onClientTickEvent(TickEvent.ClientTickEvent aEvent) {
        if (aEvent.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayer player = mc.thePlayer;

            if (player != null && mc.theWorld != null && !PLAYING_SOUNDS.isEmpty()) {
                double playerX = player.posX;
                double playerY = player.posY;
                double playerZ = player.posZ;

                for (Map.Entry<String, ElectricJukeboxSound> entry : PLAYING_SOUNDS.entrySet()) {
                    ElectricJukeboxSound sound = entry.getValue();
                    sound.xPosition = (float) playerX;
                    sound.yPosition = (float) playerY;
                    sound.zPosition = (float) playerZ;
                }
            }
        }
    }

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre event) {
        if (event.map.getTextureType() == 1) {
            registerAllIcons(event.map);
        }
    }

    @SubscribeEvent
    public void onClientConnectedToServerEvent(FMLNetworkEvent.ClientConnectedToServerEvent aEvent) {
        PLAYING_SOUNDS.clear();
    }
}
