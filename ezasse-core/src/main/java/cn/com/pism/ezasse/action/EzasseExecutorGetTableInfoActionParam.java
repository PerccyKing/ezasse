package cn.com.pism.ezasse.action;

import lombok.Data;

/**
 * @author PerccyKing
 * @since 24-12-29 15:45
 */
@Data
public class EzasseExecutorGetTableInfoActionParam implements EzasseExecutorActionParam {

    /**
     * 表名
     */
    private String tableName;
}
