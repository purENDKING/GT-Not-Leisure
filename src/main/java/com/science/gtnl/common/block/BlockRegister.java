package com.science.gtnl.common.block;

import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.RESET;
import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.addItemTooltip;
import static com.science.gtnl.common.block.Casings.BasicBlocks.*;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import com.science.gtnl.Utils.AnimatedText;
import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.common.block.Casings.Base.ItemBlockBase;
import com.science.gtnl.common.block.Casings.BasicBlocks;
import com.science.gtnl.common.block.Casings.Casing.MetaBlockConstructors;
import com.science.gtnl.common.block.Casings.Casing.MetaItemBlockCasing;
import com.science.gtnl.common.block.Casings.Column.ItemBlockColumn;
import com.science.gtnl.common.block.Casings.Glass.ItemBlockGlass;
import com.science.gtnl.common.block.Casings.Glow.ItemBlockGlow;
import com.science.gtnl.common.block.Casings.Special.BlocksStargate;
import com.science.gtnl.common.block.Casings.Special.StargateMetaBlockBase;
import com.science.gtnl.common.block.ReAvaritia.BlockSoulFarmland;
import com.science.gtnl.common.block.ReAvaritia.ExtremeAnvil.BlockExtremeAnvil;
import com.science.gtnl.common.block.ReAvaritia.ExtremeAnvil.TileEntityExtremeAnvil;
import com.science.gtnl.common.block.ReAvaritia.NeutronCollector.DenseNeutronCollector;
import com.science.gtnl.common.block.ReAvaritia.NeutronCollector.DenserNeutronCollector;
import com.science.gtnl.common.block.ReAvaritia.NeutronCollector.DensestNeutronCollector;
import com.science.gtnl.common.block.ReAvaritia.NeutronCollector.NeutronCollector;
import com.science.gtnl.common.block.ReAvaritia.NeutronCollector.TileEntityNeutronCollector;
import com.science.gtnl.common.block.blocks.artificialStar.BlockArtificialStarRender;
import com.science.gtnl.common.block.blocks.artificialStar.TileEntityArtificialStar;
import com.science.gtnl.common.block.blocks.laserBeacon.BlockLaserBeacon;
import com.science.gtnl.common.block.blocks.laserBeacon.TileEntityLaserBeacon;
import com.science.gtnl.common.block.blocks.playerDoll.ItemPlayerDoll;
import com.science.gtnl.common.block.blocks.playerDoll.PlayerDoll;
import com.science.gtnl.common.block.blocks.playerDoll.TileEntityPlayerDoll;

import cpw.mods.fml.common.registry.GameRegistry;
import gtPlusPlus.core.item.base.itemblock.ItemBlockMeta;

public class BlockRegister {

