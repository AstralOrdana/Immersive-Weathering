package net.mehvahdjukaar.moonlight.api.platform.network.fabric;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.mehvahdjukaar.moonlight.api.platform.network.Message;
import net.mehvahdjukaar.moonlight.api.platform.network.NetworkDir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class FabricClientNetwork {
    public static <M extends Message> void register(
            ResourceLocation res,
            Function<FriendlyByteBuf, M> decoder) {

        ClientPlayNetworking.registerGlobalReceiver(res,(client, handler, buf, r) ->
                handlePacket(decoder, client,handler, buf, r));

    }

    public static <M extends Message> void handlePacket(Function<FriendlyByteBuf, M> decoder, Minecraft client, ClientPacketListener listener, FriendlyByteBuf buf, PacketSender sender) {
        M message = decoder.apply(buf);
        client.execute(() -> message.handle(new ChannelHandlerImpl.Wrapper(client.player, NetworkDir.PLAY_TO_CLIENT)));
    }
}
