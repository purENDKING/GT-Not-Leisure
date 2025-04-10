package com.science.gtnl.common.item.ReAvaritia;

import static com.science.gtnl.common.item.items.TwilightSword.rayTrace;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityDragonPart;
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
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
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
import com.science.gtnl.Utils.loadingUtils.SubtitleDisplay;
import com.science.gtnl.client.GTNLCreativeTabs;
import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.config.MainConfig;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.DamageSourceInfinitySword;
import fox.spiteful.avaritia.LudicrousText;
import fox.spiteful.avaritia.entity.EntityImmortalItem;
import fox.spiteful.avaritia.items.LudicrousItems;
import fox.spiteful.avaritia.render.ICosmicRenderItem;
import galaxyspace.core.entity.boss.EntityBlazeBoss;
import galaxyspace.core.entity.boss.EntityCrystalBoss;
import galaxyspace.core.entity.boss.EntityGhastBoss;
import galaxyspace.core.entity.boss.EntitySlimeBoss;
import galaxyspace.core.entity.boss.EntityWolfBoss;
import micdoodle8.mods.galacticraft.core.entities.EntitySkeletonBoss;
import micdoodle8.mods.galacticraft.planets.mars.entities.EntityCreeperBoss;
import vazkii.botania.common.entity.EntityDoppleganger;

public class InfinitySword extends ItemSword implements ICosmicRenderItem, SubtitleDisplay {

    public static boolean cancelBloodSword = false;
    public static boolean cancelDragonArmor = false;
    private static final long COOLDOWN = 1000; // 1000 ms = 1 second
    private static final ToolMaterial INFINITY = EnumHelper.addToolMaterial("INFINITY", 32, 9999, 9999F, 9999F, 200);
    private IIcon cosmicMask;
    private IIcon pommel;
    public static final DamageSource INFINITY_DAMAGE = new DamageSource("damage.gtnl.infinity").setExplosion()
        .setDamageBypassesArmor()
        .setDamageIsAbsolute()
        .setFireDamage()
        .setProjectile()
        .setDamageAllowedInCreativeMode()
        .setMagicDamage();

    public InfinitySword() {
        super(INFINITY);
        setUnlocalizedName("InfinitySword");
        setTextureName("reavaritia:InfinitySword");
        setCreativeTab(CreativeTabs.tabCombat);
        setCreativeTab(GTNLCreativeTabs.ReAvaritia);
        this.setMaxDamage(9999);
        MinecraftForge.EVENT_BUS.register(this);
        GTNLItemList.InfinitySword.set(new ItemStack(this, 1));
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase victim, EntityLivingBase player) {
        if (player.worldObj.isRemote) return true;

        if (victim instanceof EntityPlayer targetPlayer) {
            if (MainConfig.enableInfinitySwordBypassMechanism) cancelDragonArmor = player.isSneaking();

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
                targetPlayer.setHealth(targetPlayer.getHealth() - 20.0F);
            } else {
                // 将玩家的盔甲栏和主手的物品变成掉落物
                // dropInventoryItems(targetPlayer);

                applyInfinityDamage(victim, player);
            }
            return true;
        }

        if (victim instanceof EntityDoppleganger livingTarget) {
            try {
                Field playersField = EntityDoppleganger.class.getDeclaredField("playersWhoAttacked");
                playersField.setAccessible(true);

                List<String> playersWhoAttacked = (List<String>) playersField.get(livingTarget);
                String playerName = player.getCommandSenderName();
                if (!playersWhoAttacked.contains(playerName)) {
                    playersWhoAttacked.add(playerName);
                }
                livingTarget.recentlyHit = 100;
                DamageSource playerSource = DamageSource.causePlayerDamage((EntityPlayer) player);
                livingTarget.attackEntityFrom(playerSource, Float.POSITIVE_INFINITY);
                livingTarget.setHealth(0);
                livingTarget.onDeath(playerSource);
                livingTarget.setDead();
                livingTarget.worldObj.removeEntity(livingTarget);
                return true;

            } catch (NoSuchFieldException | IllegalAccessException ignored) {}
        }

        if (victim instanceof EntityDragon) {
            victim.attackEntityFrom(INFINITY_DAMAGE, Float.POSITIVE_INFINITY);
            victim.setDead();
            return true;
        }

