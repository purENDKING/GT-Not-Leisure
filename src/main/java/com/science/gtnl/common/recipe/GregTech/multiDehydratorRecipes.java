package com.science.gtnl.common.recipe.GregTech;

import net.minecraftforge.fluids.FluidRegistry;

import com.science.gtnl.Utils.recipes.IRecipePool;
import com.science.gtnl.Utils.recipes.RecipeBuilder;
import com.science.gtnl.common.materials.MaterialPool;

import gregtech.api.enums.OrePrefixes;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTModHandler;
import gtPlusPlus.api.recipe.GTPPRecipeMaps;

public class multiDehydratorRecipes implements IRecipePool {

    final RecipeMap<?> mD = GTPPRecipeMaps.chemicalDehydratorNonCellRecipes;

    @Override
    public void loadRecipes() {
        RecipeBuilder.builder()
            .fluidInputs(MaterialPool.SilicaGelBase.getFluidOrGas(1000))
            .itemOutputs(
                MaterialPool.SilicaGel.get(OrePrefixes.dust, 3),
                GTModHandler.getModItem("gregtech", "gt.metaitem.01", 2, 2817))
            .specialValue(0)
            .noOptimize()
            .duration(130)
            .eut(480)
            .addTo(mD);

        RecipeBuilder.builder()
            .fluidInputs(FluidRegistry.getFluidStack("boricacid", 2000))
            .itemOutputs(MaterialPool.BoronTrioxide.get(OrePrefixes.dust, 5))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(480)
            .addTo(mD);

        RecipeBuilder.builder()
            .fluidInputs(MaterialPool.PloyamicAcid.getFluidOrGas(144))
            .itemOutputs()
            .fluidOutputs(MaterialPool.Polyimide.getMolten(144))
            .specialValue(0)
            .noOptimize()
            .duration(270)
            .eut(30)
            .addTo(mD);
    }
}
