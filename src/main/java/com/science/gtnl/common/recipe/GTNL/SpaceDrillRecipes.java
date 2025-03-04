package com.science.gtnl.common.recipe.GTNL;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.gtnewhorizons.gtnhintergalactic.item.IGItems;
import com.gtnewhorizons.gtnhintergalactic.item.ItemMiningDrones;
import com.science.gtnl.common.recipe.IRecipePool;
import com.science.gtnl.common.recipe.RecipeRegister;
import com.science.gtnl.common.recipe.Special.ResourceCollectionModuleTierKey;

import bartworks.system.material.WerkstoffLoader;
import goodgenerator.items.GGMaterial;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTUtility;
import gtPlusPlus.core.item.chemistry.CoalTar;
import gtPlusPlus.core.item.chemistry.RocketFuels;
import gtPlusPlus.core.material.MaterialsElements;
import gtPlusPlus.core.recipe.common.CI;
import gtPlusPlus.core.util.minecraft.FluidUtils;

public class SpaceDrillRecipes implements IRecipePool {

    final ResourceCollectionModuleTierKey MINER_TIER = ResourceCollectionModuleTierKey.INSTANCE;
    final RecipeMap<?> SDR = RecipeRegister.SpaceDrillRecipes;

    @Override
    public void loadRecipes() {
        // UV Tier
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Hydrogen.getGas(10000000))
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
            .fluidOutputs(Materials.Hydrogen.getGas(10000000))
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
            .fluidOutputs(Materials.Helium.getGas(10000000))
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
            .fluidOutputs(Materials.Helium.getGas(10000000))
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
            .fluidOutputs(Materials.Nitrogen.getGas(10000000))
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
            .fluidOutputs(Materials.Nitrogen.getGas(10000000))
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
            .fluidOutputs(Materials.Methane.getGas(10000000))
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
            .fluidOutputs(Materials.Methane.getGas(10000000))
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
            .fluidOutputs(Materials.SulfurDioxide.getGas(10000000))
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
            .fluidOutputs(Materials.SulfurDioxide.getGas(10000000))
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
            .fluidOutputs(Materials.CarbonDioxide.getGas(10000000))
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
            .fluidOutputs(Materials.CarbonDioxide.getGas(10000000))
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
            .fluidOutputs(Materials.NitrogenDioxide.getGas(10000000))
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
            .fluidOutputs(Materials.NitrogenDioxide.getGas(10000000))
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
            .fluidOutputs(Materials.Ammonia.getGas(10000000))
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
            .fluidOutputs(Materials.Ammonia.getGas(10000000))
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
            .fluidOutputs(Materials.Chlorine.getGas(10000000))
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
            .fluidOutputs(Materials.Chlorine.getGas(10000000))
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
            .fluidOutputs(Materials.Chlorine.getGas(10000000))
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
            .fluidOutputs(Materials.Fluorine.getGas(10000000))
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
            .fluidOutputs(Materials.CarbonMonoxide.getGas(10000000))
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
            .fluidOutputs(Materials.CarbonMonoxide.getGas(10000000))
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
            .fluidOutputs(Materials.Oxygen.getGas(10000000))
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
            .fluidOutputs(Materials.Oxygen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 1)
            .duration(300)
            .eut(TierEU.RECIPE_UV)
            .addTo(SDR);

