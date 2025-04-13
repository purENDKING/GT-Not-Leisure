package com.science.gtnl.common.machine.multiblock.AprilFool;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.enums.GTValues.V;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.multitileentity.multiblock.casing.Glasses.chainAllGlasses;
import static gregtech.api.recipe.RecipeMaps.compressorRecipes;
import static gregtech.api.util.GTStructureUtility.*;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.structurelib.alignment.IAlignmentLimits;
import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.machine.multiMachineClasses.SteamMultiMachineBase;

import gregtech.api.GregTechAPI;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import gregtech.common.blocks.BlockCasings2;
import gregtech.common.tileentities.render.TileEntityBlackhole;

public class SteamMegaCompressor extends SteamMultiMachineBase<SteamMegaCompressor> implements ISurvivalConstructable {

    public static final String TEXTURE_OVERLAY_MULTI_STEAMHOLE_GLOW = "sciencenotleisure:iconsets/OVERLAY_MULTI_STEAMHOLE_GLOW";
    public static final String TEXTURE_OVERLAY_MULTI_STEAMHOLE = "sciencenotleisure:iconsets/OVERLAY_MULTI_STEAMHOLE";
    public static final String TEXTURE_OVERLAY_MULTI_STEAMHOLE_ACTIVE = "sciencenotleisure:iconsets/OVERLAY_MULTI_STEAMHOLE_ACTIVE";
    public static final String TEXTURE_OVERLAY_MULTI_STEAMHOLE_ACTIVE_GLOW = "sciencenotleisure:iconsets/OVERLAY_MULTI_STEAMHOLE_ACTIVE_GLOW";
    public static Textures.BlockIcons.CustomIcon OVERLAY_MULTI_STEAMHOLE_GLOW = new Textures.BlockIcons.CustomIcon(
        TEXTURE_OVERLAY_MULTI_STEAMHOLE_GLOW);
    public static Textures.BlockIcons.CustomIcon OVERLAY_MULTI_STEAMHOLE = new Textures.BlockIcons.CustomIcon(
        TEXTURE_OVERLAY_MULTI_STEAMHOLE);
    public static Textures.BlockIcons.CustomIcon OVERLAY_MULTI_STEAMHOLE_ACTIVE = new Textures.BlockIcons.CustomIcon(
        TEXTURE_OVERLAY_MULTI_STEAMHOLE_ACTIVE);
    public static Textures.BlockIcons.CustomIcon OVERLAY_MULTI_STEAMHOLE_ACTIVE_GLOW = new Textures.BlockIcons.CustomIcon(
        TEXTURE_OVERLAY_MULTI_STEAMHOLE_ACTIVE_GLOW);
    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static IStructureDefinition<SteamMegaCompressor> STRUCTURE_DEFINITION = null;
    private static final String SMC_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/steam_mega_compressor"; // 文件路径
    private static final String[][] shape = StructureUtils.readStructureFromFile(SMC_STRUCTURE_FILE_PATH);
    public static final int HORIZONTAL_OFF_SET = 17;
    public static final int VERTICAL_OFF_SET = 27;
    public static final int DEPTH_OFF_SET = 10;

    public SteamMegaCompressor(String aName) {
        super(aName);
    }

