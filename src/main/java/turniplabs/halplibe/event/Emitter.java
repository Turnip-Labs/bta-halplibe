package turniplabs.halplibe.event;

import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public interface Emitter<LISTENER> {
    void emit(@NonNull Consumer<LISTENER> consumer);
}
