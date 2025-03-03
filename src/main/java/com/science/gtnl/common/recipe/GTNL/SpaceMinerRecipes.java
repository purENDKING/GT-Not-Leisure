package com.science.gtnl.common.recipe.GTNL;

import net.minecraft.item.ItemStack;

import com.gtnewhorizons.gtnhintergalactic.item.IGItems;
import com.gtnewhorizons.gtnhintergalactic.item.ItemMiningDrones;
import com.science.gtnl.common.recipe.IRecipePool;
import com.science.gtnl.common.recipe.RecipeRegister;
import com.science.gtnl.common.recipe.Special.ResourceCollectionModuleTierKey;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gtPlusPlus.core.item.chemistry.RocketFuels;
import gtPlusPlus.core.util.minecraft.FluidUtils;

public class SpaceMinerRecipes implements IRecipePool {

    final ResourceCollectionModuleTierKey MINER_TIER = ResourceCollectionModuleTierKey.INSTANCE;
    final RecipeMap<?> SMR = RecipeRegister.SpaceMinerRecipes;

    @Override
    public void loadRecipes() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(24),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(180, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Naquadah, 1)),
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Chromite, 1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Plutonium, 1)),
                GTUtility.copyAmountUnsafe(30, GTOreDictUnificator.get(OrePrefixes.ore, Materials.NaquadahEnriched, 1)),
                GTUtility.copyAmountUnsafe(90, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Trinium, 1)),
                GTUtility.copyAmountUnsafe(30, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Indium, 1)))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(24),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(180, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Naquadah, 1)),
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Chromite, 1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Plutonium, 1)),
                GTUtility.copyAmountUnsafe(30, GTOreDictUnificator.get(OrePrefixes.ore, Materials.NaquadahEnriched, 1)),
                GTUtility.copyAmountUnsafe(90, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Trinium, 1)),
                GTUtility.copyAmountUnsafe(30, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Indium, 1)))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

    }
}
