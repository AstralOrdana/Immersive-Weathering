package com.ordana.immersive_weathering.network;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;

public class NetworkHandler {

    public static void init(IEventBus modEventBus) {
        modEventBus.addListener(NetworkHandler::register);
    }

    private static void register(RegisterPayloadHandlersEvent event) {
        event.registrar(ImmersiveWeathering.MOD_ID)
                .executesOn(HandlerThread.MAIN)
                .playToClient(SendCustomParticlesPacket.TYPE, SendCustomParticlesPacket.STREAM_CODEC, SendCustomParticlesPacket::handle);
    }

    public static void sendToAllClientPlayersInRange(ServerLevel level, BlockPos pos, double range, CustomPacketPayload payload) {
        PacketDistributor.sendToPlayersNear(level, null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, range, payload);
    }

}