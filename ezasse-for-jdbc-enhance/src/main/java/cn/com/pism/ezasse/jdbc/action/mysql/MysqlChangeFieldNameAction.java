package cn.com.pism.ezasse.jdbc.action.mysql;

import cn.com.pism.ezasse.jdbc.action.param.ChangeFieldNameActionParam;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.com.pism.ezasse.jdbc.constants.EzasseJdbcEnhanceConstants.CHANGE_FIELD_NAME;

/**
 * @author PerccyKing
 * @since 25-03-23 15:57
 */
public class MysqlChangeFieldNameAction extends MysqlDoChangeAction<ChangeFieldNameActionParam> {

    private static final String SQL = "ALTER TABLE %s CHANGE %s %s %s;";

    @Override
    String getTableName(ChangeFieldNameActionParam param) {
        return param.getTableName();
    }

    @Override
    String buildChangeSql(String tableDdl, ChangeFieldNameActionParam param) {
        String columnDefinition = getColumnDefinition(tableDdl, param.getFieldName());
        if (StringUtils.isBlank(columnDefinition)) {
            return "";
        }

        return String.format(SQL, param.getTableName(), param.getFieldName(), param.getNewFieldName(), columnDefinition);
    }

    @Override
    public String getId() {
        return CHANGE_FIELD_NAME;
    }

    private String getColumnDefinition(String ddl, String columnName) {
        Pattern pattern = Pattern.compile("`" + columnName + "`\\s+([^,]+)");
        Matcher matcher = pattern.matcher(ddl);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }
}
