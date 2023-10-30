package turniplabs.halplibe.helper;

import turniplabs.halplibe.util.toml.Toml;

import java.util.ArrayList;
import java.util.function.Consumer;

public class RegistryHelper {
	private static final ArrayList<Runnable> regsitryFunctions = new ArrayList<>();
	private static final ArrayList<Runnable> configuredRegsitryFunctions = new ArrayList<>();
	private static final ArrayList<Runnable> smartRregsitryFunctions = new ArrayList<>();

	/**
	 * Only intended for internal use from {@link BlockHelper#reserveRuns(Toml, int, Consumer)} and {@link ItemHelper#reserveRuns(Toml, int, Consumer)}
	 *
	 *
	 * @param function the function to run on registry handling
	 */
	public static void scheduleSmartRegistry(Runnable function) {
		smartRregsitryFunctions.add(function);
	}

	/**
	 * For blocks and items, use {@link BlockHelper#reserveRuns(Toml, int, Consumer)} and {@link ItemHelper#reserveRuns(Toml, int, Consumer)}, respectively
	 * These will figure out what ids are available automatically, making sure to account for mods that aren't using halplibe, or are using {@link RegistryHelper#scheduleRegistry(boolean, Runnable)}
	 *
	 * Reason this is not deprecated:
	 * - other registries that halplibe doesn't already have utils for
	 * - mods that already have their item count fully defined from the start (i.e. some mod author already knows they will only ever have 2 items)
	 *
	 * @param configured if the mod has already been configured in the past
	 * @param function the function to run upon registering stuff
	 */
	public static void scheduleRegistry(boolean configured, Runnable function) {
		if (configured) configuredRegsitryFunctions.add(function);
		else regsitryFunctions.add(function);
	}
	
	@SuppressWarnings("unused")
	private static void runRegistry() {
		for (Runnable regsitryFunction : configuredRegsitryFunctions) regsitryFunction.run();
		for (Runnable regsitryFunction : smartRregsitryFunctions) regsitryFunction.run();
		for (Runnable regsitryFunction : regsitryFunctions) regsitryFunction.run();
	}
}
