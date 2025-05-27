package com.science.gtnl.common.recipe.GregTech;

import static com.science.gtnl.Utils.enums.Mods.ScienceNotLeisure;
import static gregtech.api.util.GTModHandler.getModItem;
import static tectech.recipe.TecTechRecipeMaps.eyeOfHarmonyRecipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

import org.apache.commons.lang3.tuple.Pair;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.util.GTUtility;
import gtneioreplugin.plugin.block.BlockDimensionDisplay;
import gtneioreplugin.plugin.block.ModBlocks;
import tectech.recipe.EyeOfHarmonyRecipe;

public class EyeOfHarmonyRecipes {

    private ArrayList<Pair<Materials, Long>> processSG(final ArrayList<Materials> validMaterialList) {
        ArrayList<Pair<Materials, Long>> outputList = new ArrayList<>();
        return outputList;
    }

    private void specialStarGateRecipe(final HashMap<String, EyeOfHarmonyRecipe> hashMap,
        final BlockDimensionDisplay blockDimensionDisplay) {
        HashSet<Materials> validMaterialSet = new HashSet<>();
        ArrayList<Materials> validMaterialList = new ArrayList<>(validMaterialSet);

        hashMap.put(
            "SG",
            new EyeOfHarmonyRecipe(
                processSG(validMaterialList),
                blockDimensionDisplay,
                50000,
                1000,
                1000,
                60L,
                9,
                1.0));
    }

    private final HashMap<String, EyeOfHarmonyRecipe> recipeHashMap = new HashMap<>() {

        private static final long serialVersionUID = -3501819612517400500L;
        {
            BlockDimensionDisplay blockDimensionDisplay = (BlockDimensionDisplay) ModBlocks.blocks.get("DD");
            specialStarGateRecipe(this, blockDimensionDisplay);
        }
    };

    public EyeOfHarmonyRecipes() {

        for (EyeOfHarmonyRecipe recipe : recipeHashMap.values()) {
            ItemStack planetItem = recipe.getRecipeTriggerItem()
                .copy();
            planetItem.stackSize = 0;

            GTValues.RA.stdBuilder()
                .itemInputs(planetItem)
                .itemOutputs(
                    GTUtility.copyAmountUnsafe(1919810, getModItem(ScienceNotLeisure.ID, "StargateTier9", 1, 0)))
                .fluidInputs(
                    Materials.Hydrogen.getGas(0),
                    Materials.Helium.getGas(0),
                    MaterialsUEVplus.RawStarMatter.getFluid(0))
                .fluidOutputs(FluidRegistry.getFluidStack("rawstarmatter", 2147483647))
                .duration(recipe.getRecipeTimeInTicks())
                .eut(0)
                .special(recipe)
                .noOptimize()
                .addTo(eyeOfHarmonyRecipes);
        }
    }
}
