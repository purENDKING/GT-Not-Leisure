package com.science.gtnl.mixins.early;

import static codechicken.nei.NEIServerUtils.sendNotice;
import static codechicken.nei.NEIServerUtils.setColour;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import codechicken.lib.inventory.InventoryUtils;
import codechicken.nei.NEIServerUtils;
import cpw.mods.fml.common.registry.GameData;

@SuppressWarnings("UnusedMixin")
@Mixin(value = NEIServerUtils.class, remap = false)
public abstract class NEIServerUtils_Mixin {

    /**
     * @reason Overwrites the original NEIServerUtils#givePlayerItem method.
     *         This overwrite allows tracking of items given via NEI by recording
     *         both the executor and the received item count to XML files for later analysis.
     *         The tracking system mirrors the structure used in CommandGive,
     *         ensuring consistency across both manual and NEI-based item grants.
     * @author GTNotLeisure
     */
    @Overwrite
    public static void givePlayerItem(EntityPlayerMP player, ItemStack stack, boolean infinite, boolean doGive) {
        if (stack.getItem() == null) {
            player.addChatComponentMessage(
                setColour(new ChatComponentTranslation("nei.chat.give.noitem"), EnumChatFormatting.WHITE));
            return;
        }

        int given = stack.stackSize;
        if (doGive) {
            if (infinite) player.inventory.addItemStackToInventory(stack);
            else given -= InventoryUtils.insertItem(player.inventory, stack, false);
        }

        sendNotice(
            player,
            new ChatComponentTranslation(
                "commands.give.success",
                stack.func_151000_E(),
                infinite ? "\u221E" : Integer.toString(given),
                player.getCommandSenderName()),
            "notify-item");
        player.openContainer.detectAndSendChanges();

        if (!doGive || stack.getItem() == null || given <= 0) return;
        recordGiveAction(player, stack, given);
    }

    private static void recordGiveAction(EntityPlayerMP player, ItemStack stack, int given) {
        String executor = player.getCommandSenderName();
        String displayName = resolveDisplayName(stack);
        if (displayName == null) return;

        World world = player.getEntityWorld();
        File worldDir = world.getSaveHandler()
            .getWorldDirectory();
        File gtDir = new File(worldDir, "GTNotLeisure");
        if (!gtDir.exists()) gtDir.mkdirs();

        File usesFile = new File(gtDir, "player_give_count.xml");
        File itemsFile = new File(gtDir, "player_give_item.xml");

        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();

            Document docUses = usesFile.exists() ? db.parse(usesFile) : db.newDocument();
            Element rootUses = docUses.getDocumentElement();
            if (rootUses == null) {
                rootUses = docUses.createElement("players");
                docUses.appendChild(rootUses);
            }

            Element playerEl = null;
            NodeList players = rootUses.getElementsByTagName("player");
            for (int i = 0; i < players.getLength(); i++) {
                Element el = (Element) players.item(i);
                if (el.getAttribute("name")
                    .equals(executor)) {
                    playerEl = el;
                    break;
                }
            }
            if (playerEl == null) {
                playerEl = docUses.createElement("player");
                playerEl.setAttribute("name", executor);
                playerEl.setAttribute("uses", "0");
                rootUses.appendChild(playerEl);
            }
            int prevUses = Integer.parseInt(playerEl.getAttribute("uses"));
            playerEl.setAttribute("uses", String.valueOf(prevUses + 1));
            Transformer t = TransformerFactory.newInstance()
                .newTransformer();
            t.transform(new DOMSource(docUses), new StreamResult(usesFile));

            Document docItems = itemsFile.exists() ? db.parse(itemsFile) : db.newDocument();
            Element rootItems = docItems.getDocumentElement();
            if (rootItems == null) {
                rootItems = docItems.createElement("targets");
                docItems.appendChild(rootItems);
            }

            Element targetEl = null;
            NodeList targets = rootItems.getElementsByTagName("target");
            for (int i = 0; i < targets.getLength(); i++) {
                Element el = (Element) targets.item(i);
                if (el.getAttribute("name")
                    .equals(executor)) {
                    targetEl = el;
                    break;
                }
            }
            if (targetEl == null) {
                targetEl = docItems.createElement("target");
                targetEl.setAttribute("name", executor);
                rootItems.appendChild(targetEl);
            }

            Element itemEl = null;
            NodeList items = targetEl.getElementsByTagName("item");
            for (int i = 0; i < items.getLength(); i++) {
                Element el = (Element) items.item(i);
                if (el.getAttribute("name")
                    .equals(displayName)) {
                    itemEl = el;
                    break;
                }
            }
            if (itemEl == null) {
                itemEl = docItems.createElement("item");
                itemEl.setAttribute("name", displayName);
                itemEl.setAttribute("count", "0");
                targetEl.appendChild(itemEl);
            }
            int prevCount = Integer.parseInt(itemEl.getAttribute("count"));
            itemEl.setAttribute("count", String.valueOf(prevCount + given));
            t.transform(new DOMSource(docItems), new StreamResult(itemsFile));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从 ItemStack 构造注册名:meta 的形式
     */
    private static String resolveDisplayName(ItemStack stack) {
        if (stack == null || stack.getItem() == null) return null;
        String regName = GameData.getItemRegistry()
            .getNameForObject(stack.getItem());
        if (regName == null) return null;
        return regName + ":" + stack.getItemDamage();
    }
}
