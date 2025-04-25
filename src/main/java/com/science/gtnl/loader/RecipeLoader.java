package com.science.gtnl.loader;

import static gregtech.api.enums.Mods.IndustrialCraft2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.science.gtnl.Utils.recipes.IRecipePool;
import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.common.machine.OreProcessing.OP_NormalProcessing;
import com.science.gtnl.common.machine.multiMachineClasses.GTNLProcessingArrayRecipeLoader;
import com.science.gtnl.common.materials.MaterialPool;
import com.science.gtnl.common.recipe.AprilFool.CactusWonderFakeRecipes;
import com.science.gtnl.common.recipe.AprilFool.CraftingTableAprilFoolRecipes;
import com.science.gtnl.common.recipe.AprilFool.InfernalCokeRecipes;
import com.science.gtnl.common.recipe.AprilFool.LavaMakerRecipes;
import com.science.gtnl.common.recipe.AprilFool.RockBreakerRecipes;
import com.science.gtnl.common.recipe.AprilFool.SteamCarpenterRecipe;
import com.science.gtnl.common.recipe.AprilFool.SteamExtractinatorRecipes;
import com.science.gtnl.common.recipe.AprilFool.SteamFusionReactorRecipes;
import com.science.gtnl.common.recipe.AprilFool.SteamGateAssemblerRecipes;
import com.science.gtnl.common.recipe.AprilFool.SteamManufacturerRecipes;
import com.science.gtnl.common.recipe.AprilFool.SteamWoodcutterRecipes;
import com.science.gtnl.common.recipe.GTNL.AdvancedCircuitAssemblyLineRecipes;
import com.science.gtnl.common.recipe.GTNL.AlchemicChemistrySetRecipes;
import com.science.gtnl.common.recipe.GTNL.BloodDemonInjectionRecipes;
import com.science.gtnl.common.recipe.GTNL.CellRegulatorRecipes;
import com.science.gtnl.common.recipe.GTNL.DecayHastenerRecipes;
import com.science.gtnl.common.recipe.GTNL.DesulfurizerRecipes;
import com.science.gtnl.common.recipe.GTNL.ElementCopyingRecipes;
import com.science.gtnl.common.recipe.GTNL.FallingTowerRecipes;
import com.science.gtnl.common.recipe.GTNL.FishingGroundRecipes;
import com.science.gtnl.common.recipe.GTNL.FuelRefiningComplexRecipes;
import com.science.gtnl.common.recipe.GTNL.IsaMillRecipes;
import com.science.gtnl.common.recipe.GTNL.ManaInfusionRecipes;
import com.science.gtnl.common.recipe.GTNL.MatterFabricatorRecipes;
import com.science.gtnl.common.recipe.GTNL.MolecularTransformerRecipes;
import com.science.gtnl.common.recipe.GTNL.NaquadahReactorRecipes;
import com.science.gtnl.common.recipe.GTNL.NatureSpiritArrayRecipes;
import com.science.gtnl.common.recipe.GTNL.PetrochemicalPlantRecipes;
import com.science.gtnl.common.recipe.GTNL.PlatinumBasedTreatmentRecipes;
import com.science.gtnl.common.recipe.GTNL.PortalToAlfheimRecipes;
import com.science.gtnl.common.recipe.GTNL.RareEarthCentrifugalRecipes;
import com.science.gtnl.common.recipe.GTNL.ReFusionReactorRecipes;
import com.science.gtnl.common.recipe.GTNL.RealArtificialStarRecipes;
import com.science.gtnl.common.recipe.GTNL.ShallowChemicalCouplingRecipes;
import com.science.gtnl.common.recipe.GTNL.SmeltingMixingFurnaceRecipes;
import com.science.gtnl.common.recipe.GTNL.SpaceDrillRecipes;
import com.science.gtnl.common.recipe.GTNL.SpaceMinerRecipes;
import com.science.gtnl.common.recipe.GTNL.SteamCrackerRecipes;
import com.science.gtnl.common.recipe.GTNL.Thaumcraft.InfusionCraftingRecipesPool;
import com.science.gtnl.common.recipe.GTNL.Thaumcraft.ShapedArcaneCraftingRecipesPool;
import com.science.gtnl.common.recipe.GTNL.TheTwilightForestRecipes;
import com.science.gtnl.common.recipe.GregTech.AlloyBlastSmelterRecipes;
import com.science.gtnl.common.recipe.GregTech.AlloySmelterRecipes;
import com.science.gtnl.common.recipe.GregTech.AssemblerRecipes;
import com.science.gtnl.common.recipe.GregTech.AssemblingLineRecipes;
import com.science.gtnl.common.recipe.GregTech.AutoclaveRecipes;
import com.science.gtnl.common.recipe.GregTech.BacterialVatRecipes;
import com.science.gtnl.common.recipe.GregTech.BlastFurnaceRecipes;
import com.science.gtnl.common.recipe.GregTech.CentrifugeRecipes;
import com.science.gtnl.common.recipe.GregTech.ChemicalBathRecipes;
import com.science.gtnl.common.recipe.GregTech.ChemicalDehydratorRecipes;
import com.science.gtnl.common.recipe.GregTech.ChemicalRecipes;
import com.science.gtnl.common.recipe.GregTech.CompressorRecipes;
import com.science.gtnl.common.recipe.GregTech.CrackingRecipes;
import com.science.gtnl.common.recipe.GregTech.CraftingTableRecipes;
import com.science.gtnl.common.recipe.GregTech.CuttingRecipes;
import com.science.gtnl.common.recipe.GregTech.DigesterRecipes;
import com.science.gtnl.common.recipe.GregTech.DissolutionTankRecipes;
import com.science.gtnl.common.recipe.GregTech.DistillationTowerRecipes;
import com.science.gtnl.common.recipe.GregTech.DistilleryRecipes;
import com.science.gtnl.common.recipe.GregTech.DragonEvolutionFusionCraftingRecipes;
import com.science.gtnl.common.recipe.GregTech.ElectrolyzerRecipes;
import com.science.gtnl.common.recipe.GregTech.FluidExtraction;
import com.science.gtnl.common.recipe.GregTech.FluidExtractorRecipes;
import com.science.gtnl.common.recipe.GregTech.FusionReactorRecipes;
import com.science.gtnl.common.recipe.GregTech.LaserEngraverRecipes;
import com.science.gtnl.common.recipe.GregTech.MixerRecipes;
import com.science.gtnl.common.recipe.GregTech.PCBFactoryRecipes;
import com.science.gtnl.common.recipe.GregTech.PlasmaForgeRecipes;
import com.science.gtnl.common.recipe.GregTech.PreciseAssemblerRecipes;
import com.science.gtnl.common.recipe.GregTech.SpaceAssemblerRecipes;
import com.science.gtnl.common.recipe.GregTech.TranscendentPlasmaMixerRecipes;
import com.science.gtnl.common.recipe.GregTech.VacuumFurnaceRecipes;
import com.science.gtnl.common.recipe.GregTech.multiDehydratorRecipes;
import com.science.gtnl.common.recipe.Special.OreDictionary.PortalToAlfheimOreRecipes;

