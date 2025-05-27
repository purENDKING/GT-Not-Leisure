package com.science.gtnl.mixins.early.Minecraft;

import net.minecraft.entity.monster.EntityCreeper;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@SuppressWarnings("UnusedMixin")
@Mixin(EntityCreeper.class)
public interface EntityCreeperAccessor {

    @Accessor("explosionRadius")
    int getExplosionRadius();

    @Accessor("explosionRadius")
    @Mutable
    void setExplosionRadius(int radius);
}
