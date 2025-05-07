package com.science.gtnl.config;

import static com.science.gtnl.ScienceNotLeisure.network;

import net.minecraft.entity.player.EntityPlayerMP;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class ServerEventHandler {

    @SubscribeEvent
    public void onPlayerLogin(PlayerLoggedInEvent event) {
        if (event.player instanceof EntityPlayerMP) {
            // construct message from current server config
            ConfigSyncMessage msg = new ConfigSyncMessage(new MainConfig());// or pass static values
            network.sendTo(msg, (EntityPlayerMP) event.player);
        }
    }
}
