package com.science.gtnl.common.block.blocks.playerDoll;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StringUtils;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class TileEntityPlayerDoll extends TileEntity {

    private GameProfile skullOwner;

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.hasKey("SkullOwner", 10)) { // 10 表示 NBTTagCompound
            this.skullOwner = NBTUtil.func_152459_a(nbt.getCompoundTag("SkullOwner"));
        } else if (nbt.hasKey("SkullOwner", 8)) { // 8 表示 NBTTagString
            String playerName = nbt.getString("SkullOwner");
            this.skullOwner = new GameProfile(null, playerName);
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
