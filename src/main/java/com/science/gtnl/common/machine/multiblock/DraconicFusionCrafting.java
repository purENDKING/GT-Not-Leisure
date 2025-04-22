package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.common.block.Casings.BasicBlocks.MetaCasing;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_TELEPORTER;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_TELEPORTER_ACTIVE;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableList;
import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.machine.multiMachineClasses.GTMMultiMachineBase;

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
import gregtech.common.blocks.BlockCasings10;
import kubatech.loaders.BlockLoader;
import kubatech.loaders.DEFCRecipes;
import tectech.thing.casing.TTCasingsContainer;

public class DraconicFusionCrafting extends GTMMultiMachineBase<DraconicFusionCrafting>
    implements ISurvivalConstructable {

    public static final String STRUCTURE_PIECE_MAIN = "main";
    private static IStructureDefinition<DraconicFusionCrafting> STRUCTURE_DEFINITION = null;
    public static final String DFC_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/draconic_fusion_crafting";
    public static final int CASING_INDEX = ((BlockCasings10) sBlockCasings10).getTextureIndex(12);
    public final int horizontalOffSet = 14;
    public final int verticalOffSet = 33;
    public final int depthOffSet = 5;
    public static String[][] shape = StructureUtils.readStructureFromFile(DFC_STRUCTURE_FILE_PATH);
    public int tierCasing = -1;

    public DraconicFusionCrafting(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public DraconicFusionCrafting(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new DraconicFusionCrafting(this.mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(OVERLAY_TELEPORTER_ACTIVE)
                    .extFacing()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(OVERLAY_TELEPORTER)
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
    public RecipeMap<?> getRecipeMap() {
        return DEFCRecipes.fusionCraftingRecipes;
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.DraconicFusionCraftingRecipeType)
            .addInfo(TextLocalization.Tooltip_DraconicFusionCrafting_00)
            .addInfo(TextLocalization.Tooltip_DraconicFusionCrafting_01)
            .addInfo(TextLocalization.Tooltip_DraconicFusionCrafting_02)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_02)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_03)
            .addInfo(TextLocalization.Tooltip_Tectech_Hatch)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(29, 36, 29, true)
            .addInputBus(TextLocalization.Tooltip_DraconicFusionCrafting_Casing)
            .addOutputBus(TextLocalization.Tooltip_DraconicFusionCrafting_Casing)
            .addEnergyHatch(TextLocalization.Tooltip_DraconicFusionCrafting_Casing)
            .addMaintenanceHatch(TextLocalization.Tooltip_DraconicFusionCrafting_Casing)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IStructureDefinition<DraconicFusionCrafting> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<DraconicFusionCrafting>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', ofBlock(sBlockCasings9, 11))
                .addElement('B', ofBlock(MetaCasing, 14))
                .addElement(
                    'C',
                    buildHatchAdder(DraconicFusionCrafting.class).casingIndex(CASING_INDEX)
                        .dot(1)
                        .atLeast(InputHatch, OutputHatch, InputBus, OutputBus, Maintenance, Energy.or(ExoticEnergy))
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(sBlockCasings10, 12))))
                .addElement(
                    'D',
                    withChannel(
                        "tiercasing",
                        ofBlocksTiered(
                            DraconicFusionCrafting::getTierCasingFromBlock,
                            ImmutableList.of(
                                Pair.of(BlockLoader.defcCasingBlock, 8),
                                Pair.of(BlockLoader.defcCasingBlock, 9),
                                Pair.of(BlockLoader.defcCasingBlock, 10),
                                Pair.of(BlockLoader.defcCasingBlock, 11),
                                Pair.of(BlockLoader.defcCasingBlock, 12),
                                Pair.of(TTCasingsContainer.SpacetimeCompressionFieldGenerators, 2)),
                            -1,
                            (t, m) -> t.tierCasing = m,
                            t -> t.tierCasing)))
                .addElement('E', ofBlock(sBlockGlass1, 1))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Nullable
    public static Integer getTierCasingFromBlock(Block block, int meta) {
        if (block == null) return null;
        if (block == BlockLoader.defcCasingBlock) return meta - 7;
        if (block == TTCasingsContainer.SpacetimeCompressionFieldGenerators && 2 == meta) return 6;
        return null;
    }

    @Override
    public boolean isEnablePerfectOverclock() {
        return tierCasing >= 4;
    }

    @Override
    public ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @NotNull
            @Override
            protected CheckRecipeResult validateRecipe(@NotNull GTRecipe recipe) {
                return recipe.mSpecialValue <= tierCasing ? CheckRecipeResultRegistry.SUCCESSFUL
                    : CheckRecipeResultRegistry.insufficientMachineTier(recipe.mSpecialValue);
            }

            @NotNull
            @Override
            public OverclockCalculator createOverclockCalculator(@NotNull GTRecipe recipe) {

                if (tierCasing >= 4) {
                    return super.createOverclockCalculator(recipe).setRecipeEUt(recipe.mEUt)
                        .setAmperage(availableAmperage)
                        .setEUt(availableVoltage)
                        .setDuration(recipe.mDuration)
                        .setAmperageOC(true)
                        .setDurationDecreasePerOC(4)
                        .setEUtIncreasePerOC(4)
                        .setEUtDiscount(0.5 - (ParallelTier / 50.0))
                        .setSpeedBoost(1.0 / 2.0 - (ParallelTier / 200.0));
                } else return super.createOverclockCalculator(recipe).setEUtDiscount(0.5 - (ParallelTier / 50.0))
                    .setSpeedBoost(1.0 / 2.0 - (ParallelTier / 200.0));
            }
        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        mCasing = 0;
        ParallelTier = 0;
        tierCasing = -1;

        if (!checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet) && checkHatch()) {
            return false;
        }

        energyHatchTier = checkEnergyHatchTier();
        if (tierCasing < 0) return false;

        ParallelTier = getParallelTier(aStack);
        return mCasing >= 25;
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
