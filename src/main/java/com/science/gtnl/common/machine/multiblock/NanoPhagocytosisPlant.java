package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.common.block.Casings.BasicBlocks.MetaCasing;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTStructureUtility.ofFrame;
import static gtPlusPlus.core.block.ModBlocks.blockCasingsMisc;
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
import com.science.gtnl.misc.OverclockType;

import bartworks.API.BorosilicateGlass;
import goodgenerator.loader.Loaders;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.interfaces.tileentity.IWirelessEnergyHatchInformation;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import gregtech.common.blocks.BlockCasings9;
import gtnhlanth.common.register.LanthItemList;

public class NanoPhagocytosisPlant extends WirelessEnergyMultiMachineBase<NanoPhagocytosisPlant>
    implements IWirelessEnergyHatchInformation {

    private byte mGlassTier = 0;
    private static final int HORIZONTAL_OFF_SET = 10;
    private static final int VERTICAL_OFF_SET = 22;
    private static final int DEPTH_OFF_SET = 0;
    private int tCountCasing = 0;
    private static IStructureDefinition<NanoPhagocytosisPlant> STRUCTURE_DEFINITION = null;
    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final String NPP_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/nano_phagocytosis_plant"; // 文件路径
    private static final String[][] shape = StructureUtils.readStructureFromFile(NPP_STRUCTURE_FILE_PATH);

    public NanoPhagocytosisPlant(String aName) {
        super(aName);
    }

    public NanoPhagocytosisPlant(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new NanoPhagocytosisPlant(this.mName);
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.NanoPhagocytosisPlantRecipeType)
            .addInfo(TextLocalization.Tooltip_NanoPhagocytosisPlant_00)
            .addInfo(TextLocalization.Tooltip_NanoPhagocytosisPlant_01)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_00)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_01)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_02)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_03)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_04)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_05)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_06)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_07)
            .addInfo(String.format(TextLocalization.Tooltip_WirelessEnergyMultiMachine_08, "150"))
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_09)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_10)
            .addInfo(TextLocalization.Tooltip_Tectech_Hatch)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(21, 24, 38, true)
            .addInputBus(TextLocalization.Tooltip_NanoPhagocytosisPlant_Casing, 1)
            .addOutputBus(TextLocalization.Tooltip_NanoPhagocytosisPlant_Casing, 1)
            .addEnergyHatch(TextLocalization.Tooltip_NanoPhagocytosisPlant_Casing, 1)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public int getCasingTextureID() {
        return ((BlockCasings9) GregTechAPI.sBlockCasings9).getTextureIndex(12);
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
    public IStructureDefinition<NanoPhagocytosisPlant> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<NanoPhagocytosisPlant>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', ofBlock(MetaCasing, 2))
                .addElement(
                    'B',
                    buildHatchAdder(NanoPhagocytosisPlant.class).atLeast(InputBus, OutputBus, Energy.or(ExoticEnergy))
                        .casingIndex(((BlockCasings9) GregTechAPI.sBlockCasings9).getTextureIndex(12))
                        .dot(1)
                        .buildAndChain(onElementPass(x -> ++x.tCountCasing, ofBlock(sBlockCasings9, 12))))
                .addElement('C', ofBlock(sBlockCasingsTT, 0))
                .addElement('D', ofBlock(sBlockCasings10, 7))
                .addElement('E', ofBlock(sBlockCasings8, 7))
                .addElement('F', ofBlock(sBlockCasings1, 15))
                .addElement('G', ofBlock(sBlockCasings4, 7))
                .addElement('H', ofBlock(sBlockCasings4, 11))
                .addElement('I', ofBlock(sBlockCasings4, 12))
                .addElement('J', ofBlock(Loaders.compactFusionCoil, 2))
                .addElement('K', ofBlock(sBlockCasings10, 3))
                .addElement('L', ofBlock(LanthItemList.SHIELDED_ACCELERATOR_CASING, 0))
                .addElement('M', ofBlock(sBlockCasings8, 11))
                .addElement('N', ofBlock(sBlockCasings9, 13))
                .addElement('O', ofFrame(Materials.EnrichedHolmium))
                .addElement('P', ofBlock(sBlockCasings8, 10))
                .addElement('Q', BorosilicateGlass.ofBoroGlass((byte) 0, (t, v) -> t.mGlassTier = v, t -> t.mGlassTier))
                .addElement('R', ofBlock(sBlockMetal5, 1))
                .addElement('S', ofBlock(sBlockCasings10, 8))
                .addElement('T', ofBlock(MetaCasing, 4))
                .addElement('U', ofBlock(sBlockCasingsTT, 6))
                .addElement('V', ofBlock(sBlockCasings3, 10))
                .addElement('W', ofBlock(MetaCasing, 18))
                .addElement('X', ofBlock(blockCasingsMisc, 5))
                .addElement('Y', ofBlock(Loaders.compactFusionCoil, 0))
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

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        repairMachine();
        tCountCasing = 0;
        wirelessMode = false;
        if (!checkPiece(STRUCTURE_PIECE_MAIN, HORIZONTAL_OFF_SET, VERTICAL_OFF_SET, DEPTH_OFF_SET)) return false;
        if (tCountCasing <= 200 && !checkHatches()) {
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
                        .setEUtDiscount(0.4 - (ParallelTier / 50.0) * Math.pow(0.95, mGlassTier))
                        .setSpeedBoost(0.1 * Math.pow(0.75, ParallelTier) * Math.pow(0.95, mGlassTier));
            }
        }.setMaxParallelSupplier(this::getLimitedMaxParallel);
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeMaps.maceratorRecipes;
    }

    @Override
    public int getWirelessModeProcessingTime() {
        return 150 - ParallelTier * 10;
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

}
