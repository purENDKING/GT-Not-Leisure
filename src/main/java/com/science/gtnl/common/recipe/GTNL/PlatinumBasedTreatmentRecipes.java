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
import gtPlusPlus.core.material.MaterialMisc;

public class PlatinumBasedTreatmentRecipes implements IRecipePool {

    final RecipeMap<?> PBTR = RecipeRegister.PlatinumBasedTreatmentRecipes;

    @Override
    public void loadRecipes() {

        RecipeBuilder.builder()
            .itemInputs(
                GTUtility.copyAmountUnsafe(100, WerkstoffLoader.PTMetallicPowder.get(OrePrefixes.dust, 1)),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 7),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 15),
                GTUtility.copyAmountUnsafe(74, GTOreDictUnificator.get(OrePrefixes.dust, Materials.Saltpeter, 1)),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Calcium, 45),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.SodiumHydroxide, 21),
                MaterialMisc.SODIUM_NITRATE.getDust(15))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sodium, 18),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Silicon, 5),
                GTUtility.copyAmountUnsafe(72, GTOreDictUnificator.get(OrePrefixes.dust, Materials.Platinum, 1)),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Palladium, 29),
                WerkstoffLoader.Rhodium.get(OrePrefixes.dust, 15),
                WerkstoffLoader.Ruthenium.get(OrePrefixes.dust, 54),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Iridium, 23),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Osmium, 3),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Gold, 9),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Copper, 11),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Nickel, 11))
            .fluidInputs(
                Materials.Oxygen.getGas(176000),
                Materials.Hydrogen.getGas(561000),
                Materials.Chlorine.getGas(89000),
                Materials.Ammonia.getGas(86000))
            .fluidOutputs(Materials.Ethylene.getGas(29000))
            .specialValue(0)
            .noOptimize()
            .duration(12000)
            .eut(TierEU.RECIPE_IV)
            .addTo(PBTR);
    }
}
