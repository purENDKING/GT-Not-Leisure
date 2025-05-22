package com.science.gtnl.mixins;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.science.gtnl.config.MainConfig;

public class EarlyMixinPlugin {

    public static List<String> getEarlyMixins(Set<String> loadedMods) {
        final List<String> mixins = new ArrayList<>();
        mixins.add("AccessorEntityRenderer");
        mixins.add("Entity_Mixin");
        mixins.add("Minecraft_Mixin");
        mixins.add("World_Mixin");
        mixins.add("WorldClient_Mixin");
        mixins.add("WorldServer_Mixin");
        mixins.add("FMLProxyPacket_Mixin");
        mixins.add("Explosion_Mixin");
        mixins.add("ForgeHook_Mixin");

        if (MainConfig.enableSpecialCheatIcon) {
            mixins.add("NotEnoughItems.DrawableBuilderAccessor");
            mixins.add("NotEnoughItems.DrawableResourceAccessor");
            mixins.add("NotEnoughItems.DrawableResource_Mixin");
            mixins.add("NotEnoughItems.LayoutStyleMinecraft_Mixin");
            mixins.add("NotEnoughItems.LayoutManager_Mixin");
        }
        return mixins;
    }
}
