package com.science.gtnl.common.block.blocks.tile;

import static com.science.gtnl.common.render.PlayerDollRenderManager.fetchUUID;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPlayerDoll extends TileEntity {

    public String skullOwner;
    public String ownerUUID;
    public String skinHttp;
    public String capeHttp;
    public boolean enableElytra;

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        if (nbt.hasKey("SkullOwner", 8)) {
            this.skullOwner = nbt.getString("SkullOwner");
        }

        if (nbt.hasKey("OwnerUUID", 8)) {
            this.ownerUUID = nbt.getString("OwnerUUID");
        }

        if (nbt.hasKey("SkinHttp", 8)) {
            this.skinHttp = nbt.getString("SkinHttp");
        }
        if (nbt.hasKey("CapeHttp", 8)) {
            this.capeHttp = nbt.getString("CapeHttp");
        }
        if (nbt.hasKey("enableElytra")) {
            enableElytra = nbt.getBoolean("enableElytra");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        if (this.skullOwner != null) {
            nbt.setString("SkullOwner", this.skullOwner);
        }

        if (this.ownerUUID != null) {
            nbt.setString("OwnerUUID", this.ownerUUID);
        }
        if (this.skinHttp != null) {
            nbt.setString("SkinHttp", this.skinHttp);
        }
        if (this.capeHttp != null) {
            nbt.setString("CapeHttp", this.capeHttp);
        }

        nbt.setBoolean("enableElytra", enableElytra);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 4, nbt);
    }

    @Override
    public void onDataPacket(net.minecraft.network.NetworkManager net,
        net.minecraft.network.play.server.S35PacketUpdateTileEntity pkt) {
        NBTTagCompound nbt = pkt.func_148857_g();
        this.readFromNBT(nbt);
    }

    public String getSkullOwner() {
        return skullOwner;
    }

    public String getOwnerUUID() {
        return ownerUUID;
    }

    public void clearOwner() {
        this.skullOwner = null;
        this.ownerUUID = null;
        this.markDirty();
    }

    public boolean hasOwner() {
        return skullOwner != null && !skullOwner.isEmpty();
    }

    public void setOwner(String username) {
        if (username != null && !username.isEmpty()) {
            String uuid = fetchUUID(username);
            if (uuid != null) {
                this.skullOwner = username;
                this.ownerUUID = uuid;
                this.markDirty();
            }
        }
    }

    public boolean hasOwnerUUID() {
        return ownerUUID != null && !ownerUUID.isEmpty();
    }

    public void setOwnerUUID(String uuid) {
        this.ownerUUID = uuid;
        this.markDirty();
    }

    public boolean hasSkinHttp() {
        return skinHttp != null && !skinHttp.isEmpty();
    }

    public String getSkinHttp() {
        return skinHttp;
    }

    public void setSkinHttp(String skinHttp) {
        this.skinHttp = skinHttp;
        this.markDirty();
    }

    public boolean hasCapeHttp() {
        return capeHttp != null && !capeHttp.isEmpty();
    }

    public String getCapeHttp() {
        return capeHttp;
    }

    public void setCapeHttp(String capeHttp) {
        this.capeHttp = capeHttp;
        this.markDirty();
    }

    public boolean getEnableElytra() {
        return enableElytra;
    }

    public void setEnableElytra(boolean enableElytra) {
        this.enableElytra = enableElytra;
        this.markDirty();
    }
}
