package com.reavaritia.common.item;

import static com.reavaritia.ReAvaritia.RESOURCE_ROOT_ID;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import com.reavaritia.ReAvaCreativeTabs;
import com.reavaritia.ReAvaItemList;
import com.reavaritia.common.entity.EntityBlazeFireball;
import com.science.gtnl.Mods;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlazeSword extends ItemSword {

    public static final ToolMaterial BLAZE = EnumHelper.addToolMaterial("BLAZE", 3, 7777, 10.0F, 22.0F, 30);

    public BlazeSword() {
        super(BLAZE);
        this.setUnlocalizedName("BlazeSword");
        this.setTextureName(RESOURCE_ROOT_ID + ":" + "BlazeSword");
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.setCreativeTab(ReAvaCreativeTabs.ReAvaritia);
        this.setMaxDamage(7777);
        MinecraftForge.EVENT_BUS.register(this);
        ReAvaItemList.BlazeSword.set(new ItemStack(this, 1));
    }

    @SubscribeEvent
    public void onEntityDrop(LivingDropsEvent event) {
        if (event.entity instanceof EntitySkeleton) {
            Entity attacker = event.source.getEntity();

            if (attacker instanceof EntityPlayer player) {
                ItemStack heldItem = player.getHeldItem();

                if (heldItem != null && heldItem.getItem() == this) {

                    Iterator<EntityItem> iterator = event.drops.iterator();
                    while (iterator.hasNext()) {
                        EntityItem item = iterator.next();
                        ItemStack stack = item.getEntityItem();
                        if (stack.getItem() == Items.skull) {
                            iterator.remove();
                        }
                    }

                    event.drops.add(
                        new EntityItem(
                            event.entity.worldObj,
                            event.entity.posX,
                            event.entity.posY,
                            event.entity.posZ,
                            new ItemStack(Items.skull, 1, 1)));
                }
            }
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        Map<Integer, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
        enchantments.put(Enchantment.fireAspect.effectId, 10);
        if (!world.isRemote) {
            EntityBlazeFireball fireball = new EntityBlazeFireball(world, player);
            world.spawnEntityInWorld(fireball);
        }
        return stack;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft) {
        playerIn.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 40, 1));
    }

    @Override
    public boolean hasEffect(ItemStack stack, int pass) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemStack, final EntityPlayer player, final List<String> toolTip,
        final boolean advancedToolTips) {
        toolTip.add(StatCollector.translateToLocal("Tooltip_BlazeSword_00"));
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        stack.addEnchantment(Enchantment.fireAspect, 10);
    }

    public static void registerEntity() {
        EntityRegistry
            .registerModEntity(EntityBlazeFireball.class, "BlazeFireBall", 0, Mods.ReAvaritia.ID, 64, 1, true);
    }
}
