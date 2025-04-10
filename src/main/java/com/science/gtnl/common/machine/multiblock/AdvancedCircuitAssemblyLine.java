package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static goodgenerator.loader.Loaders.FRF_Coil_1;
import static gregtech.api.GregTechAPI.sBlockCasings2;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.enums.Textures.BlockIcons.*;
import static gregtech.api.util.GTStructureUtility.*;
import static gtPlusPlus.core.block.ModBlocks.blockCasings3Misc;
import static tectech.thing.casing.TTCasingsContainer.sBlockCasingsTT;

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

import gregtech.api.enums.HeatingCoilLevel;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.ExoticEnergyInputHelper;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import tectech.thing.block.BlockQuantumGlass;
import tectech.thing.casing.BlockGTCasingsTT;

public class AdvancedCircuitAssemblyLine extends GTMMultiMachineBase<AdvancedCircuitAssemblyLine>
    implements ISurvivalConstructable {

    private HeatingCoilLevel mCoilLevel;
    private byte glassTier = 0;
    private int mHeatingCapacity = 0;
    private static IStructureDefinition<AdvancedCircuitAssemblyLine> STRUCTURE_DEFINITION = null;
    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final String ACAL_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/advanced_circuit_assembly_line";
    private static final int CASING_INDEX = BlockGTCasingsTT.textureOffset + 3;
    private static final String[][] shape = StructureUtils.readStructureFromFile(ACAL_STRUCTURE_FILE_PATH);
    public final int horizontalOffSet = 0;
    public final int verticalOffSet = 2;
    public final int depthOffSet = 0;

    public AdvancedCircuitAssemblyLine(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public AdvancedCircuitAssemblyLine(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new AdvancedCircuitAssemblyLine(this.mName);
    }

    @Override
    public IStructureDefinition<AdvancedCircuitAssemblyLine> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<AdvancedCircuitAssemblyLine>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', ofBlock(FRF_Coil_1, 0))
                .addElement('B', ofBlock(sBlockCasings2, 5))
                .addElement('C', ofBlock(sBlockCasingsTT, 0))
                .addElement('D', ofBlock(sBlockCasingsTT, 1))
                .addElement('E', ofBlock(sBlockCasingsTT, 2))
                .addElement(
                    'F',
                    buildHatchAdder(AdvancedCircuitAssemblyLine.class).casingIndex(CASING_INDEX)
                        .dot(1)
                        .atLeast(InputHatch, InputBus, OutputBus, Maintenance, Energy.or(ExoticEnergy))
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(sBlockCasingsTT, 3))))
                .addElement('G', ofBlock(sBlockCasingsTT, 6))
                .addElement('H', ofBlock(sBlockCasingsTT, 7))
                .addElement('I', ofBlock(sBlockCasingsTT, 8))
                .addElement('J', ofBlock(blockCasings3Misc, 15))
                .addElement('K', ofBlock(BlockQuantumGlass.INSTANCE, 0))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.AdvancedCircuitAssemblyLineRecipes)
            .addInfo(TextLocalization.Tooltip_AdvancedCircuitAssemblyLine_00)
            .addInfo(TextLocalization.Tooltip_AdvancedCircuitAssemblyLine_01)
            .addInfo(TextLocalization.Tooltip_AdvancedCircuitAssemblyLine_02)
            .addInfo(TextLocalization.Tooltip_AdvancedCircuitAssemblyLine_03)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_02)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_03)
            .addInfo(TextLocalization.Tooltip_PerfectOverclock)
            .addInfo(TextLocalization.Tooltip_Tectech_Hatch)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(32, 5, 5, true)
            .addInputHatch(TextLocalization.Tooltip_AdvancedCircuitAssemblyLine_Casing_00)
            .addInputBus(TextLocalization.Tooltip_AdvancedCircuitAssemblyLine_Casing_00)
            .addOutputBus(TextLocalization.Tooltip_AdvancedCircuitAssemblyLine_Casing_00)
            .addEnergyHatch(TextLocalization.Tooltip_AdvancedCircuitAssemblyLine_Casing_00)
            .addMaintenanceHatch(TextLocalization.Tooltip_AdvancedCircuitAssemblyLine_Casing_00)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public boolean isEnablePerfectOverclock() {
        return true;
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
                return super.createOverclockCalculator(recipe).setAmperageOC(true)
                    .setDurationDecreasePerOC(2)
                    .setEUtIncreasePerOC(4)
                    .setAmperage(availableAmperage)
                    .setRecipeEUt(recipe.mEUt)
                    .setEUt(availableVoltage)
                    .setEUtDiscount(0.8 - (ParallelTier / 50.0))
                    .setSpeedBoost(Math.max(0.05, 1.0 / 3.75 - (ParallelTier / 200.0)));
            }

            @Override
            protected @Nonnull CheckRecipeResult validateRecipe(@Nonnull GTRecipe recipe) {
                return recipe.mSpecialValue <= AdvancedCircuitAssemblyLine.this.mHeatingCapacity
                    ? CheckRecipeResultRegistry.SUCCESSFUL
                    : CheckRecipeResultRegistry.insufficientHeat(recipe.mSpecialValue);
            }
        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }

    @Override
    public int getMaxParallelRecipes() {
        return GTUtility.getTier(this.getMaxInputVoltage()) * 32 + 8;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        this.buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, horizontalOffSet, verticalOffSet, depthOffSet);
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
    public boolean checkMachine(IGregTechTileEntity iGregTechTileEntity, ItemStack aStack) {
        this.mHeatingCapacity = 0;
        ParallelTier = 0;
        mCasing = 0;
        energyHatchTier = 0;

        if (!checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet) && checkHatch()) {
            return false;
        }

        ParallelTier = getParallelTier(aStack);

        return mCasing >= 30;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeRegister.AdvancedCircuitAssemblyLineRecipes;
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
