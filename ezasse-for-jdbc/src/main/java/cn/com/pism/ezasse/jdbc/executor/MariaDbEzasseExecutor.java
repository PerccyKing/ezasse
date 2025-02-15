package cn.com.pism.ezasse.jdbc.executor;

/**
 * @author PerccyKing
 * @since 25-01-20 21:26
 */
public class MariaDbEzasseExecutor extends MysqlEzasseExecutor {

    @Override
    public String getDataSourceType() {
        return "MAIRADB";
    }
}
