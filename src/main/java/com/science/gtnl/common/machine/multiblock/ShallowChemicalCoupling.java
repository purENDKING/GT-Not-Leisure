package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;
import static com.science.gtnl.common.block.Casings.BasicBlocks.MetaCasing;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.enums.Textures.BlockIcons.*;
import static gregtech.api.util.GTStructureUtility.*;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.machine.multiMachineClasses.GTMMultiMachineBase;
import com.science.gtnl.common.recipe.RecipeRegister;

import bartworks.API.BorosilicateGlass;
import gregtech.api.enums.HeatingCoilLevel;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.enums.VoltageIndex;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.metatileentity.implementations.MTEHatchEnergy;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.ExoticEnergyInputHelper;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;

public class ShallowChemicalCoupling extends GTMMultiMachineBase<ShallowChemicalCoupling>
    implements ISurvivalConstructable {

    private HeatingCoilLevel mCoilLevel;
    private byte mGlassTier = 0;
    private int mHeatingCapacity = 0;
    private static IStructureDefinition<ShallowChemicalCoupling> STRUCTURE_DEFINITION = null;
    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final String SCC_STRUCTURE_FILE_PATH = RESOURCE_ROOT_ID + ":"
        + "multiblock/shallow_chemical_coupling";
    private static final int CASING_INDEX = GTUtility.getTextureId((byte) 116, (byte) 19);
    private static final String[][] shape = StructureUtils.readStructureFromFile(SCC_STRUCTURE_FILE_PATH);
    public final int horizontalOffSet = 3;
    public final int verticalOffSet = 9;
    public final int depthOffSet = 0;

    public ShallowChemicalCoupling(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public ShallowChemicalCoupling(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new ShallowChemicalCoupling(this.mName);
    }

    @Override
    public IStructureDefinition<ShallowChemicalCoupling> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<ShallowChemicalCoupling>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement(
                    'A',
                    buildHatchAdder(ShallowChemicalCoupling.class).casingIndex(CASING_INDEX)
                        .dot(1)
                        .atLeast(InputHatch, OutputHatch, InputBus, OutputBus, Maintenance, Energy.or(ExoticEnergy))
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(MetaCasing, 19))))
                .addElement(
                    'B',
                    withChannel(
                        "glass",
                        BorosilicateGlass.ofBoroGlass(
                            (byte) 0,
                            (byte) 1,
                            Byte.MAX_VALUE,
                            (te, t) -> te.mGlassTier = t,
                            te -> te.mGlassTier)))
                .addElement(
                    'C',
                    withChannel(
                        "coil",
                        ofCoil(ShallowChemicalCoupling::setCoilLevel, ShallowChemicalCoupling::getCoilLevel)))
                .addElement('D', ofBlock(sBlockCasings8, 1))
                .addElement('E', ofFrame(Materials.NaquadahAlloy))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.ShallowChemicalCouplingRecipes)
            .addInfo(TextLocalization.Tooltip_ShallowChemicalCoupling_00)
            .addInfo(TextLocalization.Tooltip_ShallowChemicalCoupling_01)
            .addInfo(TextLocalization.Tooltip_ShallowChemicalCoupling_02)
            .addInfo(TextLocalization.Tooltip_ShallowChemicalCoupling_03)
            .addInfo(TextLocalization.Tooltip_PerfectOverclock)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_02)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_03)
            .addInfo(TextLocalization.Tooltip_Tectech_Hatch)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(7, 11, 7, true)
            .addInputHatch(TextLocalization.Tooltip_ShallowChemicalCoupling_Casing_00)
            .addOutputHatch(TextLocalization.Tooltip_ShallowChemicalCoupling_Casing_00)
            .addInputBus(TextLocalization.Tooltip_ShallowChemicalCoupling_Casing_00)
            .addOutputBus(TextLocalization.Tooltip_ShallowChemicalCoupling_Casing_00)
            .addEnergyHatch(TextLocalization.Tooltip_ShallowChemicalCoupling_Casing_00)
            .addMaintenanceHatch(TextLocalization.Tooltip_ShallowChemicalCoupling_Casing_00)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public boolean isEnablePerfectOverclock() {
        return true;
    }

    public void setCoilLevel(HeatingCoilLevel aCoilLevel) {
        this.mCoilLevel = aCoilLevel;
    }

    public HeatingCoilLevel getCoilLevel() {
        return this.mCoilLevel;
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
        int aColorIndex, boolean aActive, boolean aRedstone) {
        if (side == facing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE_ACTIVE)
                    .extFacing()
                    .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE_ACTIVE_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE)
                    .extFacing()
                    .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE_GLOW)
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
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
    }

    @Override
    public ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @Nonnull
            @Override
            protected OverclockCalculator createOverclockCalculator(@Nonnull GTRecipe recipe) {
                return super.createOverclockCalculator(recipe).setRecipeHeat(recipe.mSpecialValue)
                    .setMachineHeat(ShallowChemicalCoupling.this.mHeatingCapacity)
                    .setHeatOC(true)
                    .setHeatDiscount(false)
                    .setEUtDiscount(Math.pow(0.85, getCoilLevel().getTier()))
                    .setSpeedBoost(Math.pow(0.85, getCoilLevel().getTier()));
            }

            @Override
            protected @Nonnull CheckRecipeResult validateRecipe(@Nonnull GTRecipe recipe) {
                return recipe.mSpecialValue <= ShallowChemicalCoupling.this.mHeatingCapacity
                    ? CheckRecipeResultRegistry.SUCCESSFUL
                    : CheckRecipeResultRegistry.insufficientHeat(recipe.mSpecialValue);
            }
        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        this.buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, horizontalOffSet, verticalOffSet, depthOffSet);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (mMachine) return -1;
        this.setCoilLevel(HeatingCoilLevel.None);
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
    public boolean checkMachine(IGregTechTileEntity iGregTechTileEntity, ItemStack aStack) {
        this.mHeatingCapacity = 0;
        ParallelTier = 0;
        mCasing = 0;
        energyHatchTier = 0;
        this.setCoilLevel(HeatingCoilLevel.None);

        if (!checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet) && checkHatch()) {
            return false;
        }

        this.mHeatingCapacity = (int) this.getCoilLevel()
            .getHeat();
        energyHatchTier = checkEnergyHatchTier();

        for (MTEHatchEnergy mEnergyHatch : this.mEnergyHatches) {
            if (mGlassTier < VoltageIndex.UEV & mEnergyHatch.mTier > mGlassTier) {
                return false;
            }
        }

        for (MTEHatch mExoEnergyHatch : this.mExoticEnergyHatches) {
            if (mGlassTier < VoltageIndex.UEV & mExoEnergyHatch.mTier > mGlassTier) {
                return false;
            }
        }

        if (getCoilLevel() == HeatingCoilLevel.None) return false;

        ParallelTier = getParallelTier(aStack);

        return mCasing >= 30;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeRegister.ShallowChemicalCouplingRecipes;
    }

    @Override
    protected void setProcessingLogicPower(ProcessingLogic logic) {
        boolean useSingleAmp = mEnergyHatches.size() == 1 && mExoticEnergyHatches.isEmpty() && getMaxInputAmps() <= 2;
        logic.setAvailableVoltage(getMachineVoltageLimit());
        logic.setAvailableAmperage(
            useSingleAmp ? 1
                : ExoticEnergyInputHelper.getMaxWorkingInputAmpsMulti(getExoticAndNormalEnergyHatchList()));
        logic.setAmperageOC(false);
    }

}
