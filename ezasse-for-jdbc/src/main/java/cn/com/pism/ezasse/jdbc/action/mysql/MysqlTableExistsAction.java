package cn.com.pism.ezasse.jdbc.action.mysql;

import cn.com.pism.ezasse.action.EzasseExecutorAction;
import cn.com.pism.ezasse.action.param.TableIsExistActionParam;
import org.springframework.jdbc.core.JdbcTemplate;

import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.TABLE_EXISTS;
import static cn.com.pism.ezasse.util.EzasseUtil.getDataBaseNameFromDataSource;

/**
 * @author PerccyKing
 * @since 25-01-13 22:59
 */
public class MysqlTableExistsAction implements EzasseExecutorAction<TableIsExistActionParam, Boolean> {

    private static final String SQL = "SELECT count(TABLE_NAME)>0  " +
            "FROM INFORMATION_SCHEMA.TABLES  " +
            "WHERE TABLE_NAME = ?  AND TABLE_SCHEMA = ?";

    private final JdbcTemplate jdbcTemplate;

    public MysqlTableExistsAction(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * <p>
     * 执行动作
     * </p>
     * by perccyking
     *
     * @param actionParam : 动作参数
     * @return {@link Boolean} 动作返回值
     * @since 25-01-01 11:19
     */
    @Override
    public Boolean doAction(TableIsExistActionParam actionParam) {
        return Boolean.FALSE.equals(jdbcTemplate.queryForObject(SQL, Boolean.class, actionParam.getTableName(), getDataBaseNameFromDataSource(this.jdbcTemplate.getDataSource())));
    }

    /**
     * <p>
     * 获取动作id
     * </p>
     * by perccyking
     *
     * @return {@link String} 动作id
     * @since 25-01-01 11:19
     */
    @Override
    public String getId() {
        return TABLE_EXISTS;
    }
}
