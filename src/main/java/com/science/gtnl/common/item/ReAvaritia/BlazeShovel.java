package com.science.gtnl.common.item.ReAvaritia;

import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.world.BlockEvent;

import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.Utils.loadingUtils.SubtitleDisplay;
import com.science.gtnl.client.GTNLCreativeTabs;
import com.science.gtnl.common.GTNLItemList;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlazeShovel extends ItemSpade implements SubtitleDisplay {

    public static final ToolMaterial BLAZE = EnumHelper.addToolMaterial("BLAZE", 3, 7777, 9999F, 10F, 10);

    public BlazeShovel() {
        super(BLAZE);
        this.setUnlocalizedName("BlazeShovel");
        setCreativeTab(CreativeTabs.tabTools);
        this.setCreativeTab(GTNLCreativeTabs.ReAvaritia);
        this.setTextureName("reavaritia:BlazeShovel");
        this.setMaxDamage(7777);
        MinecraftForge.EVENT_BUS.register(this);
        GTNLItemList.BlazeShovel.set(new ItemStack(this, 1));
    }

    @Override
    public boolean hasEffect(ItemStack stack, int pass) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemStack, final EntityPlayer player, final List toolTip,
        final boolean advancedToolTips) {
        toolTip.add(TextLocalization.Tooltip_BlazeShovel_00);
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        super.onCreated(stack, world, player);
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null) {
            nbt = new NBTTagCompound();
            stack.setTagCompound(nbt);
        }
        nbt.setBoolean("SmeltingMode", false);
        updateEnchantments(stack);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (player.isSneaking()) {
            toggleSmeltingMode(stack);
            String messageKey = isSmeltingModeActive(stack) ? TextLocalization.Tooltip_Blaze_Smelt_On
                : TextLocalization.Tooltip_Blaze_Smelt_Off;
            if (world.isRemote) {
                showSubtitle(messageKey);
            }
        }
        return stack;
    }

    private void toggleSmeltingMode(ItemStack stack) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null) {
            nbt = new NBTTagCompound();
            stack.setTagCompound(nbt);
        }
        boolean currentMode = nbt.getBoolean("SmeltingMode");
        nbt.setBoolean("SmeltingMode", !currentMode);
        updateEnchantments(stack);
    }

    private void updateEnchantments(ItemStack stack) {
        Map<Integer, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
        enchantments.put(Enchantment.fireAspect.effectId, 10);
        EnchantmentHelper.setEnchantments(enchantments, stack);
    }

    private boolean isSmeltingModeActive(ItemStack stack) {
        NBTTagCompound nbt = stack.getTagCompound();
        return nbt != null && nbt.getBoolean("SmeltingMode");
    }

    @SubscribeEvent
    public void onBlockHarvest(BlockEvent.HarvestDropsEvent event) {
        if (event.harvester == null || event.harvester.getCurrentEquippedItem() == null) return;

        ItemStack heldItem = event.harvester.getCurrentEquippedItem();
        if (!(heldItem.getItem() instanceof BlazeShovel)) return;

        Block block = event.block;
        List<ItemStack> drops = event.drops;

        boolean transformed = false;
        for (int i = 0; i < drops.size(); i++) {
            ItemStack drop = drops.get(i);
            if (drop.getItem() == Item.getItemFromBlock(Blocks.dirt)) {
                drops.set(i, new ItemStack(Blocks.netherrack, drop.stackSize));
                transformed = true;
            } else if (drop.getItem() == Item.getItemFromBlock(Blocks.sand)) {
                drops.set(i, new ItemStack(Blocks.soul_sand, drop.stackSize));
                transformed = true;
            } else if (drop.getItem() == Item.getItemFromBlock(Blocks.gravel)) {
                drops.set(i, new ItemStack(Blocks.netherrack, drop.stackSize));
                transformed = true;
            }
        }

        if (!transformed) {
            boolean smeltingActive = isSmeltingModeActive(heldItem);
            if (smeltingActive) {
                int meta = event.blockMetadata;
                ItemStack blockStack = new ItemStack(block, 1, meta);
                ItemStack smeltResult = FurnaceRecipes.smelting()
                    .getSmeltingResult(blockStack);
                if (smeltResult != null) {
                    int totalCount = 0;
                    for (ItemStack drop : drops) {
                        totalCount += drop.stackSize;
                    }

                    event.drops.clear();
                    ItemStack result = smeltResult.copy();
                    result.stackSize = totalCount;
                    event.drops.add(result);
                }
            }
        }

        heldItem.damageItem(1, event.harvester);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void showSubtitle(String messageKey) {
        String localized = StatCollector.translateToLocal(messageKey);
        ChatComponentText text = new ChatComponentText(EnumChatFormatting.WHITE + localized);
        Minecraft.getMinecraft().ingameGUI.func_110326_a(text.getFormattedText(), true);
    }
}
