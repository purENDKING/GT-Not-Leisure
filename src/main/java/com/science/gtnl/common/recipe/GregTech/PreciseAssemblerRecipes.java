package com.science.gtnl.common.recipe.GregTech;

import static goodgenerator.loader.Loaders.compactFusionCoil;
import static goodgenerator.loader.Loaders.huiCircuit;
import static gregtech.api.util.GTRecipeConstants.PRECISE_ASSEMBLER_CASING_TIER;

import net.minecraft.item.ItemStack;

import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.common.materials.MaterialPool;
import com.science.gtnl.common.recipe.IRecipePool;

import bartworks.system.material.WerkstoffLoader;
import goodgenerator.api.recipe.GoodGeneratorRecipeMaps;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTOreDictUnificator;
import gtPlusPlus.core.material.MaterialsAlloy;
import gtPlusPlus.core.material.MaterialsElements;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;

public class PreciseAssemblerRecipes implements IRecipePool {

    final RecipeMap<?> PAR = GoodGeneratorRecipeMaps.preciseAssemblerRecipes;

    @Override
    public void loadRecipes() {

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTNLItemList.NeutroniumWafer.get(4),
                ItemList.Circuit_Chip_Biocell.get(64),
                MaterialPool.Actinium.get(OrePrefixes.dust, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Strontium, 1))
            .itemOutputs(ItemList.Circuit_Wafer_Bioware.get(4))
            .fluidInputs(
                Materials.BioMediumSterilized.getFluid(1000),
                Materials.Lubricant.getFluid(1000),
                Materials.Nitrogen.getGas(10000))
            .duration(600)
            .eut(TierEU.RECIPE_UV)
            .metadata(PRECISE_ASSEMBLER_CASING_TIER, 3)
            .addTo(PAR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.FusionComputer_LuV.get(1),
                new ItemStack(compactFusionCoil, 1, 0),
                new ItemStack(huiCircuit, 16, 0),
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Osmiridium, 16))
            .itemOutputs(GTNLItemList.LuvKuangBiaoOneGiantNuclearFusionReactor.get(1))
            .fluidInputs(
                Materials.NiobiumTitanium.getMolten(864),
                MaterialsAlloy.INDALLOY_140.getFluidStack(1152),
                Materials.SolderingAlloy.getMolten(2304),
                Materials.Polybenzimidazole.getMolten(2304))
            .duration(400)
            .eut(TierEU.RECIPE_LuV)
            .metadata(PRECISE_ASSEMBLER_CASING_TIER, 1)
            .addTo(PAR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.FusionComputer_ZPMV.get(1),
                new ItemStack(compactFusionCoil, 1, 1),
                new ItemStack(huiCircuit, 16, 1),
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Europium, 16))
            .itemOutputs(GTNLItemList.ZpmKuangBiaoTwoGiantNuclearFusionReactor.get(1))
            .fluidInputs(
                Materials.VanadiumGallium.getMolten(864),
                MaterialsAlloy.INDALLOY_140.getFluidStack(1152),
                Materials.SolderingAlloy.getMolten(2304),
                Materials.Polybenzimidazole.getMolten(2304))
            .duration(400)
            .eut(TierEU.RECIPE_ZPM)
            .metadata(PRECISE_ASSEMBLER_CASING_TIER, 1)
            .addTo(PAR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.FusionComputer_UV.get(1),
                new ItemStack(compactFusionCoil, 1, 2),
                new ItemStack(huiCircuit, 16, 2),
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Americium, 16))
            .itemOutputs(GTNLItemList.UvKuangBiaoThreeGiantNuclearFusionReactor.get(1))
            .fluidInputs(
                Materials.YttriumBariumCuprate.getMolten(864),
                MaterialsAlloy.INDALLOY_140.getFluidStack(1152),
                Materials.SolderingAlloy.getMolten(2304),
                Materials.Polybenzimidazole.getMolten(2304))
            .duration(400)
            .eut(TierEU.RECIPE_UV)
            .metadata(PRECISE_ASSEMBLER_CASING_TIER, 2)
            .addTo(PAR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.FusionComputer_UV2.get(1),
                new ItemStack(compactFusionCoil, 1, 3),
                new ItemStack(huiCircuit, 16, 3),
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Infinity, 16))
            .itemOutputs(GTNLItemList.UhvKuangBiaoFourGiantNuclearFusionReactor.get(1))
            .fluidInputs(
                Materials.Europium.getMolten(864),
                MaterialsAlloy.INDALLOY_140.getFluidStack(1152),
                Materials.SolderingAlloy.getMolten(2304),
                Materials.Polybenzimidazole.getMolten(2304))
            .duration(400)
            .eut(TierEU.RECIPE_UHV)
            .metadata(PRECISE_ASSEMBLER_CASING_TIER, 2)
            .addTo(PAR);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.FusionComputer_UV3.get(1),
                new ItemStack(compactFusionCoil, 1, 4),
                new ItemStack(huiCircuit, 16, 4),
                MaterialsElements.STANDALONE.HYPOGEN.getPlateDouble(16))
            .itemOutputs(GTNLItemList.UevKuangBiaoFiveGiantNuclearFusionReactor.get(1))
            .fluidInputs(
                WerkstoffLoader.Oganesson.getFluidOrGas(864),
                MaterialsAlloy.INDALLOY_140.getFluidStack(1152),
                Materials.SolderingAlloy.getMolten(2304),
                Materials.Polybenzimidazole.getMolten(2304))
            .duration(400)
            .eut(TierEU.RECIPE_UEV)
            .metadata(PRECISE_ASSEMBLER_CASING_TIER, 3)
            .addTo(PAR);
    }
}
