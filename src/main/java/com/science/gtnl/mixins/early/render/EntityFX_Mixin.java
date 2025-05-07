package com.science.gtnl.mixins.early.render;

import net.minecraft.client.particle.EntityFX;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.science.gtnl.common.item.TimeStopManager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SuppressWarnings("UnusedMixin")
@Mixin(EntityFX.class)
public abstract class EntityFX_Mixin {

    /*@Inject(
        method = "onUpdate()V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/particle/EntityFX;moveEntity(DDD)V",
            shift = At.Shift.BEFORE),
        cancellable = true)
    private void onUpdate$skipMove(CallbackInfo ci) {
        if (TimeStopManager.isTimeStopped()) {
            ci.cancel();
        }
    }*/
}
