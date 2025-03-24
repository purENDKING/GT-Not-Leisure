package com.science.gtnl.Utils;

import static com.gtnewhorizon.gtnhlib.util.AnimatedTooltipHandler.*;

import java.util.Arrays;
import java.util.function.Supplier;

import com.science.gtnl.Utils.item.TextLocalization;

public class AnimatedText {

    /**
     * 构建文本链并生成彩色动画文本。
     * 只有最后一个文本会应用动画效果，前面的文本会作为普通文本拼接。
     *
     * @param texts 可变参数，传入多个文本（最后一个为彩色文本）
     * @return 动画文本的 Supplier
     */
    @SafeVarargs
    public static Supplier<String> buildTextWithAnimatedEnd(Supplier<String>... texts) {
        if (texts == null || texts.length == 0) {
            throw new IllegalArgumentException("至少需要传入一个文本");
        }
        Supplier<String> prefixText = chain(Arrays.copyOf(texts, texts.length - 1));
        Supplier<String> lastText = texts[texts.length - 1];
        Supplier<String> animatedText = animatedText(
            lastText.get(),
            1,
            80,
            RED,
            GOLD,
            YELLOW,
            GREEN,
            AQUA,
            BLUE,
            LIGHT_PURPLE);
        return chain(prefixText, animatedText);
    }

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
}
