package cn.com.pism.ezasse.jdbc.action.param;

import cn.com.pism.ezasse.model.ActionParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author PerccyKing
 * @since 25-03-23 15:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeFieldNameActionParam implements ActionParam {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 新的字段名
     */
    private String newFieldName;
}
