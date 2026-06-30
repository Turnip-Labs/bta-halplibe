package turniplabs.halplibe.event;

import org.jspecify.annotations.NonNull;

@SuppressWarnings("unused")
public interface IndirectEmitter<EMITTER> {
    @NonNull EMITTER getEmitter();
}
