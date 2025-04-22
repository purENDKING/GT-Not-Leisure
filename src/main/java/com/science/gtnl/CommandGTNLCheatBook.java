package com.science.gtnl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cpw.mods.fml.common.registry.GameData;

public class CommandGTNLCheatBook extends CommandBase {

    @Override
    public String getCommandName() {
        return "gtnl_cheatbook";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/gtnl_cheatbook [playerName]";
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.emptyList();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        String targetName;
        EntityPlayerMP targetPlayer = null;
        if (args.length >= 1 && args[0].length() > 0) {
            targetName = args[0];
        } else if (sender instanceof EntityPlayerMP) {
            targetPlayer = (EntityPlayerMP) sender;
            targetName = sender.getCommandSenderName();
        } else {
            sender.addChatMessage(new ChatComponentText("Console must specify a player name."));
            return;
        }

        World world = (targetPlayer != null) ? targetPlayer.getEntityWorld()
            : MinecraftServer.getServer().worldServers[0];
        File gtDir = new File(
            world.getSaveHandler()
                .getWorldDirectory(),
            "GTNL");
        File usesFile = new File(gtDir, "uses.xml");
        File itemsFile = new File(gtDir, "items.xml");

        if (!usesFile.exists() || !itemsFile.exists()) {
            sender.addChatMessage(new ChatComponentText("GTNL data not found."));
            return;
        }

        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();

            Document docUses = db.parse(usesFile);
            Element rootUses = docUses.getDocumentElement();
            int totalUses = 0;
            for (int i = 0; i < rootUses.getElementsByTagName("player")
                .getLength(); i++) {
                Element el = (Element) rootUses.getElementsByTagName("player")
                    .item(i);
                if (el.getAttribute("name")
                    .equals(targetName)) {
                    totalUses = Integer.parseInt(el.getAttribute("uses"));
                    break;
                }
            }

            Document docItems = db.parse(itemsFile);
            Element rootItems = docItems.getDocumentElement();
            Element targetEl = null;
            for (int i = 0; i < rootItems.getElementsByTagName("target")
                .getLength(); i++) {
                Element el = (Element) rootItems.getElementsByTagName("target")
                    .item(i);
                if (el.getAttribute("name")
                    .equals(targetName)) {
                    targetEl = el;
                    break;
                }
            }

            if (targetEl == null) {
                sender.addChatMessage(new ChatComponentText("No item data for player: " + targetName));
                return;
            }

            ItemStack book = new ItemStack(Items.written_book);
            NBTTagCompound bookTag = new NBTTagCompound();
            bookTag.setString("title", "GTNL Cheatbook - " + targetName);
            bookTag.setString("author", "GTNL Tracker");

            NBTTagList pages = new NBTTagList();
            StringBuilder page = new StringBuilder();
            page.append("Player: ")
                .append(targetName)
                .append("\n");
            page.append("Total /give: ")
                .append(totalUses)
                .append("\n");

            for (int i = 0; i < targetEl.getElementsByTagName("item")
                .getLength(); i++) {
                Element itemEl = (Element) targetEl.getElementsByTagName("item")
                    .item(i);
                String regWithMeta = itemEl.getAttribute("name");
                int count = Integer.parseInt(itemEl.getAttribute("count"));

                String[] parts = regWithMeta.split(":");
                if (parts.length < 2) continue;

                String regName = parts[0] + ":" + parts[1];
                int meta = 0;
                if (parts.length >= 3) {
                    try {
                        meta = Integer.parseInt(parts[2]);
                    } catch (NumberFormatException ignored) {}
                }

                Item item = GameData.getItemRegistry()
                    .getObject(regName);
                if (item == null) continue;

                ItemStack stack = new ItemStack(item, 1, meta);
                String unlocName = stack.getUnlocalizedName();
                if (unlocName == null) continue;

                String localized = StatCollector.translateToLocal(unlocName + ".name");
                String line = localized + ": " + count + "\n";

                if (page.length() + line.length() > 240) {
                    pages.appendTag(new NBTTagString(page.toString()));
                    page = new StringBuilder();
                }
                page.append(line);
            }

            if (page.length() > 0) {
                pages.appendTag(new NBTTagString(page.toString()));
            }
            bookTag.setTag("pages", pages);
            book.setTagCompound(bookTag);

            if (sender instanceof EntityPlayerMP) {
                ((EntityPlayerMP) sender).inventory.addItemStackToInventory(book);
                sender.addChatMessage(new ChatComponentText("Cheatbook given to " + sender.getCommandSenderName()));
            } else {
                sender.addChatMessage(new ChatComponentText("Cannot give book to console."));
            }

        } catch (Exception e) {
            e.printStackTrace();
            sender.addChatMessage(new ChatComponentText("Error reading GTNL data."));
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
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
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return index == 0;
    }

    @Override
    public int compareTo(Object o) {
        return this.getCommandName()
            .compareTo(((ICommand) o).getCommandName());
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
}
