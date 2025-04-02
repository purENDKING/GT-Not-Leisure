package com.science.gtnl.common.machine.multiblock.AprilFool;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.enums.HatchElement.OutputHatch;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.common.machine.multiMachineClasses.SteamMultiMachineBase;
import com.science.gtnl.common.recipe.RecipeRegister;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.SoundResource;
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
import gregtech.common.blocks.BlockCasings1;
import gtPlusPlus.core.block.ModBlocks;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;

public class SteamInfernalCokeOven extends SteamMultiMachineBase<SteamInfernalCokeOven>
    implements ISurvivalConstructable {

    public static final String TEXTURE_OVERLAY_STEAM_COKE_OVEN = "sciencenotleisure:iconsets/OVERLAY_STEAM_COKE_OVEN";
    public static final String TEXTURE_OVERLAY_STEAM_COKE_OVEN_ACTIVE = "sciencenotleisure:iconsets/OVERLAY_STEAM_COKE_OVEN_ACTIVE";
    public static final String TEXTURE_OVERLAY_STEAM_COKE_OVEN_ACTIVE_GLOW = "sciencenotleisure:iconsets/OVERLAY_STEAM_COKE_OVEN_ACTIVE_GLOW";
    public static Textures.BlockIcons.CustomIcon OVERLAY_STEAM_COKE_OVEN = new Textures.BlockIcons.CustomIcon(
        TEXTURE_OVERLAY_STEAM_COKE_OVEN);
    public static Textures.BlockIcons.CustomIcon OVERLAY_STEAM_COKE_OVEN_ACTIVE = new Textures.BlockIcons.CustomIcon(
        TEXTURE_OVERLAY_STEAM_COKE_OVEN_ACTIVE);
    public static Textures.BlockIcons.CustomIcon OVERLAY_STEAM_COKE_OVEN_ACTIVE_GLOW = new Textures.BlockIcons.CustomIcon(
        TEXTURE_OVERLAY_STEAM_COKE_OVEN_ACTIVE_GLOW);

    public SteamInfernalCokeOven(String aName) {
        super(aName);
    }

    public SteamInfernalCokeOven(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity arg0) {
        return new SteamInfernalCokeOven(this.mName);
    }

    @Override
    public String getMachineType() {
        return "Coke Oven";
    }

    private float speedup = 1;
    private int runningTickCounter = 0;

    private static final String STRUCTURE_PIECE_MAIN = "main";

    private static final String[][] structure = transpose(
        new String[][] { { "CCCCC", "CCCCC", "CCCCC", "CCCCC", "CCCCC" },
            { "AAAAA", "A   A", "A   A", "A   A", "AAAAA" }, { "BBBBB", "B   B", "B   B", "B   B", "BBBBB" },
            { "AA~AA", "A   A", "A   A", "A   A", "AAAAA" }, { "CCCCC", "CCCCC", "CCCCC", "CCCCC", "CCCCC" } });

    public IStructureDefinition<SteamInfernalCokeOven> getStructureDefinition() {
        return StructureDefinition.<SteamInfernalCokeOven>builder()
            .addShape(STRUCTURE_PIECE_MAIN, structure)
            .addElement(
                'A',
                ofChain(
                    buildHatchAdder(SteamInfernalCokeOven.class)
                        .atLeast(SteamHatchElement.InputBus_Steam, SteamHatchElement.OutputBus_Steam, OutputHatch)
                        .casingIndex(((BlockCasings1) GregTechAPI.sBlockCasings1).getTextureIndex(10))
                        .dot(1)
                        .buildAndChain(),
                    ofBlock(GregTechAPI.sBlockCasings1, 10)))
            .addElement('B', ofBlock(ModBlocks.blockCasingsMisc, 2))
            .addElement('C', ofBlock(Blocks.nether_brick, 0))
            .build();
    }

    private static final int HORIZONTAL_OFF_SET = 2;
    private static final int VERTICAL_OFF_SET = 3;
    private static final int DEPTH_OFF_SET = 0;

    @Override
    public int getCasingTextureID() {
        return ((BlockCasings1) GregTechAPI.sBlockCasings1).getTextureIndex(10);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity baseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        ITexture[] rTexture;
        if (side == aFacing) {
            if (aActive) {
                rTexture = new ITexture[] {
                    Textures.BlockIcons
                        .getCasingTextureForId(GTUtility.getCasingTextureIndex(GregTechAPI.sBlockCasings1, 10)),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_STEAM_COKE_OVEN_ACTIVE)
                        .extFacing()
                        .build(),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_STEAM_COKE_OVEN_ACTIVE_GLOW)
                        .extFacing()
                        .glow()
                        .build() };
            } else {
                rTexture = new ITexture[] {
                    Textures.BlockIcons
                        .getCasingTextureForId(GTUtility.getCasingTextureIndex(GregTechAPI.sBlockCasings1, 10)),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_STEAM_COKE_OVEN)
                        .extFacing()
                        .build() };
            }
        } else {
            rTexture = new ITexture[] { Textures.BlockIcons
                .getCasingTextureForId(GTUtility.getCasingTextureIndex(GregTechAPI.sBlockCasings1, 10)) };
        }
        return rTexture;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, HORIZONTAL_OFF_SET, VERTICAL_OFF_SET, DEPTH_OFF_SET);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        return survivialBuildPiece(
            STRUCTURE_PIECE_MAIN,
            stackSize,
            HORIZONTAL_OFF_SET,
            VERTICAL_OFF_SET,
            DEPTH_OFF_SET,
            elementBudget,
            env,
            false,
            true);
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        if (!checkPiece(STRUCTURE_PIECE_MAIN, HORIZONTAL_OFF_SET, VERTICAL_OFF_SET, DEPTH_OFF_SET)) return false;
        return true;
    }

    @Override
    public boolean onRunningTick(ItemStack aStack) {
        runningTickCounter++;
        if (runningTickCounter % 100 == 0 && speedup < 7) {
            runningTickCounter = 0;
            speedup += 0.1F;
        }
        return super.onRunningTick(aStack);
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @NotNull
            @Override
            protected CheckRecipeResult validateRecipe(@NotNull GTRecipe recipe) {
                setSpeedBonus(1F / speedup);
                return super.validateRecipe(recipe);
            }
        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }

    @Override
    public int getTierRecipes() {
        return 14;
    }

    @Override
    public int getMaxParallelRecipes() {
        // Max call to prevent seeing -16 parallels in waila for unformed multi
        return 16;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeRegister.InfernalCockRecipes;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType("Coke Oven")
            .addInfo("Works more efficiently than a clay oven")
            .addInfo("Every 5 seconds of continuous working it gets 1% faster, up to 600%")
            .addInfo(EnumChatFormatting.AQUA + "" + EnumChatFormatting.ITALIC + "Poggers")
            .toolTipFinisher();
        return tt;
    }

    @Override
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y,
        int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
    }

    @Override
    public void getWailaBody(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        super.getWailaBody(itemStack, currentTip, accessor, config);
        final NBTTagCompound tag = accessor.getNBTData();
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setFloat("steam", speedup);
    }

    @Override
    public void loadNBTData(final NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        speedup = aNBT.getFloat("steam");
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected SoundResource getActivitySoundLoop() {
        return SoundResource.IC2_MACHINES_MACERATOR_OP;
    }

}
