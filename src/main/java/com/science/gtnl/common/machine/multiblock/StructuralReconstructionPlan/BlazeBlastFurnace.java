package com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.GregTechAPI.sBlockCasings2;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.*;
import static gregtech.api.util.GTUtility.validMTEList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.common.machine.hatch.CustomFluidHatch;
import com.science.gtnl.common.machine.multiMachineClasses.MultiMachineBase;
import com.science.gtnl.config.MainConfig;

import bartworks.util.BWUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.*;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.metatileentity.implementations.MTEHatchEnergy;
import gregtech.api.metatileentity.implementations.MTEHatchMaintenance;
import gregtech.api.metatileentity.implementations.MTEHatchOutput;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.ExoticEnergyInputHelper;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import gregtech.api.util.shutdown.ShutDownReasonRegistry;
import gtPlusPlus.core.block.ModBlocks;
import gtPlusPlus.core.util.minecraft.FluidUtils;
import gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock;
import tectech.thing.metaTileEntity.hatch.MTEHatchEnergyTunnel;

public class BlazeBlastFurnace extends MultiMachineBase<BlazeBlastFurnace> implements ISurvivalConstructable {

    protected int energyHatchTier;
    public static final int CASING_INDEX = TAE.GTPP_INDEX(15);
    private int mCasing;
    public static final String STRUCTURE_PIECE_MAIN = "main";
    public static final String BBF_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/blaze_blast_furnace";
    public static String[][] shape = StructureUtils.readStructureFromFile(BBF_STRUCTURE_FILE_PATH);
    public final int horizontalOffSet = 3;
    public final int verticalOffSet = 3;
    public final int depthOffSet = 1;
    public static IStructureDefinition<BlazeBlastFurnace> STRUCTURE_DEFINITION = null;
    private int mHeatingCapacity = 0;
    private HeatingCoilLevel mCoilLevel;
    public int multiTier = 1;
    protected final FluidStack[] pollutionFluidStacks = { Materials.CarbonDioxide.getGas(1000),
        Materials.CarbonMonoxide.getGas(1000), Materials.SulfurDioxide.getGas(1000) };

    protected final ArrayList<MTEHatchOutput> mPollutionOutputHatches = new ArrayList<>();
    public final ArrayList<CustomFluidHatch> FluidBlazeInputHatch = new ArrayList<>();

