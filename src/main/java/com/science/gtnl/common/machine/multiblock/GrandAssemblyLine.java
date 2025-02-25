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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.GTNLItemList;

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
import gregtech.api.metatileentity.implementations.*;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.*;
import gregtech.common.tileentities.machines.MTEHatchInputBusME;
import gregtech.common.tileentities.machines.MTEHatchInputME;
import tectech.thing.casing.BlockGTCasingsTT;
import tectech.thing.metaTileEntity.hatch.MTEHatchEnergyTunnel;

public class GrandAssemblyLine extends MTEExtendedPowerMultiBlockBase<GrandAssemblyLine>
    implements ISurvivalConstructable {

    protected int ParallelTier;
    private int mCasing;
    private static IStructureDefinition<GrandAssemblyLine> STRUCTURE_DEFINITION = null;
    public static final String STRUCTURE_PIECE_MAIN = "main";
    public static String[][] shape;
    public static final String GAL_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/grand_assembly_line";
    public final int horizontalOffSet = 46;
    public final int verticalOffSet = 2;
    public final int depthOffSet = 0;
    public ArrayList<MTEHatchDataAccess> mDataAccessHatches = new ArrayList<>();
    public static final int CASING_INDEX = BlockGTCasingsTT.textureOffset + 3;
    private static Textures.BlockIcons.CustomIcon ScreenOFF;
    private static Textures.BlockIcons.CustomIcon ScreenON;

    public GrandAssemblyLine(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        shape = StructureUtils.readStructureFromFile(GAL_STRUCTURE_FILE_PATH);
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
        if (GTValues.D1) {
            GT_FML_LOGGER.info("Start ALine recipe check");
        }

        // 获取所有数据棒
        ArrayList<ItemStack> tDataStickList = getDataItems(2);
        if (tDataStickList.isEmpty()) {
            return CheckRecipeResultRegistry.NO_DATA_STICKS;
        }

        // 获取所有输入物品和流体
        ArrayList<ItemStack> allInputs = getStoredInputs();
        ArrayList<FluidStack> allFluids = getStoredFluids();

        // 将输入物品和流体转换为 Map 以便快速查找
        Map<GTUtility.ItemId, ItemStack> inputMap = convertItemStackListToMap(allInputs);
        Map<Fluid, FluidStack> fluidMap = convertFluidStackListToMap(allFluids);

        // 存储所有有效的配方
        List<GTRecipe.RecipeAssemblyLine> validRecipes = new ArrayList<>();
        for (ItemStack tDataStick : tDataStickList) {
            AssemblyLineUtils.LookupResult tLookupResult = AssemblyLineUtils
                .findAssemblyLineRecipeFromDataStick(tDataStick, false);

            if (tLookupResult.getType() != AssemblyLineUtils.LookupResultType.VALID_STACK_AND_VALID_HASH) {
                continue; // 跳过无效的数据棒
            }

            GTRecipe.RecipeAssemblyLine tRecipe = tLookupResult.getRecipe();
            if (tRecipe == null) {
                continue; // 跳过无效的配方
            }

            validRecipes.add(tRecipe);
        }

        if (validRecipes.isEmpty()) {
            return CheckRecipeResultRegistry.NO_RECIPE;
        }

        // 获取能源仓的最大功率
        long energyHatchPower = getMaxInputEnergy();

        // 过滤掉功率超过能源仓最大功率的配方
        List<GTRecipe.RecipeAssemblyLine> filteredRecipes = new ArrayList<>();
        for (GTRecipe.RecipeAssemblyLine recipe : validRecipes) {
            if (recipe.mEUt <= energyHatchPower) {
                filteredRecipes.add(recipe);
            }
        }

        // 如果过滤后没有配方，说明所有配方的功率都大于能源仓最大功率
        if (filteredRecipes.isEmpty()) {
            // 返回功率不足的错误，并附上第一个配方的功率
            GTRecipe.RecipeAssemblyLine firstRecipe = validRecipes.get(0);
            return CheckRecipeResultRegistry.insufficientPower(firstRecipe.mEUt);
        }

        // 计算所有配方的总功率
        long totalPower = 0;
        for (GTRecipe.RecipeAssemblyLine recipe : filteredRecipes) {
            totalPower += recipe.mEUt;
        }

        // 初始化调整后的功率和时间
        int adjustedTime = 0;
        for (GTRecipe.RecipeAssemblyLine recipe : filteredRecipes) {
            adjustedTime += recipe.mDuration;
        }

        // 如果总功率大于能源仓最大功率，则调整总功率和时间
        while (totalPower > energyHatchPower) {
            totalPower /= 2; // 总功率减半
            adjustedTime *= 2; // 时间翻倍
        }

        // 如果 adjustedMaxPower 大于 totalPower * 4，则调整 totalPower 和时间
        while (energyHatchPower > totalPower * 4) {
            totalPower *= 4; // 总功率乘以 4
            adjustedTime /= 2; // 时间减半
        }

        // 计算最大并行处理能力
        int maxParallel = getMaxParallelRecipes();

        // 计算每个配方的最大并行数
        int recipeParallel = maxParallel;
        for (GTRecipe.RecipeAssemblyLine recipe : filteredRecipes) {
            // 提取物品及其数量
            Map<ItemStack, Integer> itemRequirements = extractItemRequirements(recipe);
            if (itemRequirements.isEmpty()) {
                continue; // 跳过没有物品需求的配方
            }

            // 检查物品输入
            int itemParallel = (int) maxParallelCalculatedByInputItemsFromMap(
                mInputBusses,
                maxParallel,
                itemRequirements,
                inputMap);
            if (itemParallel <= 0) {
                return CheckRecipeResultRegistry.NO_RECIPE; // 物品不足，返回无配方
            }

            // 检查流体输入
            if (recipe.mFluidInputs.length > 0) {
                int fluidParallel = (int) maxParallelCalculatedByInputFluids(
                    mInputHatches,
                    maxParallel,
                    recipe.mFluidInputs,
                    fluidMap);
                if (fluidParallel <= 0) {
                    return CheckRecipeResultRegistry.NO_RECIPE; // 流体不足，返回无配方
                }

                // 取物品和流体的最小并行数
                recipeParallel = Math.min(recipeParallel, Math.min(itemParallel, fluidParallel));
            } else {
                recipeParallel = Math.min(recipeParallel, itemParallel);
            }
        }

        if (recipeParallel <= 0) {
            return CheckRecipeResultRegistry.NO_RECIPE;
        }

        // 设置最终并行处理能力
        maxParallel = recipeParallel;

        // 一次性消耗物品
        for (GTRecipe.RecipeAssemblyLine recipe : filteredRecipes) {
            // 检查物品
            for (int i = 0; i < recipe.mInputs.length; i++) {
                ItemStack requiredStack = recipe.mInputs[i]; // 配方所需的物品
                ItemStack[] alts = recipe.mOreDictAlt[i]; // 替代品列表
                int totalRequiredAmount = requiredStack.stackSize * maxParallel; // 总需求数量

                // 遍历所有输入总线
                for (MTEHatchInputBus inputBus : mInputBusses) {
                    if (!inputBus.isValid()) {
                        continue; // 跳过无效的输入总线
                    }

                    // 遍历输入总线的所有槽位
                    for (int slot = 0; slot < inputBus.getSizeInventory(); slot++) {
                        ItemStack slotStack;
                        if (inputBus instanceof MTEHatchInputBusME meBus) {
                            slotStack = meBus.getShadowItemStack(slot); // 如果是 ME 总线，获取影子物品
                        } else {
                            slotStack = inputBus.getStackInSlot(slot); // 普通总线，获取槽位物品
                        }

                        if (slotStack == null) {
                            continue; // 跳过空槽位
                        }

                        // 检查物品是否匹配
                        if (isItemStackMatching(slotStack, requiredStack, alts)) {
                            int consumedAmount = Math.min(slotStack.stackSize, totalRequiredAmount); // 本次消耗的数量
                            slotStack.stackSize -= consumedAmount; // 消耗物品
                            totalRequiredAmount -= consumedAmount; // 更新剩余需求数量

                            if (slotStack.stackSize <= 0) {
                                inputBus.setInventorySlotContents(slot, null); // 如果物品被消耗完，设置为 null
                            }

                            if (totalRequiredAmount <= 0) {
                                break; // 如果需求数量已满足，跳出循环
                            }
                        }
                    }

                    if (totalRequiredAmount <= 0) {
                        break; // 如果需求数量已满足，跳出循环
                    }
                }

                if (totalRequiredAmount > 0) {
                    return CheckRecipeResultRegistry.NO_RECIPE; // 如果物品不足，返回错误
                }
            }
        }

        // 一次性消耗流体
        for (GTRecipe.RecipeAssemblyLine recipe : filteredRecipes) {
            // 检查流体
            for (FluidStack requiredFluid : recipe.mFluidInputs) {
                int totalRequiredAmount = requiredFluid.amount * maxParallel; // 总需求数量

                // 遍历所有输入仓
                for (MTEHatchInput inputHatch : mInputHatches) {
                    if (!inputHatch.isValid()) {
                        continue; // 跳过无效的输入仓
                    }

                    // 获取输入仓中的流体
                    FluidStack storedFluid = inputHatch.getFluid(); // 假设 MTEHatchInput 提供了 getFluid() 方法
                    if (storedFluid != null && storedFluid.isFluidEqual(requiredFluid)) {
                        int consumedAmount = Math.min(storedFluid.amount, totalRequiredAmount); // 本次消耗的数量
                        inputHatch.drain(ForgeDirection.UNKNOWN, consumedAmount, true); // 消耗流体
                        totalRequiredAmount -= consumedAmount; // 更新剩余需求数量

                        if (totalRequiredAmount <= 0) {
                            break; // 如果需求数量已满足，跳出循环
                        }
                    }
                }

                if (totalRequiredAmount > 0) {
                    return CheckRecipeResultRegistry.NO_RECIPE; // 如果流体不足，返回错误
                }
            }
        }

        // 生成输出物品
        ArrayList<ItemStack> outputs = new ArrayList<>();
        for (GTRecipe.RecipeAssemblyLine recipe : filteredRecipes) {
            ItemStack output = recipe.mOutput.copy();
            output.stackSize *= maxParallel;
            if (canOutputItem(output) && output.stackSize > 0) {
                outputs.add(output);
            } else {
                return CheckRecipeResultRegistry.ITEM_OUTPUT_FULL;
            }
        }
        mOutputItems = outputs.toArray(new ItemStack[0]);

        // 设置功率和时间
        this.lEUt = -totalPower; // 使用调整后的总功率
        this.mMaxProgresstime = adjustedTime; // 使用调整后的时间

        // 更新效率
        this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
        this.mEfficiencyIncrease = 10000;

        // 更新槽位
        updateSlots();

        if (GTValues.D1) {
            GT_FML_LOGGER.info("Recipe successful");
        }
        return CheckRecipeResultRegistry.SUCCESSFUL;
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

    /**
     * 将 ItemStack 列表转换为 Map<GTUtility.ItemId, ItemStack>
     */
    private Map<GTUtility.ItemId, ItemStack> convertItemStackListToMap(ArrayList<ItemStack> itemStacks) {
        Map<GTUtility.ItemId, ItemStack> map = new HashMap<>();
        for (ItemStack stack : itemStacks) {
            if (stack != null) {
                GTUtility.ItemId itemId = GTUtility.ItemId.createWithoutNBT(stack);
                map.put(itemId, stack);
            }
        }
        return map;
    }

    /**
     * 将 FluidStack 列表转换为 Map<Fluid, FluidStack>
     */
    private Map<Fluid, FluidStack> convertFluidStackListToMap(ArrayList<FluidStack> fluidStacks) {
        Map<Fluid, FluidStack> map = new HashMap<>();
        for (FluidStack stack : fluidStacks) {
            if (stack != null) {
                map.put(stack.getFluid(), stack);
            }
        }
        return map;
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
                return super.createOverclockCalculator(recipe).setAmperageOC(true)
                    .setDurationDecreasePerOC(2)
                    .setEUtIncreasePerOC(4)
                    .setEUtDiscount(0.8 - (ParallelTier / 50.0))
                    .setSpeedBoost(0.6 - (ParallelTier / 200.0));
            }
        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }

    /**
     * 检查槽位物品是否与配方所需的物品或其替代品匹配。
     */
    private boolean isItemStackMatching(ItemStack slotStack, ItemStack requiredStack, ItemStack[] alts) {
        if (slotStack == null || requiredStack == null) {
            return false; // 如果槽位物品或配方物品为空，返回 false
        }

        // 检查直接匹配
        if (GTUtility.areStacksEqual(slotStack, requiredStack, true)) {
            return true;
        }

        // 检查替代品匹配
        if (alts != null && alts.length > 0) {
            for (ItemStack altStack : alts) {
                if (GTUtility.areStacksEqual(slotStack, altStack, true)) {
                    return true;
                }
            }
        }

        return false; // 未找到匹配物品
    }

    /**
     * 从配方中提取物品及其数量
     *
     * @param recipe 配方
     * @return 物品及其数量的映射
     */
    private Map<ItemStack, Integer> extractItemRequirements(GTRecipe.RecipeAssemblyLine recipe) {
        Map<ItemStack, Integer> itemRequirements = new HashMap<>();

        // 假设 recipe.mInputs 是配方的物品输入数组
        for (ItemStack itemStack : recipe.mInputs) {
            if (itemStack != null && itemStack.stackSize > 0) {
                itemRequirements.put(itemStack, itemStack.stackSize);
            }
        }

        return itemRequirements;
    }

    /**
     * @param inputBusses      Input bus list to check. Usually the input bus list of multi.
     * @param maxParallel      Maximum parallel recipes allowed.
     * @param itemRequirements Map of item requirements (ItemStack -> amount).
     * @param inputsFromME     Map of items available from ME (GTUtility.ItemId -> ItemStack).
     * @return The number of parallel recipes, or 0 if recipe is not satisfied at all. 0 < number < 1 means that
     *         inputs are found but not enough.
     */
    public static double maxParallelCalculatedByInputItemsFromMap(ArrayList<MTEHatchInputBus> inputBusses,
        int maxParallel, Map<ItemStack, Integer> itemRequirements, Map<GTUtility.ItemId, ItemStack> inputsFromME) {
        // Convert item requirements map to int[] format
        int[] itemConsumptions = new int[inputBusses.size()];
        for (int i = 0; i < inputBusses.size(); i++) {
            MTEHatchInputBus inputBus = inputBusses.get(i);
            if (!inputBus.isValid()) return 0;

            ItemStack busItem = inputBus.getStackInSlot(0);
            if (busItem != null) {
                // Find the matching item in the requirements map
                for (Map.Entry<ItemStack, Integer> entry : itemRequirements.entrySet()) {
                    if (ItemStack.areItemStacksEqual(busItem, entry.getKey())) {
                        itemConsumptions[i] = entry.getValue();
                        break;
                    }
                }
            }
        }

        // Call the original method with the converted itemConsumptions array
        return GTRecipe.RecipeAssemblyLine
            .maxParallelCalculatedByInputItems(inputBusses, maxParallel, itemConsumptions, inputsFromME);
    }

    public static double maxParallelCalculatedByInputFluids(ArrayList<MTEHatchInput> inputHatches, int maxParallel,
        FluidStack[] fluidConsumptions, Map<Fluid, FluidStack> fluidsFromME) {
        // 统计所有输入仓中的流体总量
        Map<Fluid, Long> totalFluids = new HashMap<>();
        for (MTEHatchInput inputHatch : inputHatches) {
            if (!inputHatch.isValid()) continue;

            FluidStack fluid;
            if (inputHatch instanceof MTEHatchMultiInput multiInput) {
                fluid = multiInput.getFluid(0);
            } else if (inputHatch instanceof MTEHatchInputME meHatch) {
                fluid = meHatch.getShadowFluidStack(0);
            } else {
                fluid = inputHatch.getFillableStack();
            }

            if (fluid != null) {
                totalFluids.merge(fluid.getFluid(), (long) fluid.amount, Long::sum);
            }
        }

        // 计算最大并行数
        double currentParallel = maxParallel;
        for (FluidStack fluidConsumption : fluidConsumptions) {
            Fluid requiredFluid = fluidConsumption.getFluid();
            long requiredAmount = fluidConsumption.amount;

            // 检查流体总量是否足够
            if (!totalFluids.containsKey(requiredFluid)) {
                return 0; // 流体不足
            }

            // 计算当前流体的最大并行数
            long availableAmount = totalFluids.get(requiredFluid);
            currentParallel = Math.min(currentParallel, (double) availableAmount / requiredAmount);

            if (currentParallel <= 0) {
                return 0; // 并行数为 0，无法处理
            }
        }

        return currentParallel;
    }

}
