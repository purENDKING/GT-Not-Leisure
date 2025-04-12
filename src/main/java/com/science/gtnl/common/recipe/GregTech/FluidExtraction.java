package com.science.gtnl.common.recipe.GregTech;

import net.minecraftforge.fluids.FluidRegistry;

import com.science.gtnl.Utils.recipes.IRecipePool;
import com.science.gtnl.Utils.recipes.RecipeBuilder;
import com.science.gtnl.common.materials.MaterialPool;

import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTModHandler;

public class FluidExtraction implements IRecipePool {

    final RecipeMap<?> fER = RecipeMaps.fluidExtractionRecipes;

    @Override
    public void loadRecipes() {
        RecipeBuilder.builder()
            .itemInputs(GTModHandler.getModItem("bartworks", "gt.bwMetaGeneratedingot", 1, 3))
            .fluidOutputs(FluidRegistry.getFluidStack("molten.zirconium", 144))
            .specialValue(0)
            .noOptimize()
            .duration(4)
            .eut(8)
            .addTo(fER);

        RecipeBuilder.builder()
            .itemInputs(GTModHandler.getModItem("GalaxySpace", "barnardaClog", 64, 0))
            .fluidOutputs(MaterialPool.BarnardaCSappy.getFluidOrGas(500))
            .specialValue(0)
            .noOptimize()
            .duration(4)
            .eut(491520)
            .addTo(fER);
    }
}
