package com.science.gtnl.mixins;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cpw.mods.fml.common.Loader;

public class EarlyMixinPlugin {

    public static List<String> getEarlyMixins(Set<String> loadedMods) {
        final List<String> mixins = new ArrayList<>();
        if (!Loader.isModLoaded("givecount")) {
            mixins.add("NEIServerUtils_Mixin");
        }
        return mixins;
    }
}
