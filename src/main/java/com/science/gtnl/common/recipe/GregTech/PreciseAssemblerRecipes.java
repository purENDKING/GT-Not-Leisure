package com.science.gtnl.common.recipe.GregTech;

import static gregtech.api.util.GTRecipeConstants.PRECISE_ASSEMBLER_CASING_TIER;

import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.common.materials.MaterialPool;
import com.science.gtnl.common.recipe.IRecipePool;

import goodgenerator.api.recipe.GoodGeneratorRecipeMaps;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTOreDictUnificator;

public class PreciseAssemblerRecipes implements IRecipePool {

    final RecipeMap<?> PAR = GoodGeneratorRecipeMaps.preciseAssemblerRecipes;

    @Override
    public void loadRecipes() {

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTNLItemList.NeutroniumWafer.get(4),
                ItemList.Circuit_Chip_Biocell.get(64),
                MaterialPool.Actinium.get(OrePrefixes.dust, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Strontium, 1))
            .itemOutputs(ItemList.Circuit_Wafer_Bioware.get(4))
            .fluidInputs(
                Materials.BioMediumSterilized.getFluid(1000),
                Materials.Lubricant.getFluid(1000),
                Materials.Nitrogen.getGas(10000))
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .metadata(PRECISE_ASSEMBLER_CASING_TIER, 3)
            .addTo(PAR);

    }
}