import WayofTime.alchemicalWizardry.common.summoning.meteor.MeteorRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import goodgenerator.util.CrackRecipeAdder;
import gregtech.api.util.GTModHandler;

public class RecipeLoader {

    public static void loadRecipesCompleteInit() {

        registerCustomMeteors();

        IRecipePool[] recipePools = new IRecipePool[] { new ChemicalRecipes(), new ElectrolyzerRecipes(),
            new MixerRecipes(), new multiDehydratorRecipes(), new AssemblerRecipes(), new AutoclaveRecipes(),
            new AlloyBlastSmelterRecipes(), new CompressorRecipes(), new ReFusionReactorRecipes(),
            new RealArtificialStarRecipes(), new PortalToAlfheimRecipes(), new NatureSpiritArrayRecipes(),
            new ManaInfusionRecipes(), new TranscendentPlasmaMixerRecipes(), new PlasmaForgeRecipes(),
            new CraftingTableRecipes(), new ChemicalBathRecipes(), new SteamCrackerRecipes(), new DesulfurizerRecipes(),
            new PetrochemicalPlantRecipes(), new FusionReactorRecipes(), new SmeltingMixingFurnaceRecipes(),
            new FluidExtraction(), new DigesterRecipes(), new DissolutionTankRecipes(), new CentrifugeRecipes(),
            new ChemicalDehydratorRecipes(), new RareEarthCentrifugalRecipes(), new MatterFabricatorRecipes(),
            new TheTwilightForestRecipes(), new IsaMillRecipes(), new CellRegulatorRecipes(),
            new VacuumFurnaceRecipes(), new FishingGroundRecipes(), new DistilleryRecipes(),
            new ElementCopyingRecipes(), new AlloySmelterRecipes(), new MolecularTransformerRecipes(),
            new NaquadahReactorRecipes(), new DragonEvolutionFusionCraftingRecipes(), new LaserEngraverRecipes(),
            new BacterialVatRecipes(), new CuttingRecipes(), new BlastFurnaceRecipes(), new FluidExtractorRecipes(),
            new DecayHastenerRecipes(), new PreciseAssemblerRecipes(), new FuelRefiningComplexRecipes(),
            new CrackingRecipes(), new DistillationTowerRecipes(), new SpaceMinerRecipes(), new SpaceDrillRecipes(),
            new SpaceAssemblerRecipes(), new PCBFactoryRecipes(), new PlatinumBasedTreatmentRecipes(),
            new ShallowChemicalCouplingRecipes(), new BloodDemonInjectionRecipes(), new AlchemicChemistrySetRecipes(),
            new AdvancedCircuitAssemblyLineRecipes(), new FallingTowerRecipes() };

        IRecipePool[] recipePoolsAprilFool = new IRecipePool[] { new CraftingTableAprilFoolRecipes(),
            new SteamManufacturerRecipes(), new SteamCarpenterRecipe(), new LavaMakerRecipes(),
            new SteamWoodcutterRecipes(), new SteamGateAssemblerRecipes(), new CactusWonderFakeRecipes(),
            new InfernalCokeRecipes(), new SteamFusionReactorRecipes(), new SteamExtractinatorRecipes(),
            new RockBreakerRecipes()

        };

        new OP_NormalProcessing().enumOreProcessingRecipes();
        new ShapedArcaneCraftingRecipesPool().loadRecipes();
        new InfusionCraftingRecipesPool().loadRecipes();
        GTNLProcessingArrayRecipeLoader.registerDefaultGregtechMaps();

        CrackRecipeAdder.reAddBlastRecipe(MaterialPool.MolybdenumDisilicide, 800, 1920, 2300, true);
        CrackRecipeAdder.reAddBlastRecipe(MaterialPool.HSLASteel, 1000, 480, 1711, true);
        CrackRecipeAdder.reAddBlastRecipe(MaterialPool.Germaniumtungstennitride, 800, 30720, 8200, true);

        for (IRecipePool recipePool : recipePools) {
            recipePool.loadRecipes();
        }

        for (IRecipePool recipePoolAprilFool : recipePoolsAprilFool) {
            recipePoolAprilFool.loadRecipes();
        }

        for (ItemStack stone : OreDictionary.getOres("stone")) {
            PortalToAlfheimOreRecipes.addManaInfusionOreRecipes(stone);
        }

    }

