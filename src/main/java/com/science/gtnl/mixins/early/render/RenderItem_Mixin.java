package com.science.gtnl.mixins.early.render;

import net.minecraft.client.renderer.entity.RenderItem;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.science.gtnl.common.item.TimeStopManager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SuppressWarnings("UnusedMixin")
@Mixin(RenderItem.class)
public abstract class RenderItem_Mixin {

    /*@ModifyVariable(
        method = "doRender(Lnet/minecraft/entity/item/EntityItem;DDDFF)V",
        at = @At(value = "STORE", ordinal = 0),
        name = "f4")
    private float modifyItemBobbingYOffset(float bobbingYOffset) {
        if (TimeStopManager.isTimeStopped()) {
            return 0.0F;
        }
        return bobbingYOffset;
    }

    @Redirect(
        method = "doRender(Lnet/minecraft/entity/item/EntityItem;DDDFF)V",
        at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V", ordinal = 1, remap = false))
    private void redirect$translateItemRotateOffset(float x, float y, float z) {
        if (!TimeStopManager.isTimeStopped()) {
            GL11.glTranslatef(x, y, z);
        }
    }

    @Redirect(
        method = "doRender(Lnet/minecraft/entity/item/EntityItem;DDDFF)V",
        at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", ordinal = 0, remap = false))
    private void redirect$rotateItemSpin(float angle, float x, float y, float z) {
        if (!TimeStopManager.isTimeStopped()) {
            GL11.glRotatef(angle, x, y, z);
        }
    }*/
}
