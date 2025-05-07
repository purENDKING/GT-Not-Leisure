package com.science.gtnl.mixins.early;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.shader.ShaderGroup;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SuppressWarnings("UnusedMixin")
@Mixin(EntityRenderer.class)
public interface AccessorEntityRenderer {

    @Accessor("theShaderGroup")
    ShaderGroup getTheShaderGroup();

    @Accessor("theShaderGroup")
    void setTheShaderGroup(ShaderGroup shaderGroup);
}
