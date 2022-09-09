package net.mehvahdjukaar.moonlight.api.platform.network.fabric;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;
import net.mehvahdjukaar.moonlight.api.platform.network.ChannelHandler;
import net.mehvahdjukaar.moonlight.api.platform.network.Message;
import net.mehvahdjukaar.moonlight.api.platform.network.NetworkDir;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ChannelHandlerImpl extends ChannelHandler {

    public static ChannelHandler createChannel(ResourceLocation channelMame) {
        return new ChannelHandlerImpl(channelMame);
    }

    private int id = 0;

    public ChannelHandlerImpl(ResourceLocation channelName) {
        super(channelName);
    }

    public static Map<Class<?>, ResourceLocation> ID_MAP = new HashMap<>();

    @Override
    public <M extends Message> void register(
            NetworkDir direction,
            Class<M> messageClass,
            Function<FriendlyByteBuf, M> decoder) {

        ResourceLocation res = new ResourceLocation(this.channelName.getNamespace(), "" + id++);
        ID_MAP.put(messageClass, res);

        if (direction == NetworkDir.PLAY_TO_SERVER) {
            ServerPlayNetworking.registerGlobalReceiver(
                    res, (server, player, h, buf, r) -> {
                        M message = decoder.apply(buf);
                        server.execute(() -> message.handle(new Wrapper(player, direction)));
                    });
        } else {
            if (PlatformHelper.getEnv().isClient()) FabricClientNetwork.register(res, decoder);
        }
    }


    static class Wrapper implements Context {

        private final Player player;
        private final NetworkDir dir;

        public Wrapper(Player player, NetworkDir dir) {
            this.player = player;
            this.dir = dir;
        }

        @Override
        public NetworkDir getDirection() {
            return dir;
        }

        @Override
        public Player getSender() {
            return player;
        }
    }

    @Override
    public void sendToClientPlayer(ServerPlayer serverPlayer, Message message) {
        //for (ServerPlayer player : PlayerLookup.tracking(entity)) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        message.writeToBuffer(buf);
        ServerPlayNetworking.send(serverPlayer, ID_MAP.get(message.getClass()), buf);
        // }
    }

    @Override
    public void sendToAllClientPlayers(Message message) {
        for (var p : PlatformHelper.getCurrentServer().getPlayerList().getPlayers()) {
            sendToClientPlayer(p, message);
        }
    }

    @Override
    public void sendToServer(Message message) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        message.writeToBuffer(buf);
        ClientPlayNetworking.send(ID_MAP.get(message.getClass()), buf);
    }

    @Override
    public void sendToAllClientPlayersInRange(Level level, BlockPos pos, double radius, Message message) {

        MinecraftServer currentServer = PlatformHelper.getCurrentServer();
        if (currentServer != null) {
            PlayerList players = currentServer.getPlayerList();
            var dimension = level.dimension();

            players.broadcast(null, pos.getX(), pos.getY(), pos.getZ(),
                    radius, dimension, toVanillaPacket(message));
        }
    }

    @Override
    public void sentToAllClientPlayersTrackingEntity(Entity target, Message message) {
        if (target.level instanceof ServerLevel serverLevel) {
            serverLevel.getChunkSource().broadcast(target, toVanillaPacket(message));
        }
    }

    @Override
    public void sentToAllClientPlayersTrackingEntityAndSelf(Entity target, Message message) {
        if (target.level instanceof ServerLevel serverLevel) {
            var p = toVanillaPacket(message);
            serverLevel.getChunkSource().broadcast(target, p);
            if (target instanceof ServerPlayer player) {
                sendToClientPlayer(player, message);
            }
        }
    }

    private Packet<?> toVanillaPacket(Message message) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        message.writeToBuffer(buf);
        return ServerPlayNetworking.createS2CPacket(ID_MAP.get(message.getClass()), buf);
    }
}
