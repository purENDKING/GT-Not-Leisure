package com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.common.block.Casings.BasicBlocks.MetaBlockGlass;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.enums.Textures.BlockIcons.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTStructureUtility.ofFrame;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.machine.multiMachineClasses.GTMMultiMachineBase;

import bartworks.API.recipe.BartWorksRecipeMaps;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.common.blocks.BlockCasings4;

public class ElectricImplosionCompressor extends GTMMultiMachineBase<ElectricImplosionCompressor>
    implements ISurvivalConstructable {

    public static final String STRUCTURE_PIECE_MAIN = "main";
    private static IStructureDefinition<ElectricImplosionCompressor> STRUCTURE_DEFINITION = null;
    public static final String ELC_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/electric_implosion_compressor";
    public static final int CASING_INDEX = ((BlockCasings4) sBlockCasings4).getTextureIndex(0);
    public final int horizontalOffSet = 2;
    public final int verticalOffSet = 7;
    public final int depthOffSet = 0;
    public static String[][] shape = StructureUtils.readStructureFromFile(ELC_STRUCTURE_FILE_PATH);

    public ElectricImplosionCompressor(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public ElectricImplosionCompressor(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new ElectricImplosionCompressor(this.mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_MULTI_LATHE_ACTIVE)
                    .extFacing()
                    .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_MULTI_LATHE_ACTIVE_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_MULTI_LATHE)
                    .extFacing()
                    .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_MULTI_LATHE_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }

    public int getCasingTextureID() {
        return CASING_INDEX;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return BartWorksRecipeMaps.electricImplosionCompressorRecipes;
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.ElectricImplosionCompressorRecipeType)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_00)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_01)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_02)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_03)
            .addInfo(TextLocalization.Tooltip_Tectech_Hatch)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(5, 8, 5, true)
            .addInputBus(TextLocalization.Tooltip_ElectricImplosionCompressor_Casing)
            .addOutputBus(TextLocalization.Tooltip_ElectricImplosionCompressor_Casing)
            .addEnergyHatch(TextLocalization.Tooltip_ElectricImplosionCompressor_Casing)
            .addMaintenanceHatch(TextLocalization.Tooltip_ElectricImplosionCompressor_Casing)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IStructureDefinition<ElectricImplosionCompressor> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<ElectricImplosionCompressor>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', ofBlock(MetaBlockGlass, 2))
                .addElement('B', ofBlock(sBlockCasings2, 15))
                .addElement(
                    'C',
                    buildHatchAdder(ElectricImplosionCompressor.class).casingIndex(CASING_INDEX)
                        .dot(1)
                        .atLeast(InputHatch, OutputHatch, InputBus, OutputBus, Maintenance, Energy.or(ExoticEnergy))
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(sBlockCasings4, 0))))
                .addElement('D', ofFrame(Materials.TungstenSteel))
                .addElement('E', Muffler.newAny(CASING_INDEX, 1))
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

        ParallelTier = getParallelTier(aStack);
        return mCasing >= 35 && this.mMufflerHatches.size() == 1;
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
