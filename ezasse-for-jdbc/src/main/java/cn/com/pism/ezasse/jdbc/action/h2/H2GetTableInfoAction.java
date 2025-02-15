package cn.com.pism.ezasse.jdbc.action.h2;

import cn.com.pism.ezasse.jdbc.action.JdbcGetTableInfoActionBuilder;

import static cn.com.pism.ezasse.util.EzasseUtil.getDataBaseNameFromDataSource;

/**
 * @author PerccyKing
 * @since 25-01-27 15:40
 */
public class H2GetTableInfoAction {

    private H2GetTableInfoAction() {
    }

    public static JdbcGetTableInfoActionBuilder build() {
        return JdbcGetTableInfoActionBuilder.builder()
                .selectColumnName("COLUMN_NAME")
                .selectDataType("DATA_TYPE")
                .selectDataLength("CHARACTER_MAXIMUM_LENGTH")
                .selectColumnComment("REMARKS")
                .from("Information_schema.columns")
                .where("TABLE_NAME", whereCondition -> whereCondition.getActionParam().getTableName())
                .where("COLUMN_NAME", whereCondition -> whereCondition.getActionParam().getColumnName())
                .where("TABLE_SCHEMA", whereCondition -> getDataBaseNameFromDataSource(whereCondition.getDataSource().getDataSource()))
                .build();
    }
}
