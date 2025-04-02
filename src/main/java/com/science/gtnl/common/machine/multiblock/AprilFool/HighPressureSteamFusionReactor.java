package com.science.gtnl.common.machine.multiblock.AprilFool;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static com.science.gtnl.common.block.Casings.BasicBlocks.MetaCasing;
import static com.science.gtnl.common.block.Casings.BasicBlocks.MetaCasing02;
import static gregtech.api.enums.HatchElement.InputHatch;
import static gregtech.api.enums.HatchElement.OutputHatch;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_TOP_STEAM_MACERATOR;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_TOP_STEAM_MACERATOR_ACTIVE;
import static gregtech.api.multitileentity.multiblock.casing.Glasses.chainAllGlasses;
import static gregtech.api.util.GTStructureUtility.*;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.science.gtnl.common.machine.multiMachineClasses.SteamMultiMachineBase;
import com.science.gtnl.common.recipe.RecipeRegister;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Materials;
import gregtech.api.enums.SoundResource;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;

public class HighPressureSteamFusionReactor extends SteamMultiMachineBase<HighPressureSteamFusionReactor>
    implements ISurvivalConstructable {

    public HighPressureSteamFusionReactor(String aName) {
        super(aName);
    }

    public HighPressureSteamFusionReactor(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    @Override
    public String getMachineType() {
        return "High Pressure Reactor";
    }

    private static final String STRUCTURE_PIECE_MAIN = "main";

    @Override
    public IStructureDefinition<HighPressureSteamFusionReactor> getStructureDefinition() {
        return StructureDefinition.<HighPressureSteamFusionReactor>builder()
            .addShape(
                STRUCTURE_PIECE_MAIN,
                transpose(
                    (new String[][] {
                        { "                                               ",
                            "                                               ",
                            "                    ECCCCCE                    ",
                            "                    ECAAACE                    ",
                            "                    ECCCCCE                    ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "  EEE                                     EEE  ",
                            "  CCC                                     CCC  ",
                            "  CAC                                     CAC  ",
                            "  CAC                                     CAC  ",
                            "  CAC                                     CAC  ",
                            "  CCC                                     CCC  ",
                            "  EEE                                     EEE  ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                    ECCCCCE                    ",
                            "                    ECAAACE                    ",
                            "                    ECCCCCE                    ",
                            "                                               ",
                            "                                               " },
                        { "                                               ",
                            "                    ECAAACE                    ",
                            "                   CC     CC                   ",
                            "                CCCCC     CCCCC                ",
                            "              CCCCCCC     CCCCCCC              ",
                            "            CCCCCCC ECAAACE CCCCCCC            ",
                            "           CCCCC               CCCCC           ",
                            "          CCCC                   CCCC          ",
                            "         CCC                       CCC         ",
                            "        CCC                         CCC        ",
                            "       CCC                           CCC       ",
                            "      CCC                             CCC      ",
                            "     CCC                               CCC     ",
                            "     CCC                               CCC     ",
                            "    CCC                                 CCC    ",
                            "    CCC                                 CCC    ",
                            "   CCC                                   CCC   ",
                            "   CCC                                   CCC   ",
                            "   CCC                                   CCC   ",
                            "  CCC                                     CCC  ",
                            " ECCCE                                   ECCCE ",
                            " C   C                                   C   C ",
                            " A   A                                   A   A ",
                            " A   A                                   A   A ",
                            " A   A                                   A   A ",
                            " C   C                                   C   C ",
                            " ECCCE                                   ECCCE ",
                            "  CCC                                     CCC  ",
                            "   CCC                                   CCC   ",
                            "   CCC                                   CCC   ",
                            "   CCC                                   CCC   ",
                            "    CCC                                 CCC    ",
                            "    CCC                                 CCC    ",
                            "     CCC                               CCC     ",
                            "     CCC                               CCC     ",
                            "      CCC                             CCC      ",
                            "       CCC                           CCC       ",
                            "        CCC                         CCC        ",
                            "         CCC                       CCC         ",
                            "          CCCC                   CCCC          ",
                            "           CCCCC               CCCCC           ",
                            "            CCCCCCC ECAAACE CCCCCCC            ",
                            "              CCCCCCC     CCCCCCC              ",
                            "                CCCCC     CCCCC                ",
                            "                   CC     CC                   ",
                            "                    ECAAACE                    ",
                            "                                               " },
                        { "                    ECCCCCE                    ",
                            "                   CC     CC                   ",
                            "                CCCCC     CCCCC                ",
                            "              CCCCCBBBBBBBBBCCCCC              ",
                            "            CCCCBBBCC     CCBBBCCCC            ",
                            "           CCCBBCCCCC     CCCCCBBCCC           ",
                            "          CCBBCCCCC ECCCCCE CCCCCBBCC          ",
                            "         CCBCCCC               CCCCBCC         ",
                            "        CCBCCC                   CCCBCC        ",
                            "       CCBCC                       CCBCC       ",
                            "      CCBCC                         CCBCC      ",
                            "     CCBCC                           CCBCC     ",
                            "    CCBCC                             CCBCC    ",
                            "    CCBCC                             CCBCC    ",
                            "   CCBCC                               CCBCC   ",
                            "   CCBCC                               CCBCC   ",
                            "  CCBCC                                 CCBCC  ",
                            "  CCBCC                                 CCBCC  ",
                            "  CCBCC                                 CCBCC  ",
                            " CCBCC                                   CCBCC ",
                            "ECCBCCE                                 ECCBCCE",
                            "C  B  C                                 C  B  C",
                            "C  B  C                                 C  B  C",
                            "C  B  C                                 C  B  C",
                            "C  B  C                                 C  B  C",
                            "C  B  C                                 C  B  C",
                            "ECCBCCE                                 ECCBCCE",
                            " CCBCC                                   CCBCC ",
                            "  CCBCC                                 CCBCC  ",
                            "  CCBCC                                 CCBCC  ",
                            "  CCBCC                                 CCBCC  ",
                            "   CCBCC                               CCBCC   ",
                            "   CCBCC                               CCBCC   ",
                            "    CCBCC                             CCBCC    ",
                            "    CCBCC                             CCBCC    ",
                            "     CCBCC                           CCBCC     ",
                            "      CCBCC                         CCBCC      ",
                            "       CCBCC                       CCBCC       ",
                            "        CCBCCC                   CCCBCC        ",
                            "         CCBCCCC               CCCCBCC         ",
                            "          CCBBCCCCC ECCCCCE CCCCCBBCC          ",
                            "           CCCBBCCCCC     CCCCCBBCCC           ",
                            "            CCCCBBBCC     CCBBBCCCC            ",
                            "              CCCCCBBBBBBBBBCCCCC              ",
                            "                CCCCC     CCCCC                ",
                            "                   CC     CC                   ",
                            "                    ECCCCCE                    " },
                        { "                    ECAAACE                    ",
                            "                   CC     CC                   ",
                            "                CCCBBBBBBBBBCCC                ",
                            "              CCBBBBBBBBBBBBBBBCC              ",
                            "            CCBBBBBBBBBBBBBBBBBBBCC            ",
                            "           CBBBBBBBCC     CCBBBBBBBC           ",
                            "          CBBBBBCCC ECDADCE CCCBBBBBC          ",
                            "         CBBBBCC               CCBBBBC         ",
                            "        CBBBCC                   CCBBBC        ",
                            "       CBBBC                       CBBBC       ",
                            "      CBBBC                         CBBBC      ",
                            "     CBBBC                           CBBBC     ",
                            "    CBBBC                             CBBBC    ",
                            "    CBBBC                             CBBBC    ",
                            "   CBBBC                               CBBBC   ",
                            "   CBBBC                               CBBBC   ",
                            "  CBBBC                                 CBBBC  ",
                            "  CBBBC                                 CBBBC  ",
                            "  CBBBC                                 CBBBC  ",
                            " CBBBC                                   CBBBC ",
                            "ECBBBCE                                 ECBBBCE",
                            "C BBB C                                 C BBB C",
                            "A BBB D                                 D BBB A",
                            "A BBB A                                 A BBB A",
                            "A BBB D                                 D BBB A",
                            "C BBB C                                 C BBB C",
                            "ECBBBCE                                 ECBBBCE",
                            " CBBBC                                   CBBBC ",
                            "  CBBBC                                 CBBBC  ",
                            "  CBBBC                                 CBBBC  ",
                            "  CBBBC                                 CBBBC  ",
                            "   CBBBC                               CBBBC   ",
                            "   CBBBC                               CBBBC   ",
                            "    CBBBC                             CBBBC    ",
                            "    CBBBC                             CBBBC    ",
                            "     CBBBC                           CBBBC     ",
                            "      CBBBC                         CBBBC      ",
                            "       CBBBC                       CBBBC       ",
                            "        CBBBCC                   CCBBBC        ",
                            "         CBBBBCC               CCBBBBC         ",
                            "          CBBBBBCCC ECD~DCE CCCBBBBBC          ",
                            "           CBBBBBBBCC     CCBBBBBBBC           ",
                            "            CCBBBBBBBBBBBBBBBBBBBCC            ",
                            "              CCBBBBBBBBBBBBBBBCC              ",
                            "                CCCBBBBBBBBBCCC                ",
                            "                   CC     CC                   ",
                            "                    ECAAACE                    " },
                        { "                    ECCCCCE                    ",
                            "                   CC     CC                   ",
                            "                CCCCC     CCCCC                ",
                            "              CCCCCBBBBBBBBBCCCCC              ",
                            "            CCCCBBBCC     CCBBBCCCC            ",
                            "           CCCBBCCCCC     CCCCCBBCCC           ",
                            "          CCBBCCCCC ECCCCCE CCCCCBBCC          ",
                            "         CCBCCCC               CCCCBCC         ",
                            "        CCBCCC                   CCCBCC        ",
                            "       CCBCC                       CCBCC       ",
                            "      CCBCC                         CCBCC      ",
                            "     CCBCC                           CCBCC     ",
                            "    CCBCC                             CCBCC    ",
                            "    CCBCC                             CCBCC    ",
                            "   CCBCC                               CCBCC   ",
                            "   CCBCC                               CCBCC   ",
                            "  CCBCC                                 CCBCC  ",
                            "  CCBCC                                 CCBCC  ",
                            "  CCBCC                                 CCBCC  ",
                            " CCBCC                                   CCBCC ",
                            "ECCBCCE                                 ECCBCCE",
                            "C  B  C                                 C  B  C",
                            "C  B  C                                 C  B  C",
                            "C  B  C                                 C  B  C",
                            "C  B  C                                 C  B  C",
                            "C  B  C                                 C  B  C",
                            "ECCBCCE                                 ECCBCCE",
                            " CCBCC                                   CCBCC ",
                            "  CCBCC                                 CCBCC  ",
                            "  CCBCC                                 CCBCC  ",
                            "  CCBCC                                 CCBCC  ",
                            "   CCBCC                               CCBCC   ",
                            "   CCBCC                               CCBCC   ",
                            "    CCBCC                             CCBCC    ",
                            "    CCBCC                             CCBCC    ",
                            "     CCBCC                           CCBCC     ",
                            "      CCBCC                         CCBCC      ",
                            "       CCBCC                       CCBCC       ",
                            "        CCBCCC                   CCCBCC        ",
                            "         CCBCCCC               CCCCBCC         ",
                            "          CCBBCCCCC ECCCCCE CCCCCBBCC          ",
                            "           CCCBBCCCCC     CCCCCBBCCC           ",
                            "            CCCCBBBCC     CCBBBCCCC            ",
                            "              CCCCCBBBBBBBBBCCCCC              ",
                            "                CCCCC     CCCCC                ",
                            "                   CC     CC                   ",
                            "                    ECCCCCE                    " },
                        { "                                               ",
                            "                    ECAAACE                    ",
                            "                   CC     CC                   ",
                            "                CCCCC     CCCCC                ",
                            "              CCCCCCC     CCCCCCC              ",
                            "            CCCCCCC ECAAACE CCCCCCC            ",
                            "           CCCCC               CCCCC           ",
                            "          CCCC                   CCCC          ",
                            "         CCC                       CCC         ",
                            "        CCC                         CCC        ",
                            "       CCC                           CCC       ",
                            "      CCC                             CCC      ",
                            "     CCC                               CCC     ",
                            "     CCC                               CCC     ",
                            "    CCC                                 CCC    ",
                            "    CCC                                 CCC    ",
                            "   CCC                                   CCC   ",
                            "   CCC                                   CCC   ",
                            "   CCC                                   CCC   ",
                            "  CCC                                     CCC  ",
                            " ECCCE                                   ECCCE ",
                            " C   C                                   C   C ",
                            " A   A                                   A   A ",
                            " A   A                                   A   A ",
                            " A   A                                   A   A ",
                            " C   C                                   C   C ",
                            " ECCCE                                   ECCCE ",
                            "  CCC                                     CCC  ",
                            "   CCC                                   CCC   ",
                            "   CCC                                   CCC   ",
                            "   CCC                                   CCC   ",
                            "    CCC                                 CCC    ",
                            "    CCC                                 CCC    ",
                            "     CCC                               CCC     ",
                            "     CCC                               CCC     ",
                            "      CCC                             CCC      ",
                            "       CCC                           CCC       ",
                            "        CCC                         CCC        ",
                            "         CCC                       CCC         ",
                            "          CCCC                   CCCC          ",
                            "           CCCCC               CCCCC           ",
                            "            CCCCCCC ECAAACE CCCCCCC            ",
                            "              CCCCCCC     CCCCCCC              ",
                            "                CCCCC     CCCCC                ",
                            "                   CC     CC                   ",
                            "                    ECAAACE                    ",
                            "                                               " },
                        { "                                               ",
                            "                                               ",
                            "                    ECCCCCE                    ",
                            "                    ECAAACE                    ",
                            "                    ECCCCCE                    ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "  EEE                                     EEE  ",
                            "  CCC                                     CCC  ",
                            "  CAC                                     CAC  ",
                            "  CAC                                     CAC  ",
                            "  CAC                                     CAC  ",
                            "  CCC                                     CCC  ",
                            "  EEE                                     EEE  ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                                               ",
                            "                    ECCCCCE                    ",
                            "                    ECAAACE                    ",
                            "                    ECCCCCE                    ",
                            "                                               ",
                            "                                               " } })))
            .addElement(
                'D',
                ofChain(
                    buildSteamInput(HighPressureSteamFusionReactor.class)
                        .casingIndex(GTUtility.getTextureId((byte) 116, (byte) 32))
                        .dot(1)
                        .build(),
                    buildHatchAdder(HighPressureSteamFusionReactor.class).atLeast(InputHatch, OutputHatch)
                        .casingIndex(GTUtility.getTextureId((byte) 116, (byte) 32))
                        .dot(1)
                        .buildAndChain(),
                    chainAllGlasses()))
            .addElement('B', ofBlock(MetaCasing, 31))
            .addElement('A', chainAllGlasses())
            .addElement('C', ofBlock(MetaCasing02, 0))
            .addElement('E', ofFrame(Materials.Steel))
            .build();
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, 23, 3, 40);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (mMachine) return -1;
        return survivialBuildPiece(STRUCTURE_PIECE_MAIN, stackSize, 23, 3, 40, elementBudget, env, false, true);
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeRegister.SteamFusionReactorRecipes;
    }

    @Override
    public boolean onRunningTick(ItemStack aStack) {
        doActivitySound(getActivitySoundLoop());
        return super.onRunningTick(aStack);
    }

    @Override
    public int getTierRecipes() {
        return 14;
    }

    @Override
    public int getMaxParallelRecipes() {
        // Max call to prevent seeing -16 parallels in waila for unformed multi
        return 256;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected SoundResource getActivitySoundLoop() {
        return SoundResource.GT_MACHINES_FUSION_LOOP;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(getMachineType())
            .addInfo("An Amalgamation of Breel and Steam, never envisioned before")
            .addInfo("For All the pressure needs you may have")
            .addInfo("Can do up to 256 recipes at once")
            .addInfo(EnumChatFormatting.AQUA + "" + EnumChatFormatting.ITALIC + "What in the world have you done")
            .addCasingInfoMin("Vibration Safe Casing", 1664, false)
            .addCasingInfoMin("Steam Compact Pipe Casing", 560, false)
            .addCasingInfoMin("Compressed Steam Frame Box", 128, false)
            .addCasingInfoMin("Reinforced Glass", 63, false)
            .addInputHatch("1-8, Internal Glass Blocks", 1)
            .addOutputHatch("1-8, Internal Glass Blocks", 1)
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
                    Textures.BlockIcons.getCasingTextureForId(GTUtility.getTextureId((byte) 116, (byte) 32)),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_TOP_STEAM_MACERATOR_ACTIVE)
                        .extFacing()
                        .build() };
            } else {
                rTexture = new ITexture[] {
                    Textures.BlockIcons.getCasingTextureForId(GTUtility.getTextureId((byte) 116, (byte) 32)),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_TOP_STEAM_MACERATOR)
                        .extFacing()
                        .build() };
            }
        } else {
            rTexture = new ITexture[] {
                Textures.BlockIcons.getCasingTextureForId(GTUtility.getTextureId((byte) 116, (byte) 32)) };
        }
        return rTexture;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        return checkPiece(STRUCTURE_PIECE_MAIN, 23, 3, 40);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new HighPressureSteamFusionReactor(this.mName);
    }
}
