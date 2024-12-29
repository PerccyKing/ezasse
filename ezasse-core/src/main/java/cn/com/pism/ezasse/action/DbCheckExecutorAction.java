package cn.com.pism.ezasse.action;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.DEFAULT_CHECK;

/**
 * @author PerccyKing
 * @since 24-12-29 17:05
 */
public class DbCheckExecutorAction implements EzasseExecutorAction<DbCheckActionParam, Boolean> {


    private final JdbcTemplate jdbcTemplate;

    public DbCheckExecutorAction(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Boolean doAction(DbCheckActionParam actionParam) {
        Integer res;
        try {
            res = jdbcTemplate.queryForObject(actionParam.getCheckContent(), Integer.class);
        } catch (DataAccessException e) {
            return Boolean.FALSE;
        }
        if (res == null) {
            return false;
        }
        return res <= 0;
    }

    @Override
    public String getId() {
        return DEFAULT_CHECK;
    }
}
