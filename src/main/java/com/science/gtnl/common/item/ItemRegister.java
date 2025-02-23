package com.science.gtnl.common.item;

import static com.science.gtnl.Utils.item.TextHandler.texter;
import static com.science.gtnl.common.item.BasicItems.MetaItem;
import static com.science.gtnl.common.item.ItemAdder.initItem;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.science.gtnl.common.GTNLItemList;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.util.GTOreDictUnificator;

public class ItemRegister {

    public static void registryItems() {
        Item[] itemsToReg = { MetaItem };

        for (Item item : itemsToReg) {
            GameRegistry.registerItem(item, item.getUnlocalizedName());
        }

    }

    public static void registryItemContainers() {
        GTNLItemList.TrollFace
            .set(initItem("TrollFace", 0, new String[] { texter("Never Gonna Give You Up~", "tooltips.TrollFace") }));
        GTNLItemList.DepletedExcitedNaquadahFuelRod.set(
            initItem(
                "§bDepleted Excited Naquadah Fuel Rod",
                1,
                new String[] {
                    texter("§oUltimate Form of Naquadah Fuel.", "tooltips.DepletedExcitedNaquadahFuelRod") }));
        GTNLItemList.BlazeCube
            .set(initItem("§eBlaze Cube", 2, new String[] { texter("§8§oBlaze Storm.", "tooltips.BlazeCube") }));
        GTNLItemList.EnhancementCore.set(
            initItem(
                "Enhancement Core",
                3,
                new String[] { texter("§8§oThe road to completion!", "tooltips.EnhancementCore") }));
        GTNLItemList.StellarConstructionFrameMaterial.set(
            initItem(
                "Stellar Construction Frame Material",
                4,
                new String[] { texter("A test item, no use.", "tooltips.TestItem0.line1") }));
        GTNLItemList.ActivatedGaiaPylon.set(initItem("Activated Gaia Pylon", 5));
        GTNLItemList.PrecisionSteamMechanism.set(initItem("Precision Steam Mechanism", 6));
        GTNLItemList.MeteorMinerSchematic1.set(
            initItem(
                "Meteor Miner Tier 1 Schematic",
                7,
                new String[] {
                    texter("Schematic needed to enable the Tier 1 Meteor Miner", "tooltips.MeteorMinerSchematic1") }));
        GTNLItemList.MeteorMinerSchematic2.set(
            initItem(
                "Meteor Miner Tier 2 Schematic",
                8,
                new String[] {
                    texter("Schematic needed to enable the Tier 2 Meteor Miner", "tooltips.MeteorMinerSchematic2") }));
        GTNLItemList.CircuitResonaticULV.set(
            initItem(
                "Circuit Resonatic ULV",
                9,
                new String[] { texter("An Original Circuit", "tooltips.CircuitResonaticULV_00"),
                    texter("§fULV-Tier", "tooltips.CircuitResonaticULV_01") }));
        GTNLItemList.CircuitResonaticLV.set(
            initItem(
                "Circuit Resonatic LV",
                10,
                new String[] { texter("An Basic Circuit", "tooltips.CircuitResonaticLV_00"),
                    texter("LV-Tier", "tooltips.CircuitResonaticLV_01") }));
        GTNLItemList.CircuitResonaticMV.set(
            initItem(
                "Circuit Resonatic MV",
                11,
                new String[] { texter("An Good Circuit", "tooltips.CircuitResonaticMV_00"),
                    texter("§6MV-Tier", "tooltips.CircuitResonaticMV_01") }));
        GTNLItemList.CircuitResonaticHV.set(
            initItem(
                "Circuit Resonatic HV",
                12,
                new String[] { texter("An Adavanced Circuit", "tooltips.CircuitResonaticHV_00"),
                    texter("§eHV-Tier", "tooltips.CircuitResonaticHV_01") }));
        GTNLItemList.CircuitResonaticEV.set(
            initItem(
                "Circuit Resonatic EV",
                13,
                new String[] { texter("An Data Circuit", "tooltips.CircuitResonaticEV_00"),
                    texter("§8EV-Tier", "tooltips.CircuitResonaticEV_01") }));
        GTNLItemList.CircuitResonaticIV.set(
            initItem(
                "Circuit Resonatic IV",
                14,
                new String[] { texter("An Elite Circuit", "tooltips.CircuitResonaticIV_00"),
                    texter("§aIV-Tier", "tooltips.CircuitResonaticIV_01") }));
        GTNLItemList.CircuitResonaticLuV.set(
            initItem(
                "Circuit Resonatic LuV",
                15,
                new String[] { texter("An Master Circuit", "tooltips.CircuitResonaticLuV_00"),
                    texter("§dLuV-Tier", "tooltips.CircuitResonaticLuV_01") }));
        GTNLItemList.CircuitResonaticZPM.set(
            initItem(
                "Circuit Resonatic ZPM",
                16,
                new String[] { texter("An Ultimate Circuit", "tooltips.CircuitResonaticZPM_00"),
                    texter("§bZPM-Tier", "tooltips.CircuitResonaticZPM_01") }));
        GTNLItemList.CircuitResonaticUV.set(
            initItem(
                "Circuit Resonatic UV",
                17,
                new String[] { texter("An Superconductor Circuit", "tooltips.CircuitResonaticUV_00"),
                    texter("§2UV-Tier", "tooltips.CircuitResonaticUV_01") }));
        GTNLItemList.CircuitResonaticUHV.set(
            initItem(
                "Circuit Resonatic UHV",
                18,
                new String[] { texter("An Infinite Circuit", "tooltips.CircuitResonaticUHV_00"),
                    texter("§4UHV-Tier", "tooltips.CircuitResonaticUHV_01") }));
        GTNLItemList.CircuitResonaticUEV.set(
            initItem(
                "Circuit Resonatic UEV",
                19,
                new String[] { texter("An Bio Circuit", "tooltips.CircuitResonaticUEV_00"),
                    texter("§5UEV-Tier", "tooltips.CircuitResonaticUEV_01") }));
        GTNLItemList.CircuitResonaticUIV.set(
            initItem(
                "Circuit Resonatic UIV",
                20,
                new String[] { texter("An Optical Circuit", "tooltips.CircuitResonaticUIV_00"),
                    texter("§l§1UIV-Tier", "tooltips.CircuitResonaticUIV_01") }));
        GTNLItemList.VerySimpleCircuit.set(
            initItem(
                "Very Simple Circuit",
                21,
                new String[] { texter("An Very Simple Circuit", "tooltips.VerySimpleCircuit_00"),
                    texter("§fULV-Tier", "tooltips.VerySimpleCircuit_01") }));
        GTNLItemList.SimpleCircuit.set(
            initItem(
                "Simple Circuit",
                22,
                new String[] { texter("An Simple Circuit", "tooltips.SimpleCircuit_00"),
                    texter("LV-Tier", "tooltips.SimpleCircuit_01") }));
        GTNLItemList.BasicCircuit.set(
            initItem(
                "Basic Circuit",
                23,
                new String[] { texter("An Basic Circuit", "tooltips.BasicCircuit_00"),
                    texter("§6MV-Tier", "tooltips.BasicCircuit_01") }));
        GTNLItemList.AdvancedCircuit.set(
            initItem(
                "Advanced Circuit",
                24,
                new String[] { texter("An Advanced Circuit", "tooltips.AdvancedCircuit_00"),
                    texter("§eHV-Tier", "tooltips.AdvancedCircuit_01") }));
        GTNLItemList.EliteCircuit.set(
            initItem(
                "Elite Circuit",
                25,
                new String[] { texter("An Elite Circuit", "tooltips.EliteCircuit_00"),
                    texter("§8EV-Tier", "tooltips.EliteCircuit_01") }));
        GTNLItemList.StargateSingularity.set(initItem("Stargate Singularity", 26));
        GTNLItemList.StargateCompressedSingularity.set(initItem("Stargate Compressed Singularity", 27));
        GTNLItemList.BiowareSMDCapacitor.set(initItem("Bioware SMD Capacitor", 28));
        GTNLItemList.BiowareSMDDiode.set(initItem("Bioware SMD Diode", 29));
        GTNLItemList.BiowareSMDInductor.set(initItem("Bioware SMD Inductor", 30));
        GTNLItemList.BiowareSMDResistor.set(initItem("Bioware SMD Resistor", 31));
        GTNLItemList.BiowareSMDTransistor.set(initItem("Bioware SMD Transistor", 32));
        GTNLItemList.ExoticSMDCapacitor.set(initItem("Exotic SMD Capacitor", 33));
        GTNLItemList.ExoticSMDDiode.set(initItem("Exotic SMD Diode", 34));
        GTNLItemList.ExoticSMDInductor.set(initItem("Exotic SMD Inductor", 35));
        GTNLItemList.ExoticSMDResistor.set(initItem("Exotic SMD Resistor", 36));
        GTNLItemList.ExoticSMDTransistor.set(initItem("Exotic SMD Transistor", 37));
        GTNLItemList.TemporallySMDCapacitor.set(initItem("Temporally SMD Capacitor", 38));
        GTNLItemList.TemporallySMDDiode.set(initItem("Temporally SMD Diode", 39));
        GTNLItemList.TemporallySMDInductor.set(initItem("Temporally SMD Inductor", 40));
        GTNLItemList.TemporallySMDResistor.set(initItem("Temporally SMD Resistor", 41));
        GTNLItemList.TemporallySMDTransistor.set(initItem("Temporally SMD Transistor", 42));
        GTNLItemList.CosmicSMDCapacitor.set(initItem("Cosmic SMD Capacitor", 43));
        GTNLItemList.CosmicSMDDiode.set(initItem("Cosmic SMD Diode", 44));
        GTNLItemList.CosmicSMDInductor.set(initItem("Cosmic SMD Inductor", 45));
        GTNLItemList.CosmicSMDResistor.set(initItem("Cosmic SMD Resistor", 46));
        GTNLItemList.CosmicSMDTransistor.set(initItem("Cosmic SMD Transistor", 47));
        GTNLItemList.LVParallelControllerCore.set(
            initItem(
                "LV Parallel Controller Core",
                48,
                new String[] { texter("§dSpeed: +1%%", "tooltips.LVParallelControllerCore_00"),
                    texter("§5EU Usage: 78%%", "tooltips.LVParallelControllerCore_01"),
                    texter("§bParallel: 0", "tooltips.LVParallelControllerCore_02") }));

        GTNLItemList.MVParallelControllerCore.set(
            initItem(
                "MV Parallel Controller Core",
                49,
                new String[] { texter("§dSpeed: +2%%", "tooltips.MVParallelControllerCore_00"),
                    texter("§5EU Usage: 76%%", "tooltips.MVParallelControllerCore_01"),
                    texter("§bParallel: 1", "tooltips.MVParallelControllerCore_02") }));

        GTNLItemList.HVParallelControllerCore.set(
            initItem(
                "HV Parallel Controller Core",
                50,
                new String[] { texter("§dSpeed: +4%%", "tooltips.HVParallelControllerCore_00"),
                    texter("§5EU Usage: 74%%", "tooltips.HVParallelControllerCore_01"),
                    texter("§bParallel: 4", "tooltips.HVParallelControllerCore_02") }));

        GTNLItemList.EVParallelControllerCore.set(
            initItem(
                "EV Parallel Controller Core",
                51,
                new String[] { texter("§dSpeed: +6%%", "tooltips.EVParallelControllerCore_00"),
                    texter("§5EU Usage: 72%%", "tooltips.EVParallelControllerCore_01"),
                    texter("§bParallel: 16", "tooltips.EVParallelControllerCore_02") }));

        GTNLItemList.IVParallelControllerCore.set(
            initItem(
                "IV Parallel Controller Core",
                52,
                new String[] { texter("§dSpeed: +8%%", "tooltips.IVParallelControllerCore_00"),
                    texter("§5EU Usage: 70%%", "tooltips.IVParallelControllerCore_01"),
                    texter("§bParallel: 64", "tooltips.IVParallelControllerCore_02") }));

        GTNLItemList.LuVParallelControllerCore.set(
            initItem(
                "LuV Parallel Controller Core",
                53,
                new String[] { texter("§dSpeed: +10%%", "tooltips.LuVParallelControllerCore_00"),
                    texter("§5EU Usage: 68%%", "tooltips.LuVParallelControllerCore_01"),
                    texter("§bParallel: 256", "tooltips.LuVParallelControllerCore_02") }));

        GTNLItemList.ZPMParallelControllerCore.set(
            initItem(
                "ZPM Parallel Controller Core",
                54,
                new String[] { texter("§dSpeed: +12%%", "tooltips.ZPMParallelControllerCore_00"),
                    texter("§5EU Usage: 66%%", "tooltips.ZPMParallelControllerCore_01"),
                    texter("§bParallel: 1024", "tooltips.ZPMParallelControllerCore_02") }));

        GTNLItemList.UVParallelControllerCore.set(
            initItem(
                "UV Parallel Controller Core",
                55,
                new String[] { texter("§dSpeed: +14%%", "tooltips.UVParallelControllerCore_00"),
                    texter("§5EU Usage: 64%%", "tooltips.UVParallelControllerCore_01"),
                    texter("§bParallel: 4096", "tooltips.UVParallelControllerCore_02") }));

        GTNLItemList.UHVParallelControllerCore.set(
            initItem(
                "UHV Parallel Controller Core",
                56,
                new String[] { texter("§dSpeed: +16%%", "tooltips.UHVParallelControllerCore_00"),
                    texter("§5EU Usage: 62%%", "tooltips.UHVParallelControllerCore_01"),
                    texter("§bParallel: 16384", "tooltips.UHVParallelControllerCore_02") }));

        GTNLItemList.UEVParallelControllerCore.set(
            initItem(
                "UEV Parallel Controller Core",
                57,
                new String[] { texter("§dSpeed: +18%%", "tooltips.UEVParallelControllerCore_00"),
                    texter("§5EU Usage: 60%%", "tooltips.UEVParallelControllerCore_01"),
                    texter("§bParallel: 65536", "tooltips.UEVParallelControllerCore_02") }));

        GTNLItemList.UIVParallelControllerCore.set(
            initItem(
                "UIV Parallel Controller Core",
                58,
                new String[] { texter("§dSpeed: +20%%", "tooltips.UIVParallelControllerCore_00"),
                    texter("§5EU Usage: 58%%", "tooltips.UIVParallelControllerCore_01"),
                    texter("§bParallel: 262144", "tooltips.UIVParallelControllerCore_02") }));

        GTNLItemList.UMVParallelControllerCore.set(
            initItem(
                "UMV Parallel Controller Core",
                59,
                new String[] { texter("§dSpeed: +22%%", "tooltips.UMVParallelControllerCore_00"),
                    texter("§5EU Usage: 56%%", "tooltips.UMVParallelControllerCore_01"),
                    texter("§bParallel: 1048576", "tooltips.UMVParallelControllerCore_02") }));

        GTNLItemList.UXVParallelControllerCore.set(
            initItem(
                "UXV Parallel Controller Core",
                60,
                new String[] { texter("§dSpeed: +24%%", "tooltips.UXVParallelControllerCore_00"),
                    texter("§5EU Usage: 54%%", "tooltips.UXVParallelControllerCore_01"),
                    texter("§bParallel: 4194304", "tooltips.UXVParallelControllerCore_02") }));

        GTNLItemList.MAXParallelControllerCore.set(
            initItem(
                "MAX Parallel Controller Core",
                61,
                new String[] { texter("§dSpeed: +26%%", "tooltips.MAXParallelControllerCore_00"),
                    texter("§5EU Usage: 52%%", "tooltips.MAXParallelControllerCore_01"),
                    texter("§bParallel: 16777216", "tooltips.MAXParallelControllerCore_02") }));

        GTNLItemList.NagaBook.set(initItem("§dRumor - Naga Book", 62));
        GTNLItemList.TwilightForestBook.set(initItem("§6Urban legends - Twilight Forest Book", 63));
        GTNLItemList.LichBook.set(initItem("Impurity - Lich Book", 64));
        GTNLItemList.MinotaurBook.set(initItem("§cUrban Strange Tales - Minotaur Book", 65));
        GTNLItemList.HydraBook.set(initItem("§dRumor - Hydra Book", 66));
        GTNLItemList.KnightPhantomBook.set(initItem("§9Urban Diseases - Knight Phantom Book", 67));
        GTNLItemList.UrGhastBook.set(initItem("§9Urban Diseases - Ur-Ghast Book", 68));
        GTNLItemList.AlphaYetiBook.set(initItem("Impurity - Alpha Yeti Book", 69));
        GTNLItemList.SnowQueenBook.set(initItem("§bCity Star - Snow Queen Book", 70));
        GTNLItemList.FinalBook.set(initItem("§bCity Star - Final Book", 71));
        GTNLItemList.GiantBook.set(initItem("§dRumor - Giant Book", 72));
        GTNLItemList.ClayedGlowstone.set(initItem("Clayed Glowstone", 73));
        GTNLItemList.QuantumDisk.set(initItem("Quantum Disk", 74));
        GTNLItemList.NeutroniumBoule.set(initItem("Neutronium Boule", 75));
        GTNLItemList.NeutroniumWafer.set(initItem("Neutronium Wafer", 76));
    }

