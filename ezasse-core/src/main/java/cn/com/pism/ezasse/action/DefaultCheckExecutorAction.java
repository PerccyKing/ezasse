package cn.com.pism.ezasse.action;

import cn.com.pism.ezasse.action.param.DefaultCheckActionParam;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.DEFAULT_CHECK;

/**
 * @author PerccyKing
 * @since 24-12-29 17:05
 */
public class DefaultCheckExecutorAction implements EzasseExecutorAction<DefaultCheckActionParam, Boolean> {


    private final JdbcTemplate jdbcTemplate;

    public DefaultCheckExecutorAction(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @SuppressWarnings("all")
    public Boolean doAction(DefaultCheckActionParam actionParam) {
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
