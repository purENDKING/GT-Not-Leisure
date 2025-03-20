package com.science.gtnl.common.block.blocks.playerDoll;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;

public class PlayerDollWailaDataProvider implements IWailaDataProvider {

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null; // 默认返回 null，使用 WAILA 的默认行为
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        return currentTip; // 默认不修改头部信息
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        final NBTTagCompound tag = accessor.getNBTData();

        if (tag.hasKey("CapeHttp", 8)) {
            String capeUrl = tag.getString("CapeHttp");
            if (!StringUtils.isNullOrEmpty(capeUrl)) {
                currentTip.add(
                    EnumChatFormatting.AQUA + StatCollector.translateToLocal("Waila_TileEntityPlayerDoll_02")
                        + EnumChatFormatting.GOLD
                        + capeUrl);
            }
        }

        if (tag.hasKey("enableElytra")) {
            boolean enableElytra = tag.getBoolean("enableElytra");
            String elytraStatus = enableElytra ? StatCollector.translateToLocal("Waila_TileEntityPlayerDoll_03_On")
                : StatCollector.translateToLocal("Waila_TileEntityPlayerDoll_03_Off");
            currentTip.add(
                EnumChatFormatting.AQUA + StatCollector.translateToLocal("Waila_TileEntityPlayerDoll_03")
                    + EnumChatFormatting.GOLD
                    + elytraStatus);
        }

        if (tag.hasKey("SkinHttp", 8)) {
            String skinUrl = tag.getString("SkinHttp");
            if (!StringUtils.isNullOrEmpty(skinUrl)) {
                currentTip.add(
                    EnumChatFormatting.AQUA + StatCollector.translateToLocal("Waila_TileEntityPlayerDoll_01")
                        + EnumChatFormatting.GOLD
                        + skinUrl);
                return currentTip;
            }
        }

        if (tag.hasKey("SkullOwner", 10)) {
            NBTTagCompound ownerTag = tag.getCompoundTag("SkullOwner");
            GameProfile profile = NBTUtil.func_152459_a(ownerTag);
            if (profile != null && !StringUtils.isNullOrEmpty(profile.getName())) {
                currentTip.add(
                    EnumChatFormatting.AQUA + StatCollector.translateToLocal("Waila_TileEntityPlayerDoll_00")
                        + EnumChatFormatting.GOLD
                        + profile.getName());
                return currentTip;
            }
        }

        if (tag.hasKey("SkullOwner", 8)) {
            String playerName = tag.getString("SkullOwner");
            if (!StringUtils.isNullOrEmpty(playerName)) {
                currentTip.add(
                    EnumChatFormatting.AQUA + StatCollector.translateToLocal("Waila_TileEntityPlayerDoll_00")
                        + EnumChatFormatting.GOLD
                        + playerName);
            }
        }

        return currentTip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        return currentTip; // 默认不修改尾部信息
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x,
        int y, int z) {
        if (te instanceof TileEntityPlayerDoll) {
            TileEntityPlayerDoll playerDoll = (TileEntityPlayerDoll) te;

            // 将 TileEntity 数据写入 NBT
            if (playerDoll.getSkullOwner() != null) {
                NBTTagCompound ownerTag = new NBTTagCompound();
                NBTUtil.func_152460_a(ownerTag, playerDoll.getSkullOwner());
                tag.setTag("SkullOwner", ownerTag);
            }

            if (playerDoll.getSkinHttp() != null) {
                tag.setString("SkinHttp", playerDoll.getSkinHttp());
            }
            if (playerDoll.getCapeHttp() != null) { // 新增 CapeHttp 写入
                tag.setString("CapeHttp", playerDoll.getCapeHttp());
            }
            tag.setBoolean("enableElytra", playerDoll.getEnableElytra());

        }
        return tag;
    }
}
