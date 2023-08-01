package turniplabs.halplibe.util;

import net.minecraft.client.render.dynamictexture.DynamicTexture;
import net.minecraft.core.util.helper.Textures;

import java.awt.image.BufferedImage;

public class TextureHandler extends DynamicTexture {
	private final String textureName;
	private final int frameCount;
	private final byte[] frames;
	private int elapsedTicks = 0;

	public TextureHandler(String textureName, String animationSource, int textureIndex, int resolution, int width) {
		super(textureIndex, resolution, width);
		this.textureName = textureName;
		BufferedImage image = Textures.readImage(TextureHandler.class.getResourceAsStream(animationSource));
		if (image == Textures.missingTexture) {
			throw new RuntimeException("Animation " + animationSource + " couldn't be found!");
		} else if (image.getWidth() != resolution) {
			throw new RuntimeException("Animation " + animationSource + " doesn't have the same width as textures in " + textureName + "!");
		} else if (image.getHeight() % image.getWidth() != 0) {
			throw new RuntimeException("Invalid Height for animation! " + animationSource);
		} else {
			this.frameCount = image.getHeight() / image.getWidth();
			System.out.println("Frame Count: " + this.frameCount);
			this.frames = new byte[resolution * resolution * 4 * this.frameCount];

			for (int frame = 0; frame < this.frameCount; ++frame) {
				for (int x = 0; x < resolution; ++x) {
					for (int y = 0; y < resolution; ++y) {
						int c = image.getRGB(x, frame * resolution + y);
						putPixel(this.frames, frame * resolution * resolution + y * resolution + x, c);
					}
				}
			}

		}
	}

	public void update() {
		this.elapsedTicks = (this.elapsedTicks + 1) % this.frameCount;

		for (int i = 0; i < this.resolution; ++i) {
			for (int j = 0; j < this.resolution; ++j) {
				transferPixel(this.frames, this.elapsedTicks * this.resolution * this.resolution + j * this.resolution + i, this.imageData, j * this.resolution + i);
			}
		}

	}

	public String getTextureName() {
		return this.textureName;
	}
}
