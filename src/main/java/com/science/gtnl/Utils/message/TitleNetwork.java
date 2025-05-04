package com.science.gtnl.Utils.message;

import static com.science.gtnl.ScienceNotLeisure.network;

import cpw.mods.fml.relauncher.Side;

public class TitleNetwork {

    public static void init() {
        network.registerMessage(TitlePacket.Handler.class, TitlePacket.class, 0, Side.CLIENT);
    }
}
