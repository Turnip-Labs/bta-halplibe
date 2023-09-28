package turniplabs.halplibe.helper;

import java.util.ArrayList;

public class RegistryHelper {
	private static final ArrayList<Runnable> regsitryFunctions = new ArrayList<>();
	
	public static void scheduleRegistry(boolean configured, Runnable function) {
		if (configured) regsitryFunctions.add(0, function);
		else regsitryFunctions.add(function);
	}
	
	@SuppressWarnings("unused")
	private static void runRegistry() {
		for (Runnable regsitryFunction : regsitryFunctions) {
			regsitryFunction.run();
		}
	}
}
