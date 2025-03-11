package com.science.gtnl.common.block.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.science.gtnl.client.GTNLCreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PlayerDoll extends BlockContainer {

    public PlayerDoll() {
        super(Material.wood);
        this.setBlockName("playerDoll");
        this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 1.0F, 0.9F); // 碰撞箱：长宽 0.8，高 1
        this.setCreativeTab(GTNLCreativeTabs.GTNotLeisureBlock);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new PlayerDollTileEntity();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        blockIcon = iconRegister.registerIcon("sciencenotleisure:air");
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof PlayerDollTileEntity playerDollTileEntity) {

            if (!stack.hasTagCompound() || !stack.getTagCompound()
                .hasKey("SkullOwner")) {
                playerDollTileEntity.setSkullOwner(placer.getCommandSenderName());
            } else {
                playerDollTileEntity.setSkullOwner(
                    stack.getTagCompound()
                        .getString("SkullOwner"));
            }
        }
    }
}
