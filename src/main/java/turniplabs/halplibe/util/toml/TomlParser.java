package turniplabs.halplibe.util.toml;

public class TomlParser {
    public static Toml parse(String src) {
        Toml toml1 = new Toml();

        String cat = "";
        for (String s : src.split("\n")) {
            s = s.trim();

            if (s.startsWith("[") && s.endsWith("]")) {
                while (s.startsWith("[") && s.endsWith("]")) {
                    s = s.substring(1, s.length() - 1);
                    cat = s;
                }
                cat = cat.trim();
                continue;
            }

            if (s.startsWith("#") || s.isEmpty()) continue;

            String[] split = s.split("=", 2);
            String key = cat + (cat.isEmpty() ? "" : ".") + split[0].trim();
            String value = split[1].trim();

            if (value.startsWith("\"")) {
                toml1.addEntry(key, new Entry<>(value.substring(1, value.length() - 1)));
            } else if (value.contains(".")) {
                toml1.addEntry(key, new Entry<>(Double.parseDouble(value)));
            } else {
                if (value.equals("true"))
                    toml1.addEntry(key, new Entry<>(true));
                else if (value.equals("false"))
                    toml1.addEntry(key, new Entry<>(false));
                else {
                    try {
                        toml1.addEntry(key, new Entry<>(Integer.parseInt(value)));
                    } catch (Throwable err) {
                        try {
                            toml1.addEntry(key, new Entry<>(Double.parseDouble(value)));
                        } catch (Throwable ignored1) {
                            toml1.addEntry(key, new Entry<>(value));
                        }
                    }
                }
            }
        }

        return toml1;
    }
}
