package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.enums.Textures.BlockIcons.*;
import static gregtech.api.util.GTStructureUtility.*;
import static gtPlusPlus.core.block.ModBlocks.*;

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
import com.science.gtnl.common.machine.multiMachineClasses.MultiMachineBase;
import com.science.gtnl.common.recipe.RecipeRegister;

import bartworks.API.BorosilicateGlass;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.HeatingCoilLevel;
import gregtech.api.enums.Materials;
import gregtech.api.enums.SoundResource;
import gregtech.api.enums.TAE;
import gregtech.api.enums.Textures;
import gregtech.api.enums.VoltageIndex;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.implementations.MTEHatchEnergy;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import gregtech.common.blocks.BlockCasings1;

public class PlatinumBasedTreatment extends MultiMachineBase<PlatinumBasedTreatment> implements ISurvivalConstructable {

    private HeatingCoilLevel mCoilLevel;
    private byte glassTier = 0;
    private static IStructureDefinition<PlatinumBasedTreatment> STRUCTURE_DEFINITION = null;
    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final String PBT_STRUCTURE_FILE_PATH = RESOURCE_ROOT_ID + ":"
        + "multiblock/platinum_based_treatment";
    private static final int CASING_INDEX = TAE.getIndexFromPage(2, 2);
    private static final String[][] shape = StructureUtils.readStructureFromFile(PBT_STRUCTURE_FILE_PATH);
    private final int horizontalOffSet = 7;
    private final int verticalOffSet = 15;
    private final int depthOffSet = 0;

    public PlatinumBasedTreatment(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public PlatinumBasedTreatment(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new PlatinumBasedTreatment(this.mName);
    }

    @Override
    public IStructureDefinition<PlatinumBasedTreatment> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<PlatinumBasedTreatment>builder()
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
                .addElement('B', ofBlock(sBlockCasings1, 11))
                .addElement('C', ofBlock(sSolenoidCoilCasings, 3))
                .addElement('D', ofBlock(sBlockCasings10, 13))
                .addElement('E', ofBlock(sBlockCasings10, 14))
                .addElement('F', ofBlock(sBlockCasings4, 0))
                .addElement('G', ofBlock(sBlockCasings4, 1))
                .addElement('H', ofCoil(PlatinumBasedTreatment::setCoilLevel, PlatinumBasedTreatment::getCoilLevel))
                .addElement('I', ofBlock(sBlockCasings8, 0))
                .addElement('J', ofBlock(sBlockCasings8, 1))
                .addElement('K', ofFrame(Materials.BlackSteel))
                .addElement('L', ofBlock(blockCasings2Misc, 5))
                .addElement('M', ofBlock(blockCasings2Misc, 6))
                .addElement('N', ofBlock(blockCasings2Misc, 11))
                .addElement(
                    'O',
                    buildHatchAdder(PlatinumBasedTreatment.class).casingIndex(CASING_INDEX)
                        .dot(1)
                        .atLeast(InputHatch, InputBus, OutputHatch, OutputBus, Maintenance, Energy.or(ExoticEnergy))
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(blockCasings3Misc, 2))))
                .addElement('P', ofBlock(blockCasingsMisc, 0))
                .addElement('Q', ofBlock(blockCasingsMisc, 5))
                .addElement('R', Muffler.newAny(((BlockCasings1) sBlockCasings1).getTextureIndex(11), 6))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.PlatinumBasedTreatmentRecipes)
            .addInfo(TextLocalization.Tooltip_PlatinumBasedTreatment_00)
            .addInfo(TextLocalization.Tooltip_PlatinumBasedTreatment_01)
            .addInfo(TextLocalization.Tooltip_PlatinumBasedTreatment_02)
            .addInfo(TextLocalization.Tooltip_PlatinumBasedTreatment_03)
            .addInfo(TextLocalization.Tooltip_PlatinumBasedTreatment_04)
            .addInfo(TextLocalization.Tooltip_PlatinumBasedTreatment_05)
            .addInfo(TextLocalization.Tooltip_PerfectOverclock)
            .addInfo(TextLocalization.Tooltip_Tectech_Hatch)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(15, 17, 18, true)
            .addInputHatch(TextLocalization.Tooltip_PlatinumBasedTreatment_Casing_00)
            .addOutputHatch(TextLocalization.Tooltip_PlatinumBasedTreatment_Casing_00)
            .addInputBus(TextLocalization.Tooltip_PlatinumBasedTreatment_Casing_00)
            .addOutputBus(TextLocalization.Tooltip_PlatinumBasedTreatment_Casing_00)
            .addEnergyHatch(TextLocalization.Tooltip_PlatinumBasedTreatment_Casing_00)
            .addMaintenanceHatch(TextLocalization.Tooltip_PlatinumBasedTreatment_Casing_00)
            .addMufflerHatch(TextLocalization.Tooltip_PlatinumBasedTreatment_Casing_01)
            .toolTipFinisher();
        return tt;
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
    public boolean isEnablePerfectOverclock() {
        return true;
    }

    @Override
    public float getSpeedBonus() {
        return 1;
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
        return TAE.GTPP_INDEX(0);
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
                return super.createOverclockCalculator(recipe).setEUtDiscount(1.0 - getCoilLevel().getTier() * 0.05)
                    .setSpeedBoost(1.0 - getCoilLevel().getTier() * 0.05);
            }

        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }

    @Override
    public int getMaxParallelRecipes() {
        return GTUtility.getTier(this.getMaxInputVoltage()) * 4 + 8;
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
        mCasing = 0;
        energyHatchTier = 0;
        this.setCoilLevel(HeatingCoilLevel.None);

        if (!checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet) && checkHatch()) {
            return false;
        }
        energyHatchTier = checkEnergyHatchTier();
        for (MTEHatchEnergy mEnergyHatch : this.mEnergyHatches) {
            if (glassTier < VoltageIndex.UHV & mEnergyHatch.mTier > glassTier) {
                return false;
            }
        }

        if (getCoilLevel() == HeatingCoilLevel.None) return false;

        if (mMaintenanceHatches.size() != 1 && mMufflerHatches.size() != 6) return false;

        return mCasing >= 30;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeRegister.PlatinumBasedTreatmentRecipes;
    }

    @Override
    public int getRecipeCatalystPriority() {
        return -2;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected SoundResource getActivitySoundLoop() {
        return SoundResource.GT_MACHINES_MEGA_BLAST_FURNACE_LOOP;
    }

    @Override
    protected void setProcessingLogicPower(ProcessingLogic logic) {
        boolean useSingleAmp = mEnergyHatches.size() == 1 && mExoticEnergyHatches.isEmpty() && getMaxInputAmps() <= 2;
        logic.setAvailableVoltage(getMachineVoltageLimit());
        logic.setAvailableAmperage(useSingleAmp ? 1 : getMaxInputAmps());
        logic.setAmperageOC(useSingleAmp);
    }

}
