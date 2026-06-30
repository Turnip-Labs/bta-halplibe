package turniplabs.halplibe.util.dependency;

import org.jspecify.annotations.NonNull;

public record Key(@NonNull String modId, @NonNull String dependsOn) {
    private static final String BTA = "bta";

    public static @NonNull Key of(@NonNull String modId) {
        return new Key(modId, BTA);
    }

    public static @NonNull Key of(@NonNull String modId, @NonNull String dependsOn) {
        return new Key(modId, dependsOn);
    }
}
