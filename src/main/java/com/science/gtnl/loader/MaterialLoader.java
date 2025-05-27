package com.science.gtnl.loader;

import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.common.item.items.MilledOre;
import com.science.gtnl.common.materials.MaterialPool;

import bartworks.API.WerkstoffAdderRegistry;
import codechicken.nei.api.API;

public class MaterialLoader {

    public static void loadPreInit() {

        ItemLoader.registry();
        ItemLoader.registryOreDictionary();
        ItemLoader.registryOreBlackList();
        WerkstoffAdderRegistry.addWerkstoffAdder(new MaterialPool());

        BlockLoader.registry();
        BlockLoader.registryAnotherData();
        BlockLoader.registerTreeBrickuoia();

        API.hideItem(GTNLItemList.NanoPhagocytosisPlantRender.get(1));
        API.hideItem(GTNLItemList.ArtificialStarRender.get(1));
        API.hideItem(GTNLItemList.TwilightSword.get(1));
    }

    public static void loadPostInit() {
        new MilledOre();
    }
}
