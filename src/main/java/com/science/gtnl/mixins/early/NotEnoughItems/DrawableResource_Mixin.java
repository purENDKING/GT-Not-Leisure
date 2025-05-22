package com.science.gtnl.mixins.early.NotEnoughItems;

import net.minecraft.util.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.science.gtnl.api.mixinHelper.IDrawableResourceAccessor;
import com.science.gtnl.common.render.WrenchSpecialRender;

import codechicken.nei.Image;
import codechicken.nei.drawable.DrawableResource;

@SuppressWarnings("UnusedMixin")
@Mixin(value = DrawableResource.class, remap = false)
public abstract class DrawableResource_Mixin extends Image
    implements IDrawableResourceAccessor, DrawableResourceAccessor {

    @Unique
    private final WrenchSpecialRender specialRender = new WrenchSpecialRender();

    @Shadow
    private ResourceLocation resourceLocation;
    @Shadow
    private int textureWidth;
    @Shadow
    private int textureHeight;

    @Shadow
    private int paddingTop;
    @Shadow
    private int paddingLeft;

    public DrawableResource_Mixin(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(ResourceLocation resourceLocation, int u, int v, int width, int height, int paddingTop,
        int paddingBottom, int paddingLeft, int paddingRight, int textureWidth, int textureHeight, CallbackInfo ci) {}

    @Unique
    public void draw(int xOffset, int yOffset, int special) {
        final int textureX = this.x;
        final int textureY = this.y;
        specialRender.draw(
            xOffset,
            yOffset,
            special,
            resourceLocation,
            paddingLeft,
            paddingTop,
            textureX,
            textureY,
            width,
            height,
            textureWidth,
            textureHeight);
    }
}
