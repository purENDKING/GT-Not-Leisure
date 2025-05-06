package com.science.gtnl.mixins.early.render;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderXPOrb;
import net.minecraft.entity.item.EntityXPOrb;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.science.gtnl.Utils.mixin.FrozenStateXP;
import com.science.gtnl.common.item.TimeStopManager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SuppressWarnings("UnusedMixin")
@Mixin(RenderXPOrb.class)
@SideOnly(Side.CLIENT)
public abstract class RenderXPOrb_Mixin extends Render {

    @Unique
    private static final Map<Integer, FrozenStateXP> FROZEN_STATES_XP = new HashMap<>();

    @Inject(method = "doRender(Lnet/minecraft/entity/item/EntityXPOrb;DDDFF)V", at = @At("HEAD"))
    private void onPreRenderXPOrb(EntityXPOrb entity, double x, double y, double z, float yaw, float partialTicks,
        CallbackInfo ci) {
        if (!TimeStopManager.isTimeStopped()) {
            FROZEN_STATES_XP.put(entity.getEntityId(), new FrozenStateXP(entity));
        }
    }

    @ModifyVariable(
        method = "doRender(Lnet/minecraft/entity/item/EntityXPOrb;DDDFF)V",
        at = @At("HEAD"),
        ordinal = 0,
        argsOnly = true)
    private double modifyRenderXXP(double originalRenderX, EntityXPOrb entity, double x, double y, double z, float yaw,
        float partialTicks) {
        if (TimeStopManager.isTimeStopped()) {
            FrozenStateXP fs = FROZEN_STATES_XP.get(entity.getEntityId());
            if (fs != null) {
                return fs.x - this.renderManager.renderPosX;
            }
        }
        return originalRenderX;
    }

    @ModifyVariable(
        method = "doRender(Lnet/minecraft/entity/item/EntityXPOrb;DDDFF)V",
        at = @At("HEAD"),
        ordinal = 1,
        argsOnly = true)
    private double modifyRenderYXP(double originalRenderY, EntityXPOrb entity, double x, double y, double z, float yaw,
        float partialTicks) {
        if (TimeStopManager.isTimeStopped()) {
            FrozenStateXP fs = FROZEN_STATES_XP.get(entity.getEntityId());
            if (fs != null) {
                return fs.y - this.renderManager.renderPosY;
            }
        }
        return originalRenderY;
    }

    @ModifyVariable(
        method = "doRender(Lnet/minecraft/entity/item/EntityXPOrb;DDDFF)V",
        at = @At("HEAD"),
        ordinal = 2,
        argsOnly = true)
    private double modifyRenderZXP(double originalRenderZ, EntityXPOrb entity, double x, double y, double z, float yaw,
        float partialTicks) {
        if (TimeStopManager.isTimeStopped()) {
            FrozenStateXP fs = FROZEN_STATES_XP.get(entity.getEntityId());
            if (fs != null) {
                return fs.z - this.renderManager.renderPosZ;
            }
        }
        return originalRenderZ;
    }
}
