package com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTUtility.filterValidMTEs;
import static gregtech.api.util.GTUtility.validMTEList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableList;
import com.gtnewhorizon.structurelib.alignment.IAlignmentLimits;
import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.SoundResource;
import gregtech.api.enums.Textures;
import gregtech.api.gui.modularui.GTUITextures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IItemLockable;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.implementations.*;
import gregtech.api.objects.GTRenderedTexture;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import gregtech.common.blocks.BlockCasings1;
import gregtech.common.blocks.BlockCasings2;
import gregtech.common.tileentities.machines.MTEHatchCraftingInputME;
import gregtech.common.tileentities.machines.MTEHatchInputBusME;
import gregtech.common.tileentities.machines.MTEHatchOutputBusME;
import gtPlusPlus.api.recipe.GTPPRecipeMaps;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.MTEHatchInputBattery;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.MTEHatchOutputBattery;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.MTEHatchSteamBusOutput;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.MteHatchSteamBusInput;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.MTEHatchCustomFluidBase;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.MTESteamMultiBase;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;

public class LargeSteamOreWasher extends MTESteamMultiBase<LargeSteamOreWasher> implements ISurvivalConstructable {

    private static final int MACHINEMODE_OREWASH = 0;
    private static final int MACHINEMODE_SIMPLEWASH = 1;

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new LargeSteamOreWasher(this.mName);
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        if (machineMode == MACHINEMODE_SIMPLEWASH) {
            return GTPPRecipeMaps.simpleWasherRecipes;
        }
        return RecipeMaps.oreWasherRecipes;
    }

    @NotNull
    @Override
    public Collection<RecipeMap<?>> getAvailableRecipeMaps() {
        return Arrays.asList(GTPPRecipeMaps.simpleWasherRecipes, RecipeMaps.oreWasherRecipes);
    }

    @Override
    public String getMachineType() {
        return TextLocalization.LargeSteamOreWasherRecipeType;
    }

    public static final String STRUCTURE_PIECE_MAIN = "main";

    public IStructureDefinition<LargeSteamOreWasher> STRUCTURE_DEFINITION = null;

    public static final String LSC_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/large_steam_ore_washer";

    public String[][] shape;

    public LargeSteamOreWasher(String aName) {
        super(aName);
    }

    public LargeSteamOreWasher(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        this.shape = StructureUtils.readStructureFromFile(LSC_STRUCTURE_FILE_PATH);
    }

    public static final int HORIZONTAL_OFF_SET = 4;
    public static final int VERTICAL_OFF_SET = 4;
    public static final int DEPTH_OFF_SET = 0;

    public boolean isBroken = true;

    public int tierPipeCasing = -1;
    public int tierMachineCasing = -1;
    public int tierMachine = 1;

    public int tCountCasing = 0;

    public int getTierMachineCasing(Block block, int meta) {
        if (block == sBlockCasings1 && 10 == meta) {
            tCountCasing++;
            return 1;
        }
        if (block == sBlockCasings2 && 0 == meta) {
            tCountCasing++;
            return 2;
        }
        return 0;
    }

    public static int getTierPipeCasing(Block block, int meta) {
        if (block == sBlockCasings2 && 12 == meta) return 1;
        if (block == sBlockCasings2 && 13 == meta) return 2;
        return 0;
    }

    protected void updateHatchTexture() {
        for (MTEHatch h : mSteamInputs) h.updateTexture(getCasingTextureID());
        for (MTEHatch h : mSteamOutputs) h.updateTexture(getCasingTextureID());
        for (MTEHatch h : mSteamInputFluids) h.updateTexture(getCasingTextureID());
        for (MTEHatch h : mInputBusses) h.updateTexture(getCasingTextureID());
        for (MTEHatch h : mOutputBusses) h.updateTexture(getCasingTextureID());
    }

    public int getCasingTextureID() {
        if (tierPipeCasing == 2 || tierMachineCasing == 2)
            return ((BlockCasings2) GregTechAPI.sBlockCasings2).getTextureIndex(0);
        return ((BlockCasings1) GregTechAPI.sBlockCasings1).getTextureIndex(10);
    }

    @Override
    public void onValueUpdate(byte aValue) {
        tierMachineCasing = aValue;
    }

    @Override
    public byte getUpdateData() {
        return (byte) tierMachineCasing;
    }

    @Override
    protected GTRenderedTexture getFrontOverlay() {
        return new GTRenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_STEAM_WASHER);
    }

    @Override
    protected GTRenderedTexture getFrontOverlayActive() {
        return new GTRenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_STEAM_WASHER_ACTIVE);
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final ForgeDirection side,
        final ForgeDirection facing, final int aColorIndex, final boolean aActive, final boolean aRedstone) {
        if (side == facing) {
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                aActive ? getFrontOverlayActive() : getFrontOverlay() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }

    @Override
    public IStructureDefinition<LargeSteamOreWasher> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<LargeSteamOreWasher>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement(
                    'A',
                    ofChain(
                        buildSteamInput(LargeSteamOreWasher.class)
                            .casingIndex(((BlockCasings1) GregTechAPI.sBlockCasings1).getTextureIndex(10))
                            .dot(1)
                            .build(),
                        buildHatchAdder(LargeSteamOreWasher.class)
                            .atLeast(
                                SteamHatchElement.InputBus_Steam,
                                SteamHatchElement.OutputBus_Steam,
                                InputBus,
                                OutputBus,
                                InputHatch)
                            .casingIndex(((BlockCasings1) GregTechAPI.sBlockCasings1).getTextureIndex(10))
                            .dot(1)
                            .buildAndChain(),
                        ofBlocksTiered(
                            this::getTierMachineCasing,
                            ImmutableList.of(Pair.of(sBlockCasings1, 10), Pair.of(sBlockCasings2, 0)),
                            -1,
                            (t, m) -> t.tierMachineCasing = m,
                            t -> t.tierMachineCasing)))
                .addElement(
                    'B',
                    ofBlocksTiered(
                        LargeSteamOreWasher::getTierPipeCasing,
                        ImmutableList.of(Pair.of(sBlockCasings2, 12), Pair.of(sBlockCasings2, 13)),
                        -1,
                        (t, m) -> t.tierPipeCasing = m,
                        t -> t.tierPipeCasing))
                .addElement('C', ofBlockAnyMeta(Blocks.glass))
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

    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        tierPipeCasing = -1;
        tierMachineCasing = -1;
        tCountCasing = 0;
        if (!checkPiece(STRUCTURE_PIECE_MAIN, HORIZONTAL_OFF_SET, VERTICAL_OFF_SET, DEPTH_OFF_SET)) return false;
        if (tierPipeCasing < 0 && tierMachineCasing < 0) return false;
        if (tierPipeCasing == 1 && tierMachineCasing == 1 && tCountCasing >= 195 && checkHatches()) {
            updateHatchTexture();
            tierMachine = 1;
            return true;
        }
        if (tierPipeCasing == 2 && tierMachineCasing == 2 && tCountCasing >= 195 && checkHatches()) {
            updateHatchTexture();
            tierMachine = 2;
            return true;
        }
        return false;
    }

    public boolean checkHatches() {
        return !mSteamInputFluids.isEmpty();
    }

    @Override
    public int getMaxParallelRecipes() {
        if (tierMachine == 1) {
            return 32;
        } else if (tierMachine == 2) {
            return 64;
        }
        return 32;
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {

        return new ProcessingLogic() {

            @NotNull
            @Override
            protected CheckRecipeResult validateRecipe(@Nonnull GTRecipe recipe) {
                if (availableVoltage < recipe.mEUt) {
                    return CheckRecipeResultRegistry.insufficientPower(recipe.mEUt);
                } else return CheckRecipeResultRegistry.SUCCESSFUL;
            }

            @Override
            @Nonnull
            protected OverclockCalculator createOverclockCalculator(@NotNull GTRecipe recipe) {
                return OverclockCalculator.ofNoOverclock(recipe)
                    .setEUtDiscount(0.75 * tierMachine)
                    .setSpeedBoost(0.6 / tierMachine);
            }
        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }

    @Override
    public int getTierRecipes() {
        return 2;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        final MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.LargeSteamOreWasherRecipeType)
            .addInfo(TextLocalization.Tooltip_LargeSteamOreWasher_00)
            .addInfo(TextLocalization.Tooltip_LargeSteamOreWasher_01)
            .addInfo(TextLocalization.Tooltip_LargeSteamOreWasher_02)
            .addInfo(TextLocalization.HIGH_PRESSURE_TOOLTIP_NOTICE)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(9, 5, 9, false)
            .addInputBus(TextLocalization.Tooltip_LargeSteamOreWasher_Casing, 1)
            .addOutputBus(TextLocalization.Tooltip_LargeSteamOreWasher_Casing, 1)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public String[] getInfoData() {
        ArrayList<String> info = new ArrayList<>(Arrays.asList(super.getInfoData()));
        info.add("Machine Tier: " + EnumChatFormatting.YELLOW + tierMachine);
        info.add("Parallel: " + EnumChatFormatting.YELLOW + getMaxParallelRecipes());
        return info.toArray(new String[0]);
    }

    @Override
    public void getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        super.getWailaBody(itemStack, currenttip, accessor, config);
        NBTTagCompound tag = accessor.getNBTData();
        currenttip.add(
            StatCollector.translateToLocal("GTPP.machines.tier") + ": "
                + EnumChatFormatting.YELLOW
                + getSteamTierTextForWaila(tag)
                + EnumChatFormatting.RESET);
        currenttip.add(
            StatCollector.translateToLocal("GT5U.multiblock.curparallelism") + ": "
                + EnumChatFormatting.BLUE
                + tag.getInteger("parallel")
                + EnumChatFormatting.RESET);
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected SoundResource getActivitySoundLoop() {
        return SoundResource.GT_MACHINES_STEAM_WASHER_LOOP;
    }

    @Override
    public boolean supportsMachineModeSwitch() {
        return true;
    }

    @Override
    public int nextMachineMode() {
        if (machineMode == MACHINEMODE_OREWASH) return MACHINEMODE_SIMPLEWASH;
        else return MACHINEMODE_OREWASH;
    }

    @Override
    public void setMachineModeIcons() {
        machineModeIcons.clear();
        machineModeIcons.add(GTUITextures.OVERLAY_BUTTON_MACHINEMODE_WASHPLANT);
        machineModeIcons.add(GTUITextures.OVERLAY_BUTTON_MACHINEMODE_SIMPLEWASHER);
    }

    @Override
    public String getMachineModeName() {
        return StatCollector.translateToLocal("LargeSteamOreWasher_Mode_" + machineMode);
    }

    @Override
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y,
        int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        tag.setInteger("tierMachine", tierMachine);
        tag.setInteger("parallel", getMaxParallelRecipes());
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("tierMachine", tierMachine);
        aNBT.setInteger("mode", machineMode);
    }

    @Override
    public void loadNBTData(final NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        tierMachine = aNBT.getInteger("tierMachine");
        machineMode = aNBT.getInteger("mode");
    }

    @Override
    protected IAlignmentLimits getInitialAlignmentLimits() {
        return (d, r, f) -> d.offsetY == 0 && r.isNotRotated();
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (aBaseMetaTileEntity.isServerSide()) {
            if (mUpdate < -250) mUpdate = 50;
            if ((aTick % 1200) == 0) {
                isBroken = true;
            }
        }
    }

    @Override
    public ArrayList<ItemStack> getStoredInputs() {
        ArrayList<ItemStack> rList = new ArrayList<>();
        Map<GTUtility.ItemId, ItemStack> inputsFromME = new HashMap<>();
        for (MteHatchSteamBusInput tHatch : validMTEList(mSteamInputs)) {
            tHatch.mRecipeMap = getRecipeMap();
            for (int i = tHatch.getBaseMetaTileEntity()
                .getSizeInventory() - 1; i >= 0; i--) {
                if (tHatch.getBaseMetaTileEntity()
                    .getStackInSlot(i) != null) {
                    rList.add(
                        tHatch.getBaseMetaTileEntity()
                            .getStackInSlot(i));
                }
            }
        }
        for (MTEHatchInputBus tHatch : validMTEList(mInputBusses)) {
            if (tHatch instanceof MTEHatchCraftingInputME) {
                continue;
            }
            tHatch.mRecipeMap = getRecipeMap();
            IGregTechTileEntity tileEntity = tHatch.getBaseMetaTileEntity();
            boolean isMEBus = tHatch instanceof MTEHatchInputBusME;
            for (int i = tileEntity.getSizeInventory() - 1; i >= 0; i--) {
                ItemStack itemStack = tileEntity.getStackInSlot(i);
                if (itemStack != null) {
                    if (isMEBus) {
                        inputsFromME.put(GTUtility.ItemId.createNoCopy(itemStack), itemStack);
                    } else {
                        rList.add(itemStack);
                    }
                }
            }
        }

        ItemStack stackInSlot1 = getStackInSlot(1);
        if (stackInSlot1 != null && stackInSlot1.getUnlocalizedName()
            .startsWith("gt.integrated_circuit")) rList.add(stackInSlot1);
        if (!inputsFromME.isEmpty()) {
            rList.addAll(inputsFromME.values());
        }
        return rList;
    }

    @Override
    public ArrayList<ItemStack> getStoredOutputs() {
        ArrayList<ItemStack> rList = new ArrayList<>();

        if (mOutputBusses != null && !mOutputBusses.isEmpty()) {
            for (MTEHatchOutputBus tHatch : validMTEList(mOutputBusses)) {
                IGregTechTileEntity baseMetaTileEntity = tHatch.getBaseMetaTileEntity();
                for (int i = baseMetaTileEntity.getSizeInventory() - 1; i >= 0; i--) {
                    rList.add(baseMetaTileEntity.getStackInSlot(i));
                }
            }
        }

        if (mSteamOutputs != null && !mSteamOutputs.isEmpty()) {
            for (MTEHatchSteamBusOutput tHatch : validMTEList(mSteamOutputs)) {
                for (int i = tHatch.getBaseMetaTileEntity()
                    .getSizeInventory() - 1; i >= 0; i--) {
                    rList.add(
                        tHatch.getBaseMetaTileEntity()
                            .getStackInSlot(i));
                }
            }
        }

        return rList;
    }

    @Override
    public List<ItemStack> getItemOutputSlots(ItemStack[] toOutput) {
        List<ItemStack> ret = new ArrayList<>();

        if (mOutputBusses != null && !mOutputBusses.isEmpty()) {
            for (final MTEHatch tBus : validMTEList(mOutputBusses)) {
                if (!(tBus instanceof MTEHatchOutputBusME)) {
                    final IInventory tBusInv = tBus.getBaseMetaTileEntity();
                    for (int i = 0; i < tBusInv.getSizeInventory(); i++) {
                        final ItemStack stackInSlot = tBus.getStackInSlot(i);

                        if (stackInSlot == null && tBus instanceof IItemLockable lockable && lockable.isLocked()) {
                            assert lockable.getLockedItem() != null;
                            ItemStack fakeItemStack = lockable.getLockedItem()
                                .copy();
                            fakeItemStack.stackSize = 0;
                            ret.add(fakeItemStack);
                        } else {
                            ret.add(stackInSlot);
                        }
                    }
                }
            }
        }

        if (mSteamOutputs != null && !mSteamOutputs.isEmpty()) {
            for (final MTEHatch tBus : validMTEList(mSteamOutputs)) {
                final IInventory tBusInv = tBus.getBaseMetaTileEntity();
                for (int i = 0; i < tBusInv.getSizeInventory(); i++) {
                    ret.add(tBus.getStackInSlot(i));
                }
            }
        }

        return ret;
    }

    private boolean dumpItem(List<MTEHatchOutputBus> outputBuses, ItemStack itemStack, boolean restrictiveBusesOnly) {
        for (MTEHatchOutputBus outputBus : outputBuses) {
            if (restrictiveBusesOnly && !outputBus.isLocked()) {
                continue;
            }

            if (outputBus.storeAll(itemStack)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean addOutput(ItemStack aStack) {
        if (GTUtility.isStackInvalid(aStack)) return false;
        aStack = GTUtility.copy(aStack);
        boolean outputSuccess = true;
        final List<MTEHatchOutputBus> filteredBuses = filterValidMTEs(mOutputBusses);
        if (dumpItem(filteredBuses, aStack, true) || dumpItem(filteredBuses, aStack, false)) {
            return true;
        }

        while (outputSuccess && aStack.stackSize > 0) {
            outputSuccess = false;
            ItemStack single = aStack.splitStack(1);
            for (MTEHatchSteamBusOutput tHatch : validMTEList(mSteamOutputs)) {
                if (!outputSuccess) {
                    for (int i = tHatch.getSizeInventory() - 1; i >= 0 && !outputSuccess; i--) {
                        if (tHatch.getBaseMetaTileEntity()
                            .addStackToSlot(i, single)) outputSuccess = true;
                    }
                }
            }
            for (MTEHatchOutput tHatch : validMTEList(mOutputHatches)) {
                if (!outputSuccess && tHatch.outputsItems()) {
                    if (tHatch.getBaseMetaTileEntity()
                        .addStackToSlot(1, single)) outputSuccess = true;
                }
            }
        }
        return outputSuccess;
    }

    @Override
    public void updateSlots() {
        for (MTEHatchCustomFluidBase tHatch : validMTEList(mSteamInputFluids)) tHatch.updateSlots();
        for (MteHatchSteamBusInput tHatch : validMTEList(mSteamInputs)) tHatch.updateSlots();
        for (MTEHatchInput tHatch : validMTEList(mInputHatches)) tHatch.updateSlots();
        for (MTEHatchInputBus tHatch : validMTEList(mInputBusses)) tHatch.updateSlots();
        for (final MTEHatchInputBattery tHatch : validMTEList(this.mChargeHatches)) {
            tHatch.updateSlots();
        }
        for (final MTEHatchOutputBattery tHatch : validMTEList(this.mDischargeHatches)) {
            tHatch.updateSlots();
        }
        super.updateSlots();
    }
}
