package turniplabs.halplibe.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.render.dynamictexture.DynamicTexture;
import net.minecraft.client.render.texturepack.TexturePackList;
import net.minecraft.core.util.helper.Textures;
import turniplabs.halplibe.helper.TextureHelper;

import java.awt.image.BufferedImage;
import java.io.File;

public class TextureHandler extends DynamicTexture {
    private final String textureName;
    private final int frameCount;
    private final String animationSource;
    private final int textureIndex;
    private final int resolution;
    private final int defaultResolution;
    private final int width;
    private final byte[] frames;
    private int elapsedTicks = 0;
    private static final Minecraft fakeMc;
    private final float scale;

    public TextureHandler(String textureName, String animationSource, int textureIndex, int resolution, int width) {
        this(textureName, animationSource, textureIndex, resolution, width, fakeMc);
    }
    public TextureHandler(String textureName, String animationSource, int textureIndex, int defaultResolution, int width, Minecraft mc) {
        super(textureIndex, getAtlasResolution(textureName, defaultResolution), width);
        this.textureName = textureName;
        this.animationSource = animationSource;
        this.textureIndex = textureIndex;
        this.defaultResolution = defaultResolution;
        BufferedImage image = Textures.readImage(mc.texturePackList.selectedTexturePack.getResourceAsStream(animationSource));
        this.resolution = image.getWidth();
        // Scaling factor from source resolution to destination resolution
        this.scale = getScale(textureName, resolution);
        this.width = width;


        if (image == Textures.missingTexture) {
            throw new RuntimeException("Animation " + animationSource + " couldn't be found!");
        } else if (image.getHeight() % image.getWidth() != 0) {
            throw new RuntimeException("Invalid Height for animation! " + animationSource);
        } else {
            this.frameCount = image.getHeight() / image.getWidth();
            System.out.println("Frame Count: " + this.frameCount);
            this.frames = new byte[(int) (resolution * resolution * 4 * this.frameCount * scale * scale)];

            for (int frame = 0; frame < this.frameCount; ++frame) {
                for (int x = 0; x < resolution * scale; ++x) {
                    for (int y = 0; y < resolution * scale; ++y) {
                        int c = image.getRGB((int) (x/scale),  (frame * resolution + (int)(y/scale)));
                        putPixel(this.frames, (int) (frame * resolution * scale * scale * resolution + y * resolution * scale + x), c);
                    }
                }
            }
        }
    }
    public TextureHandler newHandler(Minecraft mc){
        // Returns a new TextureHandler using the current state of Minecraft, If the texturepack has changed it will then use the new texturepack's textures
        return new TextureHandler(textureName, animationSource, textureIndex, defaultResolution, width, mc);
    }
    public void update() {
        this.elapsedTicks = (this.elapsedTicks + 1) % this.frameCount;

        for (int i = 0; i < this.resolution * scale; ++i) {
            for (int j = 0; j < this.resolution * scale; ++j) {
                transferPixel(this.frames, (int) (this.elapsedTicks * this.resolution * this.resolution * scale * scale + j * this.resolution * scale + i), this.imageData, (int) (j * this.resolution * scale + i));
            }
        }

    }
    public String getTextureName() {
        return this.textureName;
    }
    public static float getScale(String textureName, int resolution){
        if (TextureHelper.textureDestinationResolutions.get(textureName) != null){
            return (float)TextureHelper.textureDestinationResolutions.get(textureName)/resolution;
        }
        return 1f;
    }
    public static int getAtlasResolution(String textureName, int defaultResolution){
        if (TextureHelper.textureDestinationResolutions.get(textureName) != null){
            return TextureHelper.textureDestinationResolutions.get(textureName);
        }
        return defaultResolution;
    }

    static {
        // Create a Minecraft Container with the needed information to load the texture-pack
        Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
        File mcdir = mc.getMinecraftDir();
        mc.gameSettings = new GameSettings(mc, mcdir);
        mc.texturePackList = new TexturePackList(mc, mcdir);
        fakeMc = mc;
    }
}
