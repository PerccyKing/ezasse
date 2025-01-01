package cn.com.pism.ezasse.action.param;

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
