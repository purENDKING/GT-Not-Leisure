package com.science.gtnl.mixins.early;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.science.gtnl.common.item.TimeStopManager;

@SuppressWarnings("UnusedMixin")
@Mixin(World.class)
public abstract class MixinWorldClientUpdateEntities {

    @Redirect(
        method = "updateEntities",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;onUpdate()V"))
    private void redirectWeatherEntityOnUpdate(Entity entityInstance) {
        if (TimeStopManager.isTimeStopped() && entityInstance instanceof EntityPlayer) {
            entityInstance.onUpdate();
        }
    }

    @Redirect(
        method = "updateEntities",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;updateEntity(Lnet/minecraft/entity/Entity;)V"))
    private void redirectRegularEntityUpdate(World instance, Entity p_72870_1_) {
        if (TimeStopManager.isTimeStopped() && p_72870_1_ instanceof EntityPlayer) {
            instance.updateEntity(p_72870_1_);
        }
    }
}
