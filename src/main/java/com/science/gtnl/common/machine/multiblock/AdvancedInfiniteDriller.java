package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.common.block.Casings.BasicBlocks.MetaCasing;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_ORE_DRILL;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_ORE_DRILL_ACTIVE;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTStructureUtility.ofFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.gtnewhorizons.modularui.api.math.MainAxisAlignment;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.gtnewhorizons.modularui.common.widget.*;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.enums.TierEU;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.machine.multiMachineClasses.MultiMachineBase;

import goodgenerator.loader.Loaders;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.enums.VoidingMode;
import gregtech.api.gui.modularui.GTUITextures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.*;
import gregtech.api.util.shutdown.ShutDownReasonRegistry;
import gregtech.common.blocks.BlockCasings8;
import gtneioreplugin.plugin.block.ModBlocks;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;

public class AdvancedInfiniteDriller extends MultiMachineBase<AdvancedInfiniteDriller>
    implements ISurvivalConstructable {

    private int excessFuel = 0;
    private int mCasing;

    private static IStructureDefinition<AdvancedInfiniteDriller> STRUCTURE_DEFINITION = null;
    public static final String STRUCTURE_PIECE_MAIN = "main";
    public static final String AID_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/advanced_infinite_driller";
    public static String[][] shape = StructureUtils.readStructureFromFile(AID_STRUCTURE_FILE_PATH);
    public final int horizontalOffSet = 12;
    public final int verticalOffSet = 39;
    public final int depthOffSet = 0;
    protected static final int CASING_INDEX = ((BlockCasings8) sBlockCasings8).getTextureIndex(10);

    public AdvancedInfiniteDriller(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public AdvancedInfiniteDriller(String aName) {
        super(aName);
    }

    @Override
    public boolean isEnablePerfectOverclock() {
        return false;
    }

    @Override
    public int getMaxParallelRecipes() {
        return 1;
    }

    @Override
    public float getSpeedBonus() {
        return 1F;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        final MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.AdvancedInfiniteDrillerRecipeType)
            .addInfo(TextLocalization.Tooltip_AdvancedInfiniteDriller_00)
            .addInfo(TextLocalization.Tooltip_AdvancedInfiniteDriller_01)
            .addInfo(TextLocalization.Tooltip_AdvancedInfiniteDriller_02)
            .addInfo(TextLocalization.Tooltip_AdvancedInfiniteDriller_03)
            .addInfo(TextLocalization.Tooltip_AdvancedInfiniteDriller_04)
            .addInfo(TextLocalization.Tooltip_AdvancedInfiniteDriller_05)
            .addInfo(TextLocalization.Tooltip_AdvancedInfiniteDriller_06)
            .addInfo(TextLocalization.Tooltip_AdvancedInfiniteDriller_07)
            .addInfo(TextLocalization.Tooltip_AdvancedInfiniteDriller_08)
            .addInfo(TextLocalization.Tooltip_AdvancedInfiniteDriller_09)
            .addInfo(TextLocalization.Tooltip_AdvancedInfiniteDriller_10)
            .addInfo(TextLocalization.Tooltip_AdvancedInfiniteDriller_11)
            .addInfo(TextLocalization.Tooltip_Tectech_Hatch)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(25, 41, 25, true)
            .addInputBus(TextLocalization.Tooltip_AdvancedInfiniteDriller_Casing)
            .addInputHatch(TextLocalization.Tooltip_AdvancedInfiniteDriller_Casing)
            .addOutputHatch(TextLocalization.Tooltip_AdvancedInfiniteDriller_Casing)
            .addEnergyHatch(TextLocalization.Tooltip_AdvancedInfiniteDriller_Casing)
            .addMaintenanceHatch(TextLocalization.Tooltip_AdvancedInfiniteDriller_Casing)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IStructureDefinition<AdvancedInfiniteDriller> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<AdvancedInfiniteDriller>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', ofBlock(Loaders.MAR_Casing, 0))
                .addElement('B', ofBlock(MetaCasing, 5))
                .addElement('C', ofBlock(MetaCasing, 16))
                .addElement('D', ofBlock(MetaCasing, 18))
                .addElement('E', ofBlock(sBlockCasings1, 14))
                .addElement('F', ofBlock(sSolenoidCoilCasings, 5))
                .addElement('G', ofBlock(sBlockCasings3, 11))
                .addElement('H', ofBlock(sBlockCasings8, 1))
                .addElement('I', ofBlock(sBlockCasings8, 7))
                .addElement(
                    'J',
                    buildHatchAdder(AdvancedInfiniteDriller.class).casingIndex(CASING_INDEX)
                        .dot(1)
                        .atLeast(InputBus, InputHatch, OutputHatch, Energy.or(ExoticEnergy))
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(sBlockCasings8, 10))))
                .addElement('K', ofFrame(Materials.Neutronium))
                .addElement('L', ofBlock(sBlockMetal8, 0))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_ORE_DRILL_ACTIVE)
                    .extFacing()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_ORE_DRILL)
                    .extFacing()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }

    public int getCasingTextureID() {
        return CASING_INDEX;
    }

    @Override
    @NotNull
    public CheckRecipeResult checkProcessing() {
        if (this.excessFuel < 300) {
            this.excessFuel = 300;
        }

        if (this.excessFuel > 10000) {
            this.excessFuel = 10000;
        }

        ItemStack controllerSlot = getControllerSlot();
        if (controllerSlot == null || !controllerSlot
            .isItemEqual(GTOreDictUnificator.get(OrePrefixes.toolHeadDrill, Materials.Infinity, 1L))) {
            this.excessFuel = Math.max(300, this.excessFuel - 4);
            return CheckRecipeResultRegistry.NO_RECIPE;
        }

        ArrayList<FluidStack> storedFluids = getStoredFluids();
        if (storedFluids.isEmpty()) {
            return CheckRecipeResultRegistry.NO_RECIPE;
        }

        if (this.excessFuel < 2000) {
            int consumptionCount = 0;

            for (FluidStack tFluid : storedFluids) {
                if (tFluid != null && tFluid.getFluid()
                    .getName()
                    .equals("pyrotheum")) {
                    int consumption = (int) Math.pow(this.excessFuel, 1.3);
                    if (tFluid.amount >= consumption) {
                        tFluid.amount -= consumption;
                        consumptionCount++;
                    }
                }
            }

            if (consumptionCount > 0) {
                this.excessFuel += consumptionCount;
                this.mMaxProgresstime = 32;
                this.lEUt = (int) -TierEU.RECIPE_ZPM;
                return CheckRecipeResultRegistry.SUCCESSFUL;
            } else {
                this.excessFuel = Math.max(300, this.excessFuel - 4);
                return CheckRecipeResultRegistry.NO_RECIPE;
            }
        } else {
            int eut = 0;
            List<FluidStack> outputFluids = new ArrayList<>();
            for (ItemStack item : getAllStoredInputs()) {
                if (item.getItem() != null) {
                    if (item.isItemEqual(new ItemStack(ModBlocks.getBlock("DD")))) {
                        int random = new Random().nextInt(4) + 1;
                        switch (random) {
                            case 1:
                                outputFluids.add(Materials.Oxygen.getGas(excessFuel * 1000L));
                                eut += 128000;
                                break;
                            case 2:
                                outputFluids.add(Materials.Nitrogen.getGas(excessFuel * 1000L));
                                eut += 128000;
                                break;
                            case 3:
                                outputFluids.add(Materials.Hydrogen.getGas(excessFuel * 1000L));
                                eut += 128000;
                                break;
                            case 4:
                                outputFluids.add(Materials.Argon.getGas(excessFuel * 1000L));
                                eut += 128000;
                                break;
                        }
                    }
                    if (item.isItemEqual(new ItemStack(ModBlocks.getBlock("Ow")))) {
                        int random = new Random().nextInt(4) + 1;
                        switch (random) {
                            case 1:
                                outputFluids.add(Materials.Oxygen.getGas(excessFuel * 1000L));
                                eut += 128000;
                                break;
                            case 2:
                                outputFluids.add(Materials.Nitrogen.getGas(excessFuel * 1000L));
                                eut += 128000;
                                break;
                            case 3:
                                outputFluids.add(Materials.Hydrogen.getGas(excessFuel * 1000L));
                                eut += 128000;
                                break;
                            case 4:
                                outputFluids.add(Materials.Argon.getGas(excessFuel * 1000L));
                                eut += 128000;
                                break;
                        }
                    }
                }
            }

            if (!outputFluids.isEmpty()) {
                mOutputFluids = outputFluids.toArray(new FluidStack[0]);
            }

            if (mOutputFluids != null) {
                this.mMaxProgresstime = (5750000 / excessFuel) - 475;
                this.lEUt = -eut;
                return CheckRecipeResultRegistry.SUCCESSFUL;
            }
        }

        return CheckRecipeResultRegistry.NO_RECIPE;
    }

    @Override
    public ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @NotNull
            @Override
            public OverclockCalculator createOverclockCalculator(@NotNull GTRecipe recipe) {
                return OverclockCalculator.ofNoOverclock(recipe);
            }
        };
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("excessFuel", excessFuel);
    }

    @Override
    public void loadNBTData(final NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        if (aNBT.hasKey("excessFuel")) {
            excessFuel = aNBT.getInteger("excessFuel");
        }
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        startRecipeProcessing();

        if (this.excessFuel > 2000 && mProgresstime != 0 && mProgresstime % 20 == 0) {
            this.excessFuel += (int) Math.floor(this.excessFuel / 2000);
        }

        if (this.excessFuel > 10000) {
            ItemStack controllerSlot = getControllerSlot();
            if (controllerSlot != null && controllerSlot
                .isItemEqual(GTOreDictUnificator.get(OrePrefixes.toolHeadDrill, Materials.Infinity, 1L))) {
                mInventory[getControllerSlotIndex()] = null;
            }
            this.stopMachine(ShutDownReasonRegistry.POWER_LOSS);
        }

        if (mProgresstime != 0) {
            if (this.mProgresstime % 5 == 0 && excessFuel >= 2000) {
                ArrayList<FluidStack> storedFluids = getStoredFluids();
                for (FluidStack tFluid : storedFluids) {
                    if (tFluid != null) {
                        if (tFluid.getFluid()
                            .getName()
                            .equals("pyrotheum")) {
                            int consumption = (int) Math.pow(this.excessFuel, 1.3);
                            if (tFluid.amount >= consumption) {
                                tFluid.amount -= consumption;
                                this.excessFuel += 1;
                            }
                        }

                        if (tFluid.getFluid()
                            .getName()
                            .equals("ic2distilledwater")) {
                            if (tFluid.amount >= 200000) {
                                tFluid.amount -= 200000;
                                this.excessFuel -= 1;
                            }
                        }

                        if (tFluid.getFluid()
                            .getName()
                            .equals("liquidoxygen")) {
                            if (tFluid.amount >= 200000) {
                                tFluid.amount -= 200000;
                                this.excessFuel -= 2;
                            }
                        }

                        if (tFluid.getFluid()
                            .getName()
                            .equals("liquid helium")) {
                            if (tFluid.amount >= 200000) {
                                tFluid.amount -= 200000;
                                this.excessFuel -= 4;
                            }
                        }
                    }
                }
            }
        } else if (aTick % 20 == 0 && !aBaseMetaTileEntity.isActive()) {
            this.excessFuel -= 4;
            if (excessFuel < 300) {
                excessFuel = 300;
            }
        }

        endRecipeProcessing();
        super.onPostTick(aBaseMetaTileEntity, aTick);
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        return checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet)
            && !mOutputHatches.isEmpty()
            && mCasing >= 570;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new AdvancedInfiniteDriller(this.mName);
    }

    @Override
    public int getDamageToComponent(ItemStack aStack) {
        return 1;
    }

    @Override
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y,
        int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        tag.setInteger("excessFuel", excessFuel);

    }

    @Override
    public void getWailaBody(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        super.getWailaBody(itemStack, currentTip, accessor, config);
        final NBTTagCompound tag = accessor.getNBTData();
        if (tag.hasKey("excessFuel")) {
            currentTip.add(
                TextLocalization.Info_AdvancedInfiniteDriller_00 + EnumChatFormatting.YELLOW
                    + tag.getInteger("excessFuel")
                    + "K");
        }
    }

    @Override
    public void addUIWidgets(ModularWindow.Builder builder, UIBuildContext buildContext) {
        builder.widget(
            new DrawableWidget().setDrawable(GTUITextures.PICTURE_SCREEN_BLACK)
                .setPos(4, 4)
                .setSize(190, 85));

        slotWidgets.clear();
        createInventorySlots();

        Column slotsColumn = new Column();
        for (int i = slotWidgets.size() - 1; i >= 0; i--) {
            slotsColumn.widget(slotWidgets.get(i));
        }
        builder.widget(
            slotsColumn.setAlignment(MainAxisAlignment.END)
                .setPos(173, 167 - 1));

        final DynamicPositionedColumn screenElements = new DynamicPositionedColumn();
        drawTexts(screenElements, !slotWidgets.isEmpty() ? slotWidgets.get(0) : null);
        screenElements
            .widget(
                new TextWidget().setStringSupplier(
                    () -> StatCollector.translateToLocalFormatted("Info_AdvancedInfiniteDriller_00") + excessFuel + "K")
                    .setDefaultColor(COLOR_TEXT_WHITE.get())
                    .setEnabled(true))
            .widget(
                new FakeSyncWidget.IntegerSyncer(() -> excessFuel, Fuel -> excessFuel = Fuel).setSynced(true, false));
        builder.widget(
            new Scrollable().setVerticalScroll()
                .widget(screenElements.setPos(10, 0))
                .setPos(0, 7)
                .setSize(190, 79));

        builder.widget(createPowerSwitchButton(builder))
            .widget(createVoidExcessButton(builder))
            .widget(createInputSeparationButton(builder))
            .widget(createBatchModeButton(builder))
            .widget(createLockToSingleRecipeButton(builder))
            .widget(createStructureUpdateButton(builder));

        DynamicPositionedRow configurationElements = new DynamicPositionedRow();
        addConfigurationWidgets(configurationElements, buildContext);

        builder.widget(
            configurationElements.setSpace(2)
                .setAlignment(MainAxisAlignment.SPACE_BETWEEN)
                .setPos(getRecipeLockingButtonPos().add(18, 0)));
    }

    @Override
    public boolean supportsVoidProtection() {
        return false;
    }

    @Override
    public VoidingMode getVoidingMode() {
        return VoidingMode.VOID_NONE;
    }

    @Override
    public boolean supportsInputSeparation() {
        return false;
    }

    @Override
    public boolean supportsBatchMode() {
        return false;
    }

    @Override
    public boolean supportsSingleRecipeLocking() {
        return false;
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
