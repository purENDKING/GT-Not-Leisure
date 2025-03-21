package com.science.gtnl.common.block.Casings.Special;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;

import com.science.gtnl.common.GTNLItemList;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class CrushingWheelsEventHandler {

    private static final int DAMAGE_INTERVAL = 20; // 每秒一次 (20 ticks)

    public CrushingWheelsEventHandler() {
        FMLCommonHandler.instance()
            .bus()
            .register(this);
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            World world = player.worldObj;

            int x = (int) Math.floor(player.posX);
            int y = (int) player.posY - 1;
            int z = (int) Math.floor(player.posZ);

            Block block = world.getBlock(x, y, z);
            int meta = world.getBlockMetadata(x, y, z);

            if (block == GTNLItemList.CrushingWheels.getBlock()) {
                if (meta == 2) {
                    if (player.ticksExisted % DAMAGE_INTERVAL == 0) {
                        player.attackEntityFrom(DamageSource.generic, 5.0F);
                    }
                }
            }
        }
    }
}
