package com.science.gtnl.common.machine.multiblock.AprilFool;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlockAnyMeta;
import static com.science.gtnl.common.block.Casings.BasicBlocks.MetaCasing;
import static gregtech.api.enums.HatchElement.OutputHatch;
import static gregtech.api.multitileentity.multiblock.casing.Glasses.chainAllGlasses;
import static gregtech.api.util.GTStructureUtility.*;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.common.machine.multiMachineClasses.SteamMultiMachineBase;
import com.science.gtnl.common.recipe.RecipeRegister;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;

public class SteamLavaMaker extends SteamMultiMachineBase<SteamLavaMaker> implements ISurvivalConstructable {

    public static final String TEXTURE_OVERLAY_LAVAMAKER = "sciencenotleisure:iconsets/OVERLAY_LAVAMAKER";
    public static final String TEXTURE_OVERLAY_LAVAMAKER_ACTIVE = "sciencenotleisure:iconsets/OVERLAY_LAVAMAKER_ACTIVE";
    public static Textures.BlockIcons.CustomIcon OVERLAY_LAVAMAKER = new Textures.BlockIcons.CustomIcon(
        TEXTURE_OVERLAY_LAVAMAKER);
    public static Textures.BlockIcons.CustomIcon OVERLAY_LAVAMAKER_ACTIVE = new Textures.BlockIcons.CustomIcon(
        TEXTURE_OVERLAY_LAVAMAKER_ACTIVE);

    public SteamLavaMaker(String aName) {
        super(aName);
    }

    public SteamLavaMaker(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(getMachineType())
            .addInfo("Uses " + EnumChatFormatting.GOLD + "Superheated Steam")
            .addInfo("Can melt up to 4 stones at a time")
            .addInfo(EnumChatFormatting.AQUA + "" + EnumChatFormatting.ITALIC + "Turning up the heat")
            .addInfo("Author: " + "AuthorSteamIsTheNumber")
            .toolTipFinisher();
        return tt;
    }

    @Override
    public String getMachineType() {
        return "Superheater";
    }

    private static final String STRUCTURE_PIECE_MAIN = "main";

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, 1, 4, 0);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (mMachine) return -1;
        return survivialBuildPiece(STRUCTURE_PIECE_MAIN, stackSize, 1, 4, 0, elementBudget, env, false, true);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
        int aColorIndex, boolean aActive, boolean aRedstone) {
        ITexture[] rTexture;
        if (side == facing) {
            if (aActive) {
                rTexture = new ITexture[] {
                    Textures.BlockIcons.getCasingTextureForId(GTUtility.getTextureId((byte) 116, (byte) 27)),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_LAVAMAKER_ACTIVE)
                        .extFacing()
                        .build(),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_LAVAMAKER_ACTIVE)
                        .extFacing()
                        .glow()
                        .build() };
            } else {
                rTexture = new ITexture[] {
                    Textures.BlockIcons.getCasingTextureForId(GTUtility.getTextureId((byte) 116, (byte) 27)),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_LAVAMAKER)
                        .extFacing()
                        .build(),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_LAVAMAKER)
                        .extFacing()
                        .glow()
                        .build() };
            }
        } else {
            rTexture = new ITexture[] {
                Textures.BlockIcons.getCasingTextureForId(GTUtility.getCasingTextureIndex(MetaCasing, 27)) };
        }
        return rTexture;
    }

    // spotless:off
    private static final IStructureDefinition<SteamLavaMaker> STRUCTURE_DEFINITION = StructureDefinition
        .<SteamLavaMaker>builder()
        .addShape(
            STRUCTURE_PIECE_MAIN,
            (new String[][]{{
                "BBB",
                "BAB",
                "BAB",
                "BAB",
                "B~B"
            },{
                "BBB",
                "ALA",
                "ALA",
                "ALA",
                "BBB"
            },{
                "BBB",
                "BAB",
                "BAB",
                "BAB",
                "BBB"
            }}))
        .addElement('B', ofChain(
            buildSteamInput(SteamLavaMaker.class)
                .casingIndex(GTUtility.getTextureId((byte) 116, (byte) 27))
                .dot(1)
                .build(),
            buildHatchAdder(SteamLavaMaker.class)
                .atLeast(SteamHatchElement.InputBus_Steam, OutputHatch)
                .casingIndex(GTUtility.getTextureId((byte) 116, (byte) 27))
                .dot(1)
                .buildAndChain(),
            ofBlock(MetaCasing, 27)))
        .addElement('A', chainAllGlasses())
        .addElement('L', ofChain(ofBlockAnyMeta(Blocks.lava), ofBlockAnyMeta(Blocks.flowing_lava)))
        .build();
    //spotless:on

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeRegister.LavaMakerRecipes;
    }

    @Override
    public IStructureDefinition<SteamLavaMaker> getStructureDefinition() {
        return STRUCTURE_DEFINITION;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        return checkPiece(STRUCTURE_PIECE_MAIN, 1, 4, 0);
    }

    @Override
    public int getTierRecipes() {
        return 14;
    }

    @Override
    public int getMaxParallelRecipes() {
        // Max call to prevent seeing -16 parallels in waila for unformed multi
        return 4;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new SteamLavaMaker(this.mName);
    }
}
