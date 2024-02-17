package turniplabs.halplibe.util.network;

import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet24MobSpawn;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.data.SynchedEntityData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class PacketExtendedMobSpawn extends Packet24MobSpawn {
    public int entityId;
    public int type;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public byte yaw;
    public byte pitch;
    private SynchedEntityData metaData;
    private List receivedMetadata;
    public String nickname;
    public byte chatColor;

    public PacketExtendedMobSpawn() {
    }

    public PacketExtendedMobSpawn(EntityLiving entityliving) {
        this.entityId = entityliving.id;
        this.type = EntityDispatcher.getEntityID(entityliving);
        this.xPosition = MathHelper.floor_double(entityliving.x * 32.0);
        this.yPosition = MathHelper.floor_double(entityliving.y * 32.0);
        this.zPosition = MathHelper.floor_double(entityliving.z * 32.0);
        this.yaw = (byte)(entityliving.yRot * 256.0f / 360.0f);
        this.pitch = (byte)(entityliving.xRot * 256.0f / 360.0f);
        this.metaData = entityliving.getEntityData();
        this.nickname = entityliving.nickname;
        this.chatColor = entityliving.chatColor;
    }

    @Override
    public void readPacketData(DataInputStream dis) throws IOException {
        this.entityId = dis.readInt();
        this.type = dis.readInt();
        this.xPosition = dis.readInt();
        this.yPosition = dis.readInt();
        this.zPosition = dis.readInt();
        this.yaw = dis.readByte();
        this.pitch = dis.readByte();
        this.receivedMetadata = SynchedEntityData.unpack(dis);
        this.nickname = Packet24MobSpawn.readString(dis, 32);
        this.chatColor = dis.readByte();
    }

    @Override
    public void writePacketData(DataOutputStream dos) throws IOException {
        dos.writeInt(this.entityId);
        dos.writeInt(this.type);
        dos.writeInt(this.xPosition);
        dos.writeInt(this.yPosition);
        dos.writeInt(this.zPosition);
        dos.writeByte(this.yaw);
        dos.writeByte(this.pitch);
        this.metaData.packAll(dos);
        Packet24MobSpawn.writeString(this.nickname, dos);
        dos.writeByte(this.chatColor);
    }

    @Override
    public void processPacket(NetHandler netHandler) {
        netHandler.handleMobSpawn(this);
    }

    @Override
    public int getPacketSize() {
        return 23;
    }

    public List getMetadata() {
        return this.receivedMetadata;
    }
}
