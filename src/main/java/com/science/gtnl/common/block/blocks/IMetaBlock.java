package com.science.gtnl.common.block.blocks;

import java.util.Map;
import java.util.Set;

import net.minecraft.util.IIcon;

public interface IMetaBlock {

    Set<Integer> getUsedMetaSet();

    Map<Integer, String[]> getTooltipsMap();

    Map<Integer, IIcon> getIconMap();

    default String[] getTooltips(int meta) {
        return getTooltipsMap().get(meta);
    }

}
