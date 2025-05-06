package com.science.gtnl.common.item;

public class TimeStopManager {

    public static boolean timeStopped = false;

    public static boolean isTimeStopped() {
        return timeStopped;
    }

    public static void setTimeStopped(boolean stopped) {
        timeStopped = stopped;
    }
}
