package turniplabs.halplibe.util.achievements;

import net.minecraft.client.render.TextureFX;
import net.minecraft.core.Global;
import net.minecraft.core.achievement.AchievementList;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Side;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class VanillaAchievementsPage extends AchievementPage{
    public VanillaAchievementsPage() {
        super("Minecraft", "achievements.page.minecraft");
        achievementList.addAll(AchievementList.achievementList);
    }

    @Override
    public void getBackground(GuiAchievements guiAchievements, Random random, int iOffset, int jOffset, int blockX1, int blockY1, int blockX2, int blockY2) {
        int l7 = 0;
        while (l7 * 16 - blockY2 < 155) {
            float f5 = 0.6f - (float)(blockY1 + l7) / 25.0f * 0.3f;
            GL11.glColor4f(f5, f5, f5, 1.0f);
            int i8 = 0;
            while (i8 * 16 - blockX2 < 224) {
                random.setSeed(1234 + blockX1 + i8);
                random.nextInt();
                int j8 = random.nextInt(1 + blockY1 + l7) + (blockY1 + l7) / 2;
                int k8 = Block.sand.getBlockTextureFromSideAndMetadata(Side.BOTTOM, 0);
                if (j8 > 37 || blockY1 + l7 == 35) {
                    k8 = Block.bedrock.getBlockTextureFromSideAndMetadata(Side.BOTTOM, 0);
                } else if (j8 == 22) {
                    k8 = random.nextInt(2) == 0 ? Block.oreDiamondStone.getBlockTextureFromSideAndMetadata(Side.BOTTOM, 0) : Block.oreRedstoneStone.getBlockTextureFromSideAndMetadata(Side.BOTTOM, 0);
                } else if (j8 == 10) {
                    k8 = Block.oreIronStone.getBlockTextureFromSideAndMetadata(Side.BOTTOM, 0);
                } else if (j8 == 8) {
                    k8 = Block.oreCoalStone.getBlockTextureFromSideAndMetadata(Side.BOTTOM, 0);
                } else if (j8 > 4) {
                    k8 = Block.stone.getBlockTextureFromSideAndMetadata(Side.BOTTOM, 0);
                } else if (j8 > 0) {
                    k8 = Block.dirt.getBlockTextureFromSideAndMetadata(Side.BOTTOM, 0);
                }
                guiAchievements.drawTexturedModalRect(iOffset + i8 * 16 - blockX2, jOffset + l7 * 16 - blockY2, k8 % Global.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain, k8 / Global.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain, 16, 16, TextureFX.tileWidthTerrain, 1.0f / (float)(Global.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain));
                ++i8;
            }
            ++l7;
        }
    }
}
