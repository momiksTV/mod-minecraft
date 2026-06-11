package com.wavesurvival.mod.gui;

public class ClientWaveData {
    private static int currentWave = 0;
    private static boolean waveActive = false;
    private static int ticksUntilNextWave = 0;
    private static int coins = 0;

    public static void update(int wave, boolean active, int ticks) {
        currentWave = wave;
        waveActive = active;
        ticksUntilNextWave = ticks;
    }

    public static void setCoins(int c) { coins = c; }

    public static int getCurrentWave() { return currentWave; }
    public static boolean isWaveActive() { return waveActive; }
    public static int getTicksUntilNextWave() { return ticksUntilNextWave; }
    public static int getCoins() { return coins; }
}
