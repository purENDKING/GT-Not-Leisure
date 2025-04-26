package com.gtnlhandler;

import static com.gtnlhandler.GTNLHandler.MODID;
import static com.gtnlhandler.GTNLHandler.MODNAME;

import com.science.gtnl.loader.MaterialLoader;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(
    modid = MODID,
    version = "1.0",
    name = MODNAME,
    dependencies = "required-before:dreamcraft;",
    acceptedMinecraftVersions = "1.7.10")

public class GTNLHandler {

    @Mod.Instance("gtnlhandler")
    public static GTNLHandler instance;

    public static final String MODID = "gtnlhandler";
    public static final String MODNAME = "GTNLRecipeHandler";
    public static final String VERSION = "1.0";
    public static final String Arthor = "HFstudio";

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MaterialLoader.loadPreInit();
        // RecipeLoader.loadRecipesCompleteInit();
    }
}
