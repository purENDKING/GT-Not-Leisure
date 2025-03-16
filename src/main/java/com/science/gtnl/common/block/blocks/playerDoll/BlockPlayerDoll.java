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
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;
import com.science.gtnl.client.GTNLCreativeTabs;
import com.science.gtnl.common.GTNLItemList;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.interfaces.tileentity.IGregtechWailaProvider;

public class BlockPlayerDoll extends BlockContainer implements IGregtechWailaProvider {

    public BlockPlayerDoll() {
        super(Material.iron);
        this.setResistance(5f);
        this.setHardness(5f);
        this.setBlockName("playerDoll");
        this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 1.0F, 0.9F);
        this.setCreativeTab(GTNLCreativeTabs.GTNotLeisureBlock);
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
            // 检查 ItemStack 是否包含 NBT 数据
            if (itemstack.hasTagCompound()) {
                NBTTagCompound nbt = itemstack.getTagCompound();
                GameProfile profile = null;

                // 检查是否存在 SkinHttp 字符串
                if (nbt.hasKey("SkinHttp", 8)) { // 8 表示 NBTTagString
                    String skinHttp = nbt.getString("SkinHttp");
                    tileEntityPlayerDoll.setSkinHttp(skinHttp); // 将 SkinHttp 存储到 TileEntity
                }

                // 检查是否存在 CapeHttp 字符串
                if (nbt.hasKey("CapeHttp", 8)) { // 8 表示 NBTTagString
                    String capeHttp = nbt.getString("CapeHttp");
                    tileEntityPlayerDoll.setCapeHttp(capeHttp); // 将 CapeHttp 存储到 TileEntity
                }

                // 检查是否存在 enableElytra 布尔值
                if (nbt.hasKey("enableElytra", 1)) { // 1 表示 NBTTagByte
                    boolean enableElytra = nbt.getBoolean("enableElytra");
                    tileEntityPlayerDoll.setEnableElytra(enableElytra); // 将 enableElytra 存储到 TileEntity
                }

                boolean enableElytra = tileEntityPlayerDoll.getEnableElytra();
                nbt.setBoolean("enableElytra", enableElytra);

                // 处理 SkullOwner 数据
                if (nbt.hasKey("SkullOwner", 8)) { // 8 表示 NBTTagString
                    // SkullOwner 是字符串，直接获取玩家名称
                    String playerName = nbt.getString("SkullOwner");
                    if (playerName == null || playerName.isEmpty()) {
                        // 如果玩家名称为空，使用默认名称
                        playerName = player.getCommandSenderName(); // 使用放置方块的玩家名称
                    }
                    profile = new GameProfile(null, playerName);
                } else if (nbt.hasKey("SkullOwner", 10)) { // 10 表示 NBTTagCompound
                    // SkullOwner 是复合标签，使用 NBTUtil 解析 GameProfile
                    NBTTagCompound ownerTag = nbt.getCompoundTag("SkullOwner");
                    profile = NBTUtil.func_152459_a(ownerTag);

                    // 检查解析后的 GameProfile 是否有效
                    if (profile != null && (profile.getName() == null || profile.getName()
                        .isEmpty()) && profile.getId() == null) {
                        // 如果 GameProfile 的 name 和 ID 都为空，使用默认名称
                        profile = new GameProfile(null, player.getCommandSenderName()); // 默认名称
                    }
                }

                if (profile != null) {
                    tileEntityPlayerDoll.getGameProfile(profile);
                } else {
                    // 如果没有 SkullOwner 数据，则设置为放置方块的玩家
                    String playerName = player.getCommandSenderName(); // 获取放置方块的玩家名字
                    if (playerName == null || playerName.isEmpty()) {
                        // 如果玩家名称为空，使用默认名称
                        playerName = player.getCommandSenderName();
                    }
                    profile = new GameProfile(null, playerName); // 创建 GameProfile
                    tileEntityPlayerDoll.getGameProfile(profile); // 调用 getGameProfile
                }
            } else {
                tileEntityPlayerDoll.getGameProfileNull();
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
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        // 获取 TileEntity
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity instanceof TileEntityPlayerDoll tileEntityPlayerDoll) {
            ItemStack drop = new ItemStack(this);

            // 获取 SkullOwner 的 GameProfile
            GameProfile skullOwner = tileEntityPlayerDoll.getSkullOwner();

            // 如果 SkullOwner 存在，保存玩家名称到 NBT
            if (skullOwner != null && skullOwner.getName() != null) {
                NBTTagCompound nbt = new NBTTagCompound();
                String playerName = skullOwner.getName(); // 获取玩家名称
                nbt.setString("SkullOwner", playerName); // 保存到 SkullOwner 标签
                boolean enableElytra = tileEntityPlayerDoll.getEnableElytra();
                nbt.setBoolean("enableElytra", enableElytra);

                // 检查 TileEntity 中是否存在 SkinHttp 数据
                if (tileEntityPlayerDoll.hasSkinHttp()) {
                    nbt.setString("SkinHttp", tileEntityPlayerDoll.getSkinHttp()); // 保存到 SkinHttp 标签
                }

                // 检查 TileEntity 中是否存在 CapeHttp 数据
                if (tileEntityPlayerDoll.hasCapeHttp()) {
                    nbt.setString("CapeHttp", tileEntityPlayerDoll.getCapeHttp()); // 保存到 CapeHttp 标签
                }

                drop.setTagCompound(nbt); // 将 NBT 数据保存到 ItemStack
            } else if (tileEntityPlayerDoll.hasSkinHttp() || tileEntityPlayerDoll.hasCapeHttp()) {
                // 如果只有 SkinHttp 或 CapeHttp 数据，保存到 NBT
                NBTTagCompound nbt = new NBTTagCompound();
                boolean enableElytra = tileEntityPlayerDoll.getEnableElytra();
                nbt.setBoolean("enableElytra", enableElytra);
                if (tileEntityPlayerDoll.hasSkinHttp()) {
                    nbt.setString("SkinHttp", tileEntityPlayerDoll.getSkinHttp()); // 保存到 SkinHttp 标签
                }
                if (tileEntityPlayerDoll.hasCapeHttp()) {
                    nbt.setString("CapeHttp", tileEntityPlayerDoll.getCapeHttp()); // 保存到 CapeHttp 标签
                }
                drop.setTagCompound(nbt); // 将 NBT 数据保存到 ItemStack
            }

            // 生成 ItemEntity
            EntityItem itemEntity = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, drop);
            world.spawnEntityInWorld(itemEntity); // 将 ItemEntity 加入世界
        } else {
            // 默认掉落物
            ItemStack drop = new ItemStack(this);
            EntityItem itemEntity = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, drop);
            world.spawnEntityInWorld(itemEntity); // 将 ItemEntity 加入世界
        }

        // 移除 TileEntity
        super.breakBlock(world, x, y, z, block, meta);
        world.removeTileEntity(x, y, z);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        // 返回空列表，因为掉落物在 breakBlock 中处理
        return new ArrayList<>();
    }

    @Override
    public int getRenderType() {
        return -1;
    }
}
