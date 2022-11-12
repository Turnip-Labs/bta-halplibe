package turniplabs.halplibe.helper;

import net.minecraft.src.Block;
import turniplabs.halplibe.util.TextureHandler;

import java.util.ArrayList;
import java.util.List;

public class TextureHelper {
    public static List<TextureHandler> textureHandlers = new ArrayList<>();

    public static void addTextureToTerrain(String modId, String blockTexture, int x, int y) {
        textureHandlers.add(new TextureHandler("/terrain.png", "/" + modId+ "/assets/block/" + blockTexture, Block.texCoordToIndex(x, y), 16, 1));
    }

    public static void addTextureToItems(String modId, String itemTexture, int x, int y) {
        textureHandlers.add(new TextureHandler("/gui/items.png", "/" + modId + "/assets/item/" + itemTexture, Block.texCoordToIndex(x, y), 16, 1));
    }
}
