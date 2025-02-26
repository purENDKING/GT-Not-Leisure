package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.GTMod.GT_FML_LOGGER;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.enums.Mods.IndustrialCraft2;
import static gregtech.api.util.GTStructureUtility.*;
import static gregtech.api.util.GTUtility.validMTEList;
import static tectech.thing.casing.TTCasingsContainer.sBlockCasingsTT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.common.recipe.RecipeRegister;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.enums.VoidingMode;
import gregtech.api.interfaces.IHatchElement;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.implementations.MTEExtendedPowerMultiBlockBase;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.metatileentity.implementations.MTEHatchDataAccess;
import gregtech.api.metatileentity.implementations.MTEHatchEnergy;
import gregtech.api.metatileentity.implementations.MTEHatchOutputBus;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.AssemblyLineUtils;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.IGTHatchAdder;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import tectech.thing.casing.BlockGTCasingsTT;
import tectech.thing.metaTileEntity.hatch.MTEHatchEnergyTunnel;

public class GrandAssemblyLine extends MTEExtendedPowerMultiBlockBase<GrandAssemblyLine>
    implements ISurvivalConstructable {

    protected int ParallelTier;
    private int mCasing;
    private static IStructureDefinition<GrandAssemblyLine> STRUCTURE_DEFINITION = null;
    public static final String STRUCTURE_PIECE_MAIN = "main";
    public static final String GAL_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/grand_assembly_line";
    public static String[][] shape = StructureUtils.readStructureFromFile(GAL_STRUCTURE_FILE_PATH);
    public final int horizontalOffSet = 46;
    public final int verticalOffSet = 2;
    public final int depthOffSet = 0;
    public ArrayList<MTEHatchDataAccess> mDataAccessHatches = new ArrayList<>();
    public static final int CASING_INDEX = BlockGTCasingsTT.textureOffset + 3;
    private static Textures.BlockIcons.CustomIcon ScreenOFF;
    private static Textures.BlockIcons.CustomIcon ScreenON;

    public GrandAssemblyLine(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GrandAssemblyLine(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GrandAssemblyLine(this.mName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        final MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.GrandAssemblyLineRecipeType)
            .addInfo(TextLocalization.Tooltip_GrandAssemblyLine_00)
            .addInfo(TextLocalization.Tooltip_GrandAssemblyLine_01)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_00)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_01)
            .addInfo(TextLocalization.Tooltip_GrandAssemblyLine_02)
            .addInfo(TextLocalization.Tooltip_GrandAssemblyLine_03)
            .addInfo(TextLocalization.Tooltip_GrandAssemblyLine_04)
            .addInfo(TextLocalization.Tooltip_GrandAssemblyLine_05)
            .addInfo(TextLocalization.Tooltip_GrandAssemblyLine_06)
            .addInfo(TextLocalization.Tooltip_GrandAssemblyLine_07)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_02)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_03)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_04)
            .beginStructureBlock(48, 5, 5, true)
            .addEnergyHatch(TextLocalization.Tooltip_GrandAssemblyLine_Casing, 1)
            .addMaintenanceHatch(TextLocalization.Tooltip_GrandAssemblyLine_Casing, 1)
            .addInputBus(TextLocalization.Tooltip_GrandAssemblyLine_Casing, 1)
            .addInputHatch(TextLocalization.Tooltip_GrandAssemblyLine_Casing, 1)
            .addOutputBus(TextLocalization.Tooltip_GrandAssemblyLine_Casing, 1)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity baseMetaTileEntity, ForgeDirection sideDirection,
        ForgeDirection facingDirection, int colorIndex, boolean active, boolean redstoneLevel) {
        if (sideDirection == facingDirection) {
            if (active) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(ScreenON)
                    .extFacing()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(ScreenOFF)
                    .extFacing()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister aBlockIconRegister) {
        ScreenOFF = new Textures.BlockIcons.CustomIcon("iconsets/EM_COMPUTER");
        ScreenON = new Textures.BlockIcons.CustomIcon("iconsets/EM_COMPUTER_ACTIVE");
        super.registerIcons(aBlockIconRegister);
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeMaps.assemblylineVisualRecipes;
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    @Override
    @NotNull
    public CheckRecipeResult checkProcessing() {
        // 第一步：初始化参数
        boolean isPollingMode = false; // 轮询模式，默认关闭
        int limit = 128; // limit 取批处理时间 128 tick
        long energyEU = getMaxInputEnergy(); // 能源仓最大输入功率
        int maxParallel = getMaxParallelRecipes(); // 最大并行数

        // 获取所有输入物品和流体
        ArrayList<ItemStack> allInputs = getStoredInputs();
        ArrayList<FluidStack> allFluids = getStoredFluids();

        // 获取所有有效配方
        List<GTRecipe.RecipeAssemblyLine> validRecipes = new ArrayList<>();
        for (ItemStack tDataStick : getDataItems(2)) {
            AssemblyLineUtils.LookupResult tLookupResult = AssemblyLineUtils
                .findAssemblyLineRecipeFromDataStick(tDataStick, false);

            if (tLookupResult.getType() == AssemblyLineUtils.LookupResultType.VALID_STACK_AND_VALID_HASH) {
                GTRecipe.RecipeAssemblyLine tRecipe = tLookupResult.getRecipe();
                if (tRecipe != null) {
                    validRecipes.add(tRecipe);
                }
            }
        }

        if (validRecipes.isEmpty()) {
            return CheckRecipeResultRegistry.NO_DATA_STICKS;
        }

        // 第二步：提取输入输出物品和流体，计算超频次数，调整 mEUt 和 mDuration
        List<GTRecipe.RecipeAssemblyLine> overclockedRecipes = new ArrayList<>();
        for (GTRecipe.RecipeAssemblyLine recipe : validRecipes) {
            // 提取输入输出物品和流体
            ItemStack[] inputItems = Arrays.stream(recipe.mInputs)
                .map(ItemStack::copy)
                .toArray(ItemStack[]::new);
            FluidStack[] inputFluids = Arrays.stream(recipe.mFluidInputs)
                .map(FluidStack::copy)
                .toArray(FluidStack[]::new);
            ItemStack outputItem = recipe.mOutput.copy();

            // 计算超频次数
            long overclockCount = energyEU / (recipe.mEUt * 4L); // 超频次数
            long adjustedPower = recipe.mEUt * (long) Math.pow(4, overclockCount); // 调整后的功率
            int adjustedTime = recipe.mDuration / (int) Math.pow(2, overclockCount); // 调整后的时间

            // 构建超频后的临时配方
            GTRecipe.RecipeAssemblyLine overclockedRecipe = new GTRecipe.RecipeAssemblyLine(
                recipe.mResearchItem, // 研究物品
                recipe.mResearchTime, // 研究时间
                inputItems, // 输入物品
                inputFluids, // 输入流体
                outputItem, // 输出物品
                adjustedTime, // 调整后的时间
                (int) Math.min(adjustedPower, Integer.MAX_VALUE), // 调整后的功率
                recipe.mOreDictAlt // 替代物品
            );

            overclockedRecipes.add(overclockedRecipe);
        }

        // 第三步：计算总耗电和时间
        for (GTRecipe.RecipeAssemblyLine recipe : overclockedRecipes) {
            // 计算总耗电
            long totalEnergy = (long) recipe.mEUt * recipe.mDuration;

            // 计算 d 变量时间
            double d = (double) totalEnergy / energyEU;

            // 根据 d 和 limit 调整功率和时间
            int adjustedPower;
            int adjustedTime;

            if (d >= limit) {
                adjustedPower = (int) energyEU; // 直接取最大输入功率
                adjustedTime = (int) Math.max(limit, d); // 取 limit 和 d 的最大值
            } else {
                adjustedPower = (int) ((energyEU * d) / limit); // 按比例调整功率
                adjustedTime = limit; // 取 limit
            }

            // 更新配方的功率和时间
            recipe.mEUt = adjustedPower;
            recipe.mDuration = adjustedTime;
        }

        for (GTRecipe.RecipeAssemblyLine recipe : overclockedRecipes) {
            // 复制输入和输出的物品和流体
            ItemStack[] inputItems = Arrays.stream(recipe.mInputs)
                .map(ItemStack::copy)
                .toArray(ItemStack[]::new);
            ItemStack[] outputItems = new ItemStack[] { recipe.mOutput.copy() };
            FluidStack[] inputFluids = Arrays.stream(recipe.mFluidInputs)
                .map(FluidStack::copy)
                .toArray(FluidStack[]::new);
            FluidStack[] outputFluids = new FluidStack[0];

            // 构建临时配方并添加到临时配方池
            GTRecipe tempRecipe = GTValues.RA.stdBuilder()
                .itemInputs(inputItems)
                .itemOutputs(outputItems)
                .fluidInputs(inputFluids)
                .fluidOutputs(outputFluids)
                .duration(recipe.mDuration)
                .eut(recipe.mEUt)
                .build()
                .orElse(null);

            if (tempRecipe != null) {
                RecipeRegister.GrandAssemblyLineRecipes.add(tempRecipe);
            }
        }

        // 过滤掉需求功率大于能源仓最大功率的临时配方
        List<GTRecipe> filteredTempRecipes = RecipeRegister.GrandAssemblyLineRecipes.getAllRecipes()
            .stream()
            .filter(recipe -> recipe.mEUt <= energyEU)
            .collect(Collectors.toList());

        if (filteredTempRecipes.isEmpty()) {
            return CheckRecipeResultRegistry.insufficientPower(energyEU);
        }

        // 第五步：根据轮询模式处理配方所用并行检查逻辑
        Map<GTRecipe, Integer> recipeParallelMap = new HashMap<>();
        int remainingMaxParallel = maxParallel; // 剩余的最大并行数

        for (GTRecipe recipe : filteredTempRecipes) {
            // 计算物品并行数 (ItemParallel)
            int itemParallel = Integer.MAX_VALUE;
            for (ItemStack input : recipe.mInputs) {
                int available = getAvailableItemCount(input, allInputs); // 使用自定义方法
                int required = input.stackSize;
                itemParallel = Math.min(itemParallel, available / required);
            }

            // 计算流体并行数 (FluidParallel)
            int fluidParallel = Integer.MAX_VALUE;
            for (FluidStack fluid : recipe.mFluidInputs) {
                int available = getAvailableFluidAmount(fluid, allFluids); // 使用自定义方法
                int required = fluid.amount;
                fluidParallel = Math.min(fluidParallel, available / required);
            }

            // 取较小的并行数作为当前配方的并行数 (RecipeParallel)
            int recipeParallel = Math.min(itemParallel, fluidParallel);

            if (isPollingMode) {
                // 轮询模式下，RecipeParallel 最大为 1
                recipeParallel = Math.min(recipeParallel, 1);
            }

            if (recipeParallel <= 0) {
                continue; // 如果并行数为 0，跳过此配方
            }

            // 检查剩余的最大并行数
            if (recipeParallel > remainingMaxParallel) {
                recipeParallel = remainingMaxParallel; // 如果超出剩余并行数，则设置为剩余并行数
            }

            // 更新剩余的最大并行数
            remainingMaxParallel -= recipeParallel;

            // 将当前配方的并行数添加到结果中
            recipeParallelMap.put(recipe, recipeParallel);

            if (remainingMaxParallel <= 0) {
                break; // 如果剩余并行数为 0，跳出循环
            }
        }

        if (recipeParallelMap.isEmpty()) {
            return CheckRecipeResultRegistry.NO_RECIPE;
        }

        // 第六步：计算总耗电和时间
        long needEU = 0;
        int needTime = 0;

        for (Map.Entry<GTRecipe, Integer> entry : recipeParallelMap.entrySet()) {
            GTRecipe recipe = entry.getKey();
            int parallel = entry.getValue();
            needEU += (long) recipe.mEUt * recipe.mDuration * parallel;
            needTime += recipe.mDuration;
        }

        // 调整功率和时间以适配能源仓输入功率
        while (needEU / needTime > energyEU) {
            needEU /= 2;
            needTime *= 2;
        }

        while ((needEU / needTime) * 4 <= energyEU) {
            needEU *= 4;
            needTime /= 2;
        }

        // 第七步：生成输出物品并检查输出槽位
        ArrayList<ItemStack> outputs = new ArrayList<>();
        for (Map.Entry<GTRecipe, Integer> entry : recipeParallelMap.entrySet()) {
            GTRecipe recipe = entry.getKey();
            int parallel = entry.getValue();

            ItemStack output = recipe.mOutputs[0].copy();
            output.stackSize *= parallel;
            if (canOutputItem(output) && output.stackSize > 0) {
                outputs.add(output);
            } else {
                return CheckRecipeResultRegistry.ITEM_OUTPUT_FULL;
            }
        }
        mOutputItems = outputs.toArray(new ItemStack[0]);

        // 第八步：消耗输入的物品和流体
        for (Map.Entry<GTRecipe, Integer> entry : recipeParallelMap.entrySet()) {
            GTRecipe recipe = entry.getKey();
            int parallel = entry.getValue();

            // 消耗物品
            for (ItemStack input : recipe.mInputs) {
                consumeItems(input, input.stackSize * parallel, allInputs); // 使用自定义方法
            }

            // 消耗流体
            for (FluidStack fluid : recipe.mFluidInputs) {
                consumeFluids(fluid, fluid.amount * parallel, allFluids); // 使用自定义方法
            }
        }

        // 第九步：更新槽位
        updateSlots();

        // 设置功率和时间
        this.lEUt = -needEU; // 使用调整后的总功率
        this.mMaxProgresstime = needTime; // 使用调整后的时间

        // 更新效率
        this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
        this.mEfficiencyIncrease = 10000;

        if (GTValues.D1) {
            GT_FML_LOGGER.info("Recipe successful");
        }
        return CheckRecipeResultRegistry.SUCCESSFUL;
    }

    // 自定义方法：获取可用物品数量
    private int getAvailableItemCount(ItemStack required, ArrayList<ItemStack> allInputs) {
        int count = 0;
        for (ItemStack input : allInputs) {
            if (input != null && input.isItemEqual(required)) {
                count += input.stackSize;
            }
        }
        return count;
    }

    // 自定义方法：获取可用流体数量
    private int getAvailableFluidAmount(FluidStack required, ArrayList<FluidStack> allFluids) {
        int amount = 0;
        for (FluidStack fluid : allFluids) {
            if (fluid != null && fluid.isFluidEqual(required)) {
                amount += fluid.amount;
            }
        }
        return amount;
    }

    // 自定义方法：消耗物品
    private void consumeItems(ItemStack required, int amount, ArrayList<ItemStack> allInputs) {
        int remaining = amount;
        for (ItemStack input : allInputs) {
            if (input != null && input.isItemEqual(required)) {
                int toConsume = Math.min(input.stackSize, remaining);
                input.stackSize -= toConsume;
                remaining -= toConsume;
                if (remaining <= 0) {
                    break;
                }
            }
        }
    }

    // 自定义方法：消耗流体
    private void consumeFluids(FluidStack required, int amount, ArrayList<FluidStack> allFluids) {
        int remaining = amount;
        for (FluidStack fluid : allFluids) {
            if (fluid != null && fluid.isFluidEqual(required)) {
                int toConsume = Math.min(fluid.amount, remaining);
                fluid.amount -= toConsume;
                remaining -= toConsume;
                if (remaining <= 0) {
                    break;
                }
            }
        }
    }

    private long getMaxInputEnergy() {
        long maxInputEnergy = 0;

        // 遍历所有能源仓
        for (MTEHatchEnergy energyHatch : mEnergyHatches) {
            if (energyHatch != null && energyHatch.isValid()) {
                // 获取单个能源仓的最大输入功率
                maxInputEnergy += energyHatch.maxEUInput();
            }
        }

        return maxInputEnergy;
    }

    // 检查输出槽位是否已满
    private boolean canOutputItem(ItemStack output) {
        if (output == null || output.stackSize <= 0) {
            return false; // 如果物品无效，直接返回 false
        }

        // 检查所有输出总线的槽位
        for (MTEHatchOutputBus outputBus : validMTEList(mOutputBusses)) {
            if (outputBus != null && outputBus.isValid()) {
                IInventory inventory = outputBus.getBaseMetaTileEntity(); // 获取输出总线的物品栏

                // 遍历物品栏的每个槽位
                for (int i = 0; i < inventory.getSizeInventory(); i++) {
                    ItemStack slot = inventory.getStackInSlot(i); // 获取当前槽位的物品

                    // 如果槽位为空或可以堆叠，返回 true
                    if (slot == null
                        || (slot.isItemEqual(output) && slot.stackSize + output.stackSize <= slot.getMaxStackSize())) {
                        return true;
                    }
                }
            }
        }

        return false; // 如果没有找到合适的槽位，返回 false
    }

    @Override
    public boolean onRunningTick(ItemStack aStack) {
        for (MTEHatchDataAccess hatch_dataAccess : mDataAccessHatches) {
            hatch_dataAccess.setActive(true);
        }
        return super.onRunningTick(aStack);
    }

    @Override
    public IStructureDefinition<GrandAssemblyLine> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<GrandAssemblyLine>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', ofBlock(sBlockCasings2, 5))
                .addElement(
                    'B',
                    buildHatchAdder(GrandAssemblyLine.class).casingIndex(CASING_INDEX)
                        .dot(1)
                        .atLeast(InputBus)
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(sBlockCasingsTT, 3))))
                .addElement('C', ofBlock(sBlockCasingsTT, 2))
                .addElement(
                    'D',
                    buildHatchAdder(GrandAssemblyLine.class).casingIndex(CASING_INDEX)
                        .dot(1)
                        .atLeast(OutputBus)
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(sBlockCasingsTT, 3))))
                .addElement(
                    'E',
                    buildHatchAdder(GrandAssemblyLine.class).casingIndex(CASING_INDEX)
                        .dot(1)
                        .atLeast(
                            InputHatch,
                            InputBus,
                            OutputBus,
                            Maintenance,
                            Energy.or(ExoticEnergy),
                            DataHatchElement.DataAccess)
                        .buildAndChain(
                            onElementPass(
                                x -> ++x.mCasing,
                                ofBlockAnyMeta(GameRegistry.findBlock(IndustrialCraft2.ID, "blockAlloyGlass")))))
                .addElement(
                    'F',
                    buildHatchAdder(GrandAssemblyLine.class).casingIndex(CASING_INDEX)
                        .dot(1)
                        .atLeast(
                            InputHatch,
                            InputBus,
                            OutputBus,
                            Maintenance,
                            Energy.or(ExoticEnergy),
                            DataHatchElement.DataAccess)
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(sBlockCasingsTT, 3))))
                .addElement('G', ofBlock(sBlockCasings2, 9))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        mCasing = 0;
        ParallelTier = 0;
        mDataAccessHatches.clear();

        if (!this.checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet)) return false;

        for (MTEHatch hatch : getExoticEnergyHatches()) {
            if (hatch instanceof MTEHatchEnergyTunnel) {
                return false;
            }
        }

        ParallelTier = getParallelTier(aStack);
        return mEnergyHatches.size() <= 1 && mDataAccessHatches.size() <= 1
            && mMaintenanceHatches.size() <= 1
            && mCasing >= 595;
    }

    /**
     * @param state using bitmask, 1 for IntegratedCircuit, 2 for DataStick, 4 for DataOrb
     */
    private static boolean isCorrectDataItem(ItemStack aStack, int state) {
        if ((state & 1) != 0 && ItemList.Circuit_Integrated.isStackEqual(aStack, true, true)) return true;
        if ((state & 2) != 0 && ItemList.Tool_DataStick.isStackEqual(aStack, false, true)) return true;
        return (state & 4) != 0 && ItemList.Tool_DataOrb.isStackEqual(aStack, false, true);
    }

    /**
     * @param state using bitmask, 1 for IntegratedCircuit, 2 for DataStick, 4 for DataOrb
     */
    public ArrayList<ItemStack> getDataItems(int state) {
        ArrayList<ItemStack> rList = new ArrayList<>();
        if (GTUtility.isStackValid(mInventory[1]) && isCorrectDataItem(mInventory[1], state)) {
            rList.add(mInventory[1]);
        }
        for (MTEHatchDataAccess tHatch : validMTEList(mDataAccessHatches)) {
            rList.addAll(tHatch.getInventoryItems(stack -> isCorrectDataItem(stack, state)));
        }
        return rList;
    }

    public boolean addDataAccessToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) return false;
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null) return false;
        if (aMetaTileEntity instanceof MTEHatchDataAccess) {
            ((MTEHatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
            return mDataAccessHatches.add((MTEHatchDataAccess) aMetaTileEntity);
        }
        return false;
    }

    @Override
    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    @Override
    public int getDamageToComponent(ItemStack aStack) {
        return 0;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack aStack) {
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

    public int getCasingTextureID() {
        return CASING_INDEX;
    }

    @Override
    public boolean supportsVoidProtection() {
        return true;
    }

    @Override
    public Set<VoidingMode> getAllowedVoidingModes() {
        return VoidingMode.ITEM_ONLY_MODES;
    }

    @Override
    public boolean supportsBatchMode() {
        return true;
    }

    private enum DataHatchElement implements IHatchElement<GrandAssemblyLine> {

        DataAccess;

        @Override
        public List<? extends Class<? extends IMetaTileEntity>> mteClasses() {
            return Collections.singletonList(MTEHatchDataAccess.class);
        }

        @Override
        public IGTHatchAdder<GrandAssemblyLine> adder() {
            return GrandAssemblyLine::addDataAccessToMachineList;
        }

        @Override
        public long count(GrandAssemblyLine t) {
            return t.mDataAccessHatches.size();
        }
    }

    public int getMaxParallelRecipes() {
        if (ParallelTier <= 1) {
            return 8;
        } else {
            return (int) Math.pow(4, ParallelTier - 2);
        }
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

    @Override
    public ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @NotNull
            @Override
            public OverclockCalculator createOverclockCalculator(@NotNull GTRecipe recipe) {
                return OverclockCalculator.ofNoOverclock(recipe)
                    .setEUtDiscount(0.8 - (ParallelTier / 50.0))
                    .setSpeedBoost(0.6 - (ParallelTier / 200.0));
            }
        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }
}
