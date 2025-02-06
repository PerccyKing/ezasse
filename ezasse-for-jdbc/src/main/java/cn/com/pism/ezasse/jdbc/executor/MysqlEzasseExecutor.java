package cn.com.pism.ezasse.jdbc.executor;

import cn.com.pism.ezasse.jdbc.action.mysql.MysqlGetTableInfoAction;
import cn.com.pism.ezasse.jdbc.action.mysql.MysqlTableExistsAction;
import cn.com.pism.ezasse.model.EzasseDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 执行器的mysql实现
 *
 * @author PerccyKing
 * @since 24-12-14 14:28
 */
public class MysqlEzasseExecutor extends JdbcTemplateExecutor {

    public MysqlEzasseExecutor(EzasseDataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void registerActions(EzasseDataSource ezasseDataSource, JdbcTemplate jdbcTemplate) {
        registerAction(MysqlGetTableInfoAction.build(jdbcTemplate));
        registerAction(new MysqlTableExistsAction(jdbcTemplate));
//        registerAction(new MysqlChangeFieldCommentAction(jdbcTemplate));
    }
}
