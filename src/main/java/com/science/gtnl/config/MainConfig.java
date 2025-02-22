package com.science.gtnl.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

// spotless:off
public class MainConfig {

    public static int EUEveryEnhancementCore = 1;// default 1024L * Integer.MAX_VALUE;
    public static int EUEveryDepletedExcitedNaquadahFuelRod = 2750000;
    public static double secondsOfArtificialStarProgressCycleTime = 6.4;
    public static boolean EnableRenderDefaultArtificialStar = true;
    public static boolean enablePortalToAlfheimBigBoom = true;
    public static boolean DEFAULT_BATCH_MODE = false;

    private static Configuration config;

    public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
            loadConfig();
        }
    }

    public static void reloadConfig() {
        if (config != null) {
            config.load();
            loadConfig();
        }
    }

    public static void loadConfig() {

        EUEveryEnhancementCore = config
            .get("EU Every Enhancement Core",
                "enable",
                EUEveryEnhancementCore,
                "Set the power generation of EU Every Enhancement Core")
            .getInt(EUEveryEnhancementCore);

        enablePortalToAlfheimBigBoom = config
            .get(
                "enable Portal To Alfheim Big Boom",
                "enable",
                enablePortalToAlfheimBigBoom,
                "Setting this to false will reduce the Portal To Alfheim explosion to little more then a tnt blast")
            .getBoolean(enablePortalToAlfheimBigBoom);

        EUEveryDepletedExcitedNaquadahFuelRod = config
            .get("EU Every Depleted Excited Naquadah FuelRod",
                "enable",
                EUEveryDepletedExcitedNaquadahFuelRod,
                "Set the power generation of EU Every Depleted Excited Naquadah FuelRod")
            .getInt(EUEveryDepletedExcitedNaquadahFuelRod);

        secondsOfArtificialStarProgressCycleTime = config
            .get("seconds Of Artificial Star Progress Cycle Time",
                "enable",
                secondsOfArtificialStarProgressCycleTime,
                "Set secondsOfArtificialStarProgressCycleTime running time")
            .getDouble(secondsOfArtificialStarProgressCycleTime);

        EnableRenderDefaultArtificialStar = config
            .get("Enable Render Default Artificial Star",
                "enable",
                EnableRenderDefaultArtificialStar,
                "Open RenderDefaultArtificialStar rendering")
            .getBoolean(EnableRenderDefaultArtificialStar);

        enablePortalToAlfheimBigBoom = config
            .get("Enable Portal To Alfheim Big Boom",
                "enable",
                enablePortalToAlfheimBigBoom,
                "Whether to turn on the Big Bang")
            .getBoolean(enablePortalToAlfheimBigBoom);

        DEFAULT_BATCH_MODE = config
            .get("DEFAULT BATCH MODE!!!",
                "enable",
                DEFAULT_BATCH_MODE,
                "DEFAULT_BATCH_MODE!!!")
            .getBoolean(DEFAULT_BATCH_MODE);

        if (config.hasChanged()) {
            config.save();
        }
    }
}
