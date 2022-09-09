package net.mehvahdjukaar.moonlight.api.platform.network;

import net.minecraft.network.FriendlyByteBuf;

public interface Message {

    void writeToBuffer(FriendlyByteBuf buf);

    void handle(ChannelHandler.Context context);
}