package cn.com.pism.ezasse.jdbc.util;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import org.apache.commons.lang3.StringUtils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author PerccyKing
 * @since 25-03-24 21:44
 */
public class SqlScriptUtil {

    private static final String ALTER_SQL_TEMPLATE = "ALTER TABLE `%s` CHANGE COLUMN `%s` `%s` %s %s;";

    private SqlScriptUtil() {
    }

    public static String generateAlterSql(String datasourceType,
                                          String ddl,
                                          String column,
                                          String newColumn,
                                          String newType,
                                          String newLength,
                                          String newComment) {

        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Statement statement;
        try {
            statement = parserManager.parse(new StringReader(ddl));
        } catch (JSQLParserException e) {
            // ddl 解析失败
            return "";
        }
        CreateTable createTable = (CreateTable) statement;
        // 表名
        String tableName = createTable.getTable().getName().replace("`", "");

        // 修改后的字段名
        String modifiedColumnName = column;

        if (StringUtils.isNotBlank(newColumn)) {
            modifiedColumnName = newColumn;
        }

        // 目标字段
        ColumnDefinition targetColumn = getTargetColumnDefinition(column, createTable);

        if (targetColumn == null) {
            // 未找到目标字段
            return "";
        }

        String modifiedDataType = getModifiedDataType(targetColumn, datasourceType, newType, newLength);

        String modifiedComment = getModifiedComment(targetColumn, newComment);

        return String.format(ALTER_SQL_TEMPLATE, tableName, column, modifiedColumnName, modifiedDataType, modifiedComment).replace("``", "");
    }

    private static String getModifiedComment(ColumnDefinition targetColumn, String newComment) {
        // 空指针检查
        if (targetColumn == null) {
            return "";
        }

        List<String> columnSpecs = new ArrayList<>(targetColumn.getColumnSpecs());

        // 如果新注释为空，直接返回原列规格字符串
        if (StringUtils.isBlank(newComment)) {
            return String.join(" ", columnSpecs);
        }

        // 尝试找到已有的 COMMENT 关键字
        boolean commentFound = false;
        for (int i = 0; i < columnSpecs.size() - 1; i++) {
            if (columnSpecs.get(i).equalsIgnoreCase("COMMENT")) {
                columnSpecs.set(i + 1, "'" + newComment + "'");
                commentFound = true;
                break;
            }
        }

        // 如果未找到 COMMENT 关键字，添加新的注释
        if (!commentFound && StringUtils.isNotBlank(newComment)) {
            columnSpecs.add("COMMENT");
            columnSpecs.add("'" + newComment + "'");
        }

        return String.join(" ", columnSpecs);
    }

    private static String getModifiedDataType(ColumnDefinition targetColumn, String datasourceType, String newType, String newLength) {
        if (targetColumn == null || targetColumn.getColDataType() == null) {
            return "";
        }

        String dataType = targetColumn.getColDataType().getDataType();
        String modifiedDataType;

        if (StringUtils.isNotBlank(newType)) {
            modifiedDataType = newType;
        } else {
            modifiedDataType = dataType;
        }

        if (StringUtils.isNotBlank(newLength) && Boolean.TRUE.equals(SqlTypeUtil.isParamsEditable(datasourceType, modifiedDataType))) {
            modifiedDataType = appendLength(modifiedDataType, newLength);
        } else if (StringUtils.isBlank(newType)) {
            modifiedDataType = targetColumn.getColDataType().toString();
        }

        return modifiedDataType;
    }

    private static String appendLength(String dataType, String length) {
        if (length.contains("(") || length.contains(")")) {
            return dataType + length;
        } else {
            return dataType + "(" + length + ")";
        }
    }


    private static ColumnDefinition getTargetColumnDefinition(String column, CreateTable createTable) {
        // 字段信息
        List<ColumnDefinition> columnDefinitions = createTable.getColumnDefinitions();
        ColumnDefinition targetColumn = null;
        for (ColumnDefinition columnDefinition : columnDefinitions) {
            // 忽略大小写
            if (columnDefinition.getColumnName().replace("`", "").equalsIgnoreCase(column)) {
                targetColumn = columnDefinition;
                break;
            }
        }
        return targetColumn;
    }
}
