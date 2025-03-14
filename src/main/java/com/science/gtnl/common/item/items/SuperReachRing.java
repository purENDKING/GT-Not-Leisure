package com.science.gtnl.common.item.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import com.science.gtnl.client.GTNLCreativeTabs;

import baubles.api.BaubleType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.common.Botania;
import vazkii.botania.common.item.equipment.bauble.ItemBauble;

public class SuperReachRing extends ItemBauble {

    public SuperReachRing() {
        super(SUPER_REACH_RING);
        this.setUnlocalizedName("SuperReachRing");
        this.setTextureName("sciencenotleisure:SuperReachRing");
        this.setCreativeTab(GTNLCreativeTabs.GTNotLeisureItem);
    }

    @Override
    public void onEquippedOrLoadedIntoWorld(ItemStack stack, EntityLivingBase player) {
        Botania.proxy.setExtraReach(player, 100F);
    }

    @Override
    public void onUnequipped(ItemStack stack, EntityLivingBase player) {
        Botania.proxy.setExtraReach(player, -100F);
    }

    @Override
    public BaubleType getBaubleType(ItemStack arg0) {
        return BaubleType.RING;
    }

    public static final String SUPER_REACH_RING = "SuperReachRing";

    @Override
    public String getUnlocalizedNameInefficiently(ItemStack p_77657_1_) {
        String s = this.getUnlocalizedName(p_77657_1_);
        return s == null ? "" : StatCollector.translateToLocal(s);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon(this.getIconString());
    }

}
