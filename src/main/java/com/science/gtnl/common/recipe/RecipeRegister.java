package com.science.gtnl.common.recipe;

import java.util.Comparator;

import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.common.recipe.Special.IsaMillTierKey;
import com.science.gtnl.common.recipe.Special.NaquadahReactorSpecialValue;
import com.science.gtnl.common.recipe.Special.RealArtificialStarSpecialValue;
import com.science.gtnl.common.recipe.Special.ResourceCollectionModuleTierKey;
import com.science.gtnl.common.recipe.Special.SpaceMinerFrontend;

import goodgenerator.client.GUI.GGUITextures;
import gregtech.api.gui.modularui.GTUITextures;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMapBackend;
import gregtech.api.recipe.RecipeMapBuilder;
import gregtech.api.util.GTRecipe;
import gregtech.nei.formatter.HeatingCoilSpecialValueFormatter;

public class RecipeRegister {

    public static final RecipeMap<RecipeMapBackend> RecombinationFusionReactorRecipes = RecipeMapBuilder
        .of("gtnl.recipe.RecombinationFusionReactorRecipes", RecipeMapBackend::new)
        .maxIO(16, 16, 16, 16)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .frontend(GeneralFrontend::new)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.GenerationEarthEngine.get(1))
                .setMaxRecipesPerPage(1))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> BloodSoulTradingRecipes = RecipeMapBuilder
        .of("gtnl.recipe.BloodSoulTradingRecipes")
        .maxIO(4, 0, 1, 1)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.BloodSoulSacrificialArray.get(1))
                .setMaxRecipesPerPage(2))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> BloodDemonInjectionRecipes = RecipeMapBuilder
        .of("gtnl.recipe.BloodDemonInjectionRecipes")
        .maxIO(4, 1, 1, 1)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.BloodSoulSacrificialArray.get(1))
                .setMaxRecipesPerPage(2))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> AlchemicChemistrySetRecipes = RecipeMapBuilder
        .of("gtnl.recipe.AlchemicChemistrySetRecipes")
        .maxIO(5, 1, 1, 1)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.BloodSoulSacrificialArray.get(1))
                .setMaxRecipesPerPage(2))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> RealArtificialStarRecipes = RecipeMapBuilder
        .of("gtnl.recipe.ArtificialStarGeneratingRecipes")
        .maxIO(1, 1, 0, 0)
        .neiSpecialInfoFormatter(RealArtificialStarSpecialValue.INSTANCE)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(builder -> builder.setDisplayStack(GTNLItemList.RealArtificialStar.get(1)))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> PortalToAlfheimRecipes = RecipeMapBuilder
        .of("gtnl.recipe.PortalToAlfheimRecipes", RecipeMapBackend::new)
        .maxIO(4, 36, 1, 0)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .frontend(GeneralFrontend::new)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.TeleportationArrayToAlfheim.get(1))
                .setMaxRecipesPerPage(1))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> RuneAltarRecipes = RecipeMapBuilder
        .of("gtnl.recipe.RuneAltarRecipes", RecipeMapBackend::new)
        .maxIO(8, 1, 1, 0)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .frontend(GeneralFrontend::new)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.TeleportationArrayToAlfheim.get(1))
                .setMaxRecipesPerPage(1))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> NatureSpiritArrayRecipes = RecipeMapBuilder
        .of("gtnl.recipe.NatureSpiritArrayRecipes")
        .maxIO(1, 0, 0, 1)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(builder -> builder.setDisplayStack(GTNLItemList.TeleportationArrayToAlfheim.get(1)))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> ManaInfusionRecipes = RecipeMapBuilder
        .of("gtnl.recipe.ManaInfusionRecipes")
        .maxIO(4, 1, 1, 0)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(builder -> builder.setDisplayStack(GTNLItemList.TeleportationArrayToAlfheim.get(1)))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> LapotronChipRecipes = RecipeMapBuilder
        .of("gtnl.recipe.LapotronChipRecipes")
        .maxIO(9, 9, 3, 3)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(builder -> builder.setDisplayStack(GTNLItemList.LapotronChip.get(1)))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> SteamCrackerRecipes = RecipeMapBuilder
        .of("gtnl.recipe.SteamCrackerRecipes")
        .maxIO(1, 0, 1, 1)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(builder -> builder.setDisplayStack(GTNLItemList.SteamCracking.get(1)))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> CheatOreProcessingRecipes = RecipeMapBuilder
        .of("gtnl.recipe.CheatOreProcessingRecipes")
        .maxIO(1, 9, 1, 0)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(builder -> builder.setDisplayStack(GTNLItemList.CheatOreProcessingFactory.get(1)))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> DesulfurizerRecipes = RecipeMapBuilder
        .of("gtnl.recipe.DesulfurizerRecipes")
        .maxIO(0, 1, 1, 1)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(builder -> builder.setDisplayStack(GTNLItemList.Desulfurizer.get(1)))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> PetrochemicalPlantRecipes = RecipeMapBuilder
        .of("gtnl.recipe.PetrochemicalPlantRecipes", RecipeMapBackend::new)
        .maxIO(4, 4, 4, 12)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .frontend(GeneralFrontend::new)
        .neiHandlerInfo(builder -> builder.setDisplayStack(GTNLItemList.PetrochemicalPlant.get(1)))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> SmeltingMixingFurnaceRecipes = RecipeMapBuilder
        .of("gtnl.recipe.SmeltingMixingFurnaceRecipes", RecipeMapBackend::new)
        .maxIO(8, 4, 16, 4)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .frontend(GeneralFrontend::new)
        .neiHandlerInfo(builder -> builder.setDisplayStack(GTNLItemList.SmeltingMixingFurnace.get(1)))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> RareEarthCentrifugalRecipes = RecipeMapBuilder
        .of("gtnl.recipe.RareEarthCentrifugalRecipes", RecipeMapBackend::new)
        .maxIO(1, 17, 1, 1)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .frontend(GeneralFrontend::new)
        .neiHandlerInfo(builder -> builder.setDisplayStack(GTNLItemList.RareEarthCentrifugal.get(1)))
        .disableOptimize()
        .build();

    public static RecipeMap<RecipeMapBackend> IndustrialShapedArcaneCraftingRecipes = RecipeMapBuilder
        .of("gtnl.recipe.IndustrialShapedArcaneCraftingRecipes")
        .maxIO(9, 1, 0, 0)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.IndustrialArcaneAssembler.get(1))
                .setMaxRecipesPerPage(1))
        .disableOptimize()
        .build();

    public static RecipeMap<RecipeMapBackend> MatterFabricatorRecipes = RecipeMapBuilder
        .of("gtnl.recipe.MatterFabricatorRecipes")
        .maxIO(2, 1, 0, 1)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.MatterFabricator.get(1))
                .setMaxRecipesPerPage(1))
        .disableOptimize()
        .build();

    public static RecipeMap<RecipeMapBackend> TheTwilightForestRecipes = RecipeMapBuilder
        .of("gtnl.recipe.TheTwilightForestRecipes", RecipeMapBackend::new)
        .maxIO(4, 16, 0, 0)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .frontend(GeneralFrontend::new)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.LibraryOfRuina.get(1))
                .setMaxRecipesPerPage(1))
        .disableOptimize()
        .build();

    public static RecipeMap<RecipeMapBackend> FishingGroundRecipes = RecipeMapBuilder
        .of("gtnl.recipe.FishingGroundRecipes", RecipeMapBackend::new)
        .maxIO(4, 32, 4, 0)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .frontend(GeneralFrontend::new)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.LibraryOfRuina.get(1))
                .setMaxRecipesPerPage(1))
        .disableOptimize()
        .build();

    public static RecipeMap<RecipeMapBackend> IndustrialInfusionCraftingRecipes = RecipeMapBuilder
        .of("gtnl.recipe.IndustrialInfusionCraftingRecipes")
        .maxIO(25, 1, 0, 0)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .frontend(IndustrialInfusionCraftingRecipesFrontend::new)
        .neiTransferRect(100, 45, 18, 72)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.IndustrialArcaneAssembler.get(1))
                .setMaxRecipesPerPage(1))
        .disableOptimize()
        .build();

    public static RecipeMap<RecipeMapBackend> IsaMillRecipes = RecipeMapBuilder.of("gtnl.recipe.IsaMillRecipes")
        .maxIO(2, 1, 1, 0)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.IsaMill.get(1))
                .setMaxRecipesPerPage(1))
        .neiRecipeComparator(
            Comparator.<GTRecipe, Integer>comparing(recipe -> recipe.getMetadataOrDefault(IsaMillTierKey.INSTANCE, 0))
                .thenComparing(GTRecipe::compareTo))
        .disableOptimize()
        .build();

    public static RecipeMap<RecipeMapBackend> CellRegulatorRecipes = RecipeMapBuilder
        .of("gtnl.recipe.CellRegulatorRecipes")
        .maxIO(2, 0, 1, 1)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.FlotationCellRegulator.get(1))
                .setMaxRecipesPerPage(1))
        .disableOptimize()
        .build();

    public static RecipeMap<RecipeMapBackend> ElementCopyingRecipes = RecipeMapBuilder
        .of("gtnl.recipe.ElementCopyingRecipes")
        .maxIO(3, 9, 1, 3)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.ElementCopying.get(1))
                .setMaxRecipesPerPage(1))
        .disableOptimize()
        .build();

    public static RecipeMap<RecipeMapBackend> WoodDistillationRecipes = RecipeMapBuilder
        .of("gtnl.recipe.WoodDistillationRecipes", RecipeMapBackend::new)
        .maxIO(1, 1, 1, 16)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .frontend(GeneralFrontend::new)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.WoodDistillation.get(1))
                .setMaxRecipesPerPage(1))
        .disableOptimize()
        .build();

    public static RecipeMap<RecipeMapBackend> MolecularTransformerRecipes = RecipeMapBuilder
        .of("gtnl.recipe.MolecularTransformerRecipes")
        .maxIO(1, 1, 0, 0)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.MolecularTransformer.get(1))
                .setMaxRecipesPerPage(1))
        .disableOptimize()
        .build();

    public static final RecipeMap<RecipeMapBackend> NaquadahReactorRecipes = RecipeMapBuilder
        .of("gtnl.recipe.NaquadahReactorRecipes")
        .maxIO(0, 0, 2, 0)
        .dontUseProgressBar()
        .neiSpecialInfoFormatter(NaquadahReactorSpecialValue.INSTANCE)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.LargeNaquadahReactor.get(1))
                .setMaxRecipesPerPage(1))
        .disableOptimize()
        .addSpecialTexture(59, 20, 58, 42, GGUITextures.PICTURE_NAQUADAH_REACTOR)
        .build();

    public static RecipeMap<RecipeMapBackend> DecayHastenerRecipes = RecipeMapBuilder
        .of("gtnl.recipe.DecayHastenerRecipes")
        .maxIO(1, 1, 1, 1)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.DecayHastener.get(1))
                .setMaxRecipesPerPage(1))
        .disableOptimize()
        .build();

    public static RecipeMap<RecipeMapBackend> GrandAssemblyLineRecipes = RecipeMapBuilder
        .of("gtnl.recipe.GrandAssemblyLineRecipes")
        .maxIO(16, 1, 4, 0)
        .dontUseProgressBar()
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.GrandAssemblyLine.get(1))
                .setMaxRecipesPerPage(1))
        .disableOptimize()
        .build();

    public static RecipeMap<RecipeMapBackend> FuelRefiningComplexRecipes = RecipeMapBuilder
        .of("gtnl.recipe.FuelRefiningComplexRecipes")
        .maxIO(3, 0, 6, 1)
        .frontend(GeneralFrontend::new)
        .dontUseProgressBar()
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.FuelRefiningComplex.get(1))
                .setMaxRecipesPerPage(1))
        .neiSpecialInfoFormatter(HeatingCoilSpecialValueFormatter.INSTANCE)
        .disableOptimize()
        .build();

    public static RecipeMap<RecipeMapBackend> SpaceMinerRecipes = RecipeMapBuilder.of("gtnl.recipe.SpaceMinerRecipes")
        .maxIO(2, 9, 1, 0)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.ResourceCollectionModule.get(1))
                .setMaxRecipesPerPage(1))
        .neiRecipeComparator(
            Comparator
                .<GTRecipe, Integer>comparing(
                    recipe -> recipe.getMetadataOrDefault(ResourceCollectionModuleTierKey.INSTANCE, 0))
                .thenComparing(GTRecipe::compareTo))
        .neiRecipeComparator(
            Comparator.<GTRecipe, Integer>comparing(recipe -> recipe.mSpecialValue)
                .thenComparing(GTRecipe::compareTo))
        .disableOptimize()
        .frontend(SpaceMinerFrontend::new)
        .build();

    public static RecipeMap<RecipeMapBackend> SpaceDrillRecipes = RecipeMapBuilder.of("gtnl.recipe.SpaceDrillRecipes")
        .maxIO(2, 0, 1, 1)
        .progressBar(GTUITextures.PROGRESSBAR_ARROW_MULTIPLE)
        .neiHandlerInfo(
            builder -> builder.setDisplayStack(GTNLItemList.ResourceCollectionModule.get(1))
                .setMaxRecipesPerPage(1))
        .disableOptimize()
        .build();
}
