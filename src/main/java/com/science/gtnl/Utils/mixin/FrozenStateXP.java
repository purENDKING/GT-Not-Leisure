package com.science.gtnl.Utils.mixin;

import net.minecraft.entity.item.EntityXPOrb;

public class FrozenStateXP {

    public final double x, y, z;

    public FrozenStateXP(EntityXPOrb entity) {
        this.x = entity.posX;
        this.y = entity.posY;
        this.z = entity.posZ;
    }
}
