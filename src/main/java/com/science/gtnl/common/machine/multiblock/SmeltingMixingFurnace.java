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

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.machine.multiMachineClasses.WirelessEnergyMultiMachineBase;
import com.science.gtnl.common.recipe.RecipeRegister;

import bartworks.API.BorosilicateGlass;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.enums.VoltageIndex;
import gregtech.api.gui.modularui.GTUITextures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.interfaces.tileentity.IWirelessEnergyHatchInformation;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import tectech.thing.casing.BlockGTCasingsTT;

public class SmeltingMixingFurnace extends WirelessEnergyMultiMachineBase<SmeltingMixingFurnace>
    implements IWirelessEnergyHatchInformation {

    protected GTRecipe lastRecipeToBuffer;
    public byte mGlassTier = 0;
    public boolean hasRequiredItem = false;
    public static final int HORIZONTAL_OFF_SET = 8;
    public static final int VERTICAL_OFF_SET = 14;
    public static final int DEPTH_OFF_SET = 0;
    private static final int MACHINEMODE_SMF = 0;
    private static final int MACHINEMODE_DTPF = 1;
    protected static final int CASING_INDEX = BlockGTCasingsTT.textureOffset;
    private static IStructureDefinition<SmeltingMixingFurnace> STRUCTURE_DEFINITION = null;
    public static final String STRUCTURE_PIECE_MAIN = "main";
    public static final String SMF_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/smelting_mixing_furnace";
    public static String[][] shape = StructureUtils.readStructureFromFile(SMF_STRUCTURE_FILE_PATH);

    public SmeltingMixingFurnace(String aName) {
        super(aName);
    }

    public SmeltingMixingFurnace(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new SmeltingMixingFurnace(this.mName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.SmeltingMixingFurnaceRecipeType)
            .addInfo(TextLocalization.Tooltip_SmeltingMixingFurnace_00)
            .addInfo(TextLocalization.Tooltip_SmeltingMixingFurnace_01)
            .addInfo(TextLocalization.Tooltip_SmeltingMixingFurnace_02)
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
            .beginStructureBlock(17, 17, 33, true)
            .addInputBus(TextLocalization.Tooltip_SmeltingMixingFurnace_Casing, 1)
            .addOutputBus(TextLocalization.Tooltip_SmeltingMixingFurnace_Casing, 1)
            .addInputHatch(TextLocalization.Tooltip_SmeltingMixingFurnace_Casing, 1)
            .addOutputHatch(TextLocalization.Tooltip_SmeltingMixingFurnace_Casing, 1)
            .addEnergyHatch(TextLocalization.Tooltip_SmeltingMixingFurnace_Casing, 1)
            .toolTipFinisher();
        return tt;
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
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(sBlockCasingsTT, 0))))
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
        mCasing = 0;
        wirelessMode = false;
        hasRequiredItem = false;
        if (!checkPiece(STRUCTURE_PIECE_MAIN, HORIZONTAL_OFF_SET, VERTICAL_OFF_SET, DEPTH_OFF_SET)) return false;
        if (mCasing <= 15 && !checkHatches() && mGlassTier < VoltageIndex.UEV) {
            updateHatchTexture();
            return false;
        }

        ItemStack requiredItem = ItemList.Transdimensional_Alignment_Matrix.get(1);
        for (ItemStack item : getAllStoredInputs()) {
            if (item != null && item.isItemEqual(requiredItem)) {
                hasRequiredItem = true;
                break;
            }
        }

        wirelessMode = mEnergyHatches.isEmpty() && mExoticEnergyHatches.isEmpty();
        energyHatchTier = checkEnergyHatchTier();
        return true;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return (machineMode == MACHINEMODE_SMF) ? RecipeRegister.SmeltingMixingFurnaceRecipes
            : RecipeMaps.plasmaForgeRecipes;
    }

    @Nonnull
    @Override
    public Collection<RecipeMap<?>> getAvailableRecipeMaps() {
        return Arrays.asList(RecipeRegister.SmeltingMixingFurnaceRecipes, RecipeMaps.plasmaForgeRecipes);
    }

    @Nonnull
    @Override
    public CheckRecipeResult checkProcessing() {

        ItemStack requiredItem = ItemList.Transdimensional_Alignment_Matrix.get(1);
        for (ItemStack item : getAllStoredInputs()) {
            if (item != null && item.isItemEqual(requiredItem)) {
                hasRequiredItem = true;
                break;
            }
        }

        if (this.getRecipeMap() == RecipeMaps.plasmaForgeRecipes) {
            if (!hasRequiredItem) {
                return CheckRecipeResultRegistry.NO_RECIPE;
            }
        }

        costingEU = BigInteger.ZERO;
        costingEUText = ZERO_STRING;
        prepareProcessing();
        if (!wirelessMode) return super.checkProcessing();

        boolean succeeded = false;
        CheckRecipeResult finalResult = CheckRecipeResultRegistry.SUCCESSFUL;
        for (int i = 0; i < cycleNum; i++) {
            CheckRecipeResult r = wirelessModeProcessOnce();
            if (!r.wasSuccessful()) {
                finalResult = r;
                break;
            }
            succeeded = true;
        }

        updateSlots();
        if (!succeeded) return finalResult;
        costingEUText = GTUtility.formatNumbers(costingEU);

        mEfficiency = 10000;
        mEfficiencyIncrease = 10000;
        mMaxProgresstime = getWirelessModeProcessingTime();

        return CheckRecipeResultRegistry.SUCCESSFUL;
    }

    @Override
    public int getWirelessModeProcessingTime() {
        return 150 - ParallelTier * 10;
    }

    @Override
    public void setMachineModeIcons() {
        machineModeIcons.clear();
        machineModeIcons.add(GTUITextures.OVERLAY_BUTTON_MACHINEMODE_LPF_METAL);
        machineModeIcons.add(GTUITextures.OVERLAY_BUTTON_MACHINEMODE_LPF_FLUID);
    }

    @Override
    public void addUIWidgets(ModularWindow.Builder builder, UIBuildContext buildContext) {
        super.addUIWidgets(builder, buildContext);
        setMachineModeIcons();
        builder.widget(createModeSwitchButton(builder));
    }

    @Override
    public final void onScrewdriverRightClick(ForgeDirection side, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        this.machineMode = (byte) ((this.machineMode + 1) % 2);
        GTUtility.sendChatToPlayer(
            aPlayer,
            StatCollector.translateToLocal("SmeltingMixingFurnace_Mode_" + this.machineMode));
    }

    @Override
    public String getMachineModeName() {
        return StatCollector.translateToLocal("SmeltingMixingFurnace_Mode_" + machineMode);
    }

    @Override
    public boolean supportsMachineModeSwitch() {
        return true;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("mode", machineMode);
        aNBT.setByte("mGlassTier", mGlassTier);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        machineMode = aNBT.getInteger("mode");
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
