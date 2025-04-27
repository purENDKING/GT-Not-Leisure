package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizon.structurelib.alignment.IAlignmentLimits;
import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.machine.multiMachineClasses.MultiMachineBase;
import com.science.gtnl.common.recipe.RecipeRegister;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.common.blocks.BlockCasings2;

public class LargeGasCollector extends MultiMachineBase<LargeGasCollector> implements ISurvivalConstructable {

    public static final String TEXTURE_OVERLAY_GAS_COLLECTOR_ACTIVE = RESOURCE_ROOT_ID + ":"
        + "iconsets/OVERLAY_GAS_COLLECTOR_ACTIVE";
    public static Textures.BlockIcons.CustomIcon OVERLAY_GAS_COLLECTOR_ACTIVE = new Textures.BlockIcons.CustomIcon(
        TEXTURE_OVERLAY_GAS_COLLECTOR_ACTIVE);
    public static final String TEXTURE_OVERLAY_GAS_COLLECTOR = RESOURCE_ROOT_ID + ":"
        + "iconsets/OVERLAY_GAS_COLLECTOR";
    public static Textures.BlockIcons.CustomIcon OVERLAY_GAS_COLLECTOR = new Textures.BlockIcons.CustomIcon(
        TEXTURE_OVERLAY_GAS_COLLECTOR);

    public static final String STRUCTURE_PIECE_MAIN = "main";
    private static IStructureDefinition<LargeGasCollector> STRUCTURE_DEFINITION = null;
    public static final String LGC_STRUCTURE_FILE_PATH = RESOURCE_ROOT_ID + ":" + "multiblock/large_gas_collector";
    public static final int CASING_INDEX = ((BlockCasings2) sBlockCasings2).getTextureIndex(0);
    public final int horizontalOffSet = 2;
    public final int verticalOffSet = 2;
    public final int depthOffSet = 0;
    public static String[][] shape = StructureUtils.readStructureFromFile(LGC_STRUCTURE_FILE_PATH);

    public LargeGasCollector(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public LargeGasCollector(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new LargeGasCollector(this.mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(OVERLAY_GAS_COLLECTOR_ACTIVE)
                    .extFacing()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(OVERLAY_GAS_COLLECTOR)
                    .extFacing()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }

    @Override
    public int getMaxParallelRecipes() {
        return 1000000;
    }

    @Override
    public int getCasingTextureID() {
        return CASING_INDEX;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeRegister.GasCollectorRecipes;
    }

    @Override
    public boolean isEnablePerfectOverclock() {
        return false;
    }

    @Override
    public float getSpeedBonus() {
        return 1;
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.LargeGasCollectorRecipeType)
            .addInfo(TextLocalization.Tooltip_LargeGasCollector_00)
            .addInfo(TextLocalization.Tooltip_LargeGasCollector_01)
            .addInfo(TextLocalization.Tooltip_Tectech_Hatch)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(5, 5, 5, true)
            .addOutputHatch(TextLocalization.Tooltip_LargeGasCollector_Casing)
            .addInputBus(TextLocalization.Tooltip_LargeGasCollector_Casing)
            .addEnergyHatch(TextLocalization.Tooltip_LargeGasCollector_Casing)
            .addMaintenanceHatch(TextLocalization.Tooltip_LargeGasCollector_Casing)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IStructureDefinition<LargeGasCollector> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<LargeGasCollector>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement(
                    'A',
                    buildHatchAdder(LargeGasCollector.class).casingIndex(CASING_INDEX)
                        .dot(1)
                        .atLeast(OutputHatch, InputBus, Maintenance, Energy.or(ExoticEnergy))
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(sBlockCasings2, 0))))
                .addElement('B', ofBlock(sBlockCasings2, 15))
                .addElement('C', ofBlock(sBlockCasings3, 10))
                .addElement('D', ofBlock(sBlockCasings6, 5))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        mCasing = 0;

        if (!checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet) && checkHatch()) {
            return false;
        }

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

    @Override
    protected IAlignmentLimits getInitialAlignmentLimits() {
        return (d, r, f) -> d == ForgeDirection.UP;
    }
}
