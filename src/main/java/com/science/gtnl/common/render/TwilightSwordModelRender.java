package com.science.gtnl.common.render;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

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
        GL11.glPopMatrix();
    }
}
