package turniplabs.halplibe.helper;

@Deprecated
public class RenderHelper {

    public static int lastRenderType = 31;

    @Deprecated
    public static int getRenderType() {
        return lastRenderType++;
    }
}
