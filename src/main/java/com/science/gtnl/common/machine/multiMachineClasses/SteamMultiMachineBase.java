package com.science.gtnl.common.machine.multiMachineClasses;

import static bartworks.system.material.WerkstoffLoader.BWBlockCasings;
import static com.science.gtnl.common.block.Casings.BasicBlocks.MetaBlockColumn;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.GregTechAPI.sBlockFrames;
import static gregtech.api.enums.Mods.GregTech;
import static gregtech.api.metatileentity.BaseTileEntity.TOOLTIP_DELAY;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTUtility.filterValidMTEs;
import static gregtech.api.util.GTUtility.validMTEList;
import static gtPlusPlus.core.block.ModBlocks.blockCustomMachineCasings;

import java.util.*;

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
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.gtnewhorizons.modularui.api.drawable.ItemDrawable;
import com.gtnewhorizons.modularui.api.drawable.UITexture;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.gtnewhorizons.modularui.common.widget.DrawableWidget;
import com.gtnewhorizons.modularui.common.widget.FakeSyncWidget;
import com.science.gtnl.Utils.gui.CircularGaugeDrawable;
import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.common.machine.hatch.CustomFluidHatch;
import com.science.gtnl.common.materials.MaterialPool;

import gregtech.api.GregTechAPI;
import gregtech.api.interfaces.metatileentity.IItemLockable;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.metatileentity.implementations.MTEHatchInput;
import gregtech.api.metatileentity.implementations.MTEHatchInputBus;
import gregtech.api.metatileentity.implementations.MTEHatchOutput;
import gregtech.api.metatileentity.implementations.MTEHatchOutputBus;
import gregtech.api.objects.GTRenderedTexture;
import gregtech.api.util.GTUtility;
import gregtech.api.util.HatchElementBuilder;
import gregtech.api.util.shutdown.ShutDownReasonRegistry;
import gregtech.common.blocks.BlockCasings1;
import gregtech.common.blocks.BlockCasings2;
import gregtech.common.tileentities.machines.MTEHatchCraftingInputME;
import gregtech.common.tileentities.machines.MTEHatchInputBusME;
import gregtech.common.tileentities.machines.MTEHatchOutputBusME;
import gtPlusPlus.core.util.minecraft.FluidUtils;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.MTEHatchSteamBusOutput;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.MteHatchSteamBusInput;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.MTEHatchCustomFluidBase;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.MTESteamMultiBase;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;

public abstract class SteamMultiMachineBase<T extends SteamMultiMachineBase<T>> extends MTESteamMultiBase<T> {

    protected int tierAdvancedCasing = -1;
    protected int tierBrickCasing = -1;
    protected int tierPlatedCasing = -1;
    protected int tierPipeCasing = -1;
    protected int tierFireboxCasing = -1;
    protected int tierMaterialBlock = -1;
    protected int tierGearCasing = -1;
    protected int tierFrameCasing = -1;
    protected int tierMachineCasing = -1;
    protected int tierMachine = 0;
    protected int tCountCasing = 0;
    protected boolean isBroken = true;
    public ArrayList<CustomFluidHatch> mSteamBigInputFluids = new ArrayList<>();
    private int uiSteamStored = 0;
    private int uiSteamCapacity = 0;
    private int uiSteamStoredOfAllTypes = 0;
    public static final UITexture STEAM_GAUGE_BG = UITexture.fullImage(GregTech.ID, "gui/background/steam_dial");

