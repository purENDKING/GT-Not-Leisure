package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.transpose;
import static gregtech.api.util.GTUtility.validMTEList;

import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.gtnewhorizons.gtnhintergalactic.block.IGBlocks;
import com.gtnewhorizons.gtnhintergalactic.item.IGItems;
import com.gtnewhorizons.gtnhintergalactic.item.ItemMiningDrones;
import com.gtnewhorizons.gtnhintergalactic.tile.multi.elevator.TileEntitySpaceElevator;
import com.gtnewhorizons.gtnhintergalactic.tile.multi.elevatormodules.ModuleOverclockDescriber;
import com.gtnewhorizons.gtnhintergalactic.tile.multi.elevatormodules.TileEntityModuleBase;
import com.gtnewhorizons.modularui.api.drawable.Text;
import com.gtnewhorizons.modularui.api.math.Color;
import com.gtnewhorizons.modularui.api.math.Pos2d;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.gtnewhorizons.modularui.common.widget.DynamicTextWidget;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.common.recipe.RecipeRegister;
import com.science.gtnl.common.recipe.Special.ResourceCollectionModuleTierKey;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Textures;
import gregtech.api.gui.modularui.GTUITextures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.interfaces.tileentity.IOverclockDescriptionProvider;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.metatileentity.implementations.MTEHatchEnergy;
import gregtech.api.objects.overclockdescriber.OverclockDescriber;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.recipe.check.SimpleCheckRecipeResult;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import tectech.thing.metaTileEntity.multi.base.TTMultiblockBase;
import tectech.thing.metaTileEntity.multi.base.render.TTRenderedExtendedFacingTexture;

public class ResourceCollectionModule extends TileEntityModuleBase implements IOverclockDescriptionProvider {

    private int ParallelTier;
    private int energyHatchTier;
    private static final int MACHINEMODE_MINER = 0;
    private static final int MACHINEMODE_DRILL = 1;
    private static IStructureDefinition<ResourceCollectionModule> STRUCTURE_DEFINITION = null;
    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final String SM_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/space_module";
    private static final String[][] shape = StructureUtils.readStructureFromFile(SM_STRUCTURE_FILE_PATH);
    public final ItemStack MiningDroneMkVIII = new ItemStack(
        IGItems.MiningDrones,
        16,
        ItemMiningDrones.DroneTiers.UV.ordinal());
    public final ItemStack MiningDroneMkIX = new ItemStack(
        IGItems.MiningDrones,
        16,
        ItemMiningDrones.DroneTiers.UHV.ordinal());
    public final ItemStack MiningDroneMkX = new ItemStack(
        IGItems.MiningDrones,
        16,
        ItemMiningDrones.DroneTiers.UEV.ordinal());
    public final ItemStack MiningDroneMkXI = new ItemStack(
        IGItems.MiningDrones,
        16,
        ItemMiningDrones.DroneTiers.UIV.ordinal());

