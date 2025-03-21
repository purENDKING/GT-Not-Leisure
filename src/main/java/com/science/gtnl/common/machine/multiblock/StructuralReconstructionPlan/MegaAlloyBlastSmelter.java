package com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTStructureUtility.ofCoil;
import static gtPlusPlus.core.block.ModBlocks.blockCasingsMisc;
import static gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock.oMCDAlloyBlastSmelter;
import static gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock.oMCDAlloyBlastSmelterActive;

import java.util.Random;

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
import com.science.gtnl.common.machine.multiMachineClasses.GTMMultiMachineBase;

import bartworks.API.BorosilicateGlass;
import bartworks.util.BWUtil;
import gregtech.api.enums.HeatingCoilLevel;
import gregtech.api.enums.TAE;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import gtPlusPlus.api.recipe.GTPPRecipeMaps;

public class MegaAlloyBlastSmelter extends GTMMultiMachineBase<MegaAlloyBlastSmelter>
    implements ISurvivalConstructable {

    public static final String STRUCTURE_PIECE_MAIN = "main";
    private static IStructureDefinition<MegaAlloyBlastSmelter> STRUCTURE_DEFINITION = null;
    public static final String MABS_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/mega_alloy_blast_smelter";
    public static final int CASING_INDEX = TAE.GTPP_INDEX(15);
    private int mHeatingCapacity = 0;
    private HeatingCoilLevel heatLevel;
    public final int horizontalOffSet = 5;
    public final int verticalOffSet = 15;
    public final int depthOffSet = 0;
    public byte glassTier = 0;
    public static String[][] shape = StructureUtils.readStructureFromFile(MABS_STRUCTURE_FILE_PATH);

    public MegaAlloyBlastSmelter(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public MegaAlloyBlastSmelter(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new MegaAlloyBlastSmelter(this.mName);
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
        tt.addMachineType(TextLocalization.MegaAlloyBlastSmelterRecipeType)
            .addInfo(TextLocalization.Tooltip_MegaAlloyBlastSmelter_00)
            .addInfo(TextLocalization.Tooltip_MegaAlloyBlastSmelter_01)
            .addInfo(TextLocalization.Tooltip_MegaAlloyBlastSmelter_02)
            .addInfo(TextLocalization.Tooltip_MegaAlloyBlastSmelter_03)
            .addInfo(TextLocalization.Tooltip_MegaAlloyBlastSmelter_04)
            .addInfo(TextLocalization.Tooltip_MegaAlloyBlastSmelter_05)
            .addInfo(TextLocalization.Tooltip_PerfectOverclock)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_02)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_03)
            .addInfo(TextLocalization.Tooltip_Tectech_Hatch)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(11, 18, 11, true)
            .addInputHatch(TextLocalization.Tooltip_MegaAlloyBlastSmelter_Casing)
            .addOutputHatch(TextLocalization.Tooltip_MegaAlloyBlastSmelter_Casing)
            .addInputBus(TextLocalization.Tooltip_MegaAlloyBlastSmelter_Casing)
            .addOutputBus(TextLocalization.Tooltip_MegaAlloyBlastSmelter_Casing)
            .addEnergyHatch(TextLocalization.Tooltip_MegaAlloyBlastSmelter_Casing)
            .addMaintenanceHatch(TextLocalization.Tooltip_MegaAlloyBlastSmelter_Casing)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IStructureDefinition<MegaAlloyBlastSmelter> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<MegaAlloyBlastSmelter>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement(
                    'A',
                    withChannel(
                        "glass",
                        BorosilicateGlass.ofBoroGlass(
                            (byte) 0,
                            (byte) 1,
                            Byte.MAX_VALUE,
                            (te, t) -> te.glassTier = t,
                            te -> te.glassTier)))
                .addElement('B', ofBlock(sBlockCasings2, 15))
                .addElement('C', ofBlock(sBlockCasings3, 14))
                .addElement('D', ofBlock(sBlockCasings3, 15))
                .addElement('E', ofBlock(sBlockCasings4, 3))
                .addElement('F', ofCoil(MegaAlloyBlastSmelter::setCoilLevel, MegaAlloyBlastSmelter::getCoilLevel))
                .addElement('G', ofBlock(sBlockCasings8, 4))
                .addElement('H', ofBlock(blockCasingsMisc, 14))
                .addElement(
                    'I',
                    buildHatchAdder(MegaAlloyBlastSmelter.class).casingIndex(CASING_INDEX)
                        .dot(1)
                        .atLeast(InputHatch, OutputHatch, InputBus, OutputBus, Maintenance, Energy.or(ExoticEnergy))
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(blockCasingsMisc, 15))))
                .addElement('J', Muffler.newAny(CASING_INDEX, 1))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        mCasing = 0;
        ParallelTier = 0;

        if (checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet) && checkHatch()
            && mEnergyHatches.size() <= 1
            && mMufflerHatches.size() == 1
            && mCasing >= 290) {
            energyHatchTier = checkEnergyHatchTier();
            ParallelTier = getParallelTier(aStack);
            this.mHeatingCapacity = (int) this.getCoilLevel()
                .getHeat() + 100 * (BWUtil.getTier(this.getMaxInputEu()) - 2);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isEnablePerfectOverclock() {
        return true;
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
                return super.createOverclockCalculator(recipe).setRecipeEUt(recipe.mEUt)
                    .setAmperage(availableAmperage)
                    .setEUt(availableVoltage)
                    .setDuration(recipe.mDuration)
                    .setMachineHeat(MegaAlloyBlastSmelter.this.mHeatingCapacity)
                    .setHeatOC(true)
                    .setHeatDiscount(false)
                    .setAmperageOC(true)
                    .setDurationDecreasePerOC(4)
                    .setEUtIncreasePerOC(4)
                    .setEUtDiscount(Math.max(0.005, 0.8 - (ParallelTier / 50.0)))
                    .setSpeedBoost(Math.max(0.005, 1 / 2 - (ParallelTier / 200.0)));
            }

        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }

    @Nonnull
    @Override
    public CheckRecipeResult checkProcessing() {
        ItemStack controllerItem = getControllerSlot();
        this.ParallelTier = getParallelTier(controllerItem);
        if (processingLogic == null) {
            return checkRecipe(mInventory[1]) ? CheckRecipeResultRegistry.SUCCESSFUL
                : CheckRecipeResultRegistry.NO_RECIPE;
        }

        setupProcessingLogic(processingLogic);

        CheckRecipeResult result = doCheckRecipe();
        result = postCheckRecipe(result, processingLogic);
        updateSlots();
        if (!result.wasSuccessful()) return result;

        mEfficiency = 10000;
        mEfficiencyIncrease = 10000;
        mMaxProgresstime = processingLogic.getDuration();
        setEnergyUsage(processingLogic);

        Random random = new Random();

        ItemStack[] outputItems = processingLogic.getOutputItems();
        if (outputItems != null) {
            for (ItemStack itemStack : outputItems) {
                if (itemStack != null) {
                    if (random.nextInt(101) < (glassTier * 2)) {
                        itemStack.stackSize *= 2;
                    }
                }
            }
        }
        mOutputItems = outputItems;

        FluidStack[] outputFluids = processingLogic.getOutputFluids();
        if (outputFluids != null) {
            for (FluidStack fluidStack : outputFluids) {
                if (fluidStack != null) {
                    if (random.nextInt(101) < (glassTier * 2)) {
                        fluidStack.amount *= 2;
                    }
                }
            }
        }
        mOutputFluids = outputFluids;

        return result;
    }
}
