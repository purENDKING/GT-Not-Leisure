package com.science.gtnl.common.block.blocks.playerDoll;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;
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
            // 检查 ItemStack 是否包含 SkullOwner 的 NBT 数据
            if (itemstack.hasTagCompound()) {
                NBTTagCompound nbt = itemstack.getTagCompound();
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
                    tileEntityPlayerDoll.setSkullOwner(profile); // 设置 SkullOwner
                } else {
                    // 如果没有 SkullOwner 数据，则设置为放置方块的玩家
                    tileEntityPlayerDoll.setSkullOwner(player.getCommandSenderName());
                }
            } else {
                // 如果没有 NBT 数据，则设置为放置方块的玩家
                tileEntityPlayerDoll.setSkullOwner(player.getCommandSenderName());
            }
        }

        // 设置方块的朝向
        int direction = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        int metadata = 0;

        switch (direction) {
            case 0: // 南
                metadata = 2;
                break;
            case 1: // 西
                metadata = 5;
                break;
            case 2: // 北
                metadata = 3;
                break;
            case 3: // 东
                metadata = 4;
                break;
        }

        world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList<>();
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity instanceof TileEntityPlayerDoll tileEntityPlayerDoll) {
            ItemStack drop = new ItemStack(this);

            // 获取 SkullOwner 的 GameProfile
            GameProfile skullOwner = tileEntityPlayerDoll.getSkullOwner();

            // 如果 SkullOwner 存在，保存到 NBT
            if (skullOwner != null) {
                NBTTagCompound nbt = new NBTTagCompound();
                NBTTagCompound ownerTag = new NBTTagCompound();
                NBTUtil.func_152460_a(ownerTag, skullOwner); // 将 GameProfile 写入 NBT
                nbt.setTag("SkullOwner", ownerTag); // 保存到 SkullOwner 标签
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
