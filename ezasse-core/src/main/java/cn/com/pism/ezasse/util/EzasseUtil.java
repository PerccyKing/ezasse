package cn.com.pism.ezasse.util;

import cn.com.pism.ezasse.enums.EzasseDatabaseType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static cn.com.pism.ezasse.enums.EzasseDatabaseType.UNKNOWN;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/06 下午 11:35
 * @since 0.0.1
 */
@Slf4j
public class EzasseUtil {
    private EzasseUtil() {
    }

    public static <T> T getFromDataSource(DataSource dataSource, Callback<Connection, T> callback) {
        if (dataSource == null) {
            return null;
        }
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            return callback.call(connection);
        } catch (SQLException e) {
            log.error(e.getMessage());
            return null;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    public static EzasseDatabaseType getDatabaseTypeFromDataSource(DataSource dataSource) {
        EzasseDatabaseType databaseType = getFromDataSource(dataSource, connection -> {
            try {
                String productName = connection.getMetaData().getDatabaseProductName();
                return EzasseDatabaseType.valueOfName(productName);
            } catch (SQLException e) {
                log.error(e.getMessage());
                return UNKNOWN;
            }
        });
        if (databaseType == null) {
            return UNKNOWN;
        }
        return databaseType;
    }
}