    public BlazeBlastFurnace(final int aID, final String aName, final String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public BlazeBlastFurnace(final String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(final IGregTechTileEntity aTileEntity) {
        return new BlazeBlastFurnace(this.mName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.BlazeBlastFurnaceRecipeType)
            .addInfo(TextLocalization.Tooltip_BlazeBlastFurnace_00)
            .addInfo(TextLocalization.Tooltip_BlazeBlastFurnace_01)
            .addInfo(TextLocalization.Tooltip_BlazeBlastFurnace_02)
            .addInfo(TextLocalization.Tooltip_BlazeBlastFurnace_03)
            .addInfo(TextLocalization.Tooltip_BlazeBlastFurnace_04)
            .addInfo(TextLocalization.Tooltip_BlazeBlastFurnace_05)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_04)
            .beginStructureBlock(7, 6, 7, true)
            .addInputBus(TextLocalization.Tooltip_BlazeBlastFurnace_Casing_00, 1)
            .addOutputBus(TextLocalization.Tooltip_BlazeBlastFurnace_Casing_00, 1)
            .addInputHatch(TextLocalization.Tooltip_BlazeBlastFurnace_Casing_00, 1)
            .addOutputHatch(TextLocalization.Tooltip_BlazeBlastFurnace_Casing_00, 1)
            .addOutputHatch(TextLocalization.Tooltip_BlazeBlastFurnace_Casing_01, 1)
            .addEnergyHatch(TextLocalization.Tooltip_BlazeBlastFurnace_Casing_00, 1)
            .addMufflerHatch(TextLocalization.Tooltip_BlazeBlastFurnace_Casing_02, 1)
            .addMaintenanceHatch(TextLocalization.Tooltip_BlazeBlastFurnace_Casing_00, 1)
            .addOtherStructurePart(
                TextLocalization.FluidBlazeInputHatch,
                TextLocalization.Tooltip_BlazeBlastFurnace_Casing_00,
                1)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IStructureDefinition<BlazeBlastFurnace> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<BlazeBlastFurnace>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', ofBlock(sBlockCasings2, 15))
                .addElement('B', ofCoil(BlazeBlastFurnace::setCoilLevel, BlazeBlastFurnace::getCoilLevel))
                .addElement(
                    'C',
                    buildHatchAdder(BlazeBlastFurnace.class)
                        .atLeast(
                            OutputHatch.withAdder(BlazeBlastFurnace::addOutputHatchToTopList)
                                .withCount(t -> t.mPollutionOutputHatches.size()))
                        .casingIndex(TAE.getIndexFromPage(2, 11))
                        .dot(1)
                        .buildAndChain(ModBlocks.blockCasings3Misc, 11))
                .addElement('D', ofBlock(ModBlocks.blockCasingsMisc, 14))
                .addElement(
                    'E',
                    ofChain(
                        buildHatchAdder(BlazeBlastFurnace.class)
                            .atLeast(InputBus, OutputBus, InputHatch, OutputHatch, Energy.or(ExoticEnergy), Maintenance)
                            .dot(1)
                            .casingIndex(CASING_INDEX)
                            .build(),
                        onElementPass(x -> ++x.mCasing, ofBlock(ModBlocks.blockCasingsMisc, 15)),
                        buildHatchAdder(BlazeBlastFurnace.class).adder(BlazeBlastFurnace::addFluidBlazeInputHatch)
                            .hatchId(21503)
                            .shouldReject(x -> !x.FluidBlazeInputHatch.isEmpty())
                            .casingIndex(CASING_INDEX)
                            .dot(1)
                            .build()))
                .addElement('F', Muffler.newAny(TAE.getIndexFromPage(2, 11), 1))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    public void setCoilLevel(HeatingCoilLevel aCoilLevel) {
        this.mCoilLevel = aCoilLevel;
    }

    public HeatingCoilLevel getCoilLevel() {
        return this.mCoilLevel;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, horizontalOffSet, verticalOffSet, depthOffSet);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (mMachine) return -1;
        this.setCoilLevel(HeatingCoilLevel.None);
        return survivialBuildPiece(
            STRUCTURE_PIECE_MAIN,
            stackSize,
            horizontalOffSet,
            verticalOffSet,
            depthOffSet,
            elementBudget,
            env,
            false,
            true);
    }

    public int getMultiTier(ItemStack inventory) {
        if (inventory == null) return 1;
        if (inventory.isItemEqual(GTNLItemList.BlazeCube.get(1))) return 4;
        return 1;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        this.mHeatingCapacity = 0;
        mCasing = 0;
        multiTier = 1;
        this.setCoilLevel(HeatingCoilLevel.None);
        this.mPollutionOutputHatches.clear();
        FluidBlazeInputHatch.clear();

        if (!checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet)) return false;

        this.multiTier = getMultiTier(aStack);

        if (getCoilLevel() == HeatingCoilLevel.None) return false;

        if (mMaintenanceHatches.size() != 1 && mMufflerHatches.size() != 1) return false;

        this.mHeatingCapacity = (int) getCoilLevel().getHeat() + 100 * (BWUtil.getTier(getMaxInputVoltage()) - 2);

        energyHatchTier = checkEnergyHatchTier();
        if (MainConfig.enableMachineAmpLimit) {
            for (MTEHatch hatch : getExoticEnergyHatches()) {
                if (hatch instanceof MTEHatchEnergyTunnel) {
                    return false;
                }
            }
            if (getMaxInputAmps() > 64) return false;
        }

        return mCasing >= 50 && checkHatch();
    }

