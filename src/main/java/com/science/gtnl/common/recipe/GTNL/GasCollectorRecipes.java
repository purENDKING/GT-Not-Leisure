package com.science.gtnl.common.recipe.GTNL;

import static gregtech.api.enums.Mods.Botania;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.science.gtnl.loader.IRecipePool;
import com.science.gtnl.loader.RecipeRegister;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTUtility;
import gtneioreplugin.plugin.block.ModBlocks;

public class GasCollectorRecipes implements IRecipePool {

    final RecipeMap<?> GCR = RecipeRegister.GasCollectorRecipes;

    @Override
    public void loadRecipes() {

        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(1))
            .fluidOutputs(Materials.Air.getGas(10000))
            .duration(200)
            .eut(16)
            .addTo(GCR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(ModBlocks.getBlock("ED"), 0), new ItemStack(Items.glass_bottle, 16))
            .itemOutputs(GTModHandler.getModItem(Botania.ID, "manaResource", 16, 15))
            .duration(100)
            .eut(16)
            .addTo(GCR);
    }
}
