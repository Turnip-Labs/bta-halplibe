package turniplabs.halplibe.helper;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Global;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.util.version.EnumModList;
import turniplabs.halplibe.util.version.ModInfo;
import turniplabs.halplibe.util.version.PacketModList;

import java.util.ArrayList;
import java.util.List;

public class ModVersionHelper {
    protected static boolean isDev = FabricLoader.getInstance().isDevelopmentEnvironment();
    protected static List<ModInfo> localMods = new ArrayList<>();
    protected static List<ModInfo> serverMods = null;
    static {
        for (ModContainer modContainer: FabricLoader.getInstance().getAllMods()) {
            if (modContainer.getMetadata().getType().equals("fabric") && !modContainer.getMetadata().getId().equals("fabricloader")){
                localMods.add(new ModInfo(modContainer));
            }
        }
    }
    public static void initialize(){}

    /**
     * @return List of mods installed in the current environment.
     */
    public static List<ModInfo> getLocalModlist(){
        return localMods;
    }

    /**
     * @return List of mods from the current server, returns null if not connected to a server or the server does not support the mod list packet.
     */
    public static List<ModInfo> getServerModlist(){
        if (isClientOfServer()){
            return serverMods;
        }
        return null;
    }
    /**
     * @return Returns local mods when in single player or called from the server, returns server mods when a client of server
     */
    public static List<ModInfo> getActiveModlist(){
        if (isClientOfServer()){
            return getServerModlist();
        }
        return getLocalModlist();
    }

    /**
     * @return Returns true only if the player is currently connected to a server.
     */
    public static boolean isClientOfServer(){
        if (Global.isServer){
            return false;
        }
        Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
        return mc.theWorld != null && mc.theWorld.isClientSide;
    }
    /**
     * @return Returns true if the mod is present in the active mod list
     */
    public static boolean isModPresent(String modIdToCheck){
        return isModPresent(modIdToCheck, EnumModList.ACTIVE);
    }
    /**
     * @return Returns true if the mod is present in the specified mod list
     */
    public static boolean isModPresent(String modIdToCheck, EnumModList modList){
        return getModInfo(modIdToCheck, modList) != null;
    }
    /**
     * @return Returns 0 if versions match exactly, returns a positive integer when mod has a greater version number, returns a negative integer when a mod has a smaller version number, returns null if mod is not present or comparison fails.
     */
    public static Integer isVersionPresent(String modIdToCheck, Version version){
        return isVersionPresent(modIdToCheck, version, EnumModList.ACTIVE);
    }
    /**
     * @return Returns 0 if versions match exactly, returns a positive integer when mod has a greater version number, returns a negative integer when a mod has a smaller version number, returns null if mod is not present or comparison fails.
     */
    public static Integer isVersionPresent(String modIdToCheck, Version version, EnumModList modList){
        ModInfo modInfo = getModInfo(modIdToCheck, modList);
        if (modInfo == null) return null;
        return modInfo.version.compareTo(version);
    }
    /**
     * @return Returns the specified mod info if present, returns null if not present
     */
    public static ModInfo getModInfo(String modId, EnumModList modList){
        List<ModInfo> modInfoList = getModList(modList);
        if (modInfoList == null) return null;
        for (ModInfo info: modInfoList) {
            if (info.id.equals(modId)) return info;
        }
        return null;
    }
    /**
     * @return Returns the specified mod list.
     */
    public static List<ModInfo> getModList(EnumModList modList){
        switch (modList){
            case LOCAL:
                return getLocalModlist();
            case SERVER:
                return getServerModlist();
            case ACTIVE:
                return getActiveModlist();
            default:
                return null;
        }
    }
    protected static void handleModListPacket(PacketModList packetModList){
        serverMods = packetModList.modInfos;
        if (isDev){
            printModList();
        }

    }
    protected static void printModList(){
        HalpLibe.LOGGER.info("Server Mod List");
        if (getServerModlist() != null){
            for (ModInfo info: getServerModlist()) {
                HalpLibe.LOGGER.info(info.id + " " + info.version);
            }
        }
    }
}
