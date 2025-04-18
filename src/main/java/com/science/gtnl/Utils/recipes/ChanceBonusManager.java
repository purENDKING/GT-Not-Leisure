package com.science.gtnl.Utils.recipes;

import java.util.LinkedList;
import java.util.OptionalDouble;
import java.util.function.Function;

import com.science.gtnl.ScienceNotLeisure;
import com.science.gtnl.config.MainConfig;

import gregtech.api.metatileentity.implementations.MTEMultiBlockBase;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;

public class ChanceBonusManager {

    // Original code from Overpowered Mod. MIT License. 2025/4/18

    private static final LinkedList<ChanceBonusProvider> bonusProviders = new LinkedList<>();
    public static GTRecipe lastRecipes;

    public interface ChanceBonusProvider {

        Double getBonus(Object machine, int recipeTier, double prevMultiplier);
    }

    public static class ChanceBonusProviderContext {

        public final Object machine;
        public final int recipeTier;
        public final double prevChanceMultiplier;

        public ChanceBonusProviderContext(Object machine, int recipeTier, double prevChanceMultiplier) {
            this.machine = machine;
            this.recipeTier = recipeTier;
            this.prevChanceMultiplier = prevChanceMultiplier;
        }
    }

    public static void addLastBonusProvider(ChanceBonusProvider provider) {
        bonusProviders.addLast(provider);
    }

    public static void addFirstBonusProvider(ChanceBonusProvider provider) {
        bonusProviders.addFirst(provider);
    }

    public static void addLastBonusProvider(Function<ChanceBonusProviderContext, Double> provider) {
        bonusProviders
            .addLast((machine, tier, prev) -> provider.apply(new ChanceBonusProviderContext(machine, tier, prev)));
    }

    public static void addFirstBonusProvider(Function<ChanceBonusProviderContext, Double> provider) {
        bonusProviders
            .addFirst((machine, tier, prev) -> provider.apply(new ChanceBonusProviderContext(machine, tier, prev)));
    }

    public static void setLastGTRecipe(GTRecipe recipe) {
        lastRecipes = recipe;
    }

    public static GTRecipe getLastGTRecipe() {
        return lastRecipes;
    }

    private static double getTierChanceBonus(int tier, int baseTier, double bonusPerTier) {
        return tier <= baseTier ? 0.0 : (tier - baseTier) * bonusPerTier;
    }

    public static Double getChanceBonus(Object machine, int recipeTier, double prevMultiplier) {
        for (ChanceBonusProvider provider : bonusProviders) {
            try {
                Double bonus = provider.getBonus(machine, recipeTier, prevMultiplier);
                if (bonus != null) return bonus;
            } catch (Exception e) {
                ScienceNotLeisure.LOG.warn("Error in chance bonus provider", e);
            }
        }
        return null;
    }

    public static OptionalDouble getChanceBonusOptional(Object machine, int recipeTier, double prevMultiplier) {
        Double result = getChanceBonus(machine, recipeTier, prevMultiplier);
        return result != null ? OptionalDouble.of(result) : OptionalDouble.empty();
    }

    public static GTRecipe copyAndBonusChance(GTRecipe recipe, double bonus) {
        if (recipe.mChances == null) {
            return recipe;
        } else {
            GTRecipe copy = recipe.copy();
            int[] newChances = new int[copy.mChances.length];
            for (int i = 0; i < copy.mChances.length; i++) {
                newChances[i] = Math.min((int) (copy.mChances[i] + (bonus * 10000)), 10000);
            }
            copy.mChances = newChances;
            return copy;
        }
    }

    static {
        addLastBonusProvider((machine, recipeTier, prevBonus) -> {
            if (machine instanceof MTEMultiBlockBase mte) {
                try {
                    int machineTier = GTUtility.getTier(mte.getMaxInputVoltage());
                    int baseTier = GTUtility.getTier(getLastGTRecipe().mEUt);
                    double bonusPerTier = MainConfig.recipeOutputChance / 100.0;

                    return getTierChanceBonus(machineTier, baseTier, bonusPerTier);
                } catch (Exception e) {
                    ScienceNotLeisure.LOG.warn("Error reading MTEExtendedPowerMultiBlockBase voltage tier", e);
                }
            }
            return null;
        });
    }

}
