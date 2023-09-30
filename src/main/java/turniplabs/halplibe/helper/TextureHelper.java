package turniplabs.halplibe.helper;

import net.minecraft.core.Global;
import net.minecraft.core.block.Block;
import turniplabs.halplibe.util.TextureHandler;

import java.util.*;

public class TextureHelper {
    public static List<TextureHandler> textureHandlers = new ArrayList<>();
    public static Map<String, int[]> registeredBlockTextures = new HashMap<>();
    public static Map<String, int[]> registeredItemTextures = new HashMap<>();
    public static Map<String, Integer> textureDestinationResolutions = new HashMap<>();
    public static Map<String, Integer> textureAtlasWidths = new HashMap<>();

    static {
        // Assign Default Resolutions
        textureDestinationResolutions.put("/terrain.png", 16);
        textureDestinationResolutions.put("/gui/items.png", 16);
        // Assign Default Atlas Widths
        textureAtlasWidths.put("/terrain.png", Global.TEXTURE_ATLAS_WIDTH_TILES);
        textureAtlasWidths.put("/gui/items.png", Global.TEXTURE_ATLAS_WIDTH_TILES);
    }

    @Deprecated
    public static int[] registerBlockTexture(String modId, String blockTexture) {
        return getOrCreateBlockTexture(modId, blockTexture);
    }

    /**
     * Place mod textures in the <i>assets/modid/block/</i> directory for them to be seen.
     */
    public static int[] getOrCreateBlockTexture(String modId, String blockTexture) {
        int[] possibleCoords = registeredBlockTextures.get(modId + ":" + blockTexture);
        if (possibleCoords != null) {
            return possibleCoords;
        }

        int[] newCoords = BlockCoords.nextCoords();
        registeredBlockTextures.put(modId + ":" + blockTexture, newCoords);
        addTextureToTerrain(modId, blockTexture, newCoords[0], newCoords[1]);
        return newCoords;
    }

    @Deprecated
    public static int[] registerItemTexture(String modId, String itemTexture) {
        return getOrCreateItemTexture(modId, itemTexture);
    }

    /**
     * Place mod textures in the <i>assets/modid/item/</i> directory for them to be seen.
     */
    public static int[] getOrCreateItemTexture(String modId, String itemTexture) {
        int[] possibleCoords = registeredItemTextures.get(modId + ":" + itemTexture);
        if (possibleCoords != null) {
            return possibleCoords;
        }

        int[] newCoords = ItemCoords.nextCoords();
        registeredItemTextures.put(modId + ":" + itemTexture, newCoords);
        addTextureToItems(modId, itemTexture, newCoords[0], newCoords[1]);
        return newCoords;
    }

    public static void addTextureToTerrain(String modId, String blockTexture, int x, int y) {
        textureHandlers.add(new TextureHandler("/terrain.png", "/assets/" + modId + "/block/" + blockTexture, Block.texCoordToIndex(x, y), 16, 1));
    }

    public static void addTextureToItems(String modId, String itemTexture, int x, int y) {
        textureHandlers.add(new TextureHandler("/gui/items.png", "/assets/" + modId + "/item/" + itemTexture, Block.texCoordToIndex(x, y), 16, 1));
    }

}
