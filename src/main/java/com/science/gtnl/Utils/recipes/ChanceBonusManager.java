package com.science.gtnl.Utils.recipes;

import java.util.LinkedList;
import java.util.OptionalDouble;
import java.util.function.Function;

import org.intellij.lang.annotations.MagicConstant;

import com.science.gtnl.ScienceNotLeisure;
import com.science.gtnl.api.IVoltageChanceBonus;

import gregtech.api.enums.VoltageIndex;
import gregtech.api.util.GTRecipe;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.GTPPMultiBlockBase;

public class ChanceBonusManager {

    private static final LinkedList<ChanceBonusProvider> bonusProviders = new LinkedList<>();

    public interface ChanceBonusProvider {

        Double getBonus(Object machine, @MagicConstant(valuesFromClass = VoltageIndex.class) int recipeTier,
            double prevMultiplier);
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

    private static double getTierChanceBonus(int tier, int baseTier, double bonusPerTier) {
        return tier <= baseTier ? 0.0 : (tier - baseTier) * bonusPerTier;
    }

    public static Double getChanceBonus(Object machine,
        @MagicConstant(valuesFromClass = VoltageIndex.class) int recipeTier, double prevMultiplier) {
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

    public static OptionalDouble getChanceBonusOptional(Object machine,
        @MagicConstant(valuesFromClass = VoltageIndex.class) int recipeTier, double prevMultiplier) {
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
        // Strategy 1: IVoltageChanceBonus support
        addLastBonusProvider((machine, recipeTier, prevBonus) -> {
            if (machine instanceof IVoltageChanceBonus) {
                return ((IVoltageChanceBonus) machine).getBonusChance();
            }
            return null;
        });

        // Strategy 2: GT++ multiblock machine support
        addLastBonusProvider((machine, recipeTier, prevBonus) -> {
            if (machine instanceof GTPPMultiBlockBase) {
                try {
                    GTPPMultiBlockBase<?> mb = (GTPPMultiBlockBase<?>) machine;
                    int minTier = mb.mAllEnergyHatches.stream()
                        .mapToInt(h -> h.mTier)
                        .min()
                        .orElse(0);
                    return getTierChanceBonus(minTier, recipeTier, 0.15);
                } catch (Exception e) {
                    ScienceNotLeisure.LOG.warn("Error reading GT++ machine tier", e);
                }
            }
            return null;
        });
    }
}
