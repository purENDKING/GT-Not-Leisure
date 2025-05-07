package com.science.gtnl.mixins.early.render;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.science.gtnl.common.item.TimeStopManager;

@SuppressWarnings("UnusedMixin")
@Mixin(TextureMap.class)
public class TextureMap_Mixin {

    @WrapWithCondition(
        method = "updateAnimations",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;updateAnimation()V"))
    private boolean wrapRenderUpdateAnimation(TextureAtlasSprite instance) {
        return !TimeStopManager.isTimeStopped();
    }
}
