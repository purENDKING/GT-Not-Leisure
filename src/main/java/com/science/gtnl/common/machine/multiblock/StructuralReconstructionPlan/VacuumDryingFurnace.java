package com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTStructureUtility.ofCoil;

import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.machine.multiMachineClasses.GTMMultiMachineBase;

import bartworks.util.BWUtil;
import gregtech.api.enums.HeatingCoilLevel;
import gregtech.api.enums.TAE;
import gregtech.api.enums.Textures;
import gregtech.api.gui.modularui.GTUITextures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import gtPlusPlus.api.recipe.GTPPRecipeMaps;
import gtPlusPlus.core.block.ModBlocks;
import gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock;
import tectech.thing.metaTileEntity.hatch.MTEHatchEnergyTunnel;

public class VacuumDryingFurnace extends GTMMultiMachineBase<VacuumDryingFurnace> implements ISurvivalConstructable {

    public static final String STRUCTURE_PIECE_MAIN = "main";
    private static IStructureDefinition<VacuumDryingFurnace> STRUCTURE_DEFINITION = null;
    public static final String VDF_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/vacuum_drying_furnace";
    private static final int MACHINEMODE_VACUUMFURNACE = 0;
    private static final int MACHINEMODE_DEHYDRATOR = 1;
    private static final int MACHINEMODE_COLD_TRAP = 2;
    private static final int MACHINEMODE_NUCLEAR_SALT = 3;
    private HeatingCoilLevel mCoilLevel;
    private int mHeatingCapacity = 0;
    public final int horizontalOffSet = 1;
    public final int verticalOffSet = 4;
    public final int depthOffSet = 0;
    public static String[][] shape = StructureUtils.readStructureFromFile(VDF_STRUCTURE_FILE_PATH);

