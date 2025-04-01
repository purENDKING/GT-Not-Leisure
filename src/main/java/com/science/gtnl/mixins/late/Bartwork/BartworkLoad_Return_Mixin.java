package com.science.gtnl.mixins.late.Bartwork;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.science.gtnl.loader.RecipeLoaderServerStart;

import bartworks.MainMod;

@SuppressWarnings("UnusedMixin")
@Mixin(value = MainMod.class, remap = false)
public class BartworkLoad_Return_Mixin {

    @Inject(method = "runOnPlayerJoined(ZZ)V", at = @At("RETURN"), remap = false)
    private static void onRunOnPlayerJoined(boolean classicMode, boolean disableExtraGasRecipes, CallbackInfo ci) {
        RecipeLoaderServerStart.loadRecipesServerStart();
    }
}
