package com.science.gtnl.Utils.mixins;

import net.minecraft.item.ItemStack;

import com.google.common.collect.Multimap;
import com.science.gtnl.mixins.late.GregTechAPI_Mixin;

/**
 * This class is a bridge that used to access {@link org.spongepowered.asm.mixin.gen.Accessor}s in Kotlin while not
 * throwing {@link IncompatibleClassChangeError}.
 */
public class MixinAccessorBridge {

    /**
     * @return gregtech.api.GregTech_API#sRealConfigurationList
     */
    public static Multimap<Integer, ItemStack> getRealConfigurationList() {
        return GregTechAPI_Mixin.getRealConfigurationList();
    }

}
