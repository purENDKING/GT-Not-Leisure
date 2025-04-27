package com.reavaritia.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface playSound {

    default void playSoundIfReady(World world, EntityPlayer player) {}

    default void playSoundIfReady(EntityPlayer player) {}
}
