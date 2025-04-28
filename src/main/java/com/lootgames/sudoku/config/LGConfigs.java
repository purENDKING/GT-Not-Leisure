package com.lootgames.sudoku.config;

public class LGConfigs {

    public static final ConfigSudoku SUDOKU = new ConfigSudoku();

    public static void load() {
        SUDOKU.load();
    }
}