    public boolean addOutputHatchToTopList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) return false;
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null) return false;
        if (aMetaTileEntity instanceof MTEHatchOutput) {
            ((MTEHatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
            return this.mPollutionOutputHatches.add((MTEHatchOutput) aMetaTileEntity);
        }
        return false;
    }

    @Override
    public boolean addOutput(FluidStack aLiquid) {
        if (aLiquid == null) return false;
        FluidStack tLiquid = aLiquid.copy();
        boolean isOutputPollution = false;
        for (FluidStack pollutionFluidStack : this.pollutionFluidStacks) {
            if (!tLiquid.isFluidEqual(pollutionFluidStack)) continue;

            isOutputPollution = true;
            break;
        }
        ArrayList<MTEHatchOutput> tOutputHatches;
        if (isOutputPollution) {
            tOutputHatches = this.mPollutionOutputHatches;
            tLiquid.amount = tLiquid.amount * Math.min(100 - getAveragePollutionPercentage(), 100) / 100;
        } else {
            tOutputHatches = this.mOutputHatches;
        }
        return dumpFluid(tOutputHatches, tLiquid, true) || dumpFluid(tOutputHatches, tLiquid, false);
    }

    @Override
    public boolean checkHatch() {
        return super.checkHatch() && !FluidBlazeInputHatch.isEmpty();
    }

    private boolean addFluidBlazeInputHatch(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) {
            return false;
        } else {
            IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
            if (aMetaTileEntity instanceof CustomFluidHatch && aMetaTileEntity.getBaseMetaTileEntity()
                .getMetaTileID() == 21503) {
                return addToMachineListInternal(FluidBlazeInputHatch, aTileEntity, aBaseCasingIndex);
            }
        }
        return false;
    }

    @Override
    public void updateSlots() {
        for (CustomFluidHatch tHatch : validMTEList(FluidBlazeInputHatch)) tHatch.updateSlots();
        super.updateSlots();
    }

    protected float getSpeedBonus() {
        return 1F;
    }

    protected boolean isEnablePerfectOverclock() {
        return false;
    }

    protected int getCasingTextureID() {
        return TAE.getIndexFromPage(2, 11);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(TexturesGtBlock.oMCAAdvancedEBFActive)
                    .extFacing()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(TexturesGtBlock.oMCAAdvancedEBF)
                    .extFacing()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeMaps.blastFurnaceRecipes;
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @Nonnull
            @Override
            protected OverclockCalculator createOverclockCalculator(@Nonnull GTRecipe recipe) {
                return super.createOverclockCalculator(recipe).setRecipeHeat(recipe.mSpecialValue)
                    .setMachineHeat(BlazeBlastFurnace.this.mHeatingCapacity)
                    .setHeatOC(true)
                    .setHeatDiscount(true)
                    .setSpeedBoost(1.0 / 2.5);
            }

            @Override
            protected @Nonnull CheckRecipeResult validateRecipe(@Nonnull GTRecipe recipe) {
                return recipe.mSpecialValue <= BlazeBlastFurnace.this.mHeatingCapacity
                    ? CheckRecipeResultRegistry.SUCCESSFUL
                    : CheckRecipeResultRegistry.insufficientHeat(recipe.mSpecialValue);
            }
        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }

    @Override
    public int getMaxParallelRecipes() {
        return 64 * multiTier;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);

        if (this.mStartUpCheck < 0) {
            startRecipeProcessing();
            if (this.mMaxProgresstime > 0 && this.mProgresstime != 0 || this.getBaseMetaTileEntity()
                .hasWorkJustBeenEnabled()) {
                if (aTick % 20 == 0 || this.getBaseMetaTileEntity()
                    .hasWorkJustBeenEnabled()) {
                    int baseAmount = (int) (10 * getInputVoltageTier() * getInputVoltageTier());
                    if (multiTier == 4) {
                        baseAmount *= 2;
                    }
                    if (!this.depleteInputFromRestrictedHatches(this.FluidBlazeInputHatch, baseAmount)) {
                        this.causeMaintenanceIssue();
                        this.stopMachine(
                            ShutDownReasonRegistry.outOfFluid(
                                Objects.requireNonNull(FluidUtils.getFluidStack("molten.blaze", baseAmount))));
                        endRecipeProcessing();
                    }
                }
            }
            endRecipeProcessing();
        }
    }

    @Override
    public boolean getDefaultHasMaintenanceChecks() {
        return true;
    }

    @Override
    public boolean shouldCheckMaintenance() {
        return true;
    }

    @Override
    public void checkMaintenance() {
        if (!shouldCheckMaintenance()) return;

        if (getRepairStatus() != getIdealStatus()) {
            for (MTEHatchMaintenance tHatch : validMTEList(mMaintenanceHatches)) {
                if (tHatch.mAuto) tHatch.autoMaintainance();
                if (tHatch.mWrench) mWrench = true;
                if (tHatch.mScrewdriver) mScrewdriver = true;
                if (tHatch.mSoftHammer) mSoftHammer = true;
                if (tHatch.mHardHammer) mHardHammer = true;
                if (tHatch.mSolderingTool) mSolderingTool = true;
                if (tHatch.mCrowbar) mCrowbar = true;

                tHatch.mWrench = false;
                tHatch.mScrewdriver = false;
                tHatch.mSoftHammer = false;
                tHatch.mHardHammer = false;
                tHatch.mSolderingTool = false;
                tHatch.mCrowbar = false;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected SoundResource getActivitySoundLoop() {
        return SoundResource.GT_MACHINES_ADV_FREEZER_LOOP;
    }

    @Override
    protected void setProcessingLogicPower(ProcessingLogic logic) {
        boolean useSingleAmp = mEnergyHatches.size() == 1 && mExoticEnergyHatches.isEmpty() && getMaxInputAmps() <= 2;
        logic.setAvailableVoltage(getMachineVoltageLimit());
        logic.setAvailableAmperage(
            useSingleAmp ? 1
                : ExoticEnergyInputHelper.getMaxWorkingInputAmpsMulti(getExoticAndNormalEnergyHatchList()));
        logic.setAmperageOC(false);
    }

    protected long getMachineVoltageLimit() {
        return GTValues.V[energyHatchTier];
    }

    protected int checkEnergyHatchTier() {
        int tier = 0;
        for (MTEHatchEnergy tHatch : validMTEList(mEnergyHatches)) {
            tier = Math.max(tHatch.mTier, tier);
        }
        for (MTEHatch tHatch : validMTEList(mExoticEnergyHatches)) {
            tier = Math.max(tHatch.mTier, tier);
        }
        return tier;
    }

    @Override
    public long getMaxInputAmps() {
        return getMaxInputAmpsHatch(getExoticAndNormalEnergyHatchList());
    }

    public static long getMaxInputAmpsHatch(Collection<? extends MTEHatch> hatches) {
        List<Long> ampsList = new ArrayList<>();
        for (MTEHatch tHatch : validMTEList(hatches)) {
            long currentAmp = tHatch.getBaseMetaTileEntity()
                .getInputAmperage();
            ampsList.add(currentAmp);
        }

        if (ampsList.isEmpty()) {
            return 0L;
        }

        return Collections.max(ampsList);
    }
}
