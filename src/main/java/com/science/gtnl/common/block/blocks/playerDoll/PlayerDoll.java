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
import net.minecraft.world.World;

import com.science.gtnl.client.GTNLCreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PlayerDoll extends BlockContainer {

    public PlayerDoll() {
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
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityPlayerDoll tileEntityPlayerDoll) {

            if (!stack.hasTagCompound() || !stack.getTagCompound()
                .hasKey("SkullOwner")) {
                tileEntityPlayerDoll.setSkullOwner(placer.getCommandSenderName());
            } else {
                tileEntityPlayerDoll.setSkullOwner(
                    stack.getTagCompound()
                        .getString("SkullOwner"));
            }
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
}
