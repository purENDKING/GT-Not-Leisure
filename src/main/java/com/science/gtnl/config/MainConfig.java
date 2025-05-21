package com.science.gtnl.config;

import java.io.File;
import java.time.LocalDate;

import net.minecraftforge.common.config.Configuration;

// spotless:off
public class MainConfig {

    // Machine
    public static boolean enableRecipeOutputChance = true;
    public static boolean enableMachineAmpLimit = true;
    public static double recipeOutputChance = 2.5;

    public static int meteorMinerMaxBlockPerCycle = 1;
    public static int meteorMinerMaxRowPerCycle = 1;

    public static int euEveryEnhancementCore = 1;
    public static int euEveryDepletedExcitedNaquadahFuelRod = 2750000;
    public static double secondsOfArtificialStarProgressCycleTime = 6.4;
    public static boolean enableRenderDefaultArtificialStar = true;

    public static boolean enablePortalToAlfheimBigBoom = true;

    public static boolean enableEternalGregTechWorkshopSpiralRender = false;

    // Recipe
    public static boolean enableDeleteRecipe = true;
    public static boolean enableAprilFoolRecipe = false;
    public static boolean enableCheatRecipeWithOwner = false;

    // Tickrate
    public static float defaultTickrate = 20.0f;
    public static float minTickrate = 0.1f;
    public static float maxTickrate = 1000f;
    public static boolean showTickrateMessages = true;

    // Player Doll
    public static boolean enableCustomPlayerDoll = true;

    // Infinity Sword
    public static boolean enableInfinitySwordBypassMechanism = true;
    public static boolean enableInfinitySwordExplosion = true;
    public static boolean enableRenderInfinitySwordSpecial = true;

    // Chronarch's Clock
    public static int chronarchsClockRadius = 6;
    public static int chronarchsClockSpeedMultiplier = 100;
    public static int chronarchsClockDurationTicks = 200;
    public static int chronarchsClockCooldown = 200;

    // BloodMagic
    public static int meteorParadigmChunkSize = 1024;
    public static int meteorParadigmBatchUpdateInterval = 2048;

    // NotEnoughItems
    public static boolean enableSpecialCheatIcon = false;
    public static int specialIconType = 0;

    // Debug
    public static boolean enableDebugMode = false;

    private static Configuration config;

