package com.science.gtnl.common.recipe.GTNL;

import static gregtech.api.util.GTRecipeBuilder.MINUTES;

import com.science.gtnl.common.item.items.MilledOre;
import com.science.gtnl.common.recipe.IRecipePool;
import com.science.gtnl.common.recipe.RecipeRegister;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMap;
import gtPlusPlus.core.item.chemistry.GenericChem;
import gtPlusPlus.core.item.chemistry.MilledOreProcessing;
import gtPlusPlus.core.material.Material;
import gtPlusPlus.core.util.minecraft.FluidUtils;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import gtPlusPlus.core.util.minecraft.MaterialUtils;
import gtPlusPlus.xmod.gregtech.common.helpers.FlotationRecipeHandler;

public class CellRegulatorRecipes implements IRecipePool {

    final RecipeMap<?> CRR = RecipeRegister.CellRegulatorRecipes;

    @Override
    public void loadRecipes() {

        Material aMat = MaterialUtils.generateMaterialFromGtENUM(Materials.Nickel);
        FlotationRecipeHandler.registerOreType(aMat);
        GTValues.RA.stdBuilder()
            .itemInputs(ItemUtils.getSimpleStack(GenericChem.mPotassiumEthylXanthate, 32), aMat.getMilled(64))
            .fluidInputs(FluidUtils.getFluidStack(MilledOreProcessing.PineOil, 25000))
            .fluidOutputs(FluidUtils.getFluidStack(MilledOreProcessing.NickelFlotationFroth, 1000))
            .duration(4 * MINUTES)
            .eut(TierEU.RECIPE_IV)
            .addTo(CRR);

        aMat = MaterialUtils.generateMaterialFromGtENUM(Materials.Platinum);
        FlotationRecipeHandler.registerOreType(aMat);
        GTValues.RA.stdBuilder()
            .itemInputs(ItemUtils.getSimpleStack(GenericChem.mSodiumEthylXanthate, 32), aMat.getMilled(64))
            .fluidInputs(FluidUtils.getFluidStack(MilledOreProcessing.PineOil, 35000))
            .fluidOutputs(FluidUtils.getFluidStack(MilledOreProcessing.PlatinumFlotationFroth, 1000))
            .duration(4 * MINUTES)
            .eut(TierEU.RECIPE_LuV)
            .addTo(CRR);

        aMat = MaterialUtils.generateMaterialFromGtENUM(Materials.NaquadahEnriched);
        FlotationRecipeHandler.registerOreType(aMat);
        GTValues.RA.stdBuilder()
            .itemInputs(ItemUtils.getSimpleStack(GenericChem.mPotassiumEthylXanthate, 64), aMat.getMilled(64))
            .fluidInputs(FluidUtils.getFluidStack(MilledOreProcessing.PineOil, 140000))
            .fluidOutputs(FluidUtils.getFluidStack(MilledOre.NaquadahEnrichedFlotationFroth, 1000))
            .duration(8 * MINUTES)
            .eut(TierEU.RECIPE_LuV)
            .addTo(CRR);

        aMat = MaterialUtils.generateMaterialFromGtENUM(Materials.Almandine);
        FlotationRecipeHandler.registerOreType(aMat);
        GTValues.RA.stdBuilder()
            .itemInputs(ItemUtils.getSimpleStack(GenericChem.mSodiumEthylXanthate, 32), aMat.getMilled(64))
            .fluidInputs(FluidUtils.getFluidStack(MilledOreProcessing.PineOil, 18000))
            .fluidOutputs(FluidUtils.getFluidStack(MilledOreProcessing.AlmandineFlotationFroth, 1000))
            .duration(4 * MINUTES)
            .eut(TierEU.RECIPE_IV)
            .addTo(CRR);

        aMat = MaterialUtils.generateMaterialFromGtENUM(Materials.Chalcopyrite);
        FlotationRecipeHandler.registerOreType(aMat);
        GTValues.RA.stdBuilder()
            .itemInputs(ItemUtils.getSimpleStack(GenericChem.mPotassiumEthylXanthate, 32), aMat.getMilled(64))
            .fluidInputs(FluidUtils.getFluidStack(MilledOreProcessing.PineOil, 12000))
            .fluidOutputs(FluidUtils.getFluidStack(MilledOreProcessing.ChalcopyriteFlotationFroth, 1000))
            .duration(4 * MINUTES)
            .eut(TierEU.RECIPE_IV)
            .addTo(CRR);

        aMat = MaterialUtils.generateMaterialFromGtENUM(Materials.Grossular);
        FlotationRecipeHandler.registerOreType(aMat);
        GTValues.RA.stdBuilder()
            .itemInputs(ItemUtils.getSimpleStack(GenericChem.mPotassiumEthylXanthate, 32), aMat.getMilled(64))
            .fluidInputs(FluidUtils.getFluidStack(MilledOreProcessing.PineOil, 28000))
            .fluidOutputs(FluidUtils.getFluidStack(MilledOreProcessing.GrossularFlotationFroth, 1000))
            .duration(4 * MINUTES)
            .eut(TierEU.RECIPE_LuV)
            .addTo(CRR);

        aMat = MaterialUtils.generateMaterialFromGtENUM(Materials.Pyrope);
        FlotationRecipeHandler.registerOreType(aMat);
        GTValues.RA.stdBuilder()
            .itemInputs(ItemUtils.getSimpleStack(GenericChem.mSodiumEthylXanthate, 32), aMat.getMilled(64))
            .fluidInputs(FluidUtils.getFluidStack(MilledOreProcessing.PineOil, 8000))
            .fluidOutputs(FluidUtils.getFluidStack(MilledOreProcessing.PyropeFlotationFroth, 1000))
            .duration(4 * MINUTES)
            .eut(TierEU.RECIPE_IV)
            .addTo(CRR);

        aMat = MaterialUtils.generateMaterialFromGtENUM(Materials.Spessartine);
        FlotationRecipeHandler.registerOreType(aMat);
        GTValues.RA.stdBuilder()
            .itemInputs(ItemUtils.getSimpleStack(GenericChem.mPotassiumEthylXanthate, 32), aMat.getMilled(64))
            .fluidInputs(FluidUtils.getFluidStack(MilledOreProcessing.PineOil, 35000))
            .fluidOutputs(FluidUtils.getFluidStack(MilledOreProcessing.SpessartineFlotationFroth, 1000))
            .duration(4 * MINUTES)
            .eut(TierEU.RECIPE_LuV)
            .addTo(CRR);

        aMat = MaterialUtils.generateMaterialFromGtENUM(Materials.Sphalerite);
        FlotationRecipeHandler.registerOreType(aMat);
        GTValues.RA.stdBuilder()
            .itemInputs(ItemUtils.getSimpleStack(GenericChem.mSodiumEthylXanthate, 32), aMat.getMilled(64))
            .fluidInputs(FluidUtils.getFluidStack(MilledOreProcessing.PineOil, 14000))
            .fluidOutputs(FluidUtils.getFluidStack(MilledOreProcessing.SphaleriteFlotationFroth, 1000))
            .duration(4 * MINUTES)
            .eut(TierEU.RECIPE_LuV)
            .addTo(CRR);

        aMat = MaterialUtils.generateMaterialFromGtENUM(Materials.Pentlandite);
        FlotationRecipeHandler.registerOreType(aMat);
        GTValues.RA.stdBuilder()
            .itemInputs(ItemUtils.getSimpleStack(GenericChem.mPotassiumEthylXanthate, 32), aMat.getMilled(64))
            .fluidInputs(FluidUtils.getFluidStack(MilledOreProcessing.PineOil, 14000))
            .fluidOutputs(FluidUtils.getFluidStack(MilledOreProcessing.PentlanditeFlotationFroth, 1000))
            .duration(4 * MINUTES)
            .eut(TierEU.RECIPE_LuV)
            .addTo(CRR);

        aMat = MaterialUtils.generateMaterialFromGtENUM(Materials.Monazite);
        FlotationRecipeHandler.registerOreType(aMat);
        GTValues.RA.stdBuilder()
            .itemInputs(ItemUtils.getSimpleStack(GenericChem.mSodiumEthylXanthate, 32), aMat.getMilled(64))
            .fluidInputs(FluidUtils.getFluidStack(MilledOreProcessing.PineOil, 30000))
            .fluidOutputs(FluidUtils.getFluidStack(MilledOreProcessing.MonaziteFlotationFroth, 1000))
            .duration(4 * MINUTES)
            .eut(TierEU.RECIPE_LuV)
            .addTo(CRR);

        aMat = MaterialUtils.generateMaterialFromGtENUM(Materials.Redstone);
        FlotationRecipeHandler.registerOreType(aMat);
        GTValues.RA.stdBuilder()
            .itemInputs(ItemUtils.getSimpleStack(GenericChem.mSodiumEthylXanthate, 32), aMat.getMilled(64))
            .fluidInputs(FluidUtils.getFluidStack(MilledOreProcessing.PineOil, 13000))
            .fluidOutputs(FluidUtils.getFluidStack(MilledOreProcessing.RedstoneFlotationFroth, 1000))
            .duration(4 * MINUTES)
            .eut(TierEU.RECIPE_IV)
            .addTo(CRR);
    }
}
