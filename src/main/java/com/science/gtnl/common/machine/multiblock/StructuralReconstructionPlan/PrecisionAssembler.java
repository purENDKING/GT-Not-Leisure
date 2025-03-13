package com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.GregTechAPI.sBlockCasings1;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTStructureUtility.ofFrame;
import static gregtech.api.util.GTUtility.validMTEList;

import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableList;
import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.machine.multiMachineClasses.MultiMachineBase;
import com.science.gtnl.config.MainConfig;

import bartworks.API.BorosilicateGlass;
import goodgenerator.api.recipe.GoodGeneratorRecipeMaps;
import goodgenerator.loader.Loaders;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.enums.VoltageIndex;
import gregtech.api.gui.modularui.GTUITextures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.metatileentity.implementations.MTEHatchEnergy;
import gregtech.api.metatileentity.implementations.MTEHatchMaintenance;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import gregtech.common.tileentities.machines.IDualInputHatch;
import gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock;
import tectech.thing.metaTileEntity.hatch.MTEHatchEnergyTunnel;

public class PrecisionAssembler extends MultiMachineBase<PrecisionAssembler> implements ISurvivalConstructable {

    public static final String STRUCTURE_PIECE_MAIN = "main";
    private static IStructureDefinition<PrecisionAssembler> STRUCTURE_DEFINITION = null;
    public static final String LPA_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/precise_assembler";
    private static final int MACHINEMODE_ASSEMBLER = 0;
    private static final int MACHINEMODE_PRECISION = 1;
    private static final int CASING_INDEX = 1540;
    public final int horizontalOffSet = 4;
    public final int verticalOffSet = 4;
    public final int depthOffSet = 0;
    public static String[][] shape = StructureUtils.readStructureFromFile(LPA_STRUCTURE_FILE_PATH);
    protected int mCasing;
    protected int casingTier = 0;
    protected int machineTier = -1;
    public byte glassTier = 0;
    public int energyHatchTier;

