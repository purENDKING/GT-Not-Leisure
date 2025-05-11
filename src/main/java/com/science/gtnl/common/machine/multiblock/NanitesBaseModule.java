package com.science.gtnl.common.machine.multiblock;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;

import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.machine.multiMachineClasses.GTNLProcessingLogic;
import com.science.gtnl.common.machine.multiMachineClasses.WirelessEnergyMultiMachineBase;
import com.science.gtnl.misc.OverclockType;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;

public abstract class NanitesBaseModule<T extends NanitesBaseModule<T>> extends WirelessEnergyMultiMachineBase<T> {

    protected int tCountCasing = 0;
    protected boolean isConnected = false;
    protected boolean isOreModule = false;
    protected boolean isBioModule = false;
    protected boolean isPolModule = false;
    protected double setEUtDiscount = 0;
    protected double setSpeedBoost = 0;
    protected int setMaxParallel = 0;
    protected final int HORIZONTAL_OFF_SET = 7;
    protected final int VERTICAL_OFF_SET = 17;
    protected final int DEPTH_OFF_SET = 0;
    protected int mHeatingCapacity = 0;
    protected int WirelessModeProcessingTime = 0;

    public NanitesBaseModule(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public NanitesBaseModule(String aName) {
        super(aName);
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.NanitesIntegratedProcessingCenterRecipeType)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_00)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_01)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_02)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_03)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_04)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_05)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_06)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_07)
            .addInfo(String.format(TextLocalization.Tooltip_WirelessEnergyMultiMachine_08, "1200"))
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_09)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_10)
            .addInfo(TextLocalization.Tooltip_Tectech_Hatch)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(15, 18, 31, true)
            .addInputBus(TextLocalization.Tooltip_NanitesBaseModule_Casing, 1)
            .addOutputBus(TextLocalization.Tooltip_NanitesBaseModule_Casing, 1)
            .addInputHatch(TextLocalization.Tooltip_NanitesBaseModule_Casing, 1)
            .addOutputHatch(TextLocalization.Tooltip_NanitesBaseModule_Casing, 1)
            .addEnergyHatch(TextLocalization.Tooltip_NanitesBaseModule_Casing, 1)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (aBaseMetaTileEntity.isServerSide() && isConnected) {
            super.onPostTick(aBaseMetaTileEntity, aTick);
            if (mEfficiency < 0) mEfficiency = 0;
        }
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new GTNLProcessingLogic() {

            @NotNull
            @Override
            public CheckRecipeResult process() {
                setEuModifier(getEuModifier());
                setSpeedBonus(getSpeedBonus());
                setOverclockType(OverclockType.PerfectOverclock);
                return super.process();
            }

            @Nonnull
            @Override
            protected OverclockCalculator createOverclockCalculator(@Nonnull GTRecipe recipe) {
                return wirelessMode ? OverclockCalculator.ofNoOverclock(recipe)
                    .setRecipeHeat(recipe.mSpecialValue)
                    .setMachineHeat(mHeatingCapacity)
                    : super.createOverclockCalculator(recipe).setRecipeHeat(recipe.mSpecialValue)
                        .setMachineHeat(mHeatingCapacity)
                        .setEUtDiscount(setEUtDiscount)
                        .setSpeedBoost(setSpeedBoost);
            }

            @Override
            protected @Nonnull CheckRecipeResult validateRecipe(@Nonnull GTRecipe recipe) {
                return recipe.mSpecialValue <= mHeatingCapacity ? CheckRecipeResultRegistry.SUCCESSFUL
                    : CheckRecipeResultRegistry.insufficientHeat(recipe.mSpecialValue);
            }
        }.setMaxParallelSupplier(this::getMaxParallel);
    }

    public void connect() {
        isConnected = true;
    }

    public void disconnect() {
        isConnected = false;
    }

    public void getOreModule() {
        isOreModule = false;
    }

    public void getBioModule() {
        isBioModule = false;
    }

    public void getPolModule() {
        isPolModule = false;
    }

    public double getEUtDiscount() {
        return setEUtDiscount;
    }

    public void setEUtDiscount(double discount) {
        setEUtDiscount = discount;
    }

    public double getSpeedBoost() {
        return setSpeedBoost;
    }

    public void setSpeedBoost(double boost) {
        setSpeedBoost = boost;
    }

    public int getMaxParallel() {
        return setMaxParallel;
    }

    public void setMaxParallel(int parallel) {
        setMaxParallel = parallel;
    }

    public int getHeatingCapacity() {
        return mHeatingCapacity;
    }

    public void setHeatingCapacity(int HeatingCapacity) {
        mHeatingCapacity = HeatingCapacity;
    }

    @Override
    public int getWirelessModeProcessingTime() {
        return WirelessModeProcessingTime;
    }

    public void setWirelessModeProcessingTime(int time) {
        WirelessModeProcessingTime = time;
    }

}
