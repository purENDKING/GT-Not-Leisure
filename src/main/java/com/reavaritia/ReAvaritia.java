package com.reavaritia;

import static com.reavaritia.ReAvaritia.*;

import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.reavaritia.common.ItemLoader;
import com.reavaritia.common.block.BlockRegister;
import com.reavaritia.common.block.GooeyHandler;
import com.reavaritia.common.item.BlazeSword;
import com.reavaritia.common.item.ChronarchsClock;
import com.reavaritia.common.item.ToolEvents;
import com.science.gtnl.Mods;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(
    modid = MODID,
    version = VERSION,
    name = MODNAME,
    dependencies = "required-before:sciencenotleisure;",
    acceptedMinecraftVersions = "1.7.10")
public class ReAvaritia {

    @Mod.Instance(Mods.Names.REAVARITIA)
    public static ReAvaritia instance;

    public static final String MODID = Mods.Names.REAVARITIA;
    public static final String MODNAME = "ReAvaritia";
    public static final String VERSION = "1.0.0";
    public static final String Arthor = "HFstudio";
    public static final String RESOURCE_ROOT_ID = Mods.Names.REAVARITIA;
    public static final Logger LOG = LogManager.getLogger(MODID);

    @SidedProxy(clientSide = "com.reavaritia.ClientProxy", serverSide = "com.reavaritia.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        proxy.makeThingsPretty();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GooeyHandler());
        MinecraftForge.EVENT_BUS.register(new ToolEvents());
    }

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        BlockRegister.registryBlocks();
        ItemLoader.registerItems();
        BlockRegister.registryAnotherData();
        BlazeSword.registerEntity();
        ChronarchsClock.registerEntity();
    }
}
