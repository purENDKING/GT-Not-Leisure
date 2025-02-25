package com.science.gtnl.common.hatch;

import java.lang.reflect.Field;

import net.minecraft.item.ItemStack;

import com.gtnewhorizons.modularui.api.ModularUITextures;
import com.gtnewhorizons.modularui.api.forge.IItemHandlerModifiable;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.gtnewhorizons.modularui.common.widget.SlotWidget;
import com.science.gtnl.Utils.item.TextLocalization;

import gregtech.api.gui.modularui.GTUITextures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.MTEHatchDataAccess;

public class SuperDataAccessHatch extends MTEHatchDataAccess implements IItemHandlerModifiable {

    public SuperDataAccessHatch(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier);
        mDescriptionArray[1] = TextLocalization.Tooltip_SuperDataAccessHatch_00;
        initializeInventory(81);
    }

    public SuperDataAccessHatch(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
        initializeInventory(81);
    }

    public SuperDataAccessHatch(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
        initializeInventory(81);
    }

    private void initializeInventory(int size) {
        try {
            Field mInventoryField = MetaTileEntity.class.getDeclaredField("mInventory");
            mInventoryField.setAccessible(true);

            ItemStack[] newInventory = new ItemStack[size];

            mInventoryField.set(this, newInventory);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize mInventory", e);
        }
    }

    @Override
    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new SuperDataAccessHatch(mName, mTier, mDescriptionArray, mTextures);
    }

    @Override
    public void addUIWidgets(ModularWindow.Builder builder, UIBuildContext buildContext) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int slotIndex = row * 9 + col;
                builder.widget(
                    new SlotWidget(this, slotIndex)
                        .setBackground(ModularUITextures.ITEM_SLOT, GTUITextures.OVERLAY_SLOT_CIRCUIT)
                        .setPos(col * 18 + 52, row * 38));
            }
        }
    }

    @Override
    public int getSizeInventory() {
        return 81;
    }

    @Override
    public int getGUIWidth() {
        return super.getGUIWidth() + 72;
    }

    @Override
    public int getGUIHeight() {
        return super.getGUIHeight() + 112;
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        if (slot >= 0 && slot < mInventory.length) {
            mInventory[slot] = stack;
        }
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot >= 0 && slot < mInventory.length) {
            return mInventory[slot];
        }
        return null;
    }

    @Override
    public int getSlots() {
        return mInventory.length;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (slot >= 0 && slot < mInventory.length) {
            ItemStack existingStack = mInventory[slot];
            if (existingStack == null || existingStack.stackSize < existingStack.getMaxStackSize()) {
                int maxInsert = existingStack == null ? stack.getMaxStackSize()
                    : existingStack.getMaxStackSize() - existingStack.stackSize;
                int toInsert = Math.min(stack.stackSize, maxInsert);
                if (!simulate) {
                    if (existingStack == null) {
                        mInventory[slot] = stack.copy();
                        mInventory[slot].stackSize = toInsert;
                    } else {
                        existingStack.stackSize += toInsert;
                    }
                }
                ItemStack remaining = stack.copy();
                remaining.stackSize -= toInsert;
                return remaining;
            }
        }
        return stack;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (slot >= 0 && slot < mInventory.length) {
            ItemStack existingStack = mInventory[slot];
            if (existingStack != null) {
                int toExtract = Math.min(amount, existingStack.stackSize);
                ItemStack extracted = existingStack.copy();
                extracted.stackSize = toExtract;
                if (!simulate) {
                    existingStack.stackSize -= toExtract;
                    if (existingStack.stackSize <= 0) {
                        mInventory[slot] = null;
                    }
                }
                return extracted;
            }
        }
        return null;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }
}
