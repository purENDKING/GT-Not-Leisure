package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;
import static com.science.gtnl.common.block.Casings.BasicBlocks.MetaCasing;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gtnhlanth.common.register.LanthItemList.ELECTRODE_CASING;
import static tectech.thing.casing.TTCasingsContainer.sBlockCasingsTT;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.machine.multiMachineClasses.GTMMultiMachineBase;
import com.science.gtnl.common.recipe.RecipeRegister;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;

public class ElementCopying extends GTMMultiMachineBase<ElementCopying> implements ISurvivalConstructable {

    public int mCasing;
    public static IStructureDefinition<ElementCopying> STRUCTURE_DEFINITION = null;
    public static final String STRUCTURE_PIECE_MAIN = "main";
    public static final String EC_STRUCTURE_FILE_PATH = RESOURCE_ROOT_ID + ":" + "multiblock/element_copying";
    public static String[][] shape = StructureUtils.readStructureFromFile(EC_STRUCTURE_FILE_PATH);
    public final int horizontalOffSet = 7;
    public final int verticalOffSet = 0;
    public final int depthOffSet = 12;
    protected static final int CASING_INDEX = 1028;

    public ElementCopying(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public ElementCopying(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new ElementCopying(this.mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_DTPF_ON)
                    .extFacing()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_DTPF_OFF)
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
        return RecipeRegister.ElementCopyingRecipes;
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.ElementCopyingRecipeType)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_02)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_03)
            .addInfo(TextLocalization.Tooltip_PerfectOverclock)
            .addInfo(TextLocalization.Tooltip_Tectech_Hatch)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(15, 3, 15, true)
            .addInputHatch(TextLocalization.Tooltip_ElementCopying_Casing)
            .addInputBus(TextLocalization.Tooltip_ElementCopying_Casing)
            .addOutputBus(TextLocalization.Tooltip_ElementCopying_Casing)
            .addEnergyHatch(TextLocalization.Tooltip_ElementCopying_Casing)
            .addMaintenanceHatch(TextLocalization.Tooltip_ElementCopying_Casing)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IStructureDefinition<ElementCopying> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<ElementCopying>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', ofBlock(MetaCasing, 18))
                .addElement('B', ofBlockAnyMeta(ELECTRODE_CASING))
                .addElement(
                    'C',
                    buildHatchAdder(ElementCopying.class).casingIndex(CASING_INDEX)
                        .dot(1)
                        .atLeast(InputHatch, InputBus, OutputBus, OutputHatch, Energy.or(ExoticEnergy), Maintenance)
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(sBlockCasingsTT, 4))))
                .addElement('D', ofBlock(sBlockCasingsTT, 6))
                .addElement('E', ofBlock(sBlockCasingsTT, 7))
                .addElement('F', ofBlock(sBlockCasingsTT, 8))
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
        if (this.mEnergyHatches.size() >= 2) return false;
        return mCasing >= 200;
    }

    @Override
    public ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @NotNull
            @Override
            public OverclockCalculator createOverclockCalculator(@NotNull GTRecipe recipe) {
                return super.createOverclockCalculator(recipe).setRecipeEUt(recipe.mEUt)
                    .setAmperage(availableAmperage)
                    .setEUt(availableVoltage)
                    .setDuration(recipe.mDuration)
                    .setAmperageOC(true)
                    .setDurationDecreasePerOC(4)
                    .setEUtIncreasePerOC(4)
                    .setEUtDiscount(1 - (mParallelTier / 50.0))
                    .setSpeedBoost(1 - (mParallelTier / 200.0));
            }
        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }
}
