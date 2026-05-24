package turniplabs.halplibe.util;

import org.jspecify.annotations.NullMarked;

import java.lang.reflect.Array;

@NullMarked
public final class ArrayUtils {

    @SuppressWarnings("unchecked")
    public static <T> T[] newArray(Class<?> clazz, final int length) {
        return (T[]) Array.newInstance(clazz, length);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] addAll(final T[] first, final T[] second) {
        T[] newArr = (T[]) Array.newInstance(second.getClass(), first.length + second.length);
        System.arraycopy(first, 0, newArr, 0, first.length);
        System.arraycopy(second, 0, newArr, first.length, second.length);

        return newArr;
    }

    private ArrayUtils() {}
}
