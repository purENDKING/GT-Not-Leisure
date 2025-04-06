package com.science.gtnl.common.block.blocks.playerDoll;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.science.gtnl.client.GTNLCreativeTabs;
import com.science.gtnl.common.GTNLItemList;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPlayerDoll extends BlockContainer {

    public BlockPlayerDoll() {
        super(Material.iron);
        this.setResistance(99999999f);
        this.setHardness(5f);
        this.setBlockName("playerDoll");
        this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 1.0F, 0.9F);
        this.setCreativeTab(GTNLCreativeTabs.GTNotLeisureBlock);
        this.setLightLevel(1f);
        this.setStepSound(soundTypeGlass);
        GameRegistry.registerBlock(this, ItemBlockPlayerDoll.class, getUnlocalizedName());
        GTNLItemList.PlayerDoll.set(new ItemStack(this, 1));
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
            if (itemstack.hasTagCompound()) {
                NBTTagCompound nbt = itemstack.getTagCompound();

                if (nbt.hasKey("SkinHttp", 8)) {
                    String skinHttp = nbt.getString("SkinHttp");
                    tileEntityPlayerDoll.setSkinHttp(skinHttp);
                }

                if (nbt.hasKey("CapeHttp", 8)) {
                    String capeHttp = nbt.getString("CapeHttp");
                    tileEntityPlayerDoll.setCapeHttp(capeHttp);
                }

                if (nbt.hasKey("enableElytra", 1)) {
                    boolean enableElytra = nbt.getBoolean("enableElytra");
                    tileEntityPlayerDoll.setEnableElytra(enableElytra);
                }

                if (nbt.hasKey("SkullOwner", 8)) {
                    String playerName = nbt.getString("SkullOwner");
                    tileEntityPlayerDoll.setOwner(playerName);
                } else {
                    String playerName = player.getCommandSenderName();
                    tileEntityPlayerDoll.setOwner(playerName);
                }

                if (nbt.hasKey("OwnerUUID", 8)) {
                    String uuid = nbt.getString("OwnerUUID");
                    tileEntityPlayerDoll.setOwnerUUID(uuid);
                } else {
                    String playerName = player.getCommandSenderName();
                    String uuid = tileEntityPlayerDoll.fetchUUID(playerName);
                    tileEntityPlayerDoll.setOwnerUUID(uuid);
                }
            } else {
                String playerName = player.getCommandSenderName();
                tileEntityPlayerDoll.setOwner(playerName);
                String uuid = tileEntityPlayerDoll.fetchUUID(playerName);
                tileEntityPlayerDoll.setOwnerUUID(uuid);
            }
        }

        int direction = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        int metadata = 0;

        switch (direction) {
            case 0:
                metadata = 2;
                break; // 南
            case 1:
                metadata = 5;
                break; // 西
            case 2:
                metadata = 3;
                break; // 北
            case 3:
                metadata = 4;
                break; // 东
        }

        world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity instanceof TileEntityPlayerDoll tileEntityPlayerDoll) {
            ItemStack drop = new ItemStack(this);
            NBTTagCompound nbt = new NBTTagCompound();

            if (tileEntityPlayerDoll.hasOwner()) {
                nbt.setString("SkullOwner", tileEntityPlayerDoll.getSkullOwner());

            }

            if (tileEntityPlayerDoll.hasOwnerUUID()) {
                nbt.setString("OwnerUUID", tileEntityPlayerDoll.getOwnerUUID());
            }

            if (tileEntityPlayerDoll.hasSkinHttp()) {
                nbt.setString("SkinHttp", tileEntityPlayerDoll.getSkinHttp());
            }

            if (tileEntityPlayerDoll.hasCapeHttp()) {
                nbt.setString("CapeHttp", tileEntityPlayerDoll.getCapeHttp());
            }
            nbt.setBoolean("enableElytra", tileEntityPlayerDoll.getEnableElytra());

            if (!nbt.hasNoTags()) {
                drop.setTagCompound(nbt);
            }

            EntityItem itemEntity = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, drop);
            world.spawnEntityInWorld(itemEntity);
        } else {
            ItemStack drop = new ItemStack(this);
            EntityItem itemEntity = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, drop);
            world.spawnEntityInWorld(itemEntity);
        }

        super.breakBlock(world, x, y, z, block, meta);
        world.removeTileEntity(x, y, z);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        return new ArrayList<>();
    }

    @Override
    public int getRenderType() {
        return -1;
    }
}
