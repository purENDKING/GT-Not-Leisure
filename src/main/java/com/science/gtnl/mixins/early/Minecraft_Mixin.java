package com.science.gtnl.mixins.early;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.resources.IResourceManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.reavaritia.common.render.CustomEntityRenderer;
import com.science.gtnl.common.item.TimeStopManager;

@SuppressWarnings("UnusedMixin")
@Mixin(value = Minecraft.class)
public abstract class Minecraft_Mixin {

    @Shadow
    private boolean isGamePaused;

    @Redirect(
        method = "startGame",
        at = @At(value = "NEW", target = "Lnet/minecraft/client/renderer/EntityRenderer;", ordinal = 0))
    private EntityRenderer redirectEntityRenderer(Minecraft mc, IResourceManager resourceManager) {
        return new CustomEntityRenderer(mc, resourceManager);
    }

    @WrapWithCondition(
        method = "runTick",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;updateClouds()V"))
    private boolean wrapRenderGlobalUpdate(RenderGlobal instance) {
        return !TimeStopManager.isTimeStopped();
    }

    @WrapWithCondition(
        method = "runTick",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/EffectRenderer;updateEffects()V"))
    private boolean wrapWorldUpdateEffects(EffectRenderer instance) {
        return !TimeStopManager.isTimeStopped();
    }

}
