package com.science.gtnl.common.recipe.GTNL;

import static com.science.gtnl.common.GTNLItemList.*;
import static com.science.gtnl.config.MainConfig.*;

import com.science.gtnl.common.recipe.IRecipePool;
import com.science.gtnl.common.recipe.RecipeRegister;

import gregtech.api.enums.GTValues;
import gregtech.api.recipe.RecipeMap;

public class RealArtificialStarRecipes implements IRecipePool {

    final RecipeMap<?> RAS = RecipeRegister.RealArtificialStarRecipes;

    @Override
    public void loadRecipes() {
        GTValues.RA.stdBuilder()
            .itemInputs(EnhancementCore.get(1))
            .specialValue((int) (EUEveryEnhancementCore))
            .eut(0)
            .duration(0)
            .fake()
            .addTo(RAS);
        /*
         * GTValues.RA.stdBuilder()
         * .itemInputs(EnhancementCore.get(1))
         * .itemOutputs(
         * StellarConstructionFrameMaterial.get(1)
         * .setStackDisplayName(
         * texter(
         * "Chance to recover some raw materials. Probability is affected by module tier.",
         * "NEI.RealAntimatterFuelRodGeneratingRecipe.01")))
         * .specialValue((int) (EUEveryEnhancementCore / Integer.MAX_VALUE))
         * .eut(0)
         * .duration(0)
         * .addTo(RAS);
         */

        GTValues.RA.stdBuilder()
            .itemInputs(DepletedExcitedNaquadahFuelRod.get(1))
            .specialValue((int) (EUEveryDepletedExcitedNaquadahFuelRod))
            .eut(0)
            .duration(0)
            .fake()
            .addTo(RAS);
    }
}
