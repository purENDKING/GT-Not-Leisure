package com.science.gtnl.common.machine.multiblock.AprilFool;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofChain;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTStructureUtility.ofFrame;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.common.machine.multiMachineClasses.SteamMultiMachineBase;
import com.science.gtnl.common.recipe.RecipeRegister;

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

public class SteamCarpenter extends SteamMultiMachineBase<SteamCarpenter> implements ISurvivalConstructable {

    public static final String TEXTURE_OVERLAY_CARPENTER = "sciencenotleisure:iconsets/OVERLAY_CARPENTER";
    public static final String TEXTURE_OVERLAY_CARPENTER_ACTIVE = "sciencenotleisure:iconsets/OVERLAY_CARPENTER_ACTIVE";
    public static Textures.BlockIcons.CustomIcon OVERLAY_CARPENTER = new Textures.BlockIcons.CustomIcon(
        TEXTURE_OVERLAY_CARPENTER);
    public static Textures.BlockIcons.CustomIcon OVERLAY_CARPENTER_ACTIVE = new Textures.BlockIcons.CustomIcon(
        TEXTURE_OVERLAY_CARPENTER_ACTIVE);

    public SteamCarpenter(String aName) {
        super(aName);
    }

    public SteamCarpenter(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    @Override
    public String getMachineType() {
        return "Carpenter";
    }

    private static final String STRUCTURE_PIECE_MAIN = "main";

    @Override
    public IStructureDefinition<SteamCarpenter> getStructureDefinition() {
        return StructureDefinition.<SteamCarpenter>builder()
            .addShape(
                STRUCTURE_PIECE_MAIN,
                (new String[][] { { "A A", "   ", "A~A" }, { "ABA", "C C", "AAA" }, { " A ", " C ", " A " } }))
            .addElement(
                'A',
                ofChain(
                    buildSteamInput(SteamCarpenter.class).casingIndex(10)
                        .dot(1)
                        .build(),
                    buildHatchAdder(SteamCarpenter.class)
                        .atLeast(SteamHatchElement.InputBus_Steam, SteamHatchElement.OutputBus_Steam)
                        .casingIndex(10)
                        .dot(1)
                        .buildAndChain(),
                    ofBlock(GregTechAPI.sBlockCasings1, 10)))
            .addElement('B', ofBlock(GregTechAPI.sBlockCasings2, 2))
            .addElement('C', ofFrame(Materials.Wood))
            .build();
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, 1, 2, 0);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (mMachine) return -1;
        return survivialBuildPiece(STRUCTURE_PIECE_MAIN, stackSize, 1, 2, 0, elementBudget, env, false, true);
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeRegister.SteamCarpenterRecipes;
    }

    @Override
    public int getTierRecipes() {
        return 14;
    }

    @Override
    public int getMaxParallelRecipes() {
        return 1;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(getMachineType())
            .addInfo(
                EnumChatFormatting.AQUA + "" + EnumChatFormatting.ITALIC + "Cuts wood efficiently into many forms.")
            .addInfo("Created by: ")
            .addInfo("AuthorSteamIsTheNumber")
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
                        .getCasingTextureForId(GTUtility.getCasingTextureIndex(GregTechAPI.sBlockCasings1, 10)),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_CARPENTER_ACTIVE)
                        .extFacing()
                        .build() };
            } else {
                rTexture = new ITexture[] {
                    Textures.BlockIcons
                        .getCasingTextureForId(GTUtility.getCasingTextureIndex(GregTechAPI.sBlockCasings1, 10)),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_CARPENTER)
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
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        return checkPiece(STRUCTURE_PIECE_MAIN, 1, 2, 0);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new SteamCarpenter(this.mName);
    }
}
