package com.science.gtnl.common.effect.effects;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.FoodStats;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.science.gtnl.Utils.GTNLEffectUtil;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PerfectPhysiqueEffect extends GTNLEffectUtil {

    public static final PerfectPhysiqueEffect instance = new PerfectPhysiqueEffect();

    public PerfectPhysiqueEffect() {
        super(187, "perfect_physique", false, 0xFFD700, 2);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            PotionEffect effect = player.getActivePotionEffect(this);

            if (effect != null) {
                if (player.getHealth() > 0.0F && player.getHealth() < 15.0F) {
                    player.setHealth(15.0F);
                }
                FoodStats foodStats = player.getFoodStats();
                if (foodStats.getFoodLevel() < 20) {
                    foodStats.setFoodLevel(20);
                }
                if (foodStats.getSaturationLevel() < 20.0F) {
                    foodStats.setFoodSaturationLevel(20.0F);
                }
            }
        }
    }
}
