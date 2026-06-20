package turniplabs.halplibe.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Global;

public class EnvironmentHelper {

    /** use isMultiplayerServer */
    @Deprecated public static boolean isServerEnvironment() { return  isMultiplayerServer(); }

    /** use isSingleplayerClient */
    @Deprecated public static boolean isSinglePlayer() { return isSingleplayerClient(); }

    /** use isMultiplayerClient */
    @Deprecated public static boolean isClientWorld() { return isMultiplayerClient(); }

    public static boolean isMultiplayerServer() {
        return Global.isServer;
    }

    public static boolean isSingleplayerClient() {
        if (Global.isServer) {
            return false;
        }

        return !Minecraft.getMinecraft().isMultiplayerWorld();
    }

    public static boolean isMultiplayerClient() {
        return !isSinglePlayer() && !isServerEnvironment();
    }
}
