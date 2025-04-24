package com.science.gtnl.mixins;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.science.gtnl.Mods;

public class EarlyMixinPlugin {

    public static List<String> getEarlyMixins(Set<String> loadedMods) {
        final List<String> mixins = new ArrayList<>();
        if (!Mods.GiveCount.isModLoaded()) {
            mixins.add("NEIServerUtils_Mixin");
        }
        return mixins;
    }
}
