package com.science.gtnl.common.render;

import java.nio.FloatBuffer;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TwilightSwordModelRender implements IItemRenderer {

    private final IModelCustom swordModel;

    public TwilightSwordModelRender() {
        swordModel = AdvancedModelLoader.loadModel(new ResourceLocation("sciencenotleisure:model/TwilightSword.obj"));
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();

        // 启用光照
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_LIGHT0); // 启用光源 0
        GL11.glEnable(GL11.GL_COLOR_MATERIAL); // 启用材质颜色

        // 设置光源参数
        float[] lightPosition = { 0.0f, 10.0f, 10.0f, 1.0f }; // 光源位置
        float[] lightAmbient = { 0.2f, 0.2f, 0.2f, 1.0f }; // 环境光
        float[] lightDiffuse = { 1.0f, 1.0f, 1.0f, 1.0f }; // 漫反射光
        float[] lightSpecular = { 1.0f, 1.0f, 1.0f, 1.0f }; // 镜面反射光

        GL11.glLight(
            GL11.GL_LIGHT0,
            GL11.GL_POSITION,
            (FloatBuffer) BufferUtils.createFloatBuffer(lightPosition.length)
                .put(lightPosition)
                .flip());
        GL11.glLight(
            GL11.GL_LIGHT0,
            GL11.GL_AMBIENT,
            (FloatBuffer) BufferUtils.createFloatBuffer(lightAmbient.length)
                .put(lightAmbient)
                .flip());
        GL11.glLight(
            GL11.GL_LIGHT0,
            GL11.GL_DIFFUSE,
            (FloatBuffer) BufferUtils.createFloatBuffer(lightDiffuse.length)
                .put(lightDiffuse)
                .flip());
        GL11.glLight(
            GL11.GL_LIGHT0,
            GL11.GL_SPECULAR,
            (FloatBuffer) BufferUtils.createFloatBuffer(lightSpecular.length)
                .put(lightSpecular)
                .flip());

        // 设置材质属性
        float[] matAmbient = { 0.8f, 0.8f, 0.8f, 1.0f }; // 材质环境光反射
        float[] matDiffuse = { 0.8f, 0.8f, 0.8f, 1.0f }; // 材质漫反射
        float[] matSpecular = { 1.0f, 1.0f, 1.0f, 1.0f }; // 材质镜面反射
        float[] matShininess = { 50.0f, 0.0f, 0.0f, 0.0f }; // 材质光泽度（扩展为 4 个元素）

        GL11.glMaterial(
            GL11.GL_FRONT,
            GL11.GL_AMBIENT,
            (FloatBuffer) BufferUtils.createFloatBuffer(matAmbient.length)
                .put(matAmbient)
                .flip());
        GL11.glMaterial(
            GL11.GL_FRONT,
            GL11.GL_DIFFUSE,
            (FloatBuffer) BufferUtils.createFloatBuffer(matDiffuse.length)
                .put(matDiffuse)
                .flip());
        GL11.glMaterial(
            GL11.GL_FRONT,
            GL11.GL_SPECULAR,
            (FloatBuffer) BufferUtils.createFloatBuffer(matSpecular.length)
                .put(matSpecular)
                .flip());
        GL11.glMaterial(
            GL11.GL_FRONT,
            GL11.GL_SHININESS,
            (FloatBuffer) BufferUtils.createFloatBuffer(matShininess.length)
                .put(matShininess)
                .flip());

        // 根据不同渲染类型设置变换
        switch (type) {
            case EQUIPPED: // 第三人称手持时的渲染
                GL11.glScalef(10f, 10f, 10f);
                GL11.glTranslatef(0f, 0f, 0f);
                GL11.glRotatef(-90, 0f, 1f, 0f);
                GL11.glRotatef(90, 1f, 0f, 0f);
                break;
            case EQUIPPED_FIRST_PERSON: // 第一人称视角手持时的渲染
                GL11.glScalef(2f, 2f, 2f);
                GL11.glTranslatef(0.3f, -0.1f, 0.3f);
                GL11.glRotatef(-90, 0f, 1f, 0f);
                break;
            case ENTITY: // 掉落物的渲染
                GL11.glScalef(20f, 20f, 20f);
                GL11.glTranslatef(0f, -0.5f, 0f);
                break;
            case INVENTORY: // 物品栏中的渲染
                GL11.glScalef(10f, 10f, 10f);
                GL11.glTranslatef(0.5f, 1f, 1.5f);
                GL11.glRotatef(180, 1f, 0f, 0f);
                GL11.glRotatef(-60, 0f, 0f, 1f);
                GL11.glRotatef(45, 0f, 1f, 0f);
                break;
            default:
                break;
        }

        // 绑定纹理并渲染模型
        Minecraft.getMinecraft().renderEngine
            .bindTexture(new ResourceLocation("sciencenotleisure:model/TwilightSword.png"));
        swordModel.renderAll();

        // 禁用光照
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

}
