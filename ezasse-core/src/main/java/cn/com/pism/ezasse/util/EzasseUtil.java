package cn.com.pism.ezasse.util;

import cn.com.pism.ezasse.enums.EzasseDatabaseType;
import lombok.extern.slf4j.Slf4j;

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

    /**
     * <p>
     * 从databse中获取 数据库名称
     * </p>
     *
     * @param dataSource : datasource
     * @return {@link String} 数据库名称
     * @author PerccyKing
     * @date 2022/04/07 下午 03:49
     */
    public static String getDataBaseNameFromDataSource(DataSource dataSource) {
        if (dataSource == null) {
            return "";
        }
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            return connection.getCatalog();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return "";
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
        if (dataSource == null) {
            return UNKNOWN;
        }
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            String productName = connection.getMetaData().getDatabaseProductName();
            return EzasseDatabaseType.valueOfName(productName);
        } catch (SQLException e) {
            log.error(e.getMessage());
            return UNKNOWN;
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
}
