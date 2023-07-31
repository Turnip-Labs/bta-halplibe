package turniplabs.halplibe.helper;

import turniplabs.halplibe.HalpLibe;

public class BlockCoords {
	public static int lastX = 22;
	public static int lastY = 0;
	public static int area = 0;

	public static int[] nextCoords() {
		switch (area) {
			case 0: {
				int x = lastX;
				int y = lastY;
				if (++lastX > 31) {
					lastX = 22;
					if (++lastY > 31) {
						area = 1;
						lastX = 7;
						lastY = 16;
					}
				}
				return new int[] {x, y};
			}
			case 1: {
				int x = lastX;
				int y = lastY;
				if (++lastX > 21) {
					lastX = 7;
					if (++lastY > 30) {
						area = 2;
						lastX = 16;
						lastY = 10;
					}
				}
				return new int[] {x, y};
			}
			case 2: {
				int x = lastX;
				int y = lastY;
				if (++lastX > 21) {
					lastX = 16;
					if (++lastY > 15) {
						area = 3;
						HalpLibe.LOGGER.info("Reached the end of block texture space!");
					}
				}
				return new int[] {x, y};
			}
			default:
				HalpLibe.LOGGER.info("No more block texture spaces are available!");
				return new int[] {21, 15};
		}
	}
}
