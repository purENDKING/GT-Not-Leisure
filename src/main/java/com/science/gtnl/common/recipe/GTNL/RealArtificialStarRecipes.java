package com.science.gtnl.common.recipe.GTNL;

import static com.science.gtnl.Mods.TwistSpaceTechnology;
import static com.science.gtnl.Utils.item.TextHandler.texter;
import static com.science.gtnl.common.GTNLItemList.*;
import static com.science.gtnl.config.MainConfig.*;

import com.science.gtnl.Utils.recipes.IRecipePool;
import com.science.gtnl.common.recipe.RecipeRegister;

import gregtech.api.enums.GTValues;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTModHandler;

public class RealArtificialStarRecipes implements IRecipePool {

    final RecipeMap<?> RAS = RecipeRegister.RealArtificialStarRecipes;

    @Override
    public void loadRecipes() {
        GTValues.RA.stdBuilder()
            .itemInputs(EnhancementCore.get(1))
            .specialValue(euEveryEnhancementCore)
            .eut(0)
            .duration(0)
            .fake()
            .addTo(RAS);

        GTValues.RA.stdBuilder()
            .itemInputs(DepletedExcitedNaquadahFuelRod.get(1))
            .specialValue(euEveryDepletedExcitedNaquadahFuelRod)
            .eut(0)
            .duration(0)
            .fake()
            .addTo(RAS);

        if (TwistSpaceTechnology.isModLoaded()) {

            GTValues.RA.stdBuilder()
                .itemInputs(GTModHandler.getModItem(TwistSpaceTechnology.ID, "MetaItem01", 1, 14))
                .specialValue(3)
                .eut(0)
                .duration(0)
                .fake()
                .addTo(RAS);

            GTValues.RA.stdBuilder()
                .itemInputs(GTModHandler.getModItem(TwistSpaceTechnology.ID, "MetaItem01", 1, 16))
                .itemOutputs(
                    GTModHandler.getModItem(TwistSpaceTechnology.ID, "MetaItem01", 1, 17)
                        .setStackDisplayName(
                            texter(
                                "Chance to recover some raw materials. Probability is affected by module tier.",
                                "NEI.RealAntimatterFuelRodGeneratingRecipe.01")))
                .specialValue(1024)
                .eut(0)
                .duration(0)
                .fake()
                .addTo(RAS);

            GTValues.RA.stdBuilder()
                .itemInputs(GTModHandler.getModItem(TwistSpaceTechnology.ID, "MetaItem01", 1, 29))
                .itemOutputs(
                    GTModHandler.getModItem(TwistSpaceTechnology.ID, "MetaItem01", 1, 17)
                        .setStackDisplayName(
                            texter(
                                "Chance to recover some raw materials. Probability is affected by module tier.",
                                "NEI.RealAntimatterFuelRodGeneratingRecipe.01")))
                .specialValue(32768)
                .eut(0)
                .duration(0)
                .fake()
                .addTo(RAS);
        }
    }
}
