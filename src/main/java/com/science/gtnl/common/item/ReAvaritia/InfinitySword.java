package com.science.gtnl.common.item.ReAvaritia;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import org.lwjgl.opengl.GL11;

import com.science.gtnl.Utils.item.TextLocalization;
import com.science.gtnl.client.GTNLCreativeTabs;
import com.science.gtnl.config.MainConfig;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.DamageSourceInfinitySword;
import fox.spiteful.avaritia.LudicrousText;
import fox.spiteful.avaritia.achievements.Achievements;
import fox.spiteful.avaritia.entity.EntityImmortalItem;
import fox.spiteful.avaritia.items.LudicrousItems;
import fox.spiteful.avaritia.render.ICosmicRenderItem;

public class InfinitySword extends ItemSword implements ICosmicRenderItem {

    private static final long COOLDOWN = 1000; // 1000 ms = 1 second
    private static final ToolMaterial INFINITY = EnumHelper.addToolMaterial("INFINITY", 32, 9999, 9999F, 9999F, 200);
    private IIcon cosmicMask;
    private IIcon pommel;

    public InfinitySword() {
        super(INFINITY);
        setUnlocalizedName("InfinitySword");
        setTextureName("reavaritia:InfinitySword");
        setCreativeTab(CreativeTabs.tabCombat);
        setCreativeTab(GTNLCreativeTabs.ReAvaritia);
        this.setMaxDamage(9999);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase victim, EntityLivingBase attacker) {
        if (attacker.worldObj.isRemote) return true;

        if (victim instanceof EntityPlayer) {
            EntityPlayer targetPlayer = (EntityPlayer) victim;

            boolean wearingInfinityArmor = false;
            for (ItemStack armorStack : targetPlayer.inventory.armorInventory) {
                if (armorStack != null && (armorStack.getItem() == LudicrousItems.infinity_helm
                    || armorStack.getItem() == LudicrousItems.infinity_armor
                    || armorStack.getItem() == LudicrousItems.infinity_pants
                    || armorStack.getItem() == LudicrousItems.infinity_shoes)) {
                    wearingInfinityArmor = true;
                    break;
                }
            }

            if (wearingInfinityArmor) {
                targetPlayer.setHealth(targetPlayer.getHealth() - 4.0F);
                return true;
            } else {

                applyInfinityDamage(victim, attacker);
            }
        }
        return true;
    }

