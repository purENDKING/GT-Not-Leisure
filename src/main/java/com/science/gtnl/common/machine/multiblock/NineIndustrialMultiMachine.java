package com.science.gtnl.common.machine.multiblock;

import static bartworks.common.loaders.ItemRegistry.bw_realglas2;
import static com.dreammaster.gthandler.casings.GT_Container_CasingsNH.sBlockCasingsNH;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.Utils.Utils.NEGATIVE_ONE;
import static com.science.gtnl.Utils.Utils.mergeArray;
import static com.science.gtnl.common.block.Casings.BasicBlocks.MetaCasing;
import static goodgenerator.loader.Loaders.FRF_Coil_4;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTStructureUtility.ofFrame;
import static gregtech.common.misc.WirelessNetworkManager.addEUToGlobalEnergyMap;
import static kubatech.loaders.BlockLoader.defcCasingBlock;
import static tectech.thing.casing.TTCasingsContainer.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.machine.multiMachineClasses.GTNLProcessingLogic;
import com.science.gtnl.common.machine.multiMachineClasses.NineIndustrialMultiMachineManager;
import com.science.gtnl.common.machine.multiMachineClasses.WirelessEnergyMultiMachineBase;

import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.gui.modularui.GTUITextures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.interfaces.tileentity.IWirelessEnergyHatchInformation;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTLanguageManager;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import gregtech.api.util.ParallelHelper;
import gregtech.common.blocks.BlockCasings1;
import gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;

