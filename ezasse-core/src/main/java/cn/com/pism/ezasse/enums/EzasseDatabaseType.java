package cn.com.pism.ezasse.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/06 下午 08:39
 * @since 0.0.1
 */
public enum EzasseDatabaseType {
    /**
     * 数据库类型
     */
    MYSQL("MYSQL"),
    ORACLE("ORACLE"),
    UNKNOWN("UNKNOWN");
    /**
     * 数据库名称
     */
    @Getter
    private final String name;

    EzasseDatabaseType(String name) {
        this.name = name;
    }

    public static EzasseDatabaseType valueOfName(String name) {
        if (StringUtils.isBlank(name)) {
            return UNKNOWN;
        }
        EzasseDatabaseType[] values = EzasseDatabaseType.values();
        for (EzasseDatabaseType value : values) {
            if (value.getName().equals(name.toUpperCase(Locale.ROOT))) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
