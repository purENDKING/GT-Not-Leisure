package com.science.gtnl.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

// spotless:off
public class MainConfig {

    public static int EUEveryEnhancementCore = 1;
    public static int EUEveryDepletedExcitedNaquadahFuelRod = 2750000;
    public static double secondsOfArtificialStarProgressCycleTime = 6.4;
    public static boolean EnableRenderDefaultArtificialStar = true;
    public static boolean enablePortalToAlfheimBigBoom = true;
    public static boolean enableCheatRecipeWithOwner = false;

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
            .get(
                "EU Every Enhancement Core",
                "1",
                EUEveryEnhancementCore,
                "Set the power generation of EU Every Enhancement Core")
            .getInt(EUEveryEnhancementCore);

        EUEveryDepletedExcitedNaquadahFuelRod = config
            .get(
                "EU Every Depleted Excited Naquadah FuelRod",
                "2750000",
                EUEveryDepletedExcitedNaquadahFuelRod,
                "Set the power generation of EU Every Depleted Excited Naquadah FuelRod")
            .getInt(EUEveryDepletedExcitedNaquadahFuelRod);

        secondsOfArtificialStarProgressCycleTime = config
            .get(
                "Seconds Of Artificial Star Progress Cycle Time",
                "6.4",
                secondsOfArtificialStarProgressCycleTime,
                "Set secondsOfArtificialStarProgressCycleTime running time")
            .getDouble(secondsOfArtificialStarProgressCycleTime);

        enablePortalToAlfheimBigBoom = config
            .get(
                "Enable Portal To Alfheim Big Boom",
                "true",
                enablePortalToAlfheimBigBoom,
                "Setting this to false will reduce the Portal To Alfheim explosion to little more then a tnt blast")
            .getBoolean(enablePortalToAlfheimBigBoom);

        EnableRenderDefaultArtificialStar = config
            .get(
                "Enable Render Default Artificial Star",
                "true",
                EnableRenderDefaultArtificialStar,
                "Open RenderDefaultArtificialStar rendering")
            .getBoolean(EnableRenderDefaultArtificialStar);

        enableCheatRecipeWithOwner = config
            .get(
                "Enable Only Player Owner Cheat Recipe (Need 7 Mods)",
                "false",
                enableCheatRecipeWithOwner,
                "Add Debug Energy Hatch Recipe")
            .getBoolean(enablePortalToAlfheimBigBoom);

        if (config.hasChanged()) {
            config.save();
        }
    }
}
