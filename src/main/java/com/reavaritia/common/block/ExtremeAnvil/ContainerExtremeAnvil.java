package com.reavaritia.common.block.ExtremeAnvil;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ContainerExtremeAnvil extends Container {

    public final IInventory inputSlots;
    public final IInventory outputSlot;

    public final Slot inputSlot1;
    public final Slot inputSlot2;
    public final Slot outputSlotInstance;

    public String repairedItemName = "";

    public ContainerExtremeAnvil(InventoryPlayer playerInv, IInventory inputSlots, IInventory outputSlot) {
        this.inputSlots = inputSlots;
        this.outputSlot = outputSlot;

        this.inputSlot1 = new Slot(inputSlots, 0, 27, 47);
        this.addSlotToContainer(inputSlot1);

        this.inputSlot2 = new Slot(inputSlots, 1, 76, 47);
        this.addSlotToContainer(inputSlot2);

        this.outputSlotInstance = new SlotExtremeAnvilResult(this, outputSlot, 2, 134, 47);
        this.addSlotToContainer(outputSlotInstance);

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));
        }

        this.onCraftMatrixChanged(inputSlots);
    }

    public String getRepairedItemName() {
        return this.repairedItemName;
    }

    public void setRepairedItemName(String name) {
        this.repairedItemName = name;
        this.updateRepairOutput();
    }

    public Slot getInputSlot1() {
        return this.inputSlot1;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventory) {
        super.onCraftMatrixChanged(inventory);
        this.updateRepairOutput();
    }

    public void updateRepairOutput() {
        ItemStack input = inputSlots.getStackInSlot(0);
        ItemStack material = inputSlots.getStackInSlot(1);
        ItemStack resultStack = null;

        if (input != null) {
            if (material != null) {
                if (material.getItem() instanceof ItemEnchantedBook) {
                    resultStack = input.copy();
                    Map<Integer, Integer> enchantments = new HashMap<>();
                    this.mergeEnchantments(enchantments, input);
                    this.mergeEnchantments(enchantments, material);

                    NBTTagCompound finalTag;
                    if (input.hasTagCompound()) {
                        finalTag = input.getTagCompound();
                        finalTag.removeTag("ench");
                        finalTag.removeTag("RepairCost");
                    } else {
                        finalTag = new NBTTagCompound();
                    }

                    NBTTagList enchList = new NBTTagList();
                    for (Map.Entry<Integer, Integer> entry : enchantments.entrySet()) {
                        NBTTagCompound tag = new NBTTagCompound();
                        tag.setShort(
                            "id",
                            (short) entry.getKey()
                                .intValue());
                        tag.setShort(
                            "lvl",
                            (short) entry.getValue()
                                .intValue());
                        enchList.appendTag(tag);
                    }

                    if (enchList.tagCount() > 0) {
                        finalTag.setTag("ench", enchList);
                    }

                    if (!finalTag.func_150296_c()
                        .isEmpty()) {
                        resultStack.setTagCompound(finalTag);
                    } else {
                        resultStack.setTagCompound(null);
                    }

                } else if (input.getItem() == material.getItem()) {
                    if (input.getItem()
                        .isDamageable()) {
                        resultStack = input.copy();
                        int inputDurability = input.getMaxDamage() - input.getItemDamage();
                        int materialDurability = material.getMaxDamage() - material.getItemDamage();
                        int repairedDurabilityAdded = (int) (materialDurability * 1.1);
                        int totalRepairedDurability = inputDurability + repairedDurabilityAdded;
                        int newDamage = Math.max(0, resultStack.getMaxDamage() - totalRepairedDurability);
                        resultStack.setItemDamage(newDamage);

                        Map<Integer, Integer> enchantments = new HashMap<>();
                        this.mergeEnchantments(enchantments, input);
                        this.mergeEnchantments(enchantments, material);

                        NBTTagCompound finalTag;
                        if (input.hasTagCompound()) {
                            finalTag = input.getTagCompound();
                            finalTag.removeTag("ench");
                            finalTag.removeTag("RepairCost");
                        } else {
                            finalTag = new NBTTagCompound();
                        }

                        NBTTagList enchList = new NBTTagList();
                        for (Map.Entry<Integer, Integer> entry : enchantments.entrySet()) {
                            NBTTagCompound tag = new NBTTagCompound();
                            tag.setShort(
                                "id",
                                (short) entry.getKey()
                                    .intValue());
                            tag.setShort(
                                "lvl",
                                (short) entry.getValue()
                                    .intValue());
                            enchList.appendTag(tag);
                        }

                        if (enchList.tagCount() > 0) {
                            finalTag.setTag("ench", enchList);
                        }

                        if (!finalTag.func_150296_c()
                            .isEmpty()) {
                            resultStack.setTagCompound(finalTag);
                        } else {
                            resultStack.setTagCompound(null);
                        }
                    }
                }
            } else {
                if (!this.repairedItemName.isEmpty()) {
                    resultStack = input.copy();
                    NBTTagCompound finalTag;
                    if (input.hasTagCompound()) {
                        finalTag = input.getTagCompound();
                        finalTag.removeTag("ench");
                        finalTag.removeTag("RepairCost");
                    } else {
                        finalTag = new NBTTagCompound();
                    }
                    if (!finalTag.func_150296_c()
                        .isEmpty()) {
                        resultStack.setTagCompound(finalTag);
                    } else {
                        resultStack.setTagCompound(null);
                    }
                }
            }
        }

        if (resultStack != null && !this.repairedItemName.isEmpty()) {
            resultStack.setStackDisplayName(this.repairedItemName);
        }

        outputSlot.setInventorySlotContents(2, resultStack);

        detectAndSendChanges();
    }

    public void mergeEnchantments(Map<Integer, Integer> map, ItemStack stack) {
        if (stack == null) return;

        NBTTagList list = null;
        if (stack.isItemEnchanted()) {
            list = stack.getEnchantmentTagList();
        } else if (stack.getItem() instanceof ItemEnchantedBook) {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag != null && tag.hasKey("StoredEnchantments")) {
                list = tag.getTagList("StoredEnchantments", 10);
            }
        }

        if (list != null) {
            for (int i = 0; i < list.tagCount(); ++i) {
                NBTTagCompound tag = list.getCompoundTagAt(i);
                int id = tag.getShort("id");
                int lvl = tag.getShort("lvl");

                Enchantment ench = Enchantment.enchantmentsList[id];
                if (ench != null) {
                    int currentLevel = map.getOrDefault(id, 0);

                    int mergedLevel = currentLevel + lvl;

                    int maxLevelTenfold = ench.getMaxLevel() * 10;

                    int finalLevel = Math.min(mergedLevel, maxLevelTenfold);

                    map.put(id, finalLevel);
                }
            }
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack stack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();
            stack = stackInSlot.copy();
            if (index == 2) {
                if (!this.mergeItemStack(stackInSlot, 3, 39, true)) {

                    return null;
                }
            } else if (index >= 3) {
                if (!this.mergeItemStack(stackInSlot, 0, 2, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(stackInSlot, 3, 39, false)) {
                return null;
            }
            if (stackInSlot.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }

            if (stackInSlot.stackSize == stack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(player, stackInSlot);
        }

        return stack;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);

        for (int i = 0; i < 2; ++i) {
            ItemStack stackToReturn = inputSlots.getStackInSlot(i);

            if (stackToReturn != null) {
                if (!player.inventory.addItemStackToInventory(stackToReturn)) {
                    player.dropPlayerItemWithRandomChoice(stackToReturn, false);
                }
                inputSlots.setInventorySlotContents(i, null);
            }
        }
    }
}
