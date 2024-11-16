package cn.com.pism.ezasse.util;

import cn.com.pism.ezasse.exception.EzasseException;
import cn.com.pism.frc.resourcescanner.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static cn.com.pism.ezasse.constants.EzasseConstants.*;
import static cn.com.pism.ezasse.constants.EzasseConstants.LINE_COMMENT;
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

}
