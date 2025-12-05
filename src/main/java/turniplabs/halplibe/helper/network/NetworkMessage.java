package turniplabs.halplibe.helper.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.entity.player.Player;
import org.jspecify.annotations.NonNull;

public interface NetworkMessage {
	/**
	 * Encode the UniversalPacket into your NetworkMessage.
	 * This may be called on any thread, so this should be a pure operation.
	 *
	 * @param packet The packet to write data to.
	 */
	void encodeToUniversalPacket(@NonNull UniversalPacket packet );

	/**
	 * Decode the UniversalPacket into your NetworkMessage.
	 * This may be called on any thread, so this should be a pure operation.
	 *
	 * @param packet The packet to read data from.
	 */
	void decodeFromUniversalPacket(@NonNull UniversalPacket packet );

	/**
	 * Is called when this {@link NetworkMessage} is received.
	 * This is common for both Env and is the recommended handle if you don't try to use object exclusive to a specific Env
	 *
	 * @param context An intermediary representation of Packet handler common on both Client and Server environment.
	 */
	default void handle(NetworkContext context) {}

	/**
	 * Is called when this {@link NetworkMessage} is received with an extra check for preventing loading unavailable class for Server Env.
	 * This is called after the common handle method.
	 * NOTE: Single player doesn't execute this since Server Env is never available
	 *
	 * @param context An intermediary representation of Packet handler common on both Client and Server environment.
	 */
	@Environment(EnvType.SERVER)
	default void handleServerEnv(NetworkContext context) {}

	/**
	 * Is called when this {@link NetworkMessage} is received with an extra check for preventing loading unavailable class for Client Env.
	 * This is called after the common handle method.
	 * NOTE: Single player does execute this since Client Env is always available
	 *
	 * @param context An intermediary representation of Packet handler common on both Client and Server environment.
	 */
	@Environment(EnvType.CLIENT)
	default void handleClientEnv(NetworkContext context) {}

	class NetworkContext {
		/**
		 * The player that send the NetworkPacket to the handle
		 */
		public Player player;

		public NetworkContext(Player player) {
			this.player = player;
		}
	}

}
