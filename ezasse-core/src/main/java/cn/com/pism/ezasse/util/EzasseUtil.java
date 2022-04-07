package cn.com.pism.ezasse.util;

import cn.com.pism.ezasse.model.EzasseDataSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;

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
    public static String getDataBaseNameFromDataSource(EzasseDataSource dataSource) {
        if (dataSource == null) {
            return "";
        }
        try {
            Connection connection = dataSource.getDatasource().getConnection();
            return connection.getCatalog();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return "";
        }
    }

    public static String getDatabaseTypeFromDataSource(EzasseDataSource dataSource) {
        if (dataSource == null) {
            return "";
        }
        try {
            Connection connection = dataSource.getDatasource().getConnection();
            return connection.getMetaData().getDatabaseProductName();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return "";
        }
    }
}
