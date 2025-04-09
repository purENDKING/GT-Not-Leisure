package com.science.gtnl.common.recipe.GTNL;

import net.minecraftforge.fluids.FluidStack;

import com.science.gtnl.Utils.recipes.RecipeBuilder;
import com.science.gtnl.common.recipe.IRecipePool;
import com.science.gtnl.common.recipe.RecipeRegister;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTOreDictUnificator;
import gtPlusPlus.core.item.chemistry.CoalTar;

public class DesulfurizerRecipes implements IRecipePool {

    final RecipeMap<?> DesR = RecipeRegister.DesulfurizerRecipes;

    @Override
    public void loadRecipes() {
        RecipeBuilder.builder()
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 1))
            .fluidInputs(Materials.SulfuricGas.getGas(12000))
            .fluidOutputs(Materials.Gas.getGas(12000))
            .specialValue(0)
            .noOptimize()
            .duration(120)
            .eut(30)
            .addTo(DesR);

        RecipeBuilder.builder()
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 1))
            .fluidInputs(Materials.SulfuricNaphtha.getFluid(12000))
            .fluidOutputs(Materials.Naphtha.getFluid(12000))
            .specialValue(0)
            .noOptimize()
            .duration(120)
            .eut(30)
            .addTo(DesR);

        RecipeBuilder.builder()
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 1))
            .fluidInputs(Materials.SulfuricLightFuel.getFluid(12000))
            .fluidOutputs(Materials.LightFuel.getFluid(12000))
            .specialValue(0)
            .noOptimize()
            .duration(120)
            .eut(30)
            .addTo(DesR);

        RecipeBuilder.builder()
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 1))
            .fluidInputs(Materials.SulfuricHeavyFuel.getFluid(12000))
            .fluidOutputs(Materials.HeavyFuel.getFluid(12000))
            .specialValue(0)
            .noOptimize()
            .duration(120)
            .eut(30)
            .addTo(DesR);

        RecipeBuilder.builder()
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 1))
            .fluidInputs(new FluidStack(CoalTar.Sulfuric_Coal_Tar_Oil, 12000))
            .fluidOutputs(new FluidStack(CoalTar.Naphthalene, 12000))
            .specialValue(0)
            .noOptimize()
            .duration(120)
            .eut(30)
            .addTo(DesR);

        RecipeBuilder.builder()
            .itemOutputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 1))
            .fluidInputs(Materials.NatruralGas.getGas(12000))
            .fluidOutputs(Materials.Gas.getGas(12000))
            .specialValue(0)
            .noOptimize()
            .duration(120)
            .eut(30)
            .addTo(DesR);
    }
}
