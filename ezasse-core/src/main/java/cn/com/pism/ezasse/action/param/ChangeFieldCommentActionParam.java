package cn.com.pism.ezasse.action.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author PerccyKing
 * @since 25-02-02 23:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeFieldCommentActionParam implements ActionParam {
    /**
     * 表名
     */
    private String tableName;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * comment
     */
    private String comment;
}
