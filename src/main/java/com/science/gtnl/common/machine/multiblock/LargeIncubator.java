package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.enums.HatchElement.ExoticEnergy;
import static gregtech.api.enums.Textures.BlockIcons.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTStructureUtility.ofHatchAdder;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.machine.multiMachineClasses.MultiMachineBase;

import bartworks.API.BorosilicateGlass;
import bartworks.API.recipe.BartWorksRecipeMaps;
import bartworks.common.tileentities.tiered.GT_MetaTileEntity_RadioHatch;
import bartworks.util.BWUtil;
import bartworks.util.BioCulture;
import bartworks.util.ResultWrongSievert;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.implementations.MTEHatchInput;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import gregtech.api.util.ParallelHelper;
import gtnhlanth.common.register.LanthItemList;

public class LargeIncubator extends MultiMachineBase<LargeIncubator> implements ISurvivalConstructable {

    private int itemQuantity;
    private final ArrayList<GT_MetaTileEntity_RadioHatch> mRadHatches = new ArrayList<>();
    private int height = 1;
    private Fluid mFluid = FluidRegistry.LAVA;
    private BioCulture mCulture;
    private static final int CASING_INDEX = 210;
    private byte glassTier;
    private int mSievert;
    private int mNeededSievert;
    private int mCasing = 0;
    private int mExpectedMultiplier = 0;
    private int mTimes = 0;
    private boolean isVisibleFluid = false;
    public static IStructureDefinition<LargeIncubator> STRUCTURE_DEFINITION = null;
    public static final String STRUCTURE_PIECE_MAIN = "main";
    public static final String L_INCUBATOR_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/large_incubator";
    public static String[][] shape = StructureUtils.readStructureFromFile(L_INCUBATOR_STRUCTURE_FILE_PATH);
    public final int horizontalOffSet = 6;
    public final int verticalOffSet = 7;
    public final int depthOffSet = 0;

