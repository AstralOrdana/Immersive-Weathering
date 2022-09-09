package net.mehvahdjukaar.moonlight.network;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.mehvahdjukaar.moonlight.api.platform.network.ChannelHandler;
import net.mehvahdjukaar.moonlight.api.platform.network.NetworkDir;
import net.minecraft.resources.ResourceLocation;


public class ModMessages {

    public static ChannelHandler CHANNEL;

    public static void registerMessages() {
        CHANNEL = ChannelHandler.createChannel(ImmersiveWeathering.res("channel"));

        CHANNEL.register(NetworkDir.PLAY_TO_CLIENT,
                ClientBoundSyncConfigsMessage.class, ClientBoundSyncConfigsMessage::new);
    }

}