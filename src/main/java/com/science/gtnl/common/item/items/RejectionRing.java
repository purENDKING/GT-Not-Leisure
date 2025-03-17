package com.science.gtnl.common.item.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.science.gtnl.client.GTNLCreativeTabs;
import com.science.gtnl.common.GTNLItemList;

import baubles.api.BaubleType;
import baubles.api.IBauble;

public class RejectionRing extends Item implements IBauble {

    public RejectionRing() {
        this.setMaxStackSize(1);
        this.setUnlocalizedName("RejectionRing");
        this.setTextureName("sciencenotleisure:RejectionRing");
        this.setCreativeTab(GTNLCreativeTabs.GTNotLeisureItem);
        GTNLItemList.RejectionRing.set(new ItemStack(this, 1));
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.RING;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        if (player instanceof EntityPlayer) {
            World world = player.worldObj;
            double playerX = player.posX;
            double playerY = player.posY;
            double playerZ = player.posZ;

            AxisAlignedBB area = AxisAlignedBB
                .getBoundingBox(playerX - 10, playerY - 10, playerZ - 10, playerX + 10, playerY + 10, playerZ + 10);
            for (EntityItem entityItem : world.getEntitiesWithinAABB(EntityItem.class, area)) {
                double itemX = entityItem.posX;
                double itemY = entityItem.posY;
                double itemZ = entityItem.posZ;

                double distance = player.getDistanceToEntity(entityItem);

                if (distance <= 2.5) {
                    entityItem.setDead();
                } else if (distance <= 10.0) {
                    Vec3 direction = Vec3.createVectorHelper(itemX - playerX, itemY - playerY, itemZ - playerZ)
                        .normalize();
                    double newX = playerX + direction.xCoord * 10;
                    double newY = playerY + direction.yCoord * 10;
                    double newZ = playerZ + direction.zCoord * 10;

                    entityItem.setPosition(newX, newY, newZ);
                }
            }
        }
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {}

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {}

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return false;
    }
}
