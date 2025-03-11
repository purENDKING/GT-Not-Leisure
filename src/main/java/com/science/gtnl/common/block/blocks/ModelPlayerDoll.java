package com.science.gtnl.common.block.blocks;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelPlayerDoll {

    public static final ResourceLocation MODEL_RESOURCE = new ResourceLocation(
        "sciencenotleisure:model/PlayerDoll.obj");
    public static final IModelCustom model = AdvancedModelLoader.loadModel(MODEL_RESOURCE);

    public void renderAll() {
        model.renderAll();
    }
}
