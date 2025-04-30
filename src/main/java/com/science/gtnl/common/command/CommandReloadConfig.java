package com.science.gtnl.common.command;

import static com.science.gtnl.Utils.Utils.repeatExclamation;
import static net.minecraft.util.EnumChatFormatting.*;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;

import com.science.gtnl.Mods;
import com.science.gtnl.ScienceNotLeisure;
import com.science.gtnl.config.Config;
import com.science.gtnl.config.ConfigSyncMessage;
import com.science.gtnl.config.MainConfig;

public class CommandReloadConfig extends CommandBase {

    @Override
    public String getCommandName() {
        return "gtnl_reloadconfig";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/gtnl_reloadconfig - reload GTNL config";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        try {
            Config.reloadConfig();

            ConfigSyncMessage msg = new ConfigSyncMessage(new MainConfig());
            for (Object obj : MinecraftServer.getServer()
                .getConfigurationManager().playerEntityList) {
                if (obj instanceof EntityPlayerMP) {
                    ScienceNotLeisure.network.sendTo(msg, (EntityPlayerMP) obj);
                }
            }

            if (MainConfig.enableDeleteRecipe) {
                for (int i = 1; i <= 5; i++) {
                    String message = StatCollector.translateToLocal("Welcome_GTNL_DeleteRecipe");
                    String exclamations = repeatExclamation(i);
                    sender.addChatMessage(new ChatComponentText(YELLOW + message + exclamations));
                }
            }

            if (MainConfig.enableDebugMode) {
                sender
                    .addChatMessage(new ChatComponentText(RED + StatCollector.translateToLocal("Welcome_GTNL_Debug")));
            }

            if (!Mods.Overpowered.isModLoaded() && MainConfig.enableRecipeOutputChance) {
                sender.addChatMessage(
                    new ChatComponentText(GOLD + StatCollector.translateToLocal("Welcome_GTNL_RecipeOutputChance_00")));
                sender.addChatMessage(
                    new ChatComponentText(
                        GOLD + StatCollector.translateToLocal("Welcome_GTNL_RecipeOutputChance_01")
                            + MainConfig.recipeOutputChance
                            + "%"));
            }

            sender.addChatMessage(new ChatComponentText(GREEN + StatCollector.translateToLocal("Welcome_GTNL_reload")));

        } catch (Exception e) {
            sender.addChatMessage(new ChatComponentText("Error Reload GTNL config: " + e.getMessage()));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
}
