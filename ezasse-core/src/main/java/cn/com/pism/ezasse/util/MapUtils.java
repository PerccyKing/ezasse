package cn.com.pism.ezasse.util;

import java.util.Map;

/**
 * @author PerccyKing
 * @since 25-03-29 14:47
 */
public class MapUtils {

    private MapUtils() {
    }

    public static <K> String getString(final Map<? super K, ?> map, final K key) {
        if (map != null) {
            final Object answer = map.get(key);
            if (answer != null) {
                return answer.toString();
            }
        }
        return null;
    }

    public static boolean isEmpty(final Map<?,?> map) {
        return map == null || map.isEmpty();
    }

}
