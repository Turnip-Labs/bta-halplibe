package turniplabs.halplibe.util.deathcause;

import com.mojang.nbt.tags.CompoundTag;
import org.jspecify.annotations.NonNull;
import turniplabs.halplibe.helper.network.NetworkMessage;
import turniplabs.halplibe.helper.network.UniversalPacket;

import java.util.Objects;

public class DeathCauseNetworkMessage implements NetworkMessage {

    private CompoundTag deathCauseEncoded;
    private String deathCauseId;

    public DeathCauseNetworkMessage() {}

    public DeathCauseNetworkMessage(DeathCause deathCause) {
        this.deathCauseId = DeathCauseRegistry.getInstance().getKeyForClass(deathCause.getClass());
        this.deathCauseEncoded = new CompoundTag();
        deathCause.serialize(this.deathCauseEncoded);
    }

    @Override
    public void encodeToUniversalPacket(@NonNull UniversalPacket packet) {
        packet.writeString(this.deathCauseId);
        packet.writeCompoundTag(this.deathCauseEncoded);
    }

    @Override
    public void decodeFromUniversalPacket(@NonNull UniversalPacket packet) {
        this.deathCauseId = packet.readString();
        this.deathCauseEncoded = packet.readCompoundTag();
    }

    @Override
    public void handleClientEnv(NetworkContext context) {
        final DeathCause deathCause = Objects.requireNonNull(DeathCauseRegistry.getInstance().getItem(this.deathCauseId)).get();
        deathCause.deserialize(this.deathCauseEncoded);
        deathCause.sendMessage(context.player);
    }
}
