package com.ordana.immersive_weathering.network;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.mehvahdjukaar.moonlight.api.platform.network.ChannelHandler;
import net.mehvahdjukaar.moonlight.api.platform.network.NetworkDir;

public class NetworkHandler {

    public static ChannelHandler CHANNEL;


    public static void registerMessages() {

        CHANNEL = ChannelHandler.createChannel(ImmersiveWeathering.res("network1"));


        CHANNEL.register(NetworkDir.PLAY_TO_CLIENT,
                SendCustomParticlesPacket.class, SendCustomParticlesPacket::new);


    }


}