package cn.com.pism.ezasse.model;

import lombok.Data;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/07 下午 08:57
 * @since 0.0.1
 */
@Data
public class EzasseTableInfo {

    /**
     * 列名
     */
    private String columnName;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 字段长度
     */
    private String characterMaximumLength;

    /**
     * 备注
     */
    private String columnComment;
}
