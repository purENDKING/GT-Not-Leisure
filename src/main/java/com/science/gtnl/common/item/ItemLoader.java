package com.science.gtnl.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.common.item.items.FakeItemSiren;
import com.science.gtnl.common.item.items.FuelRod.FuelRod;
import com.science.gtnl.common.item.items.FuelRod.FuelRodDepleted;
import com.science.gtnl.common.item.items.KFCFamily;
import com.science.gtnl.common.item.items.PhysicsCape;
import com.science.gtnl.common.item.items.RejectionRing;
import com.science.gtnl.common.item.items.SatietyRing;
import com.science.gtnl.common.item.items.SuperReachRing;
import com.science.gtnl.common.item.items.TestItem;
import com.science.gtnl.common.item.items.TimeStopPocketWatch;
import com.science.gtnl.common.item.items.TwilightSword;

import cpw.mods.fml.common.registry.GameRegistry;

public class ItemLoader {

    private ItemLoader() {}

    public static Item FakeItemSiren = new FakeItemSiren();
    public static Item TestItem = new TestItem();
    public static Item KFCFamily = new KFCFamily(20, 20, true);
    public static Item SatietyRing = new SatietyRing();
    public static Item RejectionRing = new RejectionRing();
    public static Item TwilightSword = new TwilightSword();
    public static Item PhysicsCape = new PhysicsCape();
    public static Item TimeStopPocketWatch = new TimeStopPocketWatch();
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
        IRegistry(KFCFamily, "KFCFamily");
        IRegistry(SatietyRing, "SatietyRing");
        IRegistry(RejectionRing, "RejectionRing");
        IRegistry(TwilightSword, "TwilightSword");
        IRegistry(PhysicsCape, "PhysicsCape");
        IRegistry(TimeStopPocketWatch, "TimeStopPocketWatch");
        IRegistry(RecordSus, "RecordSus");
        IRegistry(RecordNewHorizons, "RecordNewHorizons");

        IRegistry(InfinityFuelRodDepleted, "InfinityFuelRodDepleted");
        IRegistry(InfinityFuelRod, "InfinityFuelRod");

    }

    public static void IRegistry(Item item, String name) {
        GameRegistry.registerItem(item, name);
    }

}
