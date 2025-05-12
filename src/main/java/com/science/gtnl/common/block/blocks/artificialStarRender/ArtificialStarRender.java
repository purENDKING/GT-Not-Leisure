package com.science.gtnl.common.block.blocks.artificialStarRender;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ArtificialStarRender implements ISimpleBlockRenderingHandler {

    public static final int renderID = RenderingRegistry.getNextAvailableRenderId();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        if (modelID == renderID) {
            GL11.glPushMatrix();
            // 创建一个 TileEntityArtificialStar 实例并传递给 renderAsItem 方法
            TileEntityArtificialStar tileEntityArtificialStar = new TileEntityArtificialStar();
            ((BlockArtificialStarRender) block).renderAsItem(tileEntityArtificialStar);
            GL11.glPopMatrix();
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        return false; // 世界中的渲染已被其他逻辑处理
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return renderID;
    }
}