    public SteamMultiMachineBase(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public SteamMultiMachineBase(String aName) {
        super(aName);
    }

    protected static int getTierAdvancedCasing(Block block, int meta) {
        if (block == BWBlockCasings && 32066 == meta) return 1;
        if (block == BWBlockCasings && 32071 == meta) return 2;
        return 0;
    }

    protected static int getTierMachineCasing(Block block, int meta) {
        if (block == sBlockCasings1 && 10 == meta) {
            return 1;
        }
        if (block == sBlockCasings2 && 0 == meta) {
            return 2;
        }
        return 0;
    }

    protected static int getTierPipeCasing(Block block, int meta) {
        if (block == sBlockCasings2 && 12 == meta) return 1;
        if (block == sBlockCasings2 && 13 == meta) return 2;
        return 0;
    }

    protected static int getTierGearCasing(Block block, int meta) {
        if (block == sBlockCasings2 && 2 == meta) return 1;
        if (block == sBlockCasings2 && 3 == meta) return 2;
        return 0;
    }

    protected static int getTierMaterialBlockCasing(Block block, int meta) {
        if (block == Blocks.iron_block) return 1;
        if (block == sBlockMetal6 && 13 == meta) return 2;
        return 0;
    }

    protected static int getTierFrameCasing(Block block, int meta) {
        if (block == sBlockFrames && 300 == meta) return 1;
        if (block == sBlockFrames && 305 == meta) return 2;
        return 0;
    }

    protected static int getTierBrickCasing(Block block, int meta) {
        if (block == MetaBlockColumn && 0 == meta) return 1;
        if (block == MetaBlockColumn && 1 == meta) return 2;
        return 0;
    }

    protected static int getTierPlatedCasing(Block block, int meta) {
        if (block == blockCustomMachineCasings && 0 == meta) return 1;
        if (block == sBlockCasings2 && 0 == meta) return 2;
        return 0;
    }

    protected static int getTierFireboxCasing(Block block, int meta) {
        if (block == sBlockCasings3 && 13 == meta) return 1;
        if (block == sBlockCasings3 && 14 == meta) return 2;
        return 0;
    }

    @Override
    public GTRenderedTexture getFrontOverlay() {
        return null;
    }

    @Override
    public GTRenderedTexture getFrontOverlayActive() {
        return null;
    }

    @Override
    public boolean supportsInputSeparation() {
        return true;
    }

    @Override
    public boolean supportsBatchMode() {
        return true;
    }

    @Override
    public boolean getDefaultInputSeparationMode() {
        return false;
    }

    protected void updateHatchTexture() {
        for (MTEHatch h : mSteamInputs) h.updateTexture(getCasingTextureID());
        for (MTEHatch h : mSteamOutputs) h.updateTexture(getCasingTextureID());
        for (MTEHatch h : mSteamInputFluids) h.updateTexture(getCasingTextureID());
        for (MTEHatch h : mSteamBigInputFluids) h.updateTexture(getCasingTextureID());
        for (MTEHatch h : mInputBusses) h.updateTexture(getCasingTextureID());
        for (MTEHatch h : mOutputBusses) h.updateTexture(getCasingTextureID());
        for (MTEHatch h : mInputHatches) h.updateTexture(getCasingTextureID());
        for (MTEHatch h : mOutputHatches) h.updateTexture(getCasingTextureID());
    }

    protected int getCasingTextureID() {
        if (tierAdvancedCasing == 2 || tierBrickCasing == 2
            || tierPlatedCasing == 2
            || tierPipeCasing == 2
            || tierFireboxCasing == 2
            || tierMaterialBlock == 2
            || tierGearCasing == 2
            || tierFrameCasing == 2
            || tierMachineCasing == 2
            || tierMachine == 2) {
            return ((BlockCasings2) GregTechAPI.sBlockCasings2).getTextureIndex(0);
        }
        return ((BlockCasings1) GregTechAPI.sBlockCasings1).getTextureIndex(10);
    }

    protected boolean checkHatches() {
        return !mSteamInputFluids.isEmpty() || !mSteamBigInputFluids.isEmpty();
    }

    @Override
    public void onValueUpdate(byte aValue) {
        if ((byte) tierMachine != aValue) {
            tierMachine = (byte) (aValue & 0x0F);
        }
    }

    @Override
    public byte getUpdateData() {
        if (tierMachine <= 0) return 0;
        return (byte) tierMachine;
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
        aNBT.setInteger("mMode", machineMode);
    }

    @Override
    public void loadNBTData(final NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        tierMachine = aNBT.getInteger("tierMachine");
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (aBaseMetaTileEntity.isServerSide()) {
            if (this.mUpdate == 1 || this.mStartUpCheck == 1) {
                this.mSteamInputs.clear();
                this.mSteamOutputs.clear();
                this.mInputHatches.clear();
                this.mSteamInputFluids.clear();
                this.mSteamBigInputFluids.clear();
            }
            if (mUpdate < -250) mUpdate = 50;
            if ((aTick % 1200) == 0) {
                isBroken = true;
            }
        }
        super.onPostTick(aBaseMetaTileEntity, aTick);
    }

    @Override
    public boolean addToMachineList(final IGregTechTileEntity aTileEntity, final int aBaseCasingIndex) {
        boolean aDidAdd = super.addToMachineList(aTileEntity, aBaseCasingIndex);

        if (aTileEntity == null) {
            log("Invalid IGregTechTileEntity");
            return false;
        }
        final IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null) {
            log("Invalid IMetaTileEntity");
            return false;
        }

        if (aMetaTileEntity instanceof CustomFluidHatch) {
            log("Adding Steam Big Input Hatch");
            aDidAdd = addToMachineListInternal(mSteamBigInputFluids, aMetaTileEntity, aBaseCasingIndex);
        }

        return aDidAdd;
    }

