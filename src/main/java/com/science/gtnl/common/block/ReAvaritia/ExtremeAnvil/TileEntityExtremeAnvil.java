package com.science.gtnl.common.block.ReAvaritia.ExtremeAnvil;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityExtremeAnvil extends TileEntity implements IInventory {

    private static final int CHECK_INTERVAL = 20;
    private int checkTimer = 0;
    private boolean isBeingRemoved = false;

    @Override
    public void updateEntity() {
        if (worldObj.isRemote) return;

        if (++checkTimer >= CHECK_INTERVAL) {
            checkTimer = 0;
            checkFoundation();
        }
    }

    private void checkFoundation() {
        if (isBeingRemoved) return;

        Block foundation = worldObj.getBlock(xCoord, yCoord - 1, zCoord);
        ItemStack stack = new ItemStack(foundation, 1, worldObj.getBlockMetadata(xCoord, yCoord - 1, zCoord));

        if (!hasOreTag(stack, "neutronUnbreak")) {
            isBeingRemoved = true;
            worldObj.func_147480_a(xCoord, yCoord, zCoord, true);
        }
    }

    @Override
    public void invalidate() {
        isBeingRemoved = true;
        super.invalidate();
    }

    // 必须实现的 NBT 方法
    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.checkTimer = tag.getInteger("CheckTimer");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("CheckTimer", this.checkTimer);
    }

    public static boolean hasOreTag(ItemStack stack, String tag) {
        if (stack == null || stack.getItem() == null) return false;
        int[] oreIDs = OreDictionary.getOreIDs(stack);
        for (int id : oreIDs) {
            if (OreDictionary.getOreName(id)
                .equalsIgnoreCase(tag)) {
                return true;
            }
        }
        return false;
    }

    private final ItemStack[] inventory = new ItemStack[3]; // 3 个槽位：2 个输入槽，1 个输出槽

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (inventory[slot] != null) {
            ItemStack stack;
            if (inventory[slot].stackSize <= amount) {
                stack = inventory[slot];
                inventory[slot] = null;
                return stack;
            } else {
                stack = inventory[slot].splitStack(amount);
                if (inventory[slot].stackSize == 0) {
                    inventory[slot] = null;
                }
                return stack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (inventory[slot] != null) {
            ItemStack stack = inventory[slot];
            inventory[slot] = null;
            return stack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory[slot] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName() {
        return "container.ExtremeAnvil";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this
            && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return true;
    }
}
