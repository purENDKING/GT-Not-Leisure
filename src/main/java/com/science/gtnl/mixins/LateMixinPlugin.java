package com.science.gtnl.mixins;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;

@LateMixin
@SuppressWarnings("unused")
public class LateMixinPlugin implements ILateMixinLoader {

    @Override
    public String getMixinConfig() {
        return "mixins.ScienceNotLeisure.late.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedMods) {
        var mixins = new ArrayList<String>();
        mixins.add("AssLineRemover.GTRecipeBuilderHook_Mixin");
        mixins.add("AssLineRemover.ForGTPreLoadHook_Mixin");
        mixins.add("AssLineRemover.TTAssLineBuilderHook_Mixin");
        mixins.add("Bartwork.BartworkLoad_Return_Mixin");
        mixins.add("Bartwork.BartworkLoad_Head_Mixin");
        mixins.add("Bartwork.WerkstoffLoader_Mixin");
        mixins.add("Bartwork.Werkstoff_Mixin");
        mixins.add("Bartwork.MultipleMetalLoader_Mixin");
        mixins.add("Bartwork.SimpleMetalLoader_Mixin");
        mixins.add("Bartwork.CircuitImprintLoader_Mixin");
        mixins.add("MTETreeFarm_Mixin");
        mixins.add("ItemBloodSword_Mixin");
        mixins.add("DraconicEvolutionEventHandler_Mixin");
        return mixins;
    }

}
