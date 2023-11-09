package com.ordana.immersive_weathering.network;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.mehvahdjukaar.moonlight.api.platform.network.ChannelHandler;
import net.mehvahdjukaar.moonlight.api.platform.network.NetworkDir;

public class NetworkHandler {

    public static final ChannelHandler CHANNEL = ChannelHandler.builder(ImmersiveWeathering.MOD_ID)
            .register(NetworkDir.PLAY_TO_CLIENT, SendCustomParticlesPacket.class, SendCustomParticlesPacket::new)
            .build();


    public static void init() {
    }


}