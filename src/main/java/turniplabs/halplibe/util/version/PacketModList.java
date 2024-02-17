package turniplabs.halplibe.util.version;

import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PacketModList extends Packet {
    public List<ModInfo> modInfos = new ArrayList<>();
    @SuppressWarnings("unused")
    public PacketModList(){
        // This needs to exist because BTA uses this constructor when a packet is received
    }
    public PacketModList(List<ModInfo> modInfos){
        this.modInfos = modInfos;
    }
    @Override
    public void readPacketData(DataInputStream dataInputStream) throws IOException {
        this.modInfos = new ArrayList<>();
        char data = 0;
        while (data != 127){
            modInfos.add(new ModInfo(dataInputStream));
            data = dataInputStream.readChar();
        }
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream) throws IOException {
        int size = modInfos.size();
        for (int i = 0; i < size; i++) {
            modInfos.get(i).pack(dataOutputStream);
            if (i == size -1){
                dataOutputStream.writeChar(127);
            } else {
                dataOutputStream.writeChar(3);
            }
        }
    }

    @Override
    public void processPacket(NetHandler netHandler) {
        ((IVersionPackets)netHandler).halplibe$handleModList(this);
    }

    @Override
    public int getPacketSize() {
        return 0;
    }
}
