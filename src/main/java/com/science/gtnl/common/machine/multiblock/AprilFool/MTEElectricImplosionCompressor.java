package com.science.gtnl.common.machine.multiblock.AprilFool;

import static bartworks.common.loaders.ItemRegistry.BW_BLOCKS;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.isAir;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlocksTiered;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofChain;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.onElementPass;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.transpose;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.withChannel;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.enums.Textures.BlockIcons.*;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_IMPLOSION_COMPRESSOR_GLOW;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableList;
import com.gtnewhorizon.structurelib.alignment.IAlignmentLimits;
import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;

import bartworks.API.recipe.BartWorksRecipeMaps;
import bartworks.common.configs.Configuration;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.blocks.LudicrousBlocks;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.Mods;
import gregtech.api.enums.SoundResource;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.implementations.MTEExtendedPowerMultiBlockBase;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;

@Deprecated
public class MTEElectricImplosionCompressor extends MTEExtendedPowerMultiBlockBase<MTEElectricImplosionCompressor>
    implements ISurvivalConstructable {

    private int mCasing;
    private int mBlockTier = 0;
    private int mStructureBlockTier = 0;
    private static final boolean pistonRenderEnabled = !Configuration.multiblocks.disablePistonInEIC;
    private boolean isSuccessful = true;
    private static final int CASING_INDEX = 16;
    private final boolean isFlipped = this.getFlip()
        .isHorizontallyFlipped();
    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final String STRUCTURE_PIECE_MAIN_SUCCESSFUL = "main_successful";
    private static IStructureDefinition<MTEElectricImplosionCompressor> STRUCTURE_DEFINITION = null;
    private static final String[][] shape = new String[][] { { "AAA", "ABA", "AAA" }, { "DDD", "DGD", "DDD" },
        { "DDD", "DGD", "DDD" }, { "EEE", "EEE", "EEE" }, { "EFE", "FFF", "EFE" }, { "EEE", "EEE", "EEE" },
        { "D~D", "DGD", "DDD" }, { "DDD", "DGD", "DDD" }, { "CCC", "CBC", "CCC" } };

    public MTEElectricImplosionCompressor(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public MTEElectricImplosionCompressor(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MTEElectricImplosionCompressor(this.mName);
    }

    @Override
    public IStructureDefinition<MTEElectricImplosionCompressor> getStructureDefinition() {
        STRUCTURE_DEFINITION = StructureDefinition.<MTEElectricImplosionCompressor>builder()
            .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
            .addShape(
                STRUCTURE_PIECE_MAIN_SUCCESSFUL,
                Arrays.stream(transpose(shape))
                    .map(
                        sa -> Arrays.stream(sa)
                            .map(s -> s.replaceAll("F", " "))
                            .toArray(String[]::new))
                    .toArray(String[][]::new))
            .addElement('A', ofChain(ofBlock(GregTechAPI.sBlockCasings2, 0), ofBlock(GregTechAPI.sBlockCasings3, 4)))
            .addElement(
                'B',
                buildHatchAdder(MTEElectricImplosionCompressor.class).atLeast(Energy.or(ExoticEnergy))
                    .casingIndex(CASING_INDEX)
                    .dot(1)
                    .buildAndChain(
                        onElementPass(x -> ++x.mCasing, ofBlock(GregTechAPI.sBlockCasings2, 0)),
                        onElementPass(x -> ++x.mCasing, ofBlock(GregTechAPI.sBlockCasings3, 4))))
            .addElement(
                'C',
                buildHatchAdder(MTEElectricImplosionCompressor.class)
                    .atLeast(InputBus, OutputBus, Maintenance, InputHatch, OutputHatch)
                    .casingIndex(CASING_INDEX)
                    .dot(1)
                    .buildAndChain(
                        onElementPass(x -> ++x.mCasing, ofBlock(GregTechAPI.sBlockCasings2, 0)),
                        onElementPass(x -> ++x.mCasing, ofBlock(GregTechAPI.sBlockCasings3, 4))))
            .addElement('D', ofBlock(BW_BLOCKS[2], 1))
            .addElement(
                'E',
                withChannel(
                    "tierblock",
                    ofBlocksTiered(
                        MTEElectricImplosionCompressor::getTierBlock,
                        getTierBlockList(),
                        -1,
                        (t, m) -> mStructureBlockTier = m,
                        t -> mStructureBlockTier)))
            .addElement(
                'F',
                ofChain(
                    withChannel(
                        "tierblock",
                        ofBlocksTiered(
                            MTEElectricImplosionCompressor::getTierBlock,
                            getTierBlockList(),
                            -1,
                            (t, m) -> mStructureBlockTier = m,
                            t -> mStructureBlockTier)),
                    isAir()))
            .addElement('G', ofBlock(BW_BLOCKS[2], 0))
            .build();
        return STRUCTURE_DEFINITION;
    }

    public static ImmutableList<Pair<Block, Integer>> getTierBlockList() {
        ImmutableList.Builder<Pair<Block, Integer>> builder = ImmutableList.builder();

        builder.add(Pair.of(GregTechAPI.sBlockMetal5, 2));

        if (Mods.Avaritia.isModLoaded()) {
            builder.add(Pair.of(LudicrousBlocks.resource_block, 1));
        }

        builder.add(Pair.of(GregTechAPI.sBlockMetal9, 4));
        builder.add(Pair.of(GregTechAPI.sBlockMetal9, 3));
        builder.add(Pair.of(GregTechAPI.sBlockMetal9, 8));

        return builder.build();
    }

    public static int getTierBlock(Block block, int meta) {
        if (block == GregTechAPI.sBlockMetal5 && meta == 2) return 1;
        if (Mods.Avaritia.isModLoaded() && block == LudicrousBlocks.resource_block && meta == 1) return 2;
        if (block == GregTechAPI.sBlockMetal9 && meta == 4) return 3;
        if (block == GregTechAPI.sBlockMetal9 && meta == 3) return 4;
        if (block == GregTechAPI.sBlockMetal9 && meta == 8) return 5;
        return 0;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType("Implosion Compressor")
            .addInfo("Explosions are fun")
            .addInfo("Uses electricity instead of Explosives")
            .addInfo("Can parallel up to 4^(Tier - 1)")
            .addInfo("Tier is determined by containment block")
            .addInfo("Valid blocks: Neutronium, Infinity, Transcendent Metal, Spacetime, Universium")
            .addInfo("Minimum allowed energy hatch tier is one below recipe tier")
            .addTecTechHatchInfo()
            .beginStructureBlock(3, 9, 3, false)
            .addController("Front 3rd layer center")
            .addCasingInfoMin("Solid Steel Machine Casing", 8, false)
            .addStructureInfo("Casings can be replaced with Explosion Warning Signs")
            .addOtherStructurePart("Transformer-Winding Blocks", "Outer layer 2,3,7,8")
            .addOtherStructurePart("Nickel-Zinc-Ferrite Blocks", "Inner layer 2,3,7,8")
            .addOtherStructurePart("Containment Blocks", "Layer 4,5,6")
            .addMaintenanceHatch("Any bottom casing", 1)
            .addInputBus("Any bottom casing", 1)
            .addInputHatch("Any bottom casing", 1)
            .addOutputBus("Any bottom casing", 1)
            .addEnergyHatch("Bottom middle and/or top middle", 2)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
        int aColorIndex, boolean aActive, boolean aRedstone) {
        if (side == facing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(CASING_INDEX),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_IMPLOSION_COMPRESSOR_ACTIVE)
                    .extFacing()
                    .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_IMPLOSION_COMPRESSOR_ACTIVE_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(CASING_INDEX), TextureFactory.builder()
                .addIcon(OVERLAY_FRONT_IMPLOSION_COMPRESSOR)
                .extFacing()
                .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_IMPLOSION_COMPRESSOR_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(CASING_INDEX) };
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return BartWorksRecipeMaps.electricImplosionCompressorRecipes;
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @NotNull
            @Override
            protected CheckRecipeResult validateRecipe(@Nonnull GTRecipe recipe) {
                long voltage = MTEElectricImplosionCompressor.this.getAverageInputVoltage();
                // Only allow a minimum of T-1 energy hatch
                if (recipe.mEUt > voltage * 4) {
                    return CheckRecipeResultRegistry.insufficientPower(recipe.mEUt);
                }
                return CheckRecipeResultRegistry.SUCCESSFUL;
            }

            @NotNull
            @Override
            protected OverclockCalculator createOverclockCalculator(@NotNull GTRecipe recipe) {
                // For overclocking we'll allow all power to be used
                return super.createOverclockCalculator(recipe)
                    .setEUt(MTEElectricImplosionCompressor.this.getMaxInputEu())
                    .setAmperage(1);
            }

        }.setMaxParallelSupplier(() -> (int) Math.pow(4, Math.max(mBlockTier - 1, 0)));
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack itemStack) {
        return true;
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        mBlockTier = -1;
        isSuccessful = false;
        mStructureBlockTier = -1;
        this.buildPiece(STRUCTURE_PIECE_MAIN, itemStack, b, 1, 6, 0);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (this.mMachine) return -1;
        mBlockTier = -1;
        isSuccessful = false;
        mStructureBlockTier = -1;
        return this.survivialBuildPiece(STRUCTURE_PIECE_MAIN, stackSize, 1, 6, 0, elementBudget, env, false, true);
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack itemStack) {
        mCasing = 0;
        int mMaxHatchTier = 0;

        if (isSuccessful) {
            if (!checkPiece(STRUCTURE_PIECE_MAIN_SUCCESSFUL, 1, 6, 0)) {
                if (mBlockTier > 0 && pistonRenderEnabled)
                    setStringBlockXZ(aBaseMetaTileEntity, 1, 6, 0, shape, isFlipped, "F", mBlockTier);
                isSuccessful = false;
                mBlockTier = -1;
                mStructureBlockTier = -1;
                return false;
            }
        } else if (!checkPiece(STRUCTURE_PIECE_MAIN, 1, 6, 0)) {
            if (mBlockTier > 0 && pistonRenderEnabled)
                setStringBlockXZ(aBaseMetaTileEntity, 1, 6, 0, shape, isFlipped, "F", mBlockTier);
            isSuccessful = false;
            mBlockTier = -1;
            mStructureBlockTier = -1;
            return false;
        }

        List<MTEHatch> energyHatches = this.getExoticAndNormalEnergyHatchList();
        for (MTEHatch hatch : energyHatches) {
            mMaxHatchTier = Math.max(mMaxHatchTier, hatch.mTier);
        }

        isSuccessful = true;
        mBlockTier = mStructureBlockTier;

        return mMaintenanceHatches.size() == 1 && !energyHatches.isEmpty() && mBlockTier > 0;
    }

    @Override
    protected IAlignmentLimits getInitialAlignmentLimits() {
        return (d, r, f) -> d.offsetY == 0 && r.isNotRotated() && f.isNotFlipped();
    }

    @Override
    public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
        isSuccessful = false;
        super.onFirstTick(aBaseMetaTileEntity);
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (pistonRenderEnabled && isSuccessful && mBlockTier > 0 && aBaseMetaTileEntity.isActive()) {
            if (aTick % 20 == 0) {
                setStringBlockXZ(aBaseMetaTileEntity, 1, 6, 0, shape, isFlipped, "F", mBlockTier);
            } else if (aTick % 10 == 0) {
                setStringBlockXZ(aBaseMetaTileEntity, 1, 6, 0, shape, isFlipped, "F", Blocks.air);
            }
        }
    }

    @Override
    public void onBlockDestroyed() {
        if (pistonRenderEnabled && isSuccessful && mBlockTier > 0) {
            setStringBlockXZ(getBaseMetaTileEntity(), 1, 6, 0, shape, isFlipped, "F", mBlockTier);
        }
        super.onBlockDestroyed();
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("mBlockTier", mBlockTier);
        aNBT.setBoolean("isSuccessful", isSuccessful);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        mBlockTier = aNBT.getInteger("mBlockTier");
        isSuccessful = aNBT.getBoolean("isSuccessful");
    }

    @Override
    public boolean supportsBatchMode() {
        return true;
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

    @Override
    public boolean supportsVoidProtection() {
        return true;
    }

    @Override
    public int getMaxEfficiency(ItemStack itemStack) {
        return 10000;
    }

    @Override
    public int getPollutionPerTick(ItemStack itemStack) {
        return 0;
    }

    @Override
    public int getDamageToComponent(ItemStack itemStack) {
        return 0;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack itemStack) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected SoundResource getActivitySoundLoop() {
        return SoundResource.RANDOM_EXPLODE;
    }

    /**
     * Like structure definition, select a character from the structure definition string array as the target to place
     * blocks in the world, with the machine facing the XZ direction.
     *
     * @param aBaseMetaTileEntity the machine
     * @param OffSetX             horizontalOffSet of the machine structure definition
     * @param OffSetY             verticalOffSet of the machine structure definition
     * @param OffSetZ             depthOffSet of the machine structure definition
     * @param StructureString     the machine structure definition string array
     * @param isStructureFlipped  if the machine is flipped, use getFlip().isHorizontallyFlipped() to get it
     * @param TargetString        target character
     * @param TargetBlock         target block
     * @param TargetMeta          target block meta
     */
    public static void setStringBlockXZ(IGregTechTileEntity aBaseMetaTileEntity, int OffSetX, int OffSetY, int OffSetZ,
        String[][] StructureString, boolean isStructureFlipped, String TargetString, Block TargetBlock,
        int TargetMeta) {
        int mDirectionX = aBaseMetaTileEntity.getFrontFacing().offsetX;
        int mDirectionZ = aBaseMetaTileEntity.getFrontFacing().offsetZ;
        int xDir = 0;
        int zDir = 0;
        if (mDirectionX == 1) {
            // EAST
            xDir = 1;
            zDir = 1;
        } else if (mDirectionX == -1) {
            // WEST
            xDir = -1;
            zDir = -1;
        }
        if (mDirectionZ == 1) {
            // SOUTH
            xDir = -1;
            zDir = 1;
        } else if (mDirectionZ == -1) {
            // NORTH
            xDir = 1;
            zDir = -1;
        }
        int LengthX = StructureString[0][0].length();
        int LengthY = StructureString.length;
        int LengthZ = StructureString[0].length;
        for (int x = 0; x < LengthX; x++) {
            for (int z = 0; z < LengthZ; z++) {
                for (int y = 0; y < LengthY; y++) {
                    String ListStr = String.valueOf(StructureString[y][z].charAt(x));
                    if (!Objects.equals(ListStr, TargetString)) continue;

                    int aX = (OffSetX - x) * xDir;
                    int aY = OffSetY - y;
                    int aZ = (OffSetZ - z) * zDir;
                    if (mDirectionX == 1 || mDirectionX == -1) {
                        int temp = aX;
                        aX = aZ;
                        aZ = temp;
                    }
                    if (isStructureFlipped) {
                        if (mDirectionX == 1 || mDirectionX == -1) {
                            aZ = -aZ;
                        } else {
                            aX = -aX;
                        }
                    }

                    aBaseMetaTileEntity.getWorld()
                        .setBlock(
                            aBaseMetaTileEntity.getXCoord() + aX,
                            aBaseMetaTileEntity.getYCoord() + aY,
                            aBaseMetaTileEntity.getZCoord() + aZ,
                            TargetBlock,
                            TargetMeta,
                            3);
                }
            }
        }
    }

    public static void setStringBlockXZ(IGregTechTileEntity aBaseMetaTileEntity, int OffSetX, int OffSetY, int OffSetZ,
        String[][] StructureString, boolean isStructureFlipped, String TargetString, Block TargetBlock) {
        setStringBlockXZ(
            aBaseMetaTileEntity,
            OffSetX,
            OffSetY,
            OffSetZ,
            StructureString,
            isStructureFlipped,
            TargetString,
            TargetBlock,
            0);
    }

    public static void setStringBlockXZ(IGregTechTileEntity aBaseMetaTileEntity, int OffSetX, int OffSetY, int OffSetZ,
        String[][] StructureString, boolean isStructureFlipped, String TargetString, int BlockTier) {
        Block targetBlock;
        int targetMeta;
        if (BlockTier == 1) {
            targetBlock = GregTechAPI.sBlockMetal5;
            targetMeta = 2;
        } else if (BlockTier == 2 && Mods.Avaritia.isModLoaded()) {
            targetBlock = LudicrousBlocks.resource_block;
            targetMeta = 1;
        } else if (BlockTier == 3) {
            targetBlock = GregTechAPI.sBlockMetal9;
            targetMeta = 4;
        } else if (BlockTier == 4) {
            targetBlock = GregTechAPI.sBlockMetal9;
            targetMeta = 3;
        } else if (BlockTier == 5) {
            targetBlock = GregTechAPI.sBlockMetal9;
            targetMeta = 8;
        } else {
            return;
        }

        setStringBlockXZ(
            aBaseMetaTileEntity,
            OffSetX,
            OffSetY,
            OffSetZ,
            StructureString,
            isStructureFlipped,
            TargetString,
            targetBlock,
            targetMeta);
    }
}
