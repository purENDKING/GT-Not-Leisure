package com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;
import static com.science.gtnl.common.block.Casings.BasicBlocks.MetaCasing;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.enums.Mods.IndustrialCraft2;
import static gregtech.api.enums.Textures.BlockIcons.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;

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

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import gregtech.common.blocks.BlockCasings2;

public class VacuumFreezer extends MultiMachineBase<VacuumFreezer> implements ISurvivalConstructable {

    public static final int CASING_INDEX = ((BlockCasings2) sBlockCasings2).getTextureIndex(1);
    private static IStructureDefinition<VacuumFreezer> STRUCTURE_DEFINITION = null;
    public static final String STRUCTURE_PIECE_MAIN = "main";
    public static final String VF_STRUCTURE_FILE_PATH = RESOURCE_ROOT_ID + ":" + "multiblock/vacuum_freezer";
    public static String[][] shape = StructureUtils.readStructureFromFile(VF_STRUCTURE_FILE_PATH);
    public final int horizontalOffSet = 3;
    public final int verticalOffSet = 5;
    public final int depthOffSet = 0;

    public VacuumFreezer(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);

    }

    public VacuumFreezer(String aName) {
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
        return new VacuumFreezer(this.mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_VACUUM_FREEZER_ACTIVE)
                    .extFacing()
                    .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_VACUUM_FREEZER_ACTIVE_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_VACUUM_FREEZER)
                    .extFacing()
                    .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_VACUUM_FREEZER_GLOW)
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
        return RecipeMaps.vacuumFreezerRecipes;
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.VacuumFreezerRecipeType)
            .addInfo(TextLocalization.Tooltip_VacuumFreezer_00)
            .addInfo(TextLocalization.Tooltip_VacuumFreezer_01)
            .addInfo(TextLocalization.Tooltip_VacuumFreezer_02)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(11, 7, 6, true)
            .addInputHatch(TextLocalization.Tooltip_VacuumFreezer_Casing)
            .addOutputHatch(TextLocalization.Tooltip_VacuumFreezer_Casing)
            .addInputBus(TextLocalization.Tooltip_VacuumFreezer_Casing)
            .addOutputBus(TextLocalization.Tooltip_VacuumFreezer_Casing)
            .addEnergyHatch(TextLocalization.Tooltip_VacuumFreezer_Casing)
            .addMaintenanceHatch(TextLocalization.Tooltip_VacuumFreezer_Casing)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IStructureDefinition<VacuumFreezer> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<VacuumFreezer>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', ofBlock(MetaCasing, 2))
                .addElement('B', ofBlockAnyMeta(GameRegistry.findBlock(IndustrialCraft2.ID, "blockAlloyGlass")))
                .addElement(
                    'C',
                    buildHatchAdder(VacuumFreezer.class).casingIndex(CASING_INDEX)
                        .dot(1)
                        .atLeast(InputHatch, OutputHatch, InputBus, OutputBus, Maintenance, Energy)
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(sBlockCasings2, 1))))
                .addElement('D', ofBlock(sBlockCasings2, 14))
                .addElement('E', ofBlock(sBlockCasings4, 1))
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
        return mCasing >= 135 && checkHatch();
    }

    @Override
    public int getMaxParallelRecipes() {
        return 8 + 2 * GTUtility.getTier(this.getMaxInputVoltage());
    }

    @Override
    public ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @NotNull
            @Override
            public OverclockCalculator createOverclockCalculator(@NotNull GTRecipe recipe) {
                return super.createOverclockCalculator(recipe).setMachineHeat(3601)
                    .setRecipeHeat(0)
                    .setHeatOC(true)
                    .setHeatDiscount(false)
                    .setSpeedBoost(1 / 1.33);
            }
        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }

    @Override
    protected void setProcessingLogicPower(ProcessingLogic logic) {
        boolean useSingleAmp = mEnergyHatches.size() == 1 && mExoticEnergyHatches.isEmpty() && getMaxInputAmps() <= 2;
        logic.setAvailableVoltage(getMachineVoltageLimit());
        logic.setAvailableAmperage(useSingleAmp ? 1 : getMaxInputAmps());
        logic.setAmperageOC(false);
    }
}
