package com.science.gtnl.common.recipe.GregTech.ServerStart;

import java.util.HashSet;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.google.common.collect.ArrayListMultimap;
import com.science.gtnl.common.recipe.IRecipePool;

import bartworks.API.recipe.BartWorksRecipeMaps;
import bartworks.system.material.CircuitGeneration.CircuitImprintLoader;
import bartworks.util.BWUtil;
import gregtech.api.enums.Materials;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTRecipe;

public class CircuitAssemblyLineRecipes implements IRecipePool {

    public static final ArrayListMultimap<NBTTagCompound, GTRecipe> recipeTagMap = ArrayListMultimap.create();
    private static final HashSet<GTRecipe> ORIGINAL_CAL_RECIPES = new HashSet<>();
    private static final HashSet<GTRecipe> MODIFIED_CAL_RECIPES = new HashSet<>();

    @Override
    public void loadRecipes() {
        HashSet<GTRecipe> toRem = new HashSet<>();
        HashSet<GTRecipe> toAdd = new HashSet<>();
        deleteCALRecipesAndTags();
        rebuildCircuitAssemblerMap(toRem, toAdd);
    }

    private static void deleteCALRecipesAndTags() {
        BartWorksRecipeMaps.circuitAssemblyLineRecipes.getBackend()
            .clearRecipes();
        recipeTagMap.clear();
    }

    private static void rebuildCircuitAssemblerMap(HashSet<GTRecipe> toRem, HashSet<GTRecipe> toAdd) {
        reAddOriginalRecipes();
        RecipeMaps.circuitAssemblerRecipes.getAllRecipes()
            .forEach(e -> handleCircuitRecipeRebuilding(e, toRem, toAdd));
    }

    private static void reAddOriginalRecipes() {
        RecipeMaps.circuitAssemblerRecipes.getBackend()
            .removeRecipes(MODIFIED_CAL_RECIPES);
        ORIGINAL_CAL_RECIPES.forEach(RecipeMaps.circuitAssemblerRecipes::add);
        ORIGINAL_CAL_RECIPES.clear();
        MODIFIED_CAL_RECIPES.clear();
    }

    private static void handleCircuitRecipeRebuilding(GTRecipe circuitRecipe, HashSet<GTRecipe> toRem,
        HashSet<GTRecipe> toAdd) {
        ItemStack[] outputs = circuitRecipe.mOutputs;
        boolean isOrePass = isCircuitOreDict(outputs[0]);
        String unlocalizedName = outputs[0].getUnlocalizedName();
        if (isOrePass || unlocalizedName.contains("Circuit") || unlocalizedName.contains("circuit")) {

            CircuitImprintLoader.recipeTagMap
                .put(CircuitImprintLoader.getTagFromStack(outputs[0]), circuitRecipe.copy());

            Fluid solderIndalloy = FluidRegistry.getFluid("molten.indalloy140") != null
                ? FluidRegistry.getFluid("molten.indalloy140")
                : FluidRegistry.getFluid("molten.solderingalloy");

            Fluid solderUEV = FluidRegistry.getFluid("molten.mutatedlivingsolder") != null
                ? FluidRegistry.getFluid("molten.mutatedlivingsolder")
                : FluidRegistry.getFluid("molten.solderingalloy");

            if (circuitRecipe.mFluidInputs[0].isFluidEqual(Materials.SolderingAlloy.getMolten(0))
                || circuitRecipe.mFluidInputs[0].isFluidEqual(new FluidStack(solderIndalloy, 0))
                || circuitRecipe.mFluidInputs[0].isFluidEqual(new FluidStack(solderUEV, 0))) {
                GTRecipe newRecipe = CircuitImprintLoader.reBuildRecipe(circuitRecipe);
                if (newRecipe != null) BartWorksRecipeMaps.circuitAssemblyLineRecipes.addRecipe(newRecipe);
            } else if (circuitRecipe.mEUt > TierEU.IV) toRem.add(circuitRecipe);
        }
    }

    private static boolean isCircuitOreDict(ItemStack item) {
        return BWUtil.isTieredCircuit(item) || BWUtil.getOreNames(item)
            .stream()
            .anyMatch(s -> "circuitPrimitiveArray".equals(s));
    }
}
