package turniplabs.halplibe.helper;

import net.minecraft.core.block.BlockLanternFirefly;
import turniplabs.halplibe.util.FireflyColor;
import turniplabs.halplibe.util.IFireflyColor;

import java.util.ArrayList;
import java.util.List;

abstract public class FireflyHelper {
    public static final List<FireflyColor> registeredColors = new ArrayList<>();

    public static void createColor(FireflyColor fireflyColor) {
        registeredColors.add(fireflyColor);
    }

    public static void setColor(BlockLanternFirefly block, FireflyColor color) {
        ((IFireflyColor) block).halplibe$setColor(color);
    }
}
