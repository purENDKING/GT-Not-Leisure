package com.reavaritia.common.render;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.shader.ShaderGroup;

import com.google.gson.JsonSyntaxException;
import com.science.gtnl.mixins.early.AccessorEntityRenderer;

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

    public void activateDesaturateShader(int index) {
        if (OpenGlHelper.shadersSupported) {
            ShaderGroup shaderGroup = ((AccessorEntityRenderer) this).getTheShaderGroup();
            if (shaderGroup != null) {
                shaderGroup.deleteShaderGroup();
            }

            this.shaderIndex = index;

            try {
                logger.info("Selecting effect " + shaderResourceLocations[this.shaderIndex]);
                shaderGroup = new ShaderGroup(
                    this.mc.getTextureManager(),
                    this.resourceManager,
                    this.mc.getFramebuffer(),
                    shaderResourceLocations[this.shaderIndex]);
                shaderGroup.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);

                // Set the shader group using the accessor
                ((AccessorEntityRenderer) this).setTheShaderGroup(shaderGroup);
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
        ShaderGroup shaderGroup = ((AccessorEntityRenderer) this).getTheShaderGroup();
        if (shaderGroup != null) {
            shaderGroup.deleteShaderGroup();
        }

        this.shaderIndex = shaderCount;

        try {
            logger.info("Resetting shader to default");
            ((AccessorEntityRenderer) this).setTheShaderGroup(null);
        } catch (Exception e) {
            logger.warn("Failed to reset shader", e);
        }
    }
}
