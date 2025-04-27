package com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;
import static gregtech.api.GregTechAPI.sBlockCasings2;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_MULTI_CANNER;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_MULTI_CANNER_ACTIVE;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gtPlusPlus.core.block.ModBlocks.blockCasings2Misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
import com.science.gtnl.config.MainConfig;

import ggfab.api.GGFabRecipeMaps;
import gregtech.api.enums.TAE;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.metatileentity.implementations.MTEHatchInput;
import gregtech.api.metatileentity.implementations.MTEHatchInputBus;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import gregtech.common.tileentities.machines.IDualInputHatch;
import gregtech.common.tileentities.machines.IDualInputInventory;
import gregtech.common.tileentities.machines.MTEHatchCraftingInputME;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.MTEHatchSolidifier;
import tectech.thing.metaTileEntity.hatch.MTEHatchEnergyTunnel;

public class LargeSolidifier extends GTMMultiMachineBase<LargeSolidifier> implements ISurvivalConstructable {

    public static final String STRUCTURE_PIECE_MAIN = "main";
    private static IStructureDefinition<LargeSolidifier> STRUCTURE_DEFINITION = null;
    public static final String LS_STRUCTURE_FILE_PATH = RESOURCE_ROOT_ID + ":" + "multiblock/large_solidifier";
    public static final int CASING_INDEX = TAE.GTPP_INDEX(11);
    public final int horizontalOffSet = 2;
    public final int verticalOffSet = 2;
    public final int depthOffSet = 0;
    public static String[][] shape = StructureUtils.readStructureFromFile(LS_STRUCTURE_FILE_PATH);

    public LargeSolidifier(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public LargeSolidifier(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new LargeSolidifier(this.mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_MULTI_CANNER_ACTIVE)
                    .extFacing()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_MULTI_CANNER)
                    .extFacing()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }

    @Override
    public int getCasingTextureID() {
        return CASING_INDEX;
    }

    @Override
    public @NotNull Collection<RecipeMap<?>> getAvailableRecipeMaps() {
        return Arrays.asList(RecipeMaps.fluidSolidifierRecipes, GGFabRecipeMaps.toolCastRecipes);
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeMaps.fluidSolidifierRecipes;
    }

    @Override
    public ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            RecipeMap<?> currentRecipeMap = RecipeMaps.fluidSolidifierRecipes;

            @Override
            protected RecipeMap<?> preProcess() {
                lastRecipeMap = currentRecipeMap;

                if (maxParallelSupplier != null) {
                    maxParallel = maxParallelSupplier.get();
                }
                return currentRecipeMap;
            }

            @NotNull
            @Override
            public CheckRecipeResult process() {
                currentRecipeMap = RecipeMaps.fluidSolidifierRecipes;
                CheckRecipeResult result = super.process();
                if (result.wasSuccessful()) return result;

                currentRecipeMap = GGFabRecipeMaps.toolCastRecipes;
                return super.process();
            }

