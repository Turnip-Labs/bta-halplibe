package turniplabs.halplibe.util;

import turniplabs.halplibe.util.toml.Toml;

import java.util.HashMap;

/**
 * Mainly
 */
public abstract class ConfigUpdater {
    Toml updating;

    public static ConfigUpdater fromProperties(
            String... text
    ) {
        TomlConverter converter = new TomlConverter();
        for (int i = 0; i < text.length; i += 2) {
            converter.conversions.put(
                    text[i], text[i + 1]
            );
        }
        return converter;
    }

    public abstract void update();

    private static class TomlConverter extends ConfigUpdater {
        HashMap<String, String> conversions = new HashMap<>();

        @Override
        public void update() {
            // if it's a toml, then there's no point in updating
            for (String orderedKey : updating.getOrderedKeys()) {
                if (orderedKey.startsWith("."))
                    return;
            }

            for (String s : conversions.keySet()) {
                String str = updating.get(s).toString();
                updating.remove(s);
                updating.addEntry(conversions.get(s), str);
            }
        }
    }
}