    @SideOnly(Side.CLIENT)
    private void showSubtitle(String message) {
        IChatComponent text = new ChatComponentText(EnumChatFormatting.WHITE + message);
        Minecraft.getMinecraft().ingameGUI.func_110326_a(text.getFormattedText(), true);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            if (player.getHeldItem() != null && player.getHeldItem()
                .getItem() == this) {
                if (player.isBurning()) {
                    player.extinguish();
                }

                List<Integer> badEffects = new ArrayList<>();
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    if (isBadEffect(effect)) {
                        badEffects.add(effect.getPotionID());
                    }
                }

                for (int potionID : badEffects) {
                    player.removePotionEffect(potionID);
                }

                player.setHealth(player.getMaxHealth());
                player.getFoodStats()
                    .addStats(20, 20.0F);

                player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 200, 255));
                if (player.posY < 0) {
                    player.setPositionAndUpdate(player.posX, 255, player.posZ);
                }
            }
        }
    }

    private boolean isBadEffect(PotionEffect effect) {
        if (FMLCommonHandler.instance()
            .getSide()
            .isClient()) {
            return isBadEffectClient(effect);
        } else {
            return isBadEffectServer(effect);
        }
    }

    @SideOnly(Side.CLIENT)
    private boolean isBadEffectClient(PotionEffect effect) {
        return net.minecraft.potion.Potion.potionTypes[effect.getPotionID()].isBadEffect();
    }

    private boolean isBadEffectServer(PotionEffect effect) {
        int potionID = effect.getPotionID();
        return potionID >= 1 && potionID < 256;
    }

    private void applyInfinityDamage(EntityLivingBase target, EntityLivingBase attacker) {
        DamageSource src = new DamageSourceInfinitySword(attacker).setDamageBypassesArmor();
        target.attackEntityFrom(src, Float.POSITIVE_INFINITY);
        target.setHealth(0);
        target.onDeath(src);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!player.isSneaking() && !world.isRemote) { // 确保代码在服务端运行
            AxisAlignedBB area = player.boundingBox.expand(50.0, 50.0, 50.0);
            List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, area);

            for (Entity entity : entities) {
                // 处理掉落物
                if (entity instanceof EntityItem) {
                    EntityItem item = (EntityItem) entity;

                    double centerX = player.posX;
                    double centerY = player.posY + (player.height / 2.0);
                    double centerZ = player.posZ;
                    item.setPosition(centerX, centerY, centerZ);

                }

                // 处理经验球
                if (entity instanceof EntityXPOrb) {
                    EntityXPOrb xpOrb = (EntityXPOrb) entity;

                    // 将经验球传送到玩家中心
                    double centerX = player.posX;
                    double centerY = player.posY + (player.height / 2.0);
                    double centerZ = player.posZ;
                    xpOrb.setPosition(centerX, centerY, centerZ);
                }
            }
        }

        // 原有的右键点击逻辑
        handleSwordAttack(stack, world, player);
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }

    private void handleSwordAttack(ItemStack stack, World world, EntityPlayer player) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null) {
            nbt = new NBTTagCompound();
            stack.setTagCompound(nbt);
        }
        long lastUsed = nbt.getLong("LastUsed");

        if (System.currentTimeMillis() - lastUsed < COOLDOWN) {
            if (world.isRemote) {
                long remainingTime = (COOLDOWN - (System.currentTimeMillis() - lastUsed)) / 1000;
                showSubtitle(
                    TextLocalization.Tooltip_InfinitySword_Aura_00 + remainingTime
                        + TextLocalization.Tooltip_InfinitySword_Aura_01);
            }
            return;
        }

        if (!world.isRemote) {
            executeSweepAttack(world, player);
        }

        nbt.setLong("LastUsed", System.currentTimeMillis());
    }

    private void executeSweepAttack(World world, EntityPlayer player) {
        AxisAlignedBB area = player.boundingBox.expand(100.0, 100.0, 100.0);
        List<Entity> targets = world.getEntitiesWithinAABBExcludingEntity(player, area);

        for (Entity target : targets) {
            if (target == player) continue;

            boolean isSneaking = player.isSneaking();
            boolean isHostile = target instanceof IMob;

            if (!isSneaking && !(isHostile || target instanceof EntityPlayer)) continue;

            if (target instanceof EntityPlayer) {
                if (isSneaking) {
                    handlePlayerTarget((EntityPlayer) target, player, world);
                    continue;
                }
            }

            if (target instanceof EntityLivingBase) {
                applyDoubleSweepDamage((EntityLivingBase) target, player);
            }
        }
    }

    private void handlePlayerTarget(EntityPlayer target, EntityPlayer attacker, World world) {
        if (LudicrousItems.isInfinite(target)) {
            target.attackEntityFrom(DamageSource.causePlayerDamage(attacker), 4.0F);

            float newHealth = target.getHealth() - 4.0F;
            if (newHealth <= 0) {
                target.setHealth(0);
                target.onDeath(DamageSource.causePlayerDamage(attacker));
            } else {
                target.setHealth(newHealth);
            }

            if (MainConfig.enableInfinitySwordExplosion) {
                world.createExplosion(null, target.posX, target.posY, target.posZ, 250.0F, true);
            }
        } else {
            applyDoubleSweepDamage(target, attacker);
        }
    }

    private void applyDoubleSweepDamage(EntityLivingBase target, EntityPlayer attacker) {
        DamageSource playerSource = DamageSource.causePlayerDamage(attacker);
        target.attackEntityFrom(playerSource, Float.POSITIVE_INFINITY);

        DamageSource originalSource = new DamageSourceInfinitySword(attacker).setDamageBypassesArmor();
        target.attackEntityFrom(originalSource, Float.POSITIVE_INFINITY);

        target.setHealth(0);
        target.onDeath(originalSource);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerAttacked(LivingAttackEvent event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            if (player.isUsingItem() && player.getHeldItem() != null
                && player.getHeldItem()
                    .getItem() == this) {
                event.setCanceled(true); // 取消任何伤害
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(LivingDeathEvent event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            if (player.getHeldItem() != null && player.getHeldItem()
                .getItem() == this) {
                event.setCanceled(true); // 取消死亡事件
                player.setHealth(player.getMaxHealth()); // 设置玩家血量为最大值
            }
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (!entity.worldObj.isRemote && entity instanceof EntityPlayer) {
            EntityPlayer victim = (EntityPlayer) entity;
            if (victim.capabilities.isCreativeMode && !victim.isDead
                && victim.getHealth() > 0
                && !LudicrousItems.isInfinite(victim)) {
                victim.func_110142_aN()
                    .func_94547_a(new DamageSourceInfinitySword(player), victim.getHealth(), victim.getHealth());
                victim.setHealth(0);
                victim.onDeath(new EntityDamageSource("infinity", player));
                player.addStat(Achievements.creative_kill, 1);
                return true;
            }
        }
        return false;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumHelper.addRarity("COSMIC", EnumChatFormatting.RED, "Cosmic");
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        super.setDamage(stack, 0);
    }

    @Override
    public IIcon getMaskTexture(ItemStack stack, EntityPlayer player) {
        return cosmicMask;
    }

    @Override
    public float getMaskMultiplier(ItemStack stack, EntityPlayer player) {
        return 1.0f;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister ir) {
        super.registerIcons(ir);

        this.cosmicMask = ir.registerIcon("reavaritia:InfinitySword_Mask");
        this.pommel = ir.registerIcon("reavaritia:InfinitySword_Pommel");
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        if (pass == 1) {
            return this.pommel;
        }

        return super.getIcon(stack, pass);
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        return new EntityImmortalItem(world, location, itemstack);
    }

    @Override
    public boolean hasEffect(ItemStack par1ItemStack, int pass) {
        return false;
    }

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        if (event.itemStack.getItem() instanceof InfinitySword) {
            for (int x = 0; x < event.toolTip.size(); x++) {
                if (event.toolTip.get(x)
                    .contains(StatCollector.translateToLocal("attribute.name.generic.attackDamage"))
                    || event.toolTip.get(x)
                        .contains(StatCollector.translateToLocal("Attack Damage"))) {
                    event.toolTip.set(
                        x,
                        EnumChatFormatting.BLUE + "+"
                            + LudicrousText.makeFabulous(TextLocalization.Damage_InfinitySword)
                            + " "
                            + EnumChatFormatting.BLUE
                            + StatCollector.translateToLocal("attribute.name.generic.attackDamage"));
                    return;
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void interceptLudicrousEvents(LivingDeathEvent event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            if (LudicrousItems.isInfinite(player)) {
                event.setCanceled(true);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onRenderPlayer(RenderPlayerEvent.Pre event) {
        if (MainConfig.enableRenderInfinitySwordSpecial) {
            EntityPlayer player = event.entityPlayer;
            ItemStack heldItem = player.getHeldItem();

            if (heldItem != null && heldItem.getItem() == this) {
                float currentTime = (player.worldObj.getTotalWorldTime() + event.partialRenderTick) / 20.0F;

                float rotationSpeed = 1800.0F;

                float rotationAngle = (currentTime * rotationSpeed) % 360;

                GL11.glPushMatrix();
                GL11.glRotatef(rotationAngle, 0.0F, 1.0F, 0.0F);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderPlayerPost(RenderPlayerEvent.Post event) {
        if (MainConfig.enableRenderInfinitySwordSpecial) {
            EntityPlayer player = event.entityPlayer;
            ItemStack heldItem = player.getHeldItem();

            if (heldItem != null && heldItem.getItem() == this) {
                GL11.glPopMatrix();

                ModelBiped playerModel = event.renderer.modelBipedMain;
                playerModel.bipedHead.rotateAngleX = 0;
            }
        }
    }
}
