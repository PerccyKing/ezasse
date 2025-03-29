package cn.com.pism.ezasse.util;

import java.util.Collection;

/**
 * @author PerccyKing
 * @since 25-03-29 14:41
 */
public class CollUtils {

    private CollUtils() {
    }

    public static boolean isEmpty(final Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }
}
