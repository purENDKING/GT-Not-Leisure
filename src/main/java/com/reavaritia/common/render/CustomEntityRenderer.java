package com.reavaritia.common.render;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.shader.ShaderGroup;

import com.google.gson.JsonSyntaxException;

public class CustomEntityRenderer extends EntityRenderer {

    public CustomEntityRenderer(Minecraft mc) {
        super(mc, mc.getResourceManager());
    }

    public CustomEntityRenderer(Minecraft mc, IResourceManager resourceManager) {
        super(mc, resourceManager);
    }

    public void setShaderIndex(int index) {
        this.shaderIndex = index;
    }

    public void activateDesaturateShader() {
        if (OpenGlHelper.shadersSupported) {
            if (this.theShaderGroup != null) {
                this.theShaderGroup.deleteShaderGroup();
            }

            this.shaderIndex = 16;

            try {
                logger.info("Selecting effect " + shaderResourceLocations[this.shaderIndex]);
                this.theShaderGroup = new ShaderGroup(
                    this.mc.getTextureManager(),
                    this.resourceManager,
                    this.mc.getFramebuffer(),
                    shaderResourceLocations[this.shaderIndex]);
                this.theShaderGroup.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
            } catch (IOException ioexception) {
                logger.warn("Failed to load shader: " + shaderResourceLocations[this.shaderIndex], ioexception);
                this.shaderIndex = shaderCount;
            } catch (JsonSyntaxException jsonsyntaxexception) {
                logger.warn("Failed to load shader: " + shaderResourceLocations[this.shaderIndex], jsonsyntaxexception);
                this.shaderIndex = shaderCount;
            }
        }
    }

    public void resetShader() {
        if (this.theShaderGroup != null) {
            this.theShaderGroup.deleteShaderGroup();
        }

        this.shaderIndex = shaderCount;

        try {
            logger.info("Resetting shader to default");
            this.theShaderGroup = null;
        } catch (Exception e) {
            logger.warn("Failed to reset shader", e);
        }
    }
}
