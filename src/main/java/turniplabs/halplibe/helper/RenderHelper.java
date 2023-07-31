package turniplabs.halplibe.helper;

public class RenderHelper {

	public static int lastRenderType = 30;

	public static int getRenderType() {
		return lastRenderType++;
	}
}
