package com.science.gtnl.common.machine.multiblock.ModuleMachine.EGTW;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.gtnewhorizons.gtnhintergalactic.block.IGBlocks;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.machine.multiMachineClasses.MultiMachineBase;

import bartworks.common.loaders.ItemRegistry;
import goodgenerator.loader.Loaders;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.HeatingCoilLevel;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Mods;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IHatchElement;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.interfaces.tileentity.IWirelessEnergyHatchInformation;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.HatchElementBuilder;
import gregtech.api.util.IGTHatchAdder;
import gregtech.api.util.MultiblockTooltipBuilder;
import gtPlusPlus.core.block.ModBlocks;
import tectech.thing.casing.TTCasingsContainer;

public class EternalGregTechWorkshop extends MultiMachineBase<EternalGregTechWorkshop>
    implements IWirelessEnergyHatchInformation {

    // 75 x 19 x 75
    public static final String STRUCTURE_PIECE_MAIN_TOP = "main_top";
    private final int HORIZONTAL_OFF_SET_TOP = 37;
    private final int VERTICAL_OFF_SET_TOP = 46;
    private final int DEPTH_OFF_SET_TOP = 11;

    // 53 x 22 x 53
    public static final String STRUCTURE_PIECE_MAIN_UP = "main_up";
    private final int HORIZONTAL_OFF_SET_UP = 26;
    private final int VERTICAL_OFF_SET_UP = 27;
    private final int DEPTH_OFF_SET_UP = 0;

    // 53 x 11 x 53
    public static final String STRUCTURE_PIECE_MAIN = "main";
    private final int HORIZONTAL_OFF_SET = 26;
    private final int VERTICAL_OFF_SET = 5;
    private final int DEPTH_OFF_SET = 1;

    // 53 x 22 x 53
    public static final String STRUCTURE_PIECE_MAIN_DOWN = "main_down";
    private final int HORIZONTAL_OFF_SET_DOWN = 26;
    private final int VERTICAL_OFF_SET_DOWN = -6;
    private final int DEPTH_OFF_SET_DOWN = 0;

    // 75 x 22 x 75
    public static final String STRUCTURE_PIECE_MAIN_BOTTOM = "main_bottom";
    private final int HORIZONTAL_OFF_SET_BOTTOM = 37;
    private final int VERTICAL_OFF_SET_BOTTOM = -28;
    private final int DEPTH_OFF_SET_BOTTOM = 11;

    // 63 x 7 x 63
    public static final String STRUCTURE_PIECE_MAIN_EXTRA = "main_extra";
    private final int HORIZONTAL_OFF_SET_EXTRA = 31;
    private final int VERTICAL_OFF_SET_EXTRA_UP = 14;
    private final int VERTICAL_OFF_SET_EXTRA_DOWN = -8;
    private final int DEPTH_OFF_SET_EXTRA = 5;

    public static final String EGTWT_STRUCTURE_FILE_PATH = RESOURCE_ROOT_ID + ":"
        + "multiblock/eternal_gregTech_workshop/top";
    public static final String EGTWU_STRUCTURE_FILE_PATH = RESOURCE_ROOT_ID + ":"
        + "multiblock/eternal_gregTech_workshop/up";
    public static final String EGTWC_STRUCTURE_FILE_PATH = RESOURCE_ROOT_ID + ":"
        + "multiblock/eternal_gregTech_workshop/center";
    public static final String EGTWD_STRUCTURE_FILE_PATH = RESOURCE_ROOT_ID + ":"
        + "multiblock/eternal_gregTech_workshop/down";
    public static final String EGTWB_STRUCTURE_FILE_PATH = RESOURCE_ROOT_ID + ":"
        + "multiblock/eternal_gregTech_workshop/bottom";
    public static final String EGTWE_STRUCTURE_FILE_PATH = RESOURCE_ROOT_ID + ":"
        + "multiblock/eternal_gregTech_workshop/extra";
    public static String[][] shape_top = StructureUtils.readStructureFromFile(EGTWT_STRUCTURE_FILE_PATH);
    public static String[][] shape_up = StructureUtils.readStructureFromFile(EGTWU_STRUCTURE_FILE_PATH);
    public static String[][] shape = StructureUtils.readStructureFromFile(EGTWC_STRUCTURE_FILE_PATH);
    public static String[][] shape_down = StructureUtils.readStructureFromFile(EGTWD_STRUCTURE_FILE_PATH);
    public static String[][] shape_bottom = StructureUtils.readStructureFromFile(EGTWB_STRUCTURE_FILE_PATH);
    public static String[][] shape_extra = StructureUtils.readStructureFromFile(EGTWE_STRUCTURE_FILE_PATH);

    public final int CASING_INDEX = 960;

    public static final String TEXTURE_OVERLAY_FRONT_SCREEN_ON = Mods.GregTech.ID + ":"
        + "iconsets/GODFORGE_CONTROLLER";
    public static Textures.BlockIcons.CustomIcon ScreenON = new Textures.BlockIcons.CustomIcon(
        TEXTURE_OVERLAY_FRONT_SCREEN_ON);
    public static final String TEXTURE_OVERLAY_FRONT_SCREEN_OFF = Mods.GregTech.ID + ":" + "iconsets/SCREEN_OFF";
    public static Textures.BlockIcons.CustomIcon ScreenOFF = new Textures.BlockIcons.CustomIcon(
        TEXTURE_OVERLAY_FRONT_SCREEN_OFF);

    private int tCountCasing = 0;
    private HeatingCoilLevel heatLevel;
    private int coilTier = 0;
    private int mHeatingCapacity = 0;
    private int mMachineTier = 0;
    private int mModuleTier = 0;
    private double setEUtDiscount = 1;
    private double setSpeedBoost = 1;

    public ArrayList<EternalGregTechWorkshopModule> moduleHatches = new ArrayList<>();

    public EternalGregTechWorkshop(String aName) {
        super(aName);
    }

    public EternalGregTechWorkshop(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new EternalGregTechWorkshop(this.mName);
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.EternalGregTechWorkshopRecipeType)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(75, 96, 75, true)
            .addInputBus(TextLocalization.Tooltip_EternalGregTechWorkshop_Casing, 1)
            .addOutputBus(TextLocalization.Tooltip_EternalGregTechWorkshop_Casing, 1)
            .addInputHatch(TextLocalization.Tooltip_EternalGregTechWorkshop_Casing, 1)
            .addOutputHatch(TextLocalization.Tooltip_EternalGregTechWorkshop_Casing, 1)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
        int colorIndex, boolean aActive, boolean aRedstone) {
        if (side == facing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(CASING_INDEX + 1),
                TextureFactory.builder()
                    .addIcon(ScreenON)
                    .extFacing()
                    .build(),
                TextureFactory.builder()
                    .addIcon(ScreenON)
                    .extFacing()
                    .glow()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(CASING_INDEX + 1),
                TextureFactory.builder()
                    .addIcon(ScreenOFF)
                    .extFacing()
                    .build(),
                TextureFactory.builder()
                    .addIcon(ScreenOFF)
                    .extFacing()
                    .glow()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(CASING_INDEX + 1) };
    }

    @Override
    public boolean isEnablePerfectOverclock() {
        return true;
    }

    @Override
    public float getSpeedBonus() {
        return 1;
    }

    @Override
    public int getMaxParallelRecipes() {
        return 1;
    }

    @Override
    public int getCasingTextureID() {
        return CASING_INDEX;
    }

    @Override
    public IStructureDefinition<EternalGregTechWorkshop> getStructureDefinition() {
        return StructureDefinition.<EternalGregTechWorkshop>builder()
            .addShape(STRUCTURE_PIECE_MAIN_TOP, transpose(shape_top))
            .addShape(STRUCTURE_PIECE_MAIN_UP, transpose(shape_up))
            .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
            .addShape(STRUCTURE_PIECE_MAIN_DOWN, transpose(shape_down))
            .addShape(STRUCTURE_PIECE_MAIN_BOTTOM, transpose(shape_bottom))
            .addShape(STRUCTURE_PIECE_MAIN_EXTRA, transpose(shape_extra))
            .addElement('A', ofBlock(TTCasingsContainer.GodforgeCasings, 0))
            .addElement('B', ofBlock(Loaders.componentAssemblylineCasing, 12))
            .addElement('C', ofBlock(GregTechAPI.sBlockCasings1, 13))
            .addElement('D', ofBlock(IGBlocks.SpaceElevatorMotor, 4))
            .addElement('E', ofBlock(GregTechAPI.sBlockCasings10, 11))
            .addElement('F', ofBlock(GregTechAPI.sBlockCasings9, 12))
            .addElement('G', ofBlock(TTCasingsContainer.GodforgeCasings, 1))
            .addElement('H', ofBlock(GregTechAPI.sBlockCasings1, 14))
            .addElement('I', ofBlock(ModBlocks.blockCasings5Misc, 14))
            .addElement('J', ofBlock(GregTechAPI.sBlockCasings9, 14))
            .addElement('K', ofFrame(Materials.NaquadahAlloy))
            .addElement('L', ofBlock(GregTechAPI.sBlockGlass1, 2))
            .addElement('M', ofBlock(ItemRegistry.bw_realglas2, 0))
            .addElement(
                'N',
                buildHatchAdder(EternalGregTechWorkshop.class).atLeast(InputBus, OutputBus, InputHatch, OutputHatch)
                    .casingIndex(CASING_INDEX + 1)
                    .dot(1)
                    .buildAndChain(
                        onElementPass(x -> ++x.tCountCasing, ofBlock(TTCasingsContainer.GodforgeCasings, 1))))
            .addElement('O', ofBlock(GregTechAPI.sBlockCasings10, 2))
            .addElement('P', ofBlock(TTCasingsContainer.sBlockCasingsBA0, 10))
            .addElement('Q', ofBlock(TTCasingsContainer.GodforgeCasings, 7))
            .addElement('R', ofBlock(TTCasingsContainer.GodforgeCasings, 4))
            .addElement('S', ofBlock(TTCasingsContainer.GodforgeCasings, 8))
            .addElement('T', ofBlock(Loaders.gravityStabilizationCasing, 0))
            .addElement('U', ofBlock(TTCasingsContainer.SpacetimeCompressionFieldGenerators, 8))
            .addElement('V', ofBlock(TTCasingsContainer.TimeAccelerationFieldGenerator, 8))
            .addElement('W', ofBlock(TTCasingsContainer.sBlockCasingsBA0, 11))
            .addElement('X', ofBlock(TTCasingsContainer.StabilisationFieldGenerators, 8))
            .addElement(
                'Y',
                HatchElementBuilder.<EternalGregTechWorkshop>builder()
                    .atLeast(EternalGregTechWorkshop.moduleElement.Module)
                    .casingIndex(CASING_INDEX)
                    .dot(1)
                    .buildAndChain(TTCasingsContainer.GodforgeCasings, 0))
            .addElement('Z', ofChain(ofBlock(GregTechAPI.sBlockCasings1, 13), ofBlock(Blocks.dirt, 0)))
            .build();
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        tCountCasing = 0;
        moduleHatches.clear();
        int checkTier = 0;
        boolean enableExtraModule = true;

        if (!checkPiece(STRUCTURE_PIECE_MAIN, HORIZONTAL_OFF_SET, VERTICAL_OFF_SET, DEPTH_OFF_SET)) return false;

        while (checkTier < Integer.MAX_VALUE - 1) {
            if (!checkPiece(
                STRUCTURE_PIECE_MAIN_UP,
                HORIZONTAL_OFF_SET_UP,
                VERTICAL_OFF_SET_UP + checkTier * 22,
                DEPTH_OFF_SET_UP)) {
                break;
            }
            if (!checkPiece(
                STRUCTURE_PIECE_MAIN_DOWN,
                HORIZONTAL_OFF_SET_DOWN,
                VERTICAL_OFF_SET_DOWN - checkTier * 22,
                DEPTH_OFF_SET_DOWN)) {
                break;
            }
            checkTier++;
        }

        if (!checkPiece(
            STRUCTURE_PIECE_MAIN_TOP,
            HORIZONTAL_OFF_SET_TOP,
            VERTICAL_OFF_SET_TOP + (checkTier - 1) * 22,
            DEPTH_OFF_SET_TOP)) {
            return false;
        }

        if (!checkPiece(
            STRUCTURE_PIECE_MAIN_BOTTOM,
            HORIZONTAL_OFF_SET_BOTTOM,
            VERTICAL_OFF_SET_BOTTOM - (checkTier - 1) * 22,
            DEPTH_OFF_SET_BOTTOM)) {
            return false;
        }

        if (enableExtraModule) {
            for (int i = 0; i < checkTier; i++) {
                if (!checkPiece(
                    STRUCTURE_PIECE_MAIN_EXTRA,
                    HORIZONTAL_OFF_SET_EXTRA,
                    VERTICAL_OFF_SET_EXTRA_UP + i * 22,
                    DEPTH_OFF_SET_EXTRA)) {
                    enableExtraModule = false;
                    break;
                }
                if (!checkPiece(
                    STRUCTURE_PIECE_MAIN_EXTRA,
                    HORIZONTAL_OFF_SET_EXTRA,
                    VERTICAL_OFF_SET_EXTRA_DOWN - i * 22,
                    DEPTH_OFF_SET_EXTRA)) {
                    enableExtraModule = false;
                    break;
                }
            }
        }

        mMachineTier = checkTier;
        return tCountCasing > 1;
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

        for (int i = 0; i < stackSize.stackSize; i++) {

            this.buildPiece(
                STRUCTURE_PIECE_MAIN_UP,
                stackSize,
                hintsOnly,
                HORIZONTAL_OFF_SET_UP,
                VERTICAL_OFF_SET_UP + i * 22,
                DEPTH_OFF_SET_UP);

            this.buildPiece(
                STRUCTURE_PIECE_MAIN_DOWN,
                stackSize,
                hintsOnly,
                HORIZONTAL_OFF_SET_DOWN,
                VERTICAL_OFF_SET_DOWN - i * 22,
                DEPTH_OFF_SET_DOWN);

            if (stackSize.stackSize > 1) {
                this.buildPiece(
                    STRUCTURE_PIECE_MAIN_EXTRA,
                    stackSize,
                    hintsOnly,
                    HORIZONTAL_OFF_SET_EXTRA,
                    VERTICAL_OFF_SET_EXTRA_UP + i * 22,
                    DEPTH_OFF_SET_EXTRA);

                this.buildPiece(
                    STRUCTURE_PIECE_MAIN_EXTRA,
                    stackSize,
                    hintsOnly,
                    HORIZONTAL_OFF_SET_EXTRA,
                    VERTICAL_OFF_SET_EXTRA_DOWN - i * 22,
                    DEPTH_OFF_SET_EXTRA);
            }
        }

        this.buildPiece(
            STRUCTURE_PIECE_MAIN_TOP,
            stackSize,
            hintsOnly,
            HORIZONTAL_OFF_SET_TOP,
            VERTICAL_OFF_SET_TOP + (stackSize.stackSize - 1) * 22,
            DEPTH_OFF_SET_TOP);

        this.buildPiece(
            STRUCTURE_PIECE_MAIN_BOTTOM,
            stackSize,
            hintsOnly,
            HORIZONTAL_OFF_SET_BOTTOM,
            VERTICAL_OFF_SET_BOTTOM - (stackSize.stackSize - 1) * 22,
            DEPTH_OFF_SET_BOTTOM);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (this.mMachine) return -1;

        int built = 0;

        built = this.survivialBuildPiece(
            STRUCTURE_PIECE_MAIN,
            stackSize,
            HORIZONTAL_OFF_SET,
            VERTICAL_OFF_SET,
            DEPTH_OFF_SET,
            elementBudget,
            env,
            false,
            true);

        if (built >= 0) return built;

        for (int i = 0; i < stackSize.stackSize; i++) {
            built = this.survivialBuildPiece(
                STRUCTURE_PIECE_MAIN_UP,
                stackSize,
                HORIZONTAL_OFF_SET_UP,
                VERTICAL_OFF_SET_UP + i * 22,
                DEPTH_OFF_SET_UP,
                elementBudget,
                env,
                false,
                true);

            if (built >= 0) return built;

            built = this.survivialBuildPiece(
                STRUCTURE_PIECE_MAIN_DOWN,
                stackSize,
                HORIZONTAL_OFF_SET_DOWN,
                VERTICAL_OFF_SET_DOWN - i * 22,
                DEPTH_OFF_SET_DOWN,
                elementBudget,
                env,
                false,
                true);

            if (built >= 0) return built;

            if (stackSize.stackSize > 1) {
                built = this.survivialBuildPiece(
                    STRUCTURE_PIECE_MAIN_EXTRA,
                    stackSize,
                    HORIZONTAL_OFF_SET_EXTRA,
                    VERTICAL_OFF_SET_EXTRA_UP + i * 22,
                    DEPTH_OFF_SET_EXTRA,
                    elementBudget,
                    env,
                    false,
                    true);

                if (built >= 0) return built;

                built = this.survivialBuildPiece(
                    STRUCTURE_PIECE_MAIN_EXTRA,
                    stackSize,
                    HORIZONTAL_OFF_SET_EXTRA,
                    VERTICAL_OFF_SET_EXTRA_DOWN - i * 22,
                    DEPTH_OFF_SET_EXTRA,
                    elementBudget,
                    env,
                    false,
                    true);

                if (built >= 0) return built;
            }
        }

        built = this.survivialBuildPiece(
            STRUCTURE_PIECE_MAIN_TOP,
            stackSize,
            HORIZONTAL_OFF_SET_TOP,
            VERTICAL_OFF_SET_TOP + (stackSize.stackSize - 1) * 22,
            DEPTH_OFF_SET_TOP,
            elementBudget,
            env,
            false,
            true);

        if (built >= 0) return built;

        built = this.survivialBuildPiece(
            STRUCTURE_PIECE_MAIN_BOTTOM,
            stackSize,
            HORIZONTAL_OFF_SET_BOTTOM,
            VERTICAL_OFF_SET_BOTTOM - (stackSize.stackSize - 1) * 22,
            DEPTH_OFF_SET_BOTTOM,
            elementBudget,
            env,
            false,
            true);

        return built;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (aBaseMetaTileEntity.isServerSide() && getBaseMetaTileEntity().isAllowedToWork()) {
            if (!moduleHatches.isEmpty() && moduleHatches.size() <= mMachineTier * 4) {
                for (EternalGregTechWorkshopModule module : moduleHatches) {
                    if (allowModuleConnection(module, this)) {
                        module.connect();
                        module.setEUtDiscount(getEUtDiscount());
                        module.setSpeedBoost(getSpeedBoost());
                        module.setMaxParallel(getMaxParallelRecipes());
                    } else {
                        module.disconnect();
                        module.setEUtDiscount(1);
                        module.setSpeedBoost(1);
                        module.setMaxParallel(0);
                    }
                }
            } else if (moduleHatches.size() > mMachineTier * 4) {
                for (EternalGregTechWorkshopModule module : moduleHatches) {
                    module.disconnect();
                }
            }
            if (mEfficiency < 0) mEfficiency = 0;

        }
        super.onPostTick(aBaseMetaTileEntity, aTick);
    }

    @Override
    public void onRemoval() {
        if (moduleHatches != null && !moduleHatches.isEmpty()) {
            for (EternalGregTechWorkshopModule module : moduleHatches) {
                module.disconnect();
            }
        }
        super.onRemoval();
    }

    @Override
    public @NotNull CheckRecipeResult checkProcessing() {
        if (getBaseMetaTileEntity().isAllowedToWork()) {
            mEfficiencyIncrease = 10000;
            mMaxProgresstime = 20;
            return CheckRecipeResultRegistry.SUCCESSFUL;
        }

        mEfficiencyIncrease = 0;
        mMaxProgresstime = 0;
        return CheckRecipeResultRegistry.NO_RECIPE;
    }

    public static boolean allowModuleConnection(EternalGregTechWorkshopModule module, EternalGregTechWorkshop center) {

        if (module instanceof EternalGregTechWorkshopModule) {
            return true;
        }

        return false;
    }

    public boolean addModuleToMachineList(IGregTechTileEntity tileEntity, int baseCasingIndex) {
        if (tileEntity == null) {
            return false;
        }
        IMetaTileEntity metaTileEntity = tileEntity.getMetaTileEntity();
        if (metaTileEntity == null) {
            return false;
        }
        if (metaTileEntity instanceof EternalGregTechWorkshopModule module) {
            return moduleHatches.add(module);
        }
        return false;
    }

    public enum moduleElement implements IHatchElement<EternalGregTechWorkshop> {

        Module(EternalGregTechWorkshop::addModuleToMachineList, EternalGregTechWorkshopModule.class) {

            @Override
            public long count(EternalGregTechWorkshop tileEntity) {
                return tileEntity.moduleHatches.size();
            }
        };

        private final List<Class<? extends IMetaTileEntity>> mteClasses;
        private final IGTHatchAdder<EternalGregTechWorkshop> adder;

        @SafeVarargs
        moduleElement(IGTHatchAdder<EternalGregTechWorkshop> adder, Class<? extends IMetaTileEntity>... mteClasses) {
            this.mteClasses = Collections.unmodifiableList(Arrays.asList(mteClasses));
            this.adder = adder;
        }

        @Override
        public List<? extends Class<? extends IMetaTileEntity>> mteClasses() {
            return mteClasses;
        }

        public IGTHatchAdder<? super EternalGregTechWorkshop> adder() {
            return adder;
        }
    }

    public HeatingCoilLevel getCoilLevel() {
        return this.heatLevel;
    }

    public void setCoilLevel(HeatingCoilLevel level) {
        this.heatLevel = level;
    }

    public double getEUtDiscount() {
        return setEUtDiscount;
    }

    public double getSpeedBoost() {
        return setSpeedBoost;
    }

    @Override
    public void checkMaintenance() {}

    @Override
    public boolean getDefaultHasMaintenanceChecks() {
        return false;
    }

    @Override
    public boolean shouldCheckMaintenance() {
        return false;
    }

    @Override
    public boolean supportsBatchMode() {
        return false;
    }

    @Override
    public boolean getDefaultInputSeparationMode() {
        return false;
    }

    @Override
    public boolean supportsInputSeparation() {
        return false;
    }

    @Override
    public boolean supportsVoidProtection() {
        return false;
    }

    @Override
    public boolean supportsSingleRecipeLocking() {
        return false;
    }

    @Override
    public boolean isRecipeLockingEnabled() {
        return false;
    }

    @Override
    public boolean isInputSeparationEnabled() {
        return false;
    }
}
