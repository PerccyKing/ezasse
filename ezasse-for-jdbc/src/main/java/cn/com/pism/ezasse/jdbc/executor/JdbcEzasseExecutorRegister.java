package cn.com.pism.ezasse.jdbc.executor;

import cn.com.pism.ezasse.executor.EzasseExecutor;
import cn.com.pism.ezasse.model.EzasseExecutorRegister;

import java.util.HashMap;
import java.util.Map;

import static cn.com.pism.ezasse.constants.EzasseDatabaseTypeConstants.*;

/**
 * @author PerccyKing
 * @since 25-02-06 23:47
 */
public class JdbcEzasseExecutorRegister implements EzasseExecutorRegister {
    @Override
    public Map<String, Class<? extends EzasseExecutor>> getExecutorMap() {
        Map<String, Class<? extends EzasseExecutor>> executorMap = new HashMap<>(16);
        executorMap.put(MYSQL, MysqlEzasseExecutor.class);
        executorMap.put(MARIADB, MariaDbEzasseExecutor.class);
        executorMap.put(ORACLE, OracleEzasseExecutor.class);
        executorMap.put(H2, H2EzasseExecutor.class);
        executorMap.put(HSQLDB, HsqlDbExecutor.class);
        return executorMap;
    }
}
