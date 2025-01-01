package cn.com.pism.ezasse.action;

import lombok.Data;

/**
 * @author PerccyKing
 * @since 24-12-29 15:45
 */
@Data
public class GetTableInfoActionParam implements ActionParam {

    /**
     * 表名
     */
    private String tableName;
}
