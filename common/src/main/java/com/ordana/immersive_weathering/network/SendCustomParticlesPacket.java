package com.ordana.immersive_weathering.network;

import com.ordana.immersive_weathering.WeatheringHelper;
import com.ordana.immersive_weathering.configs.ClientConfigs;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mehvahdjukaar.moonlight.api.platform.network.ChannelHandler;
import net.mehvahdjukaar.moonlight.api.platform.network.Message;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SendCustomParticlesPacket implements Message {

    private final EventType type;
    private final int extraData;
    private final BlockPos pos;

    public SendCustomParticlesPacket(FriendlyByteBuf buffer) {
        this.extraData = buffer.readInt();
        this.type = EventType.values()[buffer.readByte()];
        this.pos = buffer.readBlockPos();
    }

    public SendCustomParticlesPacket(EventType type, BlockPos pos, int extraData) {
        this.type = type;
        this.pos = pos;
        this.extraData = extraData;
    }

    @Override
    public void writeToBuffer(FriendlyByteBuf buf) {
        buf.writeInt(this.extraData);
        buf.writeByte(type.ordinal());
        buf.writeBlockPos(pos);
    }

    @Override
    public void handle(ChannelHandler.Context context) {
        clientStuff(context.getSender(), type, pos, extraData);
    }

    @Environment(EnvType.CLIENT)
    public void clientStuff(Player player, EventType type, BlockPos pos, int extraData) {
        var level = player.level;
        if (type == EventType.DECAY_LEAVES) {
            if (ClientConfigs.LEAF_DECAY_PARTICLES.get()) {
                BlockState state = Block.stateById(extraData);
                var leafParticle = WeatheringHelper.getFallenLeafParticle(state).orElse(null);
                if (leafParticle == null) return;
                int color = Minecraft.getInstance().getBlockColors().getColor(state, level, pos, 0);

                //add more than one?
                double d = (double) pos.getX() + level.random.nextDouble();
                double e = (double) pos.getY() - 0.05;
                double f = (double) pos.getZ() + level.random.nextDouble();
                level.addParticle(leafParticle, d, e, f, 0.0, color, 0.0);
            }

            if (ClientConfigs.LEAF_DECAY_SOUND.get()) {
                level.playSound(player, pos, SoundEvents.AZALEA_LEAVES_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
            }

        }
    }

    public enum EventType {
        DECAY_LEAVES
    }
}