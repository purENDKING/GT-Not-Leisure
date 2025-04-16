package com.science.gtnl.Utils;

import static net.minecraft.util.EnumChatFormatting.*;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;

import com.science.gtnl.config.MainConfig;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class LoginMessage {

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        event.player.addChatMessage(new ChatComponentText(BOLD + StatCollector.translateToLocal("Welcome_GTNL_00")));
        event.player.addChatMessage(new ChatComponentText(GREEN + StatCollector.translateToLocal("Welcome_GTNL_01")));
        event.player.addChatMessage(new ChatComponentText(GREEN + StatCollector.translateToLocal("Welcome_GTNL_02")));

        if (MainConfig.enableDeleteRecipe) {
            event.player.addChatMessage(
                new ChatComponentText(YELLOW + StatCollector.translateToLocal("Welcome_GTNL_DeleteRecipe")));
        }

        if (MainConfig.enableDebugMode) {
            event.player
                .addChatMessage(new ChatComponentText(RED + StatCollector.translateToLocal("Welcome_GTNL_Debug")));
        }
    }
}
