package com.science.gtnl.common.recipe.GregTech;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

import com.science.gtnl.Utils.recipes.RecipeBuilder;
import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.common.materials.MaterialPool;
import com.science.gtnl.common.recipe.IRecipePool;

import goodgenerator.items.GGMaterial;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTOreDictUnificator;
import gtPlusPlus.api.recipe.GTPPRecipeMaps;
import gtPlusPlus.core.material.MaterialMisc;
import gtnhlanth.common.register.WerkstoffMaterialPool;

public class MixerRecipes implements IRecipePool {

    final RecipeMap<?> MNCR = GTPPRecipeMaps.mixerNonCellRecipes;

    @Override
    public void loadRecipes() {
        RecipeBuilder.builder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.SodiumHydroxide, 3L),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.SiliconDioxide, 3L))
            .itemOutputs()
            .fluidInputs(FluidRegistry.getFluidStack("ic2distilledwater", 1000))
            .fluidOutputs(MaterialPool.SilicaGelBase.getFluidOrGas(1000))
            .specialValue(0)
            .noOptimize()
            .duration(130)
            .eut(480)
            .addTo(MNCR);

        RecipeBuilder.builder()
            .itemInputs(MaterialMisc.SODIUM_NITRATE.getDust(5))
            .itemOutputs()
            .fluidInputs(Materials.Water.getFluid(1000))
            .fluidOutputs(MaterialPool.SodiumNitrateSolution.getFluidOrGas(1000))
            .specialValue(0)
            .noOptimize()
            .duration(80)
            .eut(120)
            .addTo(MNCR);

        RecipeBuilder.builder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Zinc, 1L),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Iron, 1L),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Aluminium, 1L))
            .fluidInputs(Materials.Chlorine.getGas(1000))
            .itemOutputs(MaterialPool.ZnFeAlCl.get(OrePrefixes.dust, 4))
            .specialValue(0)
            .noOptimize()
            .duration(250)
            .eut(TierEU.RECIPE_LuV)
            .addTo(MNCR);

        RecipeBuilder.builder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Naquadah, 2L))
            .fluidInputs(WerkstoffMaterialPool.AmmoniumNitrate.getFluidOrGas(1000))
            .fluidOutputs(GGMaterial.naquadahSolution.getFluidOrGas(1000))
            .specialValue(0)
            .noOptimize()
            .duration(400)
            .eut(TierEU.RECIPE_LuV)
            .addTo(MNCR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.FierySteel, 4L),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Steel, 8L),
                new ItemStack(Items.bone, 16),
                new ItemStack(Items.fire_charge, 4),
                new ItemStack(Items.blaze_powder, 16),
                new ItemStack(Items.blaze_rod, 8))
            .itemOutputs(GTNLItemList.BlazeCube.get(1))
            .fluidInputs(FluidRegistry.getFluidStack("pyrotheum", 2000))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(TierEU.RECIPE_IV)
            .addTo(MNCR);

    }
}
