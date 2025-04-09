package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTStructureUtility.ofFrame;
import static gregtech.api.util.GTUtility.validMTEList;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.gtnewhorizons.gtnhintergalactic.block.IGBlocks;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.block.Casings.BasicBlocks;
import com.science.gtnl.common.machine.multiMachineClasses.GTNLProcessingLogic;
import com.science.gtnl.common.machine.multiMachineClasses.WirelessEnergyMultiMachineBase;
import com.science.gtnl.misc.OverclockType;

import bartworks.API.BorosilicateGlass;
import goodgenerator.loader.Loaders;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.interfaces.tileentity.IWirelessEnergyHatchInformation;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.metatileentity.implementations.MTEHatchEnergy;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import gregtech.common.blocks.BlockCasings8;
import gtnhlanth.common.register.LanthItemList;

public class IntegratedAssemblyFacility extends WirelessEnergyMultiMachineBase<IntegratedAssemblyFacility>
    implements IWirelessEnergyHatchInformation {

    private int casingTier;
    private byte mGlassTier = 0;
    private static final int HORIZONTAL_OFF_SET = 8;
    private static final int VERTICAL_OFF_SET = 10;
    private static final int DEPTH_OFF_SET = 0;
    private int tCountCasing = 0;
    private static IStructureDefinition<IntegratedAssemblyFacility> STRUCTURE_DEFINITION = null;
    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final String IAF_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/integrated_assembly_facility"; // 文件路径
    private static final String[][] shape = StructureUtils.readStructureFromFile(IAF_STRUCTURE_FILE_PATH);

    public IntegratedAssemblyFacility(String aName) {
        super(aName);
    }

    public IntegratedAssemblyFacility(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new IntegratedAssemblyFacility(this.mName);
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.IntegratedAssemblyFacilityRecipeType)
            .addInfo(TextLocalization.Tooltip_IntegratedAssemblyFacility_00)
            .addInfo(TextLocalization.Tooltip_IntegratedAssemblyFacility_01)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_00)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_01)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_02)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_03)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_04)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_05)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_06)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_07)
            .addInfo(String.format(TextLocalization.Tooltip_WirelessEnergyMultiMachine_08, "300"))
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_09)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_10)
            .addInfo(TextLocalization.Tooltip_Tectech_Hatch)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(47, 13, 19, true)
            .addInputBus(TextLocalization.Tooltip_IntegratedAssemblyFacility_Casing, 1)
            .addOutputBus(TextLocalization.Tooltip_IntegratedAssemblyFacility_Casing, 1)
            .addInputHatch(TextLocalization.Tooltip_IntegratedAssemblyFacility_Casing, 1)
            .addEnergyHatch(TextLocalization.Tooltip_IntegratedAssemblyFacility_Casing, 1)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public int getCasingTextureID() {
        return ((BlockCasings8) GregTechAPI.sBlockCasings8).getTextureIndex(7);
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
    public IStructureDefinition<IntegratedAssemblyFacility> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<IntegratedAssemblyFacility>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', ofBlock(BasicBlocks.MetaCasing, 4))
                .addElement(
                    'B',
                    ofBlocksTiered(
                        (block, meta) -> block == Loaders.componentAssemblylineCasing ? meta : -1,
                        IntStream.range(0, 13)
                            .mapToObj(i -> Pair.of(Loaders.componentAssemblylineCasing, i))
                            .collect(Collectors.toList()),
                        -2,
                        (t, meta) -> t.casingTier = meta,
                        t -> t.casingTier))
                .addElement(
                    'C',
                    buildHatchAdder(IntegratedAssemblyFacility.class)
                        .atLeast(InputBus, OutputBus, InputHatch, Energy.or(ExoticEnergy))
                        .casingIndex(((BlockCasings8) GregTechAPI.sBlockCasings8).getTextureIndex(7))
                        .dot(1)
                        .buildAndChain(onElementPass(x -> ++x.tCountCasing, ofBlock(sBlockCasings8, 7))))
                .addElement('D', ofBlock(sBlockCasings2, 6))
                .addElement('E', ofBlock(sBlockCasings9, 1))
                .addElement('F', ofBlock(sBlockCasings8, 12))
                .addElement('G', ofFrame(Materials.Neutronium))
                .addElement('H', ofBlock(sBlockCasings10, 11))
                .addElement('I', ofBlock(LanthItemList.SHIELDED_ACCELERATOR_CASING, 0))
                .addElement('J', ofBlock(LanthItemList.NIOBIUM_CAVITY_CASING, 0))
                .addElement('K', ofBlock(sBlockCasings2, 5))
                .addElement('L', ofBlock(LanthItemList.COOLANT_DELIVERY_CASING, 0))
                .addElement('M', ofBlock(IGBlocks.SpaceElevatorCasing, 1))
                .addElement('N', BorosilicateGlass.ofBoroGlass((byte) 0, (t, v) -> t.mGlassTier = v, t -> t.mGlassTier))
                .addElement('O', ofBlock(BasicBlocks.MetaBlockGlow, 31))
                .addElement('P', ofBlock(sBlockCasings6, 9))
                .addElement('Q', ofBlock(BasicBlocks.MetaCasing, 5))
                .addElement('R', ofFrame(Materials.CosmicNeutronium))
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
        casingTier = -2;
        tCountCasing = 0;
        wirelessMode = false;
        if (!checkPiece(STRUCTURE_PIECE_MAIN, HORIZONTAL_OFF_SET, VERTICAL_OFF_SET, DEPTH_OFF_SET)) return false;
        if (tCountCasing <= 1200 && !checkHatches()) {
            updateHatchTexture();
            return false;
        }
        wirelessMode = mEnergyHatches.isEmpty() && mExoticEnergyHatches.isEmpty();
        return true;
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new GTNLProcessingLogic() {

            @NotNull
            @Override
            public CheckRecipeResult process() {

                setEuModifier(getEuModifier());
                setSpeedBonus(getSpeedBonus());
                setOverclockType(OverclockType.PerfectOverclock);
                return super.process();
            }

            @Nonnull
            @Override
            protected OverclockCalculator createOverclockCalculator(@Nonnull GTRecipe recipe) {
                return wirelessMode ? OverclockCalculator.ofNoOverclock(recipe)
                    : super.createOverclockCalculator(recipe)
                        .setEUtDiscount(
                            0.4 - (ParallelTier / 50.0) * Math.pow(0.95, mGlassTier) * Math.pow(0.95, casingTier))
                        .setSpeedBoost(
                            0.1 * Math.pow(0.75, ParallelTier)
                                * Math.pow(0.95, mGlassTier)
                                * Math.pow(0.95, casingTier));
            }
        }.setMaxParallelSupplier(this::getLimitedMaxParallel);
    }

    @Override
    protected void setProcessingLogicPower(ProcessingLogic logic) {
        if (wirelessMode) {
            logic.setAvailableVoltage(getAverageInputVoltage());
            logic.setAvailableAmperage(getMaxInputAmps());
            logic.setAmperageOC(false);
        } else {
            boolean useSingleAmp = mEnergyHatches.size() == 1 && mExoticEnergyHatches.isEmpty()
                && getMaxInputAmps() <= 2;
            logic.setAvailableVoltage(getMachineVoltageLimit());
            logic.setAvailableAmperage(useSingleAmp ? 1 : getMaxInputAmps());
            logic.setAmperageOC(useSingleAmp);
        }
    }

    public long getMachineVoltageLimit() {
        if (casingTier < 0) return 0;
        if (casingTier >= 11) return GTValues.V[energyHatchTier];
        else return GTValues.V[Math.min(casingTier + 3, energyHatchTier)];
    }

    public int checkEnergyHatchTier() {
        int tier = 0;
        for (MTEHatchEnergy tHatch : validMTEList(mEnergyHatches)) {
            tier = Math.max(tHatch.mTier, tier);
        }
        for (MTEHatch tHatch : validMTEList(mExoticEnergyHatches)) {
            tier = Math.max(tHatch.mTier, tier);
        }
        return tier;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeMaps.assemblerRecipes;
    }

    @Override
    public int getWirelessModeProcessingTime() {
        return 300 - ParallelTier * 10;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setByte("mGlassTier", mGlassTier);
        aNBT.setInteger("casingTier", casingTier);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        mGlassTier = aNBT.getByte("mGlassTier");
        casingTier = aNBT.getInteger("casingTier");
    }

    @Override
    public String[] getInfoData() {
        String[] origin = super.getInfoData();
        String[] ret = new String[origin.length + 1];
        System.arraycopy(origin, 0, ret, 0, origin.length);
        ret[origin.length] = StatCollector.translateToLocal("scanner.info.CASS.tier")
            + (casingTier >= 0 ? GTValues.VN[casingTier + 1] : "None!");
        return ret;
    }

}
