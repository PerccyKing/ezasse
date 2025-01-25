package cn.com.pism.ezasse.action.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author PerccyKing
 * @since 25-01-13 23:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TableIsExistActionParam implements ActionParam {

    /**
     * 表名
     */
    private String tableName;
}
