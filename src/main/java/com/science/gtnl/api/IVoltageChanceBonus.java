package com.science.gtnl.api;

import org.intellij.lang.annotations.MagicConstant;

import com.science.gtnl.config.MainConfig;

import gregtech.api.enums.VoltageIndex;

/**
 * The machines with voltage tier chance bonus.
 * <p>
 * The higher tier, the more bonus.
 *
 * @see com.science.gtnl.Utils.recipes.ChanceBonusManager
 */
public interface IVoltageChanceBonus {

    // Original code from Overpowered Mod. MIT License. 2025/4/18

    /**
     * @return the voltage tier; ULV is 0, LV is 1, etc.
     */
    @MagicConstant(valuesFromClass = VoltageIndex.class)
    int getVoltageTier();

    /**
     * @return the tier that tiers after this tier are getting bonus.
     */
    @MagicConstant(valuesFromClass = VoltageIndex.class)
    default int getBaseVoltageTier() {
        return VoltageIndex.ULV;
    }

    /**
     * @return the bonus chance; 0 does nothing, -0.1 is 10% less, 0.1 is 10% more.
     */
    default double getBonusChance() {
        return getVoltageTier() > getBaseVoltageTier()
            ? getBonusChancePerVoltage() * (getVoltageTier() - getBaseVoltageTier())
            : 0.0;
    }

    default double getBonusChancePerVoltage() {
        return MainConfig.recipeOutputChance;
    }

}
