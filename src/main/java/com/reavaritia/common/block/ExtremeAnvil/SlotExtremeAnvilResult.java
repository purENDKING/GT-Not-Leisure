package com.reavaritia.common.block.ExtremeAnvil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotExtremeAnvilResult extends Slot {

    private final ContainerExtremeAnvil container;

    public SlotExtremeAnvilResult(ContainerExtremeAnvil container, IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.container = container;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canTakeStack(EntityPlayer player) {
        return true;
    }

    @Override
    public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
        if (!this.container.repairedItemName.isEmpty()) {
            stack.setStackDisplayName(this.container.repairedItemName);
        }

        this.container.repairedItemName = "";

        this.container.inputSlots.setInventorySlotContents(0, null);

        this.container.inputSlots.decrStackSize(1, 1);

        this.container.updateRepairOutput();
    }
}
