package cn.com.pism.ezasse.jdbc.executor;

/**
 * @author PerccyKing
 * @since 25-02-02 22:43
 */
public class HsqlDbExecutor extends JdbcTemplateExecutor {

    @Override
    public String getDataSourceType() {
        return "HSQLDB";
    }
}