    public static Block StargateTier0 = new BlocksStargate(0);
    public static Block StargateTier1 = new BlocksStargate(1);
    public static Block StargateTier2 = new BlocksStargate(2);
    public static Block StargateTier3 = new BlocksStargate(3);
    public static Block StargateTier4 = new BlocksStargate(4);
    public static Block StargateTier5 = new BlocksStargate(5);
    public static Block StargateTier6 = new BlocksStargate(6);
    public static Block StargateTier7 = new BlocksStargate(7);
    public static Block StargateTier8 = new BlocksStargate(8);
    public static Block StargateTier9 = new BlocksStargate(9);
    public static Block Stargate_Coil_Compressed = new StargateMetaBlockBase(
        "Stargate_Coil_Compressed",
        new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" });

    public static void registryBlocks() {

        ExtremeAnvil = new BlockExtremeAnvil().setBlockName("ExtremeAnvil");
        GameRegistry.registerBlock(ExtremeAnvil, "ExtremeAnvil");
        GameRegistry.registerTileEntity(TileEntityExtremeAnvil.class, "ExtremeAnvilTileEntity");

        NeutronCollector = GameRegistry.registerBlock(new NeutronCollector(), "NeutronCollector");
        DenseNeutronCollector = GameRegistry.registerBlock(new DenseNeutronCollector(), "DenseNeutronCollector");
        DenserNeutronCollector = GameRegistry.registerBlock(new DenserNeutronCollector(), "DenserNeutronCollector");
        DensestNeutronCollector = GameRegistry.registerBlock(new DensestNeutronCollector(), "DensestNeutronCollector");
        GameRegistry.registerTileEntity(TileEntityNeutronCollector.class, "NeutronCollectorTileEntity");

        BlockSoulFarmland = new BlockSoulFarmland();
        GameRegistry.registerBlock(BlockSoulFarmland, "BlockSoulFarmland");

        BlockArtificialStarRender = new BlockArtificialStarRender();
        GameRegistry.registerTileEntity(TileEntityArtificialStar.class, "BlockArtificialStarRender");

        PlayerDoll = new PlayerDoll();
        GameRegistry.registerBlock(PlayerDoll, ItemPlayerDoll.class, "playerDoll");
        GameRegistry.registerTileEntity(TileEntityPlayerDoll.class, "playerDollTileEntity");

        BlockLaserBeacon = new BlockLaserBeacon();
        GameRegistry.registerTileEntity(TileEntityLaserBeacon.class, "BlockLaserBeacon");

        GameRegistry.registerBlock(MetaBlock, ItemBlockBase.class, MetaBlock.getUnlocalizedName());
        GameRegistry.registerBlock(MetaBlockGlow, ItemBlockGlow.class, MetaBlockGlow.getUnlocalizedName());
        GameRegistry.registerBlock(MetaBlockGlass, ItemBlockGlass.class, MetaBlockGlass.getUnlocalizedName());
        GameRegistry.registerBlock(MetaBlockColumn, ItemBlockColumn.class, MetaBlockColumn.getUnlocalizedName());

        GameRegistry.registerBlock(
            BasicBlocks.MetaCasing,
            MetaItemBlockCasing.class,
            BasicBlocks.MetaCasing.getUnlocalizedName());

        GameRegistry.registerBlock(StargateTier0, "StargateTier0");
        GTNLItemList.StargateTier0.set(new ItemStack(StargateTier0));
        GameRegistry.registerBlock(StargateTier1, "StargateTier1");
        GTNLItemList.StargateTier1.set(new ItemStack(StargateTier1));
        GameRegistry.registerBlock(StargateTier2, "StargateTier2");
        GTNLItemList.StargateTier2.set(new ItemStack(StargateTier2));
        GameRegistry.registerBlock(StargateTier3, "StargateTier3");
        GTNLItemList.StargateTier3.set(new ItemStack(StargateTier3));
        GameRegistry.registerBlock(StargateTier4, "StargateTier4");
        GTNLItemList.StargateTier4.set(new ItemStack(StargateTier4));
        GameRegistry.registerBlock(StargateTier5, "StargateTier5");
        GTNLItemList.StargateTier5.set(new ItemStack(StargateTier5));
        GameRegistry.registerBlock(StargateTier6, "StargateTier6");
        GTNLItemList.StargateTier6.set(new ItemStack(StargateTier6));
        GameRegistry.registerBlock(StargateTier7, "StargateTier7");
        GTNLItemList.StargateTier7.set(new ItemStack(StargateTier7));
        GameRegistry.registerBlock(StargateTier8, "StargateTier8");
        GTNLItemList.StargateTier8.set(new ItemStack(StargateTier8));
        GameRegistry.registerBlock(StargateTier9, "StargateTier9");
        GTNLItemList.StargateTier9.set(new ItemStack(StargateTier9));
        GameRegistry.registerBlock(Stargate_Coil_Compressed, ItemBlockMeta.class, "Stargate Coil Compressed");
        GTNLItemList.Stargate_Coil_Compressed.set(new ItemStack(Stargate_Coil_Compressed));
    }

    public static void registryBlockContainers() {

        GTNLItemList.TestMetaBlock01_0.set(ItemBlockBase.initMetaBlock("TestMetaBlock01_0", 0));
        GTNLItemList.NewHorizonsCoil.set(
            ItemBlockBase.initMetaBlock(
                "NewHorizonsCoil",
                1,
                new String[] { RESET + StatCollector.translateToLocal("gt.coilheattooltip") }));
        addItemTooltip(GTNLItemList.NewHorizonsCoil.get(1), AnimatedText.NEW_HORIZONS_COIL_0);
        addItemTooltip(GTNLItemList.NewHorizonsCoil.get(1), AnimatedText.NEW_HORIZONS_COIL_1);
        addItemTooltip(GTNLItemList.NewHorizonsCoil.get(1), AnimatedText.NEW_HORIZONS_COIL_2);
        addItemTooltip(GTNLItemList.NewHorizonsCoil.get(1), AnimatedText.NEW_HORIZONS_COIL_3);
        addItemTooltip(GTNLItemList.NewHorizonsCoil.get(1), AnimatedText.NEW_HORIZONS_COIL_4);

        GTNLItemList.StargateCoil.set(ItemBlockBase.initMetaBlock("StargateCoil", 2));
        GTNLItemList.BlackLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Black Lamp Off",
                3,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.BlackLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Black Lamp Off Borderless",
                4,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.PinkLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Pink Lamp Off",
                5,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.PinkLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Pink Lamp Off Borderless",
                6,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.RedLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Red Lamp Off",
                7,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.RedLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Red Lamp Off Borderless",
                8,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.OrangeLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Orange Lamp Off",
                9,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.OrangeLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Orange Lamp Off Borderless",
                10,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.YellowLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Yellow Lamp Off",
                11,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.YellowLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Yellow Lamp Off Borderless",
                12,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.GreenLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Green Lamp Off",
                13,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.GreenLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Green Lamp Off Borderless",
                14,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.LimeLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Lime Lamp Off",
                15,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.LimeLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Lime Lamp Off Borderless",
                16,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.BlueLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Blue Lamp Off",
                17,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.BlueLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Blue Lamp Off Borderless",
                18,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.LightBlueLampOff.set(
            ItemBlockBase.initMetaBlock(
                "LightBlue Lamp Off",
                19,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.LightBlueLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "LightBlue Lamp Off Borderless",
                20,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.CyanLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Cyan Lamp Off",
                21,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.CyanLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Cyan Lamp Off Borderless",
                22,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.BrownLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Brown Lamp Off",
                23,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.BrownLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Brown Lamp Off Borderless",
                24,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.MagentaLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Magenta Lamp Off",
                25,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.MagentaLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Magenta Lamp Off Borderless",
                26,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.PurpleLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Purple Lamp Off",
                27,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.PurpleLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Purple Lamp Off Borderless",
                28,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.GrayLampOff.set(
            ItemBlockBase.initMetaBlock(
                "Gray Lamp Off",
                29,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.GrayLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "Gray Lamp Off Borderless",
                30,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.LightGrayLampOff.set(
            ItemBlockBase.initMetaBlock(
                "LightGray Lamp Off",
                31,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.LightGrayLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "LightGray Lamp Off Borderless",
                32,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.WhiteLampOff.set(
            ItemBlockBase.initMetaBlock(
                "White Lamp Off",
                33,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow") }));
        GTNLItemList.WhiteLampOffBorderless.set(
            ItemBlockBase.initMetaBlock(
                "White Lamp Off Borderless",
                34,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.noglow"),
                    StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.BlazeCubeBlock.set(ItemBlockBase.initMetaBlock("BlazeCubeBlock", 35));

        GTNLItemList.FortifyGlowstone.set(ItemBlockGlow.initMetaBlockGlow("Fortify_Glowstone", 0));
        GTNLItemList.BlackLamp.set(ItemBlockGlow.initMetaBlockGlow("Black Lamp", 1));
        GTNLItemList.BlackLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Black Lamp Borderless",
                2,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.PinkLamp.set(ItemBlockGlow.initMetaBlockGlow("Pink Lamp", 3));
        GTNLItemList.PinkLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Pink Lamp Borderless",
                4,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.RedLamp.set(ItemBlockGlow.initMetaBlockGlow("Red Lamp", 5));
        GTNLItemList.RedLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Red Lamp Borderless",
                6,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.OrangeLamp.set(ItemBlockGlow.initMetaBlockGlow("Orange Lamp", 7));
        GTNLItemList.OrangeLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Orange Lamp Borderless",
                8,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.YellowLamp.set(ItemBlockGlow.initMetaBlockGlow("Yellow Lamp", 9));
        GTNLItemList.YellowLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Yellow Lamp Borderless",
                10,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.GreenLamp.set(ItemBlockGlow.initMetaBlockGlow("Green Lamp", 11));
        GTNLItemList.GreenLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Green Lamp Borderless",
                12,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.LimeLamp.set(ItemBlockGlow.initMetaBlockGlow("Lime Lamp", 13));
        GTNLItemList.LimeLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Lime Lamp Borderless",
                14,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.BlueLamp.set(ItemBlockGlow.initMetaBlockGlow("Blue Lamp", 15));
        GTNLItemList.BlueLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Blue Lamp Borderless",
                16,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.LightBlueLamp.set(ItemBlockGlow.initMetaBlockGlow("LightBlue Lamp", 17));
        GTNLItemList.LightBlueLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "LightBlue Lamp Borderless",
                18,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.CyanLamp.set(ItemBlockGlow.initMetaBlockGlow("Cyan Lamp", 19));
        GTNLItemList.CyanLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Cyan Lamp Borderless",
                20,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.BrownLamp.set(ItemBlockGlow.initMetaBlockGlow("Brown Lamp", 21));
        GTNLItemList.BrownLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Brown Lamp Borderless",
                22,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.MagentaLamp.set(ItemBlockGlow.initMetaBlockGlow("Magenta Lamp", 23));
        GTNLItemList.MagentaLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Magenta Lamp Borderless",
                24,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.PurpleLamp.set(ItemBlockGlow.initMetaBlockGlow("Purple Lamp", 25));
        GTNLItemList.PurpleLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Purple Lamp Borderless",
                26,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.GrayLamp.set(ItemBlockGlow.initMetaBlockGlow("Gray Lamp", 27));
        GTNLItemList.GrayLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "Gray Lamp Borderless",
                28,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.LightGrayLamp.set(ItemBlockGlow.initMetaBlockGlow("LightGray Lamp", 29));
        GTNLItemList.LightGrayLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "LightGray Lamp Borderless",
                30,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));
        GTNLItemList.WhiteLamp.set(ItemBlockGlow.initMetaBlockGlow("White Lamp", 31));
        GTNLItemList.WhiteLampBorderless.set(
            ItemBlockGlow.initMetaBlockGlow(
                "White Lamp Borderless",
                32,
                new String[] { StatCollector.translateToLocal("tooltip.lamp.borderless") }));

        GTNLItemList.GaiaGlass.set(ItemBlockGlass.initMetaBlockGlass("Gaia Glass", 0));
        GTNLItemList.TerraGlass.set(ItemBlockGlass.initMetaBlockGlass("Terra Glass", 1));
        GTNLItemList.FusionGlass.set(ItemBlockGlass.initMetaBlockGlass("Fusion Glass", 2));

        GTNLItemList.BronzeBrickCasing.set(ItemBlockColumn.initMetaBlock("Bronze Brick Casing", 0));
        GTNLItemList.SteelBrickCasing.set(ItemBlockColumn.initMetaBlock("Steel Brick Casing", 1));
        GTNLItemList.CrushingWheels.set(ItemBlockColumn.initMetaBlock("Crushing Wheels", 2));

        GTNLItemList.TestCasing
            .set(MetaBlockConstructors.initMetaBlockCasing("Test Casing", (byte) 0, BasicBlocks.MetaCasing));
        GTNLItemList.SteamAssemblyCasing
            .set(MetaBlockConstructors.initMetaBlockCasing("Steam Assembly Casing", (byte) 1, BasicBlocks.MetaCasing));
        GTNLItemList.HeatVent
            .set(MetaBlockConstructors.initMetaBlockCasing("Heat Vent", (byte) 2, BasicBlocks.MetaCasing));
        GTNLItemList.SlicingBlades
            .set(MetaBlockConstructors.initMetaBlockCasing("Slicing Blades", (byte) 3, BasicBlocks.MetaCasing));
        GTNLItemList.NeutroniumPipeCasing
            .set(MetaBlockConstructors.initMetaBlockCasing("Neutronium Pipe Casing", (byte) 4, BasicBlocks.MetaCasing));
        GTNLItemList.NeutroniumGearbox.set(
            MetaBlockConstructors.initMetaBlockCasing("Neutronium Gear Box Casing", (byte) 5, BasicBlocks.MetaCasing));
        GTNLItemList.Laser_Cooling_Casing
            .set(MetaBlockConstructors.initMetaBlockCasing("Laser Cooling Casing", (byte) 6, BasicBlocks.MetaCasing));
        GTNLItemList.Antifreeze_Heatproof_Machine_Casing.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Antifreeze Heatproof Machine Casing", (byte) 7, BasicBlocks.MetaCasing));
        GTNLItemList.MolybdenumDisilicideCoil.set(
            MetaBlockConstructors.initMetaBlockCasing("Molybdenum Disilicide Coil", (byte) 8, BasicBlocks.MetaCasing));
        GTNLItemList.EnergeticPhotovoltaicBlock.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Energetic Photovoltaic Block", (byte) 9, BasicBlocks.MetaCasing));
        GTNLItemList.AdvancedPhotovoltaicBlock.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Advanced Photovoltaic Block", (byte) 10, BasicBlocks.MetaCasing));
        GTNLItemList.VibrantPhotovoltaicBlock.set(
            MetaBlockConstructors.initMetaBlockCasing("Vibrant Photovoltaic Block", (byte) 11, BasicBlocks.MetaCasing));
        GTNLItemList.TungstensteelGearbox.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Tungstensteel Gear Box Casing", (byte) 12, BasicBlocks.MetaCasing));
        GTNLItemList.DimensionallyStableCasing.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Dimensionally Stable Casing", (byte) 13, BasicBlocks.MetaCasing));
        GTNLItemList.PressureBalancedCasing.set(
            MetaBlockConstructors.initMetaBlockCasing("Pressure Balanced Casing", (byte) 14, BasicBlocks.MetaCasing));
        GTNLItemList.ABSUltraSolidCasing.set(
            MetaBlockConstructors.initMetaBlockCasing("ABS Ultra-Solid Casing", (byte) 15, BasicBlocks.MetaCasing));
        GTNLItemList.GravitationalFocusingLensBlock.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Gravitational Focusing Lens Block", (byte) 16, BasicBlocks.MetaCasing));
        GTNLItemList.GaiaStabilizedForceFieldCasing.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Gaia Stabilized Force Field Casing", (byte) 17, BasicBlocks.MetaCasing));
        GTNLItemList.HyperCore
            .set(MetaBlockConstructors.initMetaBlockCasing("Hyper Core", (byte) 18, BasicBlocks.MetaCasing));
        GTNLItemList.ChemicallyResistantCasing.set(
            MetaBlockConstructors
                .initMetaBlockCasing("Chemically Resistant Casing", (byte) 19, BasicBlocks.MetaCasing));
        GTNLItemList.UltraPoweredCasing
            .set(MetaBlockConstructors.initMetaBlockCasing("Ultra Powered Casing", (byte) 20, BasicBlocks.MetaCasing));
    }

    public static void registry() {
        registryBlocks();
        registryBlockContainers();
    }

    public static void registryOreDictionary() {
        ItemStack GaiaGlass = new ItemStack(MetaBlockGlass, 1, 0);
        ItemStack TerraGlass = new ItemStack(MetaBlockGlass, 1, 1);
        ItemStack FusionGlass = new ItemStack(MetaBlockGlass, 1, 2);
        ItemStack ExtremeAnvilBlock = new ItemStack(ExtremeAnvil, 1, 0);
        ItemStack Bedrock = new ItemStack(Blocks.bedrock, 1, 0);
        ItemStack EndPortal = new ItemStack(Blocks.end_portal, 1, 0);
        ItemStack EndPortalFrame = new ItemStack(Blocks.end_portal_frame, 1, 0);
        ItemStack CommandBlock = new ItemStack(Blocks.command_block, 1, 0);

        for (int LampMeta = 1; LampMeta <= 32; LampMeta++) {
            ItemStack LampTier = new ItemStack(MetaBlockGlow, 1, LampMeta);
            OreDictionary.registerOre("blockGlassHV", LampTier);
            OreDictionary.registerOre("blockGlass", LampTier);
        }

        for (int LampOffMeta = 3; LampOffMeta <= 34; LampOffMeta++) {
            ItemStack LampOffTier = new ItemStack(MetaBlock, 1, LampOffMeta);
            OreDictionary.registerOre("blockGlassHV", LampOffTier);
            OreDictionary.registerOre("blockGlass", LampOffTier);
        }

        OreDictionary.registerOre("neutronUnbreak", CommandBlock);
        OreDictionary.registerOre("neutronUnbreak", EndPortal);
        OreDictionary.registerOre("neutronUnbreak", EndPortalFrame);
        OreDictionary.registerOre("neutronUnbreak", Bedrock);
        OreDictionary.registerOre("neutronUnbreak", ExtremeAnvilBlock);
        OreDictionary.registerOre("blockGlassUEV", GaiaGlass);
        OreDictionary.registerOre("blockGlassUV", TerraGlass);
        OreDictionary.registerOre("blockGlassUV", FusionGlass);
    }
}
