package com.reavaritia.common.block;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.reavaritia.common.block.ExtremeAnvil.BlockExtremeAnvil;
import com.reavaritia.common.block.NeutronCollector.DenseNeutronCollector;
import com.reavaritia.common.block.NeutronCollector.DenserNeutronCollector;
import com.reavaritia.common.block.NeutronCollector.DensestNeutronCollector;
import com.reavaritia.common.block.NeutronCollector.NeutronCollector;
import com.reavaritia.common.block.NeutronCollector.TileEntityNeutronCollector;

import cpw.mods.fml.common.registry.GameRegistry;

public class BlockRegister {

    public static void registryBlocks() {

        ReAvaBasicBlocks.ExtremeAnvil = new BlockExtremeAnvil();

        ReAvaBasicBlocks.NeutronCollector = new NeutronCollector();
        ReAvaBasicBlocks.DenseNeutronCollector = new DenseNeutronCollector();
        ReAvaBasicBlocks.DenserNeutronCollector = new DenserNeutronCollector();
        ReAvaBasicBlocks.DensestNeutronCollector = new DensestNeutronCollector();
        GameRegistry.registerTileEntity(TileEntityNeutronCollector.class, "NeutronCollectorTileEntity");

        ReAvaBasicBlocks.BlockSoulFarmland = new BlockSoulFarmland();
    }

    public static void registryAnotherData() {
        ItemStack ExtremeAnvilBlock = new ItemStack(ReAvaBasicBlocks.ExtremeAnvil, 1, 0);
        ItemStack Bedrock = new ItemStack(Blocks.bedrock, 1, 0);
        ItemStack EndPortal = new ItemStack(Blocks.end_portal, 1, 0);
        ItemStack EndPortalFrame = new ItemStack(Blocks.end_portal_frame, 1, 0);
        ItemStack CommandBlock = new ItemStack(Blocks.command_block, 1, 0);

        OreDictionary.registerOre("neutronUnbreak", CommandBlock);
        OreDictionary.registerOre("neutronUnbreak", EndPortal);
        OreDictionary.registerOre("neutronUnbreak", EndPortalFrame);
        OreDictionary.registerOre("neutronUnbreak", Bedrock);
        OreDictionary.registerOre("neutronUnbreak", ExtremeAnvilBlock);

    }

}
