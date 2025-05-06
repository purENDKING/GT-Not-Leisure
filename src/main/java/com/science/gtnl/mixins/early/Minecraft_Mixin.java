package com.science.gtnl.mixins.early;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.IResourceManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.reavaritia.common.render.CustomEntityRenderer;
import com.science.gtnl.common.item.TimeStopManager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SuppressWarnings("UnusedMixin")
@Mixin(value = Minecraft.class)
@SideOnly(Side.CLIENT)
public abstract class Minecraft_Mixin {

    @Shadow
    private boolean isGamePaused;

    @Redirect(
        method = "startGame",
        at = @At(value = "NEW", target = "Lnet/minecraft/client/renderer/EntityRenderer;", ordinal = 0))
    private EntityRenderer redirectEntityRenderer(Minecraft mc, IResourceManager resourceManager) {
        return new CustomEntityRenderer(mc, resourceManager);
    }

    @Inject(method = "runTick", at = @At("HEAD"))
    private void onRunTick(CallbackInfo ci) {
        if (TimeStopManager.isTimeStopped()) {
            Minecraft mc = Minecraft.getMinecraft();
            mc.ingameGUI.updateTick();
            mc.playerController.updateController();
            mc.theWorld.updateEntities();
            this.isGamePaused = true;
        }
    }
}
