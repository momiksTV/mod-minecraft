package com.wavesurvival.mod.network.packets;

import com.wavesurvival.mod.gui.ShopMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

public class OpenShopPacket {
    public static void encode(OpenShopPacket pkt, FriendlyByteBuf buf) {}
    public static OpenShopPacket decode(FriendlyByteBuf buf) { return new OpenShopPacket(); }

    public static void handle(OpenShopPacket pkt, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                NetworkHooks.openScreen(player,
                        new SimpleMenuProvider((id, inv, p) -> new ShopMenu(id, inv),
                                Component.literal("§6Магазин")));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
