package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.enums.Mods.IndustrialCraft2;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTStructureUtility.ofFrame;

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
import com.science.gtnl.common.block.Casings.BasicBlocks;
import com.science.gtnl.common.machine.multiMachineClasses.GTNLProcessingLogic;
import com.science.gtnl.common.machine.multiMachineClasses.WirelessEnergyMultiMachineBase;
import com.science.gtnl.misc.OverclockType;

import bartworks.API.BorosilicateGlass;
import cpw.mods.fml.common.registry.GameRegistry;
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
import gregtech.common.blocks.BlockCasings1;
import gregtech.common.blocks.BlockCasings9;
import tectech.thing.casing.TTCasingsContainer;

public class NeutroniumWireCutting extends WirelessEnergyMultiMachineBase<NeutroniumWireCutting>
    implements IWirelessEnergyHatchInformation {

    private byte mGlassTier = 0;
    private static final int HORIZONTAL_OFF_SET = 3;
    private static final int VERTICAL_OFF_SET = 10;
    private static final int DEPTH_OFF_SET = 0;
    private int tCountCasing = 0;
    private static IStructureDefinition<NeutroniumWireCutting> STRUCTURE_DEFINITION = null;
    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final String NWC_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/neutronium_wire_cutting"; // 文件路径
    private static final String[][] shape = StructureUtils.readStructureFromFile(NWC_STRUCTURE_FILE_PATH);

    public NeutroniumWireCutting(String aName) {
        super(aName);
    }

    public NeutroniumWireCutting(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new NeutroniumWireCutting(this.mName);
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.NeutroniumWireCuttingRecipeType)
            .addInfo(TextLocalization.Tooltip_NeutroniumWireCutting_00)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_00)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_01)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_02)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_03)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_04)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_05)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_06)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_07)
            .addInfo(String.format(TextLocalization.Tooltip_WirelessEnergyMultiMachine_08, "200"))
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_09)
            .addInfo(TextLocalization.Tooltip_WirelessEnergyMultiMachine_10)
            .addInfo(TextLocalization.Tooltip_Tectech_Hatch)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(31, 14, 15, true)
            .addInputBus(TextLocalization.Tooltip_NeutroniumWireCutting_Casing, 1)
            .addOutputBus(TextLocalization.Tooltip_NeutroniumWireCutting_Casing, 1)
            .addInputHatch(TextLocalization.Tooltip_NeutroniumWireCutting_Casing, 1)
            .addEnergyHatch(TextLocalization.Tooltip_NeutroniumWireCutting_Casing, 1)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public int getCasingTextureID() {
        return ((BlockCasings1) GregTechAPI.sBlockCasings1).getTextureIndex(13);
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
    public IStructureDefinition<NeutroniumWireCutting> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<NeutroniumWireCutting>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', BorosilicateGlass.ofBoroGlass((byte) 0, (t, v) -> t.mGlassTier = v, t -> t.mGlassTier))
                .addElement('B', ofBlock(BasicBlocks.MetaCasing, 2))
                .addElement('C', ofBlockAnyMeta(GameRegistry.findBlock(IndustrialCraft2.ID, "blockAlloyGlass")))
                .addElement('D', ofBlock(sBlockCasings10, 6))
                .addElement('E', ofBlock(sBlockCasings10, 7))
                .addElement('F', ofBlock(sBlockCasings10, 11))
                .addElement('G', ofBlock(sBlockCasings3, 11))
                .addElement('H', ofBlock(sBlockCasings4, 10))
                .addElement('I', ofBlock(sBlockCasings8, 7))
                .addElement('J', ofBlock(sBlockCasings9, 3))
                .addElement('K', ofBlock(sBlockCasings9, 6))
                .addElement(
                    'L',
                    buildHatchAdder(NeutroniumWireCutting.class)
                        .atLeast(InputBus, OutputBus, InputHatch, Energy.or(ExoticEnergy))
                        .casingIndex(((BlockCasings9) GregTechAPI.sBlockCasings9).getTextureIndex(12))
                        .dot(1)
                        .buildAndChain(onElementPass(x -> ++x.tCountCasing, ofBlock(sBlockCasings9, 12))))
                .addElement('M', ofBlock(TTCasingsContainer.sBlockCasingsTT, 0))
                .addElement('N', ofBlock(TTCasingsContainer.sBlockCasingsTT, 6))
                .addElement('O', ofFrame(Materials.Neutronium))
                .addElement('P', ofBlockAnyMeta(GameRegistry.findBlock("miscutils", "blockFrameGtHastelloyN")))
                .addElement('Q', ofBlock(BasicBlocks.MetaCasing, 4))
                .addElement('R', ofBlock(BasicBlocks.MetaCasing, 5))
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

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        repairMachine();
        tCountCasing = 0;
        wirelessMode = false;
        if (!checkPiece(STRUCTURE_PIECE_MAIN, HORIZONTAL_OFF_SET, VERTICAL_OFF_SET, DEPTH_OFF_SET)) return false;
        wirelessMode = mEnergyHatches.isEmpty() && mExoticEnergyHatches.isEmpty();
        return tCountCasing > 900;
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
        return RecipeMaps.cutterRecipes;
    }

    @Override
    public int getWirelessModeProcessingTime() {
        return 200 - ParallelTier * 10;
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
