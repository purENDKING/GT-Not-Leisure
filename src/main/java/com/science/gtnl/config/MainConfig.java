package com.science.gtnl.config;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraftforge.common.config.Configuration;

// spotless:off
public class MainConfig {

    // --- Main Configuration Categories ---
    public static final String CATEGORY_GTNL_CONFIG = "gtnl_config";

    // --- Sub-Categories (direct children of gtnl_config, or deeper nested) ---
    public static final String CATEGORY_MACHINE = CATEGORY_GTNL_CONFIG + Configuration.CATEGORY_SPLITTER + "machine";
    public static final String CATEGORY_RE_AVARITIA = CATEGORY_GTNL_CONFIG + Configuration.CATEGORY_SPLITTER
        + "re_avaritia";
    public static final String CATEGORY_BLOOD_MAGIC = CATEGORY_GTNL_CONFIG + Configuration.CATEGORY_SPLITTER
        + "blood_magic";
    public static final String CATEGORY_RECIPE = CATEGORY_GTNL_CONFIG + Configuration.CATEGORY_SPLITTER + "recipe";
    public static final String CATEGORY_TICK_RATE = CATEGORY_GTNL_CONFIG + Configuration.CATEGORY_SPLITTER + "tickrate";
    public static final String CATEGORY_PLAYER_DOLL = CATEGORY_GTNL_CONFIG + Configuration.CATEGORY_SPLITTER
        + "player_doll";
    public static final String CATEGORY_NOT_ENOUGH_ITEMS = CATEGORY_GTNL_CONFIG + Configuration.CATEGORY_SPLITTER
        + "not_enough_items";
    public static final String CATEGORY_SUPER_CREEPER = CATEGORY_GTNL_CONFIG + Configuration.CATEGORY_SPLITTER
        + "super_creeper";
    public static final String CATEGORY_DEBUG = CATEGORY_GTNL_CONFIG + Configuration.CATEGORY_SPLITTER + "debug";

    // --- Deeper Nested Categories (paths constructed using CATEGORY_SPLITTER) ---
    public static final String SUB_CATEGORY_METEOR_MINER = CATEGORY_MACHINE + Configuration.CATEGORY_SPLITTER
        + "meteor_miner";
    public static final String SUB_CATEGORY_ARTIFICIAL_STAR = CATEGORY_MACHINE + Configuration.CATEGORY_SPLITTER
        + "artificial_star";
    public static final String SUB_CATEGORY_ETERNAL_GREGTECH_WORKSHOP = CATEGORY_MACHINE
        + Configuration.CATEGORY_SPLITTER
        + "eternal_gregtech_workshop";
    public static final String SUB_CATEGORY_PORTAL_TO_ALFHEIM = CATEGORY_MACHINE + Configuration.CATEGORY_SPLITTER
        + "portal_to_alfheim";
    public static final String SUB_CATEGORY_INFINITY_SWORD = CATEGORY_RE_AVARITIA + Configuration.CATEGORY_SPLITTER
        + "infinity_sword";
    public static final String SUB_CATEGORY_CHRONARCHS_CLOCK = CATEGORY_RE_AVARITIA + Configuration.CATEGORY_SPLITTER
        + "chronarch_clock";
    public static final String SUB_CATEGORY_METEOR_PARADIGM = CATEGORY_BLOOD_MAGIC + Configuration.CATEGORY_SPLITTER
        + "meteor_paradigm";

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

    // SuperCreeper
    public static List<String> targetBlockSpecs = new ArrayList<>();
    public static String[] defaultTargetBlocks = { "minecraft:chest", "appliedenergistics2:tile.BlockDrive",
        "gregtech:gt.blockmachines" };
    public static boolean enableSuperCreeper = false;

    // Debug
    public static boolean enableDebugMode = false;

    public static Configuration config;

    static {
        File configDir = new File("config/GTNotLeisure");
        if (!configDir.exists()) {
            configDir.mkdirs();
        }

        File mainConfigFile = new File(configDir, "main.cfg");

        if (config == null) {
            config = new Configuration(mainConfigFile);
            loadConfig();
        }

        LocalDate today = LocalDate.now();
        if (today.getMonthValue() == 4 && today.getDayOfMonth() == 1) {
            enableAprilFoolRecipe = true;
        }

    }

    public static void reloadConfig() {
        if (config != null) {
            if (config.hasChanged()) {
                config.save();
            }
            if (targetBlockSpecs != null) targetBlockSpecs.clear();
            config.load();
            loadConfig();
            targetBlockSpecs.addAll(Arrays.asList(defaultTargetBlocks));
        }
    }

