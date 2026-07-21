package turniplabs.halplibe.helper.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.net.handler.PacketHandlerClient;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.PacketCustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.handler.PacketHandlerServer;
import org.jspecify.annotations.NonNull;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.helper.EnvironmentHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class NetworkHandler {
    private static final List<Supplier<NetworkMessage>> messagesToRegisterForServer = new LinkedList<>(Arrays.asList(
            MessageIdsNetworkMessage::new,
            HandshakeAckNetworkMessage::new
    ));

    private static final Map<Short, BiConsumer<NetworkMessage.NetworkContext, UniversalPacket>> packetReaders = new HashMap<>();
    private static final Map<Class<?>, Short> packetIds = new HashMap<>();

    /**
     * Connections whose client confirmed it can decode the native UniversalPacket (id 88).
     */
    private static final Set<PacketHandlerServer> nativeReadyConnections = Collections.newSetFromMap(Collections.synchronizedMap(new WeakHashMap<>()));

    private NetworkHandler() {
    }

    /**
     * Register the UniversalPacket class and apply the internal messages map
     *
     * @apiNote This method is auto managed by Halplibe
     */
    public static void internalNetworkHandlerSetup() {
        Packet.addMapping(88, true, true, UniversalPacket.class);

        packetReaders.clear();
        packetIds.clear();

        for (Supplier<NetworkMessage> networkMessage : messagesToRegisterForServer) {
            addNetworkMessage(networkMessage);
        }
    }

    /**
     * Receive the universal packet
     *
     * @apiNote This method is auto managed by Halplibe
     */
    public static void internalReceiveUniversalPacket(NetworkMessage.NetworkContext context, @NonNull UniversalPacket buffer) {
        short type = buffer.readShort();

        if (!packetReaders.containsKey(type)) {
            return;
        }

        packetReaders.get(type)
                .accept(context, buffer);
    }

    /**
     * Acknowledge to the server that this client can decode the native UniversalPacket,
     * sent over the compatibility channel.
     *
     * @apiNote This method is auto managed by Halplibe
     */
    @Environment(EnvType.CLIENT)
    public static void internalSendHandshakeAck(@NonNull PacketHandlerClient packetHandler) {
        if (!packetIds.containsKey(HandshakeAckNetworkMessage.class)) {
            return;
        }
        packetHandler.addToSendQueue(generateCompatibilityNetworkMessagePacket(new HandshakeAckNetworkMessage()));
    }

    /**
     * Whether the given Player's client confirmed it can decode the native UniversalPacket.
     */
    @Environment(EnvType.SERVER)
    public static boolean canReceiveNativePackets(Player player) {
        return player instanceof PlayerServer playerServer
                && nativeReadyConnections.contains(playerServer.playerNetServerHandler);
    }

    /**
     * Register a NetworkMessage, and a thread-unsafe handler for it.
     *
     * @param factory The factory for this type of message.
     */
    @SuppressWarnings({"unused"})
    public static void registerNetworkMessage(Supplier<NetworkMessage> factory) {
        messagesToRegisterForServer.add(factory);
    }

    private static <T extends NetworkMessage> void addNetworkMessage(Supplier<T> factory) {
        registerNetworkMessage((short) packetIds.size(), factory);
    }

    private static <T extends NetworkMessage> void registerNetworkMessage(short id, Supplier<T> factory) {
        registerNetworkMessage(id, getType(factory), buf -> {
            T instance = factory.get();
            instance.decodeFromUniversalPacket(buf);
            return instance;
        });
    }

    private static <T extends NetworkMessage> void registerNetworkMessage(short id, Class<T> type, Function<UniversalPacket, T> decoder) {
        packetIds.put(type, id);
        packetReaders.put(id, (context, buf) -> {
            T result = decoder.apply(buf);
            result.handle(context);
            if (EnvironmentHelper.isMultiplayerServer()) {
                result.handleServerEnv(context);
            } else {
                result.handleClientEnv(context);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T> getType(@NonNull Supplier<T> supplier) {
        return (Class<T>) supplier.get()
                .getClass();
    }

    /**
     * Convert a NetworkMessage to an UniversalPacket ready to be sent with BTA net handler.
     * <p>
     * (Prefer using the NetworkHandler send methods for single player message loop back).
     * <p>
     * Example: If you want to use a NetworkMessage for the `getDescriptionPacket` of a `TileEntity`.
     */
    public static @NonNull UniversalPacket generateNetworkMessagePacket(@NonNull NetworkMessage message) {
        UniversalPacket buf = new UniversalPacket();
        buf.writeShort(packetIds.get(message.getClass()));
        message.encodeToUniversalPacket(buf);
        return buf;
    }

    /**
     * Convert a NetworkMessage to a PacketCustomPayload ready to be sent with BTA net handler.
     * <p>
     * (Prefer using the NetworkHandler compatibilities send methods for single player message loop back).
     * <p>
     * Example: If you want to use a NetworkMessage for the `getDescriptionPacket` of a `TileEntity`.
     */
    public static PacketCustomPayload generateCompatibilityNetworkMessagePacket(NetworkMessage message) {
        return generateNetworkMessagePacket(message).toPacketCustomPayload();
    }

    @Environment(EnvType.CLIENT)
    private static void sendToPlayerLocal(@NonNull NetworkMessage message) {
        NetworkMessage.NetworkContext context = new NetworkMessage.NetworkContext(Minecraft.getMinecraft().thePlayer);
        message.handle(context);
        message.handleClientEnv(context);
    }

    @Environment(EnvType.SERVER)
    private static void sendToPlayerServer(Player player, NetworkMessage message, boolean compatibility) {
        if (compatibility || !canReceiveNativePackets(player)) {
            ((PlayerServer) player).playerNetServerHandler.sendPacket(generateCompatibilityNetworkMessagePacket(message));
            return;
        }
        ((PlayerServer) player).playerNetServerHandler.sendPacket(generateNetworkMessagePacket(message));
    }

    @Environment(EnvType.SERVER)
    public static PacketCustomPayload getMessagesConfigurationPacket() {
        return generateCompatibilityNetworkMessagePacket(new MessageIdsNetworkMessage(packetIds));
    }

    /**
     * Send a NetworkMessage to a specific Player from the server.
     * <p>
     * If the receiver never confirmed native UniversalPacket support,
     * the message is sent over the compatibility channel
     * instead, so the client is not kicked for an unrecognized Packet.
     * <p>
     * If we are in SinglePlayer this will skip encoding and directly call the message handle.
     */
    @SuppressWarnings({"unused"})
    public static void sendToPlayer(Player player, NetworkMessage message) {
        if (!EnvironmentHelper.isMultiplayerServer()) {
            sendToPlayerLocal(message);
            return;
        }
        sendToPlayerServer(player, message, false);
    }

    /**
     * Send a NetworkMessage to a specific Player from the server.
     * <p>
     * If the receiver don't have Halplibe installed, the client will not be kick for unrecognized Packet.
     * Prefer to use {@link NetworkHandler#sendToPlayer(Player, NetworkMessage)} if you know your receiver have Halplibe installed.
     * <p>
     * If we are in SinglePlayer this will skip encoding and directly call the message handle.
     */
    @SuppressWarnings({"unused"})
    public static void sendCompatibilityToPlayer(Player player, NetworkMessage message) {
        if (!EnvironmentHelper.isMultiplayerServer()) {
            sendToPlayerLocal(message);
            return;
        }
        sendToPlayerServer(player, message, true);
    }

    /**
     * Send a NetworkMessage to all Players from the server.
     * <p>
     * Receivers that never confirmed native UniversalPacket support
     * get the message over the compatibility channel instead,
     * so nobody is kicked for an unrecognized Packet.
     * <p>
     * If we are in SinglePlayer this will skip encoding and directly call the message handle.
     */
    @SuppressWarnings({"unused"})
    public static void sendToAllPlayers(NetworkMessage message) {
        if (!EnvironmentHelper.isMultiplayerServer()) {
            sendToPlayerLocal(message);
            return;
        }
        UniversalPacket packet = generateNetworkMessagePacket(message);
        PacketCustomPayload compatibilityPacket = packet.toPacketCustomPayload();
        for (PlayerServer player : MinecraftServer.getInstance().playerList.playerEntities) {
            player.playerNetServerHandler.sendPacket(canReceiveNativePackets(player) ? packet : compatibilityPacket);
        }
    }

    /**
     * Send a NetworkMessage to all Players from the server.
     * <p>
     * If the receiver don't have Halplibe installed, the client will not be kick for unrecognized Packet.
     * Prefer to use {@link NetworkHandler#sendToAllPlayers(NetworkMessage)} if you know your receiver have Halplibe installed.
     * <p>
     * If we are in SinglePlayer this will skip encoding and directly call the message handle.
     */
    @SuppressWarnings({"unused"})
    public static void sendCompatibilityToAllPlayers(NetworkMessage message) {
        if (!EnvironmentHelper.isMultiplayerServer()) {
            sendToPlayerLocal(message);
            return;
        }
        MinecraftServer.getInstance().playerList.sendPacketToAllPlayers(generateCompatibilityNetworkMessagePacket(message));
    }

    /**
     * Send a NetworkMessage to the given Players from the server.
     * <p>
     * Receivers that never confirmed native UniversalPacket support
     * get the message over the compatibility channel instead,
     * so nobody is kicked for an unrecognized Packet.
     * <p>
     * If we are in SinglePlayer this will skip encoding and directly call the message handle.
     */
    @SuppressWarnings({"unused"})
    public static void sendToPlayers(Iterable<? extends Player> players, NetworkMessage message) {
        if (!EnvironmentHelper.isMultiplayerServer()) {
            sendToPlayerLocal(message);
            return;
        }
        UniversalPacket packet = generateNetworkMessagePacket(message);
        PacketCustomPayload compatibilityPacket = packet.toPacketCustomPayload();
        for (Player player : players) {
            ((PlayerServer) player).playerNetServerHandler.sendPacket(canReceiveNativePackets(player) ? packet : compatibilityPacket);
        }
    }

    /**
     * Send a NetworkMessage to the given Players from the server.
     * <p>
     * If the receiver don't have Halplibe installed, the client will not be kick for unrecognized Packet.
     * Prefer to use {@link NetworkHandler#sendToPlayers(Iterable, NetworkMessage)} if you know your receivers have Halplibe installed.
     * <p>
     * If we are in SinglePlayer this will skip encoding and directly call the message handle.
     */
    @SuppressWarnings({"unused"})
    public static void sendCompatibilityToPlayers(Iterable<? extends Player> players, NetworkMessage message) {
        if (!EnvironmentHelper.isMultiplayerServer()) {
            sendToPlayerLocal(message);
            return;
        }
        PacketCustomPayload compatibilityPacket = generateCompatibilityNetworkMessagePacket(message);
        for (Player player : players) {
            ((PlayerServer) player).playerNetServerHandler.sendPacket(compatibilityPacket);
        }
    }

    /**
     * Send a NetworkMessage to the Server from the player.
     * <p>
     * If the receiver don't have Halplibe installed, the client will be kick for unrecognized Packet.
     * Prefer to use {@link NetworkHandler#sendCompatibilityToServer(NetworkMessage)} if you are unsure your receiver have Halplibe installed.
     * <p>
     * If we are in SinglePlayer this will skip encoding and directly call the message handle.
     */
    @SuppressWarnings({"unused"})
    @Environment(EnvType.CLIENT)
    public static void sendToServer(NetworkMessage message) {
        if (EnvironmentHelper.isSingleplayerClient()) {
            sendToPlayerLocal(message);
            return;
        }
        Minecraft.getMinecraft().getSendQueue().addToSendQueue(generateNetworkMessagePacket(message));
    }

    /**
     * Send a NetworkMessage to the Server from the player.
     * <p>
     * If the receiver don't have Halplibe installed, the client will not be kick for unrecognized Packet.
     * Prefer to use {@link NetworkHandler#sendToServer(NetworkMessage)} if you know your receiver have Halplibe installed.
     * <p>
     * If we are in SinglePlayer this will skip encoding and directly call the message handle.
     */
    @SuppressWarnings({"unused"})
    @Environment(EnvType.CLIENT)
    public static void sendCompatibilityToServer(NetworkMessage message) {
        if (EnvironmentHelper.isSingleplayerClient()) {
            sendToPlayerLocal(message);
            return;
        }
        Minecraft.getMinecraft().getSendQueue().addToSendQueue(generateCompatibilityNetworkMessagePacket(message));
    }

    /**
     * Send a NetworkMessage to all Players around a block from the server.
     * <p>
     * Receivers that never confirmed native UniversalPacket support
     * get the message over the compatibility channel instead,
     * so nobody is kicked for an unrecognized Packet.
     * <p>
     * If we are in SinglePlayer this will skip encoding and directly call the message handle.
     */
    @SuppressWarnings({"unused"})
    public static void sendToAllAround(double x, double y, double z, double radius, int dimension, NetworkMessage message) {
        if (!EnvironmentHelper.isMultiplayerServer()) {
            sendToPlayerLocal(message);
            return;
        }
        UniversalPacket packet = generateNetworkMessagePacket(message);
        PacketCustomPayload compatibilityPacket = packet.toPacketCustomPayload();
        for (PlayerServer player : MinecraftServer.getInstance().playerList.playerEntities) {
            if (player.dimension != dimension) {
                continue;
            }
            double dx = x - player.x;
            double dy = y - player.y;
            double dz = z - player.z;
            if (dx * dx + dy * dy + dz * dz < radius * radius) {
                player.playerNetServerHandler.sendPacket(canReceiveNativePackets(player) ? packet : compatibilityPacket);
            }
        }
    }

    /**
     * Send a NetworkMessage to all Players around a block from the server.
     * <p>
     * If the receiver don't have Halplibe installed, the client will be kick for unrecognized Packet.
     * Prefer to use {@link NetworkHandler#sendToAllAround(double, double, double, double, int, NetworkMessage)} if you are unsure your receiver have Halplibe installed.
     * <p>
     * If we are in SinglePlayer this will skip encoding and directly call the message handle.
     */
    @SuppressWarnings({"unused"})
    public static void sendCompatibilityToAllAround(double x, double y, double z, double radius, int dimension, NetworkMessage message) {
        if (!EnvironmentHelper.isMultiplayerServer()) {
            sendToPlayerLocal(message);
            return;
        }
        MinecraftServer.getInstance().playerList.sendPacketToPlayersAroundPoint(x, y, z, radius, dimension, generateCompatibilityNetworkMessagePacket(message));
    }

    private static class MessageIdsNetworkMessage implements NetworkMessage {
        Map<Class<?>, Short> packetIds;

        public MessageIdsNetworkMessage() {
        }

        public MessageIdsNetworkMessage(Map<Class<?>, Short> packetIds) {
            this.packetIds = packetIds;
        }

        @Override
        public void encodeToUniversalPacket(@NonNull UniversalPacket packet) {
            packet.writeShort((short) packetIds.size());

            for (Map.Entry<Class<?>, Short> entry : packetIds.entrySet()) {
                packet.writeShort(entry.getValue());
                packet.writeString(entry.getKey().getName());
            }
        }

        @Override
        public void decodeFromUniversalPacket(@NonNull UniversalPacket packet) {
            this.packetIds = new HashMap<>();

            final short size = packet.readShort();

            for (int i = 0; i < size; i++) {
                final short id = packet.readShort();
                final String className = packet.readString();
                try {
                    final Class<?> messageClass = Class.forName(className);

                    this.packetIds.put(messageClass, id);
                } catch (ClassNotFoundException e) {
                    HalpLibe.LOGGER.warn("NetworkMessage {} from server couldn't be found on client", className);
                }
            }
        }

        @Override
        public void handleClientEnv(NetworkContext context) {
            try {
                NetworkHandler.packetReaders.clear();
                NetworkHandler.packetIds.clear();

                for (Map.Entry<Class<?>, Short> entry : packetIds.entrySet()) {
                    Class<?> klass = entry.getKey();
                    if (NetworkMessage.class.isAssignableFrom(klass)) {
                        Supplier<NetworkMessage> supplier = () -> {
                            try {
                                return (NetworkMessage) klass.getDeclaredConstructor().newInstance();
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                                     NoSuchMethodException e) {
                                throw new RuntimeException(e);
                            }
                        };
                        NetworkHandler.registerNetworkMessage(entry.getValue(), supplier);
                    } else {
                        throw new IllegalArgumentException("Class " + klass.getName() + " does not extend NetworkMessage");
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Sent by the client once login completes to confirm it decodes the native
     * UniversalPacket with the same message ids as the server.
     * Until a connection acknowledges, the server only sends it compatibility payloads.
     */
    private static class HandshakeAckNetworkMessage implements NetworkMessage {
        public HandshakeAckNetworkMessage() {
        }

        @Override
        public void encodeToUniversalPacket(@NonNull UniversalPacket packet) {
        }

        @Override
        public void decodeFromUniversalPacket(@NonNull UniversalPacket packet) {
        }

        @Override
        public void handleServerEnv(@NonNull NetworkContext context) {
            if (context.player instanceof PlayerServer playerServer) {
                nativeReadyConnections.add(playerServer.playerNetServerHandler);
            }
        }
    }
}