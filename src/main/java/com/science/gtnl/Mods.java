package com.science.gtnl;

import java.util.Locale;

import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.common.Loader;

public enum Mods {

    ScienceNotLeisure(Names.SCIENCENOTLEISURE),
    EyeOfHarmonyBuffer(Names.EYEOFHARMONYBUFFER),
    ProgrammableHatches(Names.PROGRAMMABLEHATCHES),
    TwistSpaceTechnology(Names.TWISTSPACETECHNOLOGY),
    BoxPlusPlus(Names.BOXPLUSPLUS),
    NHUtilities(Names.NHUTILITIES),
    AE2Thing(Names.AE2THING),
    QzMiner(Names.QZMINER),
    OTHTechnology(Names.OTHTECHNOLOGY),
    Baubles(Names.BAUBLES),
    Overpowered(Names.OVERPOWERED),
    GiveCount(Names.GIVECOUNT);

    public static class Names {

        public static final String SCIENCENOTLEISURE = "ScienceNotLeisure";
        public static final String EYEOFHARMONYBUFFER = "eyeofharmonybuffer";
        public static final String PROGRAMMABLEHATCHES = "programmablehatches";
        public static final String TWISTSPACETECHNOLOGY = "TwistSpaceTechnology";
        public static final String BOXPLUSPLUS = "boxplusplus";
        public static final String NHUTILITIES = "NHUtilities";
        public static final String AE2THING = "ae2thing";
        public static final String QZMINER = "qz_miner";
        public static final String OTHTECHNOLOGY = "123Technology";
        public static final String BAUBLES = "Baubles";
        public static final String OVERPOWERED = "Overpowered";
        public static final String GIVECOUNT = "givecount";
    }

    public final String ID;
    public final String resourceDomain;
    public Boolean modLoaded;

    Mods(String ID) {
        this.ID = ID;
        this.resourceDomain = ID.toLowerCase(Locale.ENGLISH);
    }

    public boolean isModLoaded() {
        if (this.modLoaded == null) {
            this.modLoaded = Loader.isModLoaded(ID);
        }
        return this.modLoaded;
    }

    public String getResourcePath(String... path) {
        return this.getResourceLocation(path)
            .toString();
    }

    public ResourceLocation getResourceLocation(String... path) {
        return new ResourceLocation(this.resourceDomain, String.join("/", path));
    }
}
