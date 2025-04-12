package com.science.gtnl.Utils.recipes;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTRecipe;

public class RecipeUtil {

    public static synchronized void copyAllRecipes(RecipeMap<?> fromMap, RecipeMap<?> toMap) {
        for (GTRecipe recipe : fromMap.getAllRecipes()) {
            if (recipe != null) {
                GTRecipe copiedRecipe = recipe.copy();
                if (copiedRecipe != null) {
                    toMap.add(copiedRecipe);
                }
            }
        }
    }

    public static synchronized void removeMatchingRecipes(RecipeMap<?> sourceMap, RecipeMap<?> targetMap) {
        for (GTRecipe source : sourceMap.getAllRecipes()) {
            List<GTRecipe> matches = targetMap.getAllRecipes()
                .stream()
                .filter(target -> recipesAreEquivalent(source, target))
                .collect(Collectors.toList());

            for (GTRecipe match : matches) {
                targetMap.getBackend()
                    .removeRecipe(match);
            }
        }
    }

    private static boolean recipesAreEquivalent(GTRecipe r1, GTRecipe r2) {
        return itemsMatch(r1.mInputs, r2.mInputs) && itemsMatch(r1.mOutputs, r2.mOutputs)
            && fluidsMatch(r1.mFluidInputs, r2.mFluidInputs)
            && fluidsMatch(r1.mFluidOutputs, r2.mFluidOutputs);
    }

    private static boolean itemsMatch(ItemStack[] a, ItemStack[] b) {
        if (a == null || b == null) return a == b;
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (!ItemStack.areItemStacksEqual(a[i], b[i])) return false;
        }
        return true;
    }

    private static boolean fluidsMatch(FluidStack[] a, FluidStack[] b) {
        if (a == null || b == null) return a == b;
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (!a[i].isFluidEqual(b[i]) || a[i].amount != b[i].amount) return false;
        }
        return true;
    }
}
