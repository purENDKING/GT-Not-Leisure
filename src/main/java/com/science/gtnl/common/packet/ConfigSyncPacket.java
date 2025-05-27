package com.science.gtnl.common.packet;

import com.science.gtnl.config.MainConfig;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class ConfigSyncPacket implements IMessage {

    public int euEnhCore;
    public int euFuelRod;
    public double starCycleTime;
    public boolean renderStar, portalBoom, cheatOwner, deleteRecipe, debug, infinityExpl, machineLimit, aprilFool,
        bypass, outputChance;
    public double recipeChance;

    // no-arg ctor required
    public ConfigSyncPacket() {}

    public ConfigSyncPacket(MainConfig cfg) {
        this.euEnhCore = cfg.euEveryEnhancementCore;
        this.euFuelRod = cfg.euEveryDepletedExcitedNaquadahFuelRod;
        this.starCycleTime = cfg.secondsOfArtificialStarProgressCycleTime;
        this.renderStar = cfg.enableRenderDefaultArtificialStar;
        this.portalBoom = cfg.enablePortalToAlfheimBigBoom;
        this.cheatOwner = cfg.enableCheatRecipeWithOwner;
        this.deleteRecipe = cfg.enableDeleteRecipe;
        this.debug = cfg.enableDebugMode;
        this.infinityExpl = cfg.enableInfinitySwordExplosion;
        this.machineLimit = cfg.enableMachineAmpLimit;
        this.aprilFool = cfg.enableAprilFoolRecipe;
        this.bypass = cfg.enableInfinitySwordBypassMechanism;
        this.outputChance = cfg.enableRecipeOutputChance;
        this.recipeChance = cfg.recipeOutputChance;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(euEnhCore);
        buf.writeInt(euFuelRod);
        buf.writeDouble(starCycleTime);
        buf.writeBoolean(renderStar);
        buf.writeBoolean(portalBoom);
        buf.writeBoolean(cheatOwner);
        buf.writeBoolean(deleteRecipe);
        buf.writeBoolean(debug);
        buf.writeBoolean(infinityExpl);
        buf.writeBoolean(machineLimit);
        buf.writeBoolean(aprilFool);
        buf.writeBoolean(bypass);
        buf.writeBoolean(outputChance);
        buf.writeDouble(recipeChance);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        euEnhCore = buf.readInt();
        euFuelRod = buf.readInt();
        starCycleTime = buf.readDouble();
        renderStar = buf.readBoolean();
        portalBoom = buf.readBoolean();
        cheatOwner = buf.readBoolean();
        deleteRecipe = buf.readBoolean();
        debug = buf.readBoolean();
        infinityExpl = buf.readBoolean();
        machineLimit = buf.readBoolean();
        aprilFool = buf.readBoolean();
        bypass = buf.readBoolean();
        outputChance = buf.readBoolean();
        recipeChance = buf.readDouble();
    }

    public static class Handler implements IMessageHandler<ConfigSyncPacket, IMessage> {

        @Override
        public IMessage onMessage(ConfigSyncPacket msg, MessageContext ctx) {
            // apply immediately on CLIENT side
            MainConfig.euEveryEnhancementCore = msg.euEnhCore;
            MainConfig.euEveryDepletedExcitedNaquadahFuelRod = msg.euFuelRod;
            MainConfig.secondsOfArtificialStarProgressCycleTime = msg.starCycleTime;
            MainConfig.enableRenderDefaultArtificialStar = msg.renderStar;
            MainConfig.enablePortalToAlfheimBigBoom = msg.portalBoom;
            MainConfig.enableCheatRecipeWithOwner = msg.cheatOwner;
            MainConfig.enableDeleteRecipe = msg.deleteRecipe;
            MainConfig.enableDebugMode = msg.debug;
            MainConfig.enableInfinitySwordExplosion = msg.infinityExpl;
            MainConfig.enableMachineAmpLimit = msg.machineLimit;
            MainConfig.enableAprilFoolRecipe = msg.aprilFool;
            MainConfig.enableInfinitySwordBypassMechanism = msg.bypass;
            MainConfig.enableRecipeOutputChance = msg.outputChance;
            MainConfig.recipeOutputChance = msg.recipeChance;
            // Optionally: write out to the client config file
            MainConfig.reloadConfig(); // will save if changed
            return null;
        }
    }
}
