package com.science.gtnl.mixins.early;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.science.gtnl.common.item.TimeStopManager;

@SuppressWarnings("UnusedMixin")
@Mixin(Block.class)
public abstract class Block_Mixin {

    @Inject(method = "onNeighborBlockChange", at = @At("HEAD"), cancellable = true)
    private void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor, CallbackInfo ci) {
        if (TimeStopManager.isTimeStopped() && !world.isRemote) {
            ci.cancel();
        }
    }

    @Inject(method = "updateTick", at = @At("HEAD"), cancellable = true)
    private void onBlockUpdateTick(World world, int x, int y, int z, java.util.Random random, CallbackInfo ci) {
        if (TimeStopManager.isTimeStopped() && !world.isRemote) {
            ci.cancel();
        }
    }

}
