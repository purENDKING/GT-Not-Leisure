package com.lootgames.sudoku.packet;

import ru.timeconqueror.lootgames.api.LootGamesAPI;

public class GamePackets {

    public static void register() {

        LootGamesAPI.regServerPacket(SPSSyncCell.class);
        LootGamesAPI.regServerPacket(SPSSyncBoard.class);
        LootGamesAPI.regServerPacket(SPMSSpawnLevelBeatParticles.class);
        LootGamesAPI.regServerPacket(SPMSResetNumber.class);

    }
}
