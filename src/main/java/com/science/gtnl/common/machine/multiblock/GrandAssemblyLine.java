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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.common.recipe.RecipeRegister;
import com.science.gtnl.config.MainConfig;

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
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMapBackend;
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
import gregtech.common.tileentities.machines.IDualInputHatch;
import gregtech.common.tileentities.machines.IDualInputInventory;
import tectech.thing.casing.BlockGTCasingsTT;
import tectech.thing.metaTileEntity.hatch.MTEHatchEnergyTunnel;

public class GrandAssemblyLine extends MTEExtendedPowerMultiBlockBase<GrandAssemblyLine>
    implements ISurvivalConstructable {

    protected int ParallelTier;
    private int mCasing;
    private int energyHatchTier;
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
    private boolean isDualInputHatch = false;
    private boolean useSingleAmp = true;

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
            .addInfo(TextLocalization.Tooltip_GrandAssemblyLine_08)
            .addInfo(TextLocalization.Tooltip_GrandAssemblyLine_09)
            .addInfo(TextLocalization.Tooltip_GrandAssemblyLine_10)
            .addInfo(TextLocalization.Tooltip_GrandAssemblyLine_11)
            .addInfo(TextLocalization.Tooltip_GrandAssemblyLine_12)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_02)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_03)
            .addInfo(TextLocalization.Tooltip_Tectech_Hatch)
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
        // 预初始化配方池并删除所有配方
        if (!MainConfig.enableDebugMode) {
            RecipeMapBackend grandAssemblyLineRecipes = RecipeRegister.GrandAssemblyLineRecipes.getBackend();
            List<GTRecipe> recipesToRemove = new ArrayList<>(grandAssemblyLineRecipes.getAllRecipes());
            grandAssemblyLineRecipes.removeRecipes(recipesToRemove);
        }

        // 第一步：初始化参数
        int limit = 20; // limit 取批处理时间 20 tick
        long energyEU = GTValues.VP[energyHatchTier] * (useSingleAmp ? 1 : getMaxInputAmps() / 4); // 能源仓最大输入功率
        int maxParallel = getMaxParallelRecipes(); // 最大并行数

        // 构建输入仓列表
        ArrayList<IDualInputInventory> inputInventories = new ArrayList<>();

        // 如果 isDualInputHatch 为 true，遍历每个槽位
        if (isDualInputHatch) {
            for (IDualInputHatch dualInputHatch : mDualInputHatches) {
                Iterator<? extends IDualInputInventory> inventoryIterator = dualInputHatch.inventories();
                while (inventoryIterator.hasNext()) {
                    IDualInputInventory inventory = inventoryIterator.next();
                    inputInventories.add(inventory); // 将每个槽位加入列表
                }
            }
        } else {
            // 非 DualInputHatch 模式，将常规输入仓/总线包装成 IDualInputInventory
            IDualInputInventory wrappedInventory = new WrappedInventory(getAllStoredInputs(), getStoredFluids());
            inputInventories.add(wrappedInventory);
        }

        // 执行配方处理逻辑
        return processRecipeLogic(inputInventories, energyEU, maxParallel, limit);
    }

    private CheckRecipeResult processRecipeLogic(ArrayList<IDualInputInventory> inputInventories, long energyEU,
        int maxParallel, int limit) {
        long totalNeedEUt = 0; // 累加的总功率
        int totalMaxProgresstime = 0; // 累加的最大时间
        int powerParallel = 0;
        int CircuitOC = -1; // 电路板限制超频次数
        ArrayList<ItemStack> totalOutputs = new ArrayList<>(); // 累加的输出物品

        for (ItemStack item : getAllStoredInputs()) {
            if (item.getItem() == ItemList.Circuit_Integrated.getItem()) {
                CircuitOC = item.getItemDamage();
                break;
            }
        }

        // 遍历每个输入仓
        for (IDualInputInventory inventory : inputInventories) {
            // 获取当前输入仓的物品和流体
            ItemStack[] itemInputs = inventory.getItemInputs();
            FluidStack[] fluidInputs = inventory.getFluidInputs();

            // 如果当前输入仓没有物品或流体，跳过
            if (itemInputs == null || fluidInputs == null || itemInputs.length == 0 || fluidInputs.length == 0) {
                continue;
            }

            // 将当前输入仓的物品和流体转换为列表
            ArrayList<ItemStack> allInputs = new ArrayList<>(Arrays.asList(itemInputs));
            ArrayList<FluidStack> allFluids = new ArrayList<>(Arrays.asList(fluidInputs));

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
                continue; // 当前输入仓没有有效配方，跳到下一个输入仓
            }

            // 第二步：提取输入输出物品和流体，计算超频次数，调整 mEUt 和 mDuration
            List<GTRecipe.RecipeAssemblyLine> overclockedRecipes = new ArrayList<>();

            // 按照 recipe.mEUt 从小到大排序
            validRecipes.sort(Comparator.comparingInt(recipe -> recipe.mEUt));

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
                int overclockCount = 0;
                long energyRatio = energyEU / recipe.mEUt; // EnergyEU 与 recipe.mEUt 的比值
                long threshold = 1; // 初始阈值是 4^0 = 1

                // 计算最大可能的超频次数
                while (energyRatio >= threshold * 4) { // 判断是否可以进行下一次超频
                    overclockCount++;
                    threshold *= 4; // 阈值更新为 4^n
                }

                if (CircuitOC >= 0) {
                    overclockCount = Math.min(overclockCount, CircuitOC);
                }

                // 同时计算 adjustedPower 和 adjustedTime，并确保满足所有约束条件
                long adjustedPower = recipe.mEUt * (long) Math.pow(4, overclockCount);
                int adjustedTime = recipe.mDuration / (int) Math.pow((ParallelTier >= 12) ? 4 : 2, overclockCount);

                // 检查功耗是否超过 int 的最大值或时间是否小于 1
                while ((adjustedPower > Integer.MAX_VALUE || adjustedTime < 1) && overclockCount > 0) {
                    overclockCount--; // 减少超频次数
                    adjustedPower = recipe.mEUt * (long) Math.pow(4, overclockCount); // 重新计算功耗
                    adjustedTime = recipe.mDuration / (int) Math.pow((ParallelTier >= 12) ? 4 : 2, overclockCount); // 重新计算时间
                }

                // 确保时间最小为 1
                adjustedTime = Math.max(1, adjustedTime);

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
                powerParallel = (int) Math.floor(energyEU / recipe.mEUt);
            }

            // 第四步：处理配方并行逻辑
            Map<GTRecipe.RecipeAssemblyLine, Integer> recipeParallelMap = new HashMap<>();
            int remainingMaxParallel = maxParallel; // 剩余的最大并行数

            // 标志变量，用于记录是否有至少一个配方的 recipeParallel 大于 0
            boolean hasValidRecipe = false;

            for (GTRecipe.RecipeAssemblyLine recipe : overclockedRecipes) {
                // 计算物品并行数 (ItemParallel)
                List<Long> itemParallels = new ArrayList<>(); // 存储每个物品的并行数，使用 long
                for (ItemStack input : recipe.mInputs) {
                    long available = getAvailableItemCount(input, allInputs); // 使用自定义方法
                    int required = input.stackSize;
                    long itemParallel = available / required; // 向下取整
                    itemParallels.add(itemParallel);
                }
                long itemParallel = Collections.min(itemParallels); // 取所有物品并行数的最小值

                // 检查 itemParallel 是否超过 int 最大值
                if (itemParallel > Integer.MAX_VALUE) {
                    itemParallel = Integer.MAX_VALUE;
                }

                // 计算流体并行数 (FluidParallel)
                List<Long> fluidParallels = new ArrayList<>(); // 存储每个流体的并行数，使用 long
                for (FluidStack fluid : recipe.mFluidInputs) {
                    long available = getAvailableFluidAmount(fluid, allFluids); // 使用自定义方法
                    int required = fluid.amount;
                    long fluidParallel = available / required; // 向下取整
                    fluidParallels.add(fluidParallel);
                }
                long fluidParallel = Collections.min(fluidParallels); // 取所有流体并行数的最小值

                // 检查 fluidParallel 是否超过 int 最大值
                if (fluidParallel > Integer.MAX_VALUE) {
                    fluidParallel = Integer.MAX_VALUE;
                }

                // 最终结果转换为 int
                int finalItemParallel = (int) itemParallel;
                int finalFluidParallel = (int) fluidParallel;

                // 取较小的并行数作为当前配方的并行数 (RecipeParallel)
                int recipeParallel = Math.min(finalItemParallel, finalFluidParallel);

                // 如果当前配方的并行数为 0，跳过此配方
                if (recipeParallel == 0) {
                    continue;
                }

                // 标记有至少一个有效配方
                hasValidRecipe = true;

                // 使用功率并行数限制 RecipeParallel
                recipeParallel = Math.min(recipeParallel, powerParallel);

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

            // 如果没有任何配方的 recipeParallel 大于 0，则跳过当前输入仓
            if (!hasValidRecipe) {
                continue;
            }

            // 第五步：计算总耗电和时间
            long needEU = 0;
            int needTime = 0;

            for (Map.Entry<GTRecipe.RecipeAssemblyLine, Integer> entry : recipeParallelMap.entrySet()) {
                GTRecipe.RecipeAssemblyLine recipe = entry.getKey();
                long parallel = entry.getValue();
                needEU += (long) recipe.mEUt * recipe.mDuration * parallel;
                needTime += recipe.mDuration;
            }

            // 调整功率和时间以适配能源仓输入功率
            long needEUt = needEU / needTime;

            while (needEU / needTime > energyEU) {
                needEU /= 2;
                needTime *= 2;
            }

            while ((needEU / needTime) * 8 < energyEU) {
                // 检查 needEUt 是否超过 Long.MAX_VALUE
                if (needEUt > Long.MAX_VALUE / 4) {
                    needEUt = Long.MAX_VALUE;
                    break; // 如果超过上限，则设置为最大值并退出循环
                }

                // 检查 needTime 是否会低于 1
                if (needTime / 2 < 1) {
                    break; // 如果时间会低于 1，则退出循环
                }

                // 更新 needEUt 和 needTime
                needEUt *= 4;
                needTime /= (ParallelTier >= 12) ? 4 : 2;
            }

            // 累加总功率和最大时间
            totalNeedEUt = needEUt;
            totalMaxProgresstime = needTime;

            // 第六步：生成输出物品并累加到总输出列表
            for (Map.Entry<GTRecipe.RecipeAssemblyLine, Integer> entry : recipeParallelMap.entrySet()) {
                GTRecipe.RecipeAssemblyLine recipe = entry.getKey();
                int parallel = entry.getValue();

                // 生成输出物品
                ItemStack output = recipe.mOutput.copy();
                output.stackSize *= parallel;

                // 将输出物品添加到总输出列表
                if (output.stackSize > 0) {
                    totalOutputs.add(output);
                }
            }

            // 检查总输出是否溢出
            if (!totalOutputs.isEmpty() && !canOutputAll(totalOutputs.toArray(new ItemStack[0]))) {
                // 如果溢出，返回
                return CheckRecipeResultRegistry.ITEM_OUTPUT_FULL;
            }

            // 第七步：消耗输入的物品和流体
            for (Map.Entry<GTRecipe.RecipeAssemblyLine, Integer> entry : recipeParallelMap.entrySet()) {
                GTRecipe.RecipeAssemblyLine recipe = entry.getKey();
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
        }

        // 如果没有有效的配方，返回 NO_RECIPE
        if (totalOutputs.isEmpty()) {
            return CheckRecipeResultRegistry.NO_RECIPE;
        }

        // 第八步：设置总输出物品
        mOutputItems = totalOutputs.toArray(new ItemStack[0]);

        // 第九步：更新槽位
        updateSlots();

        // 设置总功率和最大时间
        this.lEUt = -totalNeedEUt; // 使用累加的总功率
        this.mMaxProgresstime = totalMaxProgresstime; // 使用累加的最大时间

        // 更新效率
        this.mEfficiency = 10000;
        this.mEfficiencyIncrease = 10000;

        if (GTValues.D1) {
            GT_FML_LOGGER.info("Recipe successful");
        }
        return CheckRecipeResultRegistry.SUCCESSFUL;
    }

    // 包装常规输入仓/总线的实现
    private static class WrappedInventory implements IDualInputInventory {

        private final ArrayList<ItemStack> itemInputs;
        private final ArrayList<FluidStack> fluidInputs;

        public WrappedInventory(ArrayList<ItemStack> itemInputs, ArrayList<FluidStack> fluidInputs) {
            this.itemInputs = itemInputs;
            this.fluidInputs = fluidInputs;
        }

        @Override
        public ItemStack[] getItemInputs() {
            return itemInputs.toArray(new ItemStack[0]);
        }

        @Override
        public FluidStack[] getFluidInputs() {
            return fluidInputs.toArray(new FluidStack[0]);
        }
    }

    // 自定义方法：获取可用物品数量
    private long getAvailableItemCount(ItemStack required, ArrayList<ItemStack> allInputs) {
        long count = 0;
        Map<GTUtility.ItemId, ItemStack> inputsFromME = new HashMap<>();

        // 优先检查完全匹配的物品
        for (ItemStack input : allInputs) {
            if (input != null && input.isItemEqual(required) && ItemStack.areItemStackTagsEqual(input, required)) {
                // 如果是来自 ME 总线的物品，使用 GTUtility.ItemId 去重
                if (isFromMEBus(input)) {
                    GTUtility.ItemId itemId = GTUtility.ItemId.createNoCopy(input);
                    if (!inputsFromME.containsKey(itemId)) {
                        inputsFromME.put(itemId, input);
                        count += input.stackSize;
                    }
                } else {
                    // 非 ME 总线的物品直接累加
                    count += input.stackSize;
                }
            }
        }

        // 如果没有完全匹配的物品，尝试检查矿辞匹配的物品
        if (count == 0) {
            // 获取矿辞名称
            int[] oreIDs = OreDictionary.getOreIDs(required);
            if (oreIDs.length > 0) {
                // 遍历所有矿辞匹配的物品
                for (int oreID : oreIDs) {
                    String oreName = OreDictionary.getOreName(oreID);
                    List<ItemStack> oreDictItems = OreDictionary.getOres(oreName);

                    // 遍历输入槽位中的所有物品
                    for (ItemStack input : allInputs) {
                        if (input != null) {
                            // 检查输入物品是否与矿辞匹配
                            for (ItemStack oreDictItem : oreDictItems) {
                                if (OreDictionary.itemMatches(oreDictItem, input, false)) {
                                    // 如果是来自 ME 总线的物品，使用 GTUtility.ItemId 去重
                                    if (isFromMEBus(input)) {
                                        GTUtility.ItemId itemId = GTUtility.ItemId.createNoCopy(input);
                                        if (!inputsFromME.containsKey(itemId)) {
                                            inputsFromME.put(itemId, input);
                                            count += input.stackSize;
                                        }
                                    } else {
                                        // 非 ME 总线的物品直接累加
                                        count += input.stackSize;
                                    }
                                    break; // 匹配成功后跳出矿辞匹配循环
                                }
                            }
                        }
                    }
                }
            }
        }

        return count;
    }

    // 自定义方法：获取可用流体数量
    private long getAvailableFluidAmount(FluidStack required, ArrayList<FluidStack> allFluids) {
        long amount = 0;
        Map<Fluid, FluidStack> inputsFromME = new HashMap<>();

        for (FluidStack fluid : allFluids) {
            if (fluid != null && fluid.isFluidEqual(required)) {
                // 如果是来自 ME 总线的流体，使用 Fluid 去重
                if (isFromMEHatch(fluid)) {
                    if (!inputsFromME.containsKey(fluid.getFluid())) {
                        inputsFromME.put(fluid.getFluid(), fluid);
                        amount += fluid.amount;
                    }
                } else {
                    // 非 ME 总线的流体直接累加
                    amount += fluid.amount;
                }
            }
        }

        return amount;
    }

    // 判断物品是否来自 ME 总线
    private boolean isFromMEBus(ItemStack itemStack) {
        // 根据实际逻辑判断是否为来自 ME 总线的物品
        // 例如，检查物品的 UnlocalizedName 或其他标识
        return itemStack.getUnlocalizedName()
            .startsWith("gt.me_bus_item");
    }

    // 判断流体是否来自 ME 总线
    private boolean isFromMEHatch(FluidStack fluidStack) {
        // 根据实际逻辑判断是否为来自 ME 总线的流体
        // 例如，检查流体的 UnlocalizedName 或其他标识
        return fluidStack.getUnlocalizedName()
            .startsWith("gt.me_hatch_fluid");
    }

    // 自定义方法：消耗物品
    private void consumeItems(ItemStack required, int amount, ArrayList<ItemStack> allInputs) {
        int remaining = amount;

        // 优先消耗完全匹配的物品
        for (ItemStack input : allInputs) {
            if (input != null && input.isItemEqual(required) && ItemStack.areItemStackTagsEqual(input, required)) {
                int toConsume = Math.min(input.stackSize, remaining);
                input.stackSize -= toConsume;
                remaining -= toConsume;
                if (remaining <= 0) {
                    return; // 消耗完毕，直接返回
                }
            }
        }

        // 如果没有完全匹配的物品，尝试消耗矿辞匹配的物品
        if (remaining > 0) {
            // 获取矿辞名称
            int[] oreIDs = OreDictionary.getOreIDs(required);
            if (oreIDs.length > 0) {
                // 遍历所有矿辞匹配的物品
                for (int oreID : oreIDs) {
                    String oreName = OreDictionary.getOreName(oreID);
                    List<ItemStack> oreDictItems = OreDictionary.getOres(oreName);

                    // 遍历所有相同矿辞的物品
                    for (ItemStack oreDictItem : oreDictItems) {
                        // 在输入槽位中查找匹配的物品
                        for (ItemStack input : allInputs) {
                            if (input != null && OreDictionary.itemMatches(oreDictItem, input, false)) {
                                int toConsume = Math.min(input.stackSize, remaining);
                                input.stackSize -= toConsume;
                                remaining -= toConsume;
                                if (remaining <= 0) {
                                    return; // 消耗完毕，直接返回
                                }
                                break; // 消耗完当前物品后，跳出内层循环，继续查找下一个矿辞匹配的物品
                            }
                        }
                    }
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
        isDualInputHatch = false;
        useSingleAmp = true;

        if (!this.checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet)) return false;
        useSingleAmp = mEnergyHatches.size() == 1 && mExoticEnergyHatches.isEmpty();
        energyHatchTier = checkEnergyHatchTier();
        ParallelTier = getParallelTier(aStack);

        if (ParallelTier < 12) {
            for (MTEHatch hatch : getExoticEnergyHatches()) {
                if (hatch instanceof MTEHatchEnergyTunnel) {
                    return false;
                }
            }
            if (mEnergyHatches.size() > 1) return false;
        }

        if (!mDualInputHatches.isEmpty()) {
            isDualInputHatch = true;
            if (!mInputBusses.isEmpty() || !mInputHatches.isEmpty()) return false;
        }

        return mDataAccessHatches.size() <= 1 && mMaintenanceHatches.size() <= 1 && mCasing >= 590;
    }

    @Override
    protected void setProcessingLogicPower(ProcessingLogic logic) {
        logic.setAvailableVoltage(getMachineVoltageLimit());
        logic.setAvailableAmperage(useSingleAmp ? 2 : getMaxInputAmps() / 4);
        logic.setAmperageOC(false);
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
                    .setEUtDiscount(0.8 - (ParallelTier / 50.0) * ((ParallelTier >= 13) ? 0.2 : 1))
                    .setSpeedBoost((0.6 - (ParallelTier / 200.0)) * ((ParallelTier >= 13) ? 0.05 : 1));
            }
        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }
}
