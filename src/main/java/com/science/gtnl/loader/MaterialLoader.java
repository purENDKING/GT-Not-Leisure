package com.science.gtnl.loader;

import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.common.block.blocks.BlockRegister;
import com.science.gtnl.common.item.ItemLoader;
import com.science.gtnl.common.item.ItemRegister;
import com.science.gtnl.common.item.items.MilledOre;
import com.science.gtnl.common.materials.MaterialPool;

import bartworks.API.WerkstoffAdderRegistry;
import codechicken.nei.api.API;

public class MaterialLoader {

    public static void loadPreInit() {

        ItemRegister.registry();
        ItemLoader.registerItems();
        ItemRegister.registryOreDictionary();
        ItemRegister.registryOreBlackList();
        WerkstoffAdderRegistry.addWerkstoffAdder(new MaterialPool());

        BlockRegister.registry();
        BlockRegister.registryAnotherData();
        BlockRegister.registerTreeBrickuoia();

        API.hideItem(GTNLItemList.NanoPhagocytosisPlantRender.get(1));
        API.hideItem(GTNLItemList.ArtificialStarRender.get(1));
        API.hideItem(GTNLItemList.TwilightSword.get(1));
    }

    public static void loadPostInit() {
        new MilledOre();
    }
}
