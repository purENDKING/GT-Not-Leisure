package com.reavaritia.common.render;

import java.io.IOException;
import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonSyntaxException;

public class CustomEntityRenderer extends EntityRenderer {

    private static final Logger logger = LogManager.getLogger();
    public static final ResourceLocation[] shaderResourceLocations = new ResourceLocation[] {
        new ResourceLocation("shaders/post/notch.json"), new ResourceLocation("shaders/post/fxaa.json"),
        new ResourceLocation("shaders/post/art.json"), new ResourceLocation("shaders/post/bumpy.json"),
        new ResourceLocation("shaders/post/blobs2.json"), new ResourceLocation("shaders/post/pencil.json"),
        new ResourceLocation("shaders/post/color_convolve.json"), new ResourceLocation("shaders/post/deconverge.json"),
        new ResourceLocation("shaders/post/flip.json"), new ResourceLocation("shaders/post/invert.json"),
        new ResourceLocation("shaders/post/ntsc.json"), new ResourceLocation("shaders/post/outline.json"),
        new ResourceLocation("shaders/post/phosphor.json"), new ResourceLocation("shaders/post/scan_pincushion.json"),
        new ResourceLocation("shaders/post/sobel.json"), new ResourceLocation("shaders/post/bits.json"),
        new ResourceLocation("shaders/post/desaturate.json"), new ResourceLocation("shaders/post/green.json"),
        new ResourceLocation("shaders/post/blur.json"), new ResourceLocation("shaders/post/wobble.json"),
        new ResourceLocation("shaders/post/blobs.json"), new ResourceLocation("shaders/post/antialias.json") };

    private static final int shaderCount = shaderResourceLocations.length;
    private int shaderIndex = shaderCount;

    public CustomEntityRenderer(Minecraft mc) {
        super(mc, mc.getResourceManager());
    }

    public CustomEntityRenderer(Minecraft mc, IResourceManager resourceManager) {
        super(mc, resourceManager);
    }

    public void setShaderIndex(int index) {
        this.shaderIndex = index;
    }

    public void activateDesaturateShader(int index) {
        if (!OpenGlHelper.shadersSupported) return;

        try {
            EntityRenderer renderer = Minecraft.getMinecraft().entityRenderer;
            Field shaderField = getShaderField(renderer);
            ShaderGroup currentShader = (ShaderGroup) shaderField.get(renderer);

            if (currentShader != null) {
                currentShader.deleteShaderGroup();
            }

            this.shaderIndex = index;

            logger.info("Selecting effect " + shaderResourceLocations[this.shaderIndex]);

            ShaderGroup newShader = new ShaderGroup(
                this.mc.getTextureManager(),
                this.resourceManager,
                this.mc.getFramebuffer(),
                shaderResourceLocations[this.shaderIndex]);
            newShader.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);

            shaderField.set(renderer, newShader);

        } catch (IOException | JsonSyntaxException | ReflectiveOperationException e) {
            logger.warn("Failed to load shader: " + shaderResourceLocations[this.shaderIndex], e);
            this.shaderIndex = shaderCount;
        }
    }

    public void resetShader() {
        try {
            EntityRenderer renderer = Minecraft.getMinecraft().entityRenderer;
            Field shaderField = getShaderField(renderer);
            ShaderGroup currentShader = (ShaderGroup) shaderField.get(renderer);

            if (currentShader != null) {
                currentShader.deleteShaderGroup();
            }

            logger.info("Resetting shader to default");
            shaderField.set(renderer, null);

            this.shaderIndex = shaderCount;

        } catch (ReflectiveOperationException e) {
            logger.warn("Failed to reset shader", e);
        }
    }

    private static Field getShaderField(EntityRenderer renderer) throws NoSuchFieldException {
        Field field;
        try {
            field = EntityRenderer.class.getDeclaredField("field_147713_ae");
        } catch (NoSuchFieldException e) {
            field = EntityRenderer.class.getDeclaredField("theShaderGroup");
        }
        field.setAccessible(true);
        return field;
    }
}