    public PrecisionAssembler(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public PrecisionAssembler(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new PrecisionAssembler(this.mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        int id = Math.max(CASING_INDEX, CASING_INDEX + casingTier);
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(id),
                TextureFactory.of(TexturesGtBlock.oMCDIndustrialCuttingMachineActive), TextureFactory.builder()
                    .addIcon(TexturesGtBlock.oMCDIndustrialCuttingMachineActive)
                    .glow()
                    .build() };
            else return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(id),
                TextureFactory.of(TexturesGtBlock.oMCDIndustrialCuttingMachine), TextureFactory.builder()
                    .addIcon(TexturesGtBlock.oMCDIndustrialCuttingMachine)
                    .glow()
                    .build() };
        } else return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(id) };
    }

    @Override
    public void onValueUpdate(byte aValue) {
        if ((byte) casingTier != aValue) {
            casingTier = (byte) (aValue & 0x0F);
        }
    }

    @Override
    public byte getUpdateData() {
        if (casingTier <= -1) return 0;
        return (byte) casingTier;
    }

    public int getCasingTextureID() {
        if (casingTier >= 0) {
            return CASING_INDEX + casingTier;
        } else return CASING_INDEX;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return (machineMode == MACHINEMODE_ASSEMBLER) ? RecipeMaps.assemblerRecipes
            : GoodGeneratorRecipeMaps.preciseAssemblerRecipes;
    }

    @Nonnull
    @Override
    public Collection<RecipeMap<?>> getAvailableRecipeMaps() {
        return Arrays.asList(RecipeMaps.assemblerRecipes, GoodGeneratorRecipeMaps.preciseAssemblerRecipes);
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.PreciseAssemblerRecipeType)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_00)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_01)
            .addInfo(TextLocalization.Tooltip_PreciseAssembler_00)
            .addInfo(TextLocalization.Tooltip_PreciseAssembler_01)
            .addInfo(TextLocalization.Tooltip_PreciseAssembler_02)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_04)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(9, 5, 5, true)
            .addInputHatch(TextLocalization.Tooltip_PreciseAssembler_Casing)
            .addInputBus(TextLocalization.Tooltip_PreciseAssembler_Casing)
            .addOutputBus(TextLocalization.Tooltip_PreciseAssembler_Casing)
            .addEnergyHatch(TextLocalization.Tooltip_PreciseAssembler_Casing)
            .addMaintenanceHatch(TextLocalization.Tooltip_PreciseAssembler_Casing)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IStructureDefinition<PrecisionAssembler> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<PrecisionAssembler>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement(
                    'A',
                    withChannel(
                        "glass",
                        BorosilicateGlass.ofBoroGlass(
                            (byte) 0,
                            (byte) 1,
                            Byte.MAX_VALUE,
                            (te, t) -> te.glassTier = t,
                            te -> te.glassTier)))
                .addElement(
                    'B',
                    withChannel(
                        "tiercasing",
                        ofBlocksTiered(
                            PrecisionAssembler::getMachineTier,
                            ImmutableList.of(
                                Pair.of(sBlockCasings1, 0),
                                Pair.of(sBlockCasings1, 1),
                                Pair.of(sBlockCasings1, 2),
                                Pair.of(sBlockCasings1, 3),
                                Pair.of(sBlockCasings1, 4),
                                Pair.of(sBlockCasings1, 5),
                                Pair.of(sBlockCasings1, 6),
                                Pair.of(sBlockCasings1, 7),
                                Pair.of(sBlockCasings1, 8),
                                Pair.of(sBlockCasings1, 9)),
                            -1,
                            (t, m) -> t.machineTier = m,
                            t -> t.machineTier)))
                .addElement('C', ofFrame(Materials.TungstenSteel))
                .addElement(
                    'D',
                    ofChain(
                        buildHatchAdder(PrecisionAssembler.class).casingIndex(getCasingTextureID())
                            .dot(1)
                            .atLeast(InputHatch, InputBus, OutputBus, Maintenance, Energy.or(ExoticEnergy))
                            .buildAndChain(
                                onElementPass(
                                    x -> ++x.mCasing,
                                    withChannel(
                                        "precisecasing",
                                        ofBlocksTiered(
                                            PrecisionAssembler::getCasingTier,
                                            ImmutableList.of(
                                                Pair.of(Loaders.impreciseUnitCasing, 0),
                                                Pair.of(Loaders.preciseUnitCasing, 0),
                                                Pair.of(Loaders.preciseUnitCasing, 1),
                                                Pair.of(Loaders.preciseUnitCasing, 2),
                                                Pair.of(Loaders.preciseUnitCasing, 3)),
                                            -1,
                                            (t, m) -> t.casingTier = m,
                                            t -> t.casingTier))))))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    protected void updateHatchTexture() {
        for (MTEHatch h : mInputBusses) h.updateTexture(getCasingTextureID());
        for (MTEHatch h : mOutputBusses) h.updateTexture(getCasingTextureID());
        for (MTEHatch h : mInputHatches) h.updateTexture(getCasingTextureID());
        for (MTEHatch h : mMaintenanceHatches) h.updateTexture(getCasingTextureID());
        for (MTEHatch h : mEnergyHatches) h.updateTexture(getCasingTextureID());
        for (MTEHatch h : mExoticEnergyHatches) h.updateTexture(getCasingTextureID());
        for (IDualInputHatch h : mDualInputHatches) h.updateTexture(getCasingTextureID());
    }

    @Override
    public boolean isEnablePerfectOverclock() {
        return casingTier >= 4;
    }

    public static int getCasingTier(Block block, int meta) {
        if (block == Loaders.impreciseUnitCasing) return 0;
        if (block == Loaders.preciseUnitCasing) return meta + 1;
        return -1;
    }

    public static int getMachineTier(Block block, int meta) {
        if (block == sBlockCasings1) return meta;
        return -1;
    }

    @Override
    public boolean getDefaultHasMaintenanceChecks() {
        return true;
    }

    @Override
    public boolean shouldCheckMaintenance() {
        return true;
    }

    @Override
    public void checkMaintenance() {
        if (!shouldCheckMaintenance()) return;

        if (getRepairStatus() != getIdealStatus()) {
            for (MTEHatchMaintenance tHatch : validMTEList(mMaintenanceHatches)) {
                if (tHatch.mAuto) tHatch.autoMaintainance();
                if (tHatch.mWrench) mWrench = true;
                if (tHatch.mScrewdriver) mScrewdriver = true;
                if (tHatch.mSoftHammer) mSoftHammer = true;
                if (tHatch.mHardHammer) mHardHammer = true;
                if (tHatch.mSolderingTool) mSolderingTool = true;
                if (tHatch.mCrowbar) mCrowbar = true;

                tHatch.mWrench = false;
                tHatch.mScrewdriver = false;
                tHatch.mSoftHammer = false;
                tHatch.mHardHammer = false;
                tHatch.mSolderingTool = false;
                tHatch.mCrowbar = false;
            }
        }
    }

    @Override
    public float getSpeedBonus() {
        return 1;
    }

    @Override
    public void setMachineModeIcons() {
        machineModeIcons.clear();
        machineModeIcons.add(GTUITextures.OVERLAY_BUTTON_MACHINEMODE_LPF_METAL);
        machineModeIcons.add(GTUITextures.OVERLAY_BUTTON_MACHINEMODE_COMPRESSING);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        casingTier = aNBT.getInteger("casingTier");
        machineTier = aNBT.getInteger("machineTier");
        machineMode = aNBT.getInteger("mode");
        super.loadNBTData(aNBT);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        aNBT.setInteger("casingTier", casingTier);
        aNBT.setInteger("machineTier", machineTier);
        aNBT.setInteger("mode", machineMode);
        super.saveNBTData(aNBT);
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
        GTUtility
            .sendChatToPlayer(aPlayer, StatCollector.translateToLocal("PreciseAssembler_Mode_" + this.machineMode));
    }

    @Override
    public String getMachineModeName() {
        return StatCollector.translateToLocal("PreciseAssembler_Mode_" + machineMode);
    }

    @Override
    public boolean supportsMachineModeSwitch() {
        return true;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        mCasing = 0;
        casingTier = -1;
        machineTier = -1;
        glassTier = 0;

        if (!checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet) && checkHatch()) {
            updateHatchTexture();
            return false;
        }

        for (MTEHatchEnergy mEnergyHatch : this.mEnergyHatches) {
            if (machineTier < VoltageIndex.UHV & mEnergyHatch.mTier > machineTier) {
                updateHatchTexture();
                return false;
            }
        }

        energyHatchTier = checkEnergyHatchTier();
        if (MainConfig.enableMachineAmpLimit) {
            for (MTEHatch hatch : getExoticEnergyHatches()) {
                if (hatch instanceof MTEHatchEnergyTunnel) {
                    return false;
                }
            }
            if (getMaxInputAmps() > 64) return false;
        }

        updateHatchTexture();
        updateTexture(aBaseMetaTileEntity, getCasingTextureID());
        if (aBaseMetaTileEntity instanceof MTEHatch mteHatch) {
            mteHatch.updateTexture(getCasingTextureID());
            return true;
        }
        return mCasing >= 30 && casingTier >= 0;
    }

    @Override
    protected void setProcessingLogicPower(ProcessingLogic logic) {
        boolean useSingleAmp = mEnergyHatches.size() == 1 && mExoticEnergyHatches.isEmpty() && getMaxInputAmps() <= 2;
        logic.setAvailableVoltage(getMachineVoltageLimit());
        logic.setAvailableAmperage(useSingleAmp ? 1 : getMaxInputAmps());
        logic.setAmperageOC(useSingleAmp);
    }

    protected long getMachineVoltageLimit() {
        return GTValues.V[energyHatchTier];
    }

    protected int checkEnergyHatchTier() {
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
    public ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @Nonnull
            @Override
            protected CheckRecipeResult validateRecipe(@Nonnull GTRecipe recipe) {
                if (machineMode == 1) {
                    if (recipe.mSpecialValue > (Math.max(0, casingTier + 1))) {
                        return CheckRecipeResultRegistry.insufficientMachineTier(recipe.mSpecialValue);
                    }
                }
                if (availableVoltage < recipe.mEUt) {
                    return CheckRecipeResultRegistry.insufficientPower(recipe.mEUt);
                }
                return CheckRecipeResultRegistry.SUCCESSFUL;
            }

            @NotNull
            @Override
            public OverclockCalculator createOverclockCalculator(@NotNull GTRecipe recipe) {
                return super.createOverclockCalculator(recipe).setEUtDiscount(0.8)
                    .setSpeedBoost(0.6);
            }

        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }

    @Override
    public int getMaxParallelRecipes() {
        if (glassTier > 0) {
            return (int) Math.pow(2, glassTier) + casingTier * 64;
        } else {
            return 0;
        }
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
}
