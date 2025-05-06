package com.reavaritia.common.entity;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityChronarchPoint extends Entity {

    private final int radius;
    private final int speedMultiplier;
    private final int maxTicks;
    private int age = 0;
    private final Random rand;

    public EntityChronarchPoint(World world) {
        super(world);
        this.radius = 0;
        this.speedMultiplier = 0;
        this.maxTicks = 0;
        this.rand = new Random();
        initInvisible();
    }

    public EntityChronarchPoint(World world, double x, double y, double z, int radius, int speedMultiplier,
        int durationTicks) {
        super(world);
        this.setPosition(x, y, z);
        this.noClip = true;
        this.radius = radius;
        this.speedMultiplier = speedMultiplier;
        this.maxTicks = durationTicks;
        this.rand = world.rand;
        initInvisible();
    }

    private void initInvisible() {
        this.setSize(0.0F, 0.0F);
        this.ignoreFrustumCheck = true;
    }

    @Override
    protected void entityInit() {}

    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {}

    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {}

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (worldObj.isRemote) return;

        int cx = MathHelper.floor_double(posX);
        int cy = MathHelper.floor_double(posY);
        int cz = MathHelper.floor_double(posZ);

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    int x = cx + dx;
                    int y = cy + dy;
                    int z = cz + dz;

                    TileEntity te = worldObj.getTileEntity(x, y, z);
                    if (te != null) {
                        try {
                            for (int i = 0; i < speedMultiplier; i++) {
                                te.updateEntity();
                            }
                        } catch (Throwable ignored) {}
                    }

                    Block block = worldObj.getBlock(x, y, z);
                    if (block != null && block.getTickRandomly()) {
                        try {
                            for (int i = 0; i < speedMultiplier; i++) {
                                block.updateTick(worldObj, x, y, z, rand);
                            }
                        } catch (Throwable ignored) {}
                    }
                }
            }
        }

        if (++age >= maxTicks) {
            setDead();
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public boolean isEntityInvulnerable() {
        return true;
    }
}
