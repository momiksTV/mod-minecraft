package com.wavesurvival.mod.gui;

import com.mojang.blaze3d.platform.InputConstants;
import com.wavesurvival.mod.core.WaveSurvivalMod;
import com.wavesurvival.mod.network.NetworkHandler;
import com.wavesurvival.mod.network.packets.OpenShopPacket;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = WaveSurvivalMod.MOD_ID, value = Dist.CLIENT)
public class KeyBindings {

    public static KeyMapping OPEN_SHOP;

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        OPEN_SHOP = new KeyMapping(
                "key.wavesurvival.shop",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_M,
                "key.categories.wavesurvival"
        );
        event.register(OPEN_SHOP);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (OPEN_SHOP != null && OPEN_SHOP.consumeClick()) {
            if (Minecraft.getInstance().screen == null) {
                NetworkHandler.CHANNEL.sendToServer(new OpenShopPacket());
            }
        }
    }
}
