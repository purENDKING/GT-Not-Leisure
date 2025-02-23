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

import bartworks.API.BorosilicateGlass;
import goodgenerator.api.recipe.GoodGeneratorRecipeMaps;
import goodgenerator.loader.Loaders;
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
import gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock;
import tectech.thing.metaTileEntity.hatch.MTEHatchEnergyTunnel;

public class PreciseAssembler extends MultiMachineBase<PreciseAssembler> implements ISurvivalConstructable {

    public static final String STRUCTURE_PIECE_MAIN = "main";
    private static IStructureDefinition<PreciseAssembler> STRUCTURE_DEFINITION = null;
    public static final String LPA_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/precise_assembler";
    private static final int MACHINEMODE_ASSEMBLER = 0;
    private static final int MACHINEMODE_PRECISION = 1;
    private static final int CASING_INDEX = 1541;
    public final int horizontalOffSet = 4;
    public final int verticalOffSet = 4;
    public final int depthOffSet = 0;
    public static String[][] shape;
    protected int mCasing;
    protected int casingTier = 0;
    protected int machineTier = -1;
    public byte glassTier = 0;

    public PreciseAssembler(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        shape = StructureUtils.readStructureFromFile(LPA_STRUCTURE_FILE_PATH);
    }

    public PreciseAssembler(String aName) {
        super(aName);
        shape = StructureUtils.readStructureFromFile(LPA_STRUCTURE_FILE_PATH);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new PreciseAssembler(this.mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(TexturesGtBlock.oMCDIndustrialCuttingMachineActive)
                    .extFacing()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(TexturesGtBlock.oMCDIndustrialCuttingMachine)
                    .extFacing()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }

    public int getCasingTextureID() {
        if (casingTier >= 0) {
            return CASING_INDEX + casingTier - 1;
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
    public IStructureDefinition<PreciseAssembler> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<PreciseAssembler>builder()
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
                        "TierCasing",
                        ofBlocksTiered(
                            PreciseAssembler::getMachineTier,
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
                        buildHatchAdder(PreciseAssembler.class).casingIndex(CASING_INDEX)
                            .dot(1)
                            .atLeast(InputHatch, InputBus, OutputBus, Maintenance, Energy.or(ExoticEnergy))
                            .buildAndChain(
                                onElementPass(
                                    x -> ++x.mCasing,
                                    withChannel(
                                        "PreciseCasing",
                                        ofBlocksTiered(
                                            PreciseAssembler::getCasingTier,
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
    }

    @Override
    public boolean isEnablePerfectOverclock() {
        return true;
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
                return false;
            }
        }

        for (MTEHatch hatch : getExoticEnergyHatches()) {
            if (hatch instanceof MTEHatchEnergyTunnel) {
                return false;
            }
        }

        updateHatchTexture();
        return mCasing >= 60 && casingTier >= 0;
    }

    @Override
    public ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @Nonnull
            @Override
            protected CheckRecipeResult validateRecipe(@Nonnull GTRecipe recipe) {
                if (machineMode == 1) {
                    if (recipe.mSpecialValue > (casingTier - 1)) {
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
            return (int) Math.pow(2, glassTier);
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
