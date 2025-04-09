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
        this.input = meteor.focusStack != null ? meteor.focusStack.copy() : null;
        this.cost = meteor.cost;
        this.radius = meteor.radius;

        float fillerRatio = meteor.fillerChance / 100.0f;
        float componentRatio = 1.0f - fillerRatio;

        List<MeteorParadigmComponent> componentsCopy = copyComponents(meteor.componentList);
        List<MeteorParadigmComponent> fillersCopy = meteor.fillerChance > 0 ? copyComponents(meteor.fillerList)
            : new ArrayList<>();

        float totalComponentWeight = calculateTotalWeight(componentsCopy);
        componentsCopy.sort(Comparator.comparingInt(c -> -c.getWeight()));

        processComponents(componentsCopy, componentRatio, totalComponentWeight);

        if (meteor.fillerChance > 0) {
            float totalFillerWeight = calculateTotalWeight(fillersCopy);
            fillersCopy.sort(Comparator.comparingInt(c -> -c.getWeight()));
            processComponents(fillersCopy, fillerRatio, totalFillerWeight);
        }
    }

    private List<MeteorParadigmComponent> copyComponents(List<MeteorParadigmComponent> originals) {
        List<MeteorParadigmComponent> copies = new ArrayList<>();
        for (MeteorParadigmComponent comp : originals) {
            copies.add(
                new MeteorParadigmComponent(
                    comp.getValidBlockParadigm()
                        .copy(),
                    comp.getWeight()));
        }
        return copies;
    }

    private float calculateTotalWeight(List<MeteorParadigmComponent> components) {
        return (float) components.stream()
            .mapToInt(MeteorParadigmComponent::getWeight)
            .sum();
    }

    private void processComponents(List<MeteorParadigmComponent> components, float ratio, float totalWeight) {
        for (MeteorParadigmComponent component : components) {
            float chance = component.getWeight() / totalWeight * ratio;
            ItemStack outputStack = component.getValidBlockParadigm()
                .copy();
            outputStack.stackSize = getEstimatedAmount(chance, this.radius);
            outputs.add(outputStack);
            chances.add(chance);
            expectedAmounts.add(outputStack.stackSize);
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