    public static void registry() {
        registryItems();
        registryItemContainers();
    }

    public static void registryOreDictionary() {
        ItemStack CircuitResonaticULV = new ItemStack(MetaItem, 1, 9);
        ItemStack CircuitResonaticLV = new ItemStack(MetaItem, 1, 10);
        ItemStack CircuitResonaticMV = new ItemStack(MetaItem, 1, 11);
        ItemStack CircuitResonaticHV = new ItemStack(MetaItem, 1, 12);
        ItemStack CircuitResonaticEV = new ItemStack(MetaItem, 1, 13);
        ItemStack CircuitResonaticIV = new ItemStack(MetaItem, 1, 14);
        ItemStack CircuitResonaticLuV = new ItemStack(MetaItem, 1, 15);
        ItemStack CircuitResonaticZPM = new ItemStack(MetaItem, 1, 16);
        ItemStack CircuitResonaticUV = new ItemStack(MetaItem, 1, 17);
        ItemStack CircuitResonaticUHV = new ItemStack(MetaItem, 1, 18);
        ItemStack CircuitResonaticUEV = new ItemStack(MetaItem, 1, 19);
        ItemStack CircuitResonaticUIV = new ItemStack(MetaItem, 1, 20);
        ItemStack VerySimpleCircuit = new ItemStack(MetaItem, 1, 21);
        ItemStack SimpleCircuit = new ItemStack(MetaItem, 1, 22);
        ItemStack BasicCircuit = new ItemStack(MetaItem, 1, 23);
        ItemStack AdvancedCircuit = new ItemStack(MetaItem, 1, 24);
        ItemStack EliteCircuit = new ItemStack(MetaItem, 1, 25);

        OreDictionary.registerOre("circuitPrimitive", CircuitResonaticULV);
        OreDictionary.registerOre("circuitBasic", CircuitResonaticLV);
        OreDictionary.registerOre("circuitGood", CircuitResonaticMV);
        OreDictionary.registerOre("circuitAdvanced", CircuitResonaticHV);
        OreDictionary.registerOre("circuitData", CircuitResonaticEV);
        OreDictionary.registerOre("circuitElite", CircuitResonaticIV);
        OreDictionary.registerOre("circuitMaster", CircuitResonaticLuV);
        OreDictionary.registerOre("circuitUltimate", CircuitResonaticZPM);
        OreDictionary.registerOre("circuitSuperconductor", CircuitResonaticUV);
        OreDictionary.registerOre("circuitInfinite", CircuitResonaticUHV);
        OreDictionary.registerOre("circuitBio", CircuitResonaticUEV);
        OreDictionary.registerOre("circuitOptical", CircuitResonaticUIV);
        OreDictionary.registerOre("circuitPrimitive", VerySimpleCircuit);
        OreDictionary.registerOre("circuitBasic", SimpleCircuit);
        OreDictionary.registerOre("circuitGood", BasicCircuit);
        OreDictionary.registerOre("circuitAdvanced", AdvancedCircuit);
        OreDictionary.registerOre("circuitData", EliteCircuit);

        GTOreDictUnificator.addToBlacklist(CircuitResonaticULV);
        GTOreDictUnificator.addToBlacklist(CircuitResonaticLV);
        GTOreDictUnificator.addToBlacklist(CircuitResonaticMV);
        GTOreDictUnificator.addToBlacklist(CircuitResonaticHV);
        GTOreDictUnificator.addToBlacklist(CircuitResonaticEV);
        GTOreDictUnificator.addToBlacklist(CircuitResonaticIV);
        GTOreDictUnificator.addToBlacklist(CircuitResonaticLuV);
        GTOreDictUnificator.addToBlacklist(CircuitResonaticZPM);
        GTOreDictUnificator.addToBlacklist(CircuitResonaticUV);
        GTOreDictUnificator.addToBlacklist(CircuitResonaticUHV);
        GTOreDictUnificator.addToBlacklist(CircuitResonaticUEV);
        GTOreDictUnificator.addToBlacklist(CircuitResonaticUIV);
        GTOreDictUnificator.addToBlacklist(VerySimpleCircuit);
        GTOreDictUnificator.addToBlacklist(SimpleCircuit);
        GTOreDictUnificator.addToBlacklist(BasicCircuit);
        GTOreDictUnificator.addToBlacklist(AdvancedCircuit);
        GTOreDictUnificator.addToBlacklist(EliteCircuit);
    }
}
