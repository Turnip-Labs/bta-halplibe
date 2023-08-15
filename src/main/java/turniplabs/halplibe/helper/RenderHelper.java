package turniplabs.halplibe.helper;

public class RenderHelper {

    public static int lastRenderType = 31;

    public static int getRenderType() {
        return lastRenderType++;
    }
}
