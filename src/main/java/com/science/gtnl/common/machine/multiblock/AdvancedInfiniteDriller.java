package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.common.block.Casings.BasicBlocks.MetaCasing;
import static gregtech.api.GregTechAPI.sBlockCasings4;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTStructureUtility.ofFrame;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.MTEEnhancedMultiBlockBase;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.common.blocks.BlockCasings4;
import gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock;

public class AdvancedInfiniteDriller extends MTEEnhancedMultiBlockBase<AdvancedInfiniteDriller>
    implements ISurvivalConstructable {

    private int excessFuel = 0;
    private int mCasing;
    protected int fuelConsumption;

    private static IStructureDefinition<AdvancedInfiniteDriller> STRUCTURE_DEFINITION = null;
    public static final String STRUCTURE_PIECE_MAIN = "main";
    public static final String PPS_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/photovoltaic_power_station";
    public static String[][] shape = StructureUtils.readStructureFromFile(PPS_STRUCTURE_FILE_PATH);
    public final int horizontalOffSet = 4;
    public final int verticalOffSet = 4;
    public final int depthOffSet = 2;
    protected static final int CASING_INDEX = ((BlockCasings4) sBlockCasings4).getTextureIndex(2);

    public AdvancedInfiniteDriller(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public AdvancedInfiniteDriller(String aName) {
        super(aName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        final MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.PhotovoltaicPowerStationRecipeType)
            .addInfo(TextLocalization.Tooltip_PhotovoltaicPowerStation_00)
            .addInfo(TextLocalization.Tooltip_PhotovoltaicPowerStation_01)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(9, 5, 7, true)
            .addInputHatch(TextLocalization.Tooltip_AdvancedPhotovoltaicPowerStation_Casing)
            .addDynamoHatch(TextLocalization.Tooltip_AdvancedPhotovoltaicPowerStation_Casing)
            .addMaintenanceHatch(TextLocalization.Tooltip_AdvancedPhotovoltaicPowerStation_Casing)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IStructureDefinition<AdvancedInfiniteDriller> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<AdvancedInfiniteDriller>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement(
                    'A',
                    buildHatchAdder(AdvancedInfiniteDriller.class).casingIndex(CASING_INDEX)
                        .dot(1)
                        .atLeast(InputHatch, Dynamo, Maintenance)
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(sBlockCasings4, 2))))
                .addElement('B', ofFrame(Materials.StainlessSteel))
                .addElement('D', ofBlock(MetaCasing, 10))
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
                    .addIcon(TexturesGtBlock.oMCDSolarTowerActive)
                    .extFacing()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(TexturesGtBlock.oMCDSolarTower)
                    .extFacing()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }

    public int getCasingTextureID() {
        return CASING_INDEX;
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    @Override
    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }

    @Override
    @NotNull
    public CheckRecipeResult checkProcessing() {
        // 确保 excessFuel 最低不会低于 300
        if (this.excessFuel < 300) {
            this.excessFuel = 300;
        }

        if (this.excessFuel > 10000) {
            this.excessFuel = 10000;
        }

        // 检查控制器槽是否有 Infinity Drill Head
        ItemStack controllerSlot = getControllerSlot();
        if (controllerSlot == null || !controllerSlot
            .isItemEqual(GTOreDictUnificator.get(OrePrefixes.toolHeadDrill, Materials.Infinity, 1L))) {
            // 如果没有 Infinity Drill Head，每秒将 excessFuel 减 4
            this.excessFuel = Math.max(300, this.excessFuel - 4);
            return CheckRecipeResultRegistry.NO_RECIPE;
        }

        // 获取所有存储的流体
        ArrayList<FluidStack> storedFluids = getStoredFluids();
        if (storedFluids.isEmpty()) {
            return CheckRecipeResultRegistry.NO_RECIPE;
        }

        // 检查 excessFuel 是否大于 2000
        if (this.excessFuel <= 2000) {
            // 初始化消耗次数
            int consumptionCount = 0;

            // 循环处理所有流体
            for (FluidStack tFluid : storedFluids) {
                if (tFluid != null && tFluid.getFluid()
                    .getName()
                    .equals("pyrotheum")) {
                    // 计算每次消耗的数量
                    int consumption = (int) Math.pow(this.excessFuel, 1.3);
                    if (this.excessFuel >= consumption) {
                        // 消耗 excessFuel
                        this.excessFuel -= consumption;
                        // 增加消耗次数
                        consumptionCount++;
                    }
                }
            }

            // 如果有消耗
            if (consumptionCount > 0) {
                // 根据消耗次数增加 excessFuel
                this.excessFuel += consumptionCount;
                // 设置配方时间为 128 tick
                this.mMaxProgresstime = 128;
                // 设置 EUt 为 0
                this.mEUt = 0;
                return CheckRecipeResultRegistry.SUCCESSFUL;
            } else {
                // 如果 excessFuel 小于等于 2000 且没有 Pyrotheum，返回无配方并每秒减 4 excessFuel
                this.excessFuel = Math.max(300, this.excessFuel - 4);
                return CheckRecipeResultRegistry.NO_RECIPE;
            }
        } else {
            // 如果 excessFuel 大于 2000
            int random = new Random().nextInt(4) + 1; // 生成 1 到 4 的随机数
            FluidStack fluidStack = null;
            switch (random) {
                case 1:
                    fluidStack = Materials.Oxygen.getGas(5760);
                    break;
                case 2:
                    fluidStack = Materials.Nitrogen.getGas(5760);
                    break;
                case 3:
                    fluidStack = Materials.Hydrogen.getGas(5760);
                    break;
                case 4:
                    fluidStack = Materials.Argon.getGas(5760);
                    break;
            }
            if (fluidStack != null) {
                this.mMaxProgresstime = 128; // 设置配方时间为 128 tick
                this.mEUt = random * 128000; // 根据随机数和流体数量计算 EUt
                return CheckRecipeResultRegistry.SUCCESSFUL;
            }
        }

        return CheckRecipeResultRegistry.NO_RECIPE;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);

        // 如果 excessFuel 大于 2000，每秒加 excessFuel / 2000 的数值（向下取整）
        if (this.excessFuel > 2000) {
            this.excessFuel += (int) Math.floor(this.excessFuel / 2000);
        }

        // 如果 excessFuel 大于 10000，手动将控制器槽位设置为空
        if (this.excessFuel > 10000) {
            ItemStack controllerSlot = getControllerSlot();
            if (controllerSlot != null && controllerSlot
                .isItemEqual(GTOreDictUnificator.get(OrePrefixes.toolHeadDrill, Materials.Infinity, 1L))) {
                // 手动将控制器槽位设置为空
                mInventory[getControllerSlotIndex()] = null;
            }
        }

        // 每隔 5 tick 检查一次输入流体
        if (aTick % 5 == 0) {
            ArrayList<FluidStack> storedFluids = getStoredFluids();
            for (FluidStack tFluid : storedFluids) {
                if (tFluid != null) {
                    // 检查流体类型是否为 Pyrotheum
                    if (tFluid.getFluid()
                        .getName()
                        .equals("pyrotheum")) {
                        // 尝试消耗 excessFuel ^ 1.3 次方的数量
                        int consumption = (int) Math.pow(this.excessFuel, 1.3);
                        if (this.excessFuel >= consumption) {
                            this.excessFuel -= consumption;
                            this.excessFuel += 1; // 将 excessFuel 加 1
                        }
                    }
                    // 检查是否存在 IC2 Distilled Water
                    else if (tFluid.getFluid()
                        .getName()
                        .equals("ic2distilledwater")) {
                            if (this.excessFuel >= 200000) {
                                this.excessFuel -= 200000;
                                this.excessFuel -= 1; // 将 excessFuel 减 1
                            }
                        }
                    // 检查是否存在 Liquid Oxygen
                    else if (tFluid.getFluid()
                        .getName()
                        .equals("liquidoxygen")) {
                            if (this.excessFuel >= 200000) {
                                this.excessFuel -= 200000;
                                this.excessFuel -= 2; // 将 excessFuel 减 2
                            }
                        }
                    // 检查是否存在 Liquid Helium
                    else if (tFluid.getFluid()
                        .getName()
                        .equals("liquid helium")) {
                            if (this.excessFuel >= 200000) {
                                this.excessFuel -= 200000;
                                this.excessFuel -= 4; // 将 excessFuel 减 4
                            }
                        }
                }
            }
        }
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        return checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet)
            && mMaintenanceHatches.size() == 1
            && mCasing >= 8;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new AdvancedInfiniteDriller(this.mName);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
    }

    @Override
    public int getDamageToComponent(ItemStack aStack) {
        return 1;
    }

    @Override
    public String[] getInfoData() {
        return new String[] {
            StatCollector.translateToLocal("GT5U.engine.output") + ": "
                + EnumChatFormatting.RED
                + GTUtility.formatNumbers(mEUt)
                + EnumChatFormatting.RESET
                + " EU/t",
            StatCollector.translateToLocal("GT5U.engine.consumption") + ": "
                + EnumChatFormatting.YELLOW
                + GTUtility.formatNumbers(fuelConsumption)
                + EnumChatFormatting.RESET
                + " L/t" };
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
