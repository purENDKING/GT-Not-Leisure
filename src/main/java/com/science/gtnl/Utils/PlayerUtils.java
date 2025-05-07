package com.science.gtnl.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.GameRules;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;

import com.reavaritia.common.render.CustomEntityRenderer;
import com.science.gtnl.Mods;
import com.science.gtnl.Utils.message.TitlePacket;
import com.science.gtnl.api.TickrateAPI;
import com.science.gtnl.asm.GTNLEarlyCoreMod;
import com.science.gtnl.common.command.CommandTickrate;
import com.science.gtnl.common.item.TimeStopManager;
import com.science.gtnl.config.MainConfig;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PlayerUtils {

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayerMP player = (EntityPlayerMP) event.player;
        TimeStopManager.setTimeStopped(false);

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

        if (FMLCommonHandler.instance()
            .getEffectiveSide() == Side.SERVER) {
            float tickrate = MainConfig.defaultTickrate;
            try {
                GameRules rules = MinecraftServer.getServer()
                    .getEntityWorld()
                    .getGameRules();
                if (rules.hasRule(GTNLEarlyCoreMod.GAME_RULE)) {
                    tickrate = Float.parseFloat(rules.getGameRuleStringValue(GTNLEarlyCoreMod.GAME_RULE));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            TickrateAPI.changeClientTickrate(event.player, tickrate);
        }
    }

    @SubscribeEvent
    public void onPlayerLoginOut(PlayerEvent.PlayerLoggedOutEvent event) {
        TimeStopManager.setTimeStopped(false);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (event.world.isRemote) {
            EntityRenderer renderer = Minecraft.getMinecraft().entityRenderer;
            if (renderer instanceof CustomEntityRenderer customEntityRenderer) {
                customEntityRenderer.resetShader();
            }
        }
        TimeStopManager.setTimeStopped(false);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        if (event.world.isRemote) {
            EntityRenderer renderer = Minecraft.getMinecraft().entityRenderer;
            if (renderer instanceof CustomEntityRenderer customEntityRenderer) {
                customEntityRenderer.resetShader();
            }
        }
        TimeStopManager.setTimeStopped(false);
    }

    @SubscribeEvent
    public void connect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if (event.isLocal) {
            float tickrate = MainConfig.defaultTickrate;
            try {
                GameRules rules = MinecraftServer.getServer()
                    .getEntityWorld()
                    .getGameRules();
                if (rules.hasRule(GTNLEarlyCoreMod.GAME_RULE)) {
                    tickrate = Float.parseFloat(rules.getGameRuleStringValue(GTNLEarlyCoreMod.GAME_RULE));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            TickrateAPI.changeServerTickrate(tickrate);
            TickrateAPI.changeClientTickrate(null, tickrate);
        } else {
            TickrateAPI.changeClientTickrate(null, 20F);
        }
    }

    @SubscribeEvent
    public void disconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        TickrateAPI.changeServerTickrate(MainConfig.defaultTickrate);
        TickrateAPI.changeClientTickrate(null, MainConfig.defaultTickrate);
    }

    @SubscribeEvent
    public void chat(ClientChatReceivedEvent event) {
        if (event.message instanceof ChatComponentTranslation) {
            ChatComponentTranslation t = (ChatComponentTranslation) event.message;
            if (t.getKey()
                .equals("GTNLEarlyCoreMod.show.clientside")) {
                event.message = new ChatComponentText("");
                event.message.appendSibling(CommandTickrate.c("Your Current Client Tickrate: ", 'f', 'l'));
                event.message
                    .appendSibling(CommandTickrate.c(TickrateAPI.getClientTickrate() + " ticks per second", 'a'));
            }
        }
    }
}
