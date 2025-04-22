package com.science.gtnl.asm;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gtnewhorizon.gtnhmixins.IEarlyMixinLoader;
import com.science.gtnl.mixins.EarlyMixinPlugin;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.TransformerExclusions({ "com.science.gtnl.asm" })
@IFMLLoadingPlugin.Name("GTNL core plugin")
public class GTNLEarlyCoreMod implements IFMLLoadingPlugin, IEarlyMixinLoader {

    @Override
    public String[] getASMTransformerClass() {
        return null;
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    public String getMixinConfig() {
        return "mixins.ScienceNotLeisure.early.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedCoreMods) {
        return EarlyMixinPlugin.getEarlyMixins(loadedCoreMods);
    }
}
