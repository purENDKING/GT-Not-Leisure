package com.science.gtnl.common.recipe.GregTech;

import static bartworks.util.BWRecipes.computeSieverts;
import static gregtech.api.enums.Materials.NaquadahEnriched;
import static gregtech.api.enums.Materials.Tritanium;
import static gregtech.api.util.GTRecipeConstants.*;

import com.dreammaster.gthandler.CustomItemList;
import com.science.gtnl.common.recipe.IRecipePool;

import bartworks.API.recipe.BartWorksRecipeMaps;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTOreDictUnificator;

public class BacterialVatRecipes implements IRecipePool {

    final RecipeMap<?> BVR = BartWorksRecipeMaps.bacterialVatRecipes;

    @Override
    public void loadRecipes() {

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Chip_Stemcell.get(64),
                GTOreDictUnificator.get(OrePrefixes.dust, NaquadahEnriched, 1L))
            .itemOutputs(ItemList.Circuit_Chip_Biocell.get(64))
            .fluidInputs(Materials.BioMediumSterilized.getFluid(1000))
            .eut(TierEU.RECIPE_ZPM)
            .metadata(SIEVERTS, computeSieverts(Materials.NaquadahEnriched, 7, false, false, false))
            .noOptimize()
            .duration(800)
            .addTo(BVR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Chip_Stemcell.get(64),
                CustomItemList.TCetiESeaweedExtract.get(16L),
                GTOreDictUnificator.get(OrePrefixes.dust, Tritanium, 1L))
            .fluidInputs(Materials.GrowthMediumRaw.getFluid(10))
            .fluidOutputs(Materials.BioMediumRaw.getFluid(10))
            .eut(TierEU.RECIPE_EV)
            .metadata(SIEVERTS, computeSieverts(Materials.NaquadahEnriched, 7, false, false, false))
            .noOptimize()
            .duration(1200)
            .addTo(BVR);

    }
}
