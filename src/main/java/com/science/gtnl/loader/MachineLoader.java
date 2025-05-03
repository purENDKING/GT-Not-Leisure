package com.science.gtnl.loader;

import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.*;
import static com.science.gtnl.common.GTNLMachineID.*;
import static gregtech.api.enums.Textures.BlockIcons.*;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_VALVE;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import com.google.common.collect.ImmutableSet;
import com.science.gtnl.Mods;
import com.science.gtnl.Utils.AnimatedText;
import com.science.gtnl.Utils.MoreMaterialToolUtil;
import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.common.block.Casings.BasicBlocks;
import com.science.gtnl.common.machine.basicMachine.ManaTank;
import com.science.gtnl.common.machine.basicMachine.SteamAssemblerBronze;
import com.science.gtnl.common.machine.basicMachine.SteamAssemblerSteel;
import com.science.gtnl.common.machine.basicMachine.SteamTurbine;
import com.science.gtnl.common.machine.hatch.CustomFluidHatch;
import com.science.gtnl.common.machine.hatch.DebugEnergyHatch;
import com.science.gtnl.common.machine.hatch.DualInputHatch;
import com.science.gtnl.common.machine.hatch.DualOutputHatch;
import com.science.gtnl.common.machine.hatch.HumongousInputBus;
import com.science.gtnl.common.machine.hatch.HumongousNinefoldInputHatch;
import com.science.gtnl.common.machine.hatch.HumongousSolidifierHatch;
import com.science.gtnl.common.machine.hatch.ManaDynamoHatch;
import com.science.gtnl.common.machine.hatch.ManaEnergyHatch;
import com.science.gtnl.common.machine.hatch.NinefoldInputHatch;
import com.science.gtnl.common.machine.hatch.ParallelControllerHatch;
import com.science.gtnl.common.machine.hatch.SuperCraftingInputHatchME;
import com.science.gtnl.common.machine.hatch.SuperCraftingInputProxy;
import com.science.gtnl.common.machine.hatch.SuperDataAccessHatch;
import com.science.gtnl.common.machine.multiblock.AdvancedCircuitAssemblyLine;
import com.science.gtnl.common.machine.multiblock.AdvancedInfiniteDriller;
import com.science.gtnl.common.machine.multiblock.AdvancedPhotovoltaicPowerStation;
import com.science.gtnl.common.machine.multiblock.AprilFool.HighPressureSteamFusionReactor;
import com.science.gtnl.common.machine.multiblock.AprilFool.MegaSolarBoiler;
import com.science.gtnl.common.machine.multiblock.AprilFool.MegaSteamCompressor;
import com.science.gtnl.common.machine.multiblock.AprilFool.SteamCactusWonder;
import com.science.gtnl.common.machine.multiblock.AprilFool.SteamCarpenter;
import com.science.gtnl.common.machine.multiblock.AprilFool.SteamExtractinator;
import com.science.gtnl.common.machine.multiblock.AprilFool.SteamFusionReactor;
import com.science.gtnl.common.machine.multiblock.AprilFool.SteamGateAssembler;
import com.science.gtnl.common.machine.multiblock.AprilFool.SteamInfernalCokeOven;
import com.science.gtnl.common.machine.multiblock.AprilFool.SteamLavaMaker;
import com.science.gtnl.common.machine.multiblock.AprilFool.SteamManufacturer;
import com.science.gtnl.common.machine.multiblock.AprilFool.SteamRockBreaker;
import com.science.gtnl.common.machine.multiblock.AprilFool.SteamWoodcutter;
import com.science.gtnl.common.machine.multiblock.AprilFool.Steamgate;
import com.science.gtnl.common.machine.multiblock.BioengineeringModule;
import com.science.gtnl.common.machine.multiblock.BloodSoulSacrificialArray;
import com.science.gtnl.common.machine.multiblock.BrickedBlastFurnace;
import com.science.gtnl.common.machine.multiblock.CheatOreProcessingFactory;
import com.science.gtnl.common.machine.multiblock.ComponentAssembler;
import com.science.gtnl.common.machine.multiblock.CrackerHub;
import com.science.gtnl.common.machine.multiblock.DecayHastener;
import com.science.gtnl.common.machine.multiblock.Desulfurizer;
import com.science.gtnl.common.machine.multiblock.DraconicFusionCrafting;
import com.science.gtnl.common.machine.multiblock.EdenGarden;
import com.science.gtnl.common.machine.multiblock.ElementCopying;
import com.science.gtnl.common.machine.multiblock.EnergeticPhotovoltaicPowerStation;
import com.science.gtnl.common.machine.multiblock.FuelRefiningComplex;
import com.science.gtnl.common.machine.multiblock.GenerationEarthEngine;
import com.science.gtnl.common.machine.multiblock.GrandAssemblyLine;
import com.science.gtnl.common.machine.multiblock.HandOfJohnDavisonRockefeller;
import com.science.gtnl.common.machine.multiblock.IndustrialArcaneAssembler;
import com.science.gtnl.common.machine.multiblock.IntegratedAssemblyFacility;
import com.science.gtnl.common.machine.multiblock.LapotronChip;
import com.science.gtnl.common.machine.multiblock.LargeBioLab;
import com.science.gtnl.common.machine.multiblock.LargeBrewer;
import com.science.gtnl.common.machine.multiblock.LargeCircuitAssembler;
import com.science.gtnl.common.machine.multiblock.LargeGasCollector;
import com.science.gtnl.common.machine.multiblock.LargeIncubator;
import com.science.gtnl.common.machine.multiblock.LargeNaquadahReactor;
import com.science.gtnl.common.machine.multiblock.LargeSteamAlloySmelter;
import com.science.gtnl.common.machine.multiblock.LargeSteamChemicalBath;
import com.science.gtnl.common.machine.multiblock.LargeSteamCircuitAssembler;
import com.science.gtnl.common.machine.multiblock.LargeSteamCrusher;
import com.science.gtnl.common.machine.multiblock.LargeSteamExtractor;
import com.science.gtnl.common.machine.multiblock.LargeSteamExtruder;
import com.science.gtnl.common.machine.multiblock.LargeSteamFormingPress;
import com.science.gtnl.common.machine.multiblock.LargeSteamFurnace;
import com.science.gtnl.common.machine.multiblock.LargeSteamSifter;
import com.science.gtnl.common.machine.multiblock.LargeSteamThermalCentrifuge;
import com.science.gtnl.common.machine.multiblock.LibraryOfRuina;
import com.science.gtnl.common.machine.multiblock.MagneticEnergyReactionFurnace;
import com.science.gtnl.common.machine.multiblock.MatterFabricator;
import com.science.gtnl.common.machine.multiblock.MeteorMiner;
import com.science.gtnl.common.machine.multiblock.NanitesIntegratedProcessingCenter;
import com.science.gtnl.common.machine.multiblock.NanoPhagocytosisPlant;
import com.science.gtnl.common.machine.multiblock.NeutroniumWireCutting;
import com.science.gtnl.common.machine.multiblock.NineIndustrialMultiMachine;
import com.science.gtnl.common.machine.multiblock.OreExtractionModule;
import com.science.gtnl.common.machine.multiblock.PetrochemicalPlant;
import com.science.gtnl.common.machine.multiblock.PlatinumBasedTreatment;
import com.science.gtnl.common.machine.multiblock.PolymerTwistingModule;
import com.science.gtnl.common.machine.multiblock.PrimitiveDistillationTower;
import com.science.gtnl.common.machine.multiblock.ProcessingArray;
import com.science.gtnl.common.machine.multiblock.RareEarthCentrifugal;
import com.science.gtnl.common.machine.multiblock.ReactionFurnace;
import com.science.gtnl.common.machine.multiblock.RealArtificialStar;
import com.science.gtnl.common.machine.multiblock.ResourceCollectionModule;
import com.science.gtnl.common.machine.multiblock.ShallowChemicalCoupling;
import com.science.gtnl.common.machine.multiblock.SmeltingMixingFurnace;
import com.science.gtnl.common.machine.multiblock.SteamCracking;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.AlloyBlastSmelter;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.BlazeBlastFurnace;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.ChemicalPlant;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.ColdIceFreezer;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.Digester;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.ElectricBlastFurnace;
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
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeBoiler;
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
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeSteamCentrifuge;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeSteamCompressor;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeSteamHammer;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeSteamMixer;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeSteamOreWasher;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LargeWiremill;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.LuvKuangBiaoOneGiantNuclearFusionReactor;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.MegaAlloyBlastSmelter;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.MegaBlastFurnace;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.MolecularTransformer;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.PrecisionAssembler;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.UevKuangBiaoFiveGiantNuclearFusionReactor;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.UhvKuangBiaoFourGiantNuclearFusionReactor;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.UvKuangBiaoThreeGiantNuclearFusionReactor;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.VacuumDryingFurnace;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.VacuumFreezer;
import com.science.gtnl.common.machine.multiblock.StructuralReconstructionPlan.ZpmKuangBiaoTwoGiantNuclearFusionReactor;
import com.science.gtnl.common.machine.multiblock.SuperSpaceElevator;
import com.science.gtnl.common.machine.multiblock.TeleportationArrayToAlfheim;
import com.science.gtnl.common.machine.multiblock.VibrantPhotovoltaicPowerStation;
import com.science.gtnl.common.machine.multiblock.WhiteNightGenerator;
import com.science.gtnl.common.machine.multiblock.WoodDistillation;
import com.science.gtnl.common.materials.MaterialPool;
import com.science.gtnl.common.recipe.RecipeRegister;

import bartworks.API.BorosilicateGlass;
import goodgenerator.util.CrackRecipeAdder;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.Materials;
import gregtech.api.enums.SoundResource;
import gregtech.api.metatileentity.implementations.MTEBasicMachineWithRecipe;
import gregtech.api.render.TextureFactory;
import gregtech.common.covers.CoverConveyor;
import gregtech.common.covers.CoverFluidRegulator;
import gregtech.common.covers.CoverPump;
import gregtech.common.covers.CoverSteamRegulator;
import gregtech.common.covers.CoverSteamValve;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.util.minecraft.FluidUtils;

public class MachineLoader {

    public static ItemStack ResourceCollectionModule;
    public static ItemStack SuperSpaceElevator;

