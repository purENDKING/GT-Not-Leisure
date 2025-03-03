package com.science.gtnl.common.recipe.GTNL;

import static gregtech.api.enums.Mods.AppliedEnergistics2;

import net.minecraft.item.ItemStack;

import com.gtnewhorizons.gtnhintergalactic.item.IGItems;
import com.gtnewhorizons.gtnhintergalactic.item.ItemMiningDrones;
import com.science.gtnl.common.recipe.IRecipePool;
import com.science.gtnl.common.recipe.RecipeRegister;
import com.science.gtnl.common.recipe.Special.ResourceCollectionModuleTierKey;

import bartworks.system.material.WerkstoffLoader;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gtPlusPlus.core.item.chemistry.RocketFuels;
import gtPlusPlus.core.material.MaterialsOres;
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

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(23),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Scheelite, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Tungstate, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Lithium, 1)),
                GTUtility.copyAmountUnsafe(20, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Tellurium, 1)),
                GTUtility.copyAmountUnsafe(30, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Tungsten, 1)),
                GTUtility.copyAmountUnsafe(180, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Pitchblende, 1)))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(23),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Scheelite, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Tungstate, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Lithium, 1)),
                GTUtility.copyAmountUnsafe(20, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Tellurium, 1)),
                GTUtility.copyAmountUnsafe(30, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Tungsten, 1)),
                GTUtility.copyAmountUnsafe(180, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Pitchblende, 1)))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(22),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Garnierite, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Nickel, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Cobaltite, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Pentlandite, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Platinum, 1)),
                GTUtility.copyAmountUnsafe(20, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Palladium, 1)))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(22),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Garnierite, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Nickel, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Cobaltite, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Pentlandite, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Platinum, 1)),
                GTUtility.copyAmountUnsafe(20, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Palladium, 1)))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(21),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(60, WerkstoffLoader.Bornite.get(OrePrefixes.ore, 1)),
                GTUtility.copyAmountUnsafe(40, WerkstoffLoader.PTMetallicPowder.get(OrePrefixes.ore, 1)),
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Graphite, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Diamond, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Coal, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Titanium, 1)))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(21),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(60, WerkstoffLoader.Bornite.get(OrePrefixes.ore, 1)),
                GTUtility.copyAmountUnsafe(40, WerkstoffLoader.PTMetallicPowder.get(OrePrefixes.ore, 1)),
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Graphite, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Diamond, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Coal, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Titanium, 1)))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(20),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(240, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Oilsands, 1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Gold, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.InfusedGold, 1)),
                GTUtility.copyAmountUnsafe(160, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Bauxite, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Ilmenite, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Aluminium, 1)))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(20),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(240, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Oilsands, 1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Gold, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.InfusedGold, 1)),
                GTUtility.copyAmountUnsafe(160, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Bauxite, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Ilmenite, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Aluminium, 1)))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(19),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(180, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Magnetite, 1)),
                GTUtility
                    .copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.VanadiumMagnetite, 1)),
                GTUtility.copyAmountUnsafe(240, GTOreDictUnificator.get(OrePrefixes.ore, Materials.CassiteriteSand, 1)),
                GTUtility.copyAmountUnsafe(160, GTOreDictUnificator.get(OrePrefixes.ore, Materials.GarnetSand, 1)),
                GTUtility.copyAmountUnsafe(160, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Asbestos, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Diatomite, 1)))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(19),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(180, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Magnetite, 1)),
                GTUtility
                    .copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.VanadiumMagnetite, 1)),
                GTUtility.copyAmountUnsafe(240, GTOreDictUnificator.get(OrePrefixes.ore, Materials.CassiteriteSand, 1)),
                GTUtility.copyAmountUnsafe(160, GTOreDictUnificator.get(OrePrefixes.ore, Materials.GarnetRed, 1)),
                GTUtility.copyAmountUnsafe(160, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Asbestos, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Diatomite, 1)))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Apatite, 1)),
                GTUtility
                    .copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.TricalciumPhosphate, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Pyrochlore, 1)),
                GTUtility.copyAmountUnsafe(300, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Sulfur, 1)),
                GTUtility.copyAmountUnsafe(200, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Pyrite, 1)),
                GTUtility.copyAmountUnsafe(100, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Sphalerite, 1)))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(18),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Apatite, 1)),
                GTUtility
                    .copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.TricalciumPhosphate, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Pyrochlore, 1)),
                GTUtility.copyAmountUnsafe(300, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Sulfur, 1)),
                GTUtility.copyAmountUnsafe(200, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Pyrite, 1)),
                GTUtility.copyAmountUnsafe(100, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Sphalerite, 1)))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(17),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(180, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Redstone, 1)),
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Ruby, 1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Cinnabar, 1)),
                GTUtility.copyAmountUnsafe(240, GTOreDictUnificator.get(OrePrefixes.ore, Materials.NetherQuartz, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Quartzite, 1)),
                GTUtility.copyAmountUnsafe(50, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Lanthanum, 1)))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(17),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(180, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Redstone, 1)),
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Ruby, 1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Cinnabar, 1)),
                GTUtility.copyAmountUnsafe(240, GTOreDictUnificator.get(OrePrefixes.ore, Materials.NetherQuartz, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Quartzite, 1)),
                GTUtility.copyAmountUnsafe(50, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Lanthanum, 1)))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(16),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(150, GTOreDictUnificator.get(OrePrefixes.ore, Materials.RockSalt, 1)),
                GTUtility.copyAmountUnsafe(10, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Salt, 1)),
                GTUtility.copyAmountUnsafe(50, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Lepidolite, 1)),
                GTUtility.copyAmountUnsafe(50, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Spodumene, 1)),
                GTUtility.copyAmountUnsafe(140, WerkstoffLoader.Djurleit.get(OrePrefixes.ore, 1)),
                GTUtility.copyAmountUnsafe(70, WerkstoffLoader.Bornite.get(OrePrefixes.ore, 1)))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(16),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(150, GTOreDictUnificator.get(OrePrefixes.ore, Materials.RockSalt, 1)),
                GTUtility.copyAmountUnsafe(10, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Salt, 1)),
                GTUtility.copyAmountUnsafe(50, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Lepidolite, 1)),
                GTUtility.copyAmountUnsafe(50, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Spodumene, 1)),
                GTUtility.copyAmountUnsafe(140, WerkstoffLoader.Djurleit.get(OrePrefixes.ore, 1)),
                GTUtility.copyAmountUnsafe(70, WerkstoffLoader.Bornite.get(OrePrefixes.ore, 1)))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(15),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(210, GTOreDictUnificator.get(OrePrefixes.ore, Materials.BlueTopaz, 1)),
                GTUtility.copyAmountUnsafe(140, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Topaz, 1)),
                GTUtility
                    .copyAmountUnsafe(240, GTOreDictUnificator.get(OrePrefixes.ore, Materials.BasalticMineralSand, 1)),
                GTUtility
                    .copyAmountUnsafe(160, GTOreDictUnificator.get(OrePrefixes.ore, Materials.GraniticMineralSand, 1)),
                GTUtility.copyAmountUnsafe(160, GTOreDictUnificator.get(OrePrefixes.ore, Materials.FullersEarth, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Gypsum, 1)))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(15),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(210, GTOreDictUnificator.get(OrePrefixes.ore, Materials.BlueTopaz, 1)),
                GTUtility.copyAmountUnsafe(140, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Topaz, 1)),
                GTUtility
                    .copyAmountUnsafe(240, GTOreDictUnificator.get(OrePrefixes.ore, Materials.BasalticMineralSand, 1)),
                GTUtility
                    .copyAmountUnsafe(160, GTOreDictUnificator.get(OrePrefixes.ore, Materials.GraniticMineralSand, 1)),
                GTUtility.copyAmountUnsafe(160, GTOreDictUnificator.get(OrePrefixes.ore, Materials.FullersEarth, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Gypsum, 1)))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(14),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Barite, 1)),
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.GarnetRed, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.GarnetYellow, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Amethyst, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.GreenSapphire, 1)),
                GTUtility.copyAmountUnsafe(20, WerkstoffLoader.Roquesit.get(OrePrefixes.ore, 1)))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(14),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Barite, 1)),
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.GarnetRed, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.GarnetYellow, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Amethyst, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.GreenSapphire, 1)),
                GTUtility.copyAmountUnsafe(20, WerkstoffLoader.Roquesit.get(OrePrefixes.ore, 1)))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(13),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Desh, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.CertusQuartz, 1)),
                GTUtility.copyAmountUnsafe(90, GTOreDictUnificator.get(OrePrefixes.ore, Materials.BrownLimonite, 1)),
                GTUtility.copyAmountUnsafe(160, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Cassiterite, 1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.BandedIron, 1)),
                GTUtility.copyAmountUnsafe(30, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Gold, 1)))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(13),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Desh, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.CertusQuartz, 1)),
                GTUtility.copyAmountUnsafe(90, GTOreDictUnificator.get(OrePrefixes.ore, Materials.BrownLimonite, 1)),
                GTUtility.copyAmountUnsafe(160, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Cassiterite, 1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.BandedIron, 1)),
                GTUtility.copyAmountUnsafe(30, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Gold, 1)))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(12),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(140, MaterialsOres.ZIRCON.getOre(1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.YellowLimonite, 1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Kyanite, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Mica, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Bauxite, 1)),
                GTUtility.copyAmountUnsafe(20, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Almandine, 1)))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(12),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(140, MaterialsOres.ZIRCON.getOre(1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.YellowLimonite, 1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Kyanite, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Mica, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Bauxite, 1)),
                GTUtility.copyAmountUnsafe(20, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Almandine, 1)))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(11),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Galena, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Silver, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Lead, 1)),
                GTUtility.copyAmountUnsafe(100, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Molybdenite, 1)),
                GTUtility.copyAmountUnsafe(50, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Molybdenum, 1)),
                GTUtility.copyAmountUnsafe(50, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Powellite, 1)))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(11),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Galena, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Silver, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Lead, 1)),
                GTUtility.copyAmountUnsafe(100, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Molybdenite, 1)),
                GTUtility.copyAmountUnsafe(50, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Molybdenum, 1)),
                GTUtility.copyAmountUnsafe(50, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Powellite, 1)))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(10),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Lazurite, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Sodalite, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Lapis, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Calcite, 1)),
                GTUtility.copyAmountUnsafe(150, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Wulfenite, 1)),
                GTUtility.copyAmountUnsafe(30, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Quantium, 1)))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(10),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Lazurite, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Sodalite, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Lapis, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Calcite, 1)),
                GTUtility.copyAmountUnsafe(150, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Wulfenite, 1)),
                GTUtility.copyAmountUnsafe(30, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Quantium, 1)))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Grossular, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Pyrolusite, 1)),
                GTUtility.copyAmountUnsafe(20, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Tantalite, 1)),
                GTUtility.copyAmountUnsafe(240, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Magnetite, 1)),
                GTUtility
                    .copyAmountUnsafe(160, GTOreDictUnificator.get(OrePrefixes.ore, Materials.VanadiumMagnetite, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Gold, 1)))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(9),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Grossular, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Pyrolusite, 1)),
                GTUtility.copyAmountUnsafe(20, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Tantalite, 1)),
                GTUtility.copyAmountUnsafe(240, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Magnetite, 1)),
                GTUtility
                    .copyAmountUnsafe(160, GTOreDictUnificator.get(OrePrefixes.ore, Materials.VanadiumMagnetite, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Gold, 1)))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(8),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(90, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Beryllium, 1)),
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Emerald, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Chalcopyrite, 1)),
                GTUtility.copyAmountUnsafe(160, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Iron, 1)),
                GTUtility.copyAmountUnsafe(160, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Pyrite, 1)),
                GTUtility.copyAmountUnsafe(160, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Copper, 1)))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(8),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(90, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Beryllium, 1)),
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Emerald, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Chalcopyrite, 1)),
                GTUtility.copyAmountUnsafe(160, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Iron, 1)),
                GTUtility.copyAmountUnsafe(160, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Pyrite, 1)),
                GTUtility.copyAmountUnsafe(160, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Copper, 1)))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(7),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Saltpeter, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Diatomite, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Electrotine, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Alunite, 1)),
                GTUtility.copyAmountUnsafe(240, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Coal, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Rubidium, 1)))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(7),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Saltpeter, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Diatomite, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Electrotine, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Alunite, 1)),
                GTUtility.copyAmountUnsafe(240, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Coal, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Rubidium, 1)))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(6),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(250, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Chalcopyrite, 1)),
                GTUtility.copyAmountUnsafe(10, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Zeolite, 1)),
                GTUtility.copyAmountUnsafe(10, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Cassiterite, 1)),
                GTUtility.copyAmountUnsafe(50, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Realgar, 1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Cinnabar, 1)),
                GTUtility
                    .copyAmountUnsafe(80, GTModHandler.getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1)))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(6),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(250, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Chalcopyrite, 1)),
                GTUtility.copyAmountUnsafe(10, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Zeolite, 1)),
                GTUtility.copyAmountUnsafe(10, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Cassiterite, 1)),
                GTUtility.copyAmountUnsafe(50, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Realgar, 1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Cinnabar, 1)),
                GTUtility
                    .copyAmountUnsafe(80, GTModHandler.getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1)))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(180, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Redstone, 1)),
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Ruby, 1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Grossular, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Spessartine, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Draconium, 1)),
                GTUtility.copyAmountUnsafe(20, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Tantalite, 1)))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(180, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Redstone, 1)),
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Ruby, 1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Grossular, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Spessartine, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Draconium, 1)),
                GTUtility.copyAmountUnsafe(20, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Tantalite, 1)))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(4),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Soapstone, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Talc, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Glauconite, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Pentlandite, 1)),
                GTUtility.copyAmountUnsafe(30, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Neodymium, 1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Monazite, 1)))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(4),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Soapstone, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Talc, 1)),
                GTUtility.copyAmountUnsafe(80, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Glauconite, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Pentlandite, 1)),
                GTUtility.copyAmountUnsafe(30, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Neodymium, 1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Monazite, 1)))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(3),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(90, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Bastnasite, 1)),
                GTUtility.copyAmountUnsafe(30, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Molybdenum, 1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.BrownLimonite, 1)),
                GTUtility.copyAmountUnsafe(240, GTOreDictUnificator.get(OrePrefixes.ore, Materials.YellowLimonite, 1)),
                GTUtility.copyAmountUnsafe(240, GTOreDictUnificator.get(OrePrefixes.ore, Materials.BandedIron, 1)),
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Malachite, 1)))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(3),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(90, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Bastnasite, 1)),
                GTUtility.copyAmountUnsafe(30, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Molybdenum, 1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.BrownLimonite, 1)),
                GTUtility.copyAmountUnsafe(240, GTOreDictUnificator.get(OrePrefixes.ore, Materials.YellowLimonite, 1)),
                GTUtility.copyAmountUnsafe(240, GTOreDictUnificator.get(OrePrefixes.ore, Materials.BandedIron, 1)),
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Malachite, 1)))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(2),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(180, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Almandine, 1)),
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Pyrope, 1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Sapphire, 1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.GreenSapphire, 1)),
                GTUtility.copyAmountUnsafe(70, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Stibnite, 1)),
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Uraninite, 1)))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(2),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(180, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Almandine, 1)),
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Pyrope, 1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Sapphire, 1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.GreenSapphire, 1)),
                GTUtility.copyAmountUnsafe(70, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Stibnite, 1)),
                GTUtility.copyAmountUnsafe(120, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Uraninite, 1)))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(280, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Tetrahedrite, 1)),
                GTUtility.copyAmountUnsafe(140, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Copper, 1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Bentonite, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Magnetite, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Olivine, 1)),
                GTUtility.copyAmountUnsafe(20, GTOreDictUnificator.get(OrePrefixes.ore, Materials.GlauconiteSand, 1)))
            .fluidInputs(Materials.GasolinePremium.getFluid(10000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTUtility.copyAmountUnsafe(
                    0,
                    new ItemStack(IGItems.MiningDrones, 1, ItemMiningDrones.DroneTiers.UV.ordinal())))
            .itemOutputs(
                GTUtility.copyAmountUnsafe(280, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Tetrahedrite, 1)),
                GTUtility.copyAmountUnsafe(140, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Copper, 1)),
                GTUtility.copyAmountUnsafe(60, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Bentonite, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Magnetite, 1)),
                GTUtility.copyAmountUnsafe(40, GTOreDictUnificator.get(OrePrefixes.ore, Materials.Olivine, 1)),
                GTUtility.copyAmountUnsafe(20, GTOreDictUnificator.get(OrePrefixes.ore, Materials.GlauconiteSand, 1)))
            .fluidInputs(FluidUtils.getFluidStack(RocketFuels.RP1_Plus_Liquid_Oxygen, 6000))
            .noOptimize()
            .metadata(MINER_TIER, 1)
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .addTo(SMR);

    }
}