public class NineIndustrialMultiMachine extends WirelessEnergyMultiMachineBase<NineIndustrialMultiMachine>
    implements IWirelessEnergyHatchInformation {

    private int machineMode;
    private final NineIndustrialMultiMachineManager modeManager = new NineIndustrialMultiMachineManager();
    public static final String[] aToolTipNames = new String[108];
    private int mCasing;
    protected static final int CASING_INDEX = ((BlockCasings1) sBlockCasings1).getTextureIndex(12);
    private static IStructureDefinition<NineIndustrialMultiMachine> STRUCTURE_DEFINITION = null;
    public static final String STRUCTURE_PIECE_MAIN = "main";
    public static String[][] shape;
    public static final String NIMM_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/nine_industrial_multi_machine";
    public final int horizontalOffSet = 14;
    public final int verticalOffSet = 27;
    public final int depthOffSet = 0;

    static {
        for (int id = 0; id < 108; id++) {
            RecipeMap<?> recipeMap = getRecipeMap(id);
            if (recipeMap != null) {
                String aNEI = GTLanguageManager.getTranslation(getRecipeMap(id).unlocalizedName);
                aToolTipNames[id] = aNEI != null ? aNEI : "BAD NEI NAME";
            }
        }
    }

    public NineIndustrialMultiMachine(final int aID, final String aName, final String aNameRegional) {
        super(aID, aName, aNameRegional);
        shape = StructureUtils.readStructureFromFile(NIMM_STRUCTURE_FILE_PATH);
    }

    public NineIndustrialMultiMachine(final String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(final IGregTechTileEntity aTileEntity) {
        return new NineIndustrialMultiMachine(this.mName);
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        String[] aBuiltStrings = new String[36];
        for (int i = 0; i < 36; i++) {
            int baseIndex = i * 3;
            if (baseIndex + 2 < aToolTipNames.length) {
                aBuiltStrings[i] = String
                    .join(", ", aToolTipNames[baseIndex], aToolTipNames[baseIndex + 1], aToolTipNames[baseIndex + 2]);
            } else if (baseIndex + 1 < aToolTipNames.length) {
                aBuiltStrings[i] = String.join(", ", aToolTipNames[baseIndex], aToolTipNames[baseIndex + 1]);
            } else if (baseIndex < aToolTipNames.length) {
                aBuiltStrings[i] = aToolTipNames[baseIndex];
            } else {
                aBuiltStrings[i] = "";
            }
        }

        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.NineIndustrialMultiMachineRecipeType)
            .addInfo(TextLocalization.Tooltip_NineIndustrialMultiMachine_00)
            .addInfo(TextLocalization.Tooltip_NineIndustrialMultiMachine_01)
            .addInfo(TextLocalization.Tooltip_NineIndustrialMultiMachine_02)
            .addInfo(TextLocalization.Tooltip_NineIndustrialMultiMachine_03)
            .addInfo(TextLocalization.Tooltip_NineIndustrialMultiMachine_04)
            .addInfo(TextLocalization.Tooltip_NineIndustrialMultiMachine_05)
            .addInfo(TextLocalization.Tooltip_NineIndustrialMultiMachine_06)
            .addInfo(TextLocalization.Tooltip_NineIndustrialMultiMachine_07);
        for (int i = 0; i < 36; i++) {
            tt.addInfo(
                I18n.format("Tooltip_NineIndustrialMultiMachine_Mode_" + i) + " - "
                    + EnumChatFormatting.YELLOW
                    + aBuiltStrings[i]
                    + EnumChatFormatting.RESET);
        }
        tt.beginStructureBlock(29, 29, 29, true)
            .addInputBus(TextLocalization.Tooltip_NineIndustrialMultiMachine_Casing)
            .addOutputBus(TextLocalization.Tooltip_NineIndustrialMultiMachine_Casing)
            .addInputHatch(TextLocalization.Tooltip_NineIndustrialMultiMachine_Casing)
            .addOutputHatch(TextLocalization.Tooltip_NineIndustrialMultiMachine_Casing)
            .addEnergyHatch(TextLocalization.Tooltip_NineIndustrialMultiMachine_Casing)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IStructureDefinition<NineIndustrialMultiMachine> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<NineIndustrialMultiMachine>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', ofBlock(bw_realglas2, 0))
                .addElement('B', ofBlock(FRF_Coil_4, 0))
                .addElement('C', ofBlock(MetaCasing, 5))
                .addElement('D', ofBlock(defcCasingBlock, 12))
                .addElement(
                    'E',
                    buildHatchAdder(NineIndustrialMultiMachine.class).casingIndex(CASING_INDEX)
                        .dot(1)
                        .atLeast(InputHatch, OutputHatch, InputBus, OutputBus, Maintenance, Energy.or(ExoticEnergy))
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(sBlockCasings1, 12))))
                .addElement('F', ofBlock(sBlockCasings1, 13))
                .addElement('G', ofBlock(sBlockCasings1, 14))
                .addElement('H', ofBlock(sBlockCasings10, 6))
                .addElement('I', ofBlock(sBlockCasings10, 7))
                .addElement('J', ofBlock(sBlockCasings10, 11))
                .addElement('K', ofBlock(sBlockCasings5, 13))
                .addElement('L', ofBlock(sBlockCasingsNH, 12))
                .addElement('M', ofBlock(sBlockCasingsTT, 4))
                .addElement('N', ofFrame(Materials.Neutronium))
                .addElement('O', ofBlock(StabilisationFieldGenerators, 2))
                .addElement('P', ofBlock(TimeAccelerationFieldGenerator, 8))
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

    public void updateHatchTexture() {
        for (MTEHatch h : mInputHatches) h.updateTexture(getCasingTextureID());
        for (MTEHatch h : mOutputHatches) h.updateTexture(getCasingTextureID());
        for (MTEHatch h : mInputBusses) h.updateTexture(getCasingTextureID());
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        mCasing = 0;
        wirelessMode = false;

        if (!checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet)) return false;

        if (mCasing <= 256 && !checkHatch()) {
            updateHatchTexture();
            return false;
        }

        wirelessMode = mEnergyHatches.isEmpty() && mExoticEnergyHatches.isEmpty();
        return true;
    }

    protected int getCasingTextureID() {
        return CASING_INDEX;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(TexturesGtBlock.oMCAIndustrialMultiMachineActive)
                    .extFacing()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(TexturesGtBlock.oMCAIndustrialMultiMachine)
                    .extFacing()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }

    @Override
    public int getMaxParallelRecipes() {
        return Integer.MAX_VALUE;
    }

    private ItemStack getCircuit(ItemStack[] t) {
        for (ItemStack j : t) {
            if (j.getItem() == GTUtility.getIntegratedCircuit(0)
                .getItem()) {
                if (j.getItemDamage() >= 20 && j.getItemDamage() <= 22) {
                    return j;
                }
            }
        }
        return null;
    }

    private int getCircuitID(ItemStack circuit) {
        int H = circuit.getItemDamage();
        int T = (H == 20 ? 0 : (H == 21 ? 1 : (H == 22 ? 2 : -1)));
        if (T == -1) {
            throw new IllegalArgumentException("Invalid circuit item damage: " + H);
        }
        return NineIndustrialMultiMachineManager.MODE_MAP[machineMode][T];
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return null;
    }

    @Override
    public int getWirelessModeProcessingTime() {
        if (batchMode) {
            return 1;
        } else {
            return 128;
        }
    }

    @Override
    public boolean isEnablePerfectOverclock() {
        return true;
    }

    @Override
    public float getSpeedBonus() {
        return 1;
    }

    @Nonnull
    @Override
    public Collection<RecipeMap<?>> getAvailableRecipeMaps() {
        return NineIndustrialMultiMachineManager.getAvailableRecipeMaps();
    }

    private static RecipeMap<?> getRecipeMap(int mode) {
        return NineIndustrialMultiMachineManager.getRecipeMap(mode);
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new GTNLProcessingLogic() {

            private ItemStack lastCircuit = null;
            private int lastMode = -1;

            @NotNull
            @Override
            public CheckRecipeResult process() {
                setEuModifier(0);
                return super.process();
            }

            @Nonnull
            @Override
            protected Stream<GTRecipe> findRecipeMatches(@Nullable RecipeMap<?> map) {
                ItemStack circuit = getCircuit(inputItems);
                if (circuit == null) {
                    return Stream.empty();
                }
                if (!GTUtility.areStacksEqual(circuit, lastCircuit)) {
                    lastRecipe = null;
                    lastCircuit = circuit;
                }
                if (machineMode != lastMode) {
                    lastRecipe = null;
                    lastMode = machineMode;
                }
                RecipeMap<?> foundMap = getRecipeMap(getCircuitID(circuit));
                if (foundMap == null) {
                    return Stream.empty();
                }
                return super.findRecipeMatches(foundMap);
            }

            @Nonnull
            @Override
            protected ParallelHelper createParallelHelper(@Nonnull GTRecipe recipe) {
                return new ParallelHelper().setRecipe(recipe)
                    .setItemInputs(inputItems)
                    .setFluidInputs(inputFluids)
                    .setAvailableEUt(Integer.MAX_VALUE)
                    .setMachine(machine, protectItems, protectFluids)
                    .setRecipeLocked(recipeLockableMachine, isRecipeLocked)
                    .setEUtModifier(0)
                    .setMaxParallel(Integer.MAX_VALUE)
                    .setConsumption(true)
                    .setOutputCalculation(true);
            }

            @Override
            protected double calculateDuration(@Nonnull GTRecipe recipe, @Nonnull ParallelHelper helper,
                @Nonnull OverclockCalculator calculator) {
                if (batchMode) {
                    return 1;
                } else {
                    return 128;
                }
            }

            @NotNull
            @Override
            protected CheckRecipeResult validateRecipe(@NotNull GTRecipe recipe) {
                return CheckRecipeResultRegistry.SUCCESSFUL;
            }
        };
    }

    @Override
    public String getMachineModeName() {
        return StatCollector.translateToLocal("NineIndustrialMultiMachine_Mode_" + machineMode);
    }

    @Override
    public String[] getInfoData() {
        String[] data = super.getInfoData();
        ArrayList<String> mInfo = new ArrayList<>(Arrays.asList(data));
        String mode = StatCollector
            .translateToLocal(NineIndustrialMultiMachineManager.getModeLocalization(machineMode));
        mInfo.add(mode);
        return mInfo.toArray(new String[0]);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        aNBT.setInteger("mInternalMode", machineMode);
        super.saveNBTData(aNBT);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        if (aNBT.hasKey("mInternalMode")) {
            machineMode = aNBT.getInteger("mInternalMode");
        }
        super.loadNBTData(aNBT);
    }

    @Override
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y,
        int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        tag.setInteger("mode", machineMode);
    }

    @Override
    public void getWailaBody(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        super.getWailaBody(itemStack, currentTip, accessor, config);
        final NBTTagCompound tag = accessor.getNBTData();
        if (tag.hasKey("mode")) {
            currentTip.add(
                StatCollector.translateToLocal("GT5U.machines.oreprocessor1") + " "
                    + EnumChatFormatting.WHITE
                    + StatCollector.translateToLocal("NineIndustrialMultiMachine_Mode_" + tag.getInteger("mode"))
                    + EnumChatFormatting.RESET);
        }
    }

    @Override
    public boolean supportsMachineModeSwitch() {
        return true;
    }

    @Override
    public int nextMachineMode() {
        machineMode = modeManager.getNextMachineMode(machineMode);
        return machineMode;
    }

    @Override
    public void addUIWidgets(ModularWindow.Builder builder, UIBuildContext buildContext) {
        super.addUIWidgets(builder, buildContext);
        setMachineModeIcons();
        builder.widget(createModeSwitchButton(builder));
    }

    public void setMachineModeIcons() {
        machineModeIcons.clear();
        machineModeIcons.add(GTUITextures.OVERLAY_BUTTON_MACHINEMODE_DEFAULT);
        for (int i = 0; i <= 35; i++) {
            machineModeIcons.add(GTUITextures.OVERLAY_BUTTON_MACHINEMODE_DEFAULT);
        }
    }

    @Nonnull
    @Override
    public CheckRecipeResult checkProcessing() {
        costingEU = BigInteger.ZERO;
        costingEUText = ZERO_STRING;
        prepareProcessing();

        if (!wirelessMode) return handleNonWirelessModeProcessing();

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
    public CheckRecipeResult wirelessModeProcessOnce() {
        if (!isRecipeProcessing) startRecipeProcessing();
        setupProcessingLogic(processingLogic);
        setupWirelessProcessingPowerLogic(processingLogic);

        CheckRecipeResult result = doCheckRecipe();
        if (!result.wasSuccessful()) {
            return result;
        }

        BigInteger costEU = BigInteger.valueOf(processingLogic.getCalculatedEut())
            .multiply(BigInteger.valueOf(processingLogic.getDuration()));

        int m = getExtraEUCostMultiplier();
        if (m > 1) {
            costEU = costEU.multiply(BigInteger.valueOf(m));
        }

        if (!addEUToGlobalEnergyMap(ownerUUID, costEU.multiply(NEGATIVE_ONE))) {
            return CheckRecipeResultRegistry.insufficientPower(costEU.longValue());
        }

        costingEU = costingEU.add(costEU);

        ItemStack[] outputItems = processingLogic.getOutputItems();
        if (outputItems != null) {
            for (ItemStack itemStack : outputItems) {
                if (itemStack != null) {
                    if (batchMode) {
                        itemStack.stackSize = Integer.MAX_VALUE;
                    }
                }
            }
        }
        mOutputItems = mergeArray(mOutputItems, outputItems);

        FluidStack[] outputFluids = processingLogic.getOutputFluids();
        if (outputFluids != null) {
            for (FluidStack fluidStack : outputFluids) {
                if (fluidStack != null) {
                    if (batchMode) {
                        fluidStack.amount = Integer.MAX_VALUE;
                    }
                }
            }
        }
        mOutputFluids = mergeArray(mOutputFluids, outputFluids);

        endRecipeProcessing();
        return result;
    }

    @Nonnull
    private CheckRecipeResult handleNonWirelessModeProcessing() {
        if (processingLogic == null) {
            return checkRecipe(mInventory[1]) ? CheckRecipeResultRegistry.SUCCESSFUL
                : CheckRecipeResultRegistry.NO_RECIPE;
        }

        setupProcessingLogic(processingLogic);

        CheckRecipeResult result = doCheckRecipe();
        result = postCheckRecipe(result, processingLogic);
        updateSlots();
        if (!result.wasSuccessful()) return result;

        mEfficiency = 10000;
        mEfficiencyIncrease = 10000;
        if (batchMode) {
            mMaxProgresstime = 1;
        } else {
            mMaxProgresstime = 128;
        }
        setEnergyUsage(processingLogic);

        ItemStack[] outputItems = processingLogic.getOutputItems();
        if (outputItems != null) {
            for (ItemStack itemStack : outputItems) {
                if (itemStack != null) {
                    if (batchMode) {
                        itemStack.stackSize = Integer.MAX_VALUE;
                    }
                }
            }
        }
        mOutputItems = outputItems;

        FluidStack[] outputFluids = processingLogic.getOutputFluids();
        if (outputFluids != null) {
            for (FluidStack fluidStack : outputFluids) {
                if (fluidStack != null) {
                    if (batchMode) {
                        fluidStack.amount = Integer.MAX_VALUE;
                    }
                }
            }
        }
        mOutputFluids = outputFluids;
        return result;
    }
}
