package turniplabs.halplibe.helper;

import net.minecraft.core.Global;
import net.minecraft.core.block.Block;
import turniplabs.halplibe.util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract public class TextureHelper {
    public static List<String> supportedImageFormats = new ArrayList<>();
    static {
        supportedImageFormats.add("BMP");
        supportedImageFormats.add("bmp");
        supportedImageFormats.add("jpeg");
        supportedImageFormats.add("wbmp");
        supportedImageFormats.add("gif");
        supportedImageFormats.add("png");
        supportedImageFormats.add("JPG");
        supportedImageFormats.add("jpg");
        supportedImageFormats.add("WBMP");
        supportedImageFormats.add("JPEG");
    }
    public static List<TextureHandler> textureHandlers = new ArrayList<>();
    public static Map<String, int[]> registeredBlockTextures = new HashMap<>();
    public static Map<String, int[]> registeredItemTextures = new HashMap<>();
    public static Map<String, int[]> registeredParticleTextures = new HashMap<>();
    public static Map<String, Integer> textureDestinationResolutions = new HashMap<>();
    public static Map<String, Integer> textureAtlasWidths = new HashMap<>();

    static {
        // Assign Default Resolutions
        textureDestinationResolutions.put("/terrain.png", 16);
        textureDestinationResolutions.put("/gui/items.png", 16);
        textureDestinationResolutions.put("/particles.png", 8);
        // Assign Default Atlas Widths
        textureAtlasWidths.put("/terrain.png", Global.TEXTURE_ATLAS_WIDTH_TILES);
        textureAtlasWidths.put("/gui/items.png", Global.TEXTURE_ATLAS_WIDTH_TILES);
        textureAtlasWidths.put("/particles.png", 16);
    }

    /**
     * Place mod textures in the <i>assets/modId/block/</i> directory for them to be seen.
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

    /**
     * Place mod textures in the <i>assets/modId/block/</i> directory for them to be seen.
     */
    @SuppressWarnings("unused") // API function
    public static int getOrCreateBlockTextureIndex(String modId, String blockTexture){
        int[] tex = getOrCreateBlockTexture(modId, blockTexture);
        return Block.texCoordToIndex(tex[0], tex[1]);
    }

    /**
     * Place mod textures in the <i>assets/modId/item/</i> directory for them to be seen.
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
    /**
     * Place mod textures in the <i>assets/modId/item/</i> directory for them to be seen.
     */
    @SuppressWarnings("unused") // API function
    public static int getOrCreateItemTextureIndex(String modId, String itemTexture){
        int[] tex = getOrCreateItemTexture(modId, itemTexture);
        return Block.texCoordToIndex(tex[0], tex[1]);
    }

    /**
     * Place mod textures in the <i>assets/modId/particle/</i> directory for them to be seen.
     */
    public static int[] getOrCreateParticleTexture(String modId, String itemTexture) {
        int[] possibleCoords = registeredParticleTextures.get(modId + ":" + itemTexture);
        if (possibleCoords != null) {
            return possibleCoords;
        }

        int[] newCoords = ParticleCoords.nextCoords();
        registeredParticleTextures.put(modId + ":" + itemTexture, newCoords);
        addTextureToParticles(modId, itemTexture, newCoords[0], newCoords[1]);
        return newCoords;
    }
    /**
     * Place mod textures in the <i>assets/modId/particle/</i> directory for them to be seen.
     */
    @SuppressWarnings("unused") // API function
    public static int getOrCreateParticleTextureIndex(String modId, String itemTexture){
        int[] tex = getOrCreateParticleTexture(modId, itemTexture);
        return tex[0] + tex[1] * textureAtlasWidths.get("/particles.png");
    }

    public static void addTextureToTerrain(String modId, String blockTexture, int x, int y) {
        textureHandlers.add(new TextureHandler("/terrain.png", DirectoryManager.getBlockTextureDirectory(modId) + blockTexture, Block.texCoordToIndex(x, y), 16, 1));
    }

    public static void addTextureToItems(String modId, String itemTexture, int x, int y) {
        textureHandlers.add(new TextureHandler("/gui/items.png", DirectoryManager.getItemTextureDirectory(modId) + itemTexture, Block.texCoordToIndex(x, y), 16, 1));
    }
    public static void addTextureToParticles(String modId, String itemTexture, int x, int y) {
        textureHandlers.add(new TextureHandler("/particles.png", DirectoryManager.getParticleTextureDirectory(modId) + itemTexture, Block.texCoordToIndex(x, y), 16, 1));
    }
}
