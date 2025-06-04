package com.science.gtnl.loader;

import com.Nxer.TwistSpaceTechnology.recipe.machineRecipe.expanded.CircuitAssemblyLineWithoutImprintRecipePool;
import com.science.gtnl.Utils.recipes.RecipeUtil;
import com.science.gtnl.common.recipe.GregTech.ServerStart.CircuitAssemblyLineRecipes;

import gregtech.api.recipe.RecipeMaps;

public class RecipeLoaderServerStart {

    private static boolean recipesAdded;

    public static void loadRecipesServerStart() {
        if (!recipesAdded) {
            RecipeLoader.loadRecipesServerStart();
        }
        loadCircuitRelatedRecipes();
        recipesAdded = true;
    }

    private static void loadCircuitRelatedRecipes() {
        RecipeUtil.copyAllRecipes(RecipeRegister.ConvertToCircuitAssembler, RecipeMaps.circuitAssemblerRecipes);

        new CircuitAssemblyLineRecipes().loadRecipes();

        if (!recipesAdded && com.science.gtnl.Utils.enums.Mods.TwistSpaceTechnology.isModLoaded()) {
            CircuitAssemblyLineWithoutImprintRecipePool.loadRecipes();
        }
    }
}
