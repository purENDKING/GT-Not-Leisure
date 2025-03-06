package com.science.gtnl.common.block.ReAvaritia.NeutronCollector;

import static net.minecraft.block.BlockPistonBase.getPistonOrientation;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.science.gtnl.ScienceNotLeisure;
import com.science.gtnl.client.GTNLCreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DensestNeutronCollector extends BlockContainer {

    private IIcon bottom, front, sideLeft, sideRight, side, topFacingNorth, topFacingSouth, topFacingWest,
        topFacingEast;

    public DensestNeutronCollector() {
        super(Material.iron);
        setStepSound(Block.soundTypeMetal);
        setHardness(20.0F);
        setBlockName("DensestNeutronCollector");
        setHarvestLevel("pickaxe", 3);
        this.setCreativeTab(GTNLCreativeTabs.ReAvaritia);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.bottom = iconRegister.registerIcon("reavaritia:DensestNeutronCollector_Bottom");
        this.front = iconRegister.registerIcon("reavaritia:DensestNeutronCollector_Front");
        this.sideLeft = iconRegister.registerIcon("reavaritia:DensestNeutronCollector_SideLeft");
        this.sideRight = iconRegister.registerIcon("reavaritia:DensestNeutronCollector_SideRight");
        this.side = iconRegister.registerIcon("reavaritia:DensestNeutronCollector_Side");
        this.topFacingNorth = iconRegister.registerIcon("reavaritia:DensestNeutronCollector_Top_North");
        this.topFacingSouth = iconRegister.registerIcon("reavaritia:DensestNeutronCollector_Top_South");
        this.topFacingWest = iconRegister.registerIcon("reavaritia:DensestNeutronCollector_Top_West");
        this.topFacingEast = iconRegister.registerIcon("reavaritia:DensestNeutronCollector_Top_East");
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        TileEntityNeutronCollector machine = (TileEntityNeutronCollector) world.getTileEntity(x, y, z);
        int facing = (machine != null) ? machine.getFacing() : 2;

        if (side == 0) return bottom;
        if (side == 1) return getTopIconByFacing(facing);

        switch (facing) {
            case 2:
                return getFacingIcon(side, front, this.side, sideLeft, sideRight);
            case 3:
                return getFacingIcon(side, this.side, front, sideRight, sideLeft);
            case 4:
                return getFacingIcon(side, sideRight, sideLeft, front, this.side);
            case 5:
                return getFacingIcon(side, sideLeft, sideRight, this.side, front);
            default:
                return this.side;
        }
    }

    private IIcon getTopIconByFacing(int facing) {
        switch (facing) {
            case 2:
                return topFacingNorth;
            case 3:
                return topFacingSouth;
            case 4:
                return topFacingWest;
            case 5:
                return topFacingEast;
            default:
                return topFacingNorth;
        }
    }

    private IIcon getFacingIcon(int side, IIcon front, IIcon back, IIcon left, IIcon right) {
        if (side == 2) return front;
        if (side == 3) return back;
        if (side == 4) return left;
        if (side == 5) return right;
        return back;
    }

    @Override
    public IIcon getIcon(int side, int metadata) {
        int facing = getPistonOrientation(metadata);
        if (side == 0) return bottom;
        if (side == 1) return getTopIconByFacing(facing);
        switch (side) {
            case 2:
                return this.side;
            case 3:
                return front;
            case 4:
                return sideLeft;
            case 5:
                return sideRight;
            default:
                return this.side;
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7,
        float par8, float par9) {
        if (world.isRemote) {
            return true;
        } else {
            player.openGui(ScienceNotLeisure.instance, 0, world, x, y, z);
            return true;
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityNeutronCollector(200, 4, "DensestNeutronCollector");
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack item) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileEntityNeutronCollector) {
            TileEntityNeutronCollector machine = (TileEntityNeutronCollector) tile;
            int l = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

            if (l == 0) machine.setFacing(2);
            if (l == 1) machine.setFacing(5);
            if (l == 2) machine.setFacing(3);
            if (l == 3) machine.setFacing(4);
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int wut) {
        TileEntityNeutronCollector collector = (TileEntityNeutronCollector) world.getTileEntity(x, y, z);

        if (collector != null) {
            ItemStack itemstack = collector.getStackInSlot(0);

            if (itemstack != null) {
                float f = world.rand.nextFloat() * 0.8F + 0.1F;
                float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
                float f2 = world.rand.nextFloat() * 0.8F + 0.1F;

                while (itemstack.stackSize > 0) {
                    int j1 = world.rand.nextInt(21) + 10;

                    if (j1 > itemstack.stackSize) {
                        j1 = itemstack.stackSize;
                    }

                    itemstack.stackSize -= j1;
                    EntityItem entityitem = new EntityItem(
                        world,
                        (float) x + f,
                        (float) y + f1,
                        (float) z + f2,
                        new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                    if (itemstack.hasTagCompound()) {
                        entityitem.getEntityItem()
                            .setTagCompound(
                                (NBTTagCompound) itemstack.getTagCompound()
                                    .copy());
                    }

                    float f3 = 0.05F;
                    entityitem.motionX = (float) world.rand.nextGaussian() * f3;
                    entityitem.motionY = (float) world.rand.nextGaussian() * f3 + 0.2F;
                    entityitem.motionZ = (float) world.rand.nextGaussian() * f3;
                    world.spawnEntityInWorld(entityitem);
                }
            }

            world.func_147453_f(x, y, z, block); // updateNeighborsAboutBlockChange
        }

        super.breakBlock(world, x, y, z, block, wut);
    }
}
