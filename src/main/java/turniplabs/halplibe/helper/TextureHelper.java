package turniplabs.halplibe.helper;

import net.minecraft.src.Block;
import turniplabs.halplibe.util.TextureHandler;

import java.util.ArrayList;
import java.util.List;

public class TextureHelper {
    public static List<TextureHandler> textureHandlers = new ArrayList<>();

    public void addTextureToTerrain(String MOD_ID, String blockTexture, int x, int y) {
        textureHandlers.add(new TextureHandler("/terrain.png", MOD_ID + "assets/block/" + blockTexture, Block.texCoordToIndex(x, y), 16, 1));
    }

    public void addTextureToItems(String MOD_ID, String itemTexture, int x, int y) {
        textureHandlers.add(new TextureHandler("/gui/items.png", MOD_ID + "assets/items/" + itemTexture, Block.texCoordToIndex(x, y), 16, 1));
    }
}
