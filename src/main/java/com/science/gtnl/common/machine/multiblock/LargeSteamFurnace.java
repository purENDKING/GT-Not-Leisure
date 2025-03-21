package com.science.gtnl.common.machine.multiblock;

import static bartworks.system.material.WerkstoffLoader.BWBlockCasings;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.common.block.Casings.BasicBlocks.MetaBlockColumn;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.GregTechAPI.sBlockFrames;
import static gregtech.api.enums.HatchElement.InputBus;
import static gregtech.api.enums.HatchElement.OutputBus;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTUtility.validMTEList;
import static gtPlusPlus.core.block.ModBlocks.blockCustomMachineCasings;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableList;
import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.machine.multiMachineClasses.SteamMultiMachineBase;

import gregtech.api.GregTechAPI;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import gregtech.common.blocks.BlockCasings1;
import gregtech.common.blocks.BlockCasings2;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.MteHatchSteamBusInput;

public class LargeSteamFurnace extends SteamMultiMachineBase<LargeSteamFurnace> implements ISurvivalConstructable {

    public static final int HORIZONTAL_OFF_SET = 7;
    public static final int VERTICAL_OFF_SET = 6;
    public static final int DEPTH_OFF_SET = 1;
    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static IStructureDefinition<LargeSteamFurnace> STRUCTURE_DEFINITION = null;
    private static final String LSF_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/large_steam_furnace"; // 文件路径
    private static final String[][] shape = StructureUtils.readStructureFromFile(LSF_STRUCTURE_FILE_PATH);

