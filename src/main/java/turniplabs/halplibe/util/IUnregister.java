package turniplabs.halplibe.util;

public interface IUnregister<T> {
    void bta_halplibe$unregister(String key);
    void bta_halplibe$unregister(T object);
}
