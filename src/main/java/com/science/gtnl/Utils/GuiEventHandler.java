package com.science.gtnl.Utils;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;

import com.science.gtnl.common.effect.effects.AweEffect;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiEventHandler {

    public GuiEventHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static final Random random = new Random();

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.thePlayer;

        if (player != null && !player.capabilities.isCreativeMode) {
            PotionEffect effect = player.getActivePotionEffect(AweEffect.instance);

            if (effect != null && event.gui instanceof GuiInventory) {
                event.setCanceled(true);
                String[] messages = { "Awe_Cancel_02_01", "Awe_Cancel_02_02" };
                String message = messages[random.nextInt(messages.length)];
                IChatComponent chatComponent = new ChatComponentTranslation(message);
                player.addChatMessage(chatComponent);

            }
        }
    }
}
