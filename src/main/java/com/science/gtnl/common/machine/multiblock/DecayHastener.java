package com.science.gtnl.common.machine.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.GregTechAPI.*;
import static gregtech.api.enums.HatchElement.*;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTStructureUtility.ofFrame;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.Utils.StructureUtils;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.machine.multiMachineClasses.GTMMultiMachineBase;
import com.science.gtnl.common.recipe.RecipeRegister;

import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.common.blocks.BlockCasings8;
import tectech.thing.metaTileEntity.hatch.MTEHatchEnergyTunnel;

public class DecayHastener extends GTMMultiMachineBase<DecayHastener> implements ISurvivalConstructable {

    public static final String STRUCTURE_PIECE_MAIN = "main";
    private static IStructureDefinition<DecayHastener> STRUCTURE_DEFINITION = null;
    public static final String DH_STRUCTURE_FILE_PATH = "sciencenotleisure:multiblock/decay_hastener";
    protected static final int CASING_INDEX = ((BlockCasings8) sBlockCasings8).getTextureIndex(10);
    public final int horizontalOffSet = 2;
    public final int verticalOffSet = 11;
    public final int depthOffSet = 0;
    public static String[][] shape;

    public DecayHastener(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        shape = StructureUtils.readStructureFromFile(DH_STRUCTURE_FILE_PATH);
    }

    public DecayHastener(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new DecayHastener(this.mName);
    }

    private static final IIconContainer textureFontOn = new Textures.BlockIcons.CustomIcon("icons/NeutronActivator_On");
    private static final IIconContainer textureFontOff = new Textures.BlockIcons.CustomIcon(
        "icons/NeutronActivator_Off");

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(textureFontOn)
                    .extFacing()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()),
                TextureFactory.builder()
                    .addIcon(textureFontOff)
                    .extFacing()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(getCasingTextureID()) };
    }

    public int getCasingTextureID() {
        return CASING_INDEX;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeRegister.DecayHastenerRecipes;
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.DecayHastenerRecipeType)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_02)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_03)
            .addInfo(TextLocalization.Tooltip_GTMMultiMachine_04)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(5, 13, 5, true)
            .addInputBus(TextLocalization.Tooltip_DecayHastener_Casing)
            .addOutputBus(TextLocalization.Tooltip_DecayHastener_Casing)
            .addEnergyHatch(TextLocalization.Tooltip_DecayHastener_Casing)
            .addMaintenanceHatch(TextLocalization.Tooltip_DecayHastener_Casing)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IStructureDefinition<DecayHastener> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<DecayHastener>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shape))
                .addElement('A', ofBlock(sBlockCasings1, 15))
                .addElement('B', ofBlock(sBlockCasings10, 3))
                .addElement('C', ofBlock(sBlockCasings4, 6))
                .addElement('D', ofBlock(sBlockCasings6, 8))
                .addElement(
                    'E',
                    buildHatchAdder(DecayHastener.class).casingIndex(CASING_INDEX)
                        .dot(1)
                        .atLeast(InputBus, OutputBus, InputHatch, OutputHatch, Maintenance, Energy.or(ExoticEnergy))
                        .buildAndChain(onElementPass(x -> ++x.mCasing, ofBlock(sBlockCasings8, 10))))
                .addElement('F', ofFrame(Materials.BlackSteel))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        mCasing = 0;
        ParallelTier = 0;

        if (!checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet)) {
            return false;
        }

        if (!checkHatch()) {
            return false;
        }

        for (MTEHatch hatch : getExoticEnergyHatches()) {
            if (hatch instanceof MTEHatchEnergyTunnel) {
                return false;
            }
        }

        ParallelTier = getParallelTier(aStack);

        return mCasing >= 80;
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
