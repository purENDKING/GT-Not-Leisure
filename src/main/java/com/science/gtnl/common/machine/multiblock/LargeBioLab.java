package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.common.machine.multiMachineClasses.MultiMachineBase.ParallelControllerElement.ParallelController;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_OIL_CRACKER;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_OIL_CRACKER_ACTIVE;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTStructureUtility.ofFrame;
import static gtPlusPlus.core.block.ModBlocks.blockSpecialMultiCasings2;
import static tectech.thing.casing.TTCasingsContainer.sBlockCasingsTT;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

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
import gregtech.api.recipe.RecipeMap;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.common.blocks.BlockCasings9;

public class LargeBioLab extends GTMMultiMachineBase<LargeBioLab> implements ISurvivalConstructable {

    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static IStructureDefinition<LargeBioLab> STRUCTURE_DEFINITION = null;
    private static final String LBL_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/large_bio_lab";
    private final int horizontalOffSet = 3;
    private final int verticalOffSet = 2;
    private final int depthOffSet = 0;
    public static String[][] shape = StructureUtils.readStructureFromFile(LBL_STRUCTURE_FILE_PATH);

    public LargeBioLab(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public LargeBioLab(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new LargeBioLab(this.mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_OIL_CRACKER_ACTIVE)
                    .extFacing()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_OIL_CRACKER)
                    .extFacing()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }

    @Override
    public int getCasingTextureID() {
        return ((BlockCasings9) sBlockCasings9).getTextureIndex(12);
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeRegister.LargeBioLabRecipes;
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.LargeBioLabRecipeType)
            .addInfo(TextLocalization.Tooltip_LargeBioLab_00)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_01)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_02)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_03)
            .addInfo(TextLocalization.Tooltip_Tectech_Hatch)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(7, 5, 5, true)
            .addInputHatch(TextLocalization.Tooltip_LargeBioLab_Casing)
            .addOutputHatch(TextLocalization.Tooltip_LargeBioLab_Casing)
            .addInputBus(TextLocalization.Tooltip_LargeBioLab_Casing)
            .addOutputBus(TextLocalization.Tooltip_LargeBioLab_Casing)
            .addEnergyHatch(TextLocalization.Tooltip_LargeBioLab_Casing)
            .addMaintenanceHatch(TextLocalization.Tooltip_LargeBioLab_Casing)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IStructureDefinition<LargeBioLab> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<LargeBioLab>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', ofBlock(sBlockCasings10, 3))
                .addElement(
                    'B',
                    buildHatchAdder(LargeBioLab.class).casingIndex(getCasingTextureID())
                        .dot(1)
                        .atLeast(
                            InputHatch,
                            OutputHatch,
                            InputBus,
                            OutputBus,
                            Maintenance,
                            Energy.or(ExoticEnergy),
                            ParallelController)
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(sBlockCasings9, 12))))
                .addElement('C', ofBlock(sBlockCasingsTT, 0))
                .addElement('D', ofFrame(Materials.CosmicNeutronium))
                .addElement('E', ofBlock(blockSpecialMultiCasings2, 2))
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
        return mCasing >= 20;
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