        // UHV Tier
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Hydrogen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(300)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Hydrogen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(150)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(2),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Helium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(300)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(2),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Helium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(150)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(3),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Nitrogen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(300)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(3),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Nitrogen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(150)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(4),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Methane.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(300)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(4),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Methane.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(150)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.SulfurDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(300)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.SulfurDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(150)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(6),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.CarbonDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(300)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(6),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.CarbonDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(150)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(7),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.NitrogenDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(300)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(7),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.NitrogenDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(150)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(8),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Ammonia.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(300)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(8),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Ammonia.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(150)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Chlorine.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(300)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Chlorine.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(150)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(10),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Chlorine.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(300)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(10),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Fluorine.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(150)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(11),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(300)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(11),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(150)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(12),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Oxygen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(300)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(12),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Oxygen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(150)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(13),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(FluidRegistry.getFluidStack("unknowwater", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(300)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(13),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(FluidRegistry.getFluidStack("unknowwater", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(150)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(14),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(WerkstoffLoader.Neon.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(300)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(14),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(WerkstoffLoader.Neon.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(150)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(15),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(Materials.Argon.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(300)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(15),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(Materials.Argon.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(150)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(16),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(WerkstoffLoader.Krypton.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(300)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(16),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(WerkstoffLoader.Krypton.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(150)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(17),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(WerkstoffLoader.Xenon.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(300)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(17),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(WerkstoffLoader.Xenon.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(150)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(Materials.Radon.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(300)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(Materials.Radon.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(150)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(19),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(Materials.Helium_3.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(300)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(19),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UHV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(Materials.Helium_3.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 2)
            .duration(150)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SDR);

        // UEV Tier
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Hydrogen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Hydrogen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(2),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Helium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(2),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Helium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(3),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Nitrogen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(3),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Nitrogen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(4),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Methane.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(4),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Methane.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.SulfurDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.SulfurDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(6),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.CarbonDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(6),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.CarbonDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(7),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.NitrogenDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(7),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.NitrogenDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(8),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Ammonia.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(8),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Ammonia.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Chlorine.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Chlorine.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(10),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Chlorine.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(10),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Fluorine.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(11),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(11),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(12),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Oxygen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(12),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Oxygen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(13),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(FluidRegistry.getFluidStack("unknowwater", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(13),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(FluidRegistry.getFluidStack("unknowwater", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(14),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(WerkstoffLoader.Neon.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(14),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(WerkstoffLoader.Neon.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(15),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(Materials.Argon.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(15),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(Materials.Argon.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(16),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(WerkstoffLoader.Krypton.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(16),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(WerkstoffLoader.Krypton.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(17),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(WerkstoffLoader.Xenon.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(17),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(WerkstoffLoader.Xenon.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(Materials.Radon.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(Materials.Radon.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(19),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(Materials.Helium_3.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(19),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(Materials.Helium_3.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(20),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Deuterium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(20),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Deuterium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(21),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Tritium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(21),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Tritium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(22),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.HeavyFuel.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(22),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.HeavyFuel.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(23),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.LightFuel.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(23),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.LightFuel.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(24),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Naphtha.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(24),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Naphtha.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(1),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Gas.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(1),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Gas.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(2),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(new FluidStack(CoalTar.Coal_Gas, 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(2),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(new FluidStack(CoalTar.Coal_Gas, 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(3),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(new FluidStack(MaterialsElements.getInstance().BROMINE.getFluid(), 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(3),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(new FluidStack(MaterialsElements.getInstance().BROMINE.getFluid(), 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(4),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Methane.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(4),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Methane.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(5),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Oil.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(5),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Oil.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(6),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.OilHeavy.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(6),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.OilHeavy.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(7),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Lava.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(7),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Lava.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(8),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.SaltWater.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(8),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.SaltWater.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(FluidRegistry.getFluidStack("ic2distilledwater", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(FluidRegistry.getFluidStack("ic2distilledwater", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(10),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(FluidRegistry.getFluidStack("pyrotheum", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(10),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(FluidRegistry.getFluidStack("pyrotheum", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(11),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(FluidRegistry.getFluidStack("cryotheum", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(11),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(FluidRegistry.getFluidStack("cryotheum", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(12),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(FluidRegistry.getFluidStack("liquiddna", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(12),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(FluidRegistry.getFluidStack("liquiddna", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(13),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(13),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(14),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(14),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(15),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.SulfuricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(15),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.SulfuricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(16),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.NitricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(16),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.NitricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(17),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.HydrofluoricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(17),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.HydrofluoricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(18),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.PhosphoricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(18),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.PhosphoricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(19),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.PhthalicAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(150)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(19),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UEV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.PhthalicAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 3)
            .duration(75)
            .eut(TierEU.RECIPE_UEV)
            .addTo(SDR);

        // UIV Tier
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Hydrogen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Hydrogen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(2),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Helium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(2),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Helium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(3),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Nitrogen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(3),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Nitrogen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(4),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Methane.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(4),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Methane.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.SulfurDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.SulfurDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(6),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.CarbonDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(6),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.CarbonDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(7),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.NitrogenDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(7),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.NitrogenDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(8),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Ammonia.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(8),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Ammonia.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Chlorine.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Chlorine.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(10),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Chlorine.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(10),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Fluorine.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(11),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(11),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(12),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Oxygen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(12),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Oxygen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(13),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(FluidRegistry.getFluidStack("unknowwater", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(13),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(FluidRegistry.getFluidStack("unknowwater", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(14),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(WerkstoffLoader.Neon.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(14),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(WerkstoffLoader.Neon.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(15),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(Materials.Argon.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(15),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(Materials.Argon.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(16),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(WerkstoffLoader.Krypton.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(16),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(WerkstoffLoader.Krypton.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(17),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(WerkstoffLoader.Xenon.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(17),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(WerkstoffLoader.Xenon.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(Materials.Radon.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(Materials.Radon.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(19),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(Materials.Helium_3.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(19),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(Materials.Helium_3.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(20),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Deuterium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(20),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Deuterium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(21),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Tritium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(21),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Tritium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(22),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.HeavyFuel.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(22),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.HeavyFuel.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(23),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.LightFuel.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(23),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.LightFuel.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(24),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Naphtha.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(24),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Naphtha.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(1),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Gas.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(1),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Gas.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(2),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(new FluidStack(CoalTar.Coal_Gas, 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(2),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(new FluidStack(CoalTar.Coal_Gas, 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(3),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(new FluidStack(MaterialsElements.getInstance().BROMINE.getFluid(), 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(3),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(new FluidStack(MaterialsElements.getInstance().BROMINE.getFluid(), 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(4),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Methane.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(4),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Methane.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(5),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Oil.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(5),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Oil.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(6),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.OilHeavy.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(6),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.OilHeavy.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(7),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Lava.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(7),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Lava.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(8),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.SaltWater.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(8),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.SaltWater.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(FluidRegistry.getFluidStack("ic2distilledwater", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(FluidRegistry.getFluidStack("ic2distilledwater", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(10),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(FluidRegistry.getFluidStack("pyrotheum", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(10),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(FluidRegistry.getFluidStack("pyrotheum", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(11),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(FluidRegistry.getFluidStack("cryotheum", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(11),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(FluidRegistry.getFluidStack("cryotheum", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(12),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(FluidRegistry.getFluidStack("liquiddna", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(12),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(FluidRegistry.getFluidStack("liquiddna", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(13),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(13),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(14),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(14),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(15),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.SulfuricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(15),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.SulfuricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(16),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.NitricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(16),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.NitricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(17),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.HydrofluoricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(17),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.HydrofluoricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(18),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.PhosphoricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(18),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.PhosphoricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(19),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.PhthalicAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(75)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(19),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UIV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.PhthalicAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 4)
            .duration(37)
            .eut(TierEU.RECIPE_UIV)
            .addTo(SDR);

        // UMV Tier
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Hydrogen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Hydrogen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(2),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Helium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(2),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Helium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(3),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Nitrogen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(3),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Nitrogen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(4),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Methane.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(4),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Methane.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.SulfurDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.SulfurDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(6),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.CarbonDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(6),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.CarbonDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(7),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.NitrogenDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(7),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.NitrogenDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(8),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Ammonia.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(8),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Ammonia.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Chlorine.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Chlorine.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(10),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Chlorine.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(10),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Fluorine.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(11),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(11),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(12),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Oxygen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(12),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Oxygen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(13),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(FluidRegistry.getFluidStack("unknowwater", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(13),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(FluidRegistry.getFluidStack("unknowwater", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(14),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(WerkstoffLoader.Neon.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(14),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(WerkstoffLoader.Neon.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(15),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(Materials.Argon.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(15),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(Materials.Argon.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(16),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(WerkstoffLoader.Krypton.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(16),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(WerkstoffLoader.Krypton.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(17),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(WerkstoffLoader.Xenon.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(17),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(WerkstoffLoader.Xenon.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(Materials.Radon.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(Materials.Radon.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(19),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(Materials.Helium_3.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(19),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(Materials.Helium_3.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(20),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Deuterium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(20),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Deuterium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(21),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Tritium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(21),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Tritium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(22),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.HeavyFuel.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(22),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.HeavyFuel.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(23),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.LightFuel.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(23),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.LightFuel.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(24),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Naphtha.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(24),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Naphtha.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(1),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Gas.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(1),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Gas.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(2),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(new FluidStack(CoalTar.Coal_Gas, 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(2),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(new FluidStack(CoalTar.Coal_Gas, 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(3),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(new FluidStack(MaterialsElements.getInstance().BROMINE.getFluid(), 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(3),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(new FluidStack(MaterialsElements.getInstance().BROMINE.getFluid(), 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(4),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Methane.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(4),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Methane.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(5),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Oil.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(5),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Oil.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(6),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.OilHeavy.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(6),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.OilHeavy.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(7),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Lava.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(7),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Lava.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(8),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.SaltWater.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(8),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.SaltWater.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(FluidRegistry.getFluidStack("ic2distilledwater", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(FluidRegistry.getFluidStack("ic2distilledwater", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(10),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(FluidRegistry.getFluidStack("pyrotheum", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(10),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(FluidRegistry.getFluidStack("pyrotheum", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(11),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(FluidRegistry.getFluidStack("cryotheum", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(11),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(FluidRegistry.getFluidStack("cryotheum", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(12),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(FluidRegistry.getFluidStack("liquiddna", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(12),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(FluidRegistry.getFluidStack("liquiddna", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(13),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(13),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(14),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(14),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(15),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.SulfuricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(15),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.SulfuricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(16),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.NitricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(16),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.NitricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(17),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.HydrofluoricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(17),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.HydrofluoricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(18),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.PhosphoricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(18),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.PhosphoricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(19),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.PhthalicAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(37)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(19),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UMV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.PhthalicAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 5)
            .duration(18)
            .eut(TierEU.RECIPE_UMV)
            .addTo(SDR);

        // UXV Tier
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Hydrogen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Hydrogen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(2),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Helium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(2),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Helium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(3),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Nitrogen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(3),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Nitrogen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(4),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Methane.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(4),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Methane.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.SulfurDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.SulfurDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(6),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.CarbonDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(6),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.CarbonDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(7),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.NitrogenDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(7),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.NitrogenDioxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(8),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Ammonia.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(8),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Ammonia.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Chlorine.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Chlorine.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(10),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Chlorine.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(10),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Fluorine.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(11),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(11),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.CarbonMonoxide.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(12),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .fluidOutputs(Materials.Oxygen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(12),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .fluidOutputs(Materials.Oxygen.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(13),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(FluidRegistry.getFluidStack("unknowwater", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(13),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(FluidRegistry.getFluidStack("unknowwater", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(14),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(WerkstoffLoader.Neon.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(14),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(WerkstoffLoader.Neon.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(15),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(Materials.Argon.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(15),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(Materials.Argon.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(16),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(WerkstoffLoader.Krypton.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(16),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(WerkstoffLoader.Krypton.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(17),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(WerkstoffLoader.Xenon.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(17),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(WerkstoffLoader.Xenon.getFluidOrGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(Materials.Radon.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(Materials.Radon.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(19),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Dense_Hydrazine_Mix, 10000))
            .fluidOutputs(Materials.Helium_3.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(19),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.Monomethylhydrazine_Plus_Nitric_Acid, 6000))
            .fluidOutputs(Materials.Helium_3.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(20),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Deuterium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(20),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Deuterium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(21),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Tritium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(21),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Tritium.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(22),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.HeavyFuel.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(22),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.HeavyFuel.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(23),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.LightFuel.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(23),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.LightFuel.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(24),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Naphtha.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(24),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Naphtha.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(1),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Gas.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(1),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Gas.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(2),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(new FluidStack(CoalTar.Coal_Gas, 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(2),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(new FluidStack(CoalTar.Coal_Gas, 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(3),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(new FluidStack(MaterialsElements.getInstance().BROMINE.getFluid(), 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(3),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(new FluidStack(MaterialsElements.getInstance().BROMINE.getFluid(), 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(4),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Methane.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(4),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Methane.getGas(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(5),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Oil.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(5),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Oil.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(6),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.OilHeavy.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(6),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.OilHeavy.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(7),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.Lava.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(7),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.Lava.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(8),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.SaltWater.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(8),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.SaltWater.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(FluidRegistry.getFluidStack("ic2distilledwater", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(FluidRegistry.getFluidStack("ic2distilledwater", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(10),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(FluidRegistry.getFluidStack("pyrotheum", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(10),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(FluidRegistry.getFluidStack("pyrotheum", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(11),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(FluidRegistry.getFluidStack("cryotheum", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(11),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(FluidRegistry.getFluidStack("cryotheum", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(12),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(FluidRegistry.getFluidStack("liquiddna", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(12),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(FluidRegistry.getFluidStack("liquiddna", 10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(13),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(13),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(14),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(14),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.HydrochloricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(15),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.SulfuricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(15),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.SulfuricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(16),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.NitricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(16),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.NitricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(17),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.HydrofluoricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(17),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.HydrofluoricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.PhosphoricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.PhosphoricAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(19),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(
                FluidUtils.getFluidStack(RocketFuels.Unsymmetrical_Dimethylhydrazine_Plus_Nitrogen_Tetroxide, 10000))
            .fluidOutputs(Materials.PhthalicAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(18)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(19),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.thoriumBasedLiquidFuel.getFluidOrGas(6000))
            .fluidOutputs(Materials.PhthalicAcid.getFluid(10000000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(9)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(20),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.naquadahBasedFuelMkV.getFluidOrGas(10000))
            .fluidOutputs(MaterialsUEVplus.WhiteDwarfMatter.getMolten(100000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(750)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                CI.getNumberedAdvancedCircuit(21),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UXV.ordinal())))
            .fluidInputs(GGMaterial.naquadahBasedFuelMkV.getFluidOrGas(10000))
            .fluidOutputs(MaterialsUEVplus.BlackDwarfMatter.getMolten(100000))
            .noOptimize()
            .specialValue(1)
            .metadata(MINER_TIER, 6)
            .duration(750)
            .eut(TierEU.RECIPE_UXV)
            .addTo(SDR);
    }
}
