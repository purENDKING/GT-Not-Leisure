package com.science.gtnl.mixins.early;

import net.minecraft.entity.player.EntityPlayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.science.gtnl.common.item.TimeStopManager;

@SuppressWarnings("UnusedMixin")
@Mixin(EntityPlayer.class)
public abstract class EntityPlayer_Mixin {

    @Inject(method = "onUpdate", at = @At("HEAD"), cancellable = true)
    private void onUpdatePlayer(CallbackInfo ci) {
        if (TimeStopManager.isTimeStopped()) {
            // 玩家仍需更新，故不取消
            // 这里根据注释，即使时间停止，玩家的 onUpdate 方法仍然会执行到这里
            // 如果你想让玩家在时间停止时也不更新，可以在这里加上 ci.cancel();
            // 例如：
            // if (TimeStopManager.isTimeStopped()) {
            // // 玩家在时间停止时跳过更新
            // ci.cancel();
            // }
        }
    }
}