    public ResourceCollectionModule(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional, 14, 1, 1);
    }

    public ResourceCollectionModule(String aName) {
        super(aName, 14, 1, 1);
    }

    @Override
    public OverclockDescriber getOverclockDescriber() {
        return new ModuleOverclockDescriber((byte) 1, 5);
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return (machineMode == MACHINEMODE_MINER) ? RecipeRegister.SpaceMinerRecipes : RecipeRegister.SpaceDrillRecipes;
    }

    @Nonnull
    @Override
    public Collection<RecipeMap<?>> getAvailableRecipeMaps() {
        return Arrays.asList(RecipeRegister.SpaceMinerRecipes, RecipeRegister.SpaceDrillRecipes);
    }

    @Override
    public void setMachineModeIcons() {
        machineModeIcons.clear();
        machineModeIcons.add(GTUITextures.OVERLAY_BUTTON_MACHINEMODE_LPF_METAL);
        machineModeIcons.add(GTUITextures.OVERLAY_BUTTON_MACHINEMODE_LPF_FLUID);
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
        builder.widget(
            new DynamicTextWidget(
                () -> new Text(
                    StatCollector.translateToLocal("Interaction_DESCRIPTION_Index_400")
                        + StatCollector.translateToLocal("ResourceCollectionModule_Mode_" + this.machineMode))
                            .color(Color.WHITE.normal)).setPos(10, 77));

        builder.widget(createModeSwitchButton(builder));
    }

    @Override
    public Pos2d getMachineModeSwitchButtonPos() {
        return new Pos2d(174, 97);
    }

    @Override
    public final void onScrewdriverRightClick(ForgeDirection side, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        this.machineMode = (byte) ((this.machineMode + 1) % 2);
        GTUtility.sendChatToPlayer(
            aPlayer,
            StatCollector.translateToLocal("ResourceCollectionModule_Mode_" + this.machineMode));
    }

    @Override
    public String getMachineModeName() {
        return StatCollector.translateToLocal("ResourceCollectionModule_Mode_" + machineMode);
    }

    @Override
    public boolean supportsMachineModeSwitch() {
        return true;
    }

    @Override
    public IStructureDefinition<? extends TTMultiblockBase> getStructure_EM() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<ResourceCollectionModule>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement(
                    'H',
                    GTStructureUtility.ofHatchAdderOptional(
                        ResourceCollectionModule::addClassicToMachineList,
                        4096,
                        1,
                        IGBlocks.SpaceElevatorCasing,
                        0))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        structureBuild_EM(STRUCTURE_PIECE_MAIN, 0, 1, 0, stackSize, hintsOnly);
    }

    @Override
    public boolean checkMachine_EM(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        fixAllIssues();
        ParallelTier = 0;

        if (!structureCheck_EM(STRUCTURE_PIECE_MAIN, 0, 1, 0)) return false;

        energyHatchTier = checkEnergyHatchTier();
        ParallelTier = getParallelTier(aStack);

        return true;
    }

    @Override
    public void setProcessingLogicPower(ProcessingLogic logic) {
        boolean useSingleAmp = mEnergyHatches.size() == 1 && mExoticEnergyHatches.isEmpty();
        logic.setAvailableVoltage(getMachineVoltageLimit());
        logic.setAvailableAmperage(useSingleAmp ? 2 : getMaxInputAmps());
        logic.setAmperageOC(useSingleAmp);
    }

    public long getMachineVoltageLimit() {
        return GTValues.V[energyHatchTier];
    }

    public int checkEnergyHatchTier() {
        int tier = 0;
        for (MTEHatchEnergy tHatch : validMTEList(mEnergyHatches)) {
            tier = Math.max(tHatch.mTier, tier);
        }
        for (MTEHatch tHatch : validMTEList(mExoticEnergyHatches)) {
            tier = Math.max(tHatch.mTier, tier);
        }
        return tier;
    }

    public int getMaxParallelRecipes() {
        if (ParallelTier <= 1) {
            return 8;
        } else {
            return (int) Math.pow(4, ParallelTier - 2);
        }
    }

    @Override
    public ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @NotNull
            @Override
            protected CheckRecipeResult validateRecipe(@NotNull GTRecipe recipe) {
                int recipeReq = recipe.getMetadataOrDefault(ResourceCollectionModuleTierKey.INSTANCE, 0);
                ItemStack miningDrone = findMiningDrone();
                if (miningDrone != null) {
                    if (recipeReq == 1) {
                        if (miningDrone.isItemEqual(MiningDroneMkVIII)) {
                            return CheckRecipeResultRegistry.SUCCESSFUL;
                        }
                        return CheckRecipeResultRegistry.NO_RECIPE;
                    } else if (recipeReq == 2) {
                        if (miningDrone.isItemEqual(MiningDroneMkIX)) {
                            return CheckRecipeResultRegistry.SUCCESSFUL;
                        }
                        return CheckRecipeResultRegistry.NO_RECIPE;
                    } else if (recipeReq == 3) {
                        if (miningDrone.isItemEqual(MiningDroneMkX)) {
                            return CheckRecipeResultRegistry.SUCCESSFUL;
                        }
                        return CheckRecipeResultRegistry.NO_RECIPE;
                    } else if (recipeReq == 4) {
                        if (miningDrone.isItemEqual(MiningDroneMkXI)) {
                            return CheckRecipeResultRegistry.SUCCESSFUL;
                        }
                        return CheckRecipeResultRegistry.NO_RECIPE;
                    }
                    return super.validateRecipe(recipe);
                }
                return SimpleCheckRecipeResult.ofFailure("no_mining_drone");
            }

            @NotNull
            @Override
            public OverclockCalculator createOverclockCalculator(@NotNull GTRecipe recipe) {
                return super.createOverclockCalculator(recipe).setAmperageOC(true)
                    .setDurationDecreasePerOC(2)
                    .setEUtIncreasePerOC(4)
                    .setAmperage(availableAmperage)
                    .setEUtDiscount(1 - (ParallelTier / 50.0))
                    .setSpeedBoost(1 - (ParallelTier / 200.0));
            }

        }.setAmperageOC(false)
            .setMaxParallelSupplier(() -> getMaxParallelRecipes());
    }

    private ItemStack findMiningDrone() {
        for (ItemStack item : getAllStoredInputs()) {
            if (item != null) {
                if (item.isItemEqual(MiningDroneMkVIII) || item.isItemEqual(MiningDroneMkIX)
                    || item.isItemEqual(MiningDroneMkX)
                    || item.isItemEqual(MiningDroneMkXI)) {
                    return item;
                }
            }
        }
        return null;
    }

    @Override
    public void parametersInstantiation_EM() {
        super.parametersInstantiation_EM();
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
        int colorIndex, boolean aActive, boolean aRedstone) {
        if (side == facing) {
            return new ITexture[] {
                Textures.BlockIcons.getCasingTextureForId(TileEntitySpaceElevator.CASING_INDEX_BASE),
                new TTRenderedExtendedFacingTexture(aActive ? TTMultiblockBase.ScreenON : TTMultiblockBase.ScreenOFF) };
        } else if (facing.getRotation(ForgeDirection.UP) == side || facing.getRotation(ForgeDirection.DOWN) == side) {
            return new ITexture[] {
                Textures.BlockIcons.getCasingTextureForId(TileEntitySpaceElevator.CASING_INDEX_BASE) };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(TileEntitySpaceElevator.CASING_INDEX_BASE) };
    }

    @Override
    public boolean protectsExcessItem() {
        return !eSafeVoid;
    }

    @Override
    public boolean protectsExcessFluid() {
        return !eSafeVoid;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new ResourceCollectionModule(mName);
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        final MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.ResourceCollectionModuleRecipeType)
            .addInfo(TextLocalization.Tooltip_ResourceCollectionModule_00)
            .addInfo(TextLocalization.Tooltip_ResourceCollectionModule_01)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_02)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_03)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(1, 5, 2, false)
            .addInputBus(TextLocalization.Tooltip_ResourceCollectionModule_Casing, 1)
            .addOutputBus(TextLocalization.Tooltip_ResourceCollectionModule_Casing, 1)
            .addInputHatch(TextLocalization.Tooltip_ResourceCollectionModule_Casing, 1)
            .addOutputHatch(TextLocalization.Tooltip_ResourceCollectionModule_Casing, 1)
            .toolTipFinisher();
        return tt;
    }

    public int getParallelTier(ItemStack inventory) {
        if (inventory == null) return 0;
        if (inventory.isItemEqual(GTNLItemList.LVParallelControllerCore.getInternalStack_unsafe())) {
            return 1;
        } else if (inventory.isItemEqual(GTNLItemList.MVParallelControllerCore.getInternalStack_unsafe())) {
            return 2;
        } else if (inventory.isItemEqual(GTNLItemList.HVParallelControllerCore.getInternalStack_unsafe())) {
            return 3;
        } else if (inventory.isItemEqual(GTNLItemList.EVParallelControllerCore.getInternalStack_unsafe())) {
            return 4;
        } else if (inventory.isItemEqual(GTNLItemList.IVParallelControllerCore.getInternalStack_unsafe())) {
            return 5;
        } else if (inventory.isItemEqual(GTNLItemList.LuVParallelControllerCore.getInternalStack_unsafe())) {
            return 6;
        } else if (inventory.isItemEqual(GTNLItemList.ZPMParallelControllerCore.getInternalStack_unsafe())) {
            return 7;
        } else if (inventory.isItemEqual(GTNLItemList.UVParallelControllerCore.getInternalStack_unsafe())) {
            return 8;
        } else if (inventory.isItemEqual(GTNLItemList.UHVParallelControllerCore.getInternalStack_unsafe())) {
            return 9;
        } else if (inventory.isItemEqual(GTNLItemList.UEVParallelControllerCore.getInternalStack_unsafe())) {
            return 10;
        } else if (inventory.isItemEqual(GTNLItemList.UIVParallelControllerCore.getInternalStack_unsafe())) {
            return 11;
        } else if (inventory.isItemEqual(GTNLItemList.UMVParallelControllerCore.getInternalStack_unsafe())) {
            return 12;
        } else if (inventory.isItemEqual(GTNLItemList.UXVParallelControllerCore.getInternalStack_unsafe())) {
            return 13;
        } else if (inventory.isItemEqual(GTNLItemList.MAXParallelControllerCore.getInternalStack_unsafe())) {
            return 14;
        }
        return 0;
    }
}
