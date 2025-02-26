package com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.common.block.Casings.BasicBlocks.MetaBlockGlass;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTStructureUtility.ofFrame;
import static kekztech.common.Blocks.lscLapotronicEnergyUnit;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.structurelib.alignment.constructable.IConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;

import cofh.api.energy.IEnergyContainerItem;
import gregtech.api.enums.Materials;
import gregtech.api.enums.SoundResource;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.MTEHatchInputBus;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.SimpleCheckRecipeResult;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.common.tileentities.machines.MTEHatchInputBusME;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import tectech.TecTech;
import tectech.loader.ConfigHandler;
import tectech.thing.casing.TTCasingsContainer;
import tectech.thing.metaTileEntity.multi.base.TTMultiblockBase;

public class EnergyInfuser extends TTMultiblockBase implements IConstructable {

    private static final int maxRepairedDamagePerOperation = 10000;
    private static final long usedEuPerDurability = 1000;
    private static final int usedUumPerDurability = 1;
    private int mCasing;
    private static IStructureDefinition<EnergyInfuser> STRUCTURE_DEFINITION = null;
    public static final String STRUCTURE_PIECE_MAIN = "main";
    public static final String EI_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/energy_infuser";
    public static String[][] shape = StructureUtils.readStructureFromFile(EI_STRUCTURE_FILE_PATH);
    public final int horizontalOffSet = 2;
    public final int verticalOffSet = 7;
    public final int depthOffSet = 0;