    public static void loadConfig() {

        // Machine
        enableRecipeOutputChance = config
            .get(
                CATEGORY_MACHINE,
                "enableRecipeOutputChance",
                enableRecipeOutputChance,
                "Enable Output Change Function")
            .getBoolean(enableRecipeOutputChance);

        enableMachineAmpLimit = config
            .get(CATEGORY_MACHINE, "enableLaserHatch", enableMachineAmpLimit, "Enable Machine Can't Use Laser Hatch")
            .getBoolean(enableMachineAmpLimit);

        recipeOutputChance = config
            .get(CATEGORY_MACHINE, "RecipeChanceOutput", recipeOutputChance, "Change Recipe Item Output, like QFT")
            .getDouble(recipeOutputChance);

        meteorMinerMaxBlockPerCycle = config
            .get(
                SUB_CATEGORY_METEOR_MINER,
                "MaxBlockCount",
                meteorMinerMaxBlockPerCycle,
                "Set the Meteor Miner how many every cycle break a block")
            .getInt(meteorMinerMaxBlockPerCycle);

        meteorMinerMaxRowPerCycle = config
            .get(
                SUB_CATEGORY_METEOR_MINER,
                "MaxRawCount",
                meteorMinerMaxRowPerCycle,
                "Set the Meteor Miner how many every cycle break row blocks")
            .getInt(meteorMinerMaxRowPerCycle);

        euEveryEnhancementCore = config
            .get(
                SUB_CATEGORY_ARTIFICIAL_STAR,
                "EUEveryEnhancementCore",
                euEveryEnhancementCore,
                "Set the power generation of EU Every Enhancement Core")
            .getInt(euEveryEnhancementCore);

        euEveryDepletedExcitedNaquadahFuelRod = config
            .get(
                SUB_CATEGORY_ARTIFICIAL_STAR,
                "EUEveryDepletedExcitedNaquadahFuelRod",
                euEveryDepletedExcitedNaquadahFuelRod,
                "Set the power generation of EU Every Depleted Excited Naquadah FuelRod")
            .getInt(euEveryDepletedExcitedNaquadahFuelRod);

        secondsOfArtificialStarProgressCycleTime = config
            .get(
                SUB_CATEGORY_ARTIFICIAL_STAR,
                "secondsOfArtificialStarProgressCycleTime",
                secondsOfArtificialStarProgressCycleTime,
                "Set secondsOfArtificialStarProgressCycleTime running time")
            .getDouble(secondsOfArtificialStarProgressCycleTime);

        enableRenderDefaultArtificialStar = config
            .get(
                SUB_CATEGORY_ARTIFICIAL_STAR,
                "EnableDefaultRender",
                enableRenderDefaultArtificialStar,
                "Open RenderDefaultArtificialStar rendering")
            .getBoolean(enableRenderDefaultArtificialStar);

        enableEternalGregTechWorkshopSpiralRender = config
            .get(
                SUB_CATEGORY_ETERNAL_GREGTECH_WORKSHOP,
                "spiralRender",
                enableEternalGregTechWorkshopSpiralRender,
                "Enable Eternal GregTech Workshop Spiral Render, like DNA")
            .getBoolean(enableEternalGregTechWorkshopSpiralRender);

        enablePortalToAlfheimBigBoom = config
            .get(
                SUB_CATEGORY_PORTAL_TO_ALFHEIM,
                "bigBoom",
                enablePortalToAlfheimBigBoom,
                "Setting this to false will reduce the Portal To Alfheim explosion to little more then a tnt blast")
            .getBoolean(enablePortalToAlfheimBigBoom);

        // Recipe
        enableDeleteRecipe = config
            .get(CATEGORY_RECIPE, "enableDeleteRecipe", enableDeleteRecipe, "Enable Delete Recipe")
            .getBoolean(enableDeleteRecipe);

        enableAprilFoolRecipe = config
            .get(CATEGORY_RECIPE, "enableAprilFoolRecipe", enableAprilFoolRecipe, "Force enable April Fool's recipe")
            .getBoolean(enableAprilFoolRecipe);

        enableCheatRecipeWithOwner = config
            .get(
                CATEGORY_RECIPE,
                "enableCheatRecipeWithOwner",
                enableCheatRecipeWithOwner,
                "Enable Only Player Owner Cheat Recipe (Need 7 Mods)")
            .getBoolean(enableCheatRecipeWithOwner);

        // Tick Rate
        defaultTickrate = (float) config
            .get(
                CATEGORY_TICK_RATE,
                "Default",
                defaultTickrate,
                "Default tickrate. The game will always initialize with this value.")
            .getDouble(defaultTickrate);

        minTickrate = (float) config
            .get(
                CATEGORY_TICK_RATE,
                "Minimum",
                minTickrate,
                "Minimum tickrate from servers. Prevents really low tickrate values.")
            .getDouble(minTickrate);

        maxTickrate = (float) config
            .get(
                CATEGORY_TICK_RATE,
                "Maximum",
                maxTickrate,
                "Maximum tickrate from servers. Prevents really high tickrate values.")
            .getDouble(maxTickrate);

        showTickrateMessages = config
            .get(
                CATEGORY_TICK_RATE,
                "show-messages",
                showTickrateMessages,
                "If it will show log messages in the console and the game")
            .getBoolean(showTickrateMessages);

        // Player Doll
        enableCustomPlayerDoll = config
            .get(CATEGORY_PLAYER_DOLL, "enable", enableCustomPlayerDoll, "Enable Custom Player Skin for Player Doll")
            .getBoolean(enableCustomPlayerDoll);

        // Infinity Sword
        enableInfinitySwordBypassMechanism = config
            .get(
                SUB_CATEGORY_INFINITY_SWORD,
                "enableBypass",
                enableInfinitySwordBypassMechanism,
                "Enable Infinity Sword bypass against Blood Sword and Draconic Armor")
            .getBoolean(enableInfinitySwordBypassMechanism);

        enableInfinitySwordExplosion = config
            .get(
                SUB_CATEGORY_INFINITY_SWORD,
                "enableExplosion",
                enableInfinitySwordExplosion,
                "Enable when Infinity Sword hit Infinity Suit create Explosion")
            .getBoolean(enableInfinitySwordExplosion);

        enableRenderInfinitySwordSpecial = config
            .get(
                SUB_CATEGORY_INFINITY_SWORD,
                "enableSpecialRender",
                enableRenderInfinitySwordSpecial,
                "Enable Player Special Render")
            .getBoolean(enableRenderInfinitySwordSpecial);

        // Chronarch's Clock
        chronarchsClockRadius = config
            .get(SUB_CATEGORY_CHRONARCHS_CLOCK, "radius", chronarchsClockRadius, "Effective radius in blocks")
            .getInt(chronarchsClockRadius);

        chronarchsClockSpeedMultiplier = config
            .get(
                SUB_CATEGORY_CHRONARCHS_CLOCK,
                "speedMultiplier",
                chronarchsClockSpeedMultiplier,
                "Speed multiplier for the clock")
            .getInt(chronarchsClockSpeedMultiplier);

        chronarchsClockDurationTicks = config
            .get(
                SUB_CATEGORY_CHRONARCHS_CLOCK,
                "durationTicks",
                chronarchsClockDurationTicks,
                "Duration of the clock's effect in ticks")
            .getInt(chronarchsClockDurationTicks);

        chronarchsClockCooldown = config
            .get(SUB_CATEGORY_CHRONARCHS_CLOCK, "Cooldown", chronarchsClockCooldown, "Change Chronarchs Clock Cooldown")
            .getInt(chronarchsClockCooldown);

        // Blood Magic
        meteorParadigmChunkSize = config
            .get(
                SUB_CATEGORY_METEOR_PARADIGM,
                "ChunkSize",
                meteorParadigmChunkSize,
                "Set the chunk size for meteor paradigm operations (default: 1024)")
            .getInt(meteorParadigmChunkSize);

        meteorParadigmBatchUpdateInterval = config
            .get(
                SUB_CATEGORY_METEOR_PARADIGM,
                "BatchUpdateInterval",
                meteorParadigmBatchUpdateInterval,
                "Set the batch update interval for meteor paradigm operations (default: 2048)")
            .getInt(meteorParadigmBatchUpdateInterval);

        // Not Enough Items
        enableSpecialCheatIcon = config
            .get(
                CATEGORY_NOT_ENOUGH_ITEMS,
                "EnableSpecialCheatIcon",
                enableSpecialCheatIcon,
                "Enable a special icon for cheat mode")
            .getBoolean(enableSpecialCheatIcon);

        specialIconType = config
            .get(
                CATEGORY_NOT_ENOUGH_ITEMS,
                "SpecialIconType",
                specialIconType,
                "Specify the type of the special cheat icon")
            .getInt(specialIconType);

        // Super Creeper
        defaultTargetBlocks = config
            .get(
                CATEGORY_SUPER_CREEPER,
                "DefaultTargetBlocks",
                defaultTargetBlocks,
                "List of target block IDs. Format: 'modid:blockname' or 'modid:blockname:meta'.")
            .getStringList();

        enableSuperCreeper = config
            .get(
                CATEGORY_SUPER_CREEPER,
                "EnableSuperCreeper",
                enableSuperCreeper,
                "Enable super creeper, can find you chest and more")
            .getBoolean(enableSuperCreeper);

        // Debug
        enableDebugMode = config.get(CATEGORY_DEBUG, "enableDebugMode", enableDebugMode, "Enable Debug Print Log")
            .getBoolean(enableDebugMode);

        if (config.hasChanged()) {
            config.save();
        }
    }
}