    public static void loadRecipesPostInit() {
        new AssemblingLineRecipes().loadRecipes();
    }

    public static void registerCustomMeteors() {
        MeteorRegistry.registerMeteorParadigm(
            GTNLItemList.StargateTier9.get(1),
            new String[] { "SGCraft:stargateBase:0:10", "SGCraft:stargateRing:0:10", "SGCraft:stargateRing:1:10",
                "bartworks:bw.werkstoffblocks.01:25201:10", "SGCraft:ocInterface:0:10", "SGCraft:rfPowerUnit:0:10",
                "gregtech:gt.blockmachines:21113:10", "gregtech:gt.blockmachines:21008:10",
                "ScienceNotLeisure:MetaCasing:21:10", "ScienceNotLeisure:MetaCasing:22:10", "IC2:blockNuke:0:10" },
            10,
            Integer.MAX_VALUE,
            new String[] { "gregtech:gt.blockmetal9:10:5" },
            5);

        MeteorRegistry.registerMeteorParadigm(
            GTModHandler.getModItem(IndustrialCraft2.ID, "blockNuke", 1),
            new String[] { "miscutils:blockMiningExplosives:0:20", "IC2:blockITNT:0:20", "minecraft:tnt:1:15",
                "HardcoreEnderExpansion:enhanced_tnt:0:15", "BloodArsenal:blood_tnt:0:15",
                "EnderZoo:blockConcussionCharge:0:5", "EnderZoo:blockConfusingCharge:0:5",
                "EnderZoo:blockEnderCharge:0:5", "TConstruct:explosive.slime:0:5", "TConstruct:explosive.slime:2:5",
                "ThaumicHorizons:alchemite:0:15", "IC2:blockNuke:0:10", "minecraft:redstone_block:0:1" },
            100,
            114514,
            new String[] {},
            0);

        MeteorRegistry.registerMeteorParadigm(
            GTNLItemList.StargateSingularity.get(1),
            generateAndMergeMetalBlocks(
                new String[] { "minecraft:diamond_block:0:5", "minecraft:emerald_block:0:5", "minecraft:coal_block:0:5",
                    "minecraft:gold_block:0:5", "minecraft:iron_block:0:5", "minecraft:lapis_block:0:5",
                    "minecraft:redstone_block:0:5", "miscutils:blockBlockAbyssalAlloy:0:5",
                    "miscutils:blockBlockAdvancedNitinol:0:5", "miscutils:blockBlockAncientGranite:0:5",
                    "miscutils:blockBlockArcanite:0:5", "miscutils:blockBlockArceusAlloy2B:0:5",
                    "miscutils:blockBlockAstralTitanium:0:5", "miscutils:blockBlockBabbitAlloy:0:5",
                    "miscutils:blockBlockBlackMetal:0:5", "miscutils:blockBlockBlackTitanium:0:5",
                    "miscutils:blockBlockBloodSteel:0:5", "miscutils:blockBlockBotmium:0:5",
                    "miscutils:blockBlockCelestialTungsten:0:5", "miscutils:blockBlockChromaticGlass:0:5",
                    "miscutils:blockBlockCinobiteA243:0:5", "miscutils:blockBlockCurium:0:5",
                    "miscutils:blockBlockDragonblood:0:5", "miscutils:blockBlockEglinSteel:0:5",
                    "miscutils:blockBlockEnergyCrystal:0:5", "miscutils:blockBlockFermium:0:5",
                    "miscutils:blockBlockGermanium:0:5", "miscutils:blockBlockGrisium:0:5",
                    "miscutils:blockBlockHastelloyC276:0:5", "miscutils:blockBlockHastelloyN:0:5",
                    "miscutils:blockBlockHastelloyW:0:5", "miscutils:blockBlockHastelloyX:0:5",
                    "miscutils:blockBlockHeLiCoPtEr:0:5", "miscutils:blockBlockHS188A:0:5",
                    "miscutils:blockBlockHypogen:0:5", "miscutils:blockBlockIncoloy020:0:5",
                    "miscutils:blockBlockIncoloyDS:0:5", "miscutils:blockBlockIncoloyMA956:0:5",
                    "miscutils:blockBlockInconel625:0:5", "miscutils:blockBlockInconel690:0:5",
                    "miscutils:blockBlockInconel792:0:5", "miscutils:blockBlockIndalloy140:0:5",
                    "miscutils:blockBlockIodine:0:5", "miscutils:blockBlockLafiumCompound:0:5",
                    "miscutils:blockBlockLaurenium:0:5", "miscutils:blockBlockLithium7:0:5",
                    "miscutils:blockBlockMaragingSteel250:0:5", "miscutils:blockBlockMaragingSteel300:0:5",
                    "miscutils:blockBlockMaragingSteel350:0:5", "miscutils:blockBlockNeptunium:0:5",
                    "miscutils:blockBlockNiobiumCarbide:0:5", "miscutils:blockBlockNitinol60:0:5",
                    "miscutils:blockBlockOctiron:0:5", "miscutils:blockBlockPikyonium64B:0:5",
                    "miscutils:blockBlockPlutonium238:0:5", "miscutils:blockBlockPolonium:0:5",
                    "miscutils:blockBlockPotin:0:5", "miscutils:blockBlockProtactinium:0:5",
                    "miscutils:blockBlockQuantum:0:5", "miscutils:blockBlockRadium:0:5",
                    "miscutils:blockBlockRhenium:0:5", "miscutils:blockBlockRhugnor:0:5",
                    "miscutils:blockBlockRunite:0:5", "miscutils:blockBlockSelenium:0:5",
                    "miscutils:blockBlockSiliconCarbide:0:5", "miscutils:blockBlockStaballoy:0:5",
                    "miscutils:blockBlockStellite:0:5", "miscutils:blockBlockTalonite:0:5",
                    "miscutils:blockBlockTantalumCarbide:0:5", "miscutils:blockBlockTantalloy60:0:5",
                    "miscutils:blockBlockTantalloy61:0:5", "miscutils:blockBlockThallium:0:5",
                    "miscutils:blockBlockTitansteel:0:5", "miscutils:blockBlockTriniumNaquadahAlloy:0:5",
                    "miscutils:blockBlockTriniumNaquadahCarbonite:0:5", "miscutils:blockBlockTriniumTitaniumAlloy:0:5",
                    "miscutils:blockBlockTumbaga:0:5", "miscutils:blockBlockTungstenTitaniumCarbide:0:5",
                    "miscutils:blockBlockUranium232:0:5", "miscutils:blockBlockUranium233:0:5",
                    "miscutils:blockBlockWatertightSteel:0:5", "miscutils:blockBlockWhiteMetal:0:5",
                    "miscutils:blockBlockZeron100:0:5", "miscutils:blockBlockZirconiumCarbide:0:5" }),
            100,
            Integer.MAX_VALUE,
            new String[] {},
            0);
    }

