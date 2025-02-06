package cn.com.pism.ezasse.jdbc.action.mysql;

import cn.com.pism.ezasse.action.EzasseExecutorAction;
import cn.com.pism.ezasse.action.param.ChangeFieldCommentActionParam;
import org.springframework.jdbc.core.JdbcTemplate;

import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.CHANGE_FIELD_COMMENT;

/**
 * @author PerccyKing
 * @since 25-02-02 23:21
 */
public class MysqlChangeFieldCommentAction implements EzasseExecutorAction<ChangeFieldCommentActionParam, Boolean> {

    private static final String SQL = "ALTER TABLE %s MODIFY COLUMN %s COMMENT '%s'";

    private final JdbcTemplate jdbcTemplate;

    public MysqlChangeFieldCommentAction(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // todo 需要拿到表信息
    @Override
    public Boolean doAction(ChangeFieldCommentActionParam actionParam) {
        jdbcTemplate.execute(String.format(SQL, actionParam.getTableName(), actionParam.getFieldName(), actionParam.getComment()));
        return true;
    }


    @Override
    public String getId() {
        return CHANGE_FIELD_COMMENT;
    }
}
