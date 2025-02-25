package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.science.gtnl.common.block.Casings.BasicBlocks.MetaCasing;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.GregTechAPI.sBlockCasings9;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTStructureUtility.ofFrame;
import static gtPlusPlus.core.block.ModBlocks.*;
import static kubatech.loaders.BlockLoader.defcCasingBlock;
import static tectech.thing.casing.TTCasingsContainer.sBlockCasingsTT;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.machine.multiMachineClasses.GTNLProcessingLogic;
import com.science.gtnl.common.machine.multiMachineClasses.WirelessEnergyMultiMachineBase;
import com.science.gtnl.common.recipe.RecipeRegister;

import bartworks.API.BorosilicateGlass;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.enums.VoltageIndex;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.interfaces.tileentity.IWirelessEnergyHatchInformation;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.objects.GTRenderedTexture;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import tectech.thing.casing.BlockGTCasingsTT;

public class SmeltingMixingFurnace extends WirelessEnergyMultiMachineBase<SmeltingMixingFurnace>
    implements IWirelessEnergyHatchInformation {

    protected GTRecipe lastRecipeToBuffer;

    public byte mGlassTier = 0;

    public static final int HORIZONTAL_OFF_SET = 8;
    public static final int VERTICAL_OFF_SET = 14;
    public static final int DEPTH_OFF_SET = 0;

    protected static final int CASING_INDEX = BlockGTCasingsTT.textureOffset;

    public int tCountCasing = 0;

    public int casing;

    public IStructureDefinition<SmeltingMixingFurnace> STRUCTURE_DEFINITION = null;

    public static final String STRUCTURE_PIECE_MAIN = "main";

    public static final String SMF_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/smelting_mixing_furnace";

    public String[][] shape;

    public SmeltingMixingFurnace(String aName) {
        super(aName);
    }

    public SmeltingMixingFurnace(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        this.shape = StructureUtils.readStructureFromFile(SMF_STRUCTURE_FILE_PATH);
    }

    @Override
    protected boolean isEnablePerfectOverclock() {
        return true;
    }

    @Override
    protected float getSpeedBonus() {
        return 1;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new SmeltingMixingFurnace(this.mName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.SmeltingMixingFurnaceRecipeType)
            .addInfo(TextLocalization.Tooltip_Tectech_Hatch)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(17, 17, 33, true)
            .addInputBus(TextLocalization.Tooltip_SmeltingMixingFurnace_Casing, 1)
            .addOutputBus(TextLocalization.Tooltip_SmeltingMixingFurnace_Casing, 1)
            .addInputHatch(TextLocalization.Tooltip_SmeltingMixingFurnace_Casing, 1)
            .addOutputHatch(TextLocalization.Tooltip_SmeltingMixingFurnace_Casing, 1)
            .addEnergyHatch(TextLocalization.Tooltip_SmeltingMixingFurnace_Casing, 1)
            .toolTipFinisher();
        return tt;
    }

    protected void updateHatchTexture() {
        for (MTEHatch h : mInputHatches) h.updateTexture(CASING_INDEX);
        for (MTEHatch h : mOutputHatches) h.updateTexture(CASING_INDEX);
        for (MTEHatch h : mInputBusses) h.updateTexture(CASING_INDEX);
    }

    protected GTRenderedTexture LCgetFrontOverlay() {
        return new GTRenderedTexture(Textures.BlockIcons.OVERLAY_DTPF_OFF);
    }

    protected GTRenderedTexture LCgetFrontOverlayActive() {
        return new GTRenderedTexture(Textures.BlockIcons.OVERLAY_DTPF_ON);
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final ForgeDirection side,
        final ForgeDirection facing, final int aColorIndex, final boolean aActive, final boolean aRedstone) {
        if (side == facing) {
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(CASING_INDEX),
                aActive ? LCgetFrontOverlayActive() : LCgetFrontOverlay() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(CASING_INDEX) };
    }

    @Override
    public IStructureDefinition<SmeltingMixingFurnace> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<SmeltingMixingFurnace>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', BorosilicateGlass.ofBoroGlass((byte) 0, (t, v) -> t.mGlassTier = v, t -> t.mGlassTier))
                .addElement('B', ofBlock(MetaCasing, 5))
                .addElement('C', ofBlock(MetaCasing, 7))
                .addElement('D', ofBlock(defcCasingBlock, 7))
                .addElement('E', ofBlock(defcCasingBlock, 10))
                .addElement('F', ofBlock(sBlockCasings1, 12))
                .addElement('G', ofBlock(sBlockCasings1, 13))
                .addElement('H', ofBlock(sBlockCasings10, 7))
                .addElement('I', ofBlock(sBlockCasings10, 13))
                .addElement('J', ofBlock(sBlockCasings8, 7))
                .addElement('K', ofBlock(sBlockCasings9, 12))
                .addElement('L', ofBlock(sBlockCasingsTT, 6))
                .addElement('M', ofBlock(sBlockCasingsTT, 8))
                .addElement('N', ofFrame(Materials.Infinity))
                .addElement('O', ofBlock(blockCasings2Misc, 4))
                .addElement('P', ofBlock(blockSpecialMultiCasings, 11))
                .addElement('Q', ofBlock(blockCasingsMisc, 12))
                .addElement(
                    'R',
                    buildHatchAdder(SmeltingMixingFurnace.class)
                        .atLeast(InputBus, OutputBus, InputHatch, OutputHatch, Energy, Energy.or(ExoticEnergy))
                        .casingIndex(CASING_INDEX)
                        .dot(1)
                        .buildAndChain(onElementPass(x -> ++x.casing, ofBlock(sBlockCasingsTT, 0))))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        this.buildPiece(
            STRUCTURE_PIECE_MAIN,
            stackSize,
            hintsOnly,
            HORIZONTAL_OFF_SET,
            VERTICAL_OFF_SET,
            DEPTH_OFF_SET);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (this.mMachine) return -1;
        return this.survivialBuildPiece(
            STRUCTURE_PIECE_MAIN,
            stackSize,
            HORIZONTAL_OFF_SET,
            VERTICAL_OFF_SET,
            DEPTH_OFF_SET,
            elementBudget,
            env,
            false,
            true);
    }

    public boolean checkHatches() {
        return !mInputHatches.isEmpty() && !mInputBusses.isEmpty()
            && !mOutputBusses.isEmpty()
            && mOutputHatches.isEmpty();
    }

    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        repairMachine();
        tCountCasing = 0;
        wirelessMode = false;
        if (!checkPiece(STRUCTURE_PIECE_MAIN, HORIZONTAL_OFF_SET, VERTICAL_OFF_SET, DEPTH_OFF_SET)) return false;
        if (tCountCasing <= 15 && !checkHatches() && mGlassTier < VoltageIndex.UEV) {
            updateHatchTexture();
            return false;
        }
        wirelessMode = mEnergyHatches.isEmpty() && mExoticEnergyHatches.isEmpty();
        return true;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeRegister.SmeltingMixingFurnaceRecipes;
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new GTNLProcessingLogic() {

            @Nonnull
            @Override
            protected OverclockCalculator createOverclockCalculator(@Nonnull GTRecipe recipe) {
                if (wirelessMode) {
                    return OverclockCalculator.ofNoOverclock(recipe);
                } else {
                    return super.createOverclockCalculator(recipe);
                }
            }

            @NotNull
            @Override
            public CheckRecipeResult process() {
                setEuModifier(0.9);
                setSpeedBonus(0.2);
                return super.process();
            }

        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }

    @Override
    public int getWirelessModeProcessingTime() {
        return 1024;
    }

    @Override
    public int getMaxParallelRecipes() {
        return 256 * (GTUtility.getTier(this.getMaxInputVoltage()));
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setByte("mGlassTier", mGlassTier);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        mGlassTier = aNBT.getByte("mGlassTier");
    }

    @Override
    public String[] getInfoData() {
        final String running = (this.mMaxProgresstime > 0 ? "Machine running" : "Machine stopped");
        final String maintenance = (this.getIdealStatus() == this.getRepairStatus() ? "No Maintenance issues"
            : "Needs Maintenance");
        String tSpecialText;

        if (lastRecipeToBuffer != null && lastRecipeToBuffer.mOutputs[0].getDisplayName() != null) {
            tSpecialText = "Currently processing: " + lastRecipeToBuffer.mOutputs[0].getDisplayName();
        } else {
            tSpecialText = "Currently processing: Nothing";
        }

        return new String[] { "Industrial Cutting Factory", running, maintenance, tSpecialText };
    }

}
