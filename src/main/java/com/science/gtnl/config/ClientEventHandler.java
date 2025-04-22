package com.science.gtnl.config;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;

public class ClientEventHandler {

    @SubscribeEvent
    public void onClientDisconnect(ClientDisconnectionFromServerEvent event) {
        // reload the config from disk (undoing the server push)
        MainConfig.reloadConfig();
    }
}
