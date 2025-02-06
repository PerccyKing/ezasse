package cn.com.pism.ezasse.jdbc.executor;

import cn.com.pism.ezasse.model.EzasseDataSource;

/**
 * @author PerccyKing
 * @since 25-01-20 21:26
 */
public class MariaDbEzasseExecutor extends MysqlEzasseExecutor {

    public MariaDbEzasseExecutor(EzasseDataSource dataSource) {
        super(dataSource);
    }
}
