package com.science.gtnl.loader;

import com.science.gtnl.common.recipe.GregTech.CircuitAssemblerRecipes;
import com.science.gtnl.common.recipe.IRecipePool;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class RecipeLoaderServerStart {

    private static boolean hasRun = false;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !hasRun) {
            loadRecipesServerStart();
            hasRun = true;
        }
    }

    public static void loadRecipesServerStart() {

        IRecipePool[] recipePools = new IRecipePool[] { new CircuitAssemblerRecipes() };

        for (IRecipePool recipePool : recipePools) {
            recipePool.loadRecipes();
        }
    }
}
