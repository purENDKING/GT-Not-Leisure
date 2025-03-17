package com.science.gtnl.common.item.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.science.gtnl.client.GTNLCreativeTabs;
import com.science.gtnl.common.GTNLItemList;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import vazkii.botania.common.entity.EntityDoppleganger;

public class SatietyRing extends Item implements IBauble {

    public SatietyRing() {
        this.setMaxStackSize(1);
        this.setUnlocalizedName("SatietyRing");
        this.setTextureName("sciencenotleisure:SatietyRing");
        this.setCreativeTab(GTNLCreativeTabs.GTNotLeisureItem);
        GTNLItemList.SatietyRing.set(new ItemStack(this, 1));
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.RING;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        player.addPotionEffect(new PotionEffect(Potion.field_76443_y.id, 200, 255));
        player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 200, 255));

        if (player instanceof EntityPlayer entityPlayer) {
            entityPlayer.getFoodStats()
                .addStats(20, 20.0F);
        }
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        player.removePotionEffect(Potion.field_76443_y.id);
    }

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (!EntityDoppleganger.isTruePlayer(par3EntityPlayer)) return par1ItemStack;

        if (canEquip(par1ItemStack, par3EntityPlayer)) {
            InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(par3EntityPlayer);
            for (int i = 0; i < baubles.getSizeInventory(); i++) {
                if (baubles.isItemValidForSlot(i, par1ItemStack)) {
                    ItemStack stackInSlot = baubles.getStackInSlot(i);
                    if (stackInSlot == null
                        || ((IBauble) stackInSlot.getItem()).canUnequip(stackInSlot, par3EntityPlayer)) {
                        if (!par2World.isRemote) {
                            baubles.setInventorySlotContents(i, par1ItemStack.copy());
                            if (!par3EntityPlayer.capabilities.isCreativeMode) par3EntityPlayer.inventory
                                .setInventorySlotContents(par3EntityPlayer.inventory.currentItem, null);
                        }

                        if (stackInSlot != null) {
                            ((IBauble) stackInSlot.getItem()).onUnequipped(stackInSlot, par3EntityPlayer);
                            return stackInSlot.copy();
                        }
                        break;
                    }
                }
            }
        }

        return par1ItemStack;
    }
}
