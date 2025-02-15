package cn.com.pism.ezasse.jdbc.action.mysql;

import cn.com.pism.ezasse.jdbc.action.JdbcGetTableInfoActionBuilder;

import static cn.com.pism.ezasse.util.EzasseUtil.getDataBaseNameFromDataSource;

/**
 * @author PerccyKing
 * @since 24-12-28 11:17
 */
public class MysqlGetTableInfoAction {

    private MysqlGetTableInfoAction() {
    }

    public static JdbcGetTableInfoActionBuilder build() {
        return JdbcGetTableInfoActionBuilder.builder()
                .selectColumnName("COLUMN_NAME")
                .selectDataType("DATA_TYPE")
                .selectDataLength("CHARACTER_MAXIMUM_LENGTH")
                .selectColumnComment("COLUMN_COMMENT")
                .from("Information_schema.columns")
                .where("TABLE_NAME", whereCondition -> whereCondition.getActionParam().getTableName())
                .where("COLUMN_NAME", whereCondition -> whereCondition.getActionParam().getColumnName())
                .where("TABLE_SCHEMA", whereCondition -> getDataBaseNameFromDataSource(whereCondition.getDataSource().getDataSource()))
                .build();
    }

}
