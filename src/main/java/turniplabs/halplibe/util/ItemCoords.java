package turniplabs.halplibe.util;

import net.minecraft.core.Global;

public class ItemCoords {
    public static boolean[][] usedCoordinates = new boolean[Global.TEXTURE_ATLAS_WIDTH_TILES][Global.TEXTURE_ATLAS_WIDTH_TILES];

    public static int[] nextCoords() {
        for (int y = 0; y < usedCoordinates[0].length; y++) {
            for (int x = 0; x < usedCoordinates.length; x++) {
                if (!usedCoordinates[x][y]){
                    markIDUsed(x, y);
                    return new int[]{x, y};
                }
            }
        }
        throw new NullPointerException("Out of Texture space! Increase the atlas size in the halplibe config");
    }
    public static void markIDUsed(int x, int y){
        if (x > Global.TEXTURE_ATLAS_WIDTH_TILES || y > Global.TEXTURE_ATLAS_WIDTH_TILES){
            throw new IllegalArgumentException("Coordinates [" + x + ", " + y + "] is outside the valid range of [" + Global.TEXTURE_ATLAS_WIDTH_TILES + ", " + Global.TEXTURE_ATLAS_WIDTH_TILES + "]");
        }
        usedCoordinates[x][y] = true;
    }
    public static void markSectionUsed(int[] topLeft, int[] bottomRight){
        if (topLeft[0] > Global.TEXTURE_ATLAS_WIDTH_TILES || topLeft[1] > Global.TEXTURE_ATLAS_WIDTH_TILES){
            throw new IllegalArgumentException("Coordinates [" + topLeft[0] + ", " + topLeft[1] + "] is outside the valid range of [" + Global.TEXTURE_ATLAS_WIDTH_TILES + ", " + Global.TEXTURE_ATLAS_WIDTH_TILES + "]");
        }
        if (bottomRight[0] > Global.TEXTURE_ATLAS_WIDTH_TILES || bottomRight[1] > Global.TEXTURE_ATLAS_WIDTH_TILES){
            throw new IllegalArgumentException("Coordinates [" + bottomRight[0] + ", " + bottomRight[1] + "] is outside the valid range of [" + Global.TEXTURE_ATLAS_WIDTH_TILES + ", " + Global.TEXTURE_ATLAS_WIDTH_TILES + "]");
        }
        for (int x = topLeft[0]; x <= bottomRight[0]; x++) {
            for (int y = topLeft[1]; y <= bottomRight[1]; y++) {
                markIDUsed(x, y);
            }
        }
    }
    static {
        markSectionUsed(new int[]{0,0}, new int[]{15, 15});
    }
}
