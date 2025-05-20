package com.science.gtnl.mixins;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EarlyMixinPlugin {

    public static List<String> getEarlyMixins(Set<String> loadedMods) {
        final List<String> mixins = new ArrayList<>();
        mixins.add("AccessorEntityRenderer");
        mixins.add("Entity_Mixin");
        mixins.add("Minecraft_Mixin");
        mixins.add("MixinWorldUpdateEntities_Wrap");
        mixins.add("World_Mixin");
        mixins.add("WorldClient_Mixin");
        mixins.add("WorldServer_Mixin");
        mixins.add("FMLProxyPacket_Mixin");
        mixins.add("Explosion_Mixin");
        mixins.add("ForgeHooksClient_Mixin");
        mixins.add("ForgeHookServer_Mixin");
        return mixins;
    }
}
