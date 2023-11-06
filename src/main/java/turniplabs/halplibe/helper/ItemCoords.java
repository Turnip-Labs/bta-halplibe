package turniplabs.halplibe.helper;

import net.minecraft.core.Global;
import turniplabs.halplibe.HalpLibe;

public class ItemCoords {
    public static int lastX = 16;
    public static int lastY = 0;
    public static int area = 0;

    public static int[] nextCoords() {
        switch (area) {
            case 0: {
                int x = lastX;
                int y = lastY;
                if (++lastX > Global.TEXTURE_ATLAS_WIDTH_TILES-1) {
                    lastX = 16;
                    if (++lastY > Global.TEXTURE_ATLAS_WIDTH_TILES-1) {
                        area = 1;
                        lastX = 0;
                        lastY = 16;
                    }
                }
                return new int[]{x, y};
            }
            case 1: {
                int x = lastX;
                int y = lastY;
                if (++lastX > 15) {
                    lastX = 0;
                    if (++lastY > Global.TEXTURE_ATLAS_WIDTH_TILES-1) {
                        area = 2;
                        HalpLibe.LOGGER.info("Reached the end of item texture space!");
                    }
                }
                return new int[]{x, y};
            }
            default:
                HalpLibe.LOGGER.info("No more item texture spaces are available!");
                return new int[]{15, Global.TEXTURE_ATLAS_WIDTH_TILES-1};
        }
    }
}
