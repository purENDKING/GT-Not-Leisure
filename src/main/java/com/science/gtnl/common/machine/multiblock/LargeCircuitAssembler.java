package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.enums.Textures.BlockIcons.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTUtility.validMTEList;
import static gtPlusPlus.core.block.ModBlocks.blockCasings3Misc;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.machine.multiMachineClasses.MultiMachineBase;

import bartworks.API.BorosilicateGlass;
import gregtech.api.enums.TAE;
import gregtech.api.enums.Textures;
import gregtech.api.enums.VoltageIndex;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.metatileentity.implementations.MTEHatchEnergy;
import gregtech.api.metatileentity.implementations.MTEHatchMaintenance;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import tectech.thing.metaTileEntity.hatch.MTEHatchEnergyTunnel;

public class LargeCircuitAssembler extends MultiMachineBase<LargeCircuitAssembler> implements ISurvivalConstructable {

    public int mCasing;
    public byte glassTier = 0;
    public static IStructureDefinition<LargeCircuitAssembler> STRUCTURE_DEFINITION = null;
    public static final String STRUCTURE_PIECE_MAIN = "main";
    public static final String LCA_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/large_circuit_assembler";
    public static String[][] shape = StructureUtils.readStructureFromFile(LCA_STRUCTURE_FILE_PATH);
    public final int horizontalOffSet = 5;
    public final int verticalOffSet = 1;
    public final int depthOffSet = 0;

    public LargeCircuitAssembler(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public LargeCircuitAssembler(String aName) {
        super(aName);
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
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new LargeCircuitAssembler(this.mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_ASSEMBLY_LINE_ACTIVE)
                    .extFacing()
                    .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_ASSEMBLY_LINE_ACTIVE_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_ASSEMBLY_LINE)
                    .extFacing()
                    .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_ASSEMBLY_LINE_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }

    public int getCasingTextureID() {
        return TAE.getIndexFromPage(2, 2);
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeMaps.circuitAssemblerRecipes;
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.LargeCircuitAssemblerRecipeType)
            .addInfo(TextLocalization.Tooltip_LargeCircuitAssembler_00)
            .addInfo(TextLocalization.Tooltip_LargeCircuitAssembler_01)
            .addInfo(TextLocalization.Tooltip_LargeCircuitAssembler_02)
            .addInfo(TextLocalization.Tooltip_LargeCircuitAssembler_03)
            .addInfo(TextLocalization.Tooltip_LargeCircuitAssembler_04)
            .addInfo(TextLocalization.Tooltip_LargeCircuitAssembler_05)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_04)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(7, 3, 5, true)
            .addInputHatch(TextLocalization.Tooltip_LargeCircuitAssembler_Casing)
            .addInputBus(TextLocalization.Tooltip_LargeCircuitAssembler_Casing)
            .addOutputBus(TextLocalization.Tooltip_LargeCircuitAssembler_Casing)
            .addEnergyHatch(TextLocalization.Tooltip_LargeCircuitAssembler_Casing)
            .addMaintenanceHatch(TextLocalization.Tooltip_LargeCircuitAssembler_Casing)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IStructureDefinition<LargeCircuitAssembler> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<LargeCircuitAssembler>builder()
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
                .addElement('B', ofBlock(sBlockCasings2, 14))
                .addElement('C', ofBlock(sBlockCasings3, 10))
                .addElement(
                    'D',
                    buildHatchAdder(LargeCircuitAssembler.class).casingIndex(TAE.getIndexFromPage(2, 2))
                        .dot(1)
                        .atLeast(InputHatch, InputBus, OutputBus, Maintenance, Energy.or(ExoticEnergy))
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(blockCasings3Misc, 2))))
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
        glassTier = 0;

        if (!checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet) && checkHatch()) {
            return false;
        }

        for (MTEHatchEnergy mEnergyHatch : this.mEnergyHatches) {
            if (glassTier < VoltageIndex.UV & mEnergyHatch.mTier > glassTier) {
                return false;
            }
        }

        for (MTEHatch hatch : getExoticEnergyHatches()) {
            if (hatch instanceof MTEHatchEnergyTunnel) {
                return false;
            }
        }

        if (this.mEnergyHatches.size() >= 2) return false;
        return mCasing >= 30;
    }

    @Override
    public int getMaxParallelRecipes() {
        return 32 + 4 * GTUtility.getTier(this.getMaxInputVoltage());
    }

    @Override
    public ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @NotNull
            @Override
            public OverclockCalculator createOverclockCalculator(@NotNull GTRecipe recipe) {
                return super.createOverclockCalculator(recipe).setEUtDiscount(0.8)
                    .setSpeedBoost(0.6);
            }
        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }

    @Override
    public boolean getDefaultHasMaintenanceChecks() {
        return true;
    }

    @Override
    public boolean shouldCheckMaintenance() {
        return true;
    }

    @Override
    public void checkMaintenance() {
        if (!shouldCheckMaintenance()) return;

        if (getRepairStatus() != getIdealStatus()) {
            for (MTEHatchMaintenance tHatch : validMTEList(mMaintenanceHatches)) {
                if (tHatch.mAuto) tHatch.autoMaintainance();
                if (tHatch.mWrench) mWrench = true;
                if (tHatch.mScrewdriver) mScrewdriver = true;
                if (tHatch.mSoftHammer) mSoftHammer = true;
                if (tHatch.mHardHammer) mHardHammer = true;
                if (tHatch.mSolderingTool) mSolderingTool = true;
                if (tHatch.mCrowbar) mCrowbar = true;

                tHatch.mWrench = false;
                tHatch.mScrewdriver = false;
                tHatch.mSoftHammer = false;
                tHatch.mHardHammer = false;
                tHatch.mSolderingTool = false;
                tHatch.mCrowbar = false;
            }
        }
    }
}
