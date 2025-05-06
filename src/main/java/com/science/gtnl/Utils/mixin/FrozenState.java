package com.science.gtnl.Utils.mixin;

import net.minecraft.entity.Entity;

public class FrozenState {

    public final double x, y, z;
    public final float yaw, pitch;

    public FrozenState(Entity entity) {
        this.x = entity.posX;
        this.y = entity.posY;
        this.z = entity.posZ;
        this.yaw = entity.rotationYaw;
        this.pitch = entity.rotationPitch;
    }
}
