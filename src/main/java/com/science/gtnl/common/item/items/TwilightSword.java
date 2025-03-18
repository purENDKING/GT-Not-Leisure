package com.science.gtnl.common.item.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

import com.science.gtnl.client.GTNLCreativeTabs;
import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.common.entity.EntityArrowCustom;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TwilightSword extends ItemSword {

    public static final Item.ToolMaterial[] TWILIGHT_MATERIALS = {
        EnumHelper.addToolMaterial("TWILIGHT_EXPLOSIVE", 3, 9999, 14.0F, 30.0F, 30),
        EnumHelper.addToolMaterial("TWILIGHT_MAGIC", 3, 9999, 14.0F, 40.0F, 30),
        EnumHelper.addToolMaterial("TWILIGHT_PHYSICAL", 3, 9999, 14.0F, 50.0F, 30),
        EnumHelper.addToolMaterial("TWILIGHT_VOID", 3, 9999, 14.0F, 80.0F, 30) };

    private enum DamageType {
        EXPLOSIVE,
        MAGIC,
        PHYSICAL,
        VOID
    }

    private DamageType currentDamageType = DamageType.PHYSICAL;
    private int attackCount = 0;
    private Map<UUID, Long> rightClickTimes = new HashMap<>();

    public TwilightSword() {
        super(TWILIGHT_MATERIALS[0]);
        this.setUnlocalizedName("TwilightSword");
        this.setTextureName("sciencenotleisure:TwilightSword");
        setCreativeTab(GTNLCreativeTabs.GTNotLeisureItem);
        setMaxDamage(9999);
        GTNLItemList.TwilightSword.set(new ItemStack(this, 1));
        this.setMaxStackSize(1);
    }

    // 右键开始使用物品
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        rightClickTimes.put(player.getUniqueID(), world.getTotalWorldTime());
        return stack;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        if (player.worldObj.getTotalWorldTime() % 5 == 0) {
            new EntityArrowCustom(player.worldObj).generateAdvancedArrowRain(player);
        }
    }

    // 攻击处理核心逻辑
    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        // 切换伤害类型
        currentDamageType = DamageType.values()[attackCount++ % 4];

        // 强制暴击
        float baseDamage = TWILIGHT_MATERIALS[0].getDamageVsEntity() * (attacker.getHealth() < 10 ? 2 : 1);
        float finalDamage = baseDamage * 1.5f; // 暴击加成

        // 应用无视创造的伤害
        applyCreativeBypassDamage(target, finalDamage, currentDamageType);

        // 添加随机负面效果
        addRandomEffect(target);
        return true;
    }

    // 自定义伤害应用方法
    private void applyCreativeBypassDamage(EntityLivingBase target, float damage, DamageType type) {
        if (target instanceof EntityPlayer && ((EntityPlayer) target).capabilities.isCreativeMode) {
            // 强制设置生命值
            target.setHealth(target.getHealth() - damage);
        } else {
            // 根据类型应用不同伤害来源
            switch (type) {
                case EXPLOSIVE:
                    target.attackEntityFrom(DamageSourceTwilight.EXPLOSIVE, damage);
                    break;
                case MAGIC:
                    target.attackEntityFrom(DamageSourceTwilight.MAGIC, damage);
                    break;
                case PHYSICAL:
                    target.attackEntityFrom(DamageSourceTwilight.PHYSICAL, damage);
                    break;
                case VOID:
                    target.attackEntityFrom(DamageSourceTwilight.VOID, damage);
                    break;
            }
        }
    }

    // 自定义伤害来源
    public static class DamageSourceTwilight extends DamageSource {

        public static final DamageSource EXPLOSIVE = new DamageSourceTwilight("twilight_explosive").setExplosion();
        public static final DamageSource MAGIC = new DamageSourceTwilight("twilight_magic").setMagicDamage();
        public static final DamageSource PHYSICAL = new DamageSourceTwilight("twilight_physical")
            .setDamageBypassesArmor();
        public static final DamageSource VOID = new DamageSourceTwilight("twilight_void").setDamageBypassesArmor();

        protected DamageSourceTwilight(String name) {
            super(name);
        }

        @Override
        public boolean isUnblockable() {
            return true;
        }
    }

    // 玩家状态更新
    @SubscribeEvent
    public void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            if (isHoldingSword(player)) {
                applyBuffs(player);
                clearProjectiles(player);
            }
        }
    }

    // 弹射物免疫处理
    @SubscribeEvent
    public void onProjectileHit(LivingAttackEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            if (isHoldingSword(player) && event.source.isProjectile()) {
                event.setCanceled(true);
                reflectProjectile(event.source.getEntity());
            }
        }
    }

    private void reflectProjectile(Entity projectile) {
        if (projectile instanceof IProjectile && !projectile.isDead) {
            // 反转运动方向并重置存活时间
            projectile.motionX *= -1.5;
            projectile.motionY *= -0.5;
            projectile.motionZ *= -1.5;
            projectile.velocityChanged = true;
            ((IProjectile) projectile)
                .setThrowableHeading(projectile.motionX, projectile.motionY, projectile.motionZ, 1.5f, 1.0f);
        }
    }

    // 辅助方法
    private boolean isHoldingSword(EntityPlayer player) {
        return player.getHeldItem() != null && player.getHeldItem()
            .getItem() instanceof TwilightSword;
    }

    private void addRandomEffect(EntityLivingBase target) {
        int effect = new Random().nextInt(4);
        switch (effect) {
            case 0:
                target.addPotionEffect(new PotionEffect(Potion.blindness.id, 100, 0));
                break;
            case 1:
                target.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100, 0));
                break;
            case 2:
                target.addPotionEffect(new PotionEffect(Potion.wither.id, 100, 0));
                break;
            case 3:
                target.addPotionEffect(new PotionEffect(Potion.weakness.id, 100, 0));
                break;
        }
    }

    private void applyBuffs(EntityPlayer player) {
        int duration = 20;
        player.addPotionEffect(new PotionEffect(Potion.resistance.id, duration, 3));
        player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, duration, 3));
        player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, duration, 3));
        player.addPotionEffect(new PotionEffect(Potion.regeneration.id, duration, 3));
        player.addPotionEffect(new PotionEffect(Potion.nightVision.id, duration, 0));
        player.addPotionEffect(new PotionEffect(Potion.jump.id, duration, 1));
        player.clearActivePotions();
    }

    private void clearProjectiles(EntityPlayer player) {
        List<Entity> projectiles = player.worldObj
            .getEntitiesWithinAABB(Entity.class, player.boundingBox.expand(3, 3, 3));
        for (Entity e : projectiles) {
            if (e instanceof IProjectile) {
                e.setDead();
            }
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 720000; // 最长使用时间
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemStack, final EntityPlayer player, final List toolTip,
        final boolean advancedToolTips) {
        toolTip.add(StatCollector.translateToLocal("Tooltip_TwilightSword_00"));
        toolTip.add(StatCollector.translateToLocal("Tooltip_TwilightSword_01"));
        toolTip.add(StatCollector.translateToLocal("Tooltip_TwilightSword_02"));
        toolTip.add(StatCollector.translateToLocal("Tooltip_TwilightSword_03"));
        toolTip.add(StatCollector.translateToLocal("Tooltip_TwilightSword_04"));
        toolTip.add(StatCollector.translateToLocal("Tooltip_TwilightSword_05"));
        toolTip.add(StatCollector.translateToLocal("Tooltip_TwilightSword_06"));
        toolTip.add(StatCollector.translateToLocal("Tooltip_TwilightSword_07"));
        toolTip.add(StatCollector.translateToLocal("Tooltip_TwilightSword_08"));
        toolTip.add(StatCollector.translateToLocal("Tooltip_TwilightSword_09"));
        toolTip.add(StatCollector.translateToLocal("Tooltip_TwilightSword_10"));
    }
}
