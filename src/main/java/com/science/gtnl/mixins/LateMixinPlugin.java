package com.science.gtnl.mixins;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;
import com.science.gtnl.Mods;
import com.science.gtnl.config.MainConfig;

@LateMixin
@SuppressWarnings("unused")
public class LateMixinPlugin implements ILateMixinLoader {

    @Override
    public String getMixinConfig() {
        return "mixins.sciencenotleisure.late.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedMods) {
        var mixins = new ArrayList<String>();
        mixins.add("Gregtech.AssLineRemover.GTRecipeBuilderHook_Mixin");
        mixins.add("Gregtech.AssLineRemover.ForGTPreLoadHook_Mixin");
        mixins.add("Gregtech.AssLineRemover.TTAssLineBuilderHook_Mixin");
        mixins.add("Gregtech.MTETreeFarm_Mixin");
        mixins.add("Gregtech.OverclockCalculator_Mixin");
        mixins.add("Bartwork.BartworkLoad_Head_Mixin");
        mixins.add("Bartwork.WerkstoffLoader_Mixin");
        mixins.add("Bartwork.Werkstoff_Mixin");
        mixins.add("Bartwork.MultipleMetalLoader_Mixin");
        mixins.add("Bartwork.SimpleMetalLoader_Mixin");
        mixins.add("Bartwork.CircuitImprintLoader_Mixin");
        mixins.add("ItemBloodSword_Mixin");
        mixins.add("DraconicEvolutionEventHandler_Mixin");
        mixins.add("BacteriaRegistry_Mixin");
        mixins.add("MeteorParadigm_Mixin");

        if (!Mods.Overpowered.isModLoaded() && MainConfig.enableRecipeOutputChance) {
            mixins.add("Gregtech.VoltageChanceBonus_GT_ParallelHelper_Mixin");
            mixins.add("Gregtech.BehaviourScanner_Mixin");
        }

        if (!Mods.NHUtilities.isModLoaded()) {
            mixins.add("Gregtech.BaseMetaTileEntityAcceleration_Mixin");
            mixins.add("Gregtech.MTEAdvAssLineAcceleration_Mixin");
            mixins.add("Gregtech.ResearchStationAcceleration_Mixin");
            mixins.add("EnderIO.AccelerateEnergyReceive_Mixin");
            mixins.add("EnderIO.AccelerateTileEntity_Mixin");
            mixins.add("EnderIO.Modify_CapBankMaxIO_Mixin");
        }

        return mixins;
    }

}
