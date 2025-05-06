package com.science.gtnl.mixins.early.render;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.science.gtnl.common.item.TimeStopManager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SuppressWarnings("UnusedMixin")
@Mixin(RendererLivingEntity.class)
@SideOnly(Side.CLIENT)
public abstract class RendererLivingEntity_Mixin {

    @Inject(method = "rotateCorpse(Lnet/minecraft/entity/EntityLivingBase;FFF)V", at = @At("HEAD"), cancellable = true)
    private void onRotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4,
        CallbackInfo ci) {
        if (TimeStopManager.isTimeStopped()) {
            ci.cancel();
        }
    }
}
