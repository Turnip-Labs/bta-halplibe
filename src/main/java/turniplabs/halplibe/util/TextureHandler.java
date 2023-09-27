package turniplabs.halplibe.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.render.dynamictexture.DynamicTexture;
import net.minecraft.client.render.texturepack.TexturePackList;
import net.minecraft.core.util.helper.Textures;

import java.awt.image.BufferedImage;
import java.io.File;

public class TextureHandler extends DynamicTexture {
    private final String textureName;
    private final int frameCount;
    private final String animationSource;
    private final int textureIndex;
    private final int resolution;
    private final int width;
    private final byte[] frames;
    private int elapsedTicks = 0;
    private static final Minecraft fakeMc;
    private static int scale = 2;

    public TextureHandler(String textureName, String animationSource, int textureIndex, int resolution, int width) {
        this(textureName, animationSource, textureIndex, resolution, width, fakeMc);
    }
    public TextureHandler(String textureName, String animationSource, int textureIndex, int resolution, int width, Minecraft mc) {
        super(textureIndex, resolution * scale, width);
        this.textureName = textureName;
        this.animationSource = animationSource;
        this.textureIndex = textureIndex;
        this.resolution = resolution;
        this.width = width;

        BufferedImage image = Textures.readImage(mc.texturePackList.selectedTexturePack.getResourceAsStream(animationSource));
        if (image == Textures.missingTexture) {
            throw new RuntimeException("Animation " + animationSource + " couldn't be found!");
        } else if (image.getWidth() != resolution) {
            throw new RuntimeException("Animation " + animationSource + " doesn't have the same width as textures in " + textureName + "!");
        } else if (image.getHeight() % image.getWidth() != 0) {
            throw new RuntimeException("Invalid Height for animation! " + animationSource);
        } else {
            this.frameCount = image.getHeight() / image.getWidth();
            System.out.println("Frame Count: " + this.frameCount);
            this.frames = new byte[resolution * resolution * 4 * this.frameCount * scale * scale];

            for (int frame = 0; frame < this.frameCount; ++frame) {
                for (int x = 0; x < resolution * scale; ++x) {
                    for (int y = 0; y < resolution * scale; ++y) {
                        int c = image.getRGB(x/scale, frame * resolution + y/scale);
                        putPixel(this.frames, frame * resolution * scale * scale * resolution + y * resolution * scale + x, c);
                    }
                }
            }

        }
    }
    public TextureHandler newHandler(Minecraft mc){
        // Returns a new TextureHandler using the current state of Minecraft, If the texturepack has changed it will then use the new texturepack's textures
        return new TextureHandler(textureName, animationSource, textureIndex, resolution, width, mc);
    }
    public void update() {
        this.elapsedTicks = (this.elapsedTicks + 1) % this.frameCount;

        for (int i = 0; i < this.resolution * scale; ++i) {
            for (int j = 0; j < this.resolution * scale; ++j) {
                transferPixel(this.frames, this.elapsedTicks * this.resolution * this.resolution * scale * scale + j * this.resolution * scale + i, this.imageData, j * this.resolution * scale + i);
            }
        }

    }

    public String getTextureName() {
        return this.textureName;
    }

    static {
        // Create a Minecraft Container with the needed information to load the texture-pack
        Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
        File mcdir = mc.getMinecraftDir();
        GameSettings gameSettings = new GameSettings(mc, mcdir);
        mc.gameSettings = gameSettings;
        TexturePackList packList = new TexturePackList(mc, mcdir);
        mc.texturePackList = packList;
        fakeMc = mc;
    }
}
