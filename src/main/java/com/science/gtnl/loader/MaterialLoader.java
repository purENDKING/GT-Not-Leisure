package com.science.gtnl.loader;

import bartworks.API.WerkstoffAdderRegistry;
import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.common.block.BlockRegister;
import com.science.gtnl.common.item.ItemRegister;
import com.science.gtnl.common.item.items.MilledOre;

import codechicken.nei.api.API;
import com.science.gtnl.common.materials.MaterialPool;

public class MaterialLoader {

    public static void loadPreInit() {

        ItemRegister.registry();
        new MilledOre();

        BlockRegister.registry();
        BlockRegister.registryAnotherData();
        BlockRegister.registerTreeBrickuoia();

        new RecipeLoaderRunnable().run();

        API.hideItem(GTNLItemList.NanoPhagocytosisPlantRender.get(1));
        API.hideItem(GTNLItemList.ArtificialStarRender.get(1));
        API.hideItem(GTNLItemList.TwilightSword.get(1));
    }

    public static void loadPostInit() {
        WerkstoffAdderRegistry.addWerkstoffAdder(new MaterialPool());
        ItemRegister.registryOreDictionary();
        ItemRegister.registryOreBlackList();
    }
}
