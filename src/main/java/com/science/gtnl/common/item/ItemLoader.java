package com.science.gtnl.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.common.item.ReAvaritia.BlazeAxe;
import com.science.gtnl.common.item.ReAvaritia.BlazeHoe;
import com.science.gtnl.common.item.ReAvaritia.BlazePickaxe;
import com.science.gtnl.common.item.ReAvaritia.BlazeShovel;
import com.science.gtnl.common.item.ReAvaritia.BlazeSword;
import com.science.gtnl.common.item.ReAvaritia.CrystalAxe;
import com.science.gtnl.common.item.ReAvaritia.CrystalHoe;
import com.science.gtnl.common.item.ReAvaritia.CrystalPickaxe;
import com.science.gtnl.common.item.ReAvaritia.CrystalShovel;
import com.science.gtnl.common.item.ReAvaritia.CrystalSword;
import com.science.gtnl.common.item.ReAvaritia.InfinityAxe;
import com.science.gtnl.common.item.ReAvaritia.InfinityBucket;
import com.science.gtnl.common.item.ReAvaritia.InfinityHoe;
import com.science.gtnl.common.item.ReAvaritia.InfinityPickaxe;
import com.science.gtnl.common.item.ReAvaritia.InfinityShovel;
import com.science.gtnl.common.item.ReAvaritia.InfinitySword;
import com.science.gtnl.common.item.ReAvaritia.InfinityTotem;
import com.science.gtnl.common.item.ReAvaritia.MatterCluster;
import com.science.gtnl.common.item.items.FakeItemSiren;
import com.science.gtnl.common.item.items.FuelRod.FuelRod;
import com.science.gtnl.common.item.items.FuelRod.FuelRodDepleted;
import com.science.gtnl.common.item.items.KFCFamily;
import com.science.gtnl.common.item.items.RejectionRing;
import com.science.gtnl.common.item.items.SatietyRing;
import com.science.gtnl.common.item.items.SuperReachRing;
import com.science.gtnl.common.item.items.TestItem;
import com.science.gtnl.common.item.items.TwilightSword;

import cpw.mods.fml.common.registry.GameRegistry;

public class ItemLoader {

    private ItemLoader() {}

    public static Item FakeItemSiren = new FakeItemSiren();
    public static Item TestItem = new TestItem();
    public static Item CrystalSword = new CrystalSword();
    public static Item CrystalAxe = new CrystalAxe();
    public static Item CrystalPickaxe = new CrystalPickaxe();
    public static Item CrystalShovel = new CrystalShovel();
    public static Item CrystalHoe = new CrystalHoe();
    public static Item BlazeSword = new BlazeSword();
    public static Item BlazeAxe = new BlazeAxe();
    public static Item BlazePickaxe = new BlazePickaxe();
    public static Item BlazeShovel = new BlazeShovel();
    public static Item BlazeHoe = new BlazeHoe();
    public static Item InfinitySword = new InfinitySword();
    public static Item InfinityPickaxe = new InfinityPickaxe();
    public static Item InfinityAxe = new InfinityAxe();
    public static Item InfinityShovel = new InfinityShovel();
    public static Item InfinityHoe = new InfinityHoe();
    public static Item InfinityTotem = new InfinityTotem();
    public static Item InfinityBucket = new InfinityBucket();
    public static Item MatterCluster = new MatterCluster();
    public static Item KFCFamily = new KFCFamily(20, 20, true);
    public static Item SatietyRing = new SatietyRing();
    public static Item RejectionRing = new RejectionRing();
    public static Item TwilightSword = new TwilightSword();
    public static Item SuperReachRing = new SuperReachRing();
    public static Item RecordSus = new ItemRecord("sus");
    public static Item RecordNewHorizons = new ItemRecord("newhorizons");

    public static Item InfinityFuelRodDepleted = new FuelRodDepleted("InfinityFuelRodDepleted", 2000);
    public static Item InfinityFuelRod = new FuelRod(
        "InfinityFuelRod",
        1,
        491520,
        500,
        15000,
        160000,
        70F,
        new ItemStack(InfinityFuelRodDepleted, 1));

    public static void registerItems() {

        GTNLItemList.RecordSus.set(new ItemStack(RecordSus, 1));
        GTNLItemList.RecordNewHorizons.set(new ItemStack(RecordNewHorizons, 1));
        GTNLItemList.InfinityFuelRodDepleted.set(new ItemStack(InfinityFuelRodDepleted, 1));
        GTNLItemList.InfinityFuelRod.set(new ItemStack(InfinityFuelRod, 1));

        IRegistry(FakeItemSiren, "FakeItemSiren");
        IRegistry(TestItem, "TestItem");
        IRegistry(CrystalPickaxe, "CrystalPickaxe");
        IRegistry(CrystalHoe, "CrystalHoe");
        IRegistry(CrystalShovel, "CrystalShovel");
        IRegistry(CrystalAxe, "CrystalAxe");
        IRegistry(CrystalSword, "CrystalSword");
        IRegistry(BlazePickaxe, "BlazePickaxe");
        IRegistry(BlazeAxe, "BlazeAxe");
        IRegistry(BlazeHoe, "BlazeHoe");
        IRegistry(BlazeSword, "BlazeSword");
        IRegistry(BlazeShovel, "BlazeShovel");
        IRegistry(InfinitySword, "InfinitySword");
        IRegistry(InfinityAxe, "InfinityAxe");
        IRegistry(InfinityPickaxe, "InfinityPickaxe");
        IRegistry(InfinityShovel, "InfinityShovel");
        IRegistry(InfinityHoe, "InfinityHoe");
        IRegistry(InfinityTotem, "InfinityTotem");
        IRegistry(InfinityBucket, "InfinityBucket");
        IRegistry(MatterCluster, "MatterCluster");
        IRegistry(KFCFamily, "KFCFamily");
        IRegistry(SatietyRing, "SatietyRing");
        IRegistry(RejectionRing, "RejectionRing");
        IRegistry(TwilightSword, "TwilightSword");
        IRegistry(RecordSus, "RecordSus");
        IRegistry(RecordNewHorizons, "RecordNewHorizons");

        IRegistry(InfinityFuelRodDepleted, "InfinityFuelRodDepleted");
        IRegistry(InfinityFuelRod, "InfinityFuelRod");

    }

    public static void IRegistry(Item item, String name) {
        GameRegistry.registerItem(item, name);
    }

}
