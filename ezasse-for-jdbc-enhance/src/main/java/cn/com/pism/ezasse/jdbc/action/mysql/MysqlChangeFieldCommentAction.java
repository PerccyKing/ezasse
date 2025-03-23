package cn.com.pism.ezasse.jdbc.action.mysql;

import cn.com.pism.ezasse.jdbc.action.param.ChangeFieldCommentActionParam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.CHANGE_FIELD_COMMENT;

/**
 * @author PerccyKing
 * @since 25-02-02 23:21
 */
public class MysqlChangeFieldCommentAction extends MysqlDoChangeAction<ChangeFieldCommentActionParam> {

    private static final String SQL = "ALTER TABLE `%s` MODIFY COLUMN `%s` %s COMMENT '%s'";

    @Override
    String getTableName(ChangeFieldCommentActionParam param) {
        return param.getTableName();
    }

    @Override
    String buildChangeSql(String tableDdl, ChangeFieldCommentActionParam param) {
        String columnName = param.getFieldName();
        String columnType = extractColumnType(tableDdl, columnName);
        return String.format(SQL, param.getTableName(), columnName, columnType, param.getComment());
    }


    @Override
    public String getId() {
        return CHANGE_FIELD_COMMENT;
    }


    /**
     * 解析字段类型（从 DDL 提取字段信息）
     *
     * @param ddl        表的 CREATE 语句
     * @param columnName 需要查找的字段
     * @return 字段类型（包含数据类型、默认值等）
     */
    private String extractColumnType(String ddl, String columnName) {
        Pattern pattern = Pattern.compile(
                // 匹配字段定义部分
                "`" + columnName + "`\\s+([^,]*)",
                Pattern.CASE_INSENSITIVE
        );
        Matcher matcher = pattern.matcher(ddl);

        if (matcher.find()) {
            String columnDefinition = matcher.group(1).trim();

            // 去掉 COMMENT 部分
            return columnDefinition.replaceAll("COMMENT\\s+'[^']*'", "").trim();
        }

        return null;
    }
}
