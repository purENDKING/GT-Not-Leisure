package com.science.gtnl.mixins.late.Gregtech;

import static gregtech.api.util.GTUtility.filterValidMTEs;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import gregtech.api.metatileentity.implementations.MTEExtendedPowerMultiBlockBase;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.util.ExoticEnergyInputHelper;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.GTPPMultiBlockBase;

@SuppressWarnings("UnusedMixin")
@Mixin(value = GTPPMultiBlockBase.class, remap = false)
public abstract class GTPPMultiBlockBase_Mixin<T extends MTEExtendedPowerMultiBlockBase<T>>
    extends MTEExtendedPowerMultiBlockBase<T> {

    @Shadow
    public ArrayList<MTEHatch> mAllEnergyHatches;

    protected GTPPMultiBlockBase_Mixin(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    @Override
    public List<MTEHatch> getExoticAndNormalEnergyHatchList() {
        return new ArrayList<>(filterValidMTEs(this.mAllEnergyHatches));
    }

    @Override
    public long getMaxInputVoltage() {
        return ExoticEnergyInputHelper.getMaxInputVoltageMulti(getExoticAndNormalEnergyHatchList());
    }
}
