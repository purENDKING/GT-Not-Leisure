package com.science.gtnl.common.block.blocks.playerDoll;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemPlayerDoll extends ItemBlock {

    public ItemPlayerDoll(Block block) {
        super(block);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ, int metadata) {
        boolean placed = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
        if (placed) {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity instanceof TileEntityPlayerDoll) {
                TileEntityPlayerDoll tileEntityPlayerDoll = (TileEntityPlayerDoll) tileEntity;
                if (stack.hasTagCompound() && stack.getTagCompound()
                    .hasKey("PlayerName")) {
                    tileEntityPlayerDoll.setSkullOwner(
                        stack.getTagCompound()
                            .getString("PlayerName"));
                } else {
                    tileEntityPlayerDoll.setSkullOwner(player.getCommandSenderName());
                }
            }
        }
        return placed;
    }
}
