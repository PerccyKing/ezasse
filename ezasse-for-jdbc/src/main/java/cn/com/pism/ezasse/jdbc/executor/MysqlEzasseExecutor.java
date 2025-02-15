package cn.com.pism.ezasse.jdbc.executor;

/**
 * 执行器的mysql实现
 *
 * @author PerccyKing
 * @since 24-12-14 14:28
 */
public class MysqlEzasseExecutor extends JdbcTemplateExecutor {


    @Override
    public String getDataSourceType() {
        return "MYSQL";
    }
}
