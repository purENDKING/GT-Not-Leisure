package com.science.gtnl.common.recipe.AprilFool;

import static gregtech.api.enums.GTValues.RA;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.science.gtnl.Utils.recipes.IRecipePool;
import com.science.gtnl.common.recipe.RecipeRegister;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gtPlusPlus.core.item.ModItems;

public class InfernalCokeRecipes implements IRecipePool {

    final RecipeMap<?> ICR = RecipeRegister.InfernalCockRecipes;

    @Override
    public void loadRecipes() {

        RA.stdBuilder()
            .itemInputs(new ItemStack(Blocks.cactus, 1))
            .itemOutputs(new ItemStack(ModItems.itemCactusCharcoal, 1))
            .fluidOutputs(Materials.Creosote.getFluid(60))
            .duration(20 * SECONDS)
            .eut(0)
            .addTo(ICR);

        RA.stdBuilder()
            .itemInputs(new ItemStack(ModItems.itemCactusCharcoal, 1))
            .itemOutputs(new ItemStack(ModItems.itemCactusCoke, 1))
            .fluidOutputs(Materials.Creosote.getFluid(60))
            .duration(20 * SECONDS)
            .eut(0)
            .addTo(ICR);

        RA.stdBuilder()
            .itemInputs(new ItemStack(Items.reeds, 1))
            .itemOutputs(new ItemStack(ModItems.itemSugarCharcoal, 1))
            .fluidOutputs(Materials.Creosote.getFluid(60))
            .duration(20 * SECONDS)
            .eut(0)
            .addTo(ICR);

        RA.stdBuilder()
            .itemInputs(new ItemStack(ModItems.itemSugarCharcoal, 1))
            .itemOutputs(new ItemStack(ModItems.itemSugarCoke, 1))
            .fluidOutputs(Materials.Creosote.getFluid(60))
            .duration(20 * SECONDS)
            .eut(0)
            .addTo(ICR);

        ArrayList<ItemStack> aLogData = OreDictionary.getOres("logWood");
        for (ItemStack stack : aLogData) {
            GTValues.RA.stdBuilder()
                .itemInputs(GTUtility.copyAmount(1, stack))
                .itemOutputs(GTOreDictUnificator.get(OrePrefixes.gem, Materials.Charcoal, 1))
                .fluidOutputs(Materials.Creosote.getFluid(200))
                .duration(20 * SECONDS)
                .eut(0)
                .addTo(ICR);
        }

        RA.stdBuilder()
            .itemInputs(new ItemStack(Items.coal, 1))
            .itemOutputs(GTOreDictUnificator.get("fuelCoke", 1))
            .fluidOutputs(Materials.Creosote.getFluid(60))
            .duration(20 * SECONDS)
            .eut(0)
            .addTo(ICR);

    }
}
