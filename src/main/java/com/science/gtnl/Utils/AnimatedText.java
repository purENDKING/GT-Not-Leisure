package com.science.gtnl.Utils;

import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.*;

import java.util.function.Supplier;

import net.minecraft.util.StatCollector;

import com.science.gtnl.Utils.item.TextLocalization;

public class AnimatedText {

    public static final Supplier<String> SNL_EDEN_GARDEN = () -> AnimatedText.SCIENCE_NOT_LEISURE.get()
        + AnimatedText.EDEN_GARDEN.get();
    public static final Supplier<String> SNL_SRP = () -> AnimatedText.SCIENCE_NOT_LEISURE.get() + RESET
        + ": "
        + AnimatedText.STRUCTURAL_RECONSTRUCTION_PLAN.get();
    public static final Supplier<String> SNL_QYZG = () -> AnimatedText.SCIENCE_NOT_LEISURE.get() + RESET
        + ": "
        + AnimatedText.QYZG.get();
    public static final Supplier<String> SNL_NLXCJH = () -> AnimatedText.SCIENCE_NOT_LEISURE.get() + RESET
        + ": "
        + AnimatedText.NLXCJH.get();
    public static final Supplier<String> SNL_TOTTO = () -> AnimatedText.SCIENCE_NOT_LEISURE.get() + RESET
        + ": "
        + AnimatedText.TOTTO.get();
    public static final Supplier<String> SNL_QYZG_SRP = () -> AnimatedText.SCIENCE_NOT_LEISURE.get() + RESET
        + ": "
        + AnimatedText.QYZG.get()
        + RESET
        + " X "
        + AnimatedText.STRUCTURAL_RECONSTRUCTION_PLAN.get();

    public static final Supplier<String> SCIENCE_NOT_LEISURE = chain(
        text(TextLocalization.Adder),
        animatedText("Science Not Leisure", 1, 80, RED, GOLD, YELLOW, GREEN, AQUA, BLUE, LIGHT_PURPLE));

    public static final Supplier<String> STRUCTURAL_RECONSTRUCTION_PLAN = chain(
        animatedText(
            TextLocalization.StructuralReconstructionPlan,
            1,
            50,
            BLUE,
            BLUE,
            BLUE,
            WHITE,
            BLUE,
            WHITE,
            WHITE,
            BLUE,
            WHITE,
            WHITE,
            BLUE,
            RED,
            WHITE,
            GRAY,
            GRAY,
            GRAY,
            GRAY,
            GRAY,
            GRAY,
            GRAY,
            GRAY,
            GRAY,
            GRAY,
            GRAY,
            GRAY,
            GRAY,
            GRAY,
            GRAY,
            GRAY));

    public static final Supplier<String> QYZG = chain(
        animatedText(
            "犰狳重工",
            1,
            80,
            YELLOW + BOLD + OBFUSCATED + UNDERLINE + ITALIC,
            GOLD + ITALIC,
            GOLD + ITALIC,
            GOLD + ITALIC,
            GOLD + ITALIC,
            GOLD + ITALIC,
            GOLD + ITALIC,
            GOLD + ITALIC,
            GOLD + ITALIC,
            GOLD + ITALIC,
            GOLD + ITALIC,
            GOLD + ITALIC,
            GOLD + ITALIC,
            GOLD + ITALIC,
            GOLD + ITALIC,
            GOLD + ITALIC,
            GOLD + ITALIC,
            GOLD + ITALIC,
            GOLD + ITALIC,
            GOLD + ITALIC,
            GOLD + ITALIC,
            GOLD + ITALIC));

    public static final Supplier<String> NLXCJH = chain(
        animatedText(
            "年",
            1,
            100,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            YELLOW + OBFUSCATED + BOLD + UNDERLINE),
        animatedText(
            "轮",
            1,
            100,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            YELLOW + OBFUSCATED + BOLD + UNDERLINE),
        animatedText(
            "新",
            1,
            200,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            YELLOW + OBFUSCATED + BOLD + UNDERLINE),
        animatedText(
            "城",
            1,
            150,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            YELLOW + OBFUSCATED + BOLD + UNDERLINE),
        animatedText(
            "计",
            1,
            150,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            YELLOW + OBFUSCATED + BOLD + UNDERLINE),
        animatedText(
            "划",
            1,
            150,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            YELLOW + OBFUSCATED + BOLD + UNDERLINE),
        animatedText(
            " b",
            1,
            200,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            YELLOW + OBFUSCATED + BOLD + UNDERLINE),
        animatedText(
            "y ",
            1,
            100,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            YELLOW + OBFUSCATED + BOLD + UNDERLINE),
        animatedText(
            "咸",
            1,
            150,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            YELLOW + OBFUSCATED + BOLD + UNDERLINE),
        animatedText(
            "到",
            1,
            100,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            YELLOW + OBFUSCATED + BOLD + UNDERLINE),
        animatedText(
            "老",
            1,
            150,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            YELLOW + OBFUSCATED + BOLD + UNDERLINE),
        animatedText(
            "时",
            1,
            100,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            YELLOW + OBFUSCATED + BOLD + UNDERLINE),
        animatedText(
            "变",
            1,
            150,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            YELLOW + OBFUSCATED + BOLD + UNDERLINE),
        animatedText(
            "成",
            1,
            100,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            YELLOW + OBFUSCATED + BOLD + UNDERLINE),
        animatedText(
            "鱼",
            1,
            150,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            GOLD + BOLD + UNDERLINE,
            YELLOW + OBFUSCATED + BOLD + UNDERLINE));

