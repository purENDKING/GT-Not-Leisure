package com.science.gtnl.common.block.blocks.playerDoll;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StringUtils;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class TileEntityPlayerDoll extends TileEntity {

    public GameProfile skullOwner;
    public String skinHttp;
    public String capeHttp;
    public boolean enableElytra;

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.hasKey("SkullOwner", 10)) { // 10 表示 NBTTagCompound
            this.skullOwner = NBTUtil.func_152459_a(nbt.getCompoundTag("SkullOwner"));
        } else if (nbt.hasKey("SkullOwner", 8)) { // 8 表示 NBTTagString
            String playerName = nbt.getString("SkullOwner");
            this.skullOwner = new GameProfile(null, playerName);
        }
        if (nbt.hasKey("SkinHttp", 8)) {
            this.skinHttp = nbt.getString("SkinHttp");
        }
        if (nbt.hasKey("CapeHttp", 8)) { // 新增 CapeHttp 读取
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
            NBTTagCompound ownerTag = new NBTTagCompound();
            NBTUtil.func_152460_a(ownerTag, this.skullOwner);
            nbt.setTag("SkullOwner", ownerTag);
        }
        if (this.skinHttp != null) {
            nbt.setString("SkinHttp", this.skinHttp);
        }
        if (this.capeHttp != null) { // 新增 CapeHttp 写入
            nbt.setString("CapeHttp", this.capeHttp);
        }
        nbt.setBoolean("enableElytra", enableElytra);
    }

    /**
     * 获取 TileEntity 的描述包，用于客户端和服务器同步数据
     */
    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt); // 将 TileEntity 数据写入 NBT
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 4, nbt);
    }

    @Override
    public void onDataPacket(net.minecraft.network.NetworkManager net,
        net.minecraft.network.play.server.S35PacketUpdateTileEntity pkt) {
        NBTTagCompound nbt = pkt.func_148857_g(); // 获取数据包中的 NBT 数据
        if (nbt.hasKey("SkullOwner", 10)) { // 10 表示 NBTTagCompound
            this.skullOwner = NBTUtil.func_152459_a(nbt.getCompoundTag("SkullOwner"));
        } else if (nbt.hasKey("SkullOwner", 8)) { // 8 表示 NBTTagString
            String playerName = nbt.getString("SkullOwner");
            this.skullOwner = new GameProfile(null, playerName);
        }
        if (nbt.hasKey("SkinHttp", 8)) {
            this.skinHttp = nbt.getString("SkinHttp");
        }
        if (nbt.hasKey("CapeHttp", 8)) { // 新增 CapeHttp 同步
            this.capeHttp = nbt.getString("CapeHttp");
        }
        if (nbt.hasKey("enableElytra")) {
            enableElytra = nbt.getBoolean("enableElytra");
        }
    }

    public GameProfile getSkullOwner() {
        return skullOwner;
    }

    public void getGameProfileNull() {
        this.skullOwner = null;
        this.markDirty();
    }

    public void getGameProfile(GameProfile gameProfile) {
        this.skullOwner = gameProfile;
        this.getProfile();
    }

    public boolean hasSkinHttp() {
        return skinHttp != null && !skinHttp.isEmpty();
    }

    public String getSkinHttp() {
        return skinHttp;
    }

    public void setSkinHttp(String skinHttp) {
        this.skinHttp = skinHttp;
    }

    public boolean hasCapeHttp() { // 新增 CapeHttp 检查
        return capeHttp != null && !capeHttp.isEmpty();
    }

    public String getCapeHttp() { // 新增 CapeHttp 获取
        return capeHttp;
    }

    public void setCapeHttp(String capeHttp) { // 新增 CapeHttp 设置
        this.capeHttp = capeHttp;
    }

    public boolean getEnableElytra() {
        return enableElytra;
    }

    public void setEnableElytra(boolean enableElytra) {
        this.enableElytra = enableElytra;
    }

    public void getProfile() {
        if (this.skullOwner != null && !StringUtils.isNullOrEmpty(this.skullOwner.getName())) {
            if (!this.skullOwner.isComplete() || !this.skullOwner.getProperties()
                .containsKey("textures")) {
                GameProfile gameprofile = MinecraftServer.getServer()
                    .func_152358_ax()
                    .func_152655_a(this.skullOwner.getName());

                if (gameprofile != null) {
                    Property property = (Property) Iterables.getFirst(
                        gameprofile.getProperties()
                            .get("textures"),
                        (Object) null);

                    if (property == null) {
                        gameprofile = MinecraftServer.getServer()
                            .func_147130_as()
                            .fillProfileProperties(gameprofile, true);
                    }

                    this.skullOwner = gameprofile;
                    this.markDirty();
                }
            }
        }
    }
}
