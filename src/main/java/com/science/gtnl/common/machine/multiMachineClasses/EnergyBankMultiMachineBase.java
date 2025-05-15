package com.science.gtnl.common.machine.multiMachineClasses;

import static com.dreammaster.gthandler.IDSU.GT_MetaTileEntity_IDSU.mTier;
import static com.science.gtnl.Utils.Utils.mergeArray;
import static gregtech.api.enums.GTValues.V;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;

import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.OverclockCalculator;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;

public abstract class EnergyBankMultiMachineBase<T extends EnergyBankMultiMachineBase<T>> extends MultiMachineBase<T> {

    // 构造方法
    public EnergyBankMultiMachineBase(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public EnergyBankMultiMachineBase(String aName) {
        super(aName);
    }

    // 成员变量
    protected boolean isRecipeProcessing = false;
    protected long totalEnergyCost = 0;
    protected String energyCostText = "0";
    protected int cycleNum = 100_000;
    protected boolean energyBankMode = true;

    // NBT持久化
    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setBoolean("energyBankMode", energyBankMode);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        energyBankMode = aNBT.getBoolean("energyBankMode");
    }

    // WAILA集成
    @Override
    public void getWailaBody(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        super.getWailaBody(itemStack, currentTip, accessor, config);
        final NBTTagCompound tag = accessor.getNBTData();
        if (tag.getBoolean("energyBankMode")) {
            currentTip.add(
                EnumChatFormatting.AQUA + StatCollector.translateToLocal("Waila_CurrentEuCost")
                    + EnumChatFormatting.RESET
                    + ": "
                    + EnumChatFormatting.GOLD
                    + tag.getString("energyCostText")
                    + EnumChatFormatting.RESET
                    + " EU");
        }
    }

    @Override
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y,
        int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        final IGregTechTileEntity tileEntity = getBaseMetaTileEntity();
        if (tileEntity != null) {
            tag.setBoolean("energyBankMode", energyBankMode);
            if (energyBankMode) tag.setString("energyCostText", energyCostText);
        }
    }

    // 核心处理逻辑
    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() { // 改为使用标准ProcessingLogic

            @NotNull
            @Override
            public CheckRecipeResult process() {

                setEuModifier(getEuModifier());
                setSpeedBonus(getSpeedBonus());
                setOverclock(isEnablePerfectOverclock() ? 2 : 1, 2);
                return super.process();
            }

            @Nonnull
            @Override
            protected OverclockCalculator createOverclockCalculator(@Nonnull GTRecipe recipe) {
                return energyBankMode ? OverclockCalculator.ofNoOverclock(recipe)
                    : super.createOverclockCalculator(recipe);
            }
        }.setMaxParallelSupplier(this::getLimitedMaxParallel);
    }

    protected boolean isEnablePerfectOverclock() {
        return false; // 根据实际情况实现
    }

    @Nonnull
    @Override
    public CheckRecipeResult checkProcessing() {
        resetEnergyTracking();
        prepareProcessing();

        if (!energyBankMode) {
            return super.checkProcessing();
        }

        return processEnergyBankRecipes();
    }

    private CheckRecipeResult processEnergyBankRecipes() {
        long availableEnergy = getStoredEnergy();
        long totalConsumption = 0;
        CheckRecipeResult finalResult = CheckRecipeResultRegistry.SUCCESSFUL;
        boolean anySucceeded = false;

        for (int i = 0; i < cycleNum; i++) {
            CheckRecipeResult result = processSingleRecipe();
            if (!result.wasSuccessful()) {
                finalResult = result;
                break;
            }

            long recipeCost = calculateRecipeEnergyCost();
            if (!canAffordRecipe(availableEnergy, totalConsumption, recipeCost)) {
                finalResult = CheckRecipeResultRegistry.insufficientPower(recipeCost);
                break;
            }

            totalConsumption += recipeCost;
            anySucceeded = true;
        }

        if (anySucceeded) {
            commitEnergyConsumption(totalConsumption);
            updateProcessingParameters();
        }

        updateSlots();
        return finalResult;
    }

    private CheckRecipeResult processSingleRecipe() {
        if (!isRecipeProcessing) startRecipeProcessing();
        setupProcessingLogic(processingLogic);
        configureEnergyBankPowerLogic(processingLogic);

        CheckRecipeResult result = doCheckRecipe();
        if (!result.wasSuccessful()) {
            return result;
        }

        mergeOutputs();
        endRecipeProcessing();
        return result;
    }

    private void resetEnergyTracking() {
        totalEnergyCost = 0;
        energyCostText = "0";
    }

    private boolean canAffordRecipe(long available, long consumed, long cost) {
        return (consumed + cost) <= available;
    }

    private void commitEnergyConsumption(long amount) {
        IMetaTileEntity mte = getBaseMetaTileEntity().getMetaTileEntity();
        if (mte instanceof BaseMetaTileEntity) {
            BaseMetaTileEntity baseMTE = (BaseMetaTileEntity) mte;
            baseMTE.setStoredEU(baseMTE.getStoredEU() - amount);
        }
        totalEnergyCost = amount;
        energyCostText = GTUtility.formatNumbers(totalEnergyCost);
    }

    private void updateProcessingParameters() {
        mEfficiency = 10000;
        mEfficiencyIncrease = 10000;
        mMaxProgresstime = getEnergyBankProcessingTime();
    }

    private void mergeOutputs() {
        mOutputItems = mergeArray(mOutputItems, processingLogic.getOutputItems());
        mOutputFluids = mergeArray(mOutputFluids, processingLogic.getOutputFluids());
    }

    // 需要实现的抽象方法
    public abstract int getEnergyBankProcessingTime();

    // 能量仓配置方法
    protected void configureEnergyBankPowerLogic(ProcessingLogic logic) {
        // 使用GregTech标准电流获取方法
        logic.setAvailableVoltage(V[mTier]);
        logic.setAvailableAmperage(getBaseMetaTileEntity().getInputAmperage());
        logic.setAmperageOC(true);
    }

    protected long getStoredEnergy() {
        return getBaseMetaTileEntity().getStoredEU();
    }

    protected long calculateRecipeEnergyCost() {
        return processingLogic.getCalculatedEut() * processingLogic.getDuration();
    }

    protected void prepareProcessing() {}
}
