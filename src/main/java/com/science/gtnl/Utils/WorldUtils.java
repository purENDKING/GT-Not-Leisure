package com.science.gtnl.Utils;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.WorldEvent;

import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.common.machine.hatch.ExplosionDynamoHatch;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import ic2.api.event.ExplosionEvent;

public class WorldUtils {

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        World world = event.world;
        if (!world.isRemote) {
            GameRules rules = world.getGameRules();
            if (!rules.hasRule("doWeatherCycle")) {
                rules.setOrCreateGameRule("doWeatherCycle", "true");
            }
        }
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.START || event.world.isRemote) return;

        World world = event.world;
        GameRules rules = world.getGameRules();

        if (rules.hasRule("doWeatherCycle") && !rules.getGameRuleBooleanValue("doWeatherCycle")) {
            WorldInfo info = world.getWorldInfo();

            info.setRainTime(1000000);
            info.setThunderTime(1000000);

            info.setRaining(info.isRaining());
            info.setThundering(info.isThundering());

            world.rainingStrength = info.isRaining() ? 1.0f : 0.0f;
            world.thunderingStrength = info.isThundering() ? 1.0f : 0.0f;
        }
    }

    @SubscribeEvent
    public void onExplosionEvent(ExplosionEvent event) {
        World world = event.world;
        double explosionX = event.x;
        double explosionY = event.y;
        double explosionZ = event.z;
        float power = (float) event.power;

        int ex = (int) explosionX;
        int ey = (int) explosionY;
        int ez = (int) explosionZ;

        for (int x = ex - 10; x <= ex + 10; x++) {
            for (int y = ey - 10; y <= ey + 10; y++) {
                for (int z = ez - 10; z <= ez + 10; z++) {
                    if (!world.blockExists(x, y, z)) continue;
                    TileEntity tile = world.getTileEntity(x, y, z);
                    if (tile instanceof BaseMetaTileEntity te) {
                        IMetaTileEntity mte = te.getMetaTileEntity();
                        if (mte instanceof ExplosionDynamoHatch machine) {
                            double dx = te.getXCoord() + 0.5 - explosionX;
                            double dy = te.getYCoord() + 0.5 - explosionY;
                            double dz = te.getZCoord() + 0.5 - explosionZ;
                            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
                            if (distance <= 10.0) {
                                double efficiency = Math.max(0.0, 1.0 - 0.05 * distance);
                                long addedEU = (long) (power * 2048 * efficiency);
                                long stored = machine.getEUVar();
                                long maxEU = machine.maxEUStore();
                                if (stored + addedEU <= maxEU) {
                                    machine.setEUVar(stored + addedEU);
                                    event.setCanceled(true);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.entity instanceof EntityPlayer player) {
            World world = player.worldObj;

            int x = (int) Math.floor(player.posX);
            int y = (int) player.posY - 1;
            int z = (int) Math.floor(player.posZ);

            Block block = world.getBlock(x, y, z);
            int meta = world.getBlockMetadata(x, y, z);

            if (block == GTNLItemList.CrushingWheels.getBlock() && meta == 2) {
                player.attackEntityFrom(DamageSource.generic, 4.0F);
                player.hurtResistantTime = 0;
            }
        }
    }
}