    public static void loadMachines() {

        GTNLItemList.EdenGarden.set(new EdenGarden(EDEN_GARDEN.ID, "EdenGarden", TextLocalization.NameEdenGarden));
        addItemTooltip(GTNLItemList.EdenGarden.get(1), AnimatedText.SNL_EDEN_GARDEN);

        GTNLItemList.LargeSteamCircuitAssembler.set(
            new LargeSteamCircuitAssembler(
                LARGE_STEAM_CIRCUIT_ASSEMBLER.ID,
                "LargeSteamCircuitAssembler",
                TextLocalization.NameLargeSteamCircuitAssembler));
        addItemTooltip(GTNLItemList.LargeSteamCircuitAssembler.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.GenerationEarthEngine.set(
            new GenerationEarthEngine(
                GENERATION_EARTH_ENGINE.ID,
                "GenerationEarthEngine",
                TextLocalization.NameGenerationEarthEngine));
        addItemTooltip(GTNLItemList.GenerationEarthEngine.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.BloodSoulSacrificialArray.set(
            new BloodSoulSacrificialArray(
                BLOOD_SOUL_SACRIFICIAL_ARRAY.ID,
                "BloodSoulSacrificialArray",
                TextLocalization.NameBloodSoulSacrificialArray));
        addItemTooltip(GTNLItemList.BloodSoulSacrificialArray.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.RealArtificialStar.set(
            new RealArtificialStar(
                REAL_ARTIFICIAL_STAR.ID,
                "RealArtificialStar",
                TextLocalization.NameRealArtificialStar));
        addItemTooltip(GTNLItemList.RealArtificialStar.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.TeleportationArrayToAlfheim.set(
            new TeleportationArrayToAlfheim(
                TELEPORTATION_ARRAY_TO_ALFHEIM.ID,
                "TeleportationArrayToAlfheim",
                TextLocalization.NameTeleportationArrayToAlfheim));
        addItemTooltip(GTNLItemList.TeleportationArrayToAlfheim.get(1), AnimatedText.SNL_NLXCJH);

        GTNLItemList.LapotronChip
            .set(new LapotronChip(LAPOTRON_CHIP.ID, "LapotronChip", TextLocalization.NameLapotronChip));
        addItemTooltip(GTNLItemList.LapotronChip.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NeutroniumWireCutting.set(
            new NeutroniumWireCutting(
                NEUTRONIUM_WIRE_CUTTING.ID,
                "NeutroniumWireCutting",
                TextLocalization.NameNeutroniumWireCutting));
        addItemTooltip(GTNLItemList.NeutroniumWireCutting.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeSteamCrusher.set(
            new LargeSteamCrusher(LARGE_STEAM_CRUSHER.ID, "LargeSteamCrusher", TextLocalization.NameLargeSteamCrusher));
        addItemTooltip(GTNLItemList.LargeSteamCrusher.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.ComponentAssembler.set(
            new ComponentAssembler(
                COMPONENT_ASSEMBLER.ID,
                "ComponentAssembler",
                TextLocalization.NameComponentAssembler));
        addItemTooltip(GTNLItemList.ComponentAssembler.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeSteamFurnace.set(
            new LargeSteamFurnace(LARGE_STEAM_FURNACE.ID, "LargeSteamFurnace", TextLocalization.NameLargeSteamFurnace));
        addItemTooltip(GTNLItemList.LargeSteamFurnace.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeSteamAlloySmelter.set(
            new LargeSteamAlloySmelter(
                LARGE_STEAM_ALLOY_SMELTER.ID,
                "LargeSteamAlloySmelter",
                TextLocalization.NameLargeSteamAlloySmelter));
        addItemTooltip(GTNLItemList.LargeSteamAlloySmelter.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeSteamThermalCentrifuge.set(
            new LargeSteamThermalCentrifuge(
                LARGE_STEAM_THERMAL_CENTRIFUGE.ID,
                "LargeSteamThermalCentrifuge",
                TextLocalization.NameLargeSteamThermalCentrifuge));
        addItemTooltip(GTNLItemList.LargeSteamThermalCentrifuge.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.SteamCracking
            .set(new SteamCracking(STEAM_CRACKING.ID, "SteamCracking", TextLocalization.NameSteamCracking));
        addItemTooltip(GTNLItemList.SteamCracking.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeSteamChemicalBath.set(
            new LargeSteamChemicalBath(
                LARGE_STEAM_CHEMICAL_BATH.ID,
                "LargeSteamChemicalBath",
                TextLocalization.NameLargeSteamChemicalBath));
        addItemTooltip(GTNLItemList.LargeSteamChemicalBath.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.PrimitiveDistillationTower.set(
            new PrimitiveDistillationTower(
                PRIMITIVE_DISTILLATION_TOWER.ID,
                "PrimitiveDistillationTower",
                TextLocalization.NamePrimitiveDistillationTower));
        addItemTooltip(GTNLItemList.PrimitiveDistillationTower.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.MeteorMiner.set(new MeteorMiner(METEOR_MINER.ID, "MeteorMiner", TextLocalization.NameMeteorMiner));
        addItemTooltip(GTNLItemList.MeteorMiner.get(1), AnimatedText.SNL_TOTTO);

        GTNLItemList.Desulfurizer
            .set(new Desulfurizer(DESULFURIZER.ID, "Desulfurizer", TextLocalization.NameDesulfurizer));
        addItemTooltip(GTNLItemList.Desulfurizer.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeCircuitAssembler.set(
            new LargeCircuitAssembler(
                LARGE_CIRCUIT_ASSEMBLER.ID,
                "LargeCircuitAssembler",
                TextLocalization.NameLargeCircuitAssembler));
        addItemTooltip(GTNLItemList.LargeCircuitAssembler.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.PetrochemicalPlant.set(
            new PetrochemicalPlant(
                PETROCHEMICAL_PLANT.ID,
                "PetrochemicalPlant",
                TextLocalization.NamePetrochemicalPlant));
        addItemTooltip(GTNLItemList.PetrochemicalPlant.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.SmeltingMixingFurnace.set(
            new SmeltingMixingFurnace(
                SMELTING_MIXING_FURNACE.ID,
                "SmeltingMixingFurnace",
                TextLocalization.NameSmeltingMixingFurnace));
        addItemTooltip(GTNLItemList.SmeltingMixingFurnace.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.WhiteNightGenerator.set(
            new WhiteNightGenerator(
                WHITE_NIGHT_GENERATOR.ID,
                "WhiteNightGenerator",
                TextLocalization.NameWhiteNightGenerator));
        addItemTooltip(GTNLItemList.WhiteNightGenerator.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ProcessingArray
            .set(new ProcessingArray(PROCESSING_ARRAY.ID, "ProcessingArray", TextLocalization.NameProcessingArrayGTNL));
        addItemTooltip(GTNLItemList.ProcessingArray.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.MegaBlastFurnace.set(
            new MegaBlastFurnace(MEGA_BLAST_FURNACE.ID, "MegaBlastFurnace", TextLocalization.NameMegaBlastFurnace));
        addItemTooltip(GTNLItemList.MegaBlastFurnace.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.BrickedBlastFurnace.set(
            new BrickedBlastFurnace(
                BRICKED_BLAST_FURNACE.ID,
                "BrickedBlastFurnace",
                TextLocalization.NameBrickedBlastFurnace));
        addItemTooltip(GTNLItemList.BrickedBlastFurnace.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.RareEarthCentrifugal.set(
            new RareEarthCentrifugal(
                RARE_EARTH_CENTRIFUGAL.ID,
                "RareEarthCentrifugal",
                TextLocalization.NameRareEarthCentrifugal));
        addItemTooltip(GTNLItemList.RareEarthCentrifugal.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.ColdIceFreezer
            .set(new ColdIceFreezer(COLD_ICE_FREEZER.ID, "ColdIceFreezer", TextLocalization.NameColdIceFreezer));
        addItemTooltip(GTNLItemList.ColdIceFreezer.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.BlazeBlastFurnace.set(
            new BlazeBlastFurnace(BLAZE_BLAST_FURNACE.ID, "BlazeBlastFurnace", TextLocalization.NameBlazeBlastFurnace));
        addItemTooltip(GTNLItemList.BlazeBlastFurnace.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.ChemicalPlant
            .set(new ChemicalPlant(CHEMICAL_PLANT.ID, "ChemicalPlant", TextLocalization.NameChemicalPlant));
        addItemTooltip(GTNLItemList.ChemicalPlant.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.VacuumFreezer
            .set(new VacuumFreezer(VACUUM_FREEZER.ID, "VacuumFreezer", TextLocalization.NameVacuumFreezer));
        addItemTooltip(GTNLItemList.VacuumFreezer.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.IndustrialArcaneAssembler.set(
            new IndustrialArcaneAssembler(
                INDUSTRIAL_ARCANE_ASSEMBLER.ID,
                "IndustrialArcaneAssembler",
                TextLocalization.NameIndustrialArcaneAssembler));
        addItemTooltip(GTNLItemList.IndustrialArcaneAssembler.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.EnergeticPhotovoltaicPowerStation.set(
            new EnergeticPhotovoltaicPowerStation(
                ENERGETIC_PHOTOVOLTAIC_POWER_STATION.ID,
                "EnergeticPhotovoltaicPowerStation",
                TextLocalization.NameEnergeticPhotovoltaicPowerStation));
        addItemTooltip(GTNLItemList.EnergeticPhotovoltaicPowerStation.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.AdvancedPhotovoltaicPowerStation.set(
            new AdvancedPhotovoltaicPowerStation(
                ADVANCED_PHOTOVOLTAIC_POWER_STATION.ID,
                "AdvancedPhotovoltaicPowerStation",
                TextLocalization.NameAdvancedPhotovoltaicPowerStation));
        addItemTooltip(GTNLItemList.AdvancedPhotovoltaicPowerStation.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.VibrantPhotovoltaicPowerStation.set(
            new VibrantPhotovoltaicPowerStation(
                VIBRANT_PHOTOVOLTAIC_POWER_STATION.ID,
                "VibrantPhotovoltaicPowerStation",
                TextLocalization.NameVibrantPhotovoltaicPowerStation));
        addItemTooltip(GTNLItemList.VibrantPhotovoltaicPowerStation.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeMacerationTower.set(
            new LargeMacerationTower(
                LARGE_MACERATION_TOWER.ID,
                "LargeMacerationTower",
                TextLocalization.NameLargeMacerationTower));
        addItemTooltip(GTNLItemList.LargeMacerationTower.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.HandOfJohnDavisonRockefeller.set(
            new HandOfJohnDavisonRockefeller(
                HAND_OF_JOHN_DAVISON_ROCKEFELLER.ID,
                "HandOfJohnDavisonRockefeller",
                TextLocalization.NameHandOfJohnDavisonRockefeller));
        addItemTooltip(GTNLItemList.HandOfJohnDavisonRockefeller.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeSiftingFunnel.set(
            new LargeSiftingFunnel(
                LARGE_SIFTING_FUNNEL.ID,
                "LargeSiftingFunnel",
                TextLocalization.NameLargeSiftingFunnel));
        addItemTooltip(GTNLItemList.LargeSiftingFunnel.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeCutter.set(new LargeCutter(LARGE_CUTTER.ID, "LargeCutter", TextLocalization.NameLargeCutter));
        addItemTooltip(GTNLItemList.LargeCutter.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeBrewer.set(new LargeBrewer(LARGE_BREWER.ID, "LargeBrewer", TextLocalization.NameLargeBrewer));
        addItemTooltip(GTNLItemList.LargeBrewer.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeIndustrialLathe.set(
            new LargeIndustrialLathe(
                LARGE_INDUSTRIAL_LATHE.ID,
                "LargeIndustrialLathe",
                TextLocalization.NameLargeIndustrialLathe));
        addItemTooltip(GTNLItemList.LargeIndustrialLathe.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeMaterialPress.set(
            new LargeMaterialPress(
                LARGE_MATERIAL_PRESS.ID,
                "LargeMaterialPress",
                TextLocalization.NameLargeMaterialPress));
        addItemTooltip(GTNLItemList.LargeMaterialPress.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeWiremill
            .set(new LargeWiremill(LARGE_WIREMILL.ID, "LargeWiremill", TextLocalization.NameLargeWiremill));
        addItemTooltip(GTNLItemList.LargeWiremill.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeBender.set(new LargeBender(LARGE_BENDER.ID, "LargeBender", TextLocalization.NameLargeBender));
        addItemTooltip(GTNLItemList.LargeBender.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.ElectricImplosionCompressor.set(
            new ElectricImplosionCompressor(
                ELECTRIC_IMPLOSION_COMPRESSOR.ID,
                "ElectricImplosionCompressor",
                TextLocalization.NameElectricImplosionCompressor));
        addItemTooltip(GTNLItemList.ElectricImplosionCompressor.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.LargeExtruder
            .set(new LargeExtruder(LARGE_EXTRUDER.ID, "LargeExtruder", TextLocalization.NameLargeExtruder));
        addItemTooltip(GTNLItemList.LargeExtruder.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeArcSmelter
            .set(new LargeArcSmelter(LARGE_ARC_SMELTER.ID, "LargeArcSmelter", TextLocalization.NameLargeArcSmelter));
        addItemTooltip(GTNLItemList.LargeArcSmelter.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeForming
            .set(new LargeForming(LARGE_FORMING.ID, "LargeForming", TextLocalization.NameLargeForming));
        addItemTooltip(GTNLItemList.LargeForming.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.MatterFabricator
            .set(new MatterFabricator(MATTER_FABRICATOR.ID, "MatterFabricator", TextLocalization.NameMatterFabricator));
        addItemTooltip(GTNLItemList.MatterFabricator.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeElectrolyzer.set(
            new LargeElectrolyzer(LARGE_ELECTROLYZER.ID, "LargeElectrolyzer", TextLocalization.NameLargeElectrolyzer));
        addItemTooltip(GTNLItemList.LargeElectrolyzer.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeElectromagnet.set(
            new LargeElectromagnet(
                LARGE_ELECTROMAGNET.ID,
                "LargeElectromagnet",
                TextLocalization.NameLargeElectromagnet));
        addItemTooltip(GTNLItemList.LargeElectromagnet.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeAssembler
            .set(new LargeAssembler(LARGE_ASSEMBLER.ID, "LargeAssembler", TextLocalization.NameLargeAssembler));
        addItemTooltip(GTNLItemList.LargeAssembler.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeMixer.set(new LargeMixer(LARGE_MIXER.ID, "LargeMixer", TextLocalization.NameLargeMixer));
        addItemTooltip(GTNLItemList.LargeMixer.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeCentrifuge
            .set(new LargeCentrifuge(LARGE_CENTRIFUGE.ID, "LargeCentrifuge", TextLocalization.NameLargeCentrifuge));
        addItemTooltip(GTNLItemList.LargeCentrifuge.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LibraryOfRuina
            .set(new LibraryOfRuina(LIBRARY_OF_RUINA.ID, "LibraryOfRuina", TextLocalization.NameLibraryOfRuina));
        addItemTooltip(GTNLItemList.LibraryOfRuina.get(1), AnimatedText.SNL_NLXCJH);

        GTNLItemList.LargeChemicalBath.set(
            new LargeChemicalBath(LARGE_CHEMICAL_BATH.ID, "LargeChemicalBath", TextLocalization.NameLargeChemicalBath));
        addItemTooltip(GTNLItemList.LargeChemicalBath.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeAutoclave
            .set(new LargeAutoclave(LARGE_AUTOCLAVE.ID, "LargeAutoclave", TextLocalization.NameLargeAutoclave));
        addItemTooltip(GTNLItemList.LargeAutoclave.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeSolidifier
            .set(new LargeSolidifier(LARGE_SOLIDIFIER.ID, "LargeSolidifier", TextLocalization.NameLargeSolidifier));
        addItemTooltip(GTNLItemList.LargeSolidifier.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeExtractor
            .set(new LargeExtractor(LARGE_EXTRACTOR.ID, "LargeExtractor", TextLocalization.NameLargeExtractor));
        addItemTooltip(GTNLItemList.LargeExtractor.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.ReactionFurnace
            .set(new ReactionFurnace(REACTION_FURNACE.ID, "ReactionFurnace", TextLocalization.NameReactionFurnace));
        addItemTooltip(GTNLItemList.ReactionFurnace.get(1), AnimatedText.SNL_NLXCJH);

        GTNLItemList.EnergyInfuser
            .set(new EnergyInfuser(ENERGY_INFUSER.ID, "EnergyInfuser", TextLocalization.NameEnergyInfuser));
        addItemTooltip(GTNLItemList.EnergyInfuser.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.LargeCanning
            .set(new LargeCanning(LARGE_CANNING.ID, "LargeCanning", TextLocalization.NameLargeCanning));
        addItemTooltip(GTNLItemList.LargeCanning.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.Digester.set(new Digester(DIGESTER.ID, "Digester", TextLocalization.NameDigester));
        addItemTooltip(GTNLItemList.Digester.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.AlloyBlastSmelter.set(
            new AlloyBlastSmelter(ALLOY_BLAST_SMELTER.ID, "AlloyBlastSmelter", TextLocalization.NameAlloyBlastSmelter));
        addItemTooltip(GTNLItemList.AlloyBlastSmelter.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeSteamExtractor.set(
            new LargeSteamExtractor(
                LARGE_STEAM_EXTRACTOR.ID,
                "LargeSteamExtractor",
                TextLocalization.NameLargeSteamExtractor));
        addItemTooltip(GTNLItemList.LargeSteamExtractor.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeSteamOreWasher.set(
            new LargeSteamOreWasher(
                LARGE_STEAM_ORE_WASHER.ID,
                "LargeSteamOreWasher",
                TextLocalization.NameLargeSteamOreWasher));
        addItemTooltip(GTNLItemList.LargeSteamOreWasher.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.LargeHammer.set(new LargeHammer(LARGE_HAMMER.ID, "LargeHammer", TextLocalization.NameLargeHammer));
        addItemTooltip(GTNLItemList.LargeHammer.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.IsaMill.set(new IsaMill(ISA_MILL.ID, "IsaMill", TextLocalization.NameIsaMill));
        addItemTooltip(GTNLItemList.IsaMill.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.FlotationCellRegulator.set(
            new FlotationCellRegulator(
                FLOTATION_CELL_REGULATOR.ID,
                "FlotationCellRegulator",
                TextLocalization.NameFlotationCellRegulator));
        addItemTooltip(GTNLItemList.FlotationCellRegulator.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.VacuumDryingFurnace.set(
            new VacuumDryingFurnace(
                VACUUM_DRYING_FURNACE.ID,
                "VacuumDryingFurnace",
                TextLocalization.NameVacuumDryingFurnace));
        addItemTooltip(GTNLItemList.VacuumDryingFurnace.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeDistillery
            .set(new LargeDistillery(LARGE_DISTILLERY.ID, "LargeDistillery", TextLocalization.NameLargeDistillery));
        addItemTooltip(GTNLItemList.LargeDistillery.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.Incubator.set(new Incubator(INCUBATOR.ID, "Incubator", TextLocalization.NameIncubator));
        addItemTooltip(GTNLItemList.Incubator.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.LargeIncubator
            .set(new LargeIncubator(LARGE_INCUBATOR.ID, "LargeIncubator", TextLocalization.NameLargeIncubator));
        addItemTooltip(GTNLItemList.LargeIncubator.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeEngravingLaser.set(
            new LargeEngravingLaser(
                LARGE_ENGRAVING_LASER.ID,
                "LargeEngravingLaser",
                TextLocalization.NameLargeEngravingLaser));
        addItemTooltip(GTNLItemList.LargeEngravingLaser.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.FishingGround
            .set(new FishingGround(FISHING_GROUND.ID, "FishingGround", TextLocalization.NameFishingGround));
        addItemTooltip(GTNLItemList.FishingGround.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.ElementCopying
            .set(new ElementCopying(ELEMENT_COPYING.ID, "ElementCopying", TextLocalization.NameElementCopying));
        addItemTooltip(GTNLItemList.ElementCopying.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.WoodDistillation
            .set(new WoodDistillation(WOOD_DISTILLATION.ID, "WoodDistillation", TextLocalization.NameWoodDistillation));
        addItemTooltip(GTNLItemList.WoodDistillation.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargePacker.set(new LargePacker(LARGE_PACKER.ID, "LargePacker", TextLocalization.NameLargePacker));
        addItemTooltip(GTNLItemList.LargePacker.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeAlloySmelter.set(
            new LargeAlloySmelter(LARGE_ALLOY_SMELTER.ID, "LargeAlloySmelter", TextLocalization.NameLargeAlloySmelter));
        addItemTooltip(GTNLItemList.LargeAlloySmelter.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.MolecularTransformer.set(
            new MolecularTransformer(
                MOLECULAR_TRANSFORMER.ID,
                "MolecularTransformer",
                TextLocalization.NameMolecularTransformer));
        addItemTooltip(GTNLItemList.MolecularTransformer.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.LargePyrolyseOven.set(
            new LargePyrolyseOven(LARGE_PYROLYSE_OVEN.ID, "LargePyrolyseOven", TextLocalization.NameLargePyrolyseOven));
        addItemTooltip(GTNLItemList.LargePyrolyseOven.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.LargeNaquadahReactor.set(
            new LargeNaquadahReactor(
                LARGE_NAQUADAH_REACTOR.ID,
                "LargeNaquadahReactor",
                TextLocalization.NameLargeNaquadahReactor));
        addItemTooltip(GTNLItemList.LargeNaquadahReactor.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.DraconicFusionCrafting.set(
            new DraconicFusionCrafting(
                DRACONIC_FUSION_CRAFTING.ID,
                "DraconicFusionCrafting",
                TextLocalization.NameDraconicFusionCrafting));
        addItemTooltip(GTNLItemList.DraconicFusionCrafting.get(1), AnimatedText.SNL_NLXCJH);

        GTNLItemList.LargeSteamExtruder.set(
            new LargeSteamExtruder(
                LARGE_STEAM_EXTRUDER.ID,
                "LargeSteamExtruder",
                TextLocalization.NameLargeSteamExtruder));
        addItemTooltip(GTNLItemList.LargeSteamExtruder.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.DecayHastener
            .set(new DecayHastener(DECAY_HASTENER.ID, "DecayHastener", TextLocalization.NameDecayHastener));
        addItemTooltip(GTNLItemList.DecayHastener.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.PreciseAssembler.set(
            new PrecisionAssembler(
                PRECISION_ASSEMBLER.ID,
                "PrecisionAssembler",
                TextLocalization.NamePrecisionAssembler));
        addItemTooltip(GTNLItemList.PreciseAssembler.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.MegaAlloyBlastSmelter.set(
            new MegaAlloyBlastSmelter(
                MEGA_ALLOY_BLAST_SMELTER.ID,
                "MegaAlloyBlastSmelter",
                TextLocalization.NameMegaAlloyBlastSmelter));
        addItemTooltip(GTNLItemList.MegaAlloyBlastSmelter.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.GrandAssemblyLine.set(
            new GrandAssemblyLine(GRAND_ASSEMBLY_LINE.ID, "GrandAssemblyLine", TextLocalization.NameGrandAssemblyLine));
        addItemTooltip(GTNLItemList.GrandAssemblyLine.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.FuelRefiningComplex.set(
            new FuelRefiningComplex(
                FUEL_REFINING_COMPLEX.ID,
                "FuelRefiningComplex",
                TextLocalization.NameFuelRefiningComplex));
        addItemTooltip(GTNLItemList.FuelRefiningComplex.get(1), AnimatedText.SNL_QYZG);

        /**
         * ResourceCollectionModule used 21092
         *
         * @see #loadMachinesPostInit()
         */

        GTNLItemList.LuvKuangBiaoOneGiantNuclearFusionReactor.set(
            new LuvKuangBiaoOneGiantNuclearFusionReactor(
                LUV_KUANG_BIAO_ONE_GIANT_NUCLEAR_FUSION_REACTOR.ID,
                "KuangBiaoOneGiantNuclearFusionReactor",
                TextLocalization.NameLuvKuangBiaoOneGiantNuclearFusionReactor));
        addItemTooltip(GTNLItemList.LuvKuangBiaoOneGiantNuclearFusionReactor.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.ZpmKuangBiaoTwoGiantNuclearFusionReactor.set(
            new ZpmKuangBiaoTwoGiantNuclearFusionReactor(
                ZPM_KUANG_BIAO_TWO_GIANT_NUCLEAR_FUSION_REACTOR.ID,
                "KuangBiaoTwoGiantNuclearFusionReactor",
                TextLocalization.NameZpmKuangBiaoTwoGiantNuclearFusionReactor));
        addItemTooltip(GTNLItemList.ZpmKuangBiaoTwoGiantNuclearFusionReactor.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.UvKuangBiaoThreeGiantNuclearFusionReactor.set(
            new UvKuangBiaoThreeGiantNuclearFusionReactor(
                UV_KUANG_BIAO_THREE_GIANT_NUCLEAR_FUSION_REACTOR.ID,
                "KuangBiaoThreeGiantNuclearFusionReactor",
                TextLocalization.NameUvKuangBiaoThreeGiantNuclearFusionReactor));
        addItemTooltip(GTNLItemList.UvKuangBiaoThreeGiantNuclearFusionReactor.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.UhvKuangBiaoFourGiantNuclearFusionReactor.set(
            new UhvKuangBiaoFourGiantNuclearFusionReactor(
                UHV_KUANG_BIAO_FOUR_GIANT_NUCLEAR_FUSION_REACTOR.ID,
                "KuangBiaoFourGiantNuclearFusionReactor",
                TextLocalization.NameUhvKuangBiaoFourGiantNuclearFusionReactor));
        addItemTooltip(GTNLItemList.UhvKuangBiaoFourGiantNuclearFusionReactor.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.UevKuangBiaoFiveGiantNuclearFusionReactor.set(
            new UevKuangBiaoFiveGiantNuclearFusionReactor(
                UEV_KUANG_BIAO_FIVE_GIANT_NUCLEAR_FUSION_REACTOR.ID,
                "KuangBiaoFiveGiantNuclearFusionReactor",
                TextLocalization.NameUevKuangBiaoFiveGiantNuclearFusionReactor));
        addItemTooltip(GTNLItemList.UevKuangBiaoFiveGiantNuclearFusionReactor.get(1), AnimatedText.SNL_QYZG_SRP);

        GTNLItemList.LargeSteamCentrifuge.set(
            new LargeSteamCentrifuge(
                LARGE_STEAM_CENTRIFUGE.ID,
                "LargeSteamCentrifuge",
                TextLocalization.NameLargeSteamCentrifuge));
        addItemTooltip(GTNLItemList.LargeSteamCentrifuge.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeSteamHammer.set(
            new LargeSteamHammer(LARGE_STEAM_HAMMER.ID, "LargeSteamHammer", TextLocalization.NameLargeSteamHammer));
        addItemTooltip(GTNLItemList.LargeSteamHammer.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeSteamCompressor.set(
            new LargeSteamCompressor(
                LARGE_STEAM_COMPRESSOR.ID,
                "LargeSteamCompressor",
                TextLocalization.NameLargeSteamCompressor));
        addItemTooltip(GTNLItemList.LargeSteamCompressor.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeSteamSifter.set(
            new LargeSteamSifter(LARGE_STEAM_SIFTER.ID, "LargeSteamSifter", TextLocalization.NameLargeSteamSifter));
        addItemTooltip(GTNLItemList.LargeSteamSifter.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.LargeBoilerBronze.set(
            new LargeBoiler.LargeBoilerBronze(
                LARGE_BOILER_BRONZE.ID,
                "LargeBoilerBronze",
                TextLocalization.NameLargeBoilerBronze));
        addItemTooltip(GTNLItemList.LargeBoilerBronze.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeBoilerSteel.set(
            new LargeBoiler.LargeBoilerSteel(
                LARGE_BOILER_STEEL.ID,
                "LargeBoilerSteel",
                TextLocalization.NameLargeBoilerSteel));
        addItemTooltip(GTNLItemList.LargeBoilerSteel.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeBoilerTitanium.set(
            new LargeBoiler.LargeBoilerTitanium(
                LARGE_BOILER_TITANIUM.ID,
                "LargeBoilerTitanium",
                TextLocalization.NameLargeBoilerTitanium));
        addItemTooltip(GTNLItemList.LargeBoilerTitanium.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeBoilerTungstenSteel.set(
            new LargeBoiler.LargeBoilerTungstenSteel(
                LARGE_BOILER_TUNGSTEN_STEEL.ID,
                "LargeBoilerTungstenSteel",
                TextLocalization.NameLargeBoilerTungstenSteel));
        addItemTooltip(GTNLItemList.LargeBoilerTungstenSteel.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.LargeSteamFormingPress.set(
            new LargeSteamFormingPress(
                LARGE_STEAM_FORMING_PRESS.ID,
                "LargeSteamFormingPress",
                TextLocalization.NameLargeSteamFormingPress));
        addItemTooltip(GTNLItemList.LargeSteamFormingPress.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.LargeSteamMixer
            .set(new LargeSteamMixer(LARGE_STEAM_MIXER.ID, "LargeSteamMixer", TextLocalization.NameLargeSteamMixer));
        addItemTooltip(GTNLItemList.LargeSteamMixer.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.CrackerHub.set(new CrackerHub(CRACKER_HUB.ID, "CrackerHub", TextLocalization.NameCrackerHub));
        addItemTooltip(GTNLItemList.CrackerHub.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.AdvancedInfiniteDriller.set(
            new AdvancedInfiniteDriller(
                ADVANCED_INFINITE_DRILLER.ID,
                "AdvancedInfiniteDriller",
                TextLocalization.NameAdvancedInfiniteDriller));
        addItemTooltip(GTNLItemList.AdvancedInfiniteDriller.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.ElectricBlastFurnace.set(
            new ElectricBlastFurnace(
                ELECTRIC_BLAST_FURNACE.ID,
                "ElectricBlastFurnace",
                TextLocalization.NameElectricBlastFurnace));
        addItemTooltip(GTNLItemList.ElectricBlastFurnace.get(1), AnimatedText.SNL_SRP);

        GTNLItemList.PlatinumBasedTreatment.set(
            new PlatinumBasedTreatment(
                PLATINUM_BASED_TREATMENT.ID,
                "PlatinumBasedTreatment",
                TextLocalization.NamePlatinumBasedTreatment));
        addItemTooltip(GTNLItemList.PlatinumBasedTreatment.get(1), AnimatedText.SNL_PBTR);

        GTNLItemList.ShallowChemicalCoupling.set(
            new ShallowChemicalCoupling(
                SHALLOW_CHEMICAL_COUPLING.ID,
                "ShallowChemicalCoupling",
                TextLocalization.NameShallowChemicalCoupling));
        addItemTooltip(GTNLItemList.ShallowChemicalCoupling.get(1), AnimatedText.SNL_SCCR);

        GTNLItemList.Steamgate.set(new Steamgate(STEAMGATE.ID, "Steamgate", TextLocalization.NameSteamgate));
        addItemTooltip(GTNLItemList.Steamgate.get(1), AnimatedText.SCIENCE_NOT_LEISURE);
        addItemTooltip(GTNLItemList.Steamgate.get(1), AnimatedText.SteamgateCredits);

        GTNLItemList.SteamGateAssembler.set(
            new SteamGateAssembler(
                STEAM_GATE_ASSEMBLER.ID,
                "SteamGateAssembler",
                TextLocalization.NameSteamGateAssembler));
        addItemTooltip(GTNLItemList.SteamGateAssembler.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.MegaSteamCompressor.set(
            new MegaSteamCompressor(
                MEGA_STEAM_COMPRESSOR.ID,
                "MegaSteamCompressor",
                TextLocalization.NameMegaSteamCompressor));
        addItemTooltip(GTNLItemList.MegaSteamCompressor.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.MegaSolarBoiler
            .set(new MegaSolarBoiler(MEGA_SOLAR_BOILER.ID, "MegaSolarBoiler", TextLocalization.NameMegaSolarBoiler));
        addItemTooltip(GTNLItemList.MegaSolarBoiler.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamCactusWonder.set(
            new SteamCactusWonder(STEAM_CACTUS_WONDER.ID, "SteamCactusWonder", TextLocalization.NameSteamCactusWonder));
        addItemTooltip(GTNLItemList.SteamCactusWonder.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamCarpenter
            .set(new SteamCarpenter(STEAM_CARPENTER.ID, "SteamCarpenter", TextLocalization.NameSteamCarpenter));
        addItemTooltip(GTNLItemList.SteamCarpenter.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamLavaMaker
            .set(new SteamLavaMaker(STEAM_LAVA_MAKER.ID, "SteamLavaMaker", TextLocalization.NameSteamLavaMaker));
        addItemTooltip(GTNLItemList.SteamLavaMaker.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamManufacturer.set(
            new SteamManufacturer(STEAM_MANUFACTURER.ID, "SteamManufacturer", TextLocalization.NameSteamManufacturer));
        addItemTooltip(GTNLItemList.SteamManufacturer.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamRockBreaker.set(
            new SteamRockBreaker(STEAM_ROCK_BREAKER.ID, "SteamRockBreaker", TextLocalization.NameSteamRockBreaker));
        addItemTooltip(GTNLItemList.SteamRockBreaker.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamWoodcutter
            .set(new SteamWoodcutter(STEAM_WOODCUTTER.ID, "SteamWoodcutter", TextLocalization.NameSteamWoodcutter));
        addItemTooltip(GTNLItemList.SteamWoodcutter.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamExtractinator.set(
            new SteamExtractinator(
                STEAM_EXTRACTINATOR.ID,
                "SteamExtractinator",
                TextLocalization.NameSteamExtractinator));
        addItemTooltip(GTNLItemList.SteamExtractinator.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamFusionReactor.set(
            new SteamFusionReactor(
                STEAM_FUSION_REACTOR.ID,
                "SteamFusionReactor",
                TextLocalization.NameSteamFusionReactor));
        addItemTooltip(GTNLItemList.SteamFusionReactor.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HighPressureSteamFusionReactor.set(
            new HighPressureSteamFusionReactor(
                HIGH_PRESSURE_STEAM_FUSION_REACTOR.ID,
                "HighPressureSteamFusionReactor",
                TextLocalization.NameHighPressureSteamFusionReactor));
        addItemTooltip(GTNLItemList.HighPressureSteamFusionReactor.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamInfernalCokeOven.set(
            new SteamInfernalCokeOven(
                STEAM_INFERNAL_COKE_OVEN.ID,
                "SteamInfernalCokeOven",
                TextLocalization.NameSteamInfernalCokeOven));
        addItemTooltip(GTNLItemList.SteamInfernalCokeOven.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.IntegratedAssemblyFacility.set(
            new IntegratedAssemblyFacility(
                INTEGRATED_ASSEMBLY_FACILITY.ID,
                "IntegratedAssemblyFacility",
                TextLocalization.NameIntegratedAssemblyFacility));
        addItemTooltip(GTNLItemList.IntegratedAssemblyFacility.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.AdvancedCircuitAssemblyLine.set(
            new AdvancedCircuitAssemblyLine(
                ADVANCED_CIRCUIT_ASSEMBLY_LINE.ID,
                "AdvancedCircuitAssemblyLine",
                TextLocalization.NameAdvancedCircuitAssemblyLine));
        addItemTooltip(GTNLItemList.AdvancedCircuitAssemblyLine.get(1), AnimatedText.SNL_SCCR);

        GTNLItemList.NanoPhagocytosisPlant.set(
            new NanoPhagocytosisPlant(
                NANO_PHAGOCYTOSIS_PLANT.ID,
                "NanoPhagocytosisPlant",
                TextLocalization.NameNanoPhagocytosisPlant));
        addItemTooltip(GTNLItemList.NanoPhagocytosisPlant.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.MagneticEnergyReactionFurnace.set(
            new MagneticEnergyReactionFurnace(
                MAGNETIC_ENERGY_REACTION_FURNACE.ID,
                "MagneticEnergyReactionFurnace",
                TextLocalization.NameMagneticEnergyReactionFurnace));
        addItemTooltip(GTNLItemList.MagneticEnergyReactionFurnace.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.NanitesIntegratedProcessingCenter.set(
            new NanitesIntegratedProcessingCenter(
                NANITES_INTEGRATED_PROCESSING_CENTER.ID,
                "NanitesIntegratedProcessingCenter",
                TextLocalization.NameNanitesIntegratedProcessingCenter));
        addItemTooltip(GTNLItemList.NanitesIntegratedProcessingCenter.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.BioengineeringModule.set(
            new BioengineeringModule(
                BIOENGINEERING_MODULE.ID,
                "BioengineeringModule",
                TextLocalization.NameBioengineeringModule));
        addItemTooltip(GTNLItemList.BioengineeringModule.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.PolymerTwistingModule.set(
            new PolymerTwistingModule(
                POLYMER_TWISTING_MODULE.ID,
                "PolymerTwistingModule",
                TextLocalization.NamePolymerTwistingModule));
        addItemTooltip(GTNLItemList.PolymerTwistingModule.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.OreExtractionModule.set(
            new OreExtractionModule(
                ORE_EXTRACTION_MODULE.ID,
                "OreExtractionModule",
                TextLocalization.NameOreExtractionModule));
        addItemTooltip(GTNLItemList.OreExtractionModule.get(1), AnimatedText.SNL_QYZG);

        /**
         * SuperSpaceElevator used 21135
         *
         * @see #loadMachinesPostInit()
         */

        GTNLItemList.LargeBioLab
            .set(new LargeBioLab(LARGE_BIO_LAB.ID, "LargeBioLab", TextLocalization.NameLargeBioLab));
        addItemTooltip(GTNLItemList.LargeBioLab.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.LargeGasCollector.set(
            new LargeGasCollector(LARGE_GAS_COLLECTOR.ID, "LargeGasCollector", TextLocalization.NameLargeGasCollector));
        addItemTooltip(GTNLItemList.LargeGasCollector.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        // Special Machine
        GTNLItemList.CheatOreProcessingFactory.set(
            new CheatOreProcessingFactory(
                CHEAT_ORE_PROCESSING_FACTORY.ID,
                "CheatOreProcessingFactory",
                TextLocalization.NameCheatOreProcessingFactory));
        addItemTooltip(GTNLItemList.CheatOreProcessingFactory.get(1), AnimatedText.SNL_QYZG);

        GTNLItemList.NineIndustrialMultiMachine.set(
            new NineIndustrialMultiMachine(
                NINE_INDUSTRIAL_MULTI_MACHINE.ID,
                "NineIndustrialMultiMachine",
                TextLocalization.NameNineIndustrialMultiMachine));
        addItemTooltip(GTNLItemList.NineIndustrialMultiMachine.get(1), AnimatedText.SNL_QYZG);
    }

    public static void registerMTEHatch() {
        Set<Fluid> acceptedFluids = new HashSet<>();
        acceptedFluids.add(
            FluidUtils.getFluidStack("fluidmana", 1)
                .getFluid());

        if (Mods.TwistSpaceTechnology.isModLoaded()) {
            acceptedFluids.add(
                FluidUtils.getFluidStack("liquid mana", 1)
                    .getFluid());
        }

        GTNLItemList.FluidManaInputHatch.set(
            new CustomFluidHatch(
                ImmutableSet.copyOf(acceptedFluids),
                512000,
                FLUID_MANA_INPUT_HATCH.ID,
                "FluidManaInputHatch",
                TextLocalization.FluidManaInputHatch,
                6));
        addItemTooltip(GTNLItemList.FluidManaInputHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.FluidIceInputHatch.set(
            new CustomFluidHatch(
                ImmutableSet.of(
                    FluidUtils.getFluidStack("ice", 1)
                        .getFluid()),
                256000,
                FLUID_ICE_INPUT_HATCH.ID,
                "FluidIceInputHatch",
                TextLocalization.FluidIceInputHatch,
                5));
        addItemTooltip(GTNLItemList.FluidIceInputHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.FluidBlazeInputHatch.set(
            new CustomFluidHatch(
                ImmutableSet.of(
                    FluidUtils.getFluidStack("molten.blaze", 1)
                        .getFluid()),
                256000,
                FLUID_BLAZE_INPUT_HATCH.ID,
                "FluidBlazeInputHatch",
                TextLocalization.FluidBlazeInputHatch,
                5));
        addItemTooltip(GTNLItemList.FluidBlazeInputHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SuperCraftingInputHatchME.set(
            new SuperCraftingInputHatchME(
                SUPER_CRAFTING_INPUT_HATCH_ME.ID,
                "SuperCraftingInputBuffer(ME)",
                TextLocalization.SuperCraftingInputHatchME,
                true));
        addItemTooltip(GTNLItemList.SuperCraftingInputHatchME.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SuperCraftingInputBusME.set(
            new SuperCraftingInputHatchME(
                SUPER_CRAFTING_INPUT_BUS_ME.ID,
                "SuperCraftingInputBus(ME)",
                TextLocalization.SuperCraftingInputBusME,
                false));
        addItemTooltip(GTNLItemList.SuperCraftingInputBusME.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousSolidifierHatch.set(
            new HumongousSolidifierHatch(
                HUMONGOUS_SOLIDIFIER_HATCH.ID,
                "HumongousSolidifierHatch",
                TextLocalization.HumongousSolidifierHatch,
                14));
        addItemTooltip(GTNLItemList.HumongousSolidifierHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DebugEnergyHatch.set(
            new DebugEnergyHatch(DEBUG_ENERGY_HATCH.ID, "DebugEnergyHatch", TextLocalization.DebugEnergyHatch, 14));
        addItemTooltip(GTNLItemList.DebugEnergyHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchEV.set(
            new NinefoldInputHatch(
                NINEFOLD_INPUT_HATCH_EV.ID,
                9,
                "NinefoldInputHatchEV",
                TextLocalization.NinefoldInputHatchEV,
                4));
        addItemTooltip(GTNLItemList.NinefoldInputHatchEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchIV.set(
            new NinefoldInputHatch(
                NINEFOLD_INPUT_HATCH_IV.ID,
                9,
                "NinefoldInputHatchIV",
                TextLocalization.NinefoldInputHatchIV,
                5));
        addItemTooltip(GTNLItemList.NinefoldInputHatchIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchLuV.set(
            new NinefoldInputHatch(
                NINEFOLD_INPUT_HATCH_LUV.ID,
                9,
                "NinefoldInputHatchLuV",
                TextLocalization.NinefoldInputHatchLuV,
                6));
        addItemTooltip(GTNLItemList.NinefoldInputHatchLuV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchZPM.set(
            new NinefoldInputHatch(
                NINEFOLD_INPUT_HATCH_ZPM.ID,
                9,
                "NinefoldInputHatchZPM",
                TextLocalization.NinefoldInputHatchZPM,
                7));
        addItemTooltip(GTNLItemList.NinefoldInputHatchZPM.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchUV.set(
            new NinefoldInputHatch(
                NINEFOLD_INPUT_HATCH_UV.ID,
                9,
                "NinefoldInputHatchUV",
                TextLocalization.NinefoldInputHatchUV,
                8));
        addItemTooltip(GTNLItemList.NinefoldInputHatchUV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchUHV.set(
            new NinefoldInputHatch(
                NINEFOLD_INPUT_HATCH_UHV.ID,
                9,
                "NinefoldInputHatchUHV",
                TextLocalization.NinefoldInputHatchUHV,
                9));
        addItemTooltip(GTNLItemList.NinefoldInputHatchUHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchUEV.set(
            new NinefoldInputHatch(
                NINEFOLD_INPUT_HATCH_UEV.ID,
                9,
                "NinefoldInputHatchUEV",
                TextLocalization.NinefoldInputHatchUEV,
                10));
        addItemTooltip(GTNLItemList.NinefoldInputHatchUEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchUIV.set(
            new NinefoldInputHatch(
                NINEFOLD_INPUT_HATCH_UIV.ID,
                9,
                "NinefoldInputHatchUIV",
                TextLocalization.NinefoldInputHatchUIV,
                11));
        addItemTooltip(GTNLItemList.NinefoldInputHatchUIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchUMV.set(
            new NinefoldInputHatch(
                NINEFOLD_INPUT_HATCH_UMV.ID,
                9,
                "NinefoldInputHatchUMV",
                TextLocalization.NinefoldInputHatchUMV,
                12));
        addItemTooltip(GTNLItemList.NinefoldInputHatchUMV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchUXV.set(
            new NinefoldInputHatch(
                NINEFOLD_INPUT_HATCH_UXV.ID,
                9,
                "NinefoldInputHatchUXV",
                TextLocalization.NinefoldInputHatchUXV,
                13));
        addItemTooltip(GTNLItemList.NinefoldInputHatchUXV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldInputHatchMAX.set(
            new NinefoldInputHatch(
                NINEFOLD_INPUT_HATCH_MAX.ID,
                9,
                "NinefoldInputHatchMAX",
                TextLocalization.NinefoldInputHatchMAX,
                14));
        addItemTooltip(GTNLItemList.NinefoldInputHatchMAX.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousNinefoldInputHatch.set(
            new HumongousNinefoldInputHatch(
                HUMONGOUS_NINEFOLD_INPUT_HATCH.ID,
                9,
                "HumongousNinefoldInputHatch",
                TextLocalization.HumongousNinefoldInputHatch));
        addItemTooltip(GTNLItemList.HumongousNinefoldInputHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchLV
            .set(new DualInputHatch(DUAL_INPUT_HATCH_LV.ID, "DualInputHatchLV", TextLocalization.DualInputHatchLV, 1));
        addItemTooltip(GTNLItemList.DualInputHatchLV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchMV
            .set(new DualInputHatch(DUAL_INPUT_HATCH_MV.ID, "DualInputHatchMV", TextLocalization.DualInputHatchMV, 2));
        addItemTooltip(GTNLItemList.DualInputHatchMV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchHV
            .set(new DualInputHatch(DUAL_INPUT_HATCH_HV.ID, "DualInputHatchHV", TextLocalization.DualInputHatchHV, 3));
        addItemTooltip(GTNLItemList.DualInputHatchHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchEV
            .set(new DualInputHatch(DUAL_INPUT_HATCH_EV.ID, "DualInputHatchEV", TextLocalization.DualInputHatchEV, 4));
        addItemTooltip(GTNLItemList.DualInputHatchEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchIV
            .set(new DualInputHatch(DUAL_INPUT_HATCH_IV.ID, "DualInputHatchIV", TextLocalization.DualInputHatchIV, 5));
        addItemTooltip(GTNLItemList.DualInputHatchIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchLuV.set(
            new DualInputHatch(DUAL_INPUT_HATCH_LUV.ID, "DualInputHatchLuV", TextLocalization.DualInputHatchLuV, 6));
        addItemTooltip(GTNLItemList.DualInputHatchLuV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchZPM.set(
            new DualInputHatch(DUAL_INPUT_HATCH_ZPM.ID, "DualInputHatchZPM", TextLocalization.DualInputHatchZPM, 7));
        addItemTooltip(GTNLItemList.DualInputHatchZPM.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchUV
            .set(new DualInputHatch(DUAL_INPUT_HATCH_UV.ID, "DualInputHatchUV", TextLocalization.DualInputHatchUV, 8));
        addItemTooltip(GTNLItemList.DualInputHatchUV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchUHV.set(
            new DualInputHatch(DUAL_INPUT_HATCH_UHV.ID, "DualInputHatchUHV", TextLocalization.DualInputHatchUHV, 9));
        addItemTooltip(GTNLItemList.DualInputHatchUHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchUEV.set(
            new DualInputHatch(DUAL_INPUT_HATCH_UEV.ID, "DualInputHatchUEV", TextLocalization.DualInputHatchUEV, 10));
        addItemTooltip(GTNLItemList.DualInputHatchUEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchUIV.set(
            new DualInputHatch(DUAL_INPUT_HATCH_UIV.ID, "DualInputHatchUIV", TextLocalization.DualInputHatchUIV, 11));
        addItemTooltip(GTNLItemList.DualInputHatchUIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchUMV.set(
            new DualInputHatch(DUAL_INPUT_HATCH_UMV.ID, "DualInputHatchUMV", TextLocalization.DualInputHatchUMV, 12));
        addItemTooltip(GTNLItemList.DualInputHatchUMV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchUXV.set(
            new DualInputHatch(DUAL_INPUT_HATCH_UXV.ID, "DualInputHatchUXV", TextLocalization.DualInputHatchUXV, 13));
        addItemTooltip(GTNLItemList.DualInputHatchUXV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.DualInputHatchMAX.set(
            new DualInputHatch(DUAL_INPUT_HATCH_MAX.ID, "DualInputHatchMAX", TextLocalization.DualInputHatchMAX, 14));
        addItemTooltip(GTNLItemList.DualInputHatchMAX.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SuperCraftingInputProxy.set(
            new SuperCraftingInputProxy(
                SUPER_CRAFTING_INPUT_PROXY.ID,
                "SuperCraftingInputProxy",
                TextLocalization.SuperCraftingInputProxy).getStackForm(1L));
        addItemTooltip(GTNLItemList.SuperCraftingInputProxy.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SuperDataAccessHatch.set(
            new SuperDataAccessHatch(
                SUPER_DATA_ACCESS_HATCH.ID,
                "SuperDataAccessHatch",
                TextLocalization.SuperDataAccessHatch,
                14));
        addItemTooltip(GTNLItemList.SuperDataAccessHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.BigSteamInputHatch.set(
            new CustomFluidHatch(
                ImmutableSet.of(
                    FluidUtils.getSteam(1)
                        .getFluid(),
                    FluidUtils.getSuperHeatedSteam(1)
                        .getFluid(),
                    FluidRegistry.getFluidStack("supercriticalsteam", 1)
                        .getFluid(),
                    MaterialPool.CompressedSteam.getMolten(1)
                        .getFluid()),
                4096000,
                BIG_STEAM_INPUT_HATCH.ID,
                "BigSteamInputHatch",
                TextLocalization.BigSteamInputHatch,
                1));
        addItemTooltip(GTNLItemList.BigSteamInputHatch.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchLV.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_LV.ID,
                "ParallelControllerHatchLV",
                TextLocalization.ParallelControllerHatchLV,
                1));
        addItemTooltip(GTNLItemList.ParallelControllerHatchLV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchMV.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_MV.ID,
                "ParallelControllerHatchMV",
                TextLocalization.ParallelControllerHatchMV,
                2));
        addItemTooltip(GTNLItemList.ParallelControllerHatchMV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchHV.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_HV.ID,
                "ParallelControllerHatchHV",
                TextLocalization.ParallelControllerHatchHV,
                3));
        addItemTooltip(GTNLItemList.ParallelControllerHatchHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchEV.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_EV.ID,
                "ParallelControllerHatchEV",
                TextLocalization.ParallelControllerHatchEV,
                4));
        addItemTooltip(GTNLItemList.ParallelControllerHatchEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchIV.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_IV.ID,
                "ParallelControllerHatchIV",
                TextLocalization.ParallelControllerHatchIV,
                5));
        addItemTooltip(GTNLItemList.ParallelControllerHatchIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchLuV.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_LUV.ID,
                "ParallelControllerHatchLuV",
                TextLocalization.ParallelControllerHatchLuV,
                6));
        addItemTooltip(GTNLItemList.ParallelControllerHatchLuV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchZPM.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_ZPM.ID,
                "ParallelControllerHatchZPM",
                TextLocalization.ParallelControllerHatchZPM,
                7));
        addItemTooltip(GTNLItemList.ParallelControllerHatchZPM.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchUV.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_UV.ID,
                "ParallelControllerHatchUV",
                TextLocalization.ParallelControllerHatchUV,
                8));
        addItemTooltip(GTNLItemList.ParallelControllerHatchUV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchUHV.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_UHV.ID,
                "ParallelControllerHatchUHV",
                TextLocalization.ParallelControllerHatchUHV,
                9));
        addItemTooltip(GTNLItemList.ParallelControllerHatchUHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchUEV.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_UEV.ID,
                "ParallelControllerHatchUEV",
                TextLocalization.ParallelControllerHatchUEV,
                10));
        addItemTooltip(GTNLItemList.ParallelControllerHatchUEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchUIV.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_UIV.ID,
                "ParallelControllerHatchUIV",
                TextLocalization.ParallelControllerHatchUIV,
                11));
        addItemTooltip(GTNLItemList.ParallelControllerHatchUIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchUMV.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_UMV.ID,
                "ParallelControllerHatchUMV",
                TextLocalization.ParallelControllerHatchUMV,
                12));
        addItemTooltip(GTNLItemList.ParallelControllerHatchUMV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchUXV.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_UXV.ID,
                "ParallelControllerHatchUXV",
                TextLocalization.ParallelControllerHatchUXV,
                13));
        addItemTooltip(GTNLItemList.ParallelControllerHatchUXV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ParallelControllerHatchMAX.set(
            new ParallelControllerHatch(
                PARALLEL_CONTROLLER_HATCH_MAX.ID,
                "ParallelControllerHatchMAX",
                TextLocalization.ParallelControllerHatchMAX,
                14));
        addItemTooltip(GTNLItemList.ParallelControllerHatchMAX.get(1), AnimatedText.SCIENCE_NOT_LEISURE);
    }

    @Deprecated
    public static void registerTestMachine() {
        GTNLItemList.QuadrupleOutputHatchEV
            .set(new DualOutputHatch(21700, 4, "QuadrupleOutputHatchEV", TextLocalization.QuadrupleOutputHatchEV, 4));
        addItemTooltip(GTNLItemList.QuadrupleOutputHatchEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.NinefoldOutputHatchEV
            .set(new DualOutputHatch(21701, 9, "NinefoldOutputHatchEV", TextLocalization.NinefoldOutputHatchEV, 4));
        addItemTooltip(GTNLItemList.NinefoldOutputHatchEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusULV
            .set(new HumongousInputBus(21702, "HumongousInputBusULV", TextLocalization.HumongousInputBusULV, 0));
        addItemTooltip(GTNLItemList.HumongousInputBusULV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusLV
            .set(new HumongousInputBus(21703, "HumongousInputBusLV", TextLocalization.HumongousInputBusLV, 1));
        addItemTooltip(GTNLItemList.HumongousInputBusLV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusMV
            .set(new HumongousInputBus(21704, "HumongousInputBusMV", TextLocalization.HumongousInputBusMV, 2));
        addItemTooltip(GTNLItemList.HumongousInputBusMV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusHV
            .set(new HumongousInputBus(21705, "HumongousInputBusHV", TextLocalization.HumongousInputBusHV, 3));
        addItemTooltip(GTNLItemList.HumongousInputBusHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusEV
            .set(new HumongousInputBus(21706, "HumongousInputBusEV", TextLocalization.HumongousInputBusEV, 4));
        addItemTooltip(GTNLItemList.HumongousInputBusEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusIV
            .set(new HumongousInputBus(21707, "HumongousInputBusIV", TextLocalization.HumongousInputBusIV, 5));
        addItemTooltip(GTNLItemList.HumongousInputBusIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusLuV
            .set(new HumongousInputBus(21708, "HumongousInputBusLuV", TextLocalization.HumongousInputBusLuV, 6));
        addItemTooltip(GTNLItemList.HumongousInputBusLuV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusZPM
            .set(new HumongousInputBus(21709, "HumongousInputBusZPM", TextLocalization.HumongousInputBusZPM, 7));
        addItemTooltip(GTNLItemList.HumongousInputBusZPM.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusUV
            .set(new HumongousInputBus(21710, "HumongousInputBusUV", TextLocalization.HumongousInputBusUV, 8));
        addItemTooltip(GTNLItemList.HumongousInputBusUV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusUHV
            .set(new HumongousInputBus(21711, "HumongousInputBusUHV", TextLocalization.HumongousInputBusUHV, 9));
        addItemTooltip(GTNLItemList.HumongousInputBusUHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusUEV
            .set(new HumongousInputBus(21712, "HumongousInputBusUEV", TextLocalization.HumongousInputBusUEV, 10));
        addItemTooltip(GTNLItemList.HumongousInputBusUEV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusUIV
            .set(new HumongousInputBus(21713, "HumongousInputBusUIV", TextLocalization.HumongousInputBusUIV, 11));
        addItemTooltip(GTNLItemList.HumongousInputBusUIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusUMV
            .set(new HumongousInputBus(21714, "HumongousInputBusUMV", TextLocalization.HumongousInputBusUMV, 12));
        addItemTooltip(GTNLItemList.HumongousInputBusUMV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusUXV
            .set(new HumongousInputBus(21715, "HumongousInputBusUXV", TextLocalization.HumongousInputBusUXV, 13));
        addItemTooltip(GTNLItemList.HumongousInputBusUXV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.HumongousInputBusMAX
            .set(new HumongousInputBus(21716, "HumongousInputBusMAX", TextLocalization.HumongousInputBusMAX, 14));
        addItemTooltip(GTNLItemList.HumongousInputBusMAX.get(1), AnimatedText.SCIENCE_NOT_LEISURE);
    }

    public static void registerBasicMachine() {
        GTNLItemList.SteamTurbineLV
            .set(new SteamTurbine(STEAM_TURBINE_LV.ID, "BasicSteamTurbine", TextLocalization.SteamTurbineLV, 1));
        addItemTooltip(GTNLItemList.SteamTurbineLV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamTurbineMV
            .set(new SteamTurbine(STEAM_TURBINE_MV.ID, "AdvancedSteamTurbine", TextLocalization.SteamTurbineMV, 2));
        addItemTooltip(GTNLItemList.SteamTurbineMV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamTurbineHV
            .set(new SteamTurbine(STEAM_TURBINE_HV.ID, "AdvancedSteamTurbineII", TextLocalization.SteamTurbineHV, 3));
        addItemTooltip(GTNLItemList.SteamTurbineHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamAssemblerBronze.set(
            new SteamAssemblerBronze(
                STEAM_ASSEMBLER_BRONZE.ID,
                "SteamAssembler",
                TextLocalization.SteamAssemblerBronze));
        addItemTooltip(GTNLItemList.SteamAssemblerBronze.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.SteamAssemblerSteel.set(
            new SteamAssemblerSteel(
                STEAM_ASSEMBLER_STEEL.ID,
                "HighPressureSteamAssembler",
                TextLocalization.SteamAssemblerSteel));
        addItemTooltip(GTNLItemList.SteamAssemblerSteel.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ManaTank.set(new ManaTank(MANA_TANK.ID, "ManaTank", TextLocalization.ManaTank));
        addItemTooltip(GTNLItemList.ManaTank.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ManaDynamoHatchLV.set(
            new ManaDynamoHatch(
                MANA_DYNAMO_HATCH_LV.ID,
                "ManaDynamoHatchLV",
                TextLocalization.ManaDynamoHatchLV,
                1,
                16));
        addItemTooltip(GTNLItemList.ManaDynamoHatchLV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ManaDynamoHatchHV.set(
            new ManaDynamoHatch(
                MANA_DYNAMO_HATCH_HV.ID,
                "ManaDynamoHatchHV",
                TextLocalization.ManaDynamoHatchHV,
                3,
                16));
        addItemTooltip(GTNLItemList.ManaDynamoHatchHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ManaDynamoHatchIV.set(
            new ManaDynamoHatch(
                MANA_DYNAMO_HATCH_IV.ID,
                "ManaDynamoHatchIV",
                TextLocalization.ManaDynamoHatchIV,
                5,
                16));
        addItemTooltip(GTNLItemList.ManaDynamoHatchIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ManaDynamoHatchZPM.set(
            new ManaDynamoHatch(
                MANA_DYNAMO_HATCH_ZPM.ID,
                "ManaDynamoHatchZPM",
                TextLocalization.ManaDynamoHatchZPM,
                7,
                16));
        addItemTooltip(GTNLItemList.ManaDynamoHatchZPM.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ManaEnergyHatchLV.set(
            new ManaEnergyHatch(
                MANA_ENERGY_HATCH_LV.ID,
                "ManaEnergyHatchLV",
                TextLocalization.ManaEnergyHatchLV,
                1,
                16));
        addItemTooltip(GTNLItemList.ManaEnergyHatchLV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ManaEnergyHatchHV.set(
            new ManaEnergyHatch(
                MANA_ENERGY_HATCH_HV.ID,
                "ManaEnergyHatchHV",
                TextLocalization.ManaEnergyHatchHV,
                3,
                16));
        addItemTooltip(GTNLItemList.ManaEnergyHatchHV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ManaEnergyHatchIV.set(
            new ManaEnergyHatch(
                MANA_ENERGY_HATCH_IV.ID,
                "ManaEnergyHatchIV",
                TextLocalization.ManaEnergyHatchIV,
                5,
                16));
        addItemTooltip(GTNLItemList.ManaEnergyHatchIV.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.ManaEnergyHatchZPM.set(
            new ManaEnergyHatch(
                MANA_ENERGY_HATCH_ZPM.ID,
                "ManaEnergyHatchZPM",
                TextLocalization.ManaEnergyHatchZPM,
                7,
                16));
        addItemTooltip(GTNLItemList.ManaEnergyHatchZPM.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        GTNLItemList.GasCollectorLV.set(
            new MTEBasicMachineWithRecipe(
                GAS_COLLECTOR_LV.ID,
                "GasCollectorLV",
                TextLocalization.GasCollectorLV,
                1,
                new String[] { TextLocalization.Tooltip_GasCollector_00,
                    StatCollector.translateToLocal("GT5U.MBTT.MachineType") + ": "
                        + EnumChatFormatting.YELLOW
                        + TextLocalization.GasCollectorRecipeType
                        + EnumChatFormatting.RESET },
                RecipeRegister.GasCollectorRecipes,
                3,
                3,
                true,
                SoundResource.NONE,
                MTEBasicMachineWithRecipe.SpecialEffects.NONE,
                "GAS_COLLECTOR",
                null).getStackForm(1L));

        GTNLItemList.GasCollectorMV.set(
            new MTEBasicMachineWithRecipe(
                GAS_COLLECTOR_MV.ID,
                "GasCollectorMV",
                TextLocalization.GasCollectorMV,
                2,
                new String[] { TextLocalization.Tooltip_GasCollector_00,
                    StatCollector.translateToLocal("GT5U.MBTT.MachineType") + ": "
                        + EnumChatFormatting.YELLOW
                        + TextLocalization.GasCollectorRecipeType
                        + EnumChatFormatting.RESET },
                RecipeRegister.GasCollectorRecipes,
                3,
                3,
                true,
                SoundResource.NONE,
                MTEBasicMachineWithRecipe.SpecialEffects.NONE,
                "GAS_COLLECTOR",
                null).getStackForm(1L));

        GTNLItemList.GasCollectorHV.set(
            new MTEBasicMachineWithRecipe(
                GAS_COLLECTOR_HV.ID,
                "GasCollectorHV",
                TextLocalization.GasCollectorHV,
                3,
                new String[] { TextLocalization.Tooltip_GasCollector_00,
                    StatCollector.translateToLocal("GT5U.MBTT.MachineType") + ": "
                        + EnumChatFormatting.YELLOW
                        + TextLocalization.GasCollectorRecipeType
                        + EnumChatFormatting.RESET },
                RecipeRegister.GasCollectorRecipes,
                3,
                3,
                true,
                SoundResource.NONE,
                MTEBasicMachineWithRecipe.SpecialEffects.NONE,
                "GAS_COLLECTOR",
                null).getStackForm(1L));

        GTNLItemList.GasCollectorEV.set(
            new MTEBasicMachineWithRecipe(
                GAS_COLLECTOR_EV.ID,
                "GasCollectorEV",
                TextLocalization.GasCollectorEV,
                4,
                new String[] { TextLocalization.Tooltip_GasCollector_00,
                    StatCollector.translateToLocal("GT5U.MBTT.MachineType") + ": "
                        + EnumChatFormatting.YELLOW
                        + TextLocalization.GasCollectorRecipeType
                        + EnumChatFormatting.RESET },
                RecipeRegister.GasCollectorRecipes,
                3,
                3,
                true,
                SoundResource.NONE,
                MTEBasicMachineWithRecipe.SpecialEffects.NONE,
                "GAS_COLLECTOR",
                null).getStackForm(1L));

        GTNLItemList.GasCollectorIV.set(
            new MTEBasicMachineWithRecipe(
                GAS_COLLECTOR_IV.ID,
                "GasCollectorIV",
                TextLocalization.GasCollectorIV,
                5,
                new String[] { TextLocalization.Tooltip_GasCollector_01,
                    StatCollector.translateToLocal("GT5U.MBTT.MachineType") + ": "
                        + EnumChatFormatting.YELLOW
                        + TextLocalization.GasCollectorRecipeType
                        + EnumChatFormatting.RESET },
                RecipeRegister.GasCollectorRecipes,
                3,
                3,
                true,
                SoundResource.NONE,
                MTEBasicMachineWithRecipe.SpecialEffects.NONE,
                "GAS_COLLECTOR",
                null).getStackForm(1L));

        GTNLItemList.GasCollectorLuV.set(
            new MTEBasicMachineWithRecipe(
                GAS_COLLECTOR_LUV.ID,
                "GasCollectorLuV",
                TextLocalization.GasCollectorLuV,
                6,
                new String[] { TextLocalization.Tooltip_GasCollector_01,
                    StatCollector.translateToLocal("GT5U.MBTT.MachineType") + ": "
                        + EnumChatFormatting.YELLOW
                        + TextLocalization.GasCollectorRecipeType
                        + EnumChatFormatting.RESET },
                RecipeRegister.GasCollectorRecipes,
                3,
                3,
                true,
                SoundResource.NONE,
                MTEBasicMachineWithRecipe.SpecialEffects.NONE,
                "GAS_COLLECTOR",
                null).getStackForm(1L));

        GTNLItemList.GasCollectorZPM.set(
            new MTEBasicMachineWithRecipe(
                GAS_COLLECTOR_ZPM.ID,
                "GasCollectorZPM",
                TextLocalization.GasCollectorZPM,
                7,
                new String[] { TextLocalization.Tooltip_GasCollector_01,
                    StatCollector.translateToLocal("GT5U.MBTT.MachineType") + ": "
                        + EnumChatFormatting.YELLOW
                        + TextLocalization.GasCollectorRecipeType
                        + EnumChatFormatting.RESET },
                RecipeRegister.GasCollectorRecipes,
                3,
                3,
                true,
                SoundResource.NONE,
                MTEBasicMachineWithRecipe.SpecialEffects.NONE,
                "GAS_COLLECTOR",
                null).getStackForm(1L));

        GTNLItemList.GasCollectorUV.set(
            new MTEBasicMachineWithRecipe(
                GAS_COLLECTOR_UV.ID,
                "GasCollectorUV",
                TextLocalization.GasCollectorUV,
                8,
                new String[] { TextLocalization.Tooltip_GasCollector_02,
                    StatCollector.translateToLocal("GT5U.MBTT.MachineType") + ": "
                        + EnumChatFormatting.YELLOW
                        + TextLocalization.GasCollectorRecipeType
                        + EnumChatFormatting.RESET },
                RecipeRegister.GasCollectorRecipes,
                3,
                3,
                true,
                SoundResource.NONE,
                MTEBasicMachineWithRecipe.SpecialEffects.NONE,
                "GAS_COLLECTOR",
                null).getStackForm(1L));

        GTNLItemList.GasCollectorUHV.set(
            new MTEBasicMachineWithRecipe(
                GAS_COLLECTOR_UHV.ID,
                "GasCollectorUHV",
                TextLocalization.GasCollectorUHV,
                9,
                new String[] { TextLocalization.Tooltip_GasCollector_02,
                    StatCollector.translateToLocal("GT5U.MBTT.MachineType") + ": "
                        + EnumChatFormatting.YELLOW
                        + TextLocalization.GasCollectorRecipeType
                        + EnumChatFormatting.RESET },
                RecipeRegister.GasCollectorRecipes,
                3,
                3,
                true,
                SoundResource.NONE,
                MTEBasicMachineWithRecipe.SpecialEffects.NONE,
                "GAS_COLLECTOR",
                null).getStackForm(1L));

        GTNLItemList.GasCollectorUEV.set(
            new MTEBasicMachineWithRecipe(
                GAS_COLLECTOR_UEV.ID,
                "GasCollectorUEV",
                TextLocalization.GasCollectorUEV,
                10,
                new String[] { TextLocalization.Tooltip_GasCollector_02,
                    StatCollector.translateToLocal("GT5U.MBTT.MachineType") + ": "
                        + EnumChatFormatting.YELLOW
                        + TextLocalization.GasCollectorRecipeType
                        + EnumChatFormatting.RESET },
                RecipeRegister.GasCollectorRecipes,
                3,
                3,
                true,
                SoundResource.NONE,
                MTEBasicMachineWithRecipe.SpecialEffects.NONE,
                "GAS_COLLECTOR",
                null).getStackForm(1L));

        GTNLItemList.GasCollectorUIV.set(
            new MTEBasicMachineWithRecipe(
                GAS_COLLECTOR_UIV.ID,
                "GasCollectorUIV",
                TextLocalization.GasCollectorUIV,
                11,
                new String[] { TextLocalization.Tooltip_GasCollector_02,
                    StatCollector.translateToLocal("GT5U.MBTT.MachineType") + ": "
                        + EnumChatFormatting.YELLOW
                        + TextLocalization.GasCollectorRecipeType
                        + EnumChatFormatting.RESET },
                RecipeRegister.GasCollectorRecipes,
                3,
                3,
                true,
                SoundResource.NONE,
                MTEBasicMachineWithRecipe.SpecialEffects.NONE,
                "GAS_COLLECTOR",
                null).getStackForm(1L));

        GTNLItemList.GasCollectorUMV.set(
            new MTEBasicMachineWithRecipe(
                GAS_COLLECTOR_UMV.ID,
                "GasCollectorUMV",
                TextLocalization.GasCollectorUMV,
                12,
                new String[] { TextLocalization.Tooltip_GasCollector_02,
                    StatCollector.translateToLocal("GT5U.MBTT.MachineType") + ": "
                        + EnumChatFormatting.YELLOW
                        + TextLocalization.GasCollectorRecipeType
                        + EnumChatFormatting.RESET },
                RecipeRegister.GasCollectorRecipes,
                3,
                3,
                true,
                SoundResource.NONE,
                MTEBasicMachineWithRecipe.SpecialEffects.NONE,
                "GAS_COLLECTOR",
                null).getStackForm(1L));

        GTNLItemList.GasCollectorUXV.set(
            new MTEBasicMachineWithRecipe(
                GAS_COLLECTOR_UXV.ID,
                "GasCollectorUXV",
                TextLocalization.GasCollectorUXV,
                13,
                new String[] { TextLocalization.Tooltip_GasCollector_03,
                    StatCollector.translateToLocal("GT5U.MBTT.MachineType") + ": "
                        + EnumChatFormatting.YELLOW
                        + TextLocalization.GasCollectorRecipeType
                        + EnumChatFormatting.RESET },
                RecipeRegister.GasCollectorRecipes,
                3,
                3,
                true,
                SoundResource.NONE,
                MTEBasicMachineWithRecipe.SpecialEffects.NONE,
                "GAS_COLLECTOR",
                null).getStackForm(1L));
    }

    public static void registerMTEWireAndPipe() {
        CrackRecipeAdder.registerWire(STAR_GATE_WIRE.ID, MaterialPool.Stargate, 2147483647, 2147483647, 0, true);
        MoreMaterialToolUtil.generateGTFluidPipes(Materials.BlueAlloy, BLUE_ALLOY_PIPE.ID, 4000, 3000, true);
        CrackRecipeAdder.registerPipe(COMPRESSED_STEAM_PIPE.ID, MaterialPool.CompressedSteam, 250000, 10000, true);
        CrackRecipeAdder.registerPipe(STRONZE_PIPE.ID, MaterialPool.Stronze, 15000, 10000, true);
        CrackRecipeAdder.registerPipe(BREEL_PIPE.ID, MaterialPool.Breel, 10000, 10000, true);
        // 这个可用 MoreMaterialToolUtil.generateNonGTFluidPipes(GregtechOrePrefixes.GT_Materials.Void, 22013, 500, 2000,
        // true);
        // 这个渲染炸了 MoreMaterialToolUtil.registerPipeGTPP(22020, MaterialsAlloy.BLOODSTEEL, 123, 123, true);
    }

    public static void loadMachinesPostInit() {
        ResourceCollectionModule = new ResourceCollectionModule(
            RESOURCE_COLLECTION_MODULE.ID,
            "ResourceCollectionModule",
            TextLocalization.NameResourceCollectionModule).getStackForm(1);
        GTNLItemList.ResourceCollectionModule.set(ResourceCollectionModule);
        addItemTooltip(GTNLItemList.ResourceCollectionModule.get(1), AnimatedText.SCIENCE_NOT_LEISURE);

        SuperSpaceElevator = new SuperSpaceElevator(
            SUPER_SPACE_ELEVATOR.ID,
            "SuperSpaceElevator",
            TextLocalization.NameSuperSpaceElevator).getStackForm(1);
        GTNLItemList.SuperSpaceElevator.set(SuperSpaceElevator);
        addItemTooltip(GTNLItemList.SuperSpaceElevator.get(1), AnimatedText.SNL_QYZG);
    }

    private static void registerCovers() {
        GregTechAPI.registerCover(
            GTNLItemList.HydraulicPump.get(1L),
            TextureFactory.of(MACHINE_CASINGS[1][0], TextureFactory.of(OVERLAY_PUMP)),
            new CoverPump(1048576, TextureFactory.of(OVERLAY_PUMP)));

        GregTechAPI.registerCover(
            GTNLItemList.HydraulicConveyor.get(1L),
            TextureFactory.of(MACHINE_CASINGS[1][0], TextureFactory.of(OVERLAY_CONVEYOR)),
            new CoverConveyor(1, 16, TextureFactory.of(OVERLAY_CONVEYOR)));

        GregTechAPI.registerCover(
            GTNLItemList.HydraulicRegulator.get(1L),
            TextureFactory.of(MACHINE_CASINGS[1][0], TextureFactory.of(OVERLAY_PUMP)),
            new CoverFluidRegulator(1048576, TextureFactory.of(OVERLAY_PUMP)));

        GregTechAPI.registerCover(
            GTNLItemList.HydraulicSteamValve.get(1L),
            TextureFactory.of(MACHINE_CASINGS[1][0], TextureFactory.of(OVERLAY_VALVE)),
            new CoverSteamValve(16777216, TextureFactory.of(OVERLAY_VALVE)));

        GregTechAPI.registerCover(
            GTNLItemList.HydraulicSteamRegulator.get(1L),
            TextureFactory.of(MACHINE_CASINGS[1][0], TextureFactory.of(OVERLAY_VALVE)),
            new CoverSteamRegulator(16777216, TextureFactory.of(OVERLAY_VALVE)));

    }

    public static void registerGlasses() {
        BorosilicateGlass.registerGlass(BasicBlocks.PlayerDoll, 0, (byte) 12);
        BorosilicateGlass.registerGlass(BasicBlocks.MetaBlockGlass, 0, (byte) 10);
        BorosilicateGlass.registerGlass(BasicBlocks.MetaBlockGlass, 1, (byte) 8);
        BorosilicateGlass.registerGlass(BasicBlocks.MetaBlockGlass, 2, (byte) 7);

        for (int LampMeta = 1; LampMeta <= 32; LampMeta++) {
            BorosilicateGlass.registerGlass(BasicBlocks.MetaBlockGlow, LampMeta, (byte) 3);
        }

        for (int LampOffMeta = 3; LampOffMeta <= 34; LampOffMeta++) {
            BorosilicateGlass.registerGlass(BasicBlocks.MetaBlock, LampOffMeta, (byte) 3);
        }
    }

    public static void run() {
        Logger.INFO("GTNL Content | Registering MTE Block Machine.");
        registerMTEHatch();
        loadMachines();
        registerMTEWireAndPipe();
        registerBasicMachine();
        registerCovers();
    }
}
