package com.science.gtnl.mixins;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EarlyMixinPlugin {

    public static List<String> getEarlyMixins(Set<String> loadedMods) {

        final List<String> mixins = new ArrayList<>();
        mixins.add("Minecraft_Mixin");
        mixins.add("World_Mixin");
        mixins.add("AccessorEntityRenderer");
        mixins.add("MixinWorldUpdateEntities_Wrap");
        mixins.add("render.TextureAtlasSprite_Mixin");
        mixins.add("render.TextureMap_Mixin");
        mixins.add("render.RenderItemFrame_Mixin");
        mixins.add("render.TextureCompass_Mixin");
        mixins.add("render.ItemStack_Mixin");
        return mixins;
    }
}
