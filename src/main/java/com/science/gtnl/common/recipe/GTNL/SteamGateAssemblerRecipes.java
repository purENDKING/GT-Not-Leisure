package com.science.gtnl.common.recipe.GTNL;

import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.science.gtnl.Utils.recipes.RecipeBuilder;
import com.science.gtnl.common.recipe.IRecipePool;
import com.science.gtnl.common.recipe.RecipeRegister;

import gregtech.api.enums.*;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTOreDictUnificator;

public class SteamGateAssemblerRecipes implements IRecipePool {

    final RecipeMap<?> SGAR = RecipeRegister.steamGateAssemblerRecipes;

    @Override
    public void loadRecipes() {
        ItemStack ironPressure = new ItemStack(Blocks.heavy_weighted_pressure_plate, 1);
        ItemStack goldPressure = new ItemStack(Blocks.light_weighted_pressure_plate, 1);
        ItemStack spdMb = GTOreDictUnificator.get(OrePrefixes.plateSuperdense, Materials.Molybdenum, 1);
        ItemStack spdBA = GTOreDictUnificator.get(OrePrefixes.plateSuperdense, Materials.BlueAlloy, 1);
        ItemStack spdBe = GTOreDictUnificator.get(OrePrefixes.plateSuperdense, Materials.Beryllium, 1);
        ItemStack spdTh = GTOreDictUnificator.get(OrePrefixes.plateSuperdense, Materials.Thorium, 1);

        RecipeBuilder.builder()
            .itemInputsAllowNulls(
                null,
                null,
                null,
                null,
                ironPressure,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                ironPressure,
                goldPressure,
                ironPressure,
                null,
                null,
                null,
                null,
                null,
                ironPressure,
                goldPressure,
                spdMb,
                goldPressure,
                ironPressure,
                null,
                null,
                null,
                ironPressure,
                goldPressure,
                spdBA,
                spdBe,
                spdBA,
                goldPressure,
                ironPressure,
                null,
                ironPressure,
                goldPressure,
                spdMb,
                spdBe,
                spdTh,
                spdBe,
                spdMb,
                goldPressure,
                ironPressure,
                null,
                ironPressure,
                goldPressure,
                spdBA,
                spdBe,
                spdBA,
                goldPressure,
                ironPressure,
                null,
                null,
                null,
                ironPressure,
                goldPressure,
                spdMb,
                goldPressure,
                ironPressure,
                null,
                null,
                null,
                null,
                null,
                ironPressure,
                goldPressure,
                ironPressure,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                ironPressure,
                null,
                null,
                null,
                null)
            .itemOutputs(new ItemStack(Items.record_13, 1))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(SGAR);

    }
}
