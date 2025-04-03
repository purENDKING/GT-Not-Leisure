package com.science.gtnl.common.recipe.AprilFool;

import static gtPlusPlus.core.block.ModBlocks.blockCactusCharcoal;
import static gtPlusPlus.core.block.ModBlocks.blockCactusCoke;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

import com.science.gtnl.common.recipe.IRecipePool;
import com.science.gtnl.common.recipe.RecipeRegister;
import com.science.gtnl.common.recipe.Special.SteamAmountSpecialValue;

import gregtech.api.enums.GTValues;
import gregtech.api.recipe.RecipeMap;
import gtPlusPlus.core.item.ModItems;
import gtPlusPlus.core.util.minecraft.FluidUtils;

public class CactusWonderFakeRecipes implements IRecipePool {

    private static final SteamAmountSpecialValue OFFER_VALUE = SteamAmountSpecialValue.INSTANCE;
    final RecipeMap<?> CWFR = RecipeRegister.CactusWonderFakeRecipes;

    @Override
    public void loadRecipes() {

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(ModItems.itemCactusCharcoal, 1))
            .fluidOutputs(FluidUtils.getSteam(64000))
            .metadata(OFFER_VALUE, 8000L)
            .duration(20)
            .eut(0)
            .fake()
            .addTo(CWFR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(blockCactusCharcoal, 1))
            .fluidOutputs(FluidUtils.getSteam(64000))
            .metadata(OFFER_VALUE, 90000L)
            .duration(20)
            .eut(0)
            .fake()
            .addTo(CWFR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(blockCactusCharcoal, 1, 1))
            .fluidOutputs(FluidUtils.getSteam(64000))
            .metadata(OFFER_VALUE, 1012500L)
            .duration(20)
            .eut(0)
            .fake()
            .addTo(CWFR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(blockCactusCharcoal, 1, 2))
            .fluidOutputs(FluidUtils.getSuperHeatedSteam(128000))
            .metadata(OFFER_VALUE, 11390625L)
            .duration(20)
            .eut(0)
            .fake()
            .addTo(CWFR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(blockCactusCharcoal, 1, 3))
            .fluidOutputs(FluidUtils.getSuperHeatedSteam(128000))
            .metadata(OFFER_VALUE, 128144531L)
            .duration(20)
            .eut(0)
            .fake()
            .addTo(CWFR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(blockCactusCharcoal, 1, 4))
            .fluidOutputs(FluidRegistry.getFluidStack("supercriticalsteam", 512000))
            .metadata(OFFER_VALUE, 1441625977L)
            .duration(20)
            .eut(0)
            .fake()
            .addTo(CWFR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(blockCactusCharcoal, 1, 5))
            .fluidOutputs(FluidRegistry.getFluidStack("supercriticalsteam", 512000))
            .metadata(OFFER_VALUE, 16218292236L)
            .duration(20)
            .eut(0)
            .fake()
            .addTo(CWFR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(ModItems.itemCactusCoke, 1))
            .fluidOutputs(FluidUtils.getSteam(64000))
            .metadata(OFFER_VALUE, 16000L)
            .duration(20)
            .eut(0)
            .fake()
            .addTo(CWFR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(blockCactusCoke, 1))
            .fluidOutputs(FluidUtils.getSteam(64000))
            .metadata(OFFER_VALUE, 180000L)
            .duration(20)
            .eut(0)
            .fake()
            .addTo(CWFR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(blockCactusCoke, 1, 1))
            .fluidOutputs(FluidUtils.getSteam(64000))
            .metadata(OFFER_VALUE, 2025000L)
            .duration(20)
            .eut(0)
            .fake()
            .addTo(CWFR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(blockCactusCoke, 1, 2))
            .fluidOutputs(FluidUtils.getSuperHeatedSteam(128000))
            .metadata(OFFER_VALUE, 22781250L)
            .duration(20)
            .eut(0)
            .fake()
            .addTo(CWFR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(blockCactusCoke, 1, 3))
            .fluidOutputs(FluidUtils.getSuperHeatedSteam(128000))
            .metadata(OFFER_VALUE, 256289063L)
            .duration(20)
            .eut(0)
            .fake()
            .addTo(CWFR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(blockCactusCoke, 1, 4))
            .fluidOutputs(FluidRegistry.getFluidStack("supercriticalsteam", 512000))
            .metadata(OFFER_VALUE, 2883251953L)
            .duration(20)
            .eut(0)
            .fake()
            .addTo(CWFR);

        GTValues.RA.stdBuilder()
            .itemInputs(new ItemStack(blockCactusCoke, 1, 5))
            .fluidOutputs(FluidRegistry.getFluidStack("supercriticalsteam", 512000))
            .metadata(OFFER_VALUE, 32436584473L)
            .duration(20)
            .eut(0)
            .fake()
            .addTo(CWFR);

    }
}
