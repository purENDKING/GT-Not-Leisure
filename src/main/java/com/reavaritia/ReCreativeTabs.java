package com.reavaritia;

import net.minecraft.item.Item;

import com.reavaritia.common.ItemLoader;

public class ReCreativeTabs {

    public static net.minecraft.creativetab.CreativeTabs ReAvaritia = new net.minecraft.creativetab.CreativeTabs(
        "ReAvaritia") {

        @Override
        public Item getTabIconItem() {
            return ItemLoader.InfinityPickaxe;
        }
    };

}
