package com.science.gtnl.api;

import com.science.gtnl.common.entity.EntityTimeAccelerator;

/**
 * only used for EntityTimeAccelerator {@link EntityTimeAccelerator#onEntityUpdate()}
 */
public interface ITileEntityTickAcceleration {

    /**
     * <li>true if the tickAcceleration logic should be executed.</li>
     * <li>false if the default TileEntity update method should proceed.</li>
     */
    boolean tickAcceleration(int tickAcceleratedRate);

    /**
     * adaptation to other aspects of the tileEntity
     */
    default int getTickAcceleratedRate() {
        return 1;
    }
}
