package com.science.gtnl.mixins.early.render;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.RenderItemFrame;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.science.gtnl.common.item.TimeStopManager;

@SuppressWarnings("UnusedMixin")
@Mixin(RenderItemFrame.class)
public class RenderItemFrame_Mixin {

    @WrapWithCondition(
        method = "func_82402_b",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;updateAnimation()V"))
    private boolean wrapRenderUpdateAnimation(TextureAtlasSprite instance) {
        return !TimeStopManager.isTimeStopped();
    }
}
