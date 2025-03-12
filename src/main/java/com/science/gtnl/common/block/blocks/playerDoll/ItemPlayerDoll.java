package com.science.gtnl.common.block.blocks.playerDoll;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

public class ItemPlayerDoll extends ItemBlock {

    public ItemPlayerDoll(Block block) {
        super(block);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ, int metadata) {
        // 调用父类方法放置方块
        boolean placed = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);

        if (placed) {
            // 获取放置方块的 TileEntity
            TileEntity tileEntity = world.getTileEntity(x, y, z);

            if (tileEntity instanceof TileEntityPlayerDoll tileEntityPlayerDoll) {
                // 检查 ItemStack 是否包含 SkullOwner 的 NBT 数据
                if (stack.hasTagCompound()) {
                    NBTTagCompound nbt = stack.getTagCompound();
                    GameProfile profile = null;

                    if (nbt.hasKey("SkullOwner", 8)) { // 8 表示 NBTTagString
                        // SkullOwner 是字符串，直接获取玩家名称
                        String playerName = nbt.getString("SkullOwner");
                        profile = new GameProfile(null, playerName);
                    } else if (nbt.hasKey("SkullOwner", 10)) { // 10 表示 NBTTagCompound
                        // SkullOwner 是复合标签，使用 NBTUtil 解析 GameProfile
                        NBTTagCompound ownerTag = nbt.getCompoundTag("SkullOwner");
                        profile = NBTUtil.func_152459_a(ownerTag);
                    }

                    if (profile != null) {
                        ((TileEntityPlayerDoll) tileEntity).getGameProfile(profile);
                    } else {
                        // 如果没有 SkullOwner 数据，则设置为放置方块的玩家
                        String playerName = player.getCommandSenderName(); // 获取放置方块的玩家名字
                        profile = new GameProfile(null, playerName); // 创建 GameProfile
                        tileEntityPlayerDoll.getGameProfile(profile); // 调用 getGameProfile
                    }
                } else {
                    ((TileEntityPlayerDoll) tileEntity).getGameProfileNull();
                }
            }
        }

        return placed;
    }
}
