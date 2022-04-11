package cn.com.pism.ezasse.executor;

import static cn.com.pism.ezasse.constants.EzasseDatabaseTypeConstants.MARIADB;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/10 下午 12:39
 * @since 0.0.1
 */
public class MariaDbEzasseExecutor extends MysqlEzasseExecutor {
    @Override
    public String getId() {
        return MARIADB;
    }
}
