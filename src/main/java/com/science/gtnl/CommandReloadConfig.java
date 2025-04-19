package com.science.gtnl;

import static net.minecraft.util.EnumChatFormatting.*;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;

import com.science.gtnl.Events.ConfigReloadedEvent;
import com.science.gtnl.config.Config;
import com.science.gtnl.config.MainConfig;

public class CommandReloadConfig extends CommandBase {

    @Override
    public String getCommandName() {
        return "gtnl_reloadconfig"; // 命令名称
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/gtnl_reloadconfig - 重新加载 gtnl 的配置文件";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        try {
            Config.reloadConfig();

            // 触发配置重载事件
            MinecraftForge.EVENT_BUS.post(new ConfigReloadedEvent());

            sender.addChatMessage(new ChatComponentText("配置文件已成功重新加载！"));

            if (MainConfig.enableDeleteRecipe) {
                sender.addChatMessage(
                    new ChatComponentText(YELLOW + StatCollector.translateToLocal("Welcome_GTNL_DeleteRecipe")));
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
        } catch (Exception e) {
            sender.addChatMessage(new ChatComponentText("配置文件重载失败！错误信息: " + e.getMessage()));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2; // 仅限管理员使用
    }
}
