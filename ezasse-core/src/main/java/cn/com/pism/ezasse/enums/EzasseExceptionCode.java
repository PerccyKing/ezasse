package cn.com.pism.ezasse.enums;

import lombok.Getter;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/05 下午 12:50
 * @since 0.0.1
 */
@Getter
public enum EzasseExceptionCode {
    /**
     * 未指定目录异常
     */
    UNSPECIFIED_FOLDER_EXCEPTION("未指定目录", "UNSPECIFIED_FOLDER"),
    /**
     * 未指定分组
     */
    UNSPECIFIED_GROUP_EXCEPTION("未指定GROUP", "UNSPECIFIED_GROUP"),
    ;

    EzasseExceptionCode(String message, String code) {
        this.message = message;
        this.code = code;
    }

    /**
     * 异常信息
     */
    private final String message;

    /**
     * 异常代码
     */
    private final String code;
}
