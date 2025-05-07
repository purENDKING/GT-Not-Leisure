package com.science.gtnl.mixins.early.render;

import net.minecraft.client.particle.EffectRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.science.gtnl.common.item.TimeStopManager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SuppressWarnings("UnusedMixin")
@Mixin(EffectRenderer.class)
public abstract class EffectRenderer_Mixin {

    /*@Inject(method = "updateEffects", at = @At("HEAD"), cancellable = true)
    private void onUpdateEffects(CallbackInfo ci) {
        if (TimeStopManager.isTimeStopped()) {
            ci.cancel();
        }
    }*/
}
