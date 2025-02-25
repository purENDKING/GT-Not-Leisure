package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.GTMod.GT_FML_LOGGER;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.enums.Mods.IndustrialCraft2;
import static gregtech.api.util.GTStructureUtility.*;
import static gregtech.api.util.GTUtility.validMTEList;
import static tectech.thing.casing.TTCasingsContainer.sBlockCasingsTT;

import java.util.*;

import net.minecraft.client.renderer.texture.IIconRegister;
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
import gregtech.api.enums.*;
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
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.*;
import gregtech.api.util.GTRecipe.RecipeAssemblyLine;
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
        ArrayList<ItemStack> allInputs = getAllStoredInputs();
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
        long adjustedMaxPower = energyHatchPower;
        int adjustedTime = 1;

        // 如果总功率大于能源仓最大功率，则调整功率和时间
        while (totalPower > adjustedMaxPower) {
            adjustedMaxPower /= 2; // 功率减半
            adjustedTime *= 2; // 时间翻倍
        }

        // 计算并行处理能力
        int maxParallel = getMaxParallelRecipes();
        int currentParallel = maxParallel;

        // 检查输入物品和流体是否足够
        for (GTRecipe.RecipeAssemblyLine recipe : filteredRecipes) {
            // 检查物品输入
            int[] itemConsumptions = GTRecipe.RecipeAssemblyLine.getItemConsumptionAmountArray(mInputBusses, recipe);
            if (itemConsumptions == null || itemConsumptions.length == 0) {
                continue; // 跳过输入不足的配方
            }

            currentParallel = (int) GTRecipe.RecipeAssemblyLine
                .maxParallelCalculatedByInputItems(mInputBusses, currentParallel, itemConsumptions, inputMap);
            if (currentParallel <= 0) {
                continue; // 跳过并行处理能力不足的配方
            }

            // 检查流体输入
            if (recipe.mFluidInputs.length > 0) {
                currentParallel = (int) RecipeAssemblyLine
                    .maxParallelCalculatedByInputFluids(mInputHatches, currentParallel, recipe.mFluidInputs, fluidMap);
                if (currentParallel <= 0) {
                    continue; // 跳过流体输入不足的配方
                }
            }
        }

        if (currentParallel <= 0) {
            return CheckRecipeResultRegistry.NO_RECIPE;
        }

        // 设置最终并行处理能力
        maxParallel = currentParallel;

        // 消耗物品
        for (GTRecipe.RecipeAssemblyLine recipe : filteredRecipes) {
            int[] itemConsumptions = GTRecipe.RecipeAssemblyLine.getItemConsumptionAmountArray(mInputBusses, recipe);
            if (itemConsumptions != null && itemConsumptions.length > 0) {
                for (int i = 0; i < itemConsumptions.length; i++) {
                    if (itemConsumptions[i] > 0) {
                        ItemStack inputStack = allInputs.get(i);
                        if (inputStack != null) {
                            inputStack.stackSize -= itemConsumptions[i] * maxParallel;
                            if (inputStack.stackSize <= 0) {
                                allInputs.set(i, null); // 如果物品被消耗完，设置为 null
                            }
                        }
                    }
                }
            }
        }

        // 消耗流体
        for (GTRecipe.RecipeAssemblyLine recipe : filteredRecipes) {
            if (recipe.mFluidInputs.length > 0) {
                for (FluidStack fluidInput : recipe.mFluidInputs) {
                    FluidStack consumedFluid = fluidMap.get(fluidInput.getFluid());
                    if (consumedFluid != null) {
                        consumedFluid.amount -= fluidInput.amount * maxParallel;
                        if (consumedFluid.amount <= 0) {
                            fluidMap.remove(fluidInput.getFluid()); // 如果流体被消耗完，移除
                        }
                    }
                }
            }
        }

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
        this.lEUt = -adjustedMaxPower;
        this.mMaxProgresstime = adjustedTime;

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
        for (ItemStack slot : mOutputItems) {
            if (slot == null
                || (slot.isItemEqual(output) && slot.stackSize + output.stackSize <= slot.getMaxStackSize())) {
                return true;
            }
        }
        return false;
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
}
