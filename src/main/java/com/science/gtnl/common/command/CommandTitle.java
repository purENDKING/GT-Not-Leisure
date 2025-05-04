package com.science.gtnl.common.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

import com.science.gtnl.Utils.message.TitlePacket;

public class CommandTitle extends CommandBase {

    @Override
    public String getCommandName() {
        return "title";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/title <player> <title|reset> <message> [durationTicks] [color] [scale] [fadeIn] [fadeOut]";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.addChatMessage(
                new ChatComponentText(
                    "Usage: /title <player> <title|reset> <message> [durationTicks] [color] [scale] [fadeIn] [fadeOut]"));
            return;
        }

        String playerName = args[0];
        String action = args[1];

        EntityPlayerMP player = MinecraftServer.getServer()
            .getConfigurationManager()
            .func_152612_a(playerName);
        if (player == null) {
            sender.addChatMessage(new ChatComponentText("Player not found: " + playerName));
            return;
        }

        if ("reset".equalsIgnoreCase(action)) {
            TitlePacket.sendTitleToPlayerName(playerName, "", 0, 0xFFFFFF, 1, 0, 0);
            return;
        }

        if (!"title".equalsIgnoreCase(action)) {
            sender.addChatMessage(new ChatComponentText("Invalid action: " + action + ". Use 'title' or 'reset'."));
            return;
        }

        if (args.length < 3) {
            sender.addChatMessage(new ChatComponentText("Missing message argument."));
            return;
        }

        String message = args[2];
        int durationTicks = 600;
        int colorText = 0xFFFFFF;
        double scaleText = 3;
        int fadeIn = 10;
        int fadeOut = 20;

        try {
            if (args.length >= 4) durationTicks = Integer.parseInt(args[3]);
            if (args.length >= 5) colorText = Integer.parseInt(args[4], 16);
            if (args.length >= 6) scaleText = Double.parseDouble(args[5]);
            if (args.length >= 7) fadeIn = Integer.parseInt(args[6]);
            if (args.length >= 8) fadeOut = Integer.parseInt(args[7]);
        } catch (NumberFormatException e) {
            sender.addChatMessage(new ChatComponentText("Invalid number format: " + e.getMessage()));
            return;
        }

        TitlePacket.sendTitleToPlayerName(playerName, message, durationTicks, colorText, scaleText, fadeIn, fadeOut);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> names = new ArrayList<>();
            for (EntityPlayerMP obj : MinecraftServer.getServer()
                .getConfigurationManager().playerEntityList) {
                names.add(obj.getCommandSenderName());
            }
            return names;
        }
        if (args.length == 2) {
            return getListOfStringsMatchingLastWord(args, "title", "reset");
        }
        return null;
    }
}