    public VacuumDryingFurnace(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public VacuumDryingFurnace(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new VacuumDryingFurnace(this.mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(TexturesGtBlock.oMCAIndustrialDehydratorActive)
                    .extFacing()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(TexturesGtBlock.oMCAIndustrialDehydrator)
                    .extFacing()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }

    public int getCasingTextureID() {
        return TAE.getIndexFromPage(3, 10);
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return switch (machineMode) {
            case 1 -> GTPPRecipeMaps.chemicalDehydratorNonCellRecipes;
            case 2 -> GTPPRecipeMaps.coldTrapRecipes;
            case 3 -> GTPPRecipeMaps.nuclearSaltProcessingPlantRecipes;
            default -> GTPPRecipeMaps.vacuumFurnaceRecipes;
        };
    }

    @Nonnull
    @Override
    public Collection<RecipeMap<?>> getAvailableRecipeMaps() {
        return Arrays.asList(
            GTPPRecipeMaps.chemicalDehydratorNonCellRecipes,
            GTPPRecipeMaps.vacuumFurnaceRecipes,
            GTPPRecipeMaps.coldTrapRecipes,
            GTPPRecipeMaps.nuclearSaltProcessingPlantRecipes);
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.VacuumDryingFurnaceRecipeType)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_00)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_01)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_02)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_03)
            .addInfo(TextLocalization.Tooltip_VacuumDryingFurnace_00)
            .addInfo(TextLocalization.Tooltip_VacuumDryingFurnace_01)
            .addInfo(TextLocalization.Tooltip_VacuumDryingFurnace_02)
            .addInfo(TextLocalization.Tooltip_VacuumDryingFurnace_03)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_04)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(3, 5, 3, true)
            .addInputHatch(TextLocalization.Tooltip_VacuumDryingFurnace_Casing)
            .addInputBus(TextLocalization.Tooltip_VacuumDryingFurnace_Casing)
            .addOutputBus(TextLocalization.Tooltip_VacuumDryingFurnace_Casing)
            .addOutputHatch(TextLocalization.Tooltip_VacuumDryingFurnace_Casing)
            .addEnergyHatch(TextLocalization.Tooltip_VacuumDryingFurnace_Casing)
            .addMaintenanceHatch(TextLocalization.Tooltip_VacuumDryingFurnace_Casing)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IStructureDefinition<VacuumDryingFurnace> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<VacuumDryingFurnace>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', ofCoil(VacuumDryingFurnace::setCoilLevel, VacuumDryingFurnace::getCoilLevel))
                .addElement(
                    'B',
                    buildHatchAdder(VacuumDryingFurnace.class)
                        .atLeast(InputHatch, OutputHatch, InputBus, OutputBus, Maintenance, Energy.or(ExoticEnergy))
                        .casingIndex(getCasingTextureID())
                        .dot(1)
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(ModBlocks.blockCasings4Misc, 10))))
                .addElement('C', Muffler.newAny(getCasingTextureID(), 1))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    public void setMachineModeIcons() {
        machineModeIcons.clear();
        machineModeIcons.add(GTUITextures.OVERLAY_BUTTON_MACHINEMODE_STEAM);
        machineModeIcons.add(GTUITextures.OVERLAY_BUTTON_MACHINEMODE_LPF_FLUID);
        machineModeIcons.add(GTUITextures.OVERLAY_BUTTON_MACHINEMODE_LPF_METAL);
        machineModeIcons.add(GTUITextures.OVERLAY_BUTTON_MACHINEMODE_WASHPLANT);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("mode", machineMode);
    }

    @Override
    public void loadNBTData(final NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        machineMode = aNBT.getInteger("mode");
    }

    @Override
    public void addUIWidgets(ModularWindow.Builder builder, UIBuildContext buildContext) {
        super.addUIWidgets(builder, buildContext);
        setMachineModeIcons();
        builder.widget(createModeSwitchButton(builder));
    }

    @Override
    public final void onScrewdriverRightClick(ForgeDirection side, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        this.machineMode = (byte) ((this.machineMode + 1) % 4);
        GTUtility
            .sendChatToPlayer(aPlayer, StatCollector.translateToLocal("VacuumDryingFurnace_Mode_" + this.machineMode));
    }

    @Override
    public String getMachineModeName() {
        return StatCollector.translateToLocal("VacuumDryingFurnace_Mode_" + machineMode);
    }

    @Override
    public ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @Nonnull
            @Override
            protected OverclockCalculator createOverclockCalculator(@Nonnull GTRecipe recipe) {
                return super.createOverclockCalculator(recipe).setMachineHeat(VacuumDryingFurnace.this.mHeatingCapacity)
                    .setHeatOC(true)
                    .setHeatDiscount(false)
                    .setEUtDiscount(0.8 - (ParallelTier / 50.0))
                    .setSpeedBoost(1/1.67 - (ParallelTier / 200.0) * ((machineMode >= 2) ? 1 : 0.1));
            }

        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }

    public void setCoilLevel(HeatingCoilLevel aCoilLevel) {
        this.mCoilLevel = aCoilLevel;
    }

    public HeatingCoilLevel getCoilLevel() {
        return this.mCoilLevel;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        this.mHeatingCapacity = 0;
        mCasing = 0;
        ParallelTier = 0;
        this.setCoilLevel(HeatingCoilLevel.None);

        if (!checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet) && checkHatch()) {
            return false;
        }

        if (getCoilLevel() == HeatingCoilLevel.None) return false;

        if (mMaintenanceHatches.size() != 1 && mMufflerHatches.size() != 1) return false;

        this.mHeatingCapacity = (int) this.getCoilLevel()
            .getHeat() + 100 * (BWUtil.getTier(this.getMaxInputEu()) - 2);

        energyHatchTier = checkEnergyHatchTier();
        for (MTEHatch hatch : getExoticEnergyHatches()) {
            if (hatch instanceof MTEHatchEnergyTunnel) {
                return false;
            }
        }

        ParallelTier = getParallelTier(aStack);
        return mCasing >= 10;
    }

    @Override
    public boolean supportsMachineModeSwitch() {
        return true;
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
