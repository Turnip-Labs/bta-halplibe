package turniplabs.halplibe.util;

import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketPlaceholder extends Packet {
    @Override
    public void readPacketData(DataInputStream dataInputStream) throws IOException {

    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream) throws IOException {

    }

    @Override
    public void processPacket(NetHandler netHandler) {

    }

    @Override
    public int getPacketSize() {
        return 0;
    }
}
