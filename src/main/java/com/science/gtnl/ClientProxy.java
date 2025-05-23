package com.science.gtnl;

import static com.science.gtnl.common.block.Casings.BasicBlocks.*;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import com.science.gtnl.Utils.GuiEventHandler;
import com.science.gtnl.Utils.IIconRegister;
import com.science.gtnl.Utils.message.TitleDisplayHandler;
import com.science.gtnl.common.block.blocks.artificialStarRender.ArtificialStarRender;
import com.science.gtnl.common.block.blocks.artificialStarRender.RealArtificialStarRender;
import com.science.gtnl.common.block.blocks.eternalGregTechWorkshopRender.ItemBlockEternalGregTechWorkshopRender;
import com.science.gtnl.common.block.blocks.eternalGregTechWorkshopRender.RenderEternalGregTechWorkshop;
import com.science.gtnl.common.block.blocks.eternalGregTechWorkshopRender.TileEntityEternalGregTechWorkshop;
import com.science.gtnl.common.block.blocks.laserBeacon.MeteorMinerRenderer;
import com.science.gtnl.common.block.blocks.nanoPhagocytosisPlantRender.ItemBlockNanoPhagocytosisPlantRender;
import com.science.gtnl.common.block.blocks.nanoPhagocytosisPlantRender.RenderNanoPhagocytosisPlant;
import com.science.gtnl.common.block.blocks.nanoPhagocytosisPlantRender.TileEntityNanoPhagocytosisPlant;
import com.science.gtnl.common.block.blocks.playerDoll.BlockPlayerDollRenderer;
import com.science.gtnl.common.block.blocks.playerDoll.ItemPlayerDollRenderer;
import com.science.gtnl.common.block.blocks.playerDoll.PlayerDollRenderManager;
import com.science.gtnl.common.block.blocks.playerDoll.TileEntityPlayerDoll;
import com.science.gtnl.common.item.BasicItems;
import com.science.gtnl.common.item.ItemLoader;
import com.science.gtnl.common.render.TwilightSwordModelRender;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import fox.spiteful.avaritia.render.FancyHaloRenderer;

public class ClientProxy extends CommonProxy {

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        new RealArtificialStarRender();
        RenderingRegistry.registerBlockHandler(new ArtificialStarRender());

        new MeteorMinerRenderer();

        new BlockPlayerDollRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlayerDoll.class, new BlockPlayerDollRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(PlayerDoll), new ItemPlayerDollRenderer());
        MinecraftForgeClient.registerItemRenderer(ItemLoader.TwilightSword, new TwilightSwordModelRender());

        MinecraftForge.EVENT_BUS.register(new PlayerDollRenderManager());

        MinecraftForgeClient.registerItemRenderer(
            Item.getItemFromBlock(BlockNanoPhagocytosisPlantRender),
            new ItemBlockNanoPhagocytosisPlantRender(BlockNanoPhagocytosisPlantRender));
        ClientRegistry
            .bindTileEntitySpecialRenderer(TileEntityNanoPhagocytosisPlant.class, new RenderNanoPhagocytosisPlant());

        MinecraftForgeClient.registerItemRenderer(
            Item.getItemFromBlock(BlockEternalGregTechWorkshopRender),
            new ItemBlockEternalGregTechWorkshopRender(BlockEternalGregTechWorkshopRender));
        ClientRegistry.bindTileEntitySpecialRenderer(
            TileEntityEternalGregTechWorkshop.class,
            new RenderEternalGregTechWorkshop());

        MinecraftForge.EVENT_BUS.register(new TitleDisplayHandler());
        MinecraftForge.EVENT_BUS.register(new IIconRegister());

        MinecraftForgeClient.registerItemRenderer(ItemLoader.TestItem, new FancyHaloRenderer());
        MinecraftForgeClient.registerItemRenderer(BasicItems.MetaItem, new FancyHaloRenderer());
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

}
