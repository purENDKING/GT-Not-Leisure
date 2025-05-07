package com.science.gtnl.mixins.early.render;

import com.science.gtnl.common.item.TimeStopManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(RenderManager.class)
public class RenderManager_Mixin {

    /*@Redirect(
        method = "renderEntityWithPosYaw",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/RenderManager;func_147939_a(Lnet/minecraft/entity/Entity;DDDFFZ)Z"
        )
    )
    private boolean redirectRenderEntity1(RenderManager instance, Entity entity, double x, double y, double z, float yaw, float partialTicks, boolean p_147939_7) {
        // 在这里你可以修改参数或完全改变行为
        // 例如，强制最后一个参数为 true 而不是 false:
        if (TimeStopManager.isTimeStopped()) {
            double renderPosX = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks) - x;
            double renderPosY = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks) - y;
            double renderPosZ = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks) - z;

            x = entity.posX - renderPosX;
            y = entity.posY - renderPosY;
            z = entity.posZ - renderPosZ;
        }

        return instance.func_147939_a(entity, x, y, z, yaw, partialTicks, p_147939_7);
    }*/

    /*@Redirect(
        method = "renderEntityStatic",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/RenderManager;func_147939_a(Lnet/minecraft/entity/Entity;DDDFFZ)Z"
        )
    )
    private boolean redirectRenderEntity2(RenderManager instance, Entity entity, double x, double y, double z, float yaw, float partialTicks, boolean p_147939_7) {
        // 在这里你可以修改参数或完全改变行为
        // 例如，强制最后一个参数为 true 而不是 false:
        if (TimeStopManager.isTimeStopped()) {
            double renderPosX = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks) - x;
            double renderPosY = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks) - y;
            double renderPosZ = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks) - z;

            x = entity.posX - renderPosX;
            y = entity.posY - renderPosY;
            z = entity.posZ - renderPosZ;
        }

        return instance.func_147939_a(entity, x, y, z, yaw, partialTicks, p_147939_7);
    }*/
}
