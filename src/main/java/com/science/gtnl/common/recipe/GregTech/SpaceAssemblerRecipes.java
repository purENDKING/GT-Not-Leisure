package com.science.gtnl.common.recipe.GregTech;

import net.minecraft.item.ItemStack;

import com.gtnewhorizons.gtnhintergalactic.recipe.IGRecipeMaps;
import com.science.gtnl.common.recipe.IRecipePool;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTOreDictUnificator;
import gtPlusPlus.core.item.ModItems;
import gtPlusPlus.core.material.MaterialsAlloy;
import tectech.thing.CustomItemList;

public class SpaceAssemblerRecipes implements IRecipePool {

    final RecipeMap<?> SAR = IGRecipeMaps.spaceAssemblerRecipes;

    @Override
    public void loadRecipes() {

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Chip_Optical.get(1),
                ItemList.Circuit_Chip_Ram.get(16),
                CustomItemList.DATApipe.get(8),
                ItemList.Circuit_Chip_CrystalCPU.get(2),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Tritanium, 8),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.HSSS, 8),
                GTOreDictUnificator.get(OrePrefixes.gemExquisite, Materials.Glass, 1),
                MaterialsAlloy.HASTELLOY_C276.getPlate(2))
            .fluidInputs(Materials.Sunnarium.getMolten(10), Materials.ElectrumFlux.getMolten(288))
            .itemOutputs(ItemList.Optically_Compatible_Memory.get(1))
            .specialValue(1)
            .duration(240)
            .eut(TierEU.RECIPE_UV)
            .addTo(SAR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Energy_Module.get(1),
                new ItemStack(ModItems.itemDehydratorCoil, 16, 3),
                MaterialsAlloy.PIKYONIUM.getPlate(32),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.ZPM, 2),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 4),
                ItemList.Field_Generator_ZPM.get(2))
            .fluidInputs(
                MaterialsAlloy.PIKYONIUM.getFluidStack(8352),
                MaterialsAlloy.ENERGYCRYSTAL.getFluidStack(9216),
                MaterialsAlloy.TRINIUM_NAQUADAH_CARBON.getFluidStack(4320),
                MaterialsAlloy.INDALLOY_140.getFluidStack(2304))
            .itemOutputs(ItemList.ZPM.get(1))
            .specialValue(1)
            .duration(400)
            .eut(TierEU.RECIPE_UHV)
            .addTo(SAR);
    }
}
