package com.science.gtnl.common.block.blocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class PlayerDollTileEntity extends TileEntity {

    private String SkullOwner;

    public void setSkullOwner(String skullOwner) {
        this.SkullOwner = skullOwner;
    }

    public String getSkullOwner() {
        return SkullOwner;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.hasKey("SkullOwner")) {
            this.SkullOwner = nbt.getString("SkullOwner");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        if (this.SkullOwner != null) {
            nbt.setString("SkullOwner", this.SkullOwner);
        }
    }
}
