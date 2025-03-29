package com.science.gtnl.loader;

import com.science.gtnl.common.recipe.GregTech.ServerStart.AlloyBlastSmelterRecipes;
import com.science.gtnl.common.recipe.GregTech.ServerStart.CircuitAssemblerRecipes;
import com.science.gtnl.common.recipe.GregTech.ServerStart.FormingPressRecipes;
import com.science.gtnl.common.recipe.IRecipePool;

public class RecipeLoaderServerStart {

    public static void loadRecipesServerStart() {

        IRecipePool[] recipePools = new IRecipePool[] { new FormingPressRecipes(), new CircuitAssemblerRecipes(),
            new AlloyBlastSmelterRecipes() };

        for (IRecipePool recipePool : recipePools) {
            recipePool.loadRecipes();
        }
    }
}
