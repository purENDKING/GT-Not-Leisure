package com.science.gtnl.common.machine.hatch;

public interface IConfigurationMaintenance {

    boolean isConfiguration();

    int getMinConfigTime();

    int getMaxConfigTime();

    int getConfigTime();

}