    @Override
    public IStructureDefinition<EnergyInfuser> getStructure_EM() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<EnergyInfuser>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', ofBlock(MetaBlockGlass, 2))
                .addElement('B', ofBlock(TTCasingsContainer.sBlockCasingsTT, 0))
                .addElement(
                    'C',
                    buildHatchAdder(EnergyInfuser.class)
                        .atLeast(InputHatch, InputBus, OutputBus, Maintenance, Energy.or(ExoticEnergy))
                        .casingIndex(1028)
                        .dot(1)
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(TTCasingsContainer.sBlockCasingsTT, 4))))
                .addElement('D', ofBlock(TTCasingsContainer.sBlockCasingsTT, 7))
                .addElement('E', ofFrame(Materials.Osmiridium))
                .addElement('F', ofBlock(lscLapotronicEnergyUnit, 6))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    public EnergyInfuser(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        minRepairStatus = (byte) getIdealStatus();
        eDismantleBoom = true;
    }

    public EnergyInfuser(String aName) {
        super(aName);
        minRepairStatus = (byte) getIdealStatus();
        eDismantleBoom = true;
    }

    private boolean isItemStackFullyCharged(ItemStack stack) {
        if (stack == null) {
            return true;
        }
        Item item = stack.getItem();
        if (stack.stackSize == 1) {
            if (item instanceof IElectricItem) {
                return ElectricItem.manager.getCharge(stack) >= ((IElectricItem) item).getMaxCharge(stack);
            } else if (TecTech.hasCOFH && item instanceof IEnergyContainerItem) {
                return ((IEnergyContainerItem) item).getEnergyStored(stack)
                    >= ((IEnergyContainerItem) item).getMaxEnergyStored(stack);
            }
        }
        return true;
    }

    private boolean isItemStackFullyRepaired(ItemStack stack) {
        if (stack == null) {
            return true;
        }
        Item item = stack.getItem();
        return !item.isRepairable() || item.getMaxDamage(stack) <= 0 || item.getDamage(stack) <= 0;
    }

    private long doChargeItemStack(IElectricItem item, ItemStack stack) {
        try {
            double euDiff = item.getMaxCharge(stack) - ElectricItem.manager.getCharge(stack);
            long remove = (long) Math.ceil(
                ElectricItem.manager.charge(stack, Math.min(euDiff, getEUVar()), item.getTier(stack), true, false));
            setEUVar(getEUVar() - remove);
            if (getEUVar() < 0) {
                setEUVar(0);
            }
            return remove;
        } catch (Exception e) {
            if (ConfigHandler.debug.DEBUG_MODE) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    private long doChargeItemStackRF(IEnergyContainerItem item, ItemStack stack) {
        try {
            long RF = Math
                .min(item.getMaxEnergyStored(stack) - item.getEnergyStored(stack), getEUVar() * mEUtoRF / 10L);
            RF = item.receiveEnergy(stack, RF > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) RF, false);
            RF = RF * 10L / mEUtoRF;
            setEUVar(getEUVar() - RF);
            if (getEUVar() < 0) {
                setEUVar(0);
            }
            return RF;
        } catch (Exception e) {
            if (ConfigHandler.debug.DEBUG_MODE) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new EnergyInfuser(this.mName);
    }

    @Override
    public boolean checkMachine_EM(IGregTechTileEntity iGregTechTileEntity, ItemStack itemStack) {
        return structureCheck_EM(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet);
    }

    @Override
    @NotNull
    protected CheckRecipeResult checkProcessing_EM() {
        for (MTEHatchInputBus inputBus : mInputBusses) {
            if (inputBus instanceof MTEHatchInputBusME) continue;
            for (int i = 0; i < inputBus.getSizeInventory(); i++) {
                ItemStack itemStackInBus = inputBus.getStackInSlot(i);
                if (itemStackInBus == null) continue;
                Item item = itemStackInBus.getItem();
                if (itemStackInBus.stackSize != 1 || item == null) continue;
                if (isItemStackFullyCharged(itemStackInBus) && isItemStackFullyRepaired(itemStackInBus)) {
                    if (addOutput(itemStackInBus)) {
                        this.depleteInput(itemStackInBus);
                    }
                } else {
                    mEfficiencyIncrease = 10000;
                    mMaxProgresstime = 1;
                    return SimpleCheckRecipeResult.ofSuccess("charging");
                }
            }
        }
        return SimpleCheckRecipeResult.ofFailure("no_chargeable_item");
    }

    @Override
    public void outputAfterRecipe_EM() {
        boolean itemProcessed = false;
        startRecipeProcessing();
        for (MTEHatchInputBus inputBus : mInputBusses) {
            if (inputBus instanceof MTEHatchInputBusME) continue;
            for (int i = 0; i < inputBus.getSizeInventory(); i++) {
                ItemStack itemStackInBus = inputBus.getStackInSlot(i);
                if (itemStackInBus == null) continue;
                Item item = itemStackInBus.getItem();
                if (itemStackInBus.stackSize != 1 || item == null) continue;
                if (isItemStackFullyCharged(itemStackInBus) && isItemStackFullyRepaired(itemStackInBus)) {
                    itemProcessed = true;
                    if (addOutput(itemStackInBus)) {
                        this.depleteInput(itemStackInBus);
                    }
                } else {
                    if (item.isRepairable()) {
                        FluidStack uum = getStoredFluids().stream()
                            .filter(
                                fluid -> Materials.UUMatter.getFluid(1)
                                    .isFluidEqual(fluid))
                            .findAny()
                            .orElse(null);
                        if (uum != null) {
                            int repairedDamage = Math
                                .min(item.getDamage(itemStackInBus), maxRepairedDamagePerOperation);
                            long euCost = repairedDamage * usedEuPerDurability;
                            if (getEUVar() >= euCost && depleteInput(
                                new FluidStack(Materials.UUMatter.mFluid, repairedDamage * usedUumPerDurability))) {
                                item.setDamage(
                                    itemStackInBus,
                                    Math.max(item.getDamage(itemStackInBus) - repairedDamage, 0));
                                setEUVar(Math.min(getEUVar() - euCost, 0));
                            }
                        }
                    }
                    if (item instanceof IElectricItem) {
                        doChargeItemStack((IElectricItem) item, itemStackInBus);
                        return;
                    } else if (TecTech.hasCOFH && item instanceof IEnergyContainerItem) {
                        doChargeItemStackRF((IEnergyContainerItem) item, itemStackInBus);
                        return;
                    }
                }
            }
        }
        endRecipeProcessing();
        if (!itemProcessed) {
            afterRecipeCheckFailed();
        }
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.EnergyInfuserRecipeType)
            .addInfo(TextLocalization.Tooltip_EnergyInfuser_00)
            .addInfo(TextLocalization.Tooltip_EnergyInfuser_01)
            .addInfo(TextLocalization.Tooltip_EnergyInfuser_02)
            .addInfo(TextLocalization.Tooltip_EnergyInfuser_03)
            .addTecTechHatchInfo()
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(5, 8, 5, true)
            .addInputHatch(TextLocalization.Tooltip_EnergyInfuser_Casing)
            .addOutputBus(TextLocalization.Tooltip_EnergyInfuser_Casing)
            .addEnergyHatch(TextLocalization.Tooltip_EnergyInfuser_Casing)
            .addMaintenanceHatch(TextLocalization.Tooltip_EnergyInfuser_Casing)
            .toolTipFinisher();
        return tt;
    }

    @Override
    protected SoundResource getActivitySoundLoop() {
        return SoundResource.TECTECH_MACHINES_FX_WHOOUM;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        structureBuild_EM(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet, stackSize, hintsOnly);
    }

    @Override
    public boolean isSafeVoidButtonEnabled() {
        return false;
    }
}
