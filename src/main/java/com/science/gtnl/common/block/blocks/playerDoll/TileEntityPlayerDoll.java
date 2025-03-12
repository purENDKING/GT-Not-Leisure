package com.science.gtnl.common.block.blocks.playerDoll;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPlayerDoll extends TileEntity {

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
        SkullOwner = nbt.getString("SkullOwner");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setString("SkullOwner", SkullOwner);
    }
}
