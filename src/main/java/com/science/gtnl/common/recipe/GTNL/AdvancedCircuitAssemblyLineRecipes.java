package com.science.gtnl.common.recipe.GTNL;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import com.science.gtnl.loader.IRecipePool;
import com.science.gtnl.loader.RecipeRegister;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMap;

public class AdvancedCircuitAssemblyLineRecipes implements IRecipePool {

    final RecipeMap<?> ACALR = RecipeRegister.AdvancedCircuitAssemblyLineRecipes;

    @Override
    public void loadRecipes() {

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(Blocks.dirt, 1))
            .itemOutputs(ItemList.Circuit_Crystalprocessor.get(64))
            .specialValue(14)
            .duration(Integer.MAX_VALUE)
            .eut(TierEU.RECIPE_MAX)
            .addTo(ACALR);
    }
}
