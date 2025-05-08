package com.science.gtnl.common.recipe.Thaumcraft;

import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;
import static com.science.gtnl.common.recipe.Thaumcraft.TCRecipePool.infusionRecipeTimeStopPocketWatch;

import net.minecraft.util.ResourceLocation;

import com.science.gtnl.common.GTNLItemList;

import gregtech.api.enums.Mods;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

public class TCResearches {

    public static void register() {
        loadResearchTab();
        loadResearches();
    }

    public static void loadResearchTab() {
        ResearchCategories.registerCategory(
            "GTNL",
            new ResourceLocation(RESOURCE_ROOT_ID, "textures/items/TestItem.png"),
            new ResourceLocation("thaumicinsurgence", "textures/gui/eldritch_bg.png"));
    }

    public static void loadResearches() {
        new ResearchItem(
            "GTNL_WELCOME",
            "GTNL",
            (new AspectList()),
            0,
            0,
            0,
            new ResourceLocation(RESOURCE_ROOT_ID, "textures/items/Record.newhorizons.png")).setAutoUnlock()
                .registerResearchItem()
                .setPages(new ResearchPage("tc.research_text.GTNL_WELCOME.1"))
                .setSpecial();

        new ResearchItem(
            "GTNL_TIME_STOP_POCKET_WATCH",
            "GTNL",
            (new AspectList()).merge(Mods.MagicBees.isModLoaded() ? Aspect.getAspect("tempus") : Aspect.MAGIC, 2)
                .merge(Aspect.ORDER, 1)
                .merge(Aspect.MAGIC, 1)
                .merge(Aspect.TRAVEL, 1)
                .merge(Aspect.ENERGY, 1)
                .merge(Aspect.VOID, 1),
            -1,
            -2,
            5,
            GTNLItemList.TimeStopPocketWatch.get(1))
                .setPages(
                    new ResearchPage("tc.research_text.GTNL_TIME_STOP_POCKET_WATCH.1"),
                    new ResearchPage(infusionRecipeTimeStopPocketWatch))
                .setParents("GTNL_WELCOME")
                .registerResearchItem();
    }
}
