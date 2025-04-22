package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.common.block.Casings.BasicBlocks.MetaCasing;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTStructureUtility.ofFrame;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.machine.multiMachineClasses.GTMMultiMachineBase;
import com.science.gtnl.common.recipe.RecipeRegister;

import gregtech.api.enums.Materials;
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
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import gregtech.common.blocks.BlockCasings1;

public class WoodDistillation extends GTMMultiMachineBase<WoodDistillation> implements ISurvivalConstructable {

    public static final int CASING_INDEX = ((BlockCasings1) sBlockCasings1).getTextureIndex(11);
    public int mCasing;
    public static IStructureDefinition<WoodDistillation> STRUCTURE_DEFINITION = null;
    public static final String STRUCTURE_PIECE_MAIN = "main";
    public static final String WD_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/wood_distillation";
    public static String[][] shape = StructureUtils.readStructureFromFile(WD_STRUCTURE_FILE_PATH);
    public final int horizontalOffSet = 11;
    public final int verticalOffSet = 18;
    public final int depthOffSet = 2;

    public WoodDistillation(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public WoodDistillation(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new WoodDistillation(this.mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_FRONT_OIL_CRACKER_ACTIVE)
                    .extFacing()
                    .build(),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_FRONT_OIL_CRACKER_ACTIVE_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_FRONT_OIL_CRACKER)
                    .extFacing()
                    .build(),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_FRONT_OIL_CRACKER_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }

    @Override
    public int getCasingTextureID() {
        return CASING_INDEX;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeRegister.WoodDistillationRecipes;
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.WoodDistillationRecipeType)
            .addInfo(TextLocalization.Tooltip_WoodDistillation_00)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_02)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_03)
            .addInfo(TextLocalization.Tooltip_Tectech_Hatch)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(23, 20, 15, true)
            .addInputHatch(TextLocalization.Tooltip_WoodDistillation_Casing)
            .addInputBus(TextLocalization.Tooltip_WoodDistillation_Casing)
            .addOutputBus(TextLocalization.Tooltip_WoodDistillation_Casing)
            .addEnergyHatch(TextLocalization.Tooltip_WoodDistillation_Casing)
            .addMaintenanceHatch(TextLocalization.Tooltip_WoodDistillation_Casing)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IStructureDefinition<WoodDistillation> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<WoodDistillation>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', ofBlock(MetaCasing, 2))
                .addElement(
                    'B',
                    buildHatchAdder(WoodDistillation.class).casingIndex(CASING_INDEX)
                        .dot(1)
                        .atLeast(InputHatch, OutputHatch, InputBus, OutputBus, Maintenance, Energy.or(ExoticEnergy))
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(sBlockCasings1, 11))))
                .addElement('C', ofBlock(sBlockCasings2, 1))
                .addElement('D', ofBlock(sBlockCasings2, 13))
                .addElement('E', ofBlock(sBlockCasings3, 11))
                .addElement('F', ofBlock(sBlockCasings3, 14))
                .addElement('G', ofBlock(sBlockCasings4, 1))
                .addElement('H', ofBlock(sBlockCasings4, 10))
                .addElement('I', ofBlock(sBlockCasings6, 3))
                .addElement('J', ofFrame(Materials.StainlessSteel))
                .addElement('K', Muffler.newAny(CASING_INDEX, 2))
                .build();
        }
        return STRUCTURE_DEFINITION;
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
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        mCasing = 0;

        if (!checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet) && checkHatch()) {
            return false;
        }

        energyHatchTier = checkEnergyHatchTier();
        return mCasing >= 220 && this.mMufflerHatches.size() == 2;
    }

    @Override
    public ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @NotNull
            @Override
            public OverclockCalculator createOverclockCalculator(@NotNull GTRecipe recipe) {
                return super.createOverclockCalculator(recipe).setEUtDiscount(1 - (ParallelTier / 50.0))
                    .setSpeedBoost(1 - (ParallelTier / 200.0));
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

        ItemStack[] outputItems = processingLogic.getOutputItems();
        if (outputItems != null) {
            for (ItemStack itemStack : outputItems) {
                if (itemStack != null) {
                    itemStack.stackSize *= GTUtility.getTier(this.getMaxInputVoltage()) * 2;
                }
            }
        }
        mOutputItems = outputItems;

        // 获取输出流体并进行 null 检查
        FluidStack[] outputFluids = processingLogic.getOutputFluids();
        List<FluidStack> expandedFluids = new ArrayList<>();

        if (outputFluids != null) {
            for (FluidStack fluidStack : outputFluids) {
                if (fluidStack != null) {
                    // 计算放大后的总量
                    long totalAmount = (long) fluidStack.amount * GTUtility.getTier(this.getMaxInputVoltage()) * 2;

                    // 拆分为多个 FluidStack，避免 amount 超过 int 上限
                    while (totalAmount > 0) {
                        int stackSize = (int) Math.min(totalAmount, Integer.MAX_VALUE);
                        expandedFluids.add(new FluidStack(fluidStack.getFluid(), stackSize));
                        totalAmount -= stackSize;
                    }
                }
            }
        }

        mOutputFluids = expandedFluids.toArray(new FluidStack[0]);

        return result;
    }
}
