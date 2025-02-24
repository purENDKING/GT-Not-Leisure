package com.science.gtnl.common.recipe.Special;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import com.science.gtnl.config.MainConfig;

import gregtech.api.enums.ItemList;
import gregtech.api.recipe.RecipeMapBackend;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTRecipe;

public class RemoveRecipes {

    public static void removeRecipes() {

        if (MainConfig.enableDeleteRecipe) {
            RecipeMapBackend recipeMapBackend = RecipeMaps.autoclaveRecipes.getBackend();
            List<GTRecipe> recipesToRemove = new ArrayList<>();
            for (GTRecipe recipe : recipeMapBackend.getAllRecipes()) {
                for (ItemStack output : recipe.mOutputs) {
                    if (output != null && output.isItemEqual(ItemList.Circuit_Wafer_Bioware.get(1))) {
                        recipesToRemove.add(recipe);
                        break;
                    }
                }
            }
            recipeMapBackend.removeRecipes(recipesToRemove);
            System.out.println("GTNL: 移除了" + recipesToRemove.size() + "个配方");
        }

    }
}
