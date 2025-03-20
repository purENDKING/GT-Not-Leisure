package com.science.gtnl.common.block.blocks.playerDoll;

import cpw.mods.fml.common.event.FMLInterModComms;
import gregtech.api.enums.Mods;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

public class PlayerDollWaila {

    public static void callbackRegister(IWailaRegistrar registrar) {
        final IWailaDataProvider playerDollProvider = new PlayerDollWailaDataProvider();

        // 注册 PlayerDoll 的 WAILA 提供器
        registrar.registerBodyProvider(playerDollProvider, TileEntityPlayerDoll.class);
        registrar.registerNBTProvider(playerDollProvider, TileEntityPlayerDoll.class);
        registrar.registerTailProvider(playerDollProvider, TileEntityPlayerDoll.class);
    }

    public static void init() {
        FMLInterModComms.sendMessage(Mods.Waila.ID, "register", PlayerDollWaila.class.getName() + ".callbackRegister");
    }
}
