package com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;

import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Nonnull;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableList;
import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.machine.multiMachineClasses.SteamMultiMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.SoundResource;
import gregtech.api.enums.Textures;
import gregtech.api.gui.modularui.GTUITextures;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.objects.GTRenderedTexture;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import gtPlusPlus.api.recipe.GTPPRecipeMaps;

public class LargeSteamOreWasher extends SteamMultiMachineBase<LargeSteamOreWasher> implements ISurvivalConstructable {

    private static final int MACHINEMODE_OREWASH = 0;
    private static final int MACHINEMODE_SIMPLEWASH = 1;
    public static final String STRUCTURE_PIECE_MAIN = "main";
    private static IStructureDefinition<LargeSteamOreWasher> STRUCTURE_DEFINITION = null;
    public static final String LSC_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/large_steam_ore_washer";
    public static String[][] shape = StructureUtils.readStructureFromFile(LSC_STRUCTURE_FILE_PATH);

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

    public LargeSteamOreWasher(String aName) {
        super(aName);
    }

    public LargeSteamOreWasher(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public static final int HORIZONTAL_OFF_SET = 4;
    public static final int VERTICAL_OFF_SET = 4;
    public static final int DEPTH_OFF_SET = 0;

    @Override
    protected GTRenderedTexture getFrontOverlay() {
        return new GTRenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_STEAM_WASHER);
    }

    @Override
    protected GTRenderedTexture getFrontOverlayActive() {
        return new GTRenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_STEAM_WASHER_ACTIVE);
    }

    @Override
    public IStructureDefinition<LargeSteamOreWasher> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<LargeSteamOreWasher>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement(
                    'A',
                    ofChain(
                        buildSteamBigInput(LargeSteamOreWasher.class).casingIndex(getCasingTextureID())
                            .dot(1)
                            .buildAndChain(
                                onElementPass(
                                    x -> ++x.tCountCasing,
                                    withChannel(
                                        "tier",
                                        ofBlocksTiered(
                                            LargeSteamOreWasher::getTierMachineCasing,
                                            ImmutableList.of(Pair.of(sBlockCasings1, 10), Pair.of(sBlockCasings2, 0)),
                                            -1,
                                            (t, m) -> t.tierMachineCasing = m,
                                            t -> t.tierMachineCasing)))),
                        buildSteamInput(LargeSteamOreWasher.class).casingIndex(getCasingTextureID())
                            .dot(1)
                            .buildAndChain(
                                onElementPass(
                                    x -> ++x.tCountCasing,
                                    withChannel(
                                        "tier",
                                        ofBlocksTiered(
                                            LargeSteamOreWasher::getTierMachineCasing,
                                            ImmutableList.of(Pair.of(sBlockCasings1, 10), Pair.of(sBlockCasings2, 0)),
                                            -1,
                                            (t, m) -> t.tierMachineCasing = m,
                                            t -> t.tierMachineCasing)))),
                        buildHatchAdder(LargeSteamOreWasher.class).casingIndex(getCasingTextureID())
                            .dot(1)
                            .atLeast(
                                SteamHatchElement.InputBus_Steam,
                                SteamHatchElement.OutputBus_Steam,
                                InputBus,
                                OutputBus,
                                InputHatch)
                            .buildAndChain(
                                onElementPass(
                                    x -> ++x.tCountCasing,
                                    withChannel(
                                        "tier",
                                        ofBlocksTiered(
                                            LargeSteamOreWasher::getTierMachineCasing,
                                            ImmutableList.of(Pair.of(sBlockCasings1, 10), Pair.of(sBlockCasings2, 0)),
                                            -1,
                                            (t, m) -> t.tierMachineCasing = m,
                                            t -> t.tierMachineCasing))))))
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

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        tierPipeCasing = -1;
        tierMachineCasing = -1;
        tCountCasing = 0;
        if (!checkPiece(STRUCTURE_PIECE_MAIN, HORIZONTAL_OFF_SET, VERTICAL_OFF_SET, DEPTH_OFF_SET)) return false;
        if (tierPipeCasing < 0 && tierMachineCasing < 0) return false;
        if (tierPipeCasing == 1 && tierMachineCasing == 1 && tCountCasing >= 195 && checkHatches()) {
            tierMachine = 1;
            getCasingTextureID();
            updateHatchTexture();
            return true;
        }
        if (tierPipeCasing == 2 && tierMachineCasing == 2 && tCountCasing >= 195 && checkHatches()) {
            tierMachine = 2;
            getCasingTextureID();
            updateHatchTexture();
            return true;
        }
        getCasingTextureID();
        updateHatchTexture();
        return false;
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
}
