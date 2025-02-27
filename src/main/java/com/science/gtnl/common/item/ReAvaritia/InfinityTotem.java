package com.science.gtnl.common.item.ReAvaritia;

import java.util.List;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.world.ExplosionEvent;

import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.client.GTNLCreativeTabs;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class InfinityTotem extends Item {

    public InfinityTotem() {
        this.setUnlocalizedName("InfinityTotem");
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.setCreativeTab(CreativeTabs.tabTools);
        this.setCreativeTab(GTNLCreativeTabs.ReAvaritia);
        this.setTextureName("reavaritia:InfinityTotem");
        this.setMaxDamage(99);
        this.setMaxStackSize(1);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean hasEffect(ItemStack stack, int pass) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemStack, final EntityPlayer player, final List toolTip,
        final boolean advancedToolTips) {
        toolTip.add(TextLocalization.Tooltip_InfinityTotem_00);
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void showSubtitle() {
        String localized = TextLocalization.Tooltip_InfinityTotem_Enable;
        ChatComponentText text = new ChatComponentText(EnumChatFormatting.WHITE + localized);

        Minecraft.getMinecraft().ingameGUI.func_110326_a(text.getFormattedText(), true);
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            ItemStack stack = getTotemFromPlayer(player);
            if (stack != null) {
                if (stack.getItemDamage() < stack.getMaxDamage() && player.posY < 0D) {
                    if (!addStackToPlayerInventory(player, stack)) {
                        dropItemToPlayer(player.worldObj, player, stack);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            ItemStack stack = getTotemFromPlayer(player);
            if (stack != null && stack.getItemDamage() < stack.getMaxDamage()) {
                event.setCanceled(true);
                if (stack.getItemDamage() == stack.getMaxDamage() - 1) {
                    triggerFinalEffect(player.worldObj, player, stack);
                } else {
                    triggerNormalEffect(player.worldObj, player, stack);
                }
                if (player.worldObj.isRemote) {
                    showSubtitle();
                    player.worldObj.playSoundAtEntity(player, "sciencenotleisure:totem.enable", 1.0F, 1.0F);
                }
            }
        }
    }

    @SubscribeEvent
    public void onItemExpire(ItemExpireEvent event) {
        if (event.entityItem.getEntityItem()
            .getItem() instanceof InfinityTotem) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onItemToss(ItemTossEvent event) {
        ItemStack stack = event.entityItem.getEntityItem();
        if (stack.getItem() instanceof InfinityTotem) {
            NBTTagCompound nbt = stack.getTagCompound();
            if (nbt == null) {
                nbt = new NBTTagCompound();
                stack.setTagCompound(nbt);
            }
            nbt.setString(
                "ownerUUID",
                event.player.getUniqueID()
                    .toString());
        }
    }

    @SubscribeEvent
    public void onExplosion(ExplosionEvent.Detonate event) {
        List<Entity> affectedEntities = event.getAffectedEntities();
        affectedEntities.removeIf(
            entity -> entity instanceof EntityItem && ((EntityItem) entity).getEntityItem()
                .getItem() instanceof InfinityTotem);
    }

    @SubscribeEvent
    public void onEntityItemUpdate(LivingUpdateEvent event) {
        if (event.entity instanceof EntityItem) {
            EntityItem entityItem = (EntityItem) event.entity;
            ItemStack stack = entityItem.getEntityItem();
            if (stack.getItem() instanceof InfinityTotem) {
                World world = entityItem.worldObj;
                int x = (int) Math.floor(entityItem.posX);
                int y = (int) Math.floor(entityItem.posY);
                int z = (int) Math.floor(entityItem.posZ);

                if (world.getBlock(x, y, z) == Blocks.cactus) {
                    returnItemToPlayerInventory(entityItem);
                }

                if (world.getBlock(x, y, z) == Blocks.lava) {
                    returnItemToPlayerInventory(entityItem);
                }

                if (entityItem.posY < 0D) {
                    returnItemToPlayerInventory(entityItem);
                }
            }
        }
    }

    private void returnItemToPlayerInventory(EntityItem entityItem) {
        if (entityItem.isDead) return;

        ItemStack stack = entityItem.getEntityItem()
            .copy();
        if (stack == null) return;

        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt != null && nbt.hasKey("ownerUUID")) {
            UUID ownerUUID = UUID.fromString(nbt.getString("ownerUUID"));
            EntityPlayer player = entityItem.worldObj.func_152378_a(ownerUUID);

            if (player != null && !player.worldObj.isRemote) {
                boolean added = player.inventory.addItemStackToInventory(stack);
                if (added) {
                    entityItem.setDead();
                } else {
                    EntityItem newEntity = new EntityItem(
                        player.worldObj,
                        player.posX,
                        player.posY + 0.5,
                        player.posZ,
                        stack);
                    newEntity.delayBeforeCanPickup = 40;
                    player.worldObj.spawnEntityInWorld(newEntity);
                    entityItem.setDead();
                }
            }
        }
    }

    private ItemStack getTotemFromPlayer(EntityPlayer player) {
        ItemStack heldItem = player.getHeldItem();
        if (heldItem != null && heldItem.getItem() instanceof InfinityTotem) {
            return heldItem;
        }
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof InfinityTotem) {
                return stack;
            }
        }
        return null;
    }

    private void triggerNormalEffect(World world, EntityPlayer player, ItemStack stack) {
        player.setHealth(10.0F);
        player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 130 * 20, 4));
        player.addPotionEffect(new PotionEffect(Potion.resistance.id, 20 * 20, 1));
        player.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 35 * 20, 2));
        player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 55 * 20, 0));
        stack.damageItem(1, player);
    }

    private void triggerFinalEffect(World world, EntityPlayer player, ItemStack stack) {
        player.setHealth(player.getMaxHealth());
        player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 40 * 20, 1));
        player.addPotionEffect(new PotionEffect(Potion.jump.id, 40 * 20, 1));
        stack.damageItem(1, player);

        List<EntityLivingBase> entities = world
            .getEntitiesWithinAABB(EntityLivingBase.class, player.boundingBox.expand(8.0D, 8.0D, 8.0D));
        UUID ownerUUID = UUID.fromString(
            stack.getTagCompound()
                .getString("ownerUUID"));
        for (EntityLivingBase entity : entities) {
            if (!entity.getUniqueID()
                .equals(ownerUUID)) {
                entity.attackEntityFrom(DamageSource.magic, 1000.0F);
            }
        }
    }

    private boolean addStackToPlayerInventory(EntityPlayer player, ItemStack stack) {
        if (player.inventory.addItemStackToInventory(stack)) {
            return true;
        } else {
            return false;
        }
    }

    private void dropItemToPlayer(World world, EntityPlayer player, ItemStack stack) {
        if (!world.isRemote) {
            EntityItem entityItem = new EntityItem(world, player.posX, player.posY, player.posZ, stack);
            entityItem.delayBeforeCanPickup = 0;
            world.spawnEntityInWorld(entityItem);
        }
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem) {
        ItemStack stack = entityItem.getEntityItem();
        if (stack.getItemDamage() >= stack.getMaxDamage()) {
            entityItem.setDead();
            return true;
        }
        if (entityItem.posY < 0D) {
            returnItemToPlayerInventory(entityItem);
            return true;
        }
        return super.onEntityItemUpdate(entityItem);
    }
}
