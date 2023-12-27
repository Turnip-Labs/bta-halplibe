package turniplabs.halplibe.helper;

/**
 * @deprecated Class is being relocated to the turniplabs.halplibe.util package
 */
@Deprecated
public class ItemCoords {
    public static boolean[][] usedCoordinates = turniplabs.halplibe.util.ItemCoords.usedCoordinates;

    public static int[] nextCoords() {
        return turniplabs.halplibe.util.ItemCoords.nextCoords();
    }
    public static void markIDUsed(int x, int y){
        turniplabs.halplibe.util.ItemCoords.markIDUsed(x, y);
    }
    public static void markSectionUsed(int[] topLeft, int[] bottomRight){
        turniplabs.halplibe.util.ItemCoords.markSectionUsed(topLeft, bottomRight);
    }
}
