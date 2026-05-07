package com.ordana.immersive_weathering.network;

import com.ordana.immersive_weathering.configs.ClientConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SendCustomParticlesPacket(EventType eventType, BlockPos pos, int extraData) implements CustomPacketPayload {

    public static final Type<SendCustomParticlesPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("immersive_weathering", "custom_particles"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SendCustomParticlesPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            packet -> packet.eventType.ordinal(),
            BlockPos.STREAM_CODEC,
            SendCustomParticlesPacket::pos,
            ByteBufCodecs.VAR_INT,
            SendCustomParticlesPacket::extraData,
            (typeId, pos, extraData) -> new SendCustomParticlesPacket(EventType.byId(typeId), pos, extraData));

    @Override
    public Type<SendCustomParticlesPacket> type() {
        return TYPE;
    }

    public static void handle(SendCustomParticlesPacket payload, IPayloadContext context) {
        context.enqueueWork(() -> payload.clientStuff());
    }

    private void clientStuff() {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        var level = player.level();
        if (eventType == EventType.DECAY_LEAVES) {
            if (ClientConfigs.LEAF_DECAY_PARTICLES.get()) {
                BlockState state = Block.stateById(extraData);
                var leafParticle = new BlockParticleOption(ParticleTypes.BLOCK, state);
                int color = Minecraft.getInstance().getBlockColors().getColor(state, level, pos, 0);

                //add more than one?
                for (int i = 0; i < 20; i++) {
                    double d = pos.getX() + level.random.nextDouble();
                    double e = pos.getY() - 0.05;
                    double f = pos.getZ() + level.random.nextDouble();
                    level.addParticle(leafParticle, d, e, f, 0.0, color, 0.0);
                }
            }

            if (ClientConfigs.LEAF_DECAY_SOUND.get()) {
                level.playSound(player, pos, SoundEvents.AZALEA_LEAVES_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
        }
    }

    public enum EventType {
        DECAY_LEAVES;

        private static EventType byId(int id) {
            EventType[] values = values();
            if (id < 0 || id >= values.length) {
                throw new IllegalArgumentException("Unknown SendCustomParticlesPacket.EventType id: " + id);
            }
            return values[id];
        }
    }
}