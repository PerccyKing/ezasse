package cn.com.pism.ezasse.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author PerccyKing
 * @since 24-11-13 23:13
 */
public class EzasseJsonUtil {

    private EzasseJsonUtil() {
    }

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String toJsonString(Object object) {
        return OBJECT_MAPPER.valueToTree(object).toString();
    }
}
