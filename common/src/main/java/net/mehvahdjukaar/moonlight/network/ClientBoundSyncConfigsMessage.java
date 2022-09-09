package net.mehvahdjukaar.moonlight.network;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigSpec;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigType;
import net.mehvahdjukaar.moonlight.api.platform.network.ChannelHandler;
import net.mehvahdjukaar.moonlight.api.platform.network.Message;
import net.minecraft.network.FriendlyByteBuf;

import java.io.ByteArrayInputStream;

public class ClientBoundSyncConfigsMessage implements Message {

    public final String fineName;
    public final String modId;
    public final byte[] configData;

    public ClientBoundSyncConfigsMessage(FriendlyByteBuf buf) {
        this.modId = buf.readUtf();
        this.fineName = buf.readUtf();
        this.configData = buf.readByteArray();
    }

    public ClientBoundSyncConfigsMessage(final byte[] configFileData, final String fileName, String modId) {
        this.modId = modId;
        this.fineName = fileName;
        this.configData = configFileData;
    }

    @Override
    public void writeToBuffer(FriendlyByteBuf buf) {
        buf.writeUtf(this.modId);
        buf.writeUtf(this.fineName);
        buf.writeByteArray(this.configData);
    }

    @Override
    public void handle(ChannelHandler.Context context) {
        var config = ConfigSpec.getSpec(this.modId, ConfigType.COMMON);
        if (config != null) {
            config.loadFromBytes( new ByteArrayInputStream(this.configData));
            ImmersiveWeathering.LOGGER.info("Synced {} configs", this.fineName);
        } else {
            ImmersiveWeathering.LOGGER.error("Failed to find config file with name {}", this.fineName);
        }
    }


}
