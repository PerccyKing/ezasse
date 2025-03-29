package cn.com.pism.ezasse.util;

import java.util.function.Predicate;

/**
 * @author PerccyKing
 * @since 25-03-29 14:50
 */
public class IterableUtils {

    private IterableUtils() {
    }

    public static <T> T find(Iterable<T> iterable, Predicate<T> predicate) {
        if (iterable == null || predicate == null) {
            return null;
        }
        for (T element : iterable) {
            if (predicate.test(element)) {
                return element;
            }
        }
        return null;
    }
}
