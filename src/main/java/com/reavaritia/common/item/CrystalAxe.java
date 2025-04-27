package com.reavaritia.common.item;

import static com.reavaritia.ReAvaritia.RESOURCE_ROOT_ID;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

import com.reavaritia.ReAvaItemList;
import com.reavaritia.ReCreativeTabs;
import com.reavaritia.TextLocalization;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CrystalAxe extends ItemAxe {

    public static final ToolMaterial CRYSTAL = EnumHelper.addToolMaterial("CRYSTAL", 32, 8888, 9999F, 7.0F, 22);

    public CrystalAxe() {
        super(CRYSTAL);
        this.setUnlocalizedName("CrystalAxe");
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.setCreativeTab(CreativeTabs.tabTools);
        this.setCreativeTab(ReCreativeTabs.ReAvaritia);
        this.setTextureName(RESOURCE_ROOT_ID + ":" + "CrystalAxe");
        this.setMaxDamage(8888);
        ReAvaItemList.CrystalAxe.set(new ItemStack(this, 1));
    }

    @Override
    public boolean hasEffect(ItemStack stack, int pass) {
        return true;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        target.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100, 2));

        performAreaAttack(attacker, target, 10);

        stack.damageItem(1, attacker);
        return true;
    }

    private void performAreaAttack(EntityLivingBase attacker, EntityLivingBase target, double radius) {
        World world = attacker.worldObj;
        AxisAlignedBB area = AxisAlignedBB.getBoundingBox(
            target.posX - radius,
            target.posY - radius,
            target.posZ - radius,
            target.posX + radius,
            target.posY + radius,
            target.posZ + radius);

        List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, area);
        for (EntityLivingBase entity : entities) {
            if (entity != attacker && entity != target) {
                entity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), 10.0F);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean advanced) {
        list.add(TextLocalization.Tooltip_CrystalAxe_00);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player) {
        player.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
        return itemStackIn;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.block;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft) {
        int duration = this.getMaxItemUseDuration(stack) - timeLeft;

        if (duration > 20) {
            playerIn.addPotionEffect(new PotionEffect(Potion.resistance.id, 40, 1));
        }
    }
}