    public static final Supplier<String> TOTTO = chain(
        text("Author: "),
        animatedText(
            "Totto",
            1,
            100,
            LIGHT_PURPLE + BOLD + OBFUSCATED + UNDERLINE,
            RED + BOLD + OBFUSCATED + UNDERLINE,
            GOLD + OBFUSCATED + BOLD + UNDERLINE,
            YELLOW + OBFUSCATED + BOLD + UNDERLINE,
            GREEN + OBFUSCATED + BOLD + UNDERLINE,
            AQUA + OBFUSCATED + BOLD + UNDERLINE,
            BLUE + OBFUSCATED + BOLD + UNDERLINE));

    public static final Supplier<String> EDEN_GARDEN = chain(
        text(" X "),
        animatedText(
            "伊甸生态园 by 茯角",
            1,
            100,
            LIGHT_PURPLE + BOLD + OBFUSCATED + UNDERLINE,
            RED + BOLD + OBFUSCATED + UNDERLINE,
            GOLD + OBFUSCATED + BOLD + UNDERLINE,
            YELLOW + OBFUSCATED + BOLD + UNDERLINE,
            GREEN + OBFUSCATED + BOLD + UNDERLINE,
            AQUA + OBFUSCATED + BOLD + UNDERLINE,
            BLUE + OBFUSCATED + BOLD + UNDERLINE));

    public static final Supplier<String> NEW_HORIZONS_COIL_0 = chain(
        animatedText(
            "179,769,313,486,231,590,772,930,519,078,902,473,361,797,697,894,230,657,273,430,081,",
            1,
            80,
            RED,
            GOLD,
            YELLOW,
            GREEN,
            AQUA,
            BLUE,
            LIGHT_PURPLE));

    public static final Supplier<String> NEW_HORIZONS_COIL_1 = chain(
        animatedText(
            "157,732,675,805,500,963,132,708,477,322,407,536,021,120,113,879,871,393,357,658,789,",
            1,
            80,
            GOLD,
            YELLOW,
            GREEN,
            AQUA,
            BLUE,
            LIGHT_PURPLE,
            RED));

    public static final Supplier<String> NEW_HORIZONS_COIL_2 = chain(
        animatedText(
            "768,814,416,622,492,847,430,639,474,124,377,767,893,424,865,485,276,302,219,601,246,",
            1,
            80,
            YELLOW,
            GREEN,
            AQUA,
            BLUE,
            LIGHT_PURPLE,
            RED,
            GOLD));

    public static final Supplier<String> NEW_HORIZONS_COIL_3 = chain(
        animatedText(
            "094,119,453,082,952,085,005,768,838,150,682,342,462,881,473,913,110,540,827,237,163,",
            1,
            80,
            GREEN,
            AQUA,
            BLUE,
            LIGHT_PURPLE,
            RED,
            GOLD,
            YELLOW));

    public static final Supplier<String> NEW_HORIZONS_COIL_4 = chain(
        animatedText(
            "350,510,684,586,298,239,947,245,938,479,716,304,835,356,329,624,224,137,216",
            1,
            80,
            AQUA,
            BLUE,
            LIGHT_PURPLE,
            RED,
            GOLD,
            YELLOW,
            GREEN),
        text(RESET + StatCollector.translateToLocal("gt.coilunittooltip")));

    public static final Supplier<String> zeroCM = chain(
        animatedText(
            "Most machine recipe by zero_CM",
            1,
            100,
            LIGHT_PURPLE + BOLD + OBFUSCATED,
            RED + BOLD + OBFUSCATED,
            GOLD + OBFUSCATED + BOLD,
            YELLOW + OBFUSCATED + BOLD,
            GREEN + OBFUSCATED + BOLD,
            AQUA + OBFUSCATED + BOLD,
            BLUE + OBFUSCATED + BOLD));
}
