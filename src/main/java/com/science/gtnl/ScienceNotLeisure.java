package com.science.gtnl;

import static com.science.gtnl.ScienceNotLeisure.MODID;
import static com.science.gtnl.ScienceNotLeisure.MODNAME;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.science.gtnl.Utils.LanguageManager;
import com.science.gtnl.Utils.LoginMessage;
import com.science.gtnl.Utils.item.GiveCommandMonitor;
import com.science.gtnl.common.block.Casings.Special.CrushingWheelsEventHandler;
import com.science.gtnl.common.block.ReAvaritia.GooeyHandler;
import com.science.gtnl.common.block.blocks.playerDoll.PlayerDollWaila;
import com.science.gtnl.common.command.CommandGiveCountBook;
import com.science.gtnl.common.command.CommandReloadConfig;
import com.science.gtnl.common.item.ReAvaritia.BlazeSword;
import com.science.gtnl.common.item.ReAvaritia.ToolEvents;
import com.science.gtnl.common.machine.hatch.SuperCraftingInputHatchME;
import com.science.gtnl.common.machine.multiMachineClasses.EdenGardenManager.EIGBucketLoader;
import com.science.gtnl.common.machine.multiblock.MeteorMiner;
import com.science.gtnl.config.ClientEventHandler;
import com.science.gtnl.config.MainConfig;
import com.science.gtnl.config.ServerEventHandler;
import com.science.gtnl.loader.MachineLoader;
import com.science.gtnl.loader.MaterialLoader;
import com.science.gtnl.loader.RecipeLoader;
import com.science.gtnl.loader.ScriptLoader;

import appeng.api.AEApi;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

// after
@Mod(
    modid = MODID,
    version = Tags.VERSION,
    name = MODNAME,
    dependencies = "required-after:IC2;" + "required-after:structurelib;"
        + "required-after:Avaritia;"
        + "required-after:eternalsingularity;"
        + "required-after:AWWayofTime;"
        + "required-after:BloodArsenal;"
        + "required-after:modularui;"
        + "required-after:Botania;"
        + "after:GalacticraftCore;"
        + "required-after:bartworks;"
        + "after:miscutils;"
        + "required-after:dreamcraft;"
        + "after:GalacticraftMars;"
        + "required-after:gregtech;"
        + "after:TwistSpaceTechnology;"
        + "after:GalacticraftPlanets",
    acceptedMinecraftVersions = "1.7.10")

public class ScienceNotLeisure {

    @Mod.Instance("ScienceNotLeisure")
    public static ScienceNotLeisure instance;
    public static final String MODID = "ScienceNotLeisure";
    public static final String MODNAME = "GTNotLeisure";
    public static final String VERSION = Tags.VERSION;
    public static final String Arthor = "HFstudio";
    public static final String RESOURCE_ROOT_ID = "sciencenotleisure";
    public static final Logger LOG = LogManager.getLogger(MODID);

    public static SimpleNetworkWrapper network;
    public static Configuration config;

    @SidedProxy(clientSide = "com.science.gtnl.ClientProxy", serverSide = "com.science.gtnl.CommonProxy")
    public static CommonProxy proxy;

    static {
        File configDir = new File("config/GTNotLeisure");
        if (!configDir.exists()) {
            configDir.mkdirs();
        }

        File mainConfigFile = new File(configDir, "main.cfg");
        MainConfig.init(mainConfigFile);
    }

    @Mod.EventHandler
    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        proxy.makeThingsPretty();
        PlayerDollWaila.init();
        MachineLoader.registerGlasses();

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GooeyHandler());
        MinecraftForge.EVENT_BUS.register(new ToolEvents());
        MinecraftForge.EVENT_BUS.register(new CrushingWheelsEventHandler());
        MinecraftForge.EVENT_BUS.register(new GiveCommandMonitor());
        FMLCommonHandler.instance()
            .bus()
            .register(new LoginMessage());
    }

    @Mod.EventHandler
    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        EIGBucketLoader.LoadEIGBuckets();
        MaterialLoader.loadPostInit();
        MachineLoader.run();
        MachineLoader.loadMachinesPostInit();
        MeteorMiner.initializeBlacklist();

        AEApi.instance()
            .registries()
            .interfaceTerminal()
            .register(SuperCraftingInputHatchME.class);
    }

    @Mod.EventHandler
    public void completeInit(FMLLoadCompleteEvent event) {
        ScriptLoader.run();
        RecipeLoader.loadRecipesCompleteInit();
    }

    @Mod.EventHandler
    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
        event.registerServerCommand(new CommandReloadConfig());
        event.registerServerCommand(new CommandGiveCountBook());
    }

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
        proxy.registerMessages();
        proxy.preInit(event);
        BlazeSword.registerEntity();
        MaterialLoader.loadPreInit();
        LanguageManager.init();

        FMLCommonHandler.instance()
            .bus()
            .register(new ServerEventHandler());
        FMLCommonHandler.instance()
            .bus()
            .register(new ClientEventHandler());
    }
}
