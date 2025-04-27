package com.reavaritia.common.item;

import static com.reavaritia.ReAvaritia.RESOURCE_ROOT_ID;

import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
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

import com.reavaritia.ReAvaItemList;
import com.reavaritia.ReCreativeTabs;
import com.reavaritia.TextLocalization;
import com.reavaritia.common.SubtitleDisplay;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlazePickaxe extends ItemPickaxe implements SubtitleDisplay {

    public static final ToolMaterial BLAZE = EnumHelper.addToolMaterial("BLAZE", 32, 7777, 9999F, 9.0F, 22);

    public BlazePickaxe() {
        super(BLAZE);
        this.setUnlocalizedName("BlazePickaxe");
        setCreativeTab(CreativeTabs.tabTools);
        this.setCreativeTab(ReCreativeTabs.ReAvaritia);
        this.setTextureName(RESOURCE_ROOT_ID + ":" + "BlazePickaxe");
        this.setMaxDamage(7777);
        MinecraftForge.EVENT_BUS.register(this);
        ReAvaItemList.BlazePickaxe.set(new ItemStack(this, 1));
    }

    @Override
    public boolean hasEffect(ItemStack stack, int pass) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemStack, final EntityPlayer player, final List<String> toolTip,
        final boolean advancedToolTips) {
        toolTip.add(TextLocalization.Tooltip_BlazePickaxe_00);
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
        if (!(heldItem.getItem() instanceof BlazePickaxe)) return;

        boolean smeltingActive = isSmeltingModeActive(heldItem);
        if (!smeltingActive) return;

        Block block = event.block;
        int meta = event.blockMetadata;
        ItemStack blockStack = new ItemStack(block, 1, meta);
        ItemStack smeltResult = FurnaceRecipes.smelting()
            .getSmeltingResult(blockStack);
        if (smeltResult == null) return;

        int totalCount = 0;
        for (ItemStack drop : event.drops) {
            totalCount += drop.stackSize;
        }

        event.drops.clear();
        ItemStack result = smeltResult.copy();
        result.stackSize = totalCount;
        event.drops.add(result);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void showSubtitle(String messageKey) {
        String localized = StatCollector.translateToLocal(messageKey);
        ChatComponentText text = new ChatComponentText(EnumChatFormatting.WHITE + localized);
        Minecraft.getMinecraft().ingameGUI.func_110326_a(text.getFormattedText(), true);
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        return Float.MAX_VALUE;
    }

    @Override
    public boolean canHarvestBlock(Block block, ItemStack stack) {
        return true;
    }

    @Override
    public int getItemEnchantability() {
        return 1;
    }
}
