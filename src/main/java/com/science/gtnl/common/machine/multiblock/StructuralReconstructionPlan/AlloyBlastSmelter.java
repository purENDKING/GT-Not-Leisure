package com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.*;
import static gregtech.api.util.GTUtility.validMTEList;
import static gtPlusPlus.core.block.ModBlocks.blockCasingsMisc;
import static gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock.*;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.machine.multiMachineClasses.GTMMultiMachineBase;

import bartworks.util.BWUtil;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.HeatingCoilLevel;
import gregtech.api.enums.TAE;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.metatileentity.implementations.MTEHatchEnergy;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import gtPlusPlus.api.recipe.GTPPRecipeMaps;
import tectech.thing.metaTileEntity.hatch.MTEHatchEnergyTunnel;

public class AlloyBlastSmelter extends GTMMultiMachineBase<AlloyBlastSmelter> implements ISurvivalConstructable {

    public static final String STRUCTURE_PIECE_MAIN = "main";
    private static IStructureDefinition<AlloyBlastSmelter> STRUCTURE_DEFINITION = null;
    public static final String LIL_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/alloy_blast_smelter";
    public static final int CASING_INDEX = TAE.GTPP_INDEX(15);
    private int mHeatingCapacity = 0;
    protected int energyHatchTier;
    private HeatingCoilLevel heatLevel;
    public final int horizontalOffSet = 2;
    public final int verticalOffSet = 4;
    public final int depthOffSet = 0;
    public static String[][] shape;

    public AlloyBlastSmelter(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        shape = StructureUtils.readStructureFromFile(LIL_STRUCTURE_FILE_PATH);
    }

    public AlloyBlastSmelter(String aName) {
        super(aName);
        shape = StructureUtils.readStructureFromFile(LIL_STRUCTURE_FILE_PATH);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new AlloyBlastSmelter(this.mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(oMCDAlloyBlastSmelterActive)
                    .extFacing()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(oMCDAlloyBlastSmelter)
                    .extFacing()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }

    public int getCasingTextureID() {
        return CASING_INDEX;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return GTPPRecipeMaps.alloyBlastSmelterRecipes;
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.AlloyBlastSmelterRecipeType)
            .addInfo(TextLocalization.Tooltip_AlloyBlastSmelter_00)
            .addInfo(TextLocalization.Tooltip_AlloyBlastSmelter_01)
            .addInfo(TextLocalization.Tooltip_AlloyBlastSmelter_02)
            .addInfo(TextLocalization.Tooltip_AlloyBlastSmelter_03)
            .addInfo(TextLocalization.Tooltip_AlloyBlastSmelter_04)
            .addInfo(TextLocalization.Tooltip_AlloyBlastSmelter_05)
            .addInfo(TextLocalization.Tooltip_AlloyBlastSmelter_06)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_04)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(5, 5, 5, true)
            .addInputHatch(TextLocalization.Tooltip_AlloyBlastSmelter_Casing)
            .addOutputHatch(TextLocalization.Tooltip_AlloyBlastSmelter_Casing)
            .addInputBus(TextLocalization.Tooltip_AlloyBlastSmelter_Casing)
            .addOutputBus(TextLocalization.Tooltip_AlloyBlastSmelter_Casing)
            .addEnergyHatch(TextLocalization.Tooltip_AlloyBlastSmelter_Casing)
            .addMaintenanceHatch(TextLocalization.Tooltip_AlloyBlastSmelter_Casing)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IStructureDefinition<AlloyBlastSmelter> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<AlloyBlastSmelter>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', ofCoil(AlloyBlastSmelter::setCoilLevel, AlloyBlastSmelter::getCoilLevel))
                .addElement('B', ofBlock(blockCasingsMisc, 14))
                .addElement(
                    'C',
                    buildHatchAdder(AlloyBlastSmelter.class).casingIndex(CASING_INDEX)
                        .dot(1)
                        .atLeast(InputHatch, OutputHatch, InputBus, OutputBus, Maintenance, Energy.or(ExoticEnergy))
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(blockCasingsMisc, 15))))
                .addElement('D', Muffler.newAny(CASING_INDEX, 1))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        mCasing = 0;
        ParallelTier = 0;
        this.energyHatchTier = 0;

        if (checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet) && checkHatch()
            && mEnergyHatches.size() <= 1
            && mMufflerHatches.size() == 1
            && mCasing >= 55) {
            ParallelTier = getParallelTier(aStack);
            energyHatchTier = checkEnergyHatchTier();
            for (MTEHatch hatch : getExoticEnergyHatches()) {
                if (hatch instanceof MTEHatchEnergyTunnel) {
                    return false;
                }
            }
            this.mHeatingCapacity = (int) this.getCoilLevel()
                .getHeat() + 100 * (BWUtil.getTier(this.getMaxInputEu()) - 2);
            return true;
        } else {
            return false;
        }
    }

    public HeatingCoilLevel getCoilLevel() {
        return this.heatLevel;
    }

    public void setCoilLevel(HeatingCoilLevel level) {
        this.heatLevel = level;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, horizontalOffSet, verticalOffSet, depthOffSet);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (mMachine) return -1;
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

    @Override
    public ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @Nonnull
            @Override
            protected OverclockCalculator createOverclockCalculator(@Nonnull GTRecipe recipe) {
                return super.createOverclockCalculator(recipe).setMachineHeat(AlloyBlastSmelter.this.mHeatingCapacity)
                    .setHeatOC(true)
                    .setHeatDiscount(false)
                    .setEUtDiscount(1 - (ParallelTier / 50.0))
                    .setSpeedBoost(1 - (ParallelTier / 200.0));
            }

        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }

    @Override
    protected void setProcessingLogicPower(ProcessingLogic logic) {
        boolean useSingleAmp = mEnergyHatches.size() == 1 && mExoticEnergyHatches.isEmpty();
        logic.setAvailableVoltage(getMachineVoltageLimit());
        logic.setAvailableAmperage(useSingleAmp ? 1 : getMaxInputAmps());
        logic.setAmperageOC(true);
    }

    public long getMachineVoltageLimit() {
        return GTValues.V[energyHatchTier];
    }

    private int checkEnergyHatchTier() {
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
    public int getMaxParallelRecipes() {
        return 16;
    }
}
