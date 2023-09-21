package turniplabs.halplibe.helper;

import java.util.ArrayList;

public class RegistryHelper {
	private static final ArrayList<Runnable> regsiterFunctions = new ArrayList<>();
	
	public static void scheduleRegistry(boolean configured, Runnable register) {
		if (configured) regsiterFunctions.add(0, register);
		else regsiterFunctions.add(register);
	}
	
	@SuppressWarnings("unused")
	private static void runRegistry() {
		for (Runnable regsiterFunction : regsiterFunctions) {
			regsiterFunction.run();
		}
	}
}
