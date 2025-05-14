package com.science.gtnl.mixins.late.Gregtech;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.science.gtnl.api.mixinHelper.IOverclockCalculatorExtension;

import gregtech.api.util.OverclockCalculator;

@SuppressWarnings("UnusedMixin")
@Mixin(value = OverclockCalculator.class, remap = false)
public class OverclockCalculator_Mixin implements IOverclockCalculatorExtension {

    @Shadow
    private double speedBoost;

    @Override
    public void setMoreSpeedBoost(double moreSpeedBoost) {
        this.speedBoost *= moreSpeedBoost;
    }
}
