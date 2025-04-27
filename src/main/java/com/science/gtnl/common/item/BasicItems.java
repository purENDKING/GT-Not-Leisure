package com.science.gtnl.common.item;

import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;

import net.minecraft.item.Item;

import com.science.gtnl.client.GTNLCreativeTabs;

public final class BasicItems {

    public static final Item MetaItem = new ItemAdder("MetaItemBase", "MetaItem", GTNLCreativeTabs.GTNotLeisureItem)
        .setTextureName(RESOURCE_ROOT_ID + ":" + "MetaItem/0");
}
