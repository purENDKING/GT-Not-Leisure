package com.science.gtnl.mixins.early.render;

import net.minecraft.client.renderer.texture.TextureCompass;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.science.gtnl.common.item.TimeStopManager;

@SuppressWarnings("UnusedMixin")
@Mixin(TextureCompass.class)
public class TextureCompass_Mixin {

    @Inject(method = "updateAnimation", at = @At("HEAD"), cancellable = true)
    private void preUpdateAnimation(CallbackInfo ci) {
        if (TimeStopManager.isTimeStopped()) {
            ci.cancel();
        }
    }
}
