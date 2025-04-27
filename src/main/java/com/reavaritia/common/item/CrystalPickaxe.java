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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

import com.reavaritia.ReAvaItemList;
import com.reavaritia.ReCreativeTabs;
import com.reavaritia.TextLocalization;
import com.reavaritia.common.SubtitleDisplay;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CrystalPickaxe extends ItemPickaxe implements SubtitleDisplay {

    public static final ToolMaterial CRYSTAL = EnumHelper.addToolMaterial("CRYSTAL", 32, 8888, 9999F, 8.0F, 22);

    public CrystalPickaxe() {
        super(CRYSTAL);
        this.setUnlocalizedName("CrystalPickaxe");
        setCreativeTab(CreativeTabs.tabTools);
        this.setCreativeTab(ReCreativeTabs.ReAvaritia);
        this.setTextureName(RESOURCE_ROOT_ID + ":" + "CrystalPickaxe");
        this.setMaxDamage(8888);
        ReAvaItemList.CrystalPickaxe.set(new ItemStack(this, 1));
    }

    @Override
    public boolean hasEffect(ItemStack stack, int pass) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemStack, final EntityPlayer player, final List<String> toolTip,
        final boolean advancedToolTips) {
        toolTip.add(TextLocalization.Tooltip_CrystalPickaxe_00);
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        super.onCreated(stack, world, player);
        stack.addEnchantment(Enchantment.fortune, 3);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (player.isSneaking()) {
            Map<Integer, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
            String messageKey;

            if (enchantments.containsKey(Enchantment.fortune.effectId)) {
                enchantments.remove(Enchantment.fortune.effectId);
                enchantments.put(Enchantment.silkTouch.effectId, 1);
                messageKey = TextLocalization.Tooltip_CrystalPickaxe_Enchant_2;
            } else {
                enchantments.remove(Enchantment.silkTouch.effectId);
                enchantments.put(Enchantment.fortune.effectId, 3);
                messageKey = TextLocalization.Tooltip_CrystalPickaxe_Enchant_1;
            }

            EnchantmentHelper.setEnchantments(enchantments, stack);

            if (world.isRemote) {
                showSubtitle(messageKey);
            }
            return stack;
        }
        return super.onItemRightClick(stack, world, player);
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
