package com.science.gtnl.Utils;

import java.util.Collection;

import net.minecraft.item.ItemStack;

import org.intellij.lang.annotations.MagicConstant;

import com.science.gtnl.Utils.mixins.MixinAccessorBridge;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.VoltageIndex;
import gregtech.api.util.GTUtility;

/**
 * Utility class for interacting with GregTech machine blocks and configurations.
 */
public class GTApi {

    /**
     * Gets all configuration circuit ItemStacks from GregTech via Mixin accessor.
     *
     * @return a Collection of circuit stacks
     */
    public static Collection<ItemStack> getConfigurationCircuits() {
        return MixinAccessorBridge.getRealConfigurationList()
            .values();
    }

    /**
     * Checks whether the given ItemStack is one of the configuration circuits.
     *
     * @param stack the ItemStack to test
     * @return true if it matches a configuration circuit, false otherwise
     */
    public static boolean isConfigurationCircuit(ItemStack stack) {
        for (ItemStack s : getConfigurationCircuits()) {
            if (GTUtility.areStacksEqual(stack, s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines the practical voltage tier based on EU/t values.
     *
     * @param eut the EU per tick value
     * @return the voltage tier index
     */
    @MagicConstant(valuesFromClass = VoltageIndex.class)
    public static int getVoltagePracticalTier(long eut) {
        for (int i = 0; i < GTValues.VP.length; i++) {
            if (eut <= GTValues.VP[i]) {
                return i;
            }
        }
        return GTValues.VP.length - 1;
    }
}
