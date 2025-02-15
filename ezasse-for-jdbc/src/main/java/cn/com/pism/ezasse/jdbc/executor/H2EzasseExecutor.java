package cn.com.pism.ezasse.jdbc.executor;

/**
 * @author PerccyKing
 * @since 25-01-27 15:37
 */
public class H2EzasseExecutor extends JdbcTemplateExecutor {

    @Override
    public String getDataSourceType() {
        return "H2";
    }
}
