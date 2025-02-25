package com.science.gtnl.loader;

import com.science.gtnl.common.recipe.GregTech.CircuitAssemblerRecipes;
import com.science.gtnl.common.recipe.IRecipePool;

public class RecipeLoaderServerStart {

    public static void loadRecipesServerStart() {

        IRecipePool[] recipePools = new IRecipePool[] { new CircuitAssemblerRecipes() };

        for (IRecipePool recipePool : recipePools) {
            recipePool.loadRecipes();
        }
    }
}