    public LargeIncubator(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public LargeIncubator(String aName) {
        super(aName);
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
    protected MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.LargeIncubatorRecipeType)
            .addInfo(TextLocalization.Tooltip_LargeIncubator_00)
            .addInfo(TextLocalization.Tooltip_LargeIncubator_01)
            .addInfo(TextLocalization.Tooltip_LargeIncubator_02)
            .addInfo(TextLocalization.Tooltip_LargeIncubator_03)
            .addInfo(TextLocalization.Tooltip_LargeIncubator_04)
            .addInfo(TextLocalization.Tooltip_LargeIncubator_05)
            .addInfo(TextLocalization.Tooltip_Tectech_Hatch)
            .beginStructureBlock(13, 9, 13, false)
            .addMaintenanceHatch(TextLocalization.Tooltip_LargeIncubator_Casing, 1)
            .addOtherStructurePart(
                TextLocalization.Tooltip_LargeIncubator_Radio_Hatch,
                TextLocalization.Tooltip_LargeIncubator_Casing,
                1)
            .addInputBus(TextLocalization.Tooltip_LargeIncubator_Casing, 1)
            .addOutputBus(TextLocalization.Tooltip_LargeIncubator_Casing, 1)
            .addInputHatch(TextLocalization.Tooltip_LargeIncubator_Casing, 1)
            .addOutputHatch(TextLocalization.Tooltip_LargeIncubator_Casing, 1)
            .addEnergyHatch(TextLocalization.Tooltip_LargeIncubator_Casing, 1)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IStructureDefinition<LargeIncubator> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<LargeIncubator>builder()
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
                .addElement('B', ofBlock(LanthItemList.SHIELDED_ACCELERATOR_CASING, 0))
                .addElement('C', ofBlock(sBlockCasings8, 1))
                .addElement('D', ofBlock(sBlockCasings9, 1))
                .addElement(
                    'E',
                    ofChain(
                        ofHatchAdder(LargeIncubator::addRadiationInputToMachineList, CASING_INDEX, 1),
                        buildHatchAdder(LargeIncubator.class)
                            .atLeast(InputBus, OutputBus, InputHatch, OutputHatch, Energy.or(ExoticEnergy))
                            .casingIndex(CASING_INDEX)
                            .dot(1)
                            .build(),
                        onElementPass(e -> e.mCasing++, ofBlock(sBlockReinforced, 2))))
                .addElement('F', ofBlockAnyMeta(Blocks.sponge))
                .addElement('G', ofChain(isAir(), ofBlockAnyMeta(Blocks.flowing_water), ofBlockAnyMeta(Blocks.water)))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    public static int[] specialValueUnpack(int aSpecialValue) {
        int[] ret = new int[4];
        ret[0] = aSpecialValue & 0xF; // = glass tier
        ret[1] = aSpecialValue >>> 4 & 0b11; // = special value
        ret[2] = aSpecialValue >>> 6 & 0b1; // boolean exact svt | 1 = true | 0 = false
        ret[3] = aSpecialValue >>> 7 & Integer.MAX_VALUE; // = sievert
        return ret;
    }

    private int getInputCapacity() {
        return this.mInputHatches.stream()
            .mapToInt(MTEHatchInput::getCapacity)
            .sum();
    }

    @Override
    public int getCapacity() {
        int ret = 0;
        ret += this.getInputCapacity();
        return ret;
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        return super.fill(resource, doFill);
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return BartWorksRecipeMaps.bacterialVatRecipes;
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @NotNull
            @Override
            protected CheckRecipeResult validateRecipe(@NotNull GTRecipe recipe) {
                ItemStack specialItem = (ItemStack) recipe.mSpecialItems;

                if (!BWUtil.areStacksEqualOrNull(specialItem, LargeIncubator.this.getControllerSlot())) {
                    return CheckRecipeResultRegistry.NO_RECIPE;
                }

                int[] conditions = LargeIncubator.specialValueUnpack(recipe.mSpecialValue);
                LargeIncubator.this.mNeededSievert = conditions[3];

                if (LargeIncubator.this.mSievert < LargeIncubator.this.mNeededSievert) {
                    return ResultWrongSievert.insufficientSievert(LargeIncubator.this.mNeededSievert);
                }

                itemQuantity = 1;
                ItemStack controllerSlotItem = LargeIncubator.this.getControllerSlot();
                itemQuantity = controllerSlotItem != null ? controllerSlotItem.stackSize : 1;

                return CheckRecipeResultRegistry.SUCCESSFUL;
            }

            @NotNull
            @Override
            public OverclockCalculator createOverclockCalculator(@NotNull GTRecipe recipe) {
                return super.createOverclockCalculator(recipe).setRecipeEUt(recipe.mEUt)
                    .setAmperage(availableAmperage)
                    .setEUt(availableVoltage)
                    .setDuration(recipe.mDuration)
                    .setAmperageOC(true)
                    .setDurationDecreasePerOC(4)
                    .setEUtIncreasePerOC(4)
                    .setEUtDiscount(0.6)
                    .setSpeedBoost(0.2);
            }

            @NotNull
            @Override
            protected ParallelHelper createParallelHelper(@NotNull GTRecipe recipe) {
                return super.createParallelHelper(recipeWithMultiplier(recipe, inputFluids));
            }
        };
    }

    @Override
    public int getMaxParallelRecipes() {
        return 4 * itemQuantity + 2 * GTUtility.getTier(this.getMaxInputVoltage()) * glassTier;
    }