    public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
            loadConfig();
        }

        LocalDate today = LocalDate.now();
        if (today.getMonthValue() == 4 && today.getDayOfMonth() == 1) {
            enableAprilFoolRecipe = true;
        }

    }

    public static void reloadConfig() {
        if (config != null) {
            config.load();
            loadConfig();
        }
    }

    public static void loadConfig() {
        // Machine
        enableRecipeOutputChance = config
            .get("Machine", "enable", enableRecipeOutputChance, "Enable Output Change Function")
            .getBoolean(enableRecipeOutputChance);

        enableMachineAmpLimit = config
            .get("Machine", "enableLaserHatch", enableMachineAmpLimit, "Enable Machine Can't Use Laser Hatch")
            .getBoolean(enableMachineAmpLimit);

        recipeOutputChance = config
            .get("Machine", "RecipeChanceOutput", recipeOutputChance, "Change Recipe Item Output, like QFT")
            .getDouble(recipeOutputChance);

        meteorMinerMaxBlockPerCycle = config
            .get(
                "Meteor Miner",
                "MaxBlockCount",
                meteorMinerMaxBlockPerCycle,
                "Set the Meteor Miner how many every cycle break a block")
            .getInt(meteorMinerMaxBlockPerCycle);

        meteorMinerMaxRowPerCycle = config
            .get(
                "Meteor Miner",
                "MaxRawCount",
                meteorMinerMaxRowPerCycle,
                "Set the Meteor Miner how many every cycle break row blocks")
            .getInt(meteorMinerMaxRowPerCycle);

        euEveryEnhancementCore = config
            .get(
                "Artificial Star",
                "EUEveryEnhancementCore",
                euEveryEnhancementCore,
                "Set the power generation of EU Every Enhancement Core")
            .getInt(euEveryEnhancementCore);

        euEveryDepletedExcitedNaquadahFuelRod = config
            .get(
                "Artificial Star",
                "EUEveryDepletedExcitedNaquadahFuelRod",
                euEveryDepletedExcitedNaquadahFuelRod,
                "Set the power generation of EU Every Depleted Excited Naquadah FuelRod")
            .getInt(euEveryDepletedExcitedNaquadahFuelRod);

        secondsOfArtificialStarProgressCycleTime = config
            .get(
                "Artificial Star",
                "secondsOfArtificialStarProgressCycleTime",
                secondsOfArtificialStarProgressCycleTime,
                "Set secondsOfArtificialStarProgressCycleTime running time")
            .getDouble(secondsOfArtificialStarProgressCycleTime);

        enableRenderDefaultArtificialStar = config
            .get(
                "Artificial Star",
                "EnableDefaultRender",
                enableRenderDefaultArtificialStar,
                "Open RenderDefaultArtificialStar rendering")
            .getBoolean(enableRenderDefaultArtificialStar);

        enableEternalGregTechWorkshopSpiralRender = config
            .get(
                "Eternal GregTech Workshop",
                "spiralRender",
                enableEternalGregTechWorkshopSpiralRender,
                "Enable Eternal GregTech Workshop Spiral Render, like DNA")
            .getBoolean(enableEternalGregTechWorkshopSpiralRender);

        enablePortalToAlfheimBigBoom = config
            .get(
                "Portal To Alfheim",
                "bigBoom",
                enablePortalToAlfheimBigBoom,
                "Setting this to false will reduce the Portal To Alfheim explosion to little more then a tnt blast")
            .getBoolean(enablePortalToAlfheimBigBoom);

        // Recipe
        enableDeleteRecipe = config.get("Recipe", "enable", enableDeleteRecipe, "Enable Delete Recipe")
            .getBoolean(enableDeleteRecipe);

        enableAprilFoolRecipe = config
            .get("Recipe", "enable", enableAprilFoolRecipe, "Force enable April Fool's recipe")
            .getBoolean(enableAprilFoolRecipe);

        enableCheatRecipeWithOwner = config
            .get("Recipe", "enable", enableCheatRecipeWithOwner, "Enable Only Player Owner Cheat Recipe (Need 7 Mods)")
            .getBoolean(enableCheatRecipeWithOwner);

        // Tick Rate
        defaultTickrate = (float) config
            .get(
                "Tickrate",
                "Default",
                defaultTickrate,
                "Default tickrate. The game will always initialize with this value.")
            .getDouble(defaultTickrate);

        minTickrate = (float) config
            .get(
                "Tickrate",
                "Minimum",
                minTickrate,
                "Minimum tickrate from servers. Prevents really low tickrate values.")
            .getDouble(minTickrate);

        maxTickrate = (float) config
            .get(
                "Tickrate",
                "Maximum",
                maxTickrate,
                "Maximum tickrate from servers. Prevents really high tickrate values.")
            .getDouble(maxTickrate);

        showTickrateMessages = config
            .get(
                "Tickrate",
                "show-messages",
                showTickrateMessages,
                "If it will show log messages in the console and the game")
            .getBoolean(showTickrateMessages);

        // Player Doll
        enableCustomPlayerDoll = config
            .get("Player Doll", "enable", enableCustomPlayerDoll, "Enable Custom Player Skin for Player Doll")
            .getBoolean(enableCustomPlayerDoll);

        // Infinity Sword
        enableInfinitySwordBypassMechanism = config
            .get(
                "Infinity Sword",
                "enableBypass",
                enableInfinitySwordBypassMechanism,
                "Enable Infinity Sword bypass against Blood Sword and Draconic Armor")
            .getBoolean(enableInfinitySwordBypassMechanism);

        enableInfinitySwordExplosion = config
            .get(
                "Infinity Sword",
                "enableExplosion",
                enableInfinitySwordExplosion,
                "Enable when Infinity Sword hit Infinity Suit create Explosion")
            .getBoolean(enableInfinitySwordExplosion);

        enableRenderInfinitySwordSpecial = config
            .get("Infinity Sword", "enable", enableRenderInfinitySwordSpecial, "Enable Player Special Render")
            .getBoolean(enableRenderInfinitySwordSpecial);

        // Chronarch's Clock
        chronarchsClockRadius = config
            .get("Chronarch's Clock", "radius", chronarchsClockRadius, "Effective radius in blocks")
            .getInt(chronarchsClockRadius);

        chronarchsClockSpeedMultiplier = config
            .get(
                "Chronarch's Clock",
                "speedMultiplier",
                chronarchsClockSpeedMultiplier,
                "Speed multiplier for the clock")
            .getInt(chronarchsClockSpeedMultiplier);

        chronarchsClockDurationTicks = config
            .get(
                "Chronarch's Clock",
                "durationTicks",
                chronarchsClockDurationTicks,
                "Duration of the clock's effect in ticks")
            .getInt(chronarchsClockDurationTicks);

        chronarchsClockCooldown = config
            .get("Chronarch's Clock", "Cooldown", chronarchsClockCooldown, "Change Chronarchs Clock Cooldown")
            .getInt(chronarchsClockCooldown);

        // Blood Magic
        meteorParadigmChunkSize = config
            .get(
                "Meteor Paradigm",
                "ChunkSize",
                meteorParadigmChunkSize,
                "Set the chunk size for meteor paradigm operations (default: 1024)")
            .getInt(meteorParadigmChunkSize);

        meteorParadigmBatchUpdateInterval = config
            .get(
                "Meteor Paradigm",
                "BatchUpdateInterval",
                meteorParadigmBatchUpdateInterval,
                "Set the batch update interval for meteor paradigm operations (default: 2048)")
            .getInt(meteorParadigmBatchUpdateInterval);

        // Not Enough Items
        enableSpecialCheatIcon = config
            .get(
                "NotEnoughItems",
                "EnableSpecialCheatIcon",
                enableSpecialCheatIcon,
                "Enable a special icon for cheat mode")
            .getBoolean(enableSpecialCheatIcon);

        specialIconType = config
            .get("NotEnoughItems", "SpecialIconType", specialIconType, "Specify the type of the special cheat icon")
            .getInt(specialIconType);

        // Debug
        enableDebugMode = config.get("Debug", "enable", enableDebugMode, "Enable Debug Print Log")
            .getBoolean(enableDebugMode);

        if (config.hasChanged()) {
            config.save();
        }
    }
}
