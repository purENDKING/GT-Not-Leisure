package com.science.gtnl.common.machine.DSP;

import com.science.gtnl.config.MainConfig;

public final class DSP_Values {

    public static final double secondsOfArtificialStarProgressCycleTime = MainConfig.secondsOfArtificialStarProgressCycleTime;// default
    // 6.4
    public static final byte EnableRenderDefaultArtificialStar = (byte) (MainConfig.enableRenderDefaultArtificialStar
        ? 1
        : 0);
}
