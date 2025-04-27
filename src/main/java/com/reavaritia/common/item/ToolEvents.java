package com.reavaritia.common.item;

import static net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import static net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.oredict.OreDictionary;

import com.reavaritia.common.ItemLoader;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import fox.spiteful.avaritia.Config;
import fox.spiteful.avaritia.items.LudicrousItems;

public class ToolEvents {

    static final String[] trash = new String[] { "dirt", "sand", "gravel", "cobblestone", "netherrack" };

    @SubscribeEvent
    public void onPlayerMine(PlayerInteractEvent event) {
        if (!Config.bedrockBreaker || event.face == -1
            || event.world.isRemote
            || event.action != PlayerInteractEvent.Action.LEFT_CLICK_BLOCK
            || event.entityPlayer.getHeldItem() == null
            || event.entityPlayer.capabilities.isCreativeMode) return;
        Block block = event.world.getBlock(event.x, event.y, event.z);
        int meta = event.world.getBlockMetadata(event.x, event.y, event.z);
        if (block.getBlockHardness(event.entityPlayer.worldObj, event.x, event.y, event.z) <= -1
            && event.entityPlayer.getHeldItem()
                .getItem() == ItemLoader.InfinityPickaxe
            && (block.getMaterial() == Material.rock || block.getMaterial() == Material.iron)) {

            if (event.entityPlayer.getHeldItem()
                .getTagCompound() != null && event.entityPlayer.getHeldItem()
                    .getTagCompound()
                    .getBoolean("HammerMode")) {
                ItemLoader.InfinityPickaxe
                    .onBlockStartBreak(event.entityPlayer.getHeldItem(), event.x, event.y, event.z, event.entityPlayer);
            } else {

                if (block.quantityDropped(event.world.rand) == 0) {
                    ItemStack drop = block.getPickBlock(
                        ToolHelper.raytraceFromEntity(event.world, event.entityPlayer, true, 10),
                        event.world,
                        event.x,
                        event.y,
                        event.z,
                        event.entityPlayer);
                    if (drop == null) drop = new ItemStack(block, 1, meta);
                    dropItem(drop, event.entityPlayer.worldObj, event.x, event.y, event.z);
                } else block.harvestBlock(event.world, event.entityPlayer, event.x, event.y, event.z, meta);
                event.entityPlayer.worldObj.setBlockToAir(event.x, event.y, event.z);
                event.world.playAuxSFX(2001, event.x, event.y, event.z, Block.getIdFromBlock(block) + (meta << 12));
            }
        }
    }

    @SubscribeEvent
    public void handleExtraLuck(HarvestDropsEvent event) {
        if (event.harvester == null) return;
        if (event.harvester.getHeldItem() == null) return;
        ItemStack held = event.harvester.getHeldItem();
        if (held.getItem() == ItemLoader.InfinityPickaxe) {

            if (held.getTagCompound() != null && held.getTagCompound()
                .getBoolean("HammerMode")
                && ToolHelper.hammering.contains(event.harvester)
                && ToolHelper.hammerdrops.containsKey(event.harvester)
                && ToolHelper.hammerdrops.get(event.harvester) != null) {

                ToolHelper.hammerdrops.get(event.harvester)
                    .addAll(event.drops);
                event.drops.clear();
            }
        } else if (held.getItem() == ItemLoader.InfinityShovel) {

            if (held.getTagCompound() != null && held.getTagCompound()
                .getBoolean("DestroyerMode")
                && ToolHelper.hammering.contains(event.harvester)
                && ToolHelper.hammerdrops.containsKey(event.harvester)
                && ToolHelper.hammerdrops.get(event.harvester) != null) {

                ArrayList<ItemStack> garbage = new ArrayList<ItemStack>();
                for (ItemStack drop : event.drops) {
                    if (isGarbage(drop)) garbage.add(drop);
                }
                for (ItemStack junk : garbage) {
                    event.drops.remove(junk);
                }
                ToolHelper.hammerdrops.get(event.harvester)
                    .addAll(event.drops);
                event.drops.clear();
            }
        } else if (held.getItem() == ItemLoader.InfinityAxe) {

            if (ToolHelper.hammering.contains(event.harvester) && ToolHelper.hammerdrops.containsKey(event.harvester)
                && ToolHelper.hammerdrops.get(event.harvester) != null) {

                ToolHelper.hammerdrops.get(event.harvester)
                    .addAll(event.drops);
                event.drops.clear();
            }
        }
    }

