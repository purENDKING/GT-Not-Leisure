package com.science.gtnl.common.recipe.GTNL.Thaumcraft;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;

public class TCRecipeTools {

    public static ArrayList<ShapedArcaneCraftingRecipe> SAR = new ArrayList<>();
    public static ArrayList<InfusionCraftingRecipe> ICR = new ArrayList<>();

    public TCRecipeTools() {}

    public static void getShapedArcaneCraftingRecipe() {
        List<Object> craftingRecipes = ThaumcraftApi.getCraftingRecipes();
        for (Object r : craftingRecipes) {
            if (!(r instanceof ShapedArcaneRecipe recipe)) {
                continue;
            }

            if (recipe.getRecipeOutput() instanceof ItemStack && recipe.getRecipeOutput()
                .getItem() != null) {
                ShapedArcaneCraftingRecipe y = new ShapedArcaneCraftingRecipe(
                    recipe.getInput(),
                    recipe.getRecipeOutput());
                SAR.add(y);
            }
        }
    }

    public static void getInfusionCraftingRecipe() {
        for (Object r : ThaumcraftApi.getCraftingRecipes()) {
            if (!(r instanceof InfusionRecipe recipe)) {
                continue;
            }

            if ((recipe.getRecipeOutput() instanceof ItemStack
                && ((ItemStack) recipe.getRecipeOutput()).getItem() != null
                && recipe.getRecipeInput() != null)) {
                InfusionCraftingRecipe y = new InfusionCraftingRecipe(
                    recipe.getRecipeInput(),
                    recipe.getRecipeOutput(),
                    recipe.getComponents());
                ICR.add(y);
            }
        }

    }

    public static class ShapedArcaneCraftingRecipe {

        private final Object[] InputItems;
        private final ItemStack OutputItem;

        public ShapedArcaneCraftingRecipe(Object[] InputItems, ItemStack OutputItem) {
            this.InputItems = InputItems;
            this.OutputItem = OutputItem;
        }

        public Object[] getInputItems() {
            return InputItems;
        }

        public ItemStack getOutput() {
            return OutputItem;
        }
    }

    public static class InfusionCraftingRecipe {

        private final ItemStack InputItem;
        private final ItemStack OutputItem;
        private final ItemStack[] Components;

        public InfusionCraftingRecipe(ItemStack InputItem, Object OutputItem, ItemStack[] Components) {
            this.InputItem = InputItem;
            this.OutputItem = (ItemStack) OutputItem;
            this.Components = Components;
        }

        public ItemStack[] getComponents() {
            return Components;
        }

        public ItemStack[] getInputItem() {
            ItemStack[] Input = new ItemStack[Components.length + 1];
            Input[0] = InputItem;
            int index = 1;
            for (ItemStack itemStack : Components) {
                Input[index] = itemStack;
                index++;
            }
            return Input;
        }

        public ItemStack getOutput() {
            return OutputItem;
        }

    }
}