            @NotNull
            @Override
            public OverclockCalculator createOverclockCalculator(@NotNull GTRecipe recipe) {
                return super.createOverclockCalculator(recipe).setEUtDiscount(0.8 - (ParallelTier / 50.0))
                    .setSpeedBoost(Math.max(0.05, 1 / 4.0 - (ParallelTier / 200.0)));
            }

        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }

    @NotNull
    @Override
    protected CheckRecipeResult doCheckRecipe() {
        CheckRecipeResult result = CheckRecipeResultRegistry.NO_RECIPE;

        // check crafting input hatches first
        if (supportsCraftingMEBuffer()) {
            for (IDualInputHatch dualInputHatch : mDualInputHatches) {
                for (var it = dualInputHatch.inventories(); it.hasNext();) {
                    IDualInputInventory slot = it.next();
                    processingLogic.setInputItems(slot.getItemInputs());
                    processingLogic.setInputFluids(slot.getFluidInputs());
                    CheckRecipeResult foundResult = processingLogic.process();
                    if (foundResult.wasSuccessful()) {
                        return foundResult;
                    }
                    if (foundResult != CheckRecipeResultRegistry.NO_RECIPE) {
                        // Recipe failed in interesting way, so remember that and continue searching
                        result = foundResult;
                    }
                }
            }
        }

        // Logic for GT_MetaTileEntity_Hatch_Solidifier
        for (MTEHatchInput solidifierHatch : mInputHatches) {
            if (solidifierHatch instanceof MTEHatchSolidifier hatch) {
                ItemStack mold = hatch.getMold();
                FluidStack fluid = solidifierHatch.getFluid();

                if (mold != null && fluid != null) {
                    List<ItemStack> inputItems = new ArrayList<>();
                    inputItems.add(mold);

                    processingLogic.setInputItems(inputItems.toArray(new ItemStack[0]));
                    processingLogic.setInputFluids(fluid);

                    CheckRecipeResult foundResult = processingLogic.process();
                    if (foundResult.wasSuccessful()) {
                        return foundResult;
                    }
                    if (foundResult != CheckRecipeResultRegistry.NO_RECIPE) {
                        // Recipe failed in interesting way, so remember that and continue searching
                        result = foundResult;
                    }
                }
            }
        }

        processingLogic.clear();
        processingLogic.setInputFluids(getStoredFluids());
        // Default logic
        for (MTEHatchInputBus bus : mInputBusses) {
            if (bus instanceof MTEHatchCraftingInputME) {
                continue;
            }
            List<ItemStack> inputItems = new ArrayList<>();
            for (int i = bus.getSizeInventory() - 1; i >= 0; i--) {
                ItemStack stored = bus.getStackInSlot(i);
                if (stored != null) {
                    inputItems.add(stored);
                }
            }
            if (canUseControllerSlotForRecipe() && getControllerSlot() != null) {
                inputItems.add(getControllerSlot());
            }
            processingLogic.setInputItems(inputItems.toArray(new ItemStack[0]));
            CheckRecipeResult foundResult = processingLogic.process();
            if (foundResult.wasSuccessful()) {
                return foundResult;
            }
            if (foundResult != CheckRecipeResultRegistry.NO_RECIPE) {
                // Recipe failed in interesting way, so remember that and continue searching
                result = foundResult;
            }
        }
        return result;
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.LargeSolidifierRecipeType)
            .addInfo(TextLocalization.Tooltip_LargeSolidifier_00)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_01)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_02)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_03)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_04)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(5, 4, 5, true)
            .addInputHatch(TextLocalization.Tooltip_LargeSolidifier_Casing)
            .addOutputHatch(TextLocalization.Tooltip_LargeSolidifier_Casing)
            .addInputBus(TextLocalization.Tooltip_LargeSolidifier_Casing)
            .addOutputBus(TextLocalization.Tooltip_LargeSolidifier_Casing)
            .addEnergyHatch(TextLocalization.Tooltip_LargeSolidifier_Casing)
            .addMaintenanceHatch(TextLocalization.Tooltip_LargeSolidifier_Casing)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IStructureDefinition<LargeSolidifier> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<LargeSolidifier>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', ofBlock(sBlockCasings2, 13))
                .addElement(
                    'B',
                    buildHatchAdder(LargeSolidifier.class).casingIndex(CASING_INDEX)
                        .dot(1)
                        .atLeast(InputHatch, OutputHatch, InputBus, OutputBus, Maintenance, Energy.or(ExoticEnergy))
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(blockCasings2Misc, 4))))
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
        if (MainConfig.enableMachineAmpLimit) {
            for (MTEHatch hatch : getExoticEnergyHatches()) {
                if (hatch instanceof MTEHatchEnergyTunnel) {
                    return false;
                }
            }
            if (getMaxInputAmps() > 64) return false;
        }

        ParallelTier = getParallelTier(aStack);
        return mCasing >= 45;
    }

    public boolean checkHatch() {
        return mMaintenanceHatches.size() <= 1 && (this.getPollutionPerSecond(null) <= 0 || !mMufflerHatches.isEmpty());
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
}
