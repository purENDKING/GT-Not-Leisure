package com.science.gtnl.loader;

import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.addItemTooltip;

import com.science.gtnl.Utils.AnimatedText;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.common.hatch.CustomFluidHatch;
import com.science.gtnl.common.hatch.DebugEnergyHatch;
import com.science.gtnl.common.hatch.DualInputHatch;
import com.science.gtnl.common.hatch.DualOutputHatch;
import com.science.gtnl.common.hatch.HumongousInputBus;
import com.science.gtnl.common.hatch.HumongousNinefoldInputHatch;
import com.science.gtnl.common.hatch.HumongousSolidifierHatch;
import com.science.gtnl.common.hatch.NinefoldInputHatch;
import com.science.gtnl.common.hatch.SuperCraftingInputHatchME;
import com.science.gtnl.common.hatch.SuperCraftingInputProxy;
import com.science.gtnl.common.hatch.SuperDataAccessHatch;
import com.science.gtnl.common.machine.multiblock.AdvancedPhotovoltaicPowerStation;
import com.science.gtnl.common.machine.multiblock.BloodSoulSacrificialArray;
import com.science.gtnl.common.machine.multiblock.BrickedBlastFurnace;
import com.science.gtnl.common.machine.multiblock.CheatOreProcessingFactory;
import com.science.gtnl.common.machine.multiblock.ComponentAssembler;
import com.science.gtnl.common.machine.multiblock.DecayHastener;
import com.science.gtnl.common.machine.multiblock.Desulfurizer;
import com.science.gtnl.common.machine.multiblock.DraconicFusionCrafting;
import com.science.gtnl.common.machine.multiblock.EdenGarden;
import com.science.gtnl.common.machine.multiblock.ElementCopying;
import com.science.gtnl.common.machine.multiblock.EnergeticPhotovoltaicPowerStation;
import com.science.gtnl.common.machine.multiblock.GenerationEarthEngine;
import com.science.gtnl.common.machine.multiblock.GrandAssemblyLine;
import com.science.gtnl.common.machine.multiblock.HandOfJohnDavisonRockefeller;
import com.science.gtnl.common.machine.multiblock.IndustrialArcaneAssembler;
import com.science.gtnl.common.machine.multiblock.LapotronChip;
import com.science.gtnl.common.machine.multiblock.LargeBrewer;
import com.science.gtnl.common.machine.multiblock.LargeCircuitAssembler;
import com.science.gtnl.common.machine.multiblock.LargeIncubator;
import com.science.gtnl.common.machine.multiblock.LargeNaquadahReactor;
import com.science.gtnl.common.machine.multiblock.LargeSteamAlloySmelter;
import com.science.gtnl.common.machine.multiblock.LargeSteamChemicalBath;
import com.science.gtnl.common.machine.multiblock.LargeSteamCircuitAssembler;
import com.science.gtnl.common.machine.multiblock.LargeSteamCrusher;
import com.science.gtnl.common.machine.multiblock.LargeSteamExtractor;
import com.science.gtnl.common.machine.multiblock.LargeSteamExtruder;
import com.science.gtnl.common.machine.multiblock.LargeSteamFurnace;
import com.science.gtnl.common.machine.multiblock.LargeSteamThermalCentrifuge;
import com.science.gtnl.common.machine.multiblock.LibraryOfRuina;
import com.science.gtnl.common.machine.multiblock.MatterFabricator;
import com.science.gtnl.common.machine.multiblock.MeteorMiner;
import com.science.gtnl.common.machine.multiblock.NeutroniumWireCutting;
import com.science.gtnl.common.machine.multiblock.NineIndustrialMultiMachine;
import com.science.gtnl.common.machine.multiblock.PetrochemicalPlant;
import com.science.gtnl.common.machine.multiblock.PrimitiveDistillationTower;
import com.science.gtnl.common.machine.multiblock.ProcessingArray;
import com.science.gtnl.common.machine.multiblock.RareEarthCentrifugal;
import com.science.gtnl.common.machine.multiblock.ReactionFurnace;
import com.science.gtnl.common.machine.multiblock.RealArtificialStar;
import com.science.gtnl.common.machine.multiblock.SmeltingMixingFurnace;
import com.science.gtnl.common.machine.multiblock.SteamCracking;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.AlloyBlastSmelter;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.BlazeBlastFurnace;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.ChemicalPlant;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.ColdIceFreezer;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.Digester;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.ElectricImplosionCompressor;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.EnergyInfuser;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.FishingGround;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.FlotationCellRegulator;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.Incubator;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.IsaMill;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeAlloySmelter;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeArcSmelter;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeAssembler;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeAutoclave;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeBender;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeCanning;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeCentrifuge;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeChemicalBath;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeCutter;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeDistillery;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeElectrolyzer;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeElectromagnet;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeEngravingLaser;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeExtractor;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeExtruder;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeForming;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeHammer;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeIndustrialLathe;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeMacerationTower;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeMaterialPress;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeMixer;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargePacker;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargePyrolyseOven;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeSiftingFunnel;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeSolidifier;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeSteamOreWasher;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeWiremill;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.MegaAlloyBlastSmelter;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.MegaBlastFurnace;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.MolecularTransformer;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.PrecisionAssembler;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.VacuumDryingFurnace;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.VacuumFreezer;
import com.science.gtnl.common.machine.multiblock.TeleportationArrayToAlfheim;
import com.science.gtnl.common.machine.multiblock.VibrantPhotovoltaicPowerStation;
import com.science.gtnl.common.machine.multiblock.WhiteNightGenerator;
import com.science.gtnl.common.machine.multiblock.WoodDistillation;
import com.science.gtnl.common.materials.MaterialPool;

