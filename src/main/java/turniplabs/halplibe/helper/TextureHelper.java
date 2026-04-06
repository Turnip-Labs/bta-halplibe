package turniplabs.halplibe.helper;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.render.texture.stitcher.AtlasStitcher;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import turniplabs.halplibe.HalpLibe;

import java.util.Optional;

public class TextureHelper {

    @SuppressWarnings("unused")
    public static void initializeAllFiles(String modId, AtlasStitcher atlas, boolean searchSubDirs) {
        Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(modId);
        if (modContainer.isEmpty()) {
            HalpLibe.LOGGER.error("Failed to find mod '{}' when loading textures!", modId);
            return;
        }

        try {
            TextureRegistry.initializeAllFiles(modId, atlas, searchSubDirs);
        } catch (Exception e) {
            HalpLibe.LOGGER.error("Failed to initialize textures for mod '{}' in atlas!", modId, e);
        }
    }
}
