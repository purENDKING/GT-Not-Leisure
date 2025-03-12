package com.science.gtnl.common.block.blocks.playerDoll;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

import com.mojang.authlib.GameProfile;

public class TileEntityPlayerDoll extends TileEntity {

    private GameProfile skullOwner;

    // 设置 SkullOwner（支持 GameProfile）
    public void setSkullOwner(GameProfile profile) {
        this.skullOwner = profile;
        this.markDirty(); // 标记数据已修改
    }

    public void setSkullOwner(String playerName) {
        if (playerName != null && !playerName.isEmpty()) {
            // 获取玩家名对应的 UUID
            GameProfile profile = MinecraftServer.getServer()
                .func_152358_ax() // 获取 GameProfile 缓存
                .func_152655_a(playerName); // 从 Mojang API 或缓存中获取完整的 GameProfile

            if (profile != null) {
                this.skullOwner = profile;
                this.markDirty(); // 标记数据已修改
            }
        }
    }

    // 获取 SkullOwner
    public GameProfile getSkullOwner() {
        return skullOwner;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.hasKey("SkullOwner", 10)) { // 检查是否存在 SkullOwner 的 NBT 数据
            skullOwner = NBTUtil.func_152459_a(nbt.getCompoundTag("SkullOwner")); // 从 NBT 中读取 GameProfile
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        if (skullOwner != null) {
            NBTTagCompound profileTag = new NBTTagCompound();
            NBTUtil.func_152460_a(profileTag, skullOwner); // 将 GameProfile 写入 NBT
            nbt.setTag("SkullOwner", profileTag);
        }
    }
}
