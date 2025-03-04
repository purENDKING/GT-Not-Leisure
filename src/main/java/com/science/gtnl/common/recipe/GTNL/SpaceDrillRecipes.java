package com.science.gtnl.common.recipe.GTNL;

import net.minecraft.item.ItemStack;

import com.gtnewhorizons.gtnhintergalactic.item.IGItems;
import com.gtnewhorizons.gtnhintergalactic.item.ItemMiningDrones;
import com.science.gtnl.common.recipe.IRecipePool;
import com.science.gtnl.common.recipe.RecipeRegister;
import com.science.gtnl.common.recipe.Special.ResourceCollectionModuleTierKey;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTUtility;
import gtPlusPlus.core.item.chemistry.RocketFuels;
import gtPlusPlus.core.util.minecraft.FluidUtils;

public class SpaceDrillRecipes implements IRecipePool {

    final ResourceCollectionModuleTierKey MINER_TIER = ResourceCollectionModuleTierKey.INSTANCE;
    final RecipeMap<?> SDR = RecipeRegister.SpaceDrillRecipes;

    @Override
    public void loadRecipes() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Hydrogen.getGas(1000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Hydrogen.getGas(1000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(300)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(2),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Helium.getGas(1000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(2),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Helium.getGas(1000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(300)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(3),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Nitrogen.getGas(1000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(3),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Nitrogen.getGas(1000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(300)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(4),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Methane.getGas(1000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(4),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Methane.getGas(1000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(300)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.SulfurDioxide.getGas(1000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.SulfurDioxide.getGas(1000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(300)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(6),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.CarbonDioxide.getGas(1000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(6),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.CarbonDioxide.getGas(1000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(300)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(7),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.NitrogenDioxide.getGas(1000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(7),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.NitrogenDioxide.getGas(1000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(300)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(8),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Ammonia.getGas(1000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(8),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Ammonia.getGas(1000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(300)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Chlorine.getGas(1000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Chlorine.getGas(1000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(300)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(10),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Chlorine.getGas(1000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(10),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Fluorine.getGas(1000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(300)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(11),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(1000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(11),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(1000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(300)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(12),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Oxygen.getGas(1000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(12),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Oxygen.getGas(1000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(300)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);
    }
}
