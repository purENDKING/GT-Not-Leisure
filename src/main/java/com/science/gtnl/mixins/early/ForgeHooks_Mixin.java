package com.science.gtnl.mixins.early;

import static com.science.gtnl.ScienceNotLeisure.network;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.ForgeEventFactory;

import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.science.gtnl.Utils.message.PacketGetTileEntityNBTRequest;

@SuppressWarnings("UnusedMixin")
@Mixin(value = ForgeHooks.class, remap = false)
public class ForgeHooks_Mixin {

    @Inject(method = "onPickBlock", at = @At("HEAD"), cancellable = true)
    private static void preOnPickBlockSendRequest(MovingObjectPosition target, EntityPlayer player, World world,
        CallbackInfoReturnable<Boolean> cir) {
        boolean isCtrlKeyDown = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);

        if (isCtrlKeyDown && player.capabilities.isCreativeMode) {
            ItemStack result;

            if (target != null && target.typeOfHit == MovingObjectType.BLOCK) {
                int x = target.blockX;
                int y = target.blockY;
                int z = target.blockZ;
                Block block = world.getBlock(x, y, z);

                if (!block.isAir(world, x, y, z)) {
                    result = block.getPickBlock(target, world, x, y, z, player);
                    Item item = result.getItem();
                    int blockID = Item.getIdFromItem(item);
                    int blockMeta = result.getItemDamage();
                    TileEntity tileentity = world.getTileEntity(x, y, z);
                    if (tileentity != null) {
                        network.sendToServer(new PacketGetTileEntityNBTRequest(x, y, z, blockID, blockMeta));
                        cir.setReturnValue(true);
                        cir.cancel();
                    }
                }
            }
        }

    }

    @Inject(method = "onPickBlock", at = @At("HEAD"), cancellable = true)
    private static void preOnPickBlockRewrite(MovingObjectPosition target, EntityPlayer player, World world,
        CallbackInfoReturnable<Boolean> cir) {
        boolean isCtrlKeyDown = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);

        if (isCtrlKeyDown && player.capabilities.isCreativeMode) {
            ItemStack result = null;

            if (target != null && target.typeOfHit == MovingObjectType.BLOCK) {
                int x = target.blockX;
                int y = target.blockY;
                int z = target.blockZ;
                Block block = world.getBlock(x, y, z);

                if (!block.isAir(world, x, y, z)) {
                    result = block.getPickBlock(target, world, x, y, z, player);

                    if (result != null) {
                        TileEntity tileentity = world.getTileEntity(x, y, z);
                        if (tileentity != null) {
                            NBTTagCompound nbt = new NBTTagCompound();
                            try {
                                tileentity.writeToNBT(nbt);
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }

                            nbt.removeTag("x");
                            nbt.removeTag("y");
                            nbt.removeTag("z");

                            if (!nbt.hasNoTags()) {
                                NBTTagCompound itemTag = new NBTTagCompound();
                                itemTag.setBoolean("showNBT", true);
                                itemTag.setTag("BlockEntityTag", nbt);

                                NBTTagCompound displayTag = new NBTTagCompound();

                                NBTTagList loreList = new NBTTagList();
                                loreList.appendTag(new NBTTagString("§5§o(+NBT)"));

                                displayTag.setTag("Lore", loreList);
                                itemTag.setTag("display", displayTag);

                                result.setTagCompound(itemTag);
                            }
                        }
                    }
                }
            }

            if (result != null) {
                for (int i = 0; i < 9; i++) {
                    ItemStack stackInSlot = player.inventory.getStackInSlot(i);
                    if (stackInSlot != null && stackInSlot.isItemEqual(result)
                        && ItemStack.areItemStackTagsEqual(stackInSlot, result)) {
                        player.inventory.currentItem = i;
                        cir.setReturnValue(true);
                        cir.cancel();
                        return;
                    }
                }

                int slot = player.inventory.getFirstEmptyStack();
                if (slot < 0 || slot >= 9) {
                    slot = player.inventory.currentItem;
                }

                player.inventory.setInventorySlotContents(slot, result);
                player.inventory.currentItem = slot;

                cir.setReturnValue(true);
                cir.cancel();
            } else {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }

    @Inject(method = "onPlaceItemIntoWorld", at = @At("HEAD"), cancellable = true, remap = false)
    private static void preOnPlaceItemIntoWorldRewrite(ItemStack itemstack, EntityPlayer player, World world, int x,
        int y, int z, int side, float hitX, float hitY, float hitZ, CallbackInfoReturnable<Boolean> cir) {
        if (!world.isRemote) {
            int meta = itemstack.getItemDamage();
            int size = itemstack.stackSize;
            NBTTagCompound nbt = null;
            if (itemstack.getTagCompound() != null) {
                nbt = (NBTTagCompound) itemstack.getTagCompound()
                    .copy();
            }

            if (!(itemstack.getItem() instanceof ItemBucket)) {
                world.captureBlockSnapshots = true;
            }

            boolean flag = itemstack.getItem()
                .onItemUse(itemstack, player, world, x, y, z, side, hitX, hitY, hitZ);
            world.captureBlockSnapshots = false;

            if (flag) {
                int newMeta = itemstack.getItemDamage();
                int newSize = itemstack.stackSize;
                NBTTagCompound newNBT = null;
                if (itemstack.getTagCompound() != null) {
                    newNBT = (NBTTagCompound) itemstack.getTagCompound()
                        .copy();
                }

                net.minecraftforge.event.world.BlockEvent.PlaceEvent placeEvent = null;
                List<BlockSnapshot> blockSnapshots = (List<BlockSnapshot>) world.capturedBlockSnapshots.clone();
                world.capturedBlockSnapshots.clear();

                itemstack.setItemDamage(meta);
                itemstack.stackSize = size;
                if (nbt != null) {
                    itemstack.setTagCompound(nbt);
                } else {
                    itemstack.setTagCompound(null);
                }

                if (blockSnapshots.size() > 1) {
                    placeEvent = ForgeEventFactory
                        .onPlayerMultiBlockPlace(player, blockSnapshots, ForgeDirection.getOrientation(side));
                } else if (blockSnapshots.size() == 1) {
                    placeEvent = ForgeEventFactory
                        .onPlayerBlockPlace(player, blockSnapshots.get(0), ForgeDirection.getOrientation(side));
                }

                if (placeEvent != null && (placeEvent.isCanceled())) {
                    flag = false;
                    world.restoringBlockSnapshots = true;
                    for (BlockSnapshot blocksnapshot : blockSnapshots) {
                        blocksnapshot.restore(true, false);
                    }
                    world.restoringBlockSnapshots = false;
                } else {
                    itemstack.setItemDamage(newMeta);
                    itemstack.stackSize = newSize;
                    if (newNBT != null) {
                        itemstack.setTagCompound(newNBT);
                    } else {
                        itemstack.setTagCompound(null);
                    }

                    for (BlockSnapshot blocksnapshot : blockSnapshots) {
                        int blockX = blocksnapshot.x;
                        int blockY = blocksnapshot.y;
                        int blockZ = blocksnapshot.z;
                        int metadata = world.getBlockMetadata(blockX, blockY, blockZ);
                        int updateFlag = blocksnapshot.flag;
                        Block oldBlock = blocksnapshot.replacedBlock;
                        Block newBlock = world.getBlock(blockX, blockY, blockZ);
                        if (newBlock != null && !(newBlock.hasTileEntity(metadata))) {
                            newBlock.onBlockAdded(world, blockX, blockY, blockZ);
                        }
                        world.markAndNotifyBlock(blockX, blockY, blockZ, null, oldBlock, newBlock, updateFlag);

                        if (itemstack.hasTagCompound()) {
                            NBTTagCompound itemNBT = itemstack.getTagCompound();
                            if (itemNBT.hasKey("BlockEntityTag", 10)) {
                                NBTTagCompound blockEntityTag = itemNBT.getCompoundTag("BlockEntityTag");
                                TileEntity tileentity = world.getTileEntity(blockX, blockY, blockZ);
                                if (tileentity != null) {
                                    blockEntityTag.setInteger("x", blockX);
                                    blockEntityTag.setInteger("y", blockY);
                                    blockEntityTag.setInteger("z", blockZ);
                                    try {
                                        tileentity.readFromNBT(blockEntityTag);
                                        tileentity.markDirty();
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        }
                    }
                    player.addStat(StatList.objectUseStats[Item.getIdFromItem(itemstack.getItem())], 1);
                }
            }

            world.capturedBlockSnapshots.clear();
            cir.setReturnValue(flag);
            cir.cancel();
        }
    }
}
