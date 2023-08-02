package cn.com.pism.ezasse.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static cn.com.pism.ezasse.constants.EzasseDatabaseTypeConstants.UNKNOWN;

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


    /**
     * <p>
     * 将resources 下的某个文件 读取到内存中
     * </p>
     * by PerccyKing
     *
     * @param path : 文件的路径
     * @return {@link List <String>} 文件每行的内容
     * @since 2023/8/2 10:23
     */
    public static List<String> readLines(ClassLoader classLoader, String path) {
        try {
            InputStream inputStream = classLoader.getResourceAsStream(path);
            if (inputStream != null) {
                InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(isr);
                final List<String> list = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    list.add(line);
                }
                return list;
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    public static List<Resource> loadSqlFile(String path) {
        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(path);
            return Arrays.stream(resources).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
