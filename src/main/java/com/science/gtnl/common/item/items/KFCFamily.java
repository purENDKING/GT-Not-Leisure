package com.science.gtnl.common.item.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.client.GTNLCreativeTabs;
import com.science.gtnl.common.GTNLItemList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class KFCFamily extends ItemFood {

    public KFCFamily(int hunger, float saturation, boolean isWolfFood) {
        super(hunger, saturation, isWolfFood);
        this.setUnlocalizedName("KFCFamily");
        this.setTextureName("sciencenotleisure:KFCFamily");
        this.setCreativeTab(GTNLCreativeTabs.GTNotLeisureItem);
        this.setAlwaysEdible();
        GTNLItemList.KFCFamily.set(new ItemStack(this, 1));
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        super.onFoodEaten(stack, world, player);
        if (!world.isRemote) {
            player.addPotionEffect(new PotionEffect(187, 86400 * 20, 1));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemStack, final EntityPlayer player, final List<String> toolTip,
        final boolean advancedToolTips) {
        toolTip.add(TextLocalization.Tooltip_KFCFamily_00);
    }
}
