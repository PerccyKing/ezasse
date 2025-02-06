package cn.com.pism.ezasse.jdbc.action;

import cn.com.pism.ezasse.action.EzasseExecutorAction;
import cn.com.pism.ezasse.action.param.GetTableInfoActionParam;
import cn.com.pism.ezasse.model.EzasseTableInfo;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.GET_TABLE_INFO;

/**
 * @author PerccyKing
 * @since 25-01-27 15:30
 */
public abstract class JdbcGetTableInfoAction implements EzasseExecutorAction<GetTableInfoActionParam, List<EzasseTableInfo>> {

    private final JdbcTemplate jdbcTemplate;

    protected JdbcGetTableInfoAction(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String getId() {
        return GET_TABLE_INFO;
    }

    public DataSource getDataSource() {
        return this.jdbcTemplate.getDataSource();
    }

}
