package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.GregTechAPI.sBlockCasings8;
import static gregtech.api.enums.HatchElement.InputBus;
import static gregtech.api.enums.HatchElement.InputHatch;
import static gregtech.api.enums.HatchElement.OutputBus;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTStructureUtility.ofFrame;

import java.util.Arrays;
import java.util.Collection;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.NotNull;

import com.dreammaster.block.BlockList;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.machine.multiMachineClasses.MultiMachineBase;
import com.science.gtnl.common.recipe.RecipeRegister;

import WayofTime.alchemicalWizardry.ModBlocks;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.common.blocks.BlockCasings8;
import kubatech.loaders.BlockLoader;

public class BloodSoulSacrificialArray extends MultiMachineBase<BloodSoulSacrificialArray> {

    public static final String STRUCTURE_PIECE_MAIN = "main";
    public static final String BSSA_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/blood_soul_sacrificial_array"; // 文件路径
    public final int horizontalOffSet = 16;
    public final int verticalOffSet = 10;
    public final int depthOffSet = 9;
    public static IStructureDefinition<BloodSoulSacrificialArray> STRUCTURE_DEFINITION = null;
    public byte mode = 1;
    public String[][] shape = StructureUtils.readStructureFromFile(BSSA_STRUCTURE_FILE_PATH);

    public BloodSoulSacrificialArray(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public BloodSoulSacrificialArray(String aName) {
        super(aName);
    }

    protected float getSpeedBonus() {
        return 1F;
    }

    protected int getMaxParallelRecipes() {
        return 2147483647;
    }

    protected boolean isEnablePerfectOverclock() {
        return false;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        switch (mode) {
            case 1:
                return RecipeRegister.BloodSoulTradingRecipes;
            case 2:
                return RecipeRegister.AlchemicChemistrySetRecipes;
            default:
                return RecipeRegister.BloodDemonInjectionRecipes;
        }
    }

    @NotNull
    @Override
    public Collection<RecipeMap<?>> getAvailableRecipeMaps() {
        return Arrays.asList(
            RecipeRegister.BloodSoulTradingRecipes,
            RecipeRegister.AlchemicChemistrySetRecipes,
            RecipeRegister.BloodDemonInjectionRecipes);
    }

    @Override
    public final void onScrewdriverRightClick(ForgeDirection side, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        this.mode = (byte) ((this.mode + 1) % 3);
        GTUtility.sendChatToPlayer(
            aPlayer,
            StatCollector.translateToLocal("BloodSoulSacrificialArray.modeMsg." + this.mode));

    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        repairMachine();
        return checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet) && checkHatch();
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        this.buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, horizontalOffSet, verticalOffSet, depthOffSet);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (this.mMachine) return -1;
        return this.survivialBuildPiece(
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
    public IStructureDefinition<BloodSoulSacrificialArray> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<BloodSoulSacrificialArray>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', ofBlock(BlockLoader.defcCasingBlock, 7))
                .addElement('B', ofBlock(sBlockCasings8, 3))
                .addElement('C', ofBlock(gtPlusPlus.core.block.ModBlocks.blockSpecialMultiCasings, 13))
                .addElement('D', ofBlock(gtPlusPlus.core.block.ModBlocks.blockCasingsMisc, 9))
                .addElement('E', ofBlockAnyMeta(BlockList.BloodyIchorium.getBlock()))
                .addElement('F', ofBlockAnyMeta(BlockList.BloodyThaumium.getBlock()))
                .addElement('G', ofBlockAnyMeta(BlockList.BloodyVoid.getBlock()))
                .addElement('H', ofBlock(Blocks.diamond_block, 0))
                .addElement('I', ofBlock(ModBlocks.bloodRune, 0))
                .addElement('J', ofBlock(ModBlocks.bloodRune, 3))
                .addElement('K', ofBlock(ModBlocks.bloodRune, 4))
                .addElement('L', ofBlock(ModBlocks.bloodRune, 5))
                .addElement('M', ofBlock(ModBlocks.bloodRune, 6))
                .addElement('N', ofBlockAnyMeta(com.arc.bloodarsenal.common.block.ModBlocks.blood_lamp))
                .addElement('O', ofBlockAnyMeta(ModBlocks.blockCrystal))
                .addElement('P', ofBlockAnyMeta(ModBlocks.bloodStoneBrick))
                .addElement('Q', ofBlockAnyMeta(Blocks.glowstone))
                .addElement('R', ofBlockAnyMeta(ModBlocks.ritualStone))
                .addElement('S', ofBlockAnyMeta(ModBlocks.runeOfSacrifice))
                .addElement('T', ofBlockAnyMeta(ModBlocks.runeOfSelfSacrifice))
                .addElement('U', ofBlockAnyMeta(ModBlocks.speedRune))
                .addElement('V', ofBlockAnyMeta(Blocks.beacon))
                .addElement('W', ofBlockAnyMeta(com.arc.bloodarsenal.common.block.ModBlocks.lp_materializer))
                .addElement('X', ofFrame(Materials.BlackPlutonium))
                .addElement('Y', ofBlockAnyMeta(ModBlocks.ritualStone))
                .addElement(
                    'Z',
                    buildHatchAdder(BloodSoulSacrificialArray.class).atLeast(InputBus, OutputBus, InputHatch)
                        .adder(BloodSoulSacrificialArray::addToMachineList)
                        .dot(1)
                        .casingIndex(((BlockCasings8) GregTechAPI.sBlockCasings8).getTextureIndex(3))
                        .buildAndChain(GregTechAPI.sBlockCasings8, 3))
                .addElement('0', ofBlockAnyMeta(ModBlocks.blockAltar))
                .addElement('1', ofBlockAnyMeta(Blocks.hopper))
                .addElement('2', ofFrame(Materials.Plutonium))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setByte("mode", mode);
    }

    @Override
    public void loadNBTData(final NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        mode = aNBT.getByte("mode");
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        final MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.BloodSoulSacrificialArrayRecipeType)
            .addInfo(TextLocalization.Tooltip_BloodSoulSacrificialArray_00)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(33, 14, 30, false)
            .addInputHatch(TextLocalization.Tooltip_BloodSoulSacrificialArray_Casing, 1)
            .addInputBus(TextLocalization.Tooltip_BloodSoulSacrificialArray_Casing, 1)
            .addOutputBus(TextLocalization.Tooltip_BloodSoulSacrificialArray_Casing, 1)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new BloodSoulSacrificialArray(this.mName);
    }

    public int getCasingTextureID() {
        return ((BlockCasings8) GregTechAPI.sBlockCasings8).getTextureIndex(3);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_DTPF_ON)
                    .extFacing()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_DTPF_OFF)
                    .extFacing()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }
}
