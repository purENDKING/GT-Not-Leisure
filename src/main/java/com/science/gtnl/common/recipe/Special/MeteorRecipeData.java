package com.science.gtnl.common.recipe.Special;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.minecraft.item.ItemStack;

import WayofTime.alchemicalWizardry.common.summoning.meteor.MeteorParadigm;
import WayofTime.alchemicalWizardry.common.summoning.meteor.MeteorParadigmComponent;

public class MeteorRecipeData {

    public final ItemStack input;
    public final List<ItemStack> outputs = new ArrayList<>();
    public final List<Float> chances = new ArrayList<>();
    public final List<Integer> expectedAmounts = new ArrayList<>();

    public final int cost;
    public final int radius;

    public MeteorRecipeData(MeteorParadigm meteor) {
        this.input = meteor.focusStack;
        this.cost = meteor.cost;
        this.radius = meteor.radius;

        float fillerRatio = meteor.fillerChance / 100.0f;
        float componentRatio = 1.0f - fillerRatio;

        float totalComponentWeight = meteor.getTotalListWeight(meteor.componentList);
        List<MeteorParadigmComponent> sortedComponents = new ArrayList<>(meteor.componentList);
        sortedComponents.sort(Comparator.comparingInt(c -> -c.getWeight()));

        for (MeteorParadigmComponent component : sortedComponents) {
            float chance = component.getWeight() / totalComponentWeight * componentRatio;
            ItemStack outputStack = component.getValidBlockParadigm();
            outputStack.stackSize = getEstimatedAmount(chance, meteor.radius); // Set the quantity
            outputs.add(outputStack);
            chances.add(chance);
            expectedAmounts.add(outputStack.stackSize);
        }

        if (meteor.fillerChance > 0) {
            float totalFillerWeight = meteor.getTotalListWeight(meteor.fillerList);
            List<MeteorParadigmComponent> sortedFillers = new ArrayList<>(meteor.fillerList);
            sortedFillers.sort(Comparator.comparingInt(c -> -c.getWeight()));

            for (MeteorParadigmComponent filler : sortedFillers) {
                float chance = filler.getWeight() / totalFillerWeight * fillerRatio;
                ItemStack fillerStack = filler.getValidBlockParadigm();
                fillerStack.stackSize = getEstimatedAmount(chance, meteor.radius); // Set the quantity
                outputs.add(fillerStack);
                chances.add(chance);
                expectedAmounts.add(fillerStack.stackSize);
            }
        }
    }

    private int getEstimatedAmount(float chance, int radius) {
        return (int) Math.ceil(4f / 3 * Math.PI * Math.pow(radius + 0.5, 3) * chance);
    }

    public int getTotalExpectedAmount() {
        return expectedAmounts.stream()
            .mapToInt(Integer::intValue)
            .sum();
    }
}
