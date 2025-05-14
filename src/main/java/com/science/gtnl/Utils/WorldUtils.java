package com.science.gtnl.Utils;

import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.event.world.WorldEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

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
}
