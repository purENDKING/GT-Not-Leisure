package com.science.gtnl.common.recipe.GTNL;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

import net.minecraftforge.fluids.FluidRegistry;

import com.dreammaster.bartworksHandler.BacteriaRegistry;
import com.science.gtnl.Utils.recipes.RecipeBuilder;
import com.science.gtnl.common.materials.MaterialPool;
import com.science.gtnl.common.recipe.IRecipePool;
import com.science.gtnl.common.recipe.RecipeRegister;

import bartworks.common.loaders.BioItemList;
import bartworks.util.BioCulture;
import gregtech.api.enums.Materials;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTUtility;

public class PetrochemicalPlantRecipes implements IRecipePool {

    final RecipeMap<?> PPR = RecipeRegister.PetrochemicalPlantRecipes;

    @Override
    public void loadRecipes() {

        RecipeBuilder.builder()
            .itemInputs(GTUtility.getIntegratedCircuit(2))
            .fluidInputs(
                FluidRegistry.getFluidStack("liquid_heavy_oil", 1000),
                FluidRegistry.getFluidStack("steam", 1000))
            .fluidOutputs(
                FluidRegistry.getFluidStack("ethylene", 450),
                FluidRegistry.getFluidStack("methane", 450),
                FluidRegistry.getFluidStack("helium", 10),
                FluidRegistry.getFluidStack("propane", 30),
                FluidRegistry.getFluidStack("propene", 300),
                FluidRegistry.getFluidStack("ethane", 45),
                FluidRegistry.getFluidStack("butane", 60),
                FluidRegistry.getFluidStack("butene", 240),
                FluidRegistry.getFluidStack("butadiene", 150),
                FluidRegistry.getFluidStack("liquid_toluene", 240),
                FluidRegistry.getFluidStack("benzene", 1200),
                FluidRegistry.getFluidStack("octane", 20))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(1920)
            .addTo(PPR);

        RecipeBuilder.builder()
            .itemInputs(GTUtility.getIntegratedCircuit(2))
            .fluidInputs(
                FluidRegistry.getFluidStack("liquid_light_oil", 1000),
                FluidRegistry.getFluidStack("steam", 1000))
            .fluidOutputs(
                FluidRegistry.getFluidStack("ethylene", 250),
                FluidRegistry.getFluidStack("methane", 2000),
                FluidRegistry.getFluidStack("helium", 40),
                FluidRegistry.getFluidStack("propane", 140),
                FluidRegistry.getFluidStack("propene", 90),
                FluidRegistry.getFluidStack("ethane", 200),
                FluidRegistry.getFluidStack("butane", 120),
                FluidRegistry.getFluidStack("butene", 80),
                FluidRegistry.getFluidStack("butadiene", 80),
                FluidRegistry.getFluidStack("liquid_toluene", 20),
                FluidRegistry.getFluidStack("benzene", 100),
                FluidRegistry.getFluidStack("octane", 20))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(1920)
            .addTo(PPR);

        RecipeBuilder.builder()
            .itemInputs(GTUtility.getIntegratedCircuit(2))
            .fluidInputs(FluidRegistry.getFluidStack("oil", 1000), FluidRegistry.getFluidStack("steam", 1000))
            .fluidOutputs(
                FluidRegistry.getFluidStack("ethylene", 400),
                FluidRegistry.getFluidStack("methane", 400),
                FluidRegistry.getFluidStack("helium", 20),
                FluidRegistry.getFluidStack("propane", 80),
                FluidRegistry.getFluidStack("propene", 400),
                FluidRegistry.getFluidStack("ethane", 80),
                FluidRegistry.getFluidStack("butane", 80),
                FluidRegistry.getFluidStack("butene", 100),
                FluidRegistry.getFluidStack("butadiene", 90),
                FluidRegistry.getFluidStack("liquid_toluene", 60),
                FluidRegistry.getFluidStack("benzene", 180),
                FluidRegistry.getFluidStack("octane", 60))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(1920)
            .addTo(PPR);

        RecipeBuilder.builder()
            .itemInputs(GTUtility.getIntegratedCircuit(2))
            .fluidInputs(
                FluidRegistry.getFluidStack("liquid_medium_oil", 1000),
                FluidRegistry.getFluidStack("steam", 1000))
            .fluidOutputs(
                FluidRegistry.getFluidStack("ethylene", 1000),
                FluidRegistry.getFluidStack("methane", 1000),
                FluidRegistry.getFluidStack("helium", 10),
                FluidRegistry.getFluidStack("propane", 30),
                FluidRegistry.getFluidStack("propene", 600),
                FluidRegistry.getFluidStack("ethane", 130),
                FluidRegistry.getFluidStack("butane", 70),
                FluidRegistry.getFluidStack("butene", 100),
                FluidRegistry.getFluidStack("butadiene", 100),
                FluidRegistry.getFluidStack("liquid_toluene", 40),
                FluidRegistry.getFluidStack("benzene", 200),
                FluidRegistry.getFluidStack("octane", 30))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(1920)
            .addTo(PPR);

        RecipeBuilder.builder()
            .fluidInputs(FluidRegistry.getFluidStack("woodtar", 1000), FluidRegistry.getFluidStack("steam", 1000))
            .fluidOutputs(
                FluidRegistry.getFluidStack("creosote", 300),
                FluidRegistry.getFluidStack("phenol", 200),
                FluidRegistry.getFluidStack("benzene", 500),
                FluidRegistry.getFluidStack("liquid_toluene", 100),
                FluidRegistry.getFluidStack("dimethylbenzene", 300),
                FluidRegistry.getFluidStack("1,3dimethylbenzene", 300),
                FluidRegistry.getFluidStack("1,4dimethylbenzene", 300))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(1920)
            .addTo(PPR);

        RecipeBuilder.builder()
            .itemInputs(
                GTUtility.copyAmount(0, BioItemList.getPetriDish(getCulture("CombinedBac"))),
                GTModHandler.getModItem("gregtech", "gt.metaitem.01", 16, 2618),
                GTModHandler.getModItem("gregtech", "gt.metaitem.01", 16, 2083))
            .fluidInputs(
                MaterialPool.BarnardaCSappy.getFluidOrGas(1000),
                FluidRegistry.getFluidStack("oil", 2000),
                Materials.Silver.getPlasma(10))
            .fluidOutputs(
                FluidRegistry.getFluidStack("liquid_heavy_oil", 100),
                FluidRegistry.getFluidStack("oil", 5),
                FluidRegistry.getFluidStack("creosote", 200),
                FluidRegistry.getFluidStack("water", 500),
                FluidRegistry.getFluidStack("fermentedbacterialsludge", 10),
                FluidRegistry.getFluidStack("fermentedbiomass", 200),
                FluidRegistry.getFluidStack("superheavyradox", 20),
                FluidRegistry.getFluidStack("heavyradox", 30),
                FluidRegistry.getFluidStack("dilutedxenoxene", 1),
                FluidRegistry.getFluidStack("lightradox", 80),
                Materials.RadoxGas.getGas(20))
            .specialValue(0)
            .noOptimize()
            .duration(2000)
            .eut(1966080)
            .addTo(PPR);

        RecipeBuilder.builder()
            .fluidInputs(FluidRegistry.getFluidStack("fluid.coaltar", 1000), FluidRegistry.getFluidStack("steam", 1000))
            .fluidOutputs(
                FluidRegistry.getFluidStack("fluid.coaltaroil", 300),
                FluidRegistry.getFluidStack("liquid_naphtha", 150),
                FluidRegistry.getFluidStack("fluid.ethylbenzene", 200),
                FluidRegistry.getFluidStack("fluid.anthracene", 50),
                FluidRegistry.getFluidStack("fluid.kerosene", 600),
                FluidRegistry.getFluidStack("fluid.naphthalene", 300))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(1920)
            .addTo(PPR);

        RecipeBuilder.builder()
            .itemInputs(GTUtility.getIntegratedCircuit(1))
            .fluidInputs(FluidRegistry.getFluidStack("liquid_heavy_oil", 2000))
            .fluidOutputs(
                FluidRegistry.getFluidStack("liquid_naphtha", 150),
                FluidRegistry.getFluidStack("naphthenicacid", 50),
                FluidRegistry.getFluidStack("gas_gas", 600),
                FluidRegistry.getFluidStack("liquid_heavy_fuel", 1000),
                FluidRegistry.getFluidStack("liquid_light_fuel", 450),
                FluidRegistry.getFluidStack("lubricant", 750))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(1920)
            .addTo(PPR);

        RecipeBuilder.builder()
            .itemInputs(GTUtility.getIntegratedCircuit(1))
            .fluidInputs(FluidRegistry.getFluidStack("liquid_light_oil", 2000))
            .fluidOutputs(
                FluidRegistry.getFluidStack("liquid_naphtha", 200),
                FluidRegistry.getFluidStack("naphthenicacid", 15),
                FluidRegistry.getFluidStack("gas_gas", 1600),
                FluidRegistry.getFluidStack("liquid_heavy_fuel", 70),
                FluidRegistry.getFluidStack("liquid_light_fuel", 130),
                FluidRegistry.getFluidStack("lubricant", 250))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(1920)
            .addTo(PPR);

        RecipeBuilder.builder()
            .itemInputs(GTUtility.getIntegratedCircuit(1))
            .fluidInputs(FluidRegistry.getFluidStack("oil", 1000))
            .fluidOutputs(
                FluidRegistry.getFluidStack("liquid_naphtha", 400),
                FluidRegistry.getFluidStack("naphthenicacid", 50),
                FluidRegistry.getFluidStack("gas_gas", 1200),
                FluidRegistry.getFluidStack("liquid_heavy_fuel", 300),
                FluidRegistry.getFluidStack("liquid_light_fuel", 1000))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(1920)
            .addTo(PPR);

        RecipeBuilder.builder()
            .itemInputs(GTUtility.getIntegratedCircuit(1))
            .fluidInputs(FluidRegistry.getFluidStack("liquid_medium_oil", 2000))
            .fluidOutputs(
                FluidRegistry.getFluidStack("liquid_naphtha", 1500),
                FluidRegistry.getFluidStack("naphthenicacid", 25),
                FluidRegistry.getFluidStack("gas_gas", 600),
                FluidRegistry.getFluidStack("liquid_heavy_fuel", 100),
                FluidRegistry.getFluidStack("liquid_light_fuel", 500),
                FluidRegistry.getFluidStack("lubricant", 500))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(1920)
            .addTo(PPR);

        RecipeBuilder.builder()
            .itemInputs(GTUtility.getIntegratedCircuit(1))
            .fluidInputs(FluidRegistry.getFluidStack("liquid_extra_heavy_oil", 2000))
            .fluidOutputs(
                FluidRegistry.getFluidStack("liquid_naphtha", 225),
                FluidRegistry.getFluidStack("naphthenicacid", 75),
                FluidRegistry.getFluidStack("gas_gas", 900),
                FluidRegistry.getFluidStack("liquid_heavy_fuel", 1500),
                FluidRegistry.getFluidStack("liquid_light_fuel", 675),
                FluidRegistry.getFluidStack("lubricant", 1125))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(1920)
            .addTo(PPR);
    }

    public static BioCulture getCulture(String key) {
        try {
            Field field = BacteriaRegistry.class.getDeclaredField("CultureSet");
            field.setAccessible(true); // 绕过访问控制
            @SuppressWarnings("unchecked")
            LinkedHashMap<String, BioCulture> map = (LinkedHashMap<String, BioCulture>) field.get(null);
            return map.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
