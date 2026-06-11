package com.wavesurvival.mod.gui;

import com.wavesurvival.mod.core.WaveSurvivalMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WaveSurvivalMod.MOD_ID, value = Dist.CLIENT)
public class WaveHud {

    @SubscribeEvent
    public static void onRenderHud(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() != VanillaGuiOverlay.PLAYER_HEALTH.type()) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) return;

        GuiGraphics g = event.getGuiGraphics();
        int screenW = mc.getWindow().getGuiScaledWidth();

        int panelW = 160;
        int panelH = 52;
        int px = screenW - panelW - 5;
        int py = 5;

        // Panel background
        g.fill(px, py, px + panelW, py + panelH, 0xAA000000);
        g.fill(px + 1, py + 1, px + panelW - 1, py + panelH - 1, 0xAA0F3460);

        int wave = ClientWaveData.getCurrentWave();
        boolean active = ClientWaveData.isWaveActive();
        int ticks = ClientWaveData.getTicksUntilNextWave();
        int coins = ClientWaveData.getCoins();

        // Wave line
        String waveStr = wave == 0 ? "§7Ожидание..." : "§c⚔ Волна: §f" + wave;
        g.drawString(mc.font, waveStr, px + 5, py + 6, 0xFFFFFF, true);

        // Status line
        String statusStr;
        if (wave == 0) {
            statusStr = "§7Игра начинается...";
        } else if (active) {
            statusStr = "§cИдёт волна!";
        } else {
            int secs = ticks / 20;
            statusStr = "§aПауза: §e" + secs + "с";
        }
        g.drawString(mc.font, statusStr, px + 5, py + 20, 0xFFFFFF, true);

        // Coins line
        g.drawString(mc.font, "§6💰 Монеты: §f" + coins, px + 5, py + 34, 0xFFFFFF, true);

        // Hint line
        g.drawString(mc.font, "§7[M] - Магазин", px + 5, py + 43, 0xAAAAAA, false);
    }
}
