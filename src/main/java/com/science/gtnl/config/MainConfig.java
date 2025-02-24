package com.science.gtnl.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

// spotless:off
public class MainConfig {

    public static int EUEveryEnhancementCore = 1;
    public static int EUEveryDepletedExcitedNaquadahFuelRod = 2750000;
    public static double secondsOfArtificialStarProgressCycleTime = 6.4;
    public static boolean enableRenderDefaultArtificialStar = true;
    public static boolean enablePortalToAlfheimBigBoom = true;
    public static boolean enableCheatRecipeWithOwner = false;
    public static boolean enableRenderInfinitySwordSpecial = true;
    public static boolean enableDeleteRecipe = true;
    public static boolean enableDebugMode = false;

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
                "EUEveryEnhancementCore",
                EUEveryEnhancementCore,
                "Set the power generation of EU Every Enhancement Core")
            .getInt(EUEveryEnhancementCore);

        EUEveryDepletedExcitedNaquadahFuelRod = config
            .get(
                "EU Every Depleted Excited Naquadah FuelRod",
                "EUEveryDepletedExcitedNaquadahFuelRod",
                EUEveryDepletedExcitedNaquadahFuelRod,
                "Set the power generation of EU Every Depleted Excited Naquadah FuelRod")
            .getInt(EUEveryDepletedExcitedNaquadahFuelRod);

        secondsOfArtificialStarProgressCycleTime = config
            .get(
                "Seconds Of Artificial Star Progress Cycle Time",
                "secondsOfArtificialStarProgressCycleTime",
                secondsOfArtificialStarProgressCycleTime,
                "Set secondsOfArtificialStarProgressCycleTime running time")
            .getDouble(secondsOfArtificialStarProgressCycleTime);

        enablePortalToAlfheimBigBoom = config
            .get(
                "Enable Portal To Alfheim Big Boom",
                "enable",
                enablePortalToAlfheimBigBoom,
                "Setting this to false will reduce the Portal To Alfheim explosion to little more then a tnt blast")
            .getBoolean(enablePortalToAlfheimBigBoom);

        enableRenderDefaultArtificialStar = config
            .get(
                "Enable Render Default Artificial Star",
                "enable",
                enableRenderDefaultArtificialStar,
                "Open RenderDefaultArtificialStar rendering")
            .getBoolean(enableRenderDefaultArtificialStar);

        enableCheatRecipeWithOwner = config
            .get(
                "Enable Only Player Owner Cheat Recipe (Need 7 Mods)",
                "enable",
                enableCheatRecipeWithOwner,
                "Add Debug Energy Hatch Recipe")
            .getBoolean(enablePortalToAlfheimBigBoom);

        enableRenderInfinitySwordSpecial = config
            .get(
                "Enable Infinity Sword Special Render (like a hack)",
                "enable",
                enableRenderInfinitySwordSpecial,
                "Enable Player Render")
            .getBoolean(enableRenderInfinitySwordSpecial);

        enableDeleteRecipe = config
            .get("Enable Delete Vanilla GTNH Recipe", "enable", enableDeleteRecipe, "Enable Delete Recipe")
            .getBoolean(enableDeleteRecipe);

        enableDebugMode = config.get("Enable Debug Print Log", "enable", enableDebugMode, "Enable Debug Print Log")
            .getBoolean(enableDebugMode);

        if (config.hasChanged()) {
            config.save();
        }
    }
}
