package com.science.gtnl.common.item.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import com.science.gtnl.api.IVoltageChanceBonus;
import com.science.gtnl.client.GTNLCreativeTabs;
import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.config.MainConfig;

import gregtech.api.metatileentity.CommonMetaTileEntity;
import gregtech.api.metatileentity.implementations.MTEMultiBlockBase;
import gregtech.api.util.GTUtility;

public class DebugItem extends Item {

    public DebugItem() {
        super();
        this.setUnlocalizedName("DebugItem");
        this.setCreativeTab(GTNLCreativeTabs.GTNotLeisureItem);
        this.setTextureName("sciencenotleisure:DebugItem");
        GTNLItemList.DebugItem.set(new ItemStack(this, 1));
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile instanceof CommonMetaTileEntity) {
                Object meta = ((CommonMetaTileEntity) tile).getMetaTileEntity();

                if (meta instanceof IVoltageChanceBonus bonusMachine) {
                    double bonus = bonusMachine.getBonusChance();
                    int tier = bonusMachine.getVoltageTier();
                    int baseTier = bonusMachine.getBaseVoltageTier();
                    player.addChatComponentMessage(
                        new ChatComponentTranslation("Debug_VoltageChanceBonus_00", bonus, tier, baseTier));
                } else if (meta instanceof MTEMultiBlockBase mte) {
                    try {
                        int tier = GTUtility.getTier(mte.getMaxInputVoltage());
                        int baseTier = GTUtility.getTier(1);
                        double bonus = (tier <= baseTier) ? 0.0 : (tier - baseTier) * MainConfig.recipeOutputChance;
                        player.addChatComponentMessage(
                            new ChatComponentTranslation("Debug_VoltageChanceBonus_00", bonus, tier, baseTier));
                    } catch (Exception ignored) {}
                }
            }
        }
        return false;
    }
}
