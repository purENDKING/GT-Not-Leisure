package com.reavaritia.common.item;

import static com.reavaritia.ReAvaritia.RESOURCE_ROOT_ID;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

import com.reavaritia.ReAvaCreativeTabs;
import com.reavaritia.ReAvaritia;
import com.reavaritia.common.SubtitleDisplay;
import com.reavaritia.common.entity.EntityChronarchPoint;
import com.science.gtnl.config.MainConfig;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ChronarchsClock extends Item implements SubtitleDisplay {

    private static final int RADIUS = 6;
    private static final int SPEED_MULTIPLIER = 100;
    private static final int DURATION_TICKS = 200;

    public ChronarchsClock() {
        this.setUnlocalizedName("ChronarchsClock");
        this.setCreativeTab(ReAvaCreativeTabs.ReAvaritia);
        this.setTextureName(RESOURCE_ROOT_ID + ":" + "ChronarchsClock");
        this.setMaxStackSize(1);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumHelper.addRarity("COSMIC", EnumChatFormatting.RED, "Cosmic");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean advanced) {
        list.add(StatCollector.translateToLocal("Tooltip_ChronarchsClock_00"));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null) {
            nbt = new NBTTagCompound();
            stack.setTagCompound(nbt);
        }
        long lastUsed = nbt.getLong("LastUsed");

        if (world.getTotalWorldTime() - lastUsed < MainConfig.ChronarchsClockCooldown) {
            showSubtitle("Tooltip_ChronarchsClock_01");
            return stack;
        }

        if (!world.isRemote) {
            EntityChronarchPoint point = new EntityChronarchPoint(
                world,
                player.posX,
                player.posY,
                player.posZ,
                RADIUS,
                SPEED_MULTIPLIER,
                DURATION_TICKS);
            world.spawnEntityInWorld(point);
        }

        nbt.setLong("LastUsed", world.getTotalWorldTime());
        return stack;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void showSubtitle(String messageKey) {
        IChatComponent component = new ChatComponentTranslation(messageKey);
        component.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.WHITE));
        Minecraft.getMinecraft().ingameGUI.func_110326_a(component.getFormattedText(), true);
    }

    public static void registerEntity() {
        EntityRegistry
            .registerModEntity(EntityChronarchPoint.class, "ChronarchPoint", 1, ReAvaritia.instance, 64, 20, true);

    }
}
