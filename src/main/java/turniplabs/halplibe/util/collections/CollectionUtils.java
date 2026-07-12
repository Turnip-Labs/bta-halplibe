package turniplabs.halplibe.util.collections;

import org.jspecify.annotations.NullMarked;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

@NullMarked
@SuppressWarnings("unused")
public final class CollectionUtils {

    public static <K, V> Map<K, V> mapCollection(final Collection<V> collection, final Map<K, V> outMap, final Function<V, K> mappingFunc) {
        collection.forEach(o -> outMap.put(mappingFunc.apply(o), o));
        return outMap;
    }

    private CollectionUtils() {}
}
