package com.science.gtnl.mixins.late;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.science.gtnl.common.recipe.Special.RemoveRecipes;
import com.science.gtnl.config.MainConfig;
import com.science.gtnl.loader.RecipeLoaderServerStart;

import bartworks.MainMod;

@SuppressWarnings("UnusedMixin")
@Mixin(MainMod.class)
public abstract class BartworkLoad_Mixin {

    @Inject(method = "runOnPlayerJoined(ZZ)V", at = @At("TAIL"), remap = false)
    private static void onRunOnPlayerJoined(boolean classicMode, boolean disableExtraGasRecipes, CallbackInfo ci) {
        if (MainConfig.enableDeleteRecipe) {
            RemoveRecipes.removeRecipes();
        }
        RecipeLoaderServerStart.loadRecipesServerStart();
    }
}
