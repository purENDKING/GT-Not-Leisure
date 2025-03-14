package com.science.gtnl;

import static com.science.gtnl.common.block.Casings.BasicBlocks.PlayerDoll;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import com.science.gtnl.Utils.GuiEventHandler;
import com.science.gtnl.Utils.LanguageManager;
import com.science.gtnl.common.block.ReAvaritia.ExtremeAnvil.RenderExtremeAnvil;
import com.science.gtnl.common.block.ReAvaritia.ExtremeAnvil.TileEntityExtremeAnvil;
import com.science.gtnl.common.block.blocks.artificialStar.ArtificialStarRender;
import com.science.gtnl.common.block.blocks.artificialStar.RealArtificialStarRender;
import com.science.gtnl.common.block.blocks.laserBeacon.MeteorMinerRenderer;
import com.science.gtnl.common.block.blocks.playerDoll.BlockPlayerDollRenderer;
import com.science.gtnl.common.block.blocks.playerDoll.ItemPlayerDollRenderer;
import com.science.gtnl.common.block.blocks.playerDoll.TileEntityPlayerDoll;
import com.science.gtnl.common.item.ItemLoader;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import fox.spiteful.avaritia.render.CosmicItemRenderer;

public class ClientProxy extends CommonProxy {

    public static int extremeAnvilRenderType;

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        new RealArtificialStarRender();
        RenderingRegistry.registerBlockHandler(new ArtificialStarRender());

        new MeteorMinerRenderer();

        extremeAnvilRenderType = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new RenderExtremeAnvil());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityExtremeAnvil.class, new RenderExtremeAnvil());

        new BlockPlayerDollRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlayerDoll.class, new BlockPlayerDollRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(PlayerDoll), new ItemPlayerDollRenderer());

        LanguageManager.writePlaceholderStrings();
        MinecraftForge.EVENT_BUS.register(new ItemPlayerDollRenderer());
        MinecraftForge.EVENT_BUS.register(new BlockPlayerDollRenderer());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        new GuiEventHandler();
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void makeThingsPretty() {
        CosmicItemRenderer sparkly = new CosmicItemRenderer();
        MinecraftForgeClient.registerItemRenderer(ItemLoader.InfinitySword, sparkly);
        MinecraftForgeClient.registerItemRenderer(ItemLoader.MatterCluster, sparkly);
    }
}
