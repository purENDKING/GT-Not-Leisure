package com.science.gtnl.loader;

import com.science.gtnl.common.machine.multiblock.RealArtificialStar;
import com.science.gtnl.common.machine.multiblock.TeleportationArrayToAlfheim;
import com.science.gtnl.misc.CheckRecipeResults.CheckRecipeResults;

public class LazyStaticsInitLoader {

    public void initStaticsOnInit() {
        CheckRecipeResults.initStatics();
    }

    public void initStaticsOnCompleteInit() {
        RealArtificialStar.initStatics();
    }
}
