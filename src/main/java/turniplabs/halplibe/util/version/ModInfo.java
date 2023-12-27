package turniplabs.halplibe.util.version;

import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.impl.util.version.StringVersion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ModInfo {
    public String id;
    public Version version;
    public ModInfo(DataInputStream dataInputStream) throws IOException {
        unpack(dataInputStream);
    }
    public ModInfo(ModContainer container){
        this(container.getMetadata().getId(), container.getMetadata().getVersion());
    }
    public ModInfo(String id, Version version){
        if (id.isEmpty()){
            id = "Unknown ID";
        }
        this.id = id;
        if (version.getFriendlyString().isEmpty()){
            version = new StringVersion("-1.-1.-1");
        }
        this.version = version;
    }
    public void pack(DataOutputStream dos) throws IOException {
        dos.writeChars(id);
        dos.writeChar(3);
        dos.writeChars(version.getFriendlyString());
        dos.writeChar(3);
    }
    public void unpack(DataInputStream dis) throws IOException {
        id = readString(dis);
        version = new StringVersion(readString(dis));
    }
    public String readString(DataInputStream dis) throws IOException {
        char data = 0;
        StringBuilder stringBuilder = new StringBuilder();
        while (data != 3){
            data = dis.readChar();
            if (data != 3){
                stringBuilder.append(data);
            }
        }
        return stringBuilder.toString();
    }
}
