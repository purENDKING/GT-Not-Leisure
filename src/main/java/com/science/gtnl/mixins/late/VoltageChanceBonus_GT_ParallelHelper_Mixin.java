package com.science.gtnl.mixins.late;

import java.util.OptionalDouble;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.science.gtnl.Utils.recipes.ChanceBonusManager;

import gregtech.api.interfaces.tileentity.IVoidable;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ParallelHelper;

@SuppressWarnings("UnusedMixin")
@Mixin(value = ParallelHelper.class, remap = false)
public abstract class VoltageChanceBonus_GT_ParallelHelper_Mixin {

    // Original code from Overpowered Mod. MIT License. 2025/4/18

    @Shadow
    private GTRecipe recipe;

    @Shadow
    private double chanceMultiplier;

    @Shadow
    private IVoidable machine;

    /**
     * Inject at the start of determineParallel to apply voltage-based chance bonus.
     */
    @Inject(method = "determineParallel", at = @At("HEAD"), remap = false)
    private void opDetermineParallel(CallbackInfo ci) {
        // Compute optional bonus based on machine and current EU/t
        ChanceBonusManager.setLastGTRecipe(recipe);
        OptionalDouble bonusOptional = ChanceBonusManager
            .getChanceBonusOptional(machine, GTUtility.getTier(recipe.mEUt), chanceMultiplier);
        // If present, apply bonus by copying the recipe with increased chances
        if (bonusOptional.isPresent()) {
            recipe = ChanceBonusManager.copyAndBonusChance(recipe, bonusOptional.getAsDouble());
        }
    }
}
