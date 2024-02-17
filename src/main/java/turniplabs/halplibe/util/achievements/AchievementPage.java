package turniplabs.halplibe.util.achievements;

import net.minecraft.core.achievement.Achievement;
import net.minecraft.core.lang.I18n;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AchievementPage {
    public String name;
    public String descriptionKey;
    public List<Achievement> achievementList = new ArrayList<>();
    public int minX;
    public int minY;
    public int maxX;
    public int maxY;

    public AchievementPage(String name, String descriptionKey) {
        this.name = name;
        this.descriptionKey = descriptionKey;
    }

    public String getDescription() {
        return I18n.getInstance().translateDescKey(descriptionKey);
    }

    public abstract void getBackground(GuiAchievements guiAchievements, Random random, int iOffset, int jOffset, int blockX1, int blockY1, int blockX2, int blockY2);
}
