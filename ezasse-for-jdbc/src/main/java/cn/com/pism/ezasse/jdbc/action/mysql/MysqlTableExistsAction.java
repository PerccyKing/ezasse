package cn.com.pism.ezasse.jdbc.action.mysql;

import cn.com.pism.ezasse.jdbc.action.JdbcTemplateCache;
import cn.com.pism.ezasse.jdbc.action.param.TableIsExistActionParam;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseExecutorAction;

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
    public Boolean doAction(TableIsExistActionParam actionParam, EzasseDataSource dataSource) {
        return Boolean.FALSE.equals(JdbcTemplateCache.get(dataSource.getId()).queryForObject(SQL, Boolean.class, actionParam.getTableName(), getDataBaseNameFromDataSource(dataSource.getDataSource())));
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