    @Override
    public boolean depleteInput(FluidStack aLiquid) {
        if (aLiquid == null) return false;
        for (MTEHatchCustomFluidBase tHatch : validMTEList(mSteamInputFluids)) {
            FluidStack tLiquid = tHatch.getFluid();
            if (tLiquid != null && tLiquid.isFluidEqual(aLiquid)) {
                tLiquid = tHatch.drain(aLiquid.amount, false);
                if (tLiquid != null && tLiquid.amount >= aLiquid.amount) {
                    tLiquid = tHatch.drain(aLiquid.amount, true);
                    return tLiquid != null && tLiquid.amount >= aLiquid.amount;
                }
            }
        }
        for (CustomFluidHatch tHatch : validMTEList(mSteamBigInputFluids)) {
            FluidStack tLiquid = tHatch.getFluid();
            if (tLiquid != null && tLiquid.isFluidEqual(aLiquid)) {
                tLiquid = tHatch.drain(aLiquid.amount, false);
                if (tLiquid != null && tLiquid.amount >= aLiquid.amount) {
                    tLiquid = tHatch.drain(aLiquid.amount, true);
                    return tLiquid != null && tLiquid.amount >= aLiquid.amount;
                }
            }
        }
        return false;
    }

    @Override
    public boolean depleteInput(ItemStack aStack) {
        if (GTUtility.isStackInvalid(aStack)) return false;
        FluidStack aLiquid = GTUtility.getFluidForFilledItem(aStack, true);
        if (aLiquid != null) return depleteInput(aLiquid);
        for (MTEHatchCustomFluidBase tHatch : validMTEList(mSteamInputFluids)) {
            if (GTUtility.areStacksEqual(
                aStack,
                tHatch.getBaseMetaTileEntity()
                    .getStackInSlot(0))) {
                if (tHatch.getBaseMetaTileEntity()
                    .getStackInSlot(0).stackSize >= aStack.stackSize) {
                    tHatch.getBaseMetaTileEntity()
                        .decrStackSize(0, aStack.stackSize);
                    return true;
                }
            }
        }
        for (CustomFluidHatch tHatch : validMTEList(mSteamBigInputFluids)) {
            if (GTUtility.areStacksEqual(
                aStack,
                tHatch.getBaseMetaTileEntity()
                    .getStackInSlot(0))) {
                if (tHatch.getBaseMetaTileEntity()
                    .getStackInSlot(0).stackSize >= aStack.stackSize) {
                    tHatch.getBaseMetaTileEntity()
                        .decrStackSize(0, aStack.stackSize);
                    return true;
                }
            }
        }
        for (MteHatchSteamBusInput tHatch : validMTEList(mSteamInputs)) {
            tHatch.mRecipeMap = getRecipeMap();
            for (int i = tHatch.getBaseMetaTileEntity()
                .getSizeInventory() - 1; i >= 0; i--) {
                if (GTUtility.areStacksEqual(
                    aStack,
                    tHatch.getBaseMetaTileEntity()
                        .getStackInSlot(i))) {
                    if (tHatch.getBaseMetaTileEntity()
                        .getStackInSlot(0).stackSize >= aStack.stackSize) {
                        tHatch.getBaseMetaTileEntity()
                            .decrStackSize(0, aStack.stackSize);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ArrayList<FluidStack> getStoredFluids() {
        ArrayList<FluidStack> rList = new ArrayList<>();
        for (MTEHatchCustomFluidBase tHatch : validMTEList(mSteamInputFluids)) {
            if (tHatch.getFillableStack() != null) {
                rList.add(tHatch.getFillableStack());
            }
        }
        for (CustomFluidHatch tHatch : validMTEList(mSteamBigInputFluids)) {
            if (tHatch.getFillableStack() != null) {
                rList.add(tHatch.getFillableStack());
            }
        }
        for (MTEHatchInput hatch : this.mInputHatches) if (hatch.getFillableStack() != null) {
            rList.add(hatch.getFillableStack());
        }
        return rList;
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

    public int getTotalSteamCapacity() {
        int aSteam = 0;
        for (MTEHatchCustomFluidBase tHatch : validMTEList(mSteamInputFluids)) {
            aSteam += tHatch.getRealCapacity();
        }
        for (CustomFluidHatch tHatch : validMTEList(mSteamBigInputFluids)) {
            aSteam += tHatch.getRealCapacity();
        }
        return aSteam;
    }

    private int getTotalSteamStoredOfAnyType() {
        int aSteam = 0;
        for (FluidStack aFluid : this.getStoredFluids()) {
            if (aFluid == null) continue;
            for (SteamTypes type : SteamTypes.VALUES) {
                if (aFluid.getFluid() == type.fluid) {
                    aSteam += aFluid.amount;
                }
            }
        }
        return aSteam;
    }

    @Override
    public ArrayList<FluidStack> getAllSteamStacks() {
        ArrayList<FluidStack> aFluids = new ArrayList<>();
        for (FluidStack aFluid : this.getStoredFluids()) {
            if (aFluid != null) {
                for (SteamTypes type : SteamTypes.VALUES) {
                    if (aFluid.getFluid() == type.fluid) {
                        aFluids.add(aFluid);
                        break;
                    }
                }
            }
        }
        return aFluids;
    }

    @Override
    public int getTotalSteamStored() {
        int total = 0;
        for (FluidStack aFluid : getAllSteamStacks()) {
            total += aFluid.amount;
        }
        return total;
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
        for (CustomFluidHatch tHatch : validMTEList(mSteamBigInputFluids)) tHatch.updateSlots();
        for (MTEHatchInput tHatch : validMTEList(mInputHatches)) tHatch.updateSlots();
        for (MTEHatchInputBus tHatch : validMTEList(mInputBusses)) tHatch.updateSlots();
        super.updateSlots();
    }

    @Override
    public void clearHatches() {
        super.clearHatches();
        mInputHatches.clear();
        mSteamInputFluids.clear();
        mSteamBigInputFluids.clear();
        mSteamInputs.clear();
        mSteamOutputs.clear();
    }

    @Override
    public boolean onRunningTick(ItemStack aStack) {
        if (lEUt < 0) {
            long aSteamVal = ((-lEUt * 10000) / Math.max(1000, mEfficiency));
            // Logger.INFO("Trying to drain "+aSteamVal+" steam per tick.");
            if (!tryConsumeSteam((int) aSteamVal)) {
                stopMachine(ShutDownReasonRegistry.POWER_LOSS);
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean tryConsumeSteam(int aAmount) {
        for (SteamTypes type : SteamTypes.getSupportedTypes()) {
            FluidStack steamStack = new FluidStack(type.fluid, Math.max(1, aAmount / type.efficiencyFactor));
            if (depleteInput(steamStack)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addUIWidgets(ModularWindow.Builder builder, UIBuildContext buildContext) {
        super.addUIWidgets(builder, buildContext);
        builder.widget(new FakeSyncWidget.IntegerSyncer(this::getTotalSteamCapacity, val -> uiSteamCapacity = val));
        builder.widget(new FakeSyncWidget.IntegerSyncer(this::getTotalSteamStored, val -> uiSteamStored = val));
        builder.widget(
            new FakeSyncWidget.IntegerSyncer(this::getTotalSteamStoredOfAnyType, val -> uiSteamStoredOfAllTypes = val));

        builder.widget(
            new DrawableWidget().setDrawable(STEAM_GAUGE_BG)
                .dynamicTooltip(() -> {
                    List<String> ret = new ArrayList<>();
                    ret.add(
                        StatCollector.translateToLocal("AllSteamCapacity") + uiSteamStored
                            + "/"
                            + uiSteamCapacity
                            + "L");
                    if (uiSteamStored == 0 && uiSteamStoredOfAllTypes != 0) {
                        ret.add(EnumChatFormatting.RED + "Found steam of wrong type!");
                    }
                    return ret;
                })
                .setTooltipShowUpDelay(TOOLTIP_DELAY)
                .setUpdateTooltipEveryTick(true)
                .setSize(64, 42)
                .setPos(-64, 100));

        builder.widget(
            new DrawableWidget().setDrawable(new CircularGaugeDrawable(() -> (float) uiSteamStored / uiSteamCapacity))
                .setPos(-64 + 21, 100 + 21)
                .setSize(18, 4));

        builder.widget(
            new ItemDrawable(GTNLItemList.FakeItemSiren.get(1)).asWidget()
                .setPos(-64 + 21 - 7, 100 - 20)
                .setEnabled(w -> uiSteamStored == 0));
    }

    protected static <T extends SteamMultiMachineBase<T>> HatchElementBuilder<T> buildSteamBigInput(
        Class<T> typeToken) {
        return buildHatchAdder(typeToken).adder(SteamMultiMachineBase::addToMachineList)
            .hatchIds(22518)
            .shouldReject(t -> !t.mSteamBigInputFluids.isEmpty());
    }

    protected enum SteamTypes {

        STEAM("Steam", FluidUtils.getSteam(1)
            .getFluid(), 1),
        SH_STEAM("Superheated Steam", FluidUtils.getSuperHeatedSteam(1)
            .getFluid(), 10),
        SC_STEAM("Supercritical Steam", FluidRegistry.getFluidStack("supercriticalsteam", 1)
            .getFluid(), 50),
        CM_STEAM("Compressed Steam", MaterialPool.CompressedSteam.getMolten(1)
            .getFluid(), 100000);

        public static final SteamTypes[] VALUES = values();

        public final String displayName;
        public final Fluid fluid;
        public final int efficiencyFactor;

        SteamTypes(String name, Fluid fluid, int efficiency) {
            this.displayName = name;
            this.fluid = fluid;
            this.efficiencyFactor = efficiency;
        }

        public static List<SteamTypes> getSupportedTypes() {
            return Arrays.asList(VALUES);
        }
    }

}
