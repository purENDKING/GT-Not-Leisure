package com.science.gtnl.common.machine.multiblock.ModuleMachine.EGTW;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;
import static com.science.gtnl.common.machine.multiblock.ModuleMachine.EGTW.EternalGregTechWorkshop.ScreenOFF;
import static com.science.gtnl.common.machine.multiblock.ModuleMachine.EGTW.EternalGregTechWorkshop.ScreenON;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.enums.HatchElement.OutputHatch;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTStructureUtility.ofFrame;
import static gregtech.common.misc.WirelessNetworkManager.addEUToGlobalEnergyMap;

import java.util.UUID;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.gtnewhorizons.gtnhintergalactic.block.IGBlocks;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.common.machine.multiMachineClasses.GTNLProcessingLogic;
import com.science.gtnl.common.machine.multiMachineClasses.MultiMachineBase;
import com.science.gtnl.misc.OverclockType;

import bartworks.common.loaders.ItemRegistry;
import goodgenerator.loader.Loaders;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.OverclockCalculator;
import gregtech.api.util.shutdown.ShutDownReasonRegistry;
import tectech.thing.casing.TTCasingsContainer;

public abstract class EternalGregTechWorkshopModule extends MultiMachineBase<EternalGregTechWorkshopModule> {

    protected UUID ownerUUID;
    protected boolean isConnected = false;
    protected double mEUtDiscount = 0;
    protected double mSpeedBoost = 0;
    protected int MaxParallel = 0;
    protected int mHeatingCapacity = 0;
    protected int tCountCasing = 0;

    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final String EGTWM_STRUCTURE_FILE_PATH = RESOURCE_ROOT_ID + ":"
        + "multiblock/eternal_gregTech_workshop/module";
    private static final String[][] shape = StructureUtils.readStructureFromFile(EGTWM_STRUCTURE_FILE_PATH);
    protected final int HORIZONTAL_OFF_SET = 4;
    protected final int VERTICAL_OFF_SET = 2;
    protected final int DEPTH_OFF_SET = 0;
    public final int CASING_INDEX = 960;

    public EternalGregTechWorkshopModule(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public EternalGregTechWorkshopModule(String aName) {
        super(aName);
    }

    public void connect() {
        isConnected = true;
    }

    public void disconnect() {
        isConnected = false;
    }

    public double getEUtDiscount() {
        return mEUtDiscount;
    }

    public void setEUtDiscount(double discount) {
        mEUtDiscount = discount;
    }

    public double getSpeedBoost() {
        return mSpeedBoost;
    }

    public void setSpeedBoost(double boost) {
        mSpeedBoost = boost;
    }

    public int getMaxParallel() {
        return MaxParallel;
    }

    public void setMaxParallel(int parallel) {
        MaxParallel = parallel;
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
                return super.createOverclockCalculator(recipe).setRecipeHeat(recipe.mSpecialValue)
                    .setMachineHeat(mHeatingCapacity)
                    .setEUtDiscount(mEUtDiscount)
                    .setSpeedBoost(mSpeedBoost);
            }

            @Override
            protected @Nonnull CheckRecipeResult validateRecipe(@Nonnull GTRecipe recipe) {
                return recipe.mSpecialValue <= mHeatingCapacity ? CheckRecipeResultRegistry.SUCCESSFUL
                    : CheckRecipeResultRegistry.insufficientHeat(recipe.mSpecialValue);
            }
        }.setMaxParallelSupplier(this::getMaxParallel);
    }

    @Override
    public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
        super.onFirstTick(aBaseMetaTileEntity);
        this.ownerUUID = aBaseMetaTileEntity.getOwnerUuid();
    }

    @Override
    public float getSpeedBonus() {
        return 1;
    }

    @Override
    public boolean isEnablePerfectOverclock() {
        return true;
    }

    @Override
    public int getCasingTextureID() {
        return CASING_INDEX;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
        int colorIndex, boolean aActive, boolean aRedstone) {
        if (side == facing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(CASING_INDEX),
                TextureFactory.builder()
                    .addIcon(ScreenON)
                    .extFacing()
                    .build(),
                TextureFactory.builder()
                    .addIcon(ScreenON)
                    .extFacing()
                    .glow()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(CASING_INDEX), TextureFactory.builder()
                .addIcon(ScreenOFF)
                .extFacing()
                .build(),
                TextureFactory.builder()
                    .addIcon(ScreenOFF)
                    .extFacing()
                    .glow()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(CASING_INDEX) };
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (aBaseMetaTileEntity.isServerSide() && isConnected) {
            super.onPostTick(aBaseMetaTileEntity, aTick);
            if (mEfficiency < 0) mEfficiency = 0;
        }
    }

    @Override
    public boolean onRunningTick(ItemStack aStack) {
        if (this.lEUt > 0) {
            addEnergyOutput((this.lEUt * mEfficiency) / 10000);
            return true;
        }
        if (this.lEUt < 0) {
            if (!addEUToGlobalEnergyMap(ownerUUID, -getActualEnergyUsage())) {
                stopMachine(ShutDownReasonRegistry.POWER_LOSS);
                return false;
            }
        }
        return true;
    }

    @Override
    public IStructureDefinition<EternalGregTechWorkshopModule> getStructureDefinition() {
        return StructureDefinition.<EternalGregTechWorkshopModule>builder()
            .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
            .addElement('A', ofBlock(ItemRegistry.bw_realglas2, 0))
            .addElement('B', ofBlock(Loaders.componentAssemblylineCasing, 12))
            .addElement('C', ofBlock(GregTechAPI.sBlockCasings1, 13))
            .addElement('D', ofBlock(GregTechAPI.sBlockCasings10, 11))
            .addElement('E', ofBlock(IGBlocks.SpaceElevatorMotor, 4))
            .addElement('F', ofFrame(Materials.NaquadahAlloy))
            .addElement('G', ofBlock(TTCasingsContainer.GodforgeCasings, 0))
            .addElement(
                'H',
                ofChain(
                    isAir(),
                    buildHatchAdder(EternalGregTechWorkshopModule.class)
                        .atLeast(InputBus, OutputBus, InputHatch, OutputHatch)
                        .casingIndex(CASING_INDEX)
                        .dot(1)
                        .buildAndChain(
                            onElementPass(x -> ++x.tCountCasing, ofBlock(TTCasingsContainer.GodforgeCasings, 0)))))
            .build();
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        if (!checkPiece(STRUCTURE_PIECE_MAIN, HORIZONTAL_OFF_SET, VERTICAL_OFF_SET, DEPTH_OFF_SET)) return false;

        return tCountCasing > 1 && isConnected;
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
}
