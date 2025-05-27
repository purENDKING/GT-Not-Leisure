package com.science.gtnl.mixins.early.Minecraft;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.science.gtnl.Utils.Utils;
import com.science.gtnl.config.MainConfig;

@SuppressWarnings("UnusedMixin")
@Mixin(EntityAICreeperSwell.class)
public abstract class EntityAICreeperSwell_Mixin {

    @Shadow
    EntityCreeper swellingCreeper;

    @Unique
    private Utils.TargetInfo cachedBlockTarget = null;
    @Unique
    private Utils.TargetInfo cachedPlayerTarget = null;

    @Unique
    private long lastBlockTargetUpdateTick = 0;
    @Unique
    private long lastPlayerTargetUpdateTick = 0;

    @Unique
    private final int blockTargetInterval = MainConfig.blockTargetInterval;
    @Unique
    private final int playerTargetInterval = MainConfig.playerTargetInterval;
    @Unique
    private final int blockFindRadius = MainConfig.blockFindRadius;
    @Unique
    private final int playerFindRadius = MainConfig.playerFindRadius;
    @Unique
    private final int explosionPower = MainConfig.explosionPower;
    @Unique
    private final double moveSpeed = MainConfig.moveSpeed;
    @Unique
    private final double explosionTriggerRange = MainConfig.explosionTriggerRange;

    public Utils.TargetInfo findNearestTarget() {
        long currentTick = this.swellingCreeper.worldObj.getTotalWorldTime();

        if (currentTick - lastBlockTargetUpdateTick >= blockTargetInterval) {
            cachedBlockTarget = findNearestTargetBlock();
            lastBlockTargetUpdateTick = currentTick;
        }

        if (currentTick - lastPlayerTargetUpdateTick >= playerTargetInterval) {
            cachedPlayerTarget = findNearestTargetPlayer();
            lastPlayerTargetUpdateTick = currentTick;
        }

        if (cachedBlockTarget == null) return cachedPlayerTarget;
        if (cachedPlayerTarget == null) return cachedBlockTarget;

        return cachedBlockTarget.distance < cachedPlayerTarget.distance ? cachedBlockTarget : cachedPlayerTarget;
    }

    private Utils.TargetInfo findNearestTargetBlock() {
        double minDistance = Double.MAX_VALUE;
        Utils.TargetInfo closestTarget = null;

        int radius = blockFindRadius;

        for (int x = MathHelper.floor_double(this.swellingCreeper.posX) - radius; x
            <= MathHelper.floor_double(this.swellingCreeper.posX) + radius; ++x) {
            for (int y = MathHelper.floor_double(this.swellingCreeper.posY) - radius; y
                <= MathHelper.floor_double(this.swellingCreeper.posY) + radius; ++y) {
                for (int z = MathHelper.floor_double(this.swellingCreeper.posZ) - radius; z
                    <= MathHelper.floor_double(this.swellingCreeper.posZ) + radius; ++z) {
                    Block block = this.swellingCreeper.worldObj.getBlock(x, y, z);
                    int meta = this.swellingCreeper.worldObj.getBlockMetadata(x, y, z);
                    if (Utils.isTargetBlock(block, meta)) {
                        double dist = this.swellingCreeper.getDistance(x + 0.5, y + 0.5, z + 0.5);
                        if (dist < minDistance) {
                            minDistance = dist;
                            closestTarget = new Utils.TargetInfo(x, y, z, dist);
                        }
                    }
                }
            }
        }

        return closestTarget;
    }

    private Utils.TargetInfo findNearestTargetPlayer() {
        double minDistance = Double.MAX_VALUE;
        Utils.TargetInfo closestTarget = null;

        int radius = playerFindRadius;

        List<EntityPlayer> players = this.swellingCreeper.worldObj
            .getEntitiesWithinAABB(EntityPlayer.class, this.swellingCreeper.boundingBox.expand(radius, radius, radius));

        for (EntityPlayer player : players) {
            if (!player.isEntityAlive() || player.capabilities.isCreativeMode) continue;

            double dist = this.swellingCreeper.getDistanceToEntity(player);
            if (dist < minDistance) {
                minDistance = dist;
                closestTarget = new Utils.TargetInfo(player, dist);
            }
        }

        return closestTarget;
    }

    @Inject(method = "shouldExecute", at = @At("HEAD"), cancellable = true)
    public void shouldExecute(CallbackInfoReturnable<Boolean> cir) {
        EntityLivingBase originalTarget = this.swellingCreeper.getAttackTarget();
        Utils.TargetInfo customTarget = findNearestTarget();

        if (this.swellingCreeper.getCreeperState() > 0
            || (originalTarget != null && this.swellingCreeper.getDistanceToEntity(originalTarget) < 16.0D)
            || (customTarget != null && customTarget.distance < 32.0D)) {
            cir.setReturnValue(true);
        } else {
            cir.setReturnValue(false);
        }
        cir.cancel();
    }

    @Inject(method = "updateTask", at = @At("HEAD"), cancellable = true)
    public void updateTask(CallbackInfo ci) {
        Utils.TargetInfo customTarget = findNearestTarget();

        if (customTarget == null) {
            if (this.swellingCreeper.getAttackTarget() == null) {
                this.swellingCreeper.setCreeperState(-1);
            }
        } else {
            double targetX = customTarget.x + 0.5;
            double targetY = customTarget.y + 0.5;
            double targetZ = customTarget.z + 0.5;

            if (customTarget.distance <= explosionTriggerRange) {
                if (this.swellingCreeper.getCreeperState() < 1) {
                    this.swellingCreeper.setCreeperState(1);
                    ((EntityCreeperAccessor) this.swellingCreeper).setExplosionRadius(explosionPower);
                }
                this.swellingCreeper.getNavigator()
                    .clearPathEntity();
            } else {
                this.swellingCreeper.getNavigator()
                    .tryMoveToXYZ(targetX, targetY, targetZ, moveSpeed);

                boolean canSeeTarget = true;
                if (customTarget.isEntityTarget() && customTarget.entityTarget != null) {
                    canSeeTarget = this.swellingCreeper.getEntitySenses()
                        .canSee(customTarget.entityTarget);
                }

                if (!canSeeTarget) {
                    this.swellingCreeper.setCreeperState(-1);
                }
            }
        }
        ci.cancel();
    }
}
