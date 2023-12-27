package turniplabs.halplibe.helper;

/**
 * @deprecated Class is being relocated to the turniplabs.halplibe.util package
 */
@Deprecated
public class BlockCoords {
    public static boolean[][] usedCoordinates = turniplabs.halplibe.util.BlockCoords.usedCoordinates;

    public static int[] nextCoords() {
        return turniplabs.halplibe.util.BlockCoords.nextCoords();
    }
    public static void markIDUsed(int x, int y){
        turniplabs.halplibe.util.BlockCoords.markIDUsed(x, y);
    }
    public static void markSectionUsed(int[] topLeft, int[] bottomRight){
        turniplabs.halplibe.util.BlockCoords.markSectionUsed(topLeft, bottomRight);
    }
}
