package com.wavesurvival.mod.network;

import com.wavesurvival.mod.core.WaveSurvivalMod;
import com.wavesurvival.mod.network.packets.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {

    private static final String PROTOCOL = "1";
    public static SimpleChannel CHANNEL;

    public static void register() {
        CHANNEL = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(WaveSurvivalMod.MOD_ID, "main"),
                () -> PROTOCOL,
                PROTOCOL::equals,
                PROTOCOL::equals
        );

        int id = 0;
        CHANNEL.registerMessage(id++, SyncWaveDataPacket.class,
                SyncWaveDataPacket::encode, SyncWaveDataPacket::decode, SyncWaveDataPacket::handle);
        CHANNEL.registerMessage(id++, SyncCoinPacket.class,
                SyncCoinPacket::encode, SyncCoinPacket::decode, SyncCoinPacket::handle);
        CHANNEL.registerMessage(id++, OpenShopPacket.class,
                OpenShopPacket::encode, OpenShopPacket::decode, OpenShopPacket::handle);
        CHANNEL.registerMessage(id++, BuyItemPacket.class,
                BuyItemPacket::encode, BuyItemPacket::decode, BuyItemPacket::handle);
    }
}