    @Override
    public IStructureDefinition<LargeSteamFurnace> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<LargeSteamFurnace>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement(
                    'A',
                    ofChain(
                        buildSteamBigInput(LargeSteamFurnace.class).casingIndex(getCasingTextureID())
                            .dot(1)
                            .build(),
                        buildSteamInput(LargeSteamFurnace.class).casingIndex(getCasingTextureID())
                            .dot(1)
                            .build(),
                        buildHatchAdder(LargeSteamFurnace.class).casingIndex(getCasingTextureID())
                            .dot(1)
                            .atLeast(
                                SteamHatchElement.InputBus_Steam,
                                SteamHatchElement.OutputBus_Steam,
                                InputBus,
                                OutputBus)
                            .buildAndChain(
                                onElementPass(
                                    x -> ++x.tCountCasing,
                                    withChannel(
                                        "tier",
                                        ofBlocksTiered(
                                            LargeSteamFurnace::getTierMachineCasing,
                                            ImmutableList.of(Pair.of(sBlockCasings1, 10), Pair.of(sBlockCasings2, 0)),
                                            -1,
                                            (t, m) -> t.tierMachineCasing = m,
                                            t -> t.tierMachineCasing))))))
                .addElement(
                    'B',
                    ofBlocksTiered(
                        LargeSteamFurnace::getTierPipeCasing,
                        ImmutableList.of(Pair.of(sBlockCasings2, 12), Pair.of(sBlockCasings2, 13)),
                        -1,
                        (t, m) -> t.tierPipeCasing = m,
                        t -> t.tierPipeCasing))
                .addElement(
                    'C',
                    ofBlocksTiered(
                        LargeSteamFurnace::getTierFireboxCasing,
                        ImmutableList.of(Pair.of(sBlockCasings3, 13), Pair.of(sBlockCasings3, 14)),
                        -1,
                        (t, m) -> t.tierFireboxCasing = m,
                        t -> t.tierFireboxCasing))
                .addElement(
                    'D',
                    ofBlocksTiered(
                        LargeSteamFurnace::getTierFrameCasing,
                        ImmutableList.of(Pair.of(sBlockFrames, 300), Pair.of(sBlockFrames, 305)),
                        -1,
                        (t, m) -> t.tierFrameCasing = m,
                        t -> t.tierFrameCasing))
                .addElement(
                    'E',
                    ofBlocksTiered(
                        LargeSteamFurnace::getTierPlatedCasing,
                        ImmutableList.of(Pair.of(blockCustomMachineCasings, 0), Pair.of(sBlockCasings2, 0)),
                        -1,
                        (t, m) -> t.tierPlatedCasing = m,
                        t -> t.tierPlatedCasing))
                .addElement(
                    'F',
                    ofBlocksTiered(
                        LargeSteamFurnace::getTierBrickCasing,
                        ImmutableList.of(Pair.of(MetaBlockColumn, 0), Pair.of(MetaBlockColumn, 1)),
                        -1,
                        (t, m) -> t.tierBrickCasing = m,
                        t -> t.tierBrickCasing))
                .addElement('G', ofBlock(Blocks.stonebrick, 0))
                .addElement(
                    'H',
                    ofBlocksTiered(
                        LargeSteamFurnace::getTierAdvancedCasing,
                        ImmutableList.of(Pair.of(BWBlockCasings, 32066), Pair.of(BWBlockCasings, 32071)),
                        -1,
                        (t, m) -> t.tierAdvancedCasing = m,
                        t -> t.tierAdvancedCasing))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    public LargeSteamFurnace(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public LargeSteamFurnace(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new LargeSteamFurnace(this.mName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.LargeSteamFurnaceRecipeType)
            .addInfo(TextLocalization.Tooltip_LargeSteamFurnace_00)
            .addInfo(TextLocalization.Tooltip_LargeSteamFurnace_01)
            .addInfo(TextLocalization.Tooltip_LargeSteamFurnace_02)
            .addInfo(TextLocalization.HIGH_PRESSURE_TOOLTIP_NOTICE)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(9, 8, 10, false)
            .addInputBus(TextLocalization.Tooltip_LargeSteamFurnace_Casing, 1)
            .addOutputBus(TextLocalization.Tooltip_LargeSteamFurnace_Casing, 1)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        int id = tierMachine == 2 ? ((BlockCasings2) GregTechAPI.sBlockCasings2).getTextureIndex(0)
            : ((BlockCasings1) GregTechAPI.sBlockCasings1).getTextureIndex(10);
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(id), TextureFactory.builder()
                .addIcon(Textures.BlockIcons.OVERLAY_FRONT_STEAM_FURNACE_ACTIVE)
                .extFacing()
                .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(id), TextureFactory.builder()
                .addIcon(Textures.BlockIcons.OVERLAY_FRONT_STEAM_FURNACE)
                .extFacing()
                .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(id) };
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeMaps.furnaceRecipes;
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {

        return new ProcessingLogic() {

            @NotNull
            @Override
            protected CheckRecipeResult validateRecipe(@Nonnull GTRecipe recipe) {
                if (availableVoltage < recipe.mEUt) {
                    return CheckRecipeResultRegistry.insufficientPower(recipe.mEUt);
                } else return CheckRecipeResultRegistry.SUCCESSFUL;
            }

            @Override
            @Nonnull
            protected OverclockCalculator createOverclockCalculator(@NotNull GTRecipe recipe) {
                return OverclockCalculator.ofNoOverclock(recipe)
                    .setEUtDiscount(0.5 * tierMachine)
                    .setSpeedBoost(1 / 10 / tierMachine);
            }
        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        tierMachine = 0;
        tierPipeCasing = -1;
        tierMachineCasing = -1;
        tierFrameCasing = -1;
        tierPlatedCasing = -1;
        tierFireboxCasing = -1;
        tierAdvancedCasing = -1;
        tierBrickCasing = -1;
        tCountCasing = 0;
        if (!checkPiece(STRUCTURE_PIECE_MAIN, HORIZONTAL_OFF_SET, VERTICAL_OFF_SET, DEPTH_OFF_SET)) return false;
        if (tierPipeCasing < 0 && tierMachineCasing < 0
            && tierFrameCasing < 0
            && tierPlatedCasing < 0
            && tierBrickCasing < 0
            && tierFireboxCasing < 0
            && tierAdvancedCasing < 0) {
            updateHatchTexture();
            return false;
        }
        if (tierPipeCasing == 1 && tierMachineCasing == 1
            && tierFrameCasing == 1
            && tierPlatedCasing == 1
            && tierBrickCasing == 1
            && tierFireboxCasing == 1
            && tierAdvancedCasing == 1
            && tCountCasing >= 100
            && checkHatches()) {
            tierMachine = 1;
            getCasingTextureID();
            updateHatchTexture();
            return true;
        }
        if (tierPipeCasing == 2 && tierMachineCasing == 2
            && tierFrameCasing == 2
            && tierPlatedCasing == 2
            && tierBrickCasing == 2
            && tierFireboxCasing == 2
            && tierAdvancedCasing == 2
            && tCountCasing >= 100
            && checkHatches()) {
            tierMachine = 2;
            getCasingTextureID();
            updateHatchTexture();
            return true;
        }
        getCasingTextureID();
        updateHatchTexture();
        return false;
    }

    @Override
    public int getMaxParallelRecipes() {
        if (tierMachine == 1) {
            return 128;
        } else if (tierMachine == 2) {
            return 256;
        }
        return 128;
    }

    @Override
    public String getMachineType() {
        return TextLocalization.LargeSteamFurnaceRecipeType;
    }

    @Override
    public int getTierRecipes() {
        return 3;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        this.buildPiece(
            STRUCTURE_PIECE_MAIN,
            stackSize,
            hintsOnly,
            HORIZONTAL_OFF_SET,
            VERTICAL_OFF_SET,
            DEPTH_OFF_SET);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (this.mMachine) return -1;
        return this.survivialBuildPiece(
            STRUCTURE_PIECE_MAIN,
            stackSize,
            HORIZONTAL_OFF_SET,
            VERTICAL_OFF_SET,
            DEPTH_OFF_SET,
            elementBudget,
            env,
            false,
            true);
    }

    @Override
    @NotNull
    public CheckRecipeResult checkProcessing() {
        ArrayList<ItemStack> tInputList = getAllStoredInputs();
        ArrayList<ItemStack> rList = new ArrayList<>();
        for (MteHatchSteamBusInput tHatch : validMTEList(mSteamInputs)) {
            tHatch.mRecipeMap = getRecipeMap();
            for (int i = tHatch.getBaseMetaTileEntity()
                .getSizeInventory() - 1; i >= 0; i--) {
                ItemStack stack = tHatch.getBaseMetaTileEntity()
                    .getStackInSlot(i);
                if (stack != null) {
                    rList.add(stack);
                }
            }
        }
        tInputList.addAll(rList);

        if (tInputList.isEmpty()) {
            return CheckRecipeResultRegistry.NO_RECIPE;
        }

        int fakeOriginalMaxParallel = 1;
        OverclockCalculator calculator = new OverclockCalculator().setEUt(getAverageInputVoltage())
            .setAmperage(getMaxInputAmps())
            .setRecipeEUt(4)
            .setDuration(32)
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

        // Calculate parallel
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
            if (smeltedOutput != null && remainingCost > 0) {
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

        this.mEfficiency = 10000;
        this.mEfficiencyIncrease = 10000;
        this.mMaxProgresstime = (int) (calculator.getDuration() * batchMultiplierMax);
        this.lEUt = calculator.getConsumption();

        if (this.lEUt > 0) this.lEUt = -this.lEUt;

        updateSlots();
        return CheckRecipeResultRegistry.SUCCESSFUL;
    }
}
