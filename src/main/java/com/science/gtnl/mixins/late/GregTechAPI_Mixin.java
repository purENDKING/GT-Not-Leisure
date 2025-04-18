package com.science.gtnl.mixins.late;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import com.google.common.collect.Multimap;

import gregtech.api.GregTechAPI;

@SuppressWarnings("UnusedMixin")
@Mixin(value = GregTechAPI.class, remap = false)
public interface GregTechAPI_Mixin {

    @Accessor("sRealConfigurationList")
    static Multimap<Integer, ItemStack> getRealConfigurationList() {
        throw new AssertionError();
    }

}
