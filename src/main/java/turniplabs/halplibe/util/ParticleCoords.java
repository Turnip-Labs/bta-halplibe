package turniplabs.halplibe.util;

import net.minecraft.core.Global;

public class ParticleCoords {
    // Particle atlas is 16x16 tiles of 8x8 textures
    private static int atlasWidth = 16;
    public static boolean[][] usedCoordinates = new boolean[atlasWidth][atlasWidth];

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
        if (x > atlasWidth || y > atlasWidth){
            throw new IllegalArgumentException("Coordinates [" + x + ", " + y + "] is outside the valid range of [" + atlasWidth + ", " + atlasWidth + "]");
        }
        usedCoordinates[x][y] = true;
    }
    public static void markSectionUsed(int[] topLeft, int[] bottomRight){
        if (topLeft[0] > atlasWidth || topLeft[1] > atlasWidth){
            throw new IllegalArgumentException("Coordinates [" + topLeft[0] + ", " + topLeft[1] + "] is outside the valid range of [" + atlasWidth + ", " + atlasWidth + "]");
        }
        if (bottomRight[0] > atlasWidth || bottomRight[1] > atlasWidth){
            throw new IllegalArgumentException("Coordinates [" + bottomRight[0] + ", " + bottomRight[1] + "] is outside the valid range of [" + atlasWidth + ", " + atlasWidth + "]");
        }
        for (int x = topLeft[0]; x <= bottomRight[0]; x++) {
            for (int y = topLeft[1]; y <= bottomRight[1]; y++) {
                markIDUsed(x, y);
            }
        }
    }
    static {
        markSectionUsed(new int[]{0,0}, new int[]{7, 0});
        markSectionUsed(new int[]{0,1}, new int[]{6, 1});
        markSectionUsed(new int[]{0,2}, new int[]{1, 2});
        markSectionUsed(new int[]{0,3}, new int[]{1, 3});
        markIDUsed(0, 4);
        markIDUsed(0, 5);
        markSectionUsed(new int[]{0,6}, new int[]{2, 6});
        markSectionUsed(new int[]{0,7}, new int[]{1, 7});
        markIDUsed(5, 6);
    }
}
