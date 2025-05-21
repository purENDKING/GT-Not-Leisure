package com.science.gtnl.api.mixinHelper;

import com.science.gtnl.mixins.early.NotEnoughItems.DrawableBuilderAccessor;

import codechicken.nei.drawable.DrawableBuilder;

public class ExtendedDrawableBuilder extends DrawableBuilder {

    public ExtendedDrawableBuilder(String resourceLocation, int u, int v, int width, int height, int textureWidth,
        int textureHeight) {
        super(resourceLocation, u, v, width, height);
        DrawableBuilderAccessor accessor = (DrawableBuilderAccessor) this;
        accessor.setTextureWidth(textureWidth);
        accessor.setTextureHeight(textureHeight);
    }
}