    public SteamMegaCompressor(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    @Override
    public String getMachineType() {
        return TextLocalization.SteamMegaCompressorRecipeType;
    }

    @Override
    public int getTierRecipes() {
        return 3;
    }

    @Override
    public IStructureDefinition<SteamMegaCompressor> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<SteamMegaCompressor>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', chainAllGlasses())
                .addElement(
                    'B',
                    ofChain(
                        buildSteamBigInput(SteamMegaCompressor.class)
                            .casingIndex(((BlockCasings2) GregTechAPI.sBlockCasings2).getTextureIndex(0))
                            .dot(1)
                            .build(),
                        buildSteamInput(SteamMegaCompressor.class)
                            .casingIndex(((BlockCasings2) GregTechAPI.sBlockCasings2).getTextureIndex(0))
                            .dot(1)
                            .build(),
                        buildHatchAdder(SteamMegaCompressor.class)
                            .casingIndex(((BlockCasings2) GregTechAPI.sBlockCasings2).getTextureIndex(0))
                            .dot(1)
                            .atLeast(
                                SteamHatchElement.InputBus_Steam,
                                SteamHatchElement.OutputBus_Steam,
                                InputBus,
                                OutputBus,
                                InputHatch,
                                OutputHatch)
                            .buildAndChain(
                                onElementPass(x -> ++x.tCountCasing, ofBlock(GregTechAPI.sBlockCasings2, 0)))))
                .addElement('C', ofBlock(GregTechAPI.sBlockCasings1, 10))
                .addElement('D', ofFrame(Materials.Steel))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, HORIZONTAL_OFF_SET, VERTICAL_OFF_SET, DEPTH_OFF_SET);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (mMachine) return -1;
        int realBudget = elementBudget >= 200 ? elementBudget : Math.min(200, elementBudget * 5);
        return survivialBuildPiece(
            STRUCTURE_PIECE_MAIN,
            stackSize,
            HORIZONTAL_OFF_SET,
            VERTICAL_OFF_SET,
            DEPTH_OFF_SET,
            realBudget,
            env,
            false,
            true);
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return compressorRecipes;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(getMachineType())
            .addInfo(TextLocalization.Tooltip_SteamMegaCompressor_00)
            .addInfo(TextLocalization.Tooltip_SteamMegaCompressor_01)
            .addInfo(TextLocalization.Tooltip_SteamMegaCompressor_02)
            .addInfo(TextLocalization.Tooltip_SteamMegaCompressor_03)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(35, 33, 35, true)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
        int aColorIndex, boolean aActive, boolean aRedstone) {
        ITexture[] rTexture;
        if (side == facing) {
            if (aActive) {
                rTexture = new ITexture[] {
                    Textures.BlockIcons
                        .getCasingTextureForId(GTUtility.getCasingTextureIndex(GregTechAPI.sBlockCasings2, 0)),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_MULTI_STEAMHOLE_ACTIVE)
                        .extFacing()
                        .build(),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_MULTI_STEAMHOLE_ACTIVE_GLOW)
                        .extFacing()
                        .glow()
                        .build() };
            } else {
                rTexture = new ITexture[] {
                    Textures.BlockIcons
                        .getCasingTextureForId(GTUtility.getCasingTextureIndex(GregTechAPI.sBlockCasings2, 0)),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_MULTI_STEAMHOLE)
                        .extFacing()
                        .build(),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_MULTI_STEAMHOLE_GLOW)
                        .extFacing()
                        .glow()
                        .build() };
            }
        } else {
            rTexture = new ITexture[] { Textures.BlockIcons
                .getCasingTextureForId(GTUtility.getCasingTextureIndex(GregTechAPI.sBlockCasings2, 0)) };
        }
        return rTexture;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        return checkPiece(STRUCTURE_PIECE_MAIN, HORIZONTAL_OFF_SET, VERTICAL_OFF_SET, DEPTH_OFF_SET);
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @NotNull
            @Override
            protected OverclockCalculator createOverclockCalculator(@NotNull GTRecipe recipe) {
                return super.createOverclockCalculator(recipe).limitOverclockCount(Math.max(4, RecipeOcCount));
            }

            @NotNull
            @Override
            protected CheckRecipeResult validateRecipe(@NotNull GTRecipe recipe) {
                return super.validateRecipe(recipe);
            }

            @NotNull
            @Override
            protected CheckRecipeResult onRecipeStart(@NotNull GTRecipe recipe) {
                createRenderBlock();
                return super.onRecipeStart(recipe);
            }

            @Override
            public ProcessingLogic clear() {
                destroyRenderBlock();
                return super.clear();
            }
        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }

    @Override
    public int getMaxParallelRecipes() {
        return 256;
    }

    @Override
    protected void setProcessingLogicPower(ProcessingLogic logic) {
        logic.setAvailableVoltage(V[3]);
        // We need to trick the GT_ParallelHelper we have enough amps for all recipe parallels.
        logic.setAvailableAmperage(getMaxParallelRecipes());
        logic.setAmperageOC(false);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new SteamMegaCompressor(this.mName);
    }

    @Override
    public void onBlockDestroyed() {
        destroyRenderBlock();
        super.onBlockDestroyed();
    }

    @Override
    protected IAlignmentLimits getInitialAlignmentLimits() {
        return (d, r, f) -> d != ForgeDirection.UP && d != ForgeDirection.DOWN;
    }

    @Override
    public boolean isRotationChangeAllowed() {
        return false;
    }

    private TileEntityBlackhole rendererTileEntity = null;

    // Returns true if render was actually created
    private boolean createRenderBlock() {
        if (!mMachine) return false;
        IGregTechTileEntity base = this.getBaseMetaTileEntity();
        ForgeDirection opposite = getDirection().getOpposite();
        int x = 7 * opposite.offsetX;
        int z = 7 * opposite.offsetZ;
        int y = 11;

        base.getWorld()
            .setBlock(base.getXCoord() + x, base.getYCoord() + y, base.getZCoord() + z, Blocks.air);
        base.getWorld()
            .setBlock(base.getXCoord() + x, base.getYCoord() + y, base.getZCoord() + z, GregTechAPI.sBlackholeRender);
        rendererTileEntity = (TileEntityBlackhole) base.getWorld()
            .getTileEntity(base.getXCoord() + x, base.getYCoord() + y, base.getZCoord() + z);

        rendererTileEntity.startScaleChange(true);
        rendererTileEntity.toggleLaser(true);
        rendererTileEntity.setLaserColor(6, 6, 6);
        rendererTileEntity.setStability(0);
        return true;
    }

    private void destroyRenderBlock() {
        IGregTechTileEntity base = this.getBaseMetaTileEntity();
        ForgeDirection opposite = getDirection().getOpposite();
        int x = 7 * opposite.offsetX;
        int z = 7 * opposite.offsetZ;
        int y = 11;

        this.getBaseMetaTileEntity()
            .getWorld()
            .setBlock(base.getXCoord() + x, base.getYCoord() + y, base.getZCoord() + z, Blocks.air);
    }
}