    protected GTRecipe recipeWithMultiplier(GTRecipe recipe, FluidStack[] fluidInputs) {
        // 检查 recipe 和 fluidInputs 是否为 null
        if (recipe == null || fluidInputs == null) {
            return recipe; // 直接返回原始 recipe
        }

        // 检查 recipe.mFluidInputs 和 recipe.mFluidOutputs 是否为 null 或空数组
        if (recipe.mFluidInputs == null || recipe.mFluidInputs.length == 0
            || recipe.mFluidOutputs == null
            || recipe.mFluidOutputs.length == 0) {
            return recipe; // 直接返回原始 recipe
        }

        // 检查 recipe.mFluidInputs[0] 和 recipe.mFluidOutputs[0] 是否为 null
        if (recipe.mFluidInputs[0] == null || recipe.mFluidOutputs[0] == null) {
            return recipe; // 直接返回原始 recipe
        }

        // 检查 fluidInputs 中是否存在 null
        for (FluidStack fluid : fluidInputs) {
            if (fluid == null) {
                return recipe; // 直接返回原始 recipe
            }
        }

        // 如果所有检查通过，继续处理
        GTRecipe tRecipe = recipe.copy();
        int multiplier = 1001;
        mExpectedMultiplier = multiplier;

        // 计算基于输入流体的最大倍数
        long fluidAmount = 0;
        for (FluidStack fluid : fluidInputs) {
            if (recipe.mFluidInputs[0].isFluidEqual(fluid)) {
                fluidAmount += fluid.amount;
            }
        }

        // 计算倍数
        multiplier = (int) Math.min(multiplier, fluidAmount / recipe.mFluidInputs[0].amount);

        // 确保倍数至少为 1
        multiplier = Math.max(multiplier, 1);
        mTimes = multiplier;

        // 更新流体输入和输出的量
        tRecipe.mFluidInputs[0].amount *= multiplier;
        tRecipe.mFluidOutputs[0].amount *= multiplier;

        return tRecipe;
    }

    @Override
    protected void setupProcessingLogic(ProcessingLogic logic) {
        super.setupProcessingLogic(logic);
        logic.setSpecialSlotItem(this.getControllerSlot());
    }

    private boolean addRadiationInputToMachineList(IGregTechTileEntity aTileEntity, int CasingIndex) {
        if (aTileEntity == null) {
            return false;
        }
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (!(aMetaTileEntity instanceof GT_MetaTileEntity_RadioHatch)) {
            return false;
        } else {
            ((GT_MetaTileEntity_RadioHatch) aMetaTileEntity).updateTexture(CasingIndex);
            return this.mRadHatches.add((GT_MetaTileEntity_RadioHatch) aMetaTileEntity);
        }
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack itemStack) {
        this.mRadHatches.clear();
        this.glassTier = 0;
        this.mCasing = 0;

        if (!this.checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet)) return false;

        if (mCasing < 19 && this.mRadHatches.size() > 1
            && this.mOutputHatches.size() != 1
            && this.mInputHatches.isEmpty()
            && this.mEnergyHatches.isEmpty()) return false;

        boolean isFlipped = this.getFlip()
            .isHorizontallyFlipped();
        StructureUtils.setStringBlockXZ(
            aBaseMetaTileEntity,
            horizontalOffSet,
            verticalOffSet,
            depthOffSet,
            shape,
            isFlipped,
            "G",
            Blocks.water);

