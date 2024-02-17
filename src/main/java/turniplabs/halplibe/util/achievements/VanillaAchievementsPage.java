package turniplabs.halplibe.util.achievements;

import net.minecraft.client.render.TextureFX;
import net.minecraft.core.Global;
import net.minecraft.core.achievement.AchievementList;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Side;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class VanillaAchievementsPage extends AchievementPage{
    private static Block[] stoneOres = new Block[]{Block.oreCoalStone, Block.oreIronStone, Block.oreGoldStone, Block.oreDiamondStone, Block.oreRedstoneStone};
    private static Block[] basaltOres = new Block[]{Block.oreCoalBasalt, Block.oreIronBasalt, Block.oreGoldBasalt, Block.oreDiamondBasalt, Block.oreRedstoneBasalt};
    public VanillaAchievementsPage() {
        super("Minecraft", "achievements.page.minecraft");
        achievementList.addAll(AchievementList.achievementList);
    }

    @Override
    public void getBackground(GuiAchievements guiAchievements, Random random, int iOffset, int jOffset, int blockX1, int blockY1, int blockX2, int blockY2) {
        int row = 0;
        while (row * 16 - blockY2 < 155) {
            float brightness = 0.6f - (float)(blockY1 + row) / 25.0f * 0.3f;
            GL11.glColor4f(brightness, brightness, brightness, 1.0f);
            int column = 0;
            while (column * 16 - blockX2 < 224) {
                random.setSeed(1234 + blockX1 + column);
                random.nextInt();
                int randomY = random.nextInt(1 + blockY1 + row) + (blockY1 + row) / 2;
                int texture = Block.sand.getBlockTextureFromSideAndMetadata(Side.BOTTOM, 0);
                Block[] oreArray = stoneOres;
                if (randomY >= 28 || blockY1 + row > 24) {
                    oreArray = basaltOres;
                }
                if (randomY > 37 || blockY1 + row == 35) {
                    texture = Block.bedrock.getBlockTextureFromSideAndMetadata(Side.BOTTOM, 0);
                } else if (randomY == 22) {
                    texture = random.nextInt(2) == 0 ? oreArray[3].getBlockTextureFromSideAndMetadata(Side.BOTTOM, 0) : oreArray[4].getBlockTextureFromSideAndMetadata(Side.BOTTOM, 0);
                } else if (randomY == 10) {
                    texture = oreArray[1].getBlockTextureFromSideAndMetadata(Side.BOTTOM, 0);
                } else if (randomY == 8) {
                    texture = oreArray[0].getBlockTextureFromSideAndMetadata(Side.BOTTOM, 0);
                } else if (randomY > 4) {
                    texture = Block.stone.getBlockTextureFromSideAndMetadata(Side.BOTTOM, 0);
                    if (randomY >= 28 || blockY1 + row > 24) {
                        texture = Block.basalt.getBlockTextureFromSideAndMetadata(Side.BOTTOM, 0);
                    }
                } else if (randomY > 0) {
                    texture = Block.dirt.getBlockTextureFromSideAndMetadata(Side.BOTTOM, 0);
                }
                guiAchievements.drawTexturedModalRect(iOffset + column * 16 - blockX2, jOffset + row * 16 - blockY2, texture % Global.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain, texture / Global.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain, 16, 16, TextureFX.tileWidthTerrain, 1.0f / (float)(Global.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain));
                ++column;
            }
            ++row;
        }
    }
}
