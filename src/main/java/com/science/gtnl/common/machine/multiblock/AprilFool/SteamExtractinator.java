package com.science.gtnl.common.machine.multiblock.AprilFool;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.common.block.Casings.BasicBlocks.*;
import static gregtech.api.enums.HatchElement.InputHatch;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTStructureUtility.ofFrame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
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
import gregtech.api.enums.Materials;
import gregtech.api.enums.SoundResource;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;

public class SteamExtractinator extends SteamMultiMachineBase<SteamExtractinator> implements ISurvivalConstructable {

    public static final String TEXTURE_OVERLAY_EXTRACTINATOR = "sciencenotleisure:iconsets/OVERLAY_EXTRACTINATOR";
    public static final String TEXTURE_OVERLAY_EXTRACTINATOR_ACTIVE = "sciencenotleisure:iconsets/OVERLAY_EXTRACTINATOR_ACTIVE";
    public static Textures.BlockIcons.CustomIcon OVERLAY_EXTRACTINATOR = new Textures.BlockIcons.CustomIcon(
        TEXTURE_OVERLAY_EXTRACTINATOR);
    public static Textures.BlockIcons.CustomIcon OVERLAY_EXTRACTINATOR_ACTIVE = new Textures.BlockIcons.CustomIcon(
        TEXTURE_OVERLAY_EXTRACTINATOR_ACTIVE);

    public SteamExtractinator(String aName) {
        super(aName);
    }

    public SteamExtractinator(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity arg0) {
        return new SteamExtractinator(this.mName);
    }

    @Override
    public String getMachineType() {
        return "Resource Extractor";
    }

    private static final String STRUCTURE_PIECE_MAIN = "main";

    private IStructureDefinition<SteamExtractinator> STRUCTURE_DEFINITION = null;

