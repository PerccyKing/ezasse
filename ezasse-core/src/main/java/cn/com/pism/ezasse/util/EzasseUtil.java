package cn.com.pism.ezasse.util;

import cn.com.pism.ezasse.exception.EzasseException;
import cn.com.pism.ezasse.model.EzasseTableInfo;
import cn.com.pism.frc.resourcescanner.Scanner;
import cn.com.pism.frc.resourcescanner.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static cn.com.pism.ezasse.constants.EzasseConstants.*;
import static cn.com.pism.ezasse.constants.EzasseDatabaseTypeConstants.UNKNOWN;
import static cn.com.pism.ezasse.enums.EzasseExceptionCode.UNSPECIFIED_FOLDER_EXCEPTION;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/06 下午 11:35
 */
public class EzasseUtil {

    private static final Log log = LogFactory.getLog(EzasseUtil.class);

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
     * @since 2022/04/07 下午 03:49
     */
    public static String getDataBaseNameFromDataSource(DataSource dataSource) {
        String catalog = getFromDataSource(dataSource, connection -> {
            try {
                return connection.getCatalog();
            } catch (SQLException e) {
                if (log.isErrorEnabled()) {
                    log.error(e.getMessage(), e);
                }
                return "";
            }
        });
        if (StringUtils.isBlank(catalog)) {
            return "";
        }
        return catalog;
    }


    /**
     * 将maplist 转换为list对象
     *
     * @param mapList 查询出来的表基本信息
     * @return tableInfo 对象
     */
    public static List<EzasseTableInfo> toTableInfo(List<Map<String, Object>> mapList) {
        if (CollectionUtils.isEmpty(mapList)) {
            return Collections.emptyList();
        }
        List<EzasseTableInfo> tableInfos = new ArrayList<>();
        mapList.forEach(map -> {
            EzasseTableInfo tableInfo = new EzasseTableInfo();
            tableInfo.setColumnName(MapUtils.getString(map, COLUMN_NAME));
            tableInfo.setDataType(MapUtils.getString(map, DATA_TYPE));
            tableInfo.setDataLength(MapUtils.getString(map, DATA_LENGTH));
            tableInfo.setColumnComment(MapUtils.getString(map, COLUMN_COMMENT));
            tableInfos.add(tableInfo);
        });
        return tableInfos;
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
            EzasseLogUtil.error(log, e.getMessage());
            return null;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    EzasseLogUtil.error(log, e.getMessage());
                }
            }
        }
    }

    public static String getDatabaseTypeFromDataSource(DataSource dataSource) {
        String databaseType = getFromDataSource(dataSource, connection -> {
            try {
                return connection.getMetaData().getDatabaseProductName().toUpperCase(Locale.ROOT);
            } catch (SQLException e) {
                EzasseLogUtil.error(log, e.getMessage());
                return UNKNOWN;
            }
        });
        if (databaseType == null) {
            return UNKNOWN;
        }
        return databaseType;
    }

    public static Collection<LoadableResource> getResourcesFromFolder(String folder) {
        //没有指定文件夹时，抛出异常
        if (StringUtils.isBlank(folder)) {
            throw new EzasseException(UNSPECIFIED_FOLDER_EXCEPTION);
        }
        List<Location> locations = new ArrayList<>();
        if (folder.startsWith(CLASSPATH_PREFIX)) {
            locations.add(new Location(folder));
        } else {
            locations.add(new Location(CLASSPATH_PREFIX + folder));
        }
        //找到文件夹下的所有SQL文件
        Scanner<JavaMigration> scanner = new Scanner<>(
                JavaMigration.class,
                locations,
                Thread.currentThread().getContextClassLoader(),
                StandardCharsets.UTF_8,
                new ResourceNameCache(),
                new LocationScannerCache()
        );
        return scanner.getResources("", SQL_EXTENSION);
    }

    public static String removeBeforeNthOccurrence(String input, char targetChar, int n) {
        if ((input == null || input.isEmpty()) || n <= 0) {
            return input;
        }

        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == targetChar) {
                count++;
                if (count == n) {
                    return input.substring(i + 1);
                }
            }
        }

        return input;
    }

}
