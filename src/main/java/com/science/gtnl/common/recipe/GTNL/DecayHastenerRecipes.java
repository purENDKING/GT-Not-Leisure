package com.science.gtnl.common.recipe.GTNL;

import net.minecraftforge.fluids.FluidStack;

import com.science.gtnl.common.materials.MaterialPool;
import com.science.gtnl.common.recipe.IRecipePool;
import com.science.gtnl.common.recipe.RecipeRegister;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMap;
import gtPlusPlus.core.material.MaterialsElements;

public class DecayHastenerRecipes implements IRecipePool {

    final RecipeMap<?> DHR = RecipeRegister.DecayHastenerRecipes;

    @Override
    public void loadRecipes() {

        GTValues.RA.stdBuilder()
            .itemOutputs(MaterialPool.Actinium.get(OrePrefixes.dust, 1))
            .fluidInputs(new FluidStack(MaterialsElements.getInstance().RADIUM.getFluid(), 144))
            .duration(2000)
            .eut(TierEU.RECIPE_HV)
            .addTo(DHR);

    }
}
