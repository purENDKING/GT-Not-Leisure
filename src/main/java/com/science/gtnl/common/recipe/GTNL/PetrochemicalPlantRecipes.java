package com.science.gtnl.common.recipe.GTNL;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;

import com.science.gtnl.Utils.recipes.RecipeBuilder;
import com.science.gtnl.common.materials.MaterialPool;
import com.science.gtnl.common.recipe.IRecipePool;
import com.science.gtnl.common.recipe.RecipeRegister;

import gregtech.api.enums.Materials;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTUtility;

public class PetrochemicalPlantRecipes implements IRecipePool {

    final RecipeMap<?> PPR = RecipeRegister.PetrochemicalPlantRecipes;

    @Override
    public void loadRecipes() {

        ItemStack BioVatXenoxene = GTModHandler.getModItem("bartworks", "BioLabParts", 1);
        NBTTagCompound BioVatType = BioVatXenoxene.getTagCompound();
        if (BioVatType != null) {
            BioVatType.setByte("Breedable", (byte) 0);
            BioVatType.setByte("Rarety", (byte) 2);
            BioVatType.setString("Name", "Xenoxene Xenoxsis");
            BioVatType.setIntArray("Color", new int[] { 54, 119, 181, });

            NBTTagCompound PlasmidTag = new NBTTagCompound();
            PlasmidTag.setInteger("Chance", 750);
            PlasmidTag.setString("Name", "Barnadafis Arboriatoris");
            PlasmidTag.setByte("Rarity", (byte) 3);
            PlasmidTag.setInteger("Tier", 2);
            BioVatType.setTag("Plasmid", PlasmidTag);

            NBTTagCompound DNATag = new NBTTagCompound();
            DNATag.setInteger("Chance", 750);
            DNATag.setString("Name", "TCetiEis Fucus Serratus");
            DNATag.setByte("Rarity", (byte) 3);
            DNATag.setInteger("Tier", 2);
            BioVatType.setTag("DNA", DNATag);
        } else {
            BioVatType = new NBTTagCompound();
            BioVatType.setByte("Breedable", (byte) 0);
            BioVatType.setByte("Rarety", (byte) 2);
            BioVatType.setString("Name", "Xenoxene Xenoxsis");
            BioVatType.setIntArray("Color", new int[] { 54, 119, 181, });

            NBTTagCompound PlasmidTag = new NBTTagCompound();
            PlasmidTag.setInteger("Chance", 750);
            PlasmidTag.setString("Name", "Barnadafis Arboriatoris");
            PlasmidTag.setByte("Rarity", (byte) 3);
            PlasmidTag.setInteger("Tier", 2);
            BioVatType.setTag("Plasmid", PlasmidTag);

            NBTTagCompound DNATag = new NBTTagCompound();
            DNATag.setInteger("Chance", 750);
            DNATag.setString("Name", "TCetiEis Fucus Serratus");
            DNATag.setByte("Rarity", (byte) 3);
            DNATag.setInteger("Tier", 2);
            BioVatType.setTag("DNA", DNATag);
            BioVatXenoxene.setTagCompound(BioVatType);
        }

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
                GTUtility.copyAmount(0, BioVatXenoxene),
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
}
