package net.mehvahdjukaar.moonlight.api.platform.network.forge;

import net.mehvahdjukaar.moonlight.api.platform.network.ChannelHandler;
import net.mehvahdjukaar.moonlight.api.platform.network.Message;
import net.mehvahdjukaar.moonlight.api.platform.network.NetworkDir;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class ChannelHandlerImpl extends ChannelHandler {

    //needs to be concurrent as mod loading happens in parallel and these are created there
    static Map<ResourceLocation, ChannelHandler> CHANNELS = new ConcurrentHashMap<>();

    public static ChannelHandler createChannel(ResourceLocation channelMame) {
        return CHANNELS.computeIfAbsent(channelMame, c -> new ChannelHandlerImpl(channelMame));
    }

    public final SimpleChannel channel;
    public int id = 0;

    public ChannelHandlerImpl(ResourceLocation channelName) {
        super(channelName);
        String version = "1";
        this.channel = NetworkRegistry.newSimpleChannel(channelName, () -> version,
                version::equals, version::equals);
    }

    @Override
    public <M extends Message> void register(
            NetworkDir dir,
            Class<M> messageClass,
            Function<FriendlyByteBuf, M> decoder) {

        NetworkDirection d = dir == NetworkDir.PLAY_TO_SERVER ? NetworkDirection.PLAY_TO_SERVER : NetworkDirection.PLAY_TO_CLIENT;
        channel.registerMessage(id++, messageClass, Message::writeToBuffer, decoder, this::consumer, Optional.of(d));
    }

    private <M extends Message> void consumer(M message, Supplier<NetworkEvent.Context> context) {
        var c = context.get();
        c.enqueueWork(() -> message.handle(new Wrapper(c)));
        c.setPacketHandled(true);
    }

    static class Wrapper implements Context {

        private final NetworkEvent.Context context;

        public Wrapper(NetworkEvent.Context ctx) {
            this.context = ctx;
        }

        @Override
        public NetworkDir getDirection() {
            return switch (context.getDirection()) {
                case PLAY_TO_CLIENT -> NetworkDir.PLAY_TO_CLIENT;
                default -> NetworkDir.PLAY_TO_SERVER;
            };
        }

        @Override
        public Player getSender() {
            return context.getSender();
        }
    }


    public void sendToClientPlayer(ServerPlayer serverPlayer, Message message) {
        channel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), message);
    }

    @Override
    public void sendToAllClientPlayers(Message message) {
        channel.send(PacketDistributor.ALL.noArg(), message);
    }

    @Override
    public void sendToServer(Message message) {
        channel.sendToServer(message);
    }

    @Override
    public void sendToAllClientPlayersInRange(Level level, BlockPos pos, double radius, Message message) {
        MinecraftServer currentServer = ServerLifecycleHooks.getCurrentServer();
        if (currentServer != null) {
            var distributor = PacketDistributor.NEAR.with(() ->
                    new PacketDistributor.TargetPoint(pos.getX(), pos.getY(), pos.getZ(), radius, level.dimension()));
            channel.send(distributor, message);
        }
    }

    @Override
    public void sentToAllClientPlayersTrackingEntity(Entity target, Message message) {
        channel.send(PacketDistributor.TRACKING_ENTITY.with(() -> target), message);
    }

    @Override
    public void sentToAllClientPlayersTrackingEntityAndSelf(Entity target, Message message) {
        channel.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> target), message);
    }

}

