package com.science.gtnl.config;

import net.minecraft.entity.player.EntityPlayerMP;

import com.science.gtnl.ScienceNotLeisure;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class ServerEventHandler {

    private final SimpleNetworkWrapper net = ScienceNotLeisure.network;

    @SubscribeEvent
    public void onPlayerLogin(PlayerLoggedInEvent event) {
        if (event.player instanceof EntityPlayerMP) {
            // construct message from current server config
            ConfigSyncMessage msg = new ConfigSyncMessage(new MainConfig());// or pass static values
            net.sendTo(msg, (EntityPlayerMP) event.player);
        }
    }
}
