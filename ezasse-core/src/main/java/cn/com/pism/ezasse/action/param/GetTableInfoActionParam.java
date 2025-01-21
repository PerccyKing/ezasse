package cn.com.pism.ezasse.action.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author PerccyKing
 * @since 24-12-29 15:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTableInfoActionParam implements ActionParam {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 列名
     */
    private String columnName;
}