    private static final int HORIZONTAL_OFF_SET = 1;
    private static final int VERTICAL_OFF_SET = 8;
    private static final int DEPTH_OFF_SET = 10;

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
        int aColorIndex, boolean aActive, boolean aRedstone) {
        ITexture[] rTexture;
        if (side == facing) {
            if (aActive) {
                rTexture = new ITexture[] {
                    Textures.BlockIcons
                        .getCasingTextureForId(GTUtility.getCasingTextureIndex(GregTechAPI.sBlockCasings1, 10)),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_EXTRACTINATOR_ACTIVE)
                        .extFacing()
                        .build() };
            } else {
                rTexture = new ITexture[] {
                    Textures.BlockIcons
                        .getCasingTextureForId(GTUtility.getCasingTextureIndex(GregTechAPI.sBlockCasings1, 10)),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_EXTRACTINATOR)
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
    public IStructureDefinition<SteamExtractinator> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<SteamExtractinator>builder()
                .addShape(
                    STRUCTURE_PIECE_MAIN,
                    (transpose(
                        new String[][] {
                            { "               ", "               ", "       AA   AA ", "       A     A ",
                                "               ", "               ", "               ", "               ",
                                "               ", "               ", "               ", "               ",
                                "               ", "       A     A ", "       AA   AA ", "               ",
                                "               " },
                            { "               ", "               ", "       AAAAAAA ", "       A     A ",
                                "       A     A ", "       A     A ", "       A     A ", "       A     A ",
                                "       A     A ", "       A     A ", "EEE    A     A ", "EEE    A     A ",
                                "EEE    A     A ", "       A     A ", "       AAAAAAA ", "               ",
                                "               " },
                            { "          G    ", "               ", "        HHHHH  ", "       H     H ",
                                "       H     H ", "       D     D ", "       H     H ", "       H     H ",
                                "       H     H ", "       H     H ", "KKK    H     H ", "KCCCCCCD     D ",
                                "KKK    H     H ", "       H     H ", "        HHHHH  ", "               ",
                                "          G    " },
                            { "          B    ", "         BBB   ", "        ABBBA  ", "       AIIIIIA ",
                                "      DDIIIIIDD", "      DDIIIIIDD", "      DDIIIIIDD", "       AIIIIIA ",
                                "       AIIIIIA ", "       AIIIIIA ", "HHH   DDIIIIIDD", "HCH   DDIIIIIDD",
                                "HHH   DDIIIIIDD", "       AIIIIIA ", "        ABBBA  ", "         BBB   ",
                                "          B    " },
                            { "          G    ", "               ", "        HHHHH  ", "       H     H ",
                                "      GH     HG", "       D     D ", "      GH     HG", "       H     H ",
                                "       H     H ", "       H     H ", "MMM   GH     HG", "MCCCCCCD     D ",
                                "MMM   GH     HG", "       H     H ", "        HHHHH  ", "               ",
                                "          G    " },
                            { "          G    ", "               ", "       HGGGGGH ", "       H     H ",
                                "      GH     HG", "       H     H ", "      G       G", "               ",
                                "               ", "               ", "HHH   G       G", "HCH    H     H ",
                                "HHH   GH     HG", "       H     H ", "       HGGGGGH ", "               ",
                                "          G    " },
                            { "          G    ", "               ", "        HHHHH  ", "       H     H ",
                                "      GH     HG", "       D     D ", "      GH     HG", "       H     H ",
                                "       H     H ", "       H     H ", "MMM   GH     HG", "MCCCCCCD     D ",
                                "MMM   GH     HG", "       H     H ", "        HHHHH  ", "               ",
                                "          G    " },
                            { "          B    ", "         BBB   ", "        ABBBA  ", "       AIIIIIA ",
                                "      DDIIIIIDD", "      DDIIIIIDD", "      DDIIIIIDD", "       AIIIIIA ",
                                "       AIIIIIA ", "       AIIIIIA ", "HHH   DDIIIIIDD", "HCC   DDIIIIIDD",
                                "HHH   DDIIIIIDD", "       AIIIIIA ", "        ABBBA  ", "         BBB   ",
                                "          B    " },
                            { "          G    ", "               ", "        HHHHH  ", "       H     H ",
                                "       H     H ", "       D     D ", "       H     H ", "       H     H ",
                                "       H     H ", "       H     H ", "L~L    H     H ", "LCCCCCCD     D ",
                                "LLL    H     H ", "       H     H ", "        HHHHH  ", "               ",
                                "          G    " },
                            { "          G    ", "               ", "       JJJJJJJ ", "       JJJJJJJ ",
                                "       JJJJJJJ ", "       JJJJJJJ ", "       JJJJJJJ ", "       JJJJJJJ ",
                                "       JJJJJJJ ", "       JJJJJJJ ", "EEE    JJJJJJJ ", "EEE    JJJJJJJ ",
                                "EEE    JJJJJJJ ", "       JJJJJJJ ", "       JJJJJJJ ", "               ",
                                "          G    " } })))
                .addElement('A', ofBlock(GregTechAPI.sBlockCasings2, 0))
                .addElement('B', ofBlock(GregTechAPI.sBlockCasings2, 3))
                .addElement('C', ofBlock(GregTechAPI.sBlockCasings2, 12))
                .addElement('D', ofBlock(GregTechAPI.sBlockCasings2, 13))
                .addElement('E', ofBlock(GregTechAPI.sBlockCasings3, 13))
                .addElement('G', ofFrame(Materials.Steel))
                .addElement('H', ofBlock(MetaCasing02, 0))
                .addElement('I', ofBlock(MetaBlockGlass, 3))
                .addElement('J', ofBlock(MetaBlockColumn, 1))
                .addElement(
                    'K',
                    ofChain(
                        buildHatchAdder(SteamExtractinator.class).atLeast(InputHatch)
                            .casingIndex(10)
                            .dot(1)
                            .buildAndChain(),
                        ofBlock(GregTechAPI.sBlockCasings1, 10)))
                .addElement(
                    'L',
                    ofChain(
                        buildHatchAdder(SteamExtractinator.class).atLeast(SteamHatchElement.OutputBus_Steam)
                            .casingIndex(10)
                            .dot(2)
                            .buildAndChain(),
                        ofBlock(GregTechAPI.sBlockCasings1, 10)))
                .addElement(
                    'M',
                    ofChain(
                        buildSteamInput(SteamExtractinator.class).casingIndex(10)
                            .dot(3)
                            .build(),
                        ofBlock(GregTechAPI.sBlockCasings1, 10)))
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
        if (checkHatches()) {
            return true;
        }
        return false;
    }

    @Override
    public int getTierRecipes() {
        return 14;
    }

    @Override
    public int getMaxParallelRecipes() {
        return 4;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeRegister.SteamExtractinatorRecipes;
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @Nonnull
            @Override
            protected CheckRecipeResult validateRecipe(@Nonnull GTRecipe recipe) {
                if (availableVoltage < recipe.mEUt) {
                    return CheckRecipeResultRegistry.insufficientPower(recipe.mEUt);
                }
                return CheckRecipeResultRegistry.SUCCESSFUL;
            }

            // note that a basic steam machine has .setEUtDiscount(2F).setSpeedBoost(2F). So these here are bonuses.
            @Override
            @Nonnull
            protected OverclockCalculator createOverclockCalculator(@NotNull GTRecipe recipe) {
                return OverclockCalculator.ofNoOverclock(recipe)
                    .setSpeedBoost(1f);
            }
        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(getMachineType())
            .addInfo("Uses " + EnumChatFormatting.GOLD + "Superheated Steam")
            .addInfo("Vaporizes impurities in different soil slurries to generate usable materials")
            .addInfo("Processes up to 4 recipes at once")
            .addInfo(
                EnumChatFormatting.AQUA + ""
                    + EnumChatFormatting.ITALIC
                    + "From Steam to rocks, the power of the pressure may bring you infinite wealth!.")
            .addInputHatch("Top Layer of Bronze Casings")
            .addOutputHatch("Bottom Layer of Bronze Casings")
            .addEnergyHatch("2 Middle Bronze Casing Layers")
            .toolTipFinisher();
        return tt;
    }

    @Override
    public String[] getInfoData() {
        ArrayList<String> info = new ArrayList<>(Arrays.asList(super.getInfoData()));
        info.add("Parallel: " + EnumChatFormatting.YELLOW + getMaxParallelRecipes());
        return info.toArray(new String[0]);
    }

    @Override
    public void getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        super.getWailaBody(itemStack, currenttip, accessor, config);
        NBTTagCompound tag = accessor.getNBTData();
        currenttip.add(
            StatCollector.translateToLocal("GT5U.multiblock.curparallelism") + ": "
                + EnumChatFormatting.BLUE
                + tag.getInteger("parallel")
                + EnumChatFormatting.RESET);
    }

    @Override
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y,
        int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        tag.setInteger("parallel", getMaxParallelRecipes());
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
    }

    @Override
    public void loadNBTData(final NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected SoundResource getActivitySoundLoop() {
        return SoundResource.IC2_MACHINES_ELECTROFURNACE_LOOP;
    }
}
