package com.science.gtnl.common.machine.multiblock.AprilFool;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofChain;
import static com.science.gtnl.common.block.Casings.BasicBlocks.MetaCasing;
import static gregtech.api.enums.GTValues.V;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTStructureUtility.ofFrame;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.NotNull;

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
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;

public class SteamManufacturer extends SteamMultiMachineBase<SteamManufacturer> implements ISurvivalConstructable {

    public static final String TEXTURE_OVERLAY_MANUFACTURER = "sciencenotleisure:iconsets/OVERLAY_MANUFACTURER";
    public static Textures.BlockIcons.CustomIcon OVERLAY_MANUFACTURER = new Textures.BlockIcons.CustomIcon(
        TEXTURE_OVERLAY_MANUFACTURER);
    public static final String TEXTURE_OVERLAY_MANUFACTURER_ACTIVE = "sciencenotleisure:iconsets/OVERLAY_MANUFACTURER_ACTIVE";
    public static Textures.BlockIcons.CustomIcon OVERLAY_MANUFACTURER_ACTIVE = new Textures.BlockIcons.CustomIcon(
        TEXTURE_OVERLAY_MANUFACTURER_ACTIVE);

    public SteamManufacturer(String aName) {
        super(aName);
    }

    public SteamManufacturer(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    @Override
    public String getMachineType() {
        return "Assembler";
    }

    private static final String STRUCTURE_PIECE_MAIN = "main";

    @Override
    public IStructureDefinition<SteamManufacturer> getStructureDefinition() {
        return StructureDefinition.<SteamManufacturer>builder()
            .addShape(
                STRUCTURE_PIECE_MAIN,
                (new String[][] {
                    { "         ", "         ", "         ", "         ", " AAAAA   ", " BB~BB   ", " AAAAA   " },
                    { "         ", "         ", "         ", "         ", "A     A  ", "B     B  ", "AAAAAAA  " },
                    { "       D ", "     DDED", "       D ", "       D ", "A     AD ", "B CCC CD ", "AAAAAAAAA" },
                    { "    DDDAD", "   EEEEEA", "   E   CA", "   E   CA", "A     ACA", "B C CCCCA", "AAAAAAAAA" },
                    { "       D ", "     DDED", "       D ", "       D ", "A     AD ", "B CCC CD ", "AAAAAAAAA" },
                    { "         ", "         ", "         ", "         ", "A     A  ", "B     B  ", "AAAAAAA  " },
                    { "         ", "         ", "         ", "         ", " AAAAA   ", " BBBBB   ", " AAAAA   " } }))
            .addElement(
                'A',
                ofChain(
                    buildSteamInput(SteamManufacturer.class).casingIndex(GTUtility.getTextureId((byte) 116, (byte) 30))
                        .dot(1)
                        .build(),
                    buildHatchAdder(SteamManufacturer.class)
                        .atLeast(SteamHatchElement.InputBus_Steam, SteamHatchElement.OutputBus_Steam)
                        .casingIndex(GTUtility.getTextureId((byte) 116, (byte) 30))
                        .dot(1)
                        .buildAndChain(),
                    ofBlock(MetaCasing, 30)))
            .addElement('B', ofBlock(GregTechAPI.sBlockCasings2, 3))
            .addElement('D', ofBlock(MetaCasing, 26))
            .addElement('C', ofFrame(Materials.Steel))
            .addElement('E', ofBlock(MetaCasing, 28))
            .build();
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, 3, 5, 0);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (mMachine) return -1;
        return survivialBuildPiece(STRUCTURE_PIECE_MAIN, stackSize, 3, 5, 0, elementBudget, env, false, true);
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeRegister.SteamManufacturerRecipes;
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
    protected MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(getMachineType())
            .addInfo("Uses " + EnumChatFormatting.GOLD + "Superheated Steam")
            .addInfo("Has 4 Parallels")
            .addInfo("Assembles assemblies assemblically")
            .addInfo(EnumChatFormatting.AQUA + "" + EnumChatFormatting.ITALIC + "Slave labor? Not in my GTNH!")
            .toolTipFinisher();
        return tt;
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @NotNull
            @Override
            protected OverclockCalculator createOverclockCalculator(@NotNull GTRecipe recipe) {
                return super.createOverclockCalculator(recipe).setNoOverclock(true);
            }
        }.setMaxParallelSupplier(this::getMaxParallelRecipes);
    }

    @Override
    protected void setProcessingLogicPower(ProcessingLogic logic) {
        logic.setAvailableVoltage(V[9]);
        // We need to trick the GT_ParallelHelper we have enough amps for all recipe parallels.
        logic.setAvailableAmperage(getMaxParallelRecipes());
        logic.setAmperageOC(false);
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
                        .addIcon(OVERLAY_MANUFACTURER_ACTIVE)
                        .extFacing()
                        .build() };
            } else {
                rTexture = new ITexture[] {
                    Textures.BlockIcons
                        .getCasingTextureForId(GTUtility.getCasingTextureIndex(GregTechAPI.sBlockCasings2, 0)),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_MANUFACTURER)
                        .extFacing()
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
        return checkPiece(STRUCTURE_PIECE_MAIN, 3, 5, 0);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new SteamManufacturer(this.mName);
    }
}