import goodgenerator.util.CrackRecipeAdder;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.util.minecraft.FluidUtils;

public class MachineLoader {

    public static void loadMachines() {

        GTNLItemList.EdenGarden.set(new EdenGarden(21004, "EdenGarden", TextLocalization.NameEdenGarden));
        addItemTooltip(GTNLItemList.EdenGarden.get(1), AnimatedText.SNL_EDEN_GARDEN);

        GTNLItemList.LargeSteamCircuitAssembler.set(
            new LargeSteamCircuitAssembler(
                21005,
                "LargeSteamCircuitAssembler",
                TextLocalization.NameLargeSteamCircuitAssembler));
        addItemTooltip(GTNLItemList.LargeSteamCircuitAssembler.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.GenerationEarthEngine
            .set(new GenerationEarthEngine(21006, "GenerationEarthEngine", TextLocalization.NameGenerationEarthEngine));
        addItemTooltip(GTNLItemList.GenerationEarthEngine.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.BloodSoulSacrificialArray.set(
            new BloodSoulSacrificialArray(
                21007,
                "BloodSoulSacrificialArray",
                TextLocalization.NameBloodSoulSacrificialArray));
        addItemTooltip(GTNLItemList.BloodSoulSacrificialArray.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.RealArtificialStar
            .set(new RealArtificialStar(21008, "RealArtificialStar", TextLocalization.NameRealArtificialStar));
        addItemTooltip(GTNLItemList.RealArtificialStar.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.TeleportationArrayToAlfheim.set(
            new TeleportationArrayToAlfheim(
                21009,
                "TeleportationArrayToAlfheim",
                TextLocalization.NameTeleportationArrayToAlfheim));
        addItemTooltip(GTNLItemList.TeleportationArrayToAlfheim.get(1), AnimatedText.SNL_NLXCJH);

        GTNLItemList.LapotronChip.set(new LapotronChip(21010, "LapotronChip", TextLocalization.NameLapotronChip));
        addItemTooltip(GTNLItemList.LapotronChip.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NeutroniumWireCutting
            .set(new NeutroniumWireCutting(21011, "NeutroniumWireCutting", TextLocalization.NameNeutroniumWireCutting));
        addItemTooltip(GTNLItemList.NeutroniumWireCutting.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeSteamCrusher
            .set(new LargeSteamCrusher(21012, "LargeSteamCrusher", TextLocalization.NameLargeSteamCrusher));
        addItemTooltip(GTNLItemList.LargeSteamCrusher.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.ComponentAssembler
            .set(new ComponentAssembler(21013, "ComponentAssembler", TextLocalization.NameComponentAssembler));
        addItemTooltip(GTNLItemList.ComponentAssembler.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeSteamFurnace
            .set(new LargeSteamFurnace(21014, "LargeSteamFurnace", TextLocalization.NameLargeSteamFurnace));
        addItemTooltip(GTNLItemList.LargeSteamFurnace.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeSteamAlloySmelter.set(
            new LargeSteamAlloySmelter(21015, "LargeSteamAlloySmelter", TextLocalization.NameLargeSteamAlloySmelter));
        addItemTooltip(GTNLItemList.LargeSteamAlloySmelter.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeSteamThermalCentrifuge.set(
            new LargeSteamThermalCentrifuge(
                21016,
                "LargeSteamThermalCentrifuge",
                TextLocalization.NameLargeSteamThermalCentrifuge));
        addItemTooltip(GTNLItemList.LargeSteamThermalCentrifuge.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.SteamCracking.set(new SteamCracking(21017, "SteamCracking", TextLocalization.NameSteamCracking));
        addItemTooltip(GTNLItemList.SteamCracking.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeSteamChemicalBath.set(
            new LargeSteamChemicalBath(21018, "LargeSteamChemicalBath", TextLocalization.NameLargeSteamChemicalBath));
        addItemTooltip(GTNLItemList.LargeSteamChemicalBath.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.PrimitiveDistillationTower.set(
            new PrimitiveDistillationTower(
                21019,
                "PrimitiveDistillationTower",
                TextLocalization.NamePrimitiveDistillationTower));
        addItemTooltip(GTNLItemList.PrimitiveDistillationTower.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.MeteorMiner.set(new MeteorMiner(21020, "MeteorMiner", TextLocalization.NameMeteorMiner));
        addItemTooltip(GTNLItemList.MeteorMiner.get(1), AnimatedText.SNL_TOTTO);

        GTNLItemList.Desulfurizer.set(new Desulfurizer(21021, "Desulfurizer", TextLocalization.NameDesulfurizer));
        addItemTooltip(GTNLItemList.Desulfurizer.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeCircuitAssembler
            .set(new LargeCircuitAssembler(21022, "LargeCircuitAssembler", TextLocalization.NameLargeCircuitAssembler));
        addItemTooltip(GTNLItemList.LargeCircuitAssembler.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.PetrochemicalPlant
            .set(new PetrochemicalPlant(21023, "PetrochemicalPlant", TextLocalization.NamePetrochemicalPlant));
        addItemTooltip(GTNLItemList.PetrochemicalPlant.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.SmeltingMixingFurnace
            .set(new SmeltingMixingFurnace(21024, "SmeltingMixingFurnace", TextLocalization.NameSmeltingMixingFurnace));
        addItemTooltip(GTNLItemList.SmeltingMixingFurnace.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.WhiteNightGenerator
            .set(new WhiteNightGenerator(21025, "WhiteNightGenerator", TextLocalization.NameWhiteNightGenerator));
        addItemTooltip(GTNLItemList.WhiteNightGenerator.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ProcessingArray
            .set(new ProcessingArray(21026, "ProcessingArray", TextLocalization.NameProcessingArrayGTNL));
        addItemTooltip(GTNLItemList.ProcessingArray.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.MegaBlastFurnace
            .set(new MegaBlastFurnace(21027, "MegaBlastFurnace", TextLocalization.NameMegaBlastFurnace));
        addItemTooltip(GTNLItemList.MegaBlastFurnace.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.BrickedBlastFurnace
            .set(new BrickedBlastFurnace(21028, "BrickedBlastFurnace", TextLocalization.NameBrickedBlastFurnace));
        addItemTooltip(GTNLItemList.BrickedBlastFurnace.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.RareEarthCentrifugal
            .set(new RareEarthCentrifugal(21029, "RareEarthCentrifugal", TextLocalization.NameRareEarthCentrifugal));
        addItemTooltip(GTNLItemList.RareEarthCentrifugal.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.ColdIceFreezer
            .set(new ColdIceFreezer(21030, "ColdIceFreezer", TextLocalization.NameColdIceFreezer));
        addItemTooltip(GTNLItemList.ColdIceFreezer.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.BlazeBlastFurnace
            .set(new BlazeBlastFurnace(21031, "BlazeBlastFurnace", TextLocalization.NameBlazeBlastFurnace));
        addItemTooltip(GTNLItemList.BlazeBlastFurnace.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.ChemicalPlant.set(new ChemicalPlant(21032, "ChemicalPlant", TextLocalization.NameChemicalPlant));
        addItemTooltip(GTNLItemList.ChemicalPlant.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.VacuumFreezer.set(new VacuumFreezer(21033, "VacuumFreezer", TextLocalization.NameVacuumFreezer));
        addItemTooltip(GTNLItemList.VacuumFreezer.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.IndustrialArcaneAssembler.set(
            new IndustrialArcaneAssembler(
                21034,
                "IndustrialArcaneAssembler",
                TextLocalization.NameIndustrialArcaneAssembler));
        addItemTooltip(GTNLItemList.IndustrialArcaneAssembler.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.EnergeticPhotovoltaicPowerStation.set(
            new EnergeticPhotovoltaicPowerStation(
                21035,
                "EnergeticPhotovoltaicPowerStation",
                TextLocalization.NameEnergeticPhotovoltaicPowerStation));
        addItemTooltip(GTNLItemList.EnergeticPhotovoltaicPowerStation.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.AdvancedPhotovoltaicPowerStation.set(
            new AdvancedPhotovoltaicPowerStation(
                21036,
                "AdvancedPhotovoltaicPowerStation",
                TextLocalization.NameAdvancedPhotovoltaicPowerStation));
        addItemTooltip(GTNLItemList.AdvancedPhotovoltaicPowerStation.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.VibrantPhotovoltaicPowerStation.set(
            new VibrantPhotovoltaicPowerStation(
                21037,
                "VibrantPhotovoltaicPowerStation",
                TextLocalization.NameVibrantPhotovoltaicPowerStation));
        addItemTooltip(GTNLItemList.VibrantPhotovoltaicPowerStation.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeMacerationTower
            .set(new LargeMacerationTower(21038, "LargeMacerationTower", TextLocalization.NameLargeMacerationTower));
        addItemTooltip(GTNLItemList.LargeMacerationTower.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.HandOfJohnDavisonRockefeller.set(
            new HandOfJohnDavisonRockefeller(
                21039,
                "HandOfJohnDavisonRockefeller",
                TextLocalization.NameHandOfJohnDavisonRockefeller));
        addItemTooltip(GTNLItemList.HandOfJohnDavisonRockefeller.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeSiftingFunnel
            .set(new LargeSiftingFunnel(21040, "LargeSiftingFunnel", TextLocalization.NameLargeSiftingFunnel));
        addItemTooltip(GTNLItemList.LargeSiftingFunnel.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeCutter.set(new LargeCutter(21041, "LargeCutter", TextLocalization.NameLargeCutter));
        addItemTooltip(GTNLItemList.LargeCutter.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeBrewer.set(new LargeBrewer(21042, "LargeBrewer", TextLocalization.NameLargeBrewer));
        addItemTooltip(GTNLItemList.LargeBrewer.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeIndustrialLathe
            .set(new LargeIndustrialLathe(21043, "LargeIndustrialLathe", TextLocalization.NameLargeIndustrialLathe));
        addItemTooltip(GTNLItemList.LargeIndustrialLathe.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeMaterialPress
            .set(new LargeMaterialPress(21044, "LargeMaterialPress", TextLocalization.NameLargeMaterialPress));
        addItemTooltip(GTNLItemList.LargeMaterialPress.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeWiremill.set(new LargeWiremill(21045, "LargeWiremill", TextLocalization.NameLargeWiremill));
        addItemTooltip(GTNLItemList.LargeWiremill.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeBender.set(new LargeBender(21046, "LargeBender", TextLocalization.NameLargeBender));
        addItemTooltip(GTNLItemList.LargeBender.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.ElectricImplosionCompressor.set(
            new ElectricImplosionCompressor(
                21047,
                "ElectricImplosionCompressor",
                TextLocalization.NameElectricImplosionCompressor));
        addItemTooltip(GTNLItemList.ElectricImplosionCompressor.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.LargeExtruder.set(new LargeExtruder(21048, "LargeExtruder", TextLocalization.NameLargeExtruder));
        addItemTooltip(GTNLItemList.LargeExtruder.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeArcSmelter
            .set(new LargeArcSmelter(21049, "LargeArcSmelter", TextLocalization.NameLargeArcSmelter));
        addItemTooltip(GTNLItemList.LargeArcSmelter.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeForming.set(new LargeForming(21050, "LargeForming", TextLocalization.NameLargeForming));
        addItemTooltip(GTNLItemList.LargeForming.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.MatterFabricator
            .set(new MatterFabricator(21051, "MatterFabricator", TextLocalization.NameMatterFabricator));
        addItemTooltip(GTNLItemList.MatterFabricator.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeElectrolyzer
            .set(new LargeElectrolyzer(21052, "LargeElectrolyzer", TextLocalization.NameLargeElectrolyzer));
        addItemTooltip(GTNLItemList.LargeElectrolyzer.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeElectromagnet
            .set(new LargeElectromagnet(21053, "LargeElectromagnet", TextLocalization.NameLargeElectromagnet));
        addItemTooltip(GTNLItemList.LargeElectromagnet.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeAssembler
            .set(new LargeAssembler(21054, "LargeAssembler", TextLocalization.NameLargeAssembler));
        addItemTooltip(GTNLItemList.LargeAssembler.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeMixer.set(new LargeMixer(21055, "LargeMixer", TextLocalization.NameLargeMixer));
        addItemTooltip(GTNLItemList.LargeMixer.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeCentrifuge
            .set(new LargeCentrifuge(21056, "LargeCentrifuge", TextLocalization.NameLargeCentrifuge));
        addItemTooltip(GTNLItemList.LargeCentrifuge.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LibraryOfRuina
            .set(new LibraryOfRuina(21057, "LibraryOfRuina", TextLocalization.NameLibraryOfRuina));
        addItemTooltip(GTNLItemList.LibraryOfRuina.get(1), AnimatedText.SNL_NLXCJH);

        GTNLItemList.LargeChemicalBath
            .set(new LargeChemicalBath(21058, "LargeChemicalBath", TextLocalization.NameLargeChemicalBath));
        addItemTooltip(GTNLItemList.LargeChemicalBath.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeAutoclave
            .set(new LargeAutoclave(21059, "LargeAutoclave", TextLocalization.NameLargeAutoclave));
        addItemTooltip(GTNLItemList.LargeAutoclave.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeSolidifier
            .set(new LargeSolidifier(21060, "LargeSolidifier", TextLocalization.NameLargeSolidifier));
        addItemTooltip(GTNLItemList.LargeSolidifier.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeExtractor
            .set(new LargeExtractor(21061, "LargeExtractor", TextLocalization.NameLargeExtractor));
        addItemTooltip(GTNLItemList.LargeExtractor.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.ReactionFurnace
            .set(new ReactionFurnace(21062, "ReactionFurnace", TextLocalization.NameReactionFurnace));
        addItemTooltip(GTNLItemList.ReactionFurnace.get(1), AnimatedText.SNL_NLXCJH);

        GTNLItemList.EnergyInfuser.set(new EnergyInfuser(21063, "EnergyInfuser", TextLocalization.NameEnergyInfuser));
        addItemTooltip(GTNLItemList.EnergyInfuser.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.LargeCanning.set(new LargeCanning(21064, "LargeCanning", TextLocalization.NameLargeCanning));
        addItemTooltip(GTNLItemList.LargeCanning.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.Digester.set(new Digester(21065, "Digester", TextLocalization.NameDigester));
        addItemTooltip(GTNLItemList.Digester.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.AlloyBlastSmelter
            .set(new AlloyBlastSmelter(21066, "AlloyBlastSmelter", TextLocalization.NameAlloyBlastSmelter));
        addItemTooltip(GTNLItemList.AlloyBlastSmelter.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeSteamExtractor
            .set(new LargeSteamExtractor(21067, "LargeSteamExtractor", TextLocalization.NameLargeSteamExtractor));
        addItemTooltip(GTNLItemList.LargeSteamExtractor.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeSteamOreWasher
            .set(new LargeSteamOreWasher(21068, "LargeSteamOreWasher", TextLocalization.NameLargeSteamOreWasher));
        addItemTooltip(GTNLItemList.LargeSteamOreWasher.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.LargeHammer.set(new LargeHammer(21069, "LargeHammer", TextLocalization.NameLargeHammer));
        addItemTooltip(GTNLItemList.LargeHammer.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.IsaMill.set(new IsaMill(21070, "IsaMill", TextLocalization.NameIsaMill));
        addItemTooltip(GTNLItemList.IsaMill.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.FlotationCellRegulator.set(
            new FlotationCellRegulator(21071, "FlotationCellRegulator", TextLocalization.NameFlotationCellRegulator));
        addItemTooltip(GTNLItemList.FlotationCellRegulator.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.VacuumDryingFurnace
            .set(new VacuumDryingFurnace(21072, "VacuumDryingFurnace", TextLocalization.NameVacuumDryingFurnace));
        addItemTooltip(GTNLItemList.VacuumDryingFurnace.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeDistillery
            .set(new LargeDistillery(21073, "LargeDistillery", TextLocalization.NameLargeDistillery));
        addItemTooltip(GTNLItemList.LargeDistillery.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.Incubator.set(new Incubator(21074, "Incubator", TextLocalization.NameIncubator));
        addItemTooltip(GTNLItemList.Incubator.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.LargeIncubator
            .set(new LargeIncubator(21075, "LargeIncubator", TextLocalization.NameLargeIncubator));
        addItemTooltip(GTNLItemList.LargeIncubator.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeEngravingLaser
            .set(new LargeEngravingLaser(21076, "LargeEngravingLaser", TextLocalization.NameLargeEngravingLaser));
        addItemTooltip(GTNLItemList.LargeEngravingLaser.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.FishingGround.set(new FishingGround(21077, "FishingGround", TextLocalization.NameFishingGround));
        addItemTooltip(GTNLItemList.FishingGround.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.ElementCopying
            .set(new ElementCopying(21078, "ElementCopying", TextLocalization.NameElementCopying));
        addItemTooltip(GTNLItemList.ElementCopying.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.WoodDistillation
            .set(new WoodDistillation(21079, "WoodDistillation", TextLocalization.NameWoodDistillation));
        addItemTooltip(GTNLItemList.WoodDistillation.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargePacker.set(new LargePacker(21080, "LargePacker", TextLocalization.NameLargePacker));
        addItemTooltip(GTNLItemList.LargePacker.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeAlloySmelter
            .set(new LargeAlloySmelter(21081, "LargeAlloySmelter", TextLocalization.NameLargeAlloySmelter));
        addItemTooltip(GTNLItemList.LargeAlloySmelter.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.MolecularTransformer
            .set(new MolecularTransformer(21082, "MolecularTransformer", TextLocalization.NameMolecularTransformer));
        addItemTooltip(GTNLItemList.MolecularTransformer.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.LargePyrolyseOven
            .set(new LargePyrolyseOven(21083, "LargePyrolyseOven", TextLocalization.NameLargePyrolyseOven));
        addItemTooltip(GTNLItemList.LargePyrolyseOven.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.LargeNaquadahReactor
            .set(new LargeNaquadahReactor(21084, "LargeNaquadahReactor", TextLocalization.NameLargeNaquadahReactor));
        addItemTooltip(GTNLItemList.LargeNaquadahReactor.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.DraconicFusionCrafting.set(
            new DraconicFusionCrafting(21085, "DraconicFusionCrafting", TextLocalization.NameDraconicFusionCrafting));
        addItemTooltip(GTNLItemList.DraconicFusionCrafting.get(1), AnimatedText.SNL_NLXCJH);

        GTNLItemList.LargeSteamExtruder
            .set(new LargeSteamExtruder(21086, "LargeSteamExtruder", TextLocalization.NameLargeSteamExtruder));
        addItemTooltip(GTNLItemList.LargeSteamExtruder.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.DecayHastener.set(new DecayHastener(21087, "DecayHastener", TextLocalization.NameDecayHastener));
        addItemTooltip(GTNLItemList.DecayHastener.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.PreciseAssembler
            .set(new PrecisionAssembler(21088, "PrecisionAssembler", TextLocalization.NamePrecisionAssembler));
        addItemTooltip(GTNLItemList.PreciseAssembler.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.MegaAlloyBlastSmelter
            .set(new MegaAlloyBlastSmelter(21089, "MegaAlloyBlastSmelter", TextLocalization.NameMegaAlloyBlastSmelter));
        addItemTooltip(GTNLItemList.MegaAlloyBlastSmelter.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.GrandAssemblyLine
            .set(new GrandAssemblyLine(21090, "GrandAssemblyLine", TextLocalization.NameGrandAssemblyLine));
        addItemTooltip(GTNLItemList.GrandAssemblyLine.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.CheatOreProcessingFactory.set(
            new CheatOreProcessingFactory(
                21919,
                "CheatOreProcessingFactory",
                TextLocalization.NameCheatOreProcessingFactory));
        addItemTooltip(GTNLItemList.CheatOreProcessingFactory.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.NineIndustrialMultiMachine.set(
            new NineIndustrialMultiMachine(
                21920,
                "NineIndustrialMultiMachine",
                TextLocalization.NameNineIndustrialMultiMachine));
        addItemTooltip(GTNLItemList.NineIndustrialMultiMachine.get(1), AnimatedText.SNL_QYZG);
    }

    public static void registerMTEHatch() {

        GTNLItemList.FluidManaInputHatch.set(
            new CustomFluidHatch(
                FluidUtils.getFluidStack("fluidmana", 1)
                    .getFluid(),
                512000,
                21501,
                "Fluid Mana Input Hatch",
                TextLocalization.FluidManaInputHatch,
                6));
        addItemTooltip(GTNLItemList.FluidManaInputHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.FluidIceInputHatch.set(
            new CustomFluidHatch(
                FluidUtils.getFluidStack("ice", 1)
                    .getFluid(),
                256000,
                21502,
                "Fluid Ice Input Hatch",
                TextLocalization.FluidIceInputHatch,
                5));
        addItemTooltip(GTNLItemList.FluidIceInputHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.FluidBlazeInputHatch.set(
            new CustomFluidHatch(
                FluidUtils.getFluidStack("molten.blaze", 1)
                    .getFluid(),
                256000,
                21503,
                "Fluid Blaze Input Hatch",
                TextLocalization.FluidBlazeInputHatch,
                5));
        addItemTooltip(GTNLItemList.FluidBlazeInputHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SuperCraftingInputHatchME.set(
            new SuperCraftingInputHatchME(
                21504,
                "Super Crafting Input Buffer (ME)",
                TextLocalization.SuperCraftingInputHatchME,
                true));
        addItemTooltip(GTNLItemList.SuperCraftingInputHatchME.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SuperCraftingInputBusME.set(
            new SuperCraftingInputHatchME(
                21505,
                "Super Crafting Input Bus (ME)",
                TextLocalization.SuperCraftingInputBusME,
                false));
        addItemTooltip(GTNLItemList.SuperCraftingInputBusME.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousSolidifierHatch.set(
            new HumongousSolidifierHatch(
                21506,
                "Humongous Solidifier Hatch",
                TextLocalization.HumongousSolidifierHatch,
                14));
        addItemTooltip(GTNLItemList.HumongousSolidifierHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DebugEnergyHatch
            .set(new DebugEnergyHatch(21507, "Debug Energy Hatch", TextLocalization.DebugEnergyHatch, 1));
        addItemTooltip(GTNLItemList.DebugEnergyHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchEV
            .set(new NinefoldInputHatch(21508, 9, "Ninefold Input Hatch EV", TextLocalization.NinefoldInputHatchEV, 4));
        addItemTooltip(GTNLItemList.NinefoldInputHatchEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchIV
            .set(new NinefoldInputHatch(21509, 9, "Ninefold Input Hatch IV", TextLocalization.NinefoldInputHatchIV, 5));
        addItemTooltip(GTNLItemList.NinefoldInputHatchIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchLuV.set(
            new NinefoldInputHatch(21510, 9, "Ninefold Input Hatch LuV", TextLocalization.NinefoldInputHatchLuV, 6));
        addItemTooltip(GTNLItemList.NinefoldInputHatchLuV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchZPM.set(
            new NinefoldInputHatch(21511, 9, "Ninefold Input Hatch ZPM", TextLocalization.NinefoldInputHatchZPM, 7));
        addItemTooltip(GTNLItemList.NinefoldInputHatchZPM.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchUV
            .set(new NinefoldInputHatch(21512, 9, "Ninefold Input Hatch UV", TextLocalization.NinefoldInputHatchUV, 8));
        addItemTooltip(GTNLItemList.NinefoldInputHatchUV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchUHV.set(
            new NinefoldInputHatch(21513, 9, "Ninefold Input Hatch UHV", TextLocalization.NinefoldInputHatchUHV, 9));
        addItemTooltip(GTNLItemList.NinefoldInputHatchUHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchUEV.set(
            new NinefoldInputHatch(21514, 9, "Ninefold Input Hatch UEV", TextLocalization.NinefoldInputHatchUEV, 10));
        addItemTooltip(GTNLItemList.NinefoldInputHatchUEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchUIV.set(
            new NinefoldInputHatch(21515, 9, "Ninefold Input Hatch UIV", TextLocalization.NinefoldInputHatchUIV, 11));
        addItemTooltip(GTNLItemList.NinefoldInputHatchUIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchUMV.set(
            new NinefoldInputHatch(21516, 9, "Ninefold Input Hatch UMV", TextLocalization.NinefoldInputHatchUMV, 12));
        addItemTooltip(GTNLItemList.NinefoldInputHatchUMV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchUXV.set(
            new NinefoldInputHatch(21517, 9, "Ninefold Input Hatch UXV", TextLocalization.NinefoldInputHatchUXV, 13));
        addItemTooltip(GTNLItemList.NinefoldInputHatchUXV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchMAX.set(
            new NinefoldInputHatch(21518, 9, "Ninefold Input Hatch MAX", TextLocalization.NinefoldInputHatchMAX, 14));
        addItemTooltip(GTNLItemList.NinefoldInputHatchMAX.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousNinefoldInputHatch.set(
            new HumongousNinefoldInputHatch(
                21519,
                9,
                "Humongous Ninefold Input Hatch",
                TextLocalization.HumongousNinefoldInputHatch));
        addItemTooltip(GTNLItemList.HumongousNinefoldInputHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchLV
            .set(new DualInputHatch(21520, "Dual Input Hatch LV", TextLocalization.DualInputHatchLV, 1));
        addItemTooltip(GTNLItemList.DualInputHatchLV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchMV
            .set(new DualInputHatch(21521, "Dual Input Hatch MV", TextLocalization.DualInputHatchMV, 2));
        addItemTooltip(GTNLItemList.DualInputHatchMV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchHV
            .set(new DualInputHatch(21522, "Dual Input Hatch HV", TextLocalization.DualInputHatchHV, 3));
        addItemTooltip(GTNLItemList.DualInputHatchHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchEV
            .set(new DualInputHatch(21523, "Dual Input Hatch EV", TextLocalization.DualInputHatchEV, 4));
        addItemTooltip(GTNLItemList.DualInputHatchEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchIV
            .set(new DualInputHatch(21524, "Dual Input Hatch IV", TextLocalization.DualInputHatchIV, 5));
        addItemTooltip(GTNLItemList.DualInputHatchIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchLuV
            .set(new DualInputHatch(21525, "Dual Input Hatch LuV", TextLocalization.DualInputHatchLuV, 6));
        addItemTooltip(GTNLItemList.DualInputHatchLuV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchZPM
            .set(new DualInputHatch(21526, "Dual Input Hatch ZPM", TextLocalization.DualInputHatchZPM, 7));
        addItemTooltip(GTNLItemList.DualInputHatchZPM.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchUV
            .set(new DualInputHatch(21527, "Dual Input Hatch UV", TextLocalization.DualInputHatchUV, 8));
        addItemTooltip(GTNLItemList.DualInputHatchUV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchUHV
            .set(new DualInputHatch(21528, "Dual Input Hatch UHV", TextLocalization.DualInputHatchUHV, 9));
        addItemTooltip(GTNLItemList.DualInputHatchUHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchUEV
            .set(new DualInputHatch(21529, "Dual Input Hatch UEV", TextLocalization.DualInputHatchUEV, 10));
        addItemTooltip(GTNLItemList.DualInputHatchUEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchUIV
            .set(new DualInputHatch(21530, "Dual Input Hatch UIV", TextLocalization.DualInputHatchUIV, 11));
        addItemTooltip(GTNLItemList.DualInputHatchUIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchUMV
            .set(new DualInputHatch(21531, "Dual Input Hatch UMV", TextLocalization.DualInputHatchUMV, 12));
        addItemTooltip(GTNLItemList.DualInputHatchUMV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchUXV
            .set(new DualInputHatch(21532, "Dual Input Hatch UXV", TextLocalization.DualInputHatchUXV, 13));
        addItemTooltip(GTNLItemList.DualInputHatchUXV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchMAX
            .set(new DualInputHatch(21533, "Dual Input Hatch MAX", TextLocalization.DualInputHatchMAX, 14));
        addItemTooltip(GTNLItemList.DualInputHatchMAX.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SuperCraftingInputProxy.set(
            new SuperCraftingInputProxy(21534, "Super Crafting Input Proxy", TextLocalization.SuperCraftingInputProxy)
                .getStackForm(1L));
        addItemTooltip(GTNLItemList.SuperCraftingInputProxy.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.QuadrupleOutputHatchEV.set(
            new DualOutputHatch(22500, 4, "Quadruple Output Hatch EV", TextLocalization.QuadrupleOutputHatchEV, 4));
        addItemTooltip(GTNLItemList.QuadrupleOutputHatchEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldOutputHatchEV
            .set(new DualOutputHatch(22501, 9, "Ninefold Output Hatch EV", TextLocalization.NinefoldOutputHatchEV, 4));
        addItemTooltip(GTNLItemList.NinefoldOutputHatchEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusULV
            .set(new HumongousInputBus(22502, "Humongous Input Bus ULV", TextLocalization.HumongousInputBusULV, 0));
        addItemTooltip(GTNLItemList.HumongousInputBusULV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusLV
            .set(new HumongousInputBus(22503, "Humongous Input Bus LV", TextLocalization.HumongousInputBusLV, 1));
        addItemTooltip(GTNLItemList.HumongousInputBusLV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusMV
            .set(new HumongousInputBus(22504, "Humongous Input Bus MV", TextLocalization.HumongousInputBusMV, 2));
        addItemTooltip(GTNLItemList.HumongousInputBusMV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusHV
            .set(new HumongousInputBus(22505, "Humongous Input Bus HV", TextLocalization.HumongousInputBusHV, 3));
        addItemTooltip(GTNLItemList.HumongousInputBusHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusEV
            .set(new HumongousInputBus(22506, "Humongous Input Bus EV", TextLocalization.HumongousInputBusEV, 4));
        addItemTooltip(GTNLItemList.HumongousInputBusEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusIV
            .set(new HumongousInputBus(22507, "Humongous Input Bus IV", TextLocalization.HumongousInputBusIV, 5));
        addItemTooltip(GTNLItemList.HumongousInputBusIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusLuV
            .set(new HumongousInputBus(22508, "Humongous Input Bus LuV", TextLocalization.HumongousInputBusLuV, 6));
        addItemTooltip(GTNLItemList.HumongousInputBusLuV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusZPM
            .set(new HumongousInputBus(22509, "Humongous Input Bus ZPM", TextLocalization.HumongousInputBusZPM, 7));
        addItemTooltip(GTNLItemList.HumongousInputBusZPM.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusUV
            .set(new HumongousInputBus(22510, "Humongous Input Bus UV", TextLocalization.HumongousInputBusUV, 8));
        addItemTooltip(GTNLItemList.HumongousInputBusUV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusUHV
            .set(new HumongousInputBus(22511, "Humongous Input Bus UHV", TextLocalization.HumongousInputBusUHV, 9));
        addItemTooltip(GTNLItemList.HumongousInputBusUHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusUEV
            .set(new HumongousInputBus(22512, "Humongous Input Bus UEV", TextLocalization.HumongousInputBusUEV, 10));
        addItemTooltip(GTNLItemList.HumongousInputBusUEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusUIV
            .set(new HumongousInputBus(22513, "Humongous Input Bus UIV", TextLocalization.HumongousInputBusUIV, 11));
        addItemTooltip(GTNLItemList.HumongousInputBusUIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusUMV
            .set(new HumongousInputBus(22514, "Humongous Input Bus UMV", TextLocalization.HumongousInputBusUMV, 12));
        addItemTooltip(GTNLItemList.HumongousInputBusUMV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusUXV
            .set(new HumongousInputBus(22515, "Humongous Input Bus UXV", TextLocalization.HumongousInputBusUXV, 13));
        addItemTooltip(GTNLItemList.HumongousInputBusUXV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusMAX
            .set(new HumongousInputBus(22516, "Humongous Input Bus MAX", TextLocalization.HumongousInputBusMAX, 14));
        addItemTooltip(GTNLItemList.HumongousInputBusMAX.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SuperDataAccessHatch
            .set(new SuperDataAccessHatch(22517, "Super Data Access Hatch", TextLocalization.SuperDataAccessHatch, 14));
        addItemTooltip(GTNLItemList.SuperDataAccessHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

    }

    public static void registerMTEWire() {
        CrackRecipeAdder.registerWire(22701, MaterialPool.Stargate, 2147483647, 2147483647, 0, true);
    }

    public static void run() {
        Logger.INFO("GTNL Content | Registering MTE Block Machine.");
        registerMTEHatch();
        loadMachines();
        registerMTEWire();
    }
}