        return true;
    }

    private int reCalculateFluidAmmount() {
        return this.getStoredFluids()
            .stream()
            .mapToInt(fluidStack -> fluidStack.amount)
            .sum();
    }

    private void reCalculateHeight() {
        if (this.reCalculateFluidAmmount() > this.getCapacity() / 4 - 1) {
            this.reCalculateFluidAmmount();
            this.getCapacity();
        }
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        this.reCalculateHeight();
        if (this.getBaseMetaTileEntity()
            .isServerSide() && this.mRadHatches.size() == 1) {
            this.mSievert = this.mRadHatches.get(0)
                .getSievert();
            if (this.getBaseMetaTileEntity()
                .isActive() && this.mNeededSievert > this.mSievert) this.mOutputFluids = null;
        }
        if (aBaseMetaTileEntity.isServerSide() && this.mMaxProgresstime <= 0) {
            this.mTimes = 0;
            this.mMaxProgresstime = 0;
        }
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        aNBT.setInteger("mFluidHeight", this.height);
        if (this.mCulture != null && !this.mCulture.getName()
            .isEmpty()) aNBT.setString("mCulture", this.mCulture.getName());
        else if ((this.mCulture == null || this.mCulture.getName()
            .isEmpty()) && !aNBT.getString("mCulture")
                .isEmpty()) {
                    aNBT.removeTag("mCulture");
                }
        if (this.mFluid != null) aNBT.setString("mFluid", this.mFluid.getName());
        aNBT.setInteger("mSievert", this.mSievert);
        aNBT.setInteger("mNeededSievert", this.mNeededSievert);
        aNBT.setBoolean("isVisibleFluid", this.isVisibleFluid);
        super.saveNBTData(aNBT);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        this.height = aNBT.getInteger("mFluidHeight");
        this.mCulture = BioCulture.getBioCulture(aNBT.getString("mCulture"));
        if (!aNBT.getString("mFluid")
            .isEmpty()) this.mFluid = FluidRegistry.getFluid(aNBT.getString("mFluid"));
        this.mSievert = aNBT.getInteger("mSievert");
        this.mNeededSievert = aNBT.getInteger("mNeededSievert");
        super.loadNBTData(aNBT);
        this.isVisibleFluid = aNBT.getBoolean("isVisibleFluid");
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new LargeIncubator(this.mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
        int aColorIndex, boolean aActive, boolean aRedstone) {
        if (side == facing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(CASING_INDEX),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_DISTILLATION_TOWER_ACTIVE)
                    .extFacing()
                    .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_DISTILLATION_TOWER_ACTIVE_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(CASING_INDEX), TextureFactory.builder()
                .addIcon(OVERLAY_FRONT_DISTILLATION_TOWER)
                .extFacing()
                .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_DISTILLATION_TOWER_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(CASING_INDEX) };
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        this.buildPiece(STRUCTURE_PIECE_MAIN, itemStack, b, horizontalOffSet, verticalOffSet, depthOffSet);
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

    @Override
    public String[] getInfoData() {
        final String[] baseInfoData = super.getInfoData();
        final String[] infoData = new String[baseInfoData.length + 2];
        System.arraycopy(baseInfoData, 0, infoData, 0, baseInfoData.length);
        // See https://github.com/GTNewHorizons/GT-New-Horizons-Modpack/issues/11923
        // here we must check the machine is well-formed as otherwise getExpectedMultiplier might error out!
        infoData[infoData.length - 2] = StatCollector.translateToLocal("BW.infoData.BioVat.expectedProduction") + ": "
            + EnumChatFormatting.GREEN
            + (this.mMachine ? (this.mMaxProgresstime <= 0 ? 1001 : this.mExpectedMultiplier) * 100 : -1)
            + EnumChatFormatting.RESET
            + " %";
        infoData[infoData.length - 1] = StatCollector.translateToLocal("BW.infoData.BioVat.production") + ": "
            + EnumChatFormatting.GREEN
            + (this.mMaxProgresstime <= 0 ? 0 : this.mTimes) * 100
            + EnumChatFormatting.RESET
            + " %";
        return infoData;
    }

    @Override
    public boolean onWireCutterRightClick(ForgeDirection side, ForgeDirection wrenchingSide, EntityPlayer aPlayer,
        float aX, float aY, float aZ) {
        if (aPlayer.isSneaking()) {
            batchMode = !batchMode;
            if (batchMode) {
                GTUtility.sendChatToPlayer(aPlayer, StatCollector.translateToLocal("misc.BatchModeTextOn"));
            } else {
                GTUtility.sendChatToPlayer(aPlayer, StatCollector.translateToLocal("misc.BatchModeTextOff"));
            }
            return true;
        }
        return false;
    }
}