    @SubscribeEvent
    public void onHarvestDrops(HarvestDropsEvent event) {
        if (event.harvester != null && event.harvester.getHeldItem() != null
            && event.harvester.getHeldItem()
                .getItem() == ItemLoader.InfinityHoe) {

            if (event.block == Blocks.melon_block || event.block == Blocks.pumpkin
                || event.block instanceof BlockCocoa) {

                event.drops.clear();
            }
        }
    }

    private static boolean isGarbage(ItemStack drop) {
        for (int id : OreDictionary.getOreIDs(drop)) {
            for (String ore : trash) {
                if (OreDictionary.getOreName(id)
                    .equals(ore)) return true;
            }
        }

        return false;
    }

    public static void dropItem(ItemStack drop, World world, int x, int y, int z) {
        float f = 0.7F;
        double d0 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
        double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
        double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
        EntityItem entityitem = new EntityItem(world, (double) x + d0, (double) y + d1, (double) z + d2, drop);
        entityitem.delayBeforeCanPickup = 10;
        world.spawnEntityInWorld(entityitem);
    }

    @SubscribeEvent
    public void onGetHurt(LivingHurtEvent event) {
        if (!(event.entityLiving instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) event.entityLiving;
        if (player.getHeldItem() != null && player.getHeldItem()
            .getItem() == ItemLoader.InfinitySword && player.isUsingItem()) event.setCanceled(true);
        if (LudicrousItems.isInfinite(player) && !event.source.damageType.equals("infinity")) event.setCanceled(true);
    }

    @SubscribeEvent
    public void onAttacked(LivingAttackEvent event) {
        if (!(event.entityLiving instanceof EntityPlayer)) return;
        if (event.source.getEntity() != null && event.source.getEntity() instanceof EntityPlayer) return;
        EntityPlayer player = (EntityPlayer) event.entityLiving;
        if (player.getHeldItem() != null && player.getHeldItem()
            .getItem() == ItemLoader.InfinitySword && player.isUsingItem()) event.setCanceled(true);
        if (LudicrousItems.isInfinite(player) && !event.source.damageType.equals("infinity")) event.setCanceled(true);
    }

    @SubscribeEvent
    public void diggity(BreakSpeed event) {
        if (event.entityPlayer.getHeldItem() != null) {
            ItemStack held = event.entityPlayer.getHeldItem();
            if (held.getItem() == ItemLoader.InfinityPickaxe || held.getItem() == ItemLoader.InfinityShovel) {
                if (!event.entityPlayer.onGround) event.newSpeed *= 5;
                if (!event.entityPlayer.isInsideOfMaterial(Material.water)
                    && !EnchantmentHelper.getAquaAffinityModifier(event.entityPlayer)) event.newSpeed *= 5;
                if (held.getTagCompound() != null) {
                    if (held.getTagCompound()
                        .getBoolean("HammerMode")
                        || held.getTagCompound()
                            .getBoolean("DestroyerMode")) {
                        event.newSpeed *= 0.5;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void canHarvest(PlayerEvent.HarvestCheck event) {
        if (event.entityPlayer.getHeldItem() != null) {
            ItemStack held = event.entityPlayer.getHeldItem();
            if (held.getItem() == ItemLoader.InfinityShovel && event.block.getMaterial() == Material.rock) {
                if (held.getTagCompound() != null && held.getTagCompound()
                    .getBoolean("DestroyerMode") && isGarbageBlock(event.block)) event.success = true;
            }
        }
    }

    private static boolean isGarbageBlock(Block block) {
        for (int id : OreDictionary.getOreIDs(new ItemStack(block, 1))) {
            String ore = OreDictionary.getOreName(id);
            if (ore.equals("cobblestone") || ore.equals("stone") || ore.equals("netherrack")) return true;
        }

        return false;
    }

    @SubscribeEvent
    public void clusterClustererererer(EntityItemPickupEvent event) {
        if (event.entityPlayer != null && event.item.getEntityItem()
            .getItem() == ItemLoader.MatterCluster) {
            ItemStack stack = event.item.getEntityItem();
            EntityPlayer player = event.entityPlayer;

            for (int i = 0; i < player.inventory.mainInventory.length; i++) {
                if (stack.stackSize == 0) {
                    break;
                }
                ItemStack slot = player.inventory.mainInventory[i];
                if (slot != null && slot.getItem() != null && slot.getItem() == ItemLoader.MatterCluster) {
                    MatterCluster.mergeClusters(stack, slot);
                }
            }
        }
    }
}
