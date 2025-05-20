package com.science.gtnl.mixins.late.Gregtech;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import gregtech.api.enums.HatchElement;
import gregtech.api.interfaces.IHatchElement;
import gregtech.api.util.HatchElementBuilder;
import gregtech.common.tileentities.machines.multi.MTEIntegratedOreFactory;

@SuppressWarnings("UnusedMixin")
@Mixin(value = MTEIntegratedOreFactory.class, remap = false)
public abstract class MTEIntegratedOreFactory_Mixin {

    @Redirect(
        method = "<clinit>",
        at = @At(
            value = "INVOKE",
            target = "Lgregtech/api/util/HatchElementBuilder;atLeast([Lgregtech/api/interfaces/IHatchElement;)Lgregtech/api/util/HatchElementBuilder;"))
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static HatchElementBuilder redirectAtLeast(HatchElementBuilder instance, IHatchElement<?>[] elements) {
        if (elements.length == 2 && elements[0] == HatchElement.Energy && elements[1] == HatchElement.Maintenance) {
            return instance.atLeast(HatchElement.Energy.or(HatchElement.ExoticEnergy), HatchElement.Maintenance);
        }
        return instance.atLeast(elements);
    }

    @ModifyConstant(method = "checkProcessing", constant = @Constant(intValue = 1024))
    private int modifyMaxParaConstant(int original) {
        return 65536;
    }

}
