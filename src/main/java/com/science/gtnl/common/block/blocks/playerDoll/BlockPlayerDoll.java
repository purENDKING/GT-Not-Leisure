package com.science.gtnl.common.block.blocks.playerDoll;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.science.gtnl.client.GTNLCreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPlayerDoll extends BlockContainer {

    public BlockPlayerDoll() {
        super(Material.iron);
        this.setResistance(5f);
        this.setHardness(5f);
        this.setBlockName("playerDoll");
        this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 1.0F, 0.9F);
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
        return new TileEntityPlayerDoll();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        blockIcon = iconRegister.registerIcon("sciencenotleisure:air");
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemstack) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityPlayerDoll tileEntityPlayerDoll) {

            if (!itemstack.hasTagCompound() || !itemstack.getTagCompound()
                .hasKey("SkullOwner")) {
                tileEntityPlayerDoll.setSkullOwner(player.getCommandSenderName());
            } else {
                tileEntityPlayerDoll.setSkullOwner(
                    itemstack.getTagCompound()
                        .getString("SkullOwner"));
            }
        }
        int l = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        if (l == 0) {
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }

        if (l == 1) {
            world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }

        if (l == 2) {
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }

        if (l == 3) {
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList<>();
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity instanceof TileEntityPlayerDoll tileEntityPlayerDoll) {
            ItemStack drop = new ItemStack(this);
            NBTTagCompound nbt = new NBTTagCompound();
            tileEntityPlayerDoll.writeToNBT(nbt); // 保存 TileEntity 数据

            if (!nbt.hasNoTags()) {
                drop.setTagCompound(nbt); // 将 NBT 数据保存到 ItemStack
            }

            drops.add(drop);
        } else {
            drops.add(new ItemStack(this)); // 默认掉落
        }

        return drops;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        super.breakBlock(world, x, y, z, block, meta); // 先调用父类方法
        world.removeTileEntity(x, y, z); // 再移除 TileEntity
    }

    @Override
    public int getRenderType() {
        return -1;
    }
}
