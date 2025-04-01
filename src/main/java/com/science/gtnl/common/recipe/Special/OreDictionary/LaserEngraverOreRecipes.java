package com.science.gtnl.common.recipe.Special.OreDictionary;

import net.minecraft.item.ItemStack;

import com.science.gtnl.common.GTNLItemList;

import gregtech.api.enums.*;
import gregtech.api.interfaces.IOreRecipeRegistrator;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTUtility;

public class LaserEngraverOreRecipes implements IOreRecipeRegistrator {

    final RecipeMap<?> lER = RecipeMaps.laserEngraverRecipes;

    public LaserEngraverOreRecipes() {}

    public void recipeWithPurifiedWater(ItemStack[] inputs, ItemStack[] outputs, Materials lowTierWater,
        Materials highTierWater, int duration, int boostedDuration, long eut) {
        GTValues.RA.stdBuilder()
            .itemInputs(inputs)
            .itemOutputs(outputs)
            .fluidInputs(lowTierWater.getFluid(100L))
            .duration(duration)
            .eut(eut)
            .addTo(lER);
        GTValues.RA.stdBuilder()
            .itemInputs(inputs)
            .itemOutputs(outputs)
            .fluidInputs(highTierWater.getFluid(100L))
            .duration(boostedDuration)
            .eut(eut)
            .addTo(lER);
    }

    @Override
    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName,
        ItemStack aStack) {
        if (aOreDictName.equals("craftingLensWhite")) {

            recipeWithPurifiedWater(
                new ItemStack[] { GTNLItemList.NeutroniumWafer.get(1), GTUtility.copyAmount(0, aStack) },
                new ItemStack[] { GTUtility.copyAmountUnsafe(256, ItemList.Circuit_Wafer_CPU.get(1)) },
                Materials.Grade5PurifiedWater,
                Materials.Grade6PurifiedWater,
                50,
                25,
                TierEU.RECIPE_IV);
        }
        if (aOreDictName.equals("craftingLensGreen")) {

            recipeWithPurifiedWater(
                new ItemStack[] { GTNLItemList.NeutroniumWafer.get(1), GTUtility.copyAmount(0, aStack) },
                new ItemStack[] { GTUtility.copyAmountUnsafe(16, ItemList.Circuit_Wafer_SoC2.get(1)) },
                Materials.Grade5PurifiedWater,
                Materials.Grade6PurifiedWater,
                500,
                250,
                TierEU.RECIPE_IV);
        }
        if (aOreDictName.equals("craftingLensYellow")) {

            recipeWithPurifiedWater(
                new ItemStack[] { GTNLItemList.NeutroniumWafer.get(1), GTUtility.copyAmount(0, aStack) },
                new ItemStack[] { GTUtility.copyAmountUnsafe(64, ItemList.Circuit_Wafer_SoC.get(1)) },
                Materials.Grade5PurifiedWater,
                Materials.Grade6PurifiedWater,
                500,
                250,
                TierEU.RECIPE_IV);

            recipeWithPurifiedWater(
                new ItemStack[] { GTNLItemList.NeutroniumWafer.get(1), GTUtility.copyAmount(0, aStack) },
                new ItemStack[] { GTUtility.copyAmountUnsafe(256, ItemList.Circuit_Wafer_ULPIC.get(1)) },
                Materials.Grade5PurifiedWater,
                Materials.Grade6PurifiedWater,
                50,
                25,
                TierEU.RECIPE_IV);

        }
        if (aOreDictName.equals("craftingLensOrange")) {

            recipeWithPurifiedWater(
                new ItemStack[] { GTNLItemList.NeutroniumWafer.get(1), GTUtility.copyAmount(0, aStack) },
                new ItemStack[] { GTUtility.copyAmountUnsafe(256, ItemList.Circuit_Wafer_Simple_SoC.get(1)) },
                Materials.Grade5PurifiedWater,
                Materials.Grade6PurifiedWater,
                50,
                25,
                TierEU.RECIPE_IV);

        }
        if (aOreDictName.equals("craftingLensBlue")) {

            recipeWithPurifiedWater(
                new ItemStack[] { GTNLItemList.NeutroniumWafer.get(1), GTUtility.copyAmountUnsafe(0, aStack) },
                new ItemStack[] { GTUtility.copyAmountUnsafe(64, ItemList.Circuit_Wafer_PIC.get(1)) },
                Materials.Grade5PurifiedWater,
                Materials.Grade6PurifiedWater,
                200,
                100,
                TierEU.RECIPE_IV);

        }
        if (aOreDictName.equals("craftingLensCyan")) {

            recipeWithPurifiedWater(
                new ItemStack[] { GTNLItemList.NeutroniumWafer.get(1), GTUtility.copyAmountUnsafe(0, aStack) },
                new ItemStack[] { GTUtility.copyAmountUnsafe(256, ItemList.Circuit_Wafer_Ram.get(1)) },
                Materials.Grade5PurifiedWater,
                Materials.Grade6PurifiedWater,
                50,
                25,
                TierEU.RECIPE_IV);

        }
        if (aOreDictName.equals("craftingLensBlack")) {

            recipeWithPurifiedWater(
                new ItemStack[] { GTNLItemList.NeutroniumWafer.get(1), GTUtility.copyAmountUnsafe(0, aStack) },
                new ItemStack[] { GTUtility.copyAmountUnsafe(1, GTNLItemList.HighlyAdvancedSocWafer.get(1)) },
                Materials.Grade5PurifiedWater,
                Materials.Grade6PurifiedWater,
                900,
                450,
                TierEU.RECIPE_IV);

        }
    }
}
