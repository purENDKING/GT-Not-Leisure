package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;
import static com.science.gtnl.common.block.Casings.BasicBlocks.MetaCasing;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.machine.multiMachineClasses.GTMMultiMachineBase;

import gregtech.api.GregTechAPI;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import gregtech.common.blocks.BlockCasings8;
import gregtech.common.blocks.BlockCasings9;

public class ReactionFurnace extends GTMMultiMachineBase<ReactionFurnace> implements ISurvivalConstructable {

    private static final long RECIPE_EUT = 4;
    private static final int RECIPE_DURATION = 128;
    public static final String STRUCTURE_PIECE_MAIN = "main";
    private static IStructureDefinition<ReactionFurnace> STRUCTURE_DEFINITION = null;
    public static final String RF_STRUCTURE_FILE_PATH = RESOURCE_ROOT_ID + ":" + "multiblock/reaction_furnace";
    public static final int CASING_INDEX = ((BlockCasings8) GregTechAPI.sBlockCasings8).getTextureIndex(7);
    public final int horizontalOffSet = 15;
    public final int verticalOffSet = 18;
    public final int depthOffSet = 3;
    public static String[][] shape = StructureUtils.readStructureFromFile(RF_STRUCTURE_FILE_PATH);

    public ReactionFurnace(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public ReactionFurnace(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new ReactionFurnace(this.mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_DTPF_ON)
                    .extFacing()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_DTPF_OFF)
                    .extFacing()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }

    @Override
    public int getCasingTextureID() {
        return ((BlockCasings9) GregTechAPI.sBlockCasings9).getTextureIndex(11);
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeMaps.furnaceRecipes;
    }

    @NotNull
    @Override
    public Collection<RecipeMap<?>> getAvailableRecipeMaps() {
        return Arrays.asList(RecipeMaps.furnaceRecipes);
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.ReactionFurnaceRecipeType)
            .addInfo(TextLocalization.Tooltip_ReactionFurnace_00)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_00)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_01)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_02)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_03)
            .addInfo(TextLocalization.Tooltip_Tectech_Hatch)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(31, 21, 29, true)
            .addInputBus(TextLocalization.Tooltip_ReactionFurnace_Casing)
            .addOutputBus(TextLocalization.Tooltip_ReactionFurnace_Casing)
            .addEnergyHatch(TextLocalization.Tooltip_ReactionFurnace_Casing)
            .addMaintenanceHatch(TextLocalization.Tooltip_ReactionFurnace_Casing)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IStructureDefinition<ReactionFurnace> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<ReactionFurnace>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', ofBlock(sBlockCasings9, 11))
                .addElement('B', ofBlock(MetaCasing, 14))
                .addElement('C', ofBlock(sBlockCasings9, 7))
                .addElement('D', ofBlock(sBlockCasings10, 3))
                .addElement('E', ofBlock(sBlockCasings8, 10))
                .addElement(
                    'F',
                    buildHatchAdder(ReactionFurnace.class).casingIndex(CASING_INDEX)
                        .dot(1)
                        .atLeast(InputBus, OutputBus, Maintenance, Energy.or(ExoticEnergy))
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(sBlockCasings8, 7))))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        mCasing = 0;
        ParallelTier = 0;

        if (!checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet) && checkHatch()) {
            return false;
        }

        energyHatchTier = checkEnergyHatchTier();
        ParallelTier = getParallelTier(aStack);
        return mCasing >= 115;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, horizontalOffSet, verticalOffSet, depthOffSet);
    }

    @Override
    @NotNull
    public CheckRecipeResult checkProcessing() {
        ItemStack controllerItem = getControllerSlot();
        this.ParallelTier = getParallelTier(controllerItem);
        ArrayList<ItemStack> tInputList = getAllStoredInputs();
        if (tInputList.isEmpty()) return CheckRecipeResultRegistry.NO_RECIPE;

        int fakeOriginalMaxParallel = 1;
        OverclockCalculator calculator = new OverclockCalculator().setEUt(getAverageInputVoltage())
            .setAmperage(getMaxInputAmps())
            .setRecipeEUt(RECIPE_EUT)
            .setDuration(RECIPE_DURATION)
            .setAmperageOC(mEnergyHatches.size() != 1)
            .setParallel(fakeOriginalMaxParallel);

        int maxParallel = getMaxParallelRecipes();
        int originalMaxParallel = maxParallel;
        double tickTimeAfterOC = calculator.calculateDurationUnderOneTick();
        if (tickTimeAfterOC < 1) {
            maxParallel = GTUtility.safeInt((long) (maxParallel / tickTimeAfterOC), 0);
        }

        int maxParallelBeforeBatchMode = maxParallel;
        if (isBatchModeEnabled()) {
            maxParallel = GTUtility.safeInt((long) maxParallel * getMaxBatchSize(), 0);
        }

        int currentParallel = 0;
        for (ItemStack item : tInputList) {
            ItemStack smeltedOutput = GTModHandler.getSmeltingOutput(item, false, null);
            if (smeltedOutput != null) {
                if (item.stackSize <= (maxParallel - currentParallel)) {
                    currentParallel += item.stackSize;
                } else {
                    currentParallel = maxParallel;
                    break;
                }
            }
        }
        if (currentParallel <= 0) {
            return CheckRecipeResultRegistry.NO_RECIPE;
        }
        int currentParallelBeforeBatchMode = Math.min(currentParallel, maxParallelBeforeBatchMode);
        int fakeCurrentParallel = (int) Math.ceil((double) currentParallelBeforeBatchMode / originalMaxParallel);

        calculator.setCurrentParallel(fakeCurrentParallel)
            .calculate();

        double batchMultiplierMax = 1;
        // In case batch mode enabled
        if (currentParallel > maxParallelBeforeBatchMode && calculator.getDuration() < getMaxBatchSize()) {
            batchMultiplierMax = (double) getMaxBatchSize() / calculator.getDuration();
            batchMultiplierMax = Math.min(batchMultiplierMax, (double) currentParallel / maxParallelBeforeBatchMode);
        }
        int finalParallel = (int) (batchMultiplierMax * currentParallelBeforeBatchMode);

        // Consume inputs and generate outputs
        ArrayList<ItemStack> smeltedOutputs = new ArrayList<>();
        int remainingCost = finalParallel;
        for (ItemStack item : tInputList) {
            ItemStack smeltedOutput = GTModHandler.getSmeltingOutput(item, false, null);
            if (smeltedOutput != null) {
                if (remainingCost >= item.stackSize) {
                    remainingCost -= item.stackSize;
                    smeltedOutput.stackSize *= item.stackSize;
                    item.stackSize = 0;
                    smeltedOutputs.add(smeltedOutput);
                } else {
                    smeltedOutput.stackSize *= remainingCost;
                    item.stackSize -= remainingCost;
                    smeltedOutputs.add(smeltedOutput);
                    break;
                }
            }
        }
        this.mOutputItems = smeltedOutputs.toArray(new ItemStack[0]);

        this.mEfficiency = 10000 - (getIdealStatus() - getRepairStatus()) * 1000;
        this.mEfficiencyIncrease = 10000;
        this.mMaxProgresstime = 20;
        this.lEUt = calculator.getConsumption();

        if (this.lEUt > 0) this.lEUt = -this.lEUt;

        updateSlots();
        return CheckRecipeResultRegistry.SUCCESSFUL;
    }

    @Override
    public int getMaxParallelRecipes() {
        if (ParallelTier <= 2) {
            return 8;
        } else {
            return (int) Math.pow(4, ParallelTier - 3) * 512 - 1;
        }
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
}
