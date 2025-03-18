package com.science.gtnl.common.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityArrowCustom extends EntityArrow {

    public EntityArrowCustom(World world) {
        super(world);
    }

    public EntityArrowCustom(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    public void onUpdate() {
        super.onUpdate(); // 重要：先调用父类更新
        if (this.inGround && this.ticksInGround >= 100) {
            this.setDead();
        }
    }

    // 修正后的箭雨生成方法（基于玩家位置筛选目标）
    public void generateAdvancedArrowRain(EntityPlayer player) {
        World world = this.worldObj;
        if (player == null) return;

        // 以玩家当前位置为中心创建检测区域（半径20格）
        AxisAlignedBB area = AxisAlignedBB.getBoundingBox(
            player.posX - 20,
            player.posY - 20,
            player.posZ - 20,
            player.posX + 20,
            player.posY + 20,
            player.posZ + 20);

        // 获取区域内的所有实体（排除玩家自己）
        List<Entity> targets = world.getEntitiesWithinAABBExcludingEntity(player, area);

        for (Entity entity : targets) {
            // 过滤非敌对生物
            if (!isValidTarget(entity, player)) continue;

            // 散射参数配置
            final int arrowsPerTarget = 5; // 每个目标生成5支箭
            final double verticalOffset = 18.0; // 起始高度
            final double horizontalSpread = 3.0; // 水平散布范围

            for (int i = 0; i < arrowsPerTarget; i++) {
                EntityArrowCustom arrow = new EntityArrowCustom(world);

                // 在目标头顶生成散射箭矢
                double spawnX = entity.posX + (world.rand.nextDouble() - 0.5) * horizontalSpread;
                double spawnZ = entity.posZ + (world.rand.nextDouble() - 0.5) * horizontalSpread;

                arrow.setPosition(
                    spawnX,
                    entity.posY + verticalOffset, // 从目标上方18格高度开始下落
                    spawnZ);

                // 设置下落速度（垂直为主，带轻微水平随机）
                arrow.addVelocity(
                    (world.rand.nextDouble() - 0.5) * 0.4,
                    -2.0 + (world.rand.nextDouble() - 0.5) * 0.3,
                    (world.rand.nextDouble() - 0.5) * 0.4);

                // 配置箭矢属性
                arrow.setDamage(10.0F); // 调整为合理伤害值
                arrow.shootingEntity = player;
                arrow.setIsCritical(true);
                arrow.canBePickedUp = 0; // 禁止拾取

                world.spawnEntityInWorld(arrow);
            }
        }
    }

    // 精简后的目标过滤（仅攻击敌对生物）
    private boolean isValidTarget(Entity entity, EntityPlayer player) {
        if (entity == player) return false;

        return entity instanceof IMob;
    }
}
