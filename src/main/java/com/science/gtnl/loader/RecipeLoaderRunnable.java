package com.science.gtnl.loader;

import com.science.gtnl.common.recipe.AprilFool.OreDictionary.SteamCarpenterOreRecipe;
import com.science.gtnl.common.recipe.Special.OreDictionary.LaserEngraverOreRecipes;
import com.science.gtnl.common.recipe.Special.OreDictionary.PortalToAlfheimOreRecipes;
import com.science.gtnl.common.recipe.Special.OreDictionary.WoodDistillationRecipes;

import gregtech.api.util.GTLog;

public class RecipeLoaderRunnable implements Runnable {

    @Override
    public void run() {
        GTLog.out.println("GTNL: Register Ore Dictionary Recipe.");
        new WoodDistillationRecipes();
        new PortalToAlfheimOreRecipes();
        new LaserEngraverOreRecipes();
        new SteamCarpenterOreRecipe();
    }
}
