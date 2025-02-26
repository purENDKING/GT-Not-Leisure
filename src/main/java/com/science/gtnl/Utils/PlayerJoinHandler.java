package com.science.gtnl.Utils;

import net.minecraft.entity.player.EntityPlayerMP;

import com.science.gtnl.ScienceNotLeisure;
import com.science.gtnl.Utils.recipes.SyncRecipesPacket;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class PlayerJoinHandler {

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
        if (event.player instanceof EntityPlayerMP) {
            ScienceNotLeisure.network.sendTo(new SyncRecipesPacket(), (EntityPlayerMP) event.player);
        }
    }
}