        victim.recentlyHit = 100;
        applyInfinityDamage(victim, player);

        return true;
    }

    private void applyInfinityDamage(EntityLivingBase target, EntityLivingBase attacker) {

        if (target instanceof EntityDragon) {
            target.attackEntityFrom(INFINITY_DAMAGE, Float.POSITIVE_INFINITY);
            target.setDead();
        } else if (target instanceof EntityPlayer playerTarget && attacker.isSneaking()) {
            for (int i = 0; i < playerTarget.inventory.armorInventory.length; i++) {
                ItemStack armorStack = playerTarget.inventory.armorInventory[i];
                if (armorStack != null) {
                    if (armorStack.hasTagCompound()) {
                        if (armorStack.getTagCompound()
                            .hasKey("Energy")) {
                            armorStack.getTagCompound()
                                .setInteger("Energy", 0);
                        }
                        if (armorStack.getTagCompound()
                            .hasKey("charge")) {
                            armorStack.getTagCompound()
                                .setDouble("charge", 0.0);
                        }
                    }
                }
            }
            target.attackEntityFrom(getAdminKill(), Float.POSITIVE_INFINITY);
        }

        DamageSource playerSource = DamageSource.causePlayerDamage((EntityPlayer) attacker);
        target.attackEntityFrom(playerSource, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(INFINITY_DAMAGE, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(new DamageSourceInfinitySword(attacker), Float.POSITIVE_INFINITY);

        target.attackEntityFrom(DamageSource.anvil, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.fallingBlock, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.wither, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.magic, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.generic, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.fall, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.cactus, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.starve, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.drown, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.inWall, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.lava, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.inFire, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.onFire, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.outOfWorld, Float.POSITIVE_INFINITY);

        if (!(target instanceof EntityPlayer || target instanceof EntityCreeperBoss
            || target instanceof EntityBlazeBoss
            || target instanceof EntityCrystalBoss
            || target instanceof EntityGhastBoss
            || target instanceof EntitySlimeBoss
            || target instanceof EntityWolfBoss
            || target instanceof EntitySkeletonBoss)) {
            target.setHealth(0);
            target.onDeath(INFINITY_DAMAGE);
            target.setDead();
            target.worldObj.removeEntity(target);
            target.worldObj.updateEntity(target);
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            AxisAlignedBB area = player.boundingBox.expand(50.0, 50.0, 50.0);
            List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, area);

            for (Entity entity : entities) {
                if (entity instanceof EntityItem item) {

                    double centerX = player.posX;
                    double centerY = player.posY + (player.height / 2.0);
                    double centerZ = player.posZ;
                    item.setPosition(centerX, centerY, centerZ);

                }

                if (entity instanceof EntityXPOrb xpOrb) {

                    double centerX = player.posX;
                    double centerY = player.posY + (player.height / 2.0);
                    double centerZ = player.posZ;
                    xpOrb.setPosition(centerX, centerY, centerZ);
                }
            }
        }

        cancelDragonArmor = false;
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

            if (target instanceof EntityDoppleganger livingTarget) {
                try {
                    Field playersField = EntityDoppleganger.class.getDeclaredField("playersWhoAttacked");
                    playersField.setAccessible(true);

                    List<String> playersWhoAttacked = (List<String>) playersField.get(livingTarget);
                    String playerName = player.getCommandSenderName();
                    if (!playersWhoAttacked.contains(playerName)) {
                        playersWhoAttacked.add(playerName);
                    }
                    livingTarget.recentlyHit = 100;
                    DamageSource playerSource = DamageSource.causePlayerDamage(player);
                    livingTarget.attackEntityFrom(playerSource, Float.POSITIVE_INFINITY);
                    livingTarget.setHealth(0);
                    livingTarget.onDeath(playerSource);
                    livingTarget.setDead();
                    livingTarget.worldObj.removeEntity(livingTarget);

                } catch (NoSuchFieldException | IllegalAccessException ignored) {}
            }

            if (target instanceof EntityDragon) {
                target.attackEntityFrom(INFINITY_DAMAGE, Float.POSITIVE_INFINITY);
                target.setDead();
            }

            if (!player.isSneaking() && !(target instanceof IMob || target instanceof EntityPlayer)) continue;

            if (target instanceof EntityPlayer targetPlater) {
                if (player.isSneaking()) {
                    handlePlayerTarget(targetPlater, player, world);
                    return;
                }
            }

            if (target instanceof EntityLivingBase livingTarget) {
                livingTarget.recentlyHit = 100;
                applyDoubleSweepDamage(livingTarget, player);
            }
        }
    }

    private void handlePlayerTarget(EntityPlayer target, EntityPlayer attacker, World world) {
        if (LudicrousItems.isInfinite(target)) {
            target.attackEntityFrom(DamageSource.causePlayerDamage(attacker), 20.0F);

            float newHealth = target.getHealth() - 20.0F;
            if (newHealth <= 0) {
                target.setHealth(0);
                target.onDeath(DamageSource.causePlayerDamage(attacker));
            } else {
                target.setHealth(newHealth);
            }

            if (MainConfig.enableInfinitySwordExplosion) {
                world.newExplosion(target, target.posX, target.posY, target.posZ, 250.0F, true, true);
            }
        } else {
            applyDoubleSweepDamage(target, attacker);
        }
    }

    private void applyDoubleSweepDamage(EntityLivingBase target, EntityPlayer attacker) {
        if (!attacker.isSneaking() && target instanceof EntityPlayer) {
            return;
        }

        DamageSource playerSource = DamageSource.causePlayerDamage(attacker);
        target.attackEntityFrom(playerSource, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(INFINITY_DAMAGE, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(new DamageSourceInfinitySword(attacker), Float.POSITIVE_INFINITY);

        target.attackEntityFrom(DamageSource.anvil, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.fallingBlock, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.wither, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.magic, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.generic, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.fall, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.cactus, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.starve, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.drown, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.inWall, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.lava, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.inFire, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.onFire, Float.POSITIVE_INFINITY);
        target.attackEntityFrom(DamageSource.outOfWorld, Float.POSITIVE_INFINITY);

        target.onDeath(INFINITY_DAMAGE);
        if (!(target instanceof EntityPlayer || target instanceof EntityCreeperBoss
            || target instanceof EntityBlazeBoss
            || target instanceof EntityCrystalBoss
            || target instanceof EntityGhastBoss
            || target instanceof EntitySlimeBoss
            || target instanceof EntityWolfBoss
            || target instanceof EntitySkeletonBoss)) {
            target.setHealth(0);
            target.onDeath(INFINITY_DAMAGE);
            target.setDead();
            target.worldObj.removeEntity(target);
            target.worldObj.updateEntity(target);
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity victim) {
        if (player.worldObj.isRemote) return true;
        if (victim instanceof EntityLivingBase entity) {
            if (entity instanceof EntityPlayer targetPlayer) {
                cancelDragonArmor = false;
                if (MainConfig.enableInfinitySwordBypassMechanism) cancelDragonArmor = player.isSneaking();

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
                    targetPlayer.setHealth(targetPlayer.getHealth() - 20.0F);
                } else {
                    // 将玩家的盔甲栏和主手的物品变成掉落物
                    // dropInventoryItems(targetPlayer);

                    applyInfinityDamage(targetPlayer, player);
                }
                return true;
            }

            if (entity instanceof EntityDoppleganger livingTarget) {
                try {
                    Field playersField = EntityDoppleganger.class.getDeclaredField("playersWhoAttacked");
                    playersField.setAccessible(true);

                    List<String> playersWhoAttacked = (List<String>) playersField.get(livingTarget);
                    String playerName = player.getCommandSenderName();
                    if (!playersWhoAttacked.contains(playerName)) {
                        playersWhoAttacked.add(playerName);
                    }
                    livingTarget.recentlyHit = 100;
                    DamageSource playerSource = DamageSource.causePlayerDamage(player);
                    livingTarget.attackEntityFrom(playerSource, Float.POSITIVE_INFINITY);
                    livingTarget.setHealth(0);
                    livingTarget.onDeath(playerSource);
                    livingTarget.setDead();
                    livingTarget.worldObj.removeEntity(livingTarget);
                    return true;

                } catch (NoSuchFieldException | IllegalAccessException ignored) {}
            }

            if (entity instanceof EntityDragon) {
                entity.attackEntityFrom(INFINITY_DAMAGE, Float.POSITIVE_INFINITY);
                entity.setDead();
                return true;
            }

            entity.recentlyHit = 100;
            applyInfinityDamage(entity, player);
        }
        return true;
    }

    @SuppressWarnings("unused")
    private void dropInventoryItems(EntityPlayer player) {
        if (player.worldObj.isRemote) {
            return;
        }

        for (int i = 0; i < player.inventory.armorInventory.length; i++) {
            ItemStack armorStack = player.inventory.armorInventory[i];
            if (armorStack != null) {
                if (armorStack.hasTagCompound()) {
                    if (armorStack.getTagCompound()
                        .hasKey("Energy")) {
                        armorStack.getTagCompound()
                            .setInteger("Energy", 0);
                    }
                    if (armorStack.getTagCompound()
                        .hasKey("charge")) {
                        armorStack.getTagCompound()
                            .setDouble("charge", 0.0);
                    }
                }

                EntityItem entityItem = new EntityItem(
                    player.worldObj,
                    player.posX,
                    player.posY,
                    player.posZ,
                    armorStack.copy());
                player.worldObj.spawnEntityInWorld(entityItem);
                player.inventory.armorInventory[i] = null;
                player.inventory.markDirty();

                if (player.openContainer != null) {
                    player.openContainer.detectAndSendChanges();
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> toolTip,
        boolean advancedToolTips) {
        toolTip.add(TextLocalization.Tooltip_InfinitySword_00);
        toolTip.add(TextLocalization.Tooltip_InfinitySword_01);
        toolTip.add(TextLocalization.Tooltip_InfinitySword_02);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        super.onUpdate(stack, world, entity, slot, isSelected);
        if (entity instanceof EntityPlayer player) {
            if (player.getCurrentEquippedItem() == stack && player.swingProgress == 0.5F && !world.isRemote) {
                MovingObjectPosition rayTraceResult = rayTrace(player, false, true);
                cancelDragonArmor = false;

                if (rayTraceResult != null && rayTraceResult.entityHit != null) {
                    Entity hitEntity = rayTraceResult.entityHit;

                    if (hitEntity instanceof EntityLivingBase livingEntity) {
                        hitEntity(stack, livingEntity, player);
                        cancelDragonArmor = false;
                        if (MainConfig.enableInfinitySwordBypassMechanism) cancelDragonArmor = player.isSneaking();

                    } else if (hitEntity instanceof EntityDragonPart dragonPart) {
                        EntityDragon dragon = (EntityDragon) dragonPart.entityDragonObj;
                        hitEntity(stack, dragon, player);
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void showSubtitle(String message) {
        IChatComponent text = new ChatComponentText(EnumChatFormatting.WHITE + message);
        Minecraft.getMinecraft().ingameGUI.func_110326_a(text.getFormattedText(), true);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.entity instanceof EntityPlayer player) {
            if (player.getHeldItem() != null && player.getHeldItem()
                .getItem() == this) {
                if (MainConfig.enableInfinitySwordBypassMechanism) cancelBloodSword = true;
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

                player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 1200, 255));
                if (player.posY < 0) {
                    player.setPositionAndUpdate(player.posX, 255, player.posZ);
                }
            } else {
                cancelBloodSword = false;
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

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPlayerAttacked(LivingAttackEvent event) {
        if (event.entity instanceof EntityPlayer player) {
            if (player.isUsingItem() && player.getHeldItem() != null
                && player.getHeldItem()
                    .getItem() == this) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPlayerDeath(LivingDeathEvent event) {
        if (event.entity instanceof EntityPlayer player) {
            if (player.getHeldItem() != null && player.getHeldItem()
                .getItem() == this) {
                event.setCanceled(true);
                player.setHealth(player.getMaxHealth());
            }
        }
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
        if (event.entity instanceof EntityPlayer player) {
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

    public static DamageSource getAdminKill() {
        try {
            Class<?> clazz = Class.forName("com.brandon3055.draconicevolution.common.items.armor.CustomArmorHandler");
            Field field = clazz.getDeclaredField("ADMIN_KILL");
            field.setAccessible(true);
            return (DamageSource) field.get(null);
        } catch (Exception e) {
            return null;
        }
    }
}
