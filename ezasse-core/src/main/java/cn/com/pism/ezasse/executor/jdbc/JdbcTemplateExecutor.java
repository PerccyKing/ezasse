package cn.com.pism.ezasse.executor.jdbc;

import cn.com.pism.ezasse.action.jdbc.DefaultCheckAction;
import cn.com.pism.ezasse.action.jdbc.DefaultDoExecuteAction;
import cn.com.pism.ezasse.executor.EzasseExecutor;
import cn.com.pism.ezasse.model.EzasseDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * jdbc 执行器
 *
 * @author PerccyKing
 * @since 25-01-13 22:37
 */
public abstract class JdbcTemplateExecutor extends EzasseExecutor {

    protected JdbcTemplateExecutor(EzasseDataSource ezasseDataSource) {
        super(ezasseDataSource);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(ezasseDataSource.getDataSource());

        //注册默认校验动作
        registerAction(new DefaultCheckAction(jdbcTemplate));
        registerAction(new DefaultDoExecuteAction(jdbcTemplate));

        registerActions(ezasseDataSource, jdbcTemplate);
    }

    protected void registerActions(EzasseDataSource ezasseDataSource, JdbcTemplate jdbcTemplate) {
        // do nothing
    }
}
