package cn.com.pism.ezasse.jdbc.action.mysql;

import cn.com.pism.ezasse.jdbc.action.JdbcGetTableInfoActionBuilder;
import cn.com.pism.ezasse.action.param.GetTableInfoActionParam;
import org.springframework.jdbc.core.JdbcTemplate;

import static cn.com.pism.ezasse.util.EzasseUtil.getDataBaseNameFromDataSource;

/**
 * @author PerccyKing
 * @since 24-12-28 11:17
 */
public class MysqlGetTableInfoAction {

    private MysqlGetTableInfoAction() {
    }

    public static JdbcGetTableInfoActionBuilder build(JdbcTemplate jdbcTemplate) {
        return JdbcGetTableInfoActionBuilder.builder()
                .selectColumnName("COLUMN_NAME")
                .selectDataType("DATA_TYPE")
                .selectDataLength("CHARACTER_MAXIMUM_LENGTH")
                .selectColumnComment("COLUMN_COMMENT")
                .from("Information_schema.columns")
                .where("TABLE_NAME", GetTableInfoActionParam::getTableName)
                .where("COLUMN_NAME", GetTableInfoActionParam::getColumnName)
                .where("TABLE_SCHEMA", getDataBaseNameFromDataSource(jdbcTemplate.getDataSource()))
                .jdbcTemplate(jdbcTemplate)
                .build();
    }

}
