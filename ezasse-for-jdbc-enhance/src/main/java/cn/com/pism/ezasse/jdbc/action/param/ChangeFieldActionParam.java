package cn.com.pism.ezasse.jdbc.action.param;

import cn.com.pism.ezasse.model.ActionParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author PerccyKing
 * @since 25-03-25 22:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeFieldActionParam implements ActionParam {
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

    /**
     * 数据类型
     */
    private String type;

    /**
     * 长度
     */
    private String length;

    /**
     * comment
     */
    private String comment;

}
