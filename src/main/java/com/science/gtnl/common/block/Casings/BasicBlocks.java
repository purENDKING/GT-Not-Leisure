package com.science.gtnl.common.block.Casings;

import net.minecraft.block.Block;

import com.science.gtnl.common.block.Casings.Base.MetaBlockBase;
import com.science.gtnl.common.block.Casings.Casing.MetaCasing;
import com.science.gtnl.common.block.Casings.Column.MetaBlockColumn;
import com.science.gtnl.common.block.Casings.Glass.MetaBlockGlass;
import com.science.gtnl.common.block.Casings.Glow.MetaBlockGlow;
import com.science.gtnl.common.block.Casings.Special.BlocksStargate;
import com.science.gtnl.common.block.Casings.Special.StargateMetaBlockBase;

public class BasicBlocks {

    public static Block BlockArtificialStarRender;
    public static Block LaserBeacon;
    public static Block PlayerDoll;
    public static Block BlockNanoPhagocytosisPlantRender;
    public static Block BlockEternalGregTechWorkshopRender;
    public static final Block MetaBlock = new MetaBlockBase("MetaBlock", "MetaBlock");
    public static final Block MetaBlockGlow = new MetaBlockGlow("MetaBlockGlow", "MetaBlockGlow");
    public static final Block MetaBlockGlass = new MetaBlockGlass("MetaBlockGlass", "MetaBlockGlass");
    public static final Block MetaBlockColumn = new MetaBlockColumn("MetaBlockColumn", "MetaBlockColumn");
    public static final MetaCasing MetaCasing = new MetaCasing("MetaCasing", (byte) 0);
    public static final MetaCasing MetaCasing02 = new MetaCasing("MetaCasing02", (byte) 32);
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
}
