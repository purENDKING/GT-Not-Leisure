package com.science.gtnl.Utils.message;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import com.science.gtnl.Mods;
import com.science.gtnl.config.MainConfig;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class LoginMessage {

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayerMP player = (EntityPlayerMP) event.player;

        player.addChatMessage(
            new ChatComponentTranslation("Welcome_GTNL_00")
                .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.BOLD)));
        player.addChatMessage(
            new ChatComponentTranslation("Welcome_GTNL_01")
                .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)));
        player.addChatMessage(
            new ChatComponentTranslation("Welcome_GTNL_02")
                .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)));

        if (MainConfig.enableDeleteRecipe) {
            player.addChatMessage(
                new ChatComponentTranslation("Welcome_GTNL_DeleteRecipe")
                    .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
        }

        if (MainConfig.enableDebugMode) {
            player.addChatMessage(
                new ChatComponentTranslation("Welcome_GTNL_Debug")
                    .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
        }

        if (!Mods.Overpowered.isModLoaded() && MainConfig.enableRecipeOutputChance) {
            player.addChatMessage(
                new ChatComponentTranslation("Welcome_GTNL_RecipeOutputChance_00")
                    .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD)));
            player.addChatMessage(
                new ChatComponentTranslation("Welcome_GTNL_RecipeOutputChance_01", MainConfig.recipeOutputChance + "%")
                    .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD)));
        }

        TitlePacket.sendTitleToPlayer(player, "Welcome_GTNL_DeleteRecipe", 200, 0xFFFF55, 2);
    }
}
