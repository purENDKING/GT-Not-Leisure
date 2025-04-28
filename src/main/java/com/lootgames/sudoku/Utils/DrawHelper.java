package com.lootgames.sudoku.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import org.lwjgl.opengl.GL11;

public class DrawHelper {

    /**
     * 在指定世界坐标下绘制文本
     *
     * @param text   文本内容
     * @param x      屏幕X
     * @param y      屏幕Y
     * @param z      深度，通常设置0
     * @param color  ARGB颜色
     * @param shadow 是否绘制阴影
     */
    public static void drawString(String text, float x, float y, float z, int color, boolean shadow) {
        FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
        // 推入深度
        GL11.glPushMatrix();
        GL11.glTranslatef(0F, 0F, z);
        if (shadow) {
            fr.drawStringWithShadow(text, (int) x, (int) y, color);
        } else {
            fr.drawString(text, (int) x, (int) y, color);
        }
        GL11.glPopMatrix();
    }
}
