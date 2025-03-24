package com.science.gtnl.common.recipe.GTNL;

import com.science.gtnl.Utils.recipes.RecipeBuilder;
import com.science.gtnl.common.recipe.IRecipePool;
import com.science.gtnl.common.recipe.RecipeRegister;

import bartworks.system.material.WerkstoffLoader;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;

public class PlatinumBasedTreatmentRecipes implements IRecipePool {

    final RecipeMap<?> PBTR = RecipeRegister.PlatinumBasedTreatmentRecipes;

    @Override
    public void loadRecipes() {

        RecipeBuilder.builder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 1))
            .itemOutputs(
                GTUtility
                    .copyAmountUnsafe(2147483647, GTOreDictUnificator.get(OrePrefixes.dust, Materials.Platinum, 1)),
                GTUtility
                    .copyAmountUnsafe(2147483647, GTOreDictUnificator.get(OrePrefixes.dust, Materials.Palladium, 1)),
                GTUtility.copyAmountUnsafe(2147483647, WerkstoffLoader.Ruthenium.get(OrePrefixes.dust, 1)),
                GTUtility.copyAmountUnsafe(2147483647, WerkstoffLoader.Rhodium.get(OrePrefixes.dust, 1)),
                GTUtility.copyAmountUnsafe(2147483647, GTOreDictUnificator.get(OrePrefixes.dust, Materials.Iridium, 1)),
                GTUtility.copyAmountUnsafe(2147483647, GTOreDictUnificator.get(OrePrefixes.dust, Materials.Osmium, 1)))
            .fluidInputs()
            .fluidOutputs()
            .specialValue(0)
            .noOptimize()
            .duration(1)
            .eut(TierEU.RECIPE_LV)
            .addTo(PBTR);
    }
}