    private static final Map<String, Boolean> ORE_DICT_CACHE = new HashMap<>();

    public static String[] generateAndMergeMetalBlocks(String[] originalFillers) {
        List<String> metalVariants = new ArrayList<>();

        for (int blockType = 1; blockType <= 9; blockType++) {
            int maxMeta = (blockType == 9) ? 11 : 15;
            for (int meta = 0; meta <= maxMeta; meta++) {
                String blockKey = String.format("gregtech:gt.blockmetal%d:%d", blockType, meta);
                if (isOreDictRegistered(blockKey)) {
                    metalVariants.add(blockKey + ":5");
                }
            }
        }

        for (int meta = 0; meta <= 32767; meta++) {
            String blockKey = String.format("bartworks:bw.werkstoffblocks.01:%d", meta);
            if (isOreDictRegistered(blockKey)) {
                metalVariants.add(blockKey + ":5");
            }
        }
        if (originalFillers != null) {
            metalVariants.addAll(Arrays.asList(originalFillers));
        }
        return metalVariants.toArray(new String[0]);
    }

    private static boolean isOreDictRegistered(String blockKey) {
        return ORE_DICT_CACHE.computeIfAbsent(blockKey, k -> {
            String[] parts = blockKey.split(":");
            if (parts.length < 3) return false;
            String modId = parts[0];
            String itemName = parts[1];
            int meta = Integer.parseInt(parts[2]);
            ItemStack stack = GameRegistry.findItemStack(modId, itemName, 1);
            if (stack == null) return false;
            stack.setItemDamage(meta);
            int[] oreIds = OreDictionary.getOreIDs(stack);
            return oreIds.length > 0;
        });
    }
}
