package com.science.gtnl.common.recipe.GTNL;

import com.science.gtnl.Utils.recipes.IRecipePool;
import com.science.gtnl.common.recipe.RecipeRegister;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTUtility;

public class GasCollectorRecipes implements IRecipePool {

    final RecipeMap<?> GCR = RecipeRegister.GasCollectorRecipes;

    @Override
    public void loadRecipes() {

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(1))
            .fluidOutputs(Materials.Air.getGas(10000))
            .duration(200)
            .eut(16)
            .addTo(GCR);
    }
}
