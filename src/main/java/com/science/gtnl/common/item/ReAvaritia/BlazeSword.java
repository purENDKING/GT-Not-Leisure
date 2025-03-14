package com.science.gtnl.common.item.ReAvaritia;

import static com.science.gtnl.Mods.ScienceNotLeisure;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.client.GTNLCreativeTabs;
import com.science.gtnl.common.GTNLItemList;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlazeSword extends ItemSword {

    public static final ToolMaterial BLAZE = EnumHelper.addToolMaterial("BLAZE", 3, 7777, 10.0F, 22.0F, 30);

    public BlazeSword() {
        super(BLAZE);
        this.setUnlocalizedName("BlazeSword");
        this.setTextureName("reavaritia:BlazeSword");
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.setCreativeTab(GTNLCreativeTabs.ReAvaritia);
        this.setMaxDamage(7777);
        MinecraftForge.EVENT_BUS.register(this);
        GTNLItemList.BlazeSword.set(new ItemStack(this, 1));
    }

    @SubscribeEvent
    public void onEntityDrop(LivingDropsEvent event) {
        if (event.entity instanceof EntitySkeleton) {
            EntitySkeleton skeleton = (EntitySkeleton) event.entity;
            Entity attacker = event.source.getEntity();

            if (attacker instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) attacker;
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
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.block;
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
    public void addInformation(final ItemStack itemStack, final EntityPlayer player, final List toolTip,
        final boolean advancedToolTips) {
        toolTip.add(TextLocalization.Tooltip_BlazeSword_00);
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        stack.addEnchantment(Enchantment.fireAspect, 10);
    }

    public static class EntityBlazeFireball extends EntitySmallFireball {

        public EntityBlazeFireball(World world, EntityPlayer player) {
            super(world, player, 0, 0, 0);
            Vec3 look = player.getLookVec();
            this.setPosition(player.posX + look.xCoord, player.posY + 1.5 + look.yCoord, player.posZ + look.zCoord);
            this.accelerationX = look.xCoord * 0.1;
            this.accelerationY = look.yCoord * 0.1;
            this.accelerationZ = look.zCoord * 0.1;
        }

        public EntityBlazeFireball(World world) {
            super(world);
        }

        @Override
        protected void onImpact(MovingObjectPosition mop) {
            if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                int x = mop.blockX;
                int y = mop.blockY;
                int z = mop.blockZ;

                if (worldObj.getBlock(x, y, z) == Blocks.sand) {
                    worldObj.setBlock(x, y, z, Blocks.glass);
                } else {
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            for (int k = -1; k <= 1; k++) {
                                int targetX = x + i;
                                int targetY = y + j;
                                int targetZ = z + k;
                                if (worldObj.isAirBlock(targetX, targetY, targetZ)) {
                                    worldObj.setBlock(targetX, targetY, targetZ, Blocks.fire);
                                }
                            }
                        }
                    }
                }
            }

            if (mop.entityHit != null && mop.entityHit instanceof EntityLivingBase) {
                EntityLivingBase target = (EntityLivingBase) mop.entityHit;

                if (target instanceof EntityEnderman) {
                    ((EntityEnderman) target).setAttackTarget((EntityLivingBase) shootingEntity);
                    target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) shootingEntity), 50.0F);
                } else {
                    if (!target.isPotionActive(Potion.fireResistance.id)) {
                        target.setFire(5);
                        target.attackEntityFrom(DamageSource.onFire, 50.0F);
                    }
                }

                if (target instanceof EntitySkeleton) {
                    if (!worldObj.isRemote) {
                        target.entityDropItem(new ItemStack(Items.skull, 1, 1), 0.0F);
                    }
                }
            }

            this.setDead();
        }
    }

    public static void registerEntity() {
        EntityRegistry
            .registerModEntity(EntityBlazeFireball.class, "BlazeFireBall", 0, ScienceNotLeisure.ID, 64, 1, true);
    }
}
