package com.science.gtnl.common.item.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.client.GTNLCreativeTabs;
import com.science.gtnl.common.GTNLItemList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TestItem extends Item {

    public TestItem() {
        super();
        this.setUnlocalizedName("TestItem");
        this.setCreativeTab(GTNLCreativeTabs.GTNotLeisureItem);
        this.setTextureName("sciencenotleisure:TestItem");
        GTNLItemList.TestItem.set(new ItemStack(this, 1));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemStack, final EntityPlayer player, final List toolTip,
        final boolean advancedToolTips) {

        toolTip.add(TextLocalization.Tooltip_Testitem_00);
        toolTip.add(TextLocalization.Tooltip_Testitem_01);
        toolTip.add(TextLocalization.Tooltip_Testitem_02);
        toolTip.add(TextLocalization.Tooltip_Testitem_03);
        toolTip.add(TextLocalization.Tooltip_Testitem_04);
        toolTip.add(TextLocalization.Tooltip_Testitem_05);
        toolTip.add(TextLocalization.Tooltip_Testitem_06);
        toolTip.add(TextLocalization.Tooltip_Testitem_07);
        toolTip.add(TextLocalization.Tooltip_Testitem_08);
        toolTip.add(TextLocalization.Tooltip_Testitem_09);
        toolTip.add(TextLocalization.Tooltip_Testitem_10);
        toolTip.add(TextLocalization.Tooltip_Testitem_11);
        toolTip.add(TextLocalization.Tooltip_Testitem_12);
        toolTip.add(TextLocalization.Tooltip_Testitem_13);

    }

    public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        if (!worldIn.isRemote) {
            playerIn.addPotionEffect(new PotionEffect(186, 6000, 1));
        }

        stack.splitStack(1);
        return stack;
    }

}
