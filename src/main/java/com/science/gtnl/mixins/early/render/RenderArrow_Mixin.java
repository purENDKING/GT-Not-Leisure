package com.science.gtnl.mixins.early.render;

import net.minecraft.client.renderer.entity.RenderArrow;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.science.gtnl.common.item.TimeStopManager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SuppressWarnings("UnusedMixin")
@Mixin(RenderArrow.class)
@SideOnly(Side.CLIENT)
public abstract class RenderArrow_Mixin {

    @Redirect(
        method = "doRender(Lnet/minecraft/entity/projectile/EntityArrow;DDDF F)V",
        at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", ordinal = 0, remap = false))
    private void redirect$rotateArrowYaw(float angle, float x, float y, float z) {
        if (!TimeStopManager.isTimeStopped()) {
            GL11.glRotatef(angle, x, y, z);
        }
    }

    @Redirect(
        method = "doRender(Lnet/minecraft/entity/projectile/EntityArrow;DDDF F)V",
        at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", ordinal = 1, remap = false))
    private void redirect$rotateArrowPitch(float angle, float x, float y, float z) {
        if (!TimeStopManager.isTimeStopped()) {
            GL11.glRotatef(angle, x, y, z);
        }
    }
}
