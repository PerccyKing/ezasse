package cn.com.pism.ezasse.action.jdbc.h2;

import cn.com.pism.ezasse.action.JdbcGetTableInfoActionBuilder;
import cn.com.pism.ezasse.action.param.GetTableInfoActionParam;
import org.springframework.jdbc.core.JdbcTemplate;

import static cn.com.pism.ezasse.util.EzasseUtil.getDataBaseNameFromDataSource;

/**
 * @author PerccyKing
 * @since 25-01-27 15:40
 */
public class H2GetTableInfoAction {

    private H2GetTableInfoAction() {
    }

    public static JdbcGetTableInfoActionBuilder build(JdbcTemplate jdbcTemplate) {
        return JdbcGetTableInfoActionBuilder.builder().jdbcTemplate(jdbcTemplate)
                .selectColumnName("COLUMN_NAME")
                .selectDataType("DATA_TYPE")
                .selectDataLength("CHARACTER_MAXIMUM_LENGTH")
                .selectColumnComment("REMARKS")
                .from("Information_schema.columns")
                .where("TABLE_NAME", GetTableInfoActionParam::getTableName)
                .where("COLUMN_NAME", GetTableInfoActionParam::getColumnName)
                .where("TABLE_SCHEMA", getDataBaseNameFromDataSource(jdbcTemplate.getDataSource()))
                .build();
    }
}
