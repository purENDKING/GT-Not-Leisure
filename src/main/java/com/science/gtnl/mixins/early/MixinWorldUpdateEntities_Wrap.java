package com.science.gtnl.mixins.early;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.science.gtnl.common.item.TimeStopManager;

@SuppressWarnings("UnusedMixin")
@Mixin(World.class)
public abstract class MixinWorldUpdateEntities_Wrap {

    @WrapWithCondition(
        method = "updateEntities",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;onUpdate()V"))
    private boolean wrapWeatherEntityOnUpdate(Entity entityInstance) {
        return !TimeStopManager.isTimeStopped() && entityInstance instanceof EntityPlayer;
    }

    @WrapWithCondition(
        method = "updateEntities",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;updateEntity(Lnet/minecraft/entity/Entity;)V"))
    private boolean wrapRegularEntityUpdate(World instance, Entity p_72870_1_) {
        return !TimeStopManager.isTimeStopped() && p_72870_1_ instanceof EntityPlayer;
    }

    @WrapWithCondition(
        method = "updateEntities",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/tileentity/TileEntity;updateEntity()V"))
    private boolean wrapTileEntityUpdate(TileEntity tileEntityInstance) {
        return !TimeStopManager.isTimeStopped();
    }
}
