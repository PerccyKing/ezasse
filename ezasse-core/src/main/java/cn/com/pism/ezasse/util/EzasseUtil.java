package cn.com.pism.ezasse.util;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import static cn.com.pism.ezasse.constants.EzasseDatabaseTypeConstants.UNKNOWN;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/06 下午 11:35
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

    public static String getDatabaseTypeFromDataSource(DataSource dataSource) {
        String databaseType = getFromDataSource(dataSource, connection -> {
            try {
                return connection.getMetaData().getDatabaseProductName().toUpperCase(Locale.ROOT);
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
