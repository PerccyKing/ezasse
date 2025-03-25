package cn.com.pism.ezasse.jdbc.action.mysql;

import cn.com.pism.ezasse.jdbc.action.param.ChangeFieldActionParam;
import cn.com.pism.ezasse.jdbc.util.SqlScriptUtil;

import static cn.com.pism.ezasse.jdbc.constants.EzasseDatabaseTypeConstants.MYSQL;
import static cn.com.pism.ezasse.jdbc.constants.EzasseJdbcEnhanceConstants.CHANGE_FIELD;

/**
 * @author PerccyKing
 * @since 25-03-25 22:23
 */
public class MysqlChangeFieldAction extends MysqlDoChangeAction<ChangeFieldActionParam> {
    @Override
    String getTableName(ChangeFieldActionParam param) {
        return param.getTableName();
    }

    @Override
    String buildChangeSql(String tableDdl, ChangeFieldActionParam param) {
        return SqlScriptUtil.generateAlterSql(MYSQL, tableDdl, param.getFieldName(), param.getNewFieldName(), param.getType(), param.getLength(), param.getComment());
    }

    @Override
    public String getId() {
        return CHANGE_FIELD;
    }
}
