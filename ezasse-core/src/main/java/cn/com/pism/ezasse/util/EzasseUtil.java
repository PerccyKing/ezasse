package cn.com.pism.ezasse.util;

import cn.com.pism.ezasse.exception.EzasseException;
import cn.com.pism.ezasse.model.EzasseTableInfo;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.Resource;
import io.github.classgraph.ScanResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static cn.com.pism.ezasse.constants.EzasseConstants.*;
import static cn.com.pism.ezasse.enums.EzasseExceptionCode.UNSPECIFIED_FOLDER_EXCEPTION;


/**
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/06 下午 11:35
 */
public class EzasseUtil {

    private static final Log log = LogFactory.getLog(EzasseUtil.class);

    public static final String CLASSPATH_PREFIX = "classpath:";

    public static final String CLASSPATH_ALL_PREFIX = "classpath*:";


    private EzasseUtil() {
    }


    /**
     * <p>
     * 从databse中获取 数据库名称
     * </p>
     * by PerccyKing
     *
     * @param dataSource : datasource
     * @return {@link String} 数据库名称
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
        if (CollUtils.isEmpty(mapList)) {
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
        try (Connection connection = dataSource.getConnection()) {
            return callback.call(connection);
        } catch (SQLException e) {
            EzasseLogUtil.error(log, e.getMessage());
            return null;
        }
    }

    /**
     * 从指定文件夹中获取所有资源文件
     * <p>
     * 扫描 classpath 下指定文件夹中的所有资源文件，不限制文件扩展名。
     * 支持 {@code classpath:} 前缀。
     * </p>
     *
     * @param folder 文件夹路径
     * @return 该文件夹下的所有资源集合
     * @throws EzasseException 如果未指定文件夹
     */
    public static Collection<Resource> getResourcesFromFolder(String folder) {
        //没有指定文件夹时，抛出异常
        if (StringUtils.isBlank(folder)) {
            throw new EzasseException(UNSPECIFIED_FOLDER_EXCEPTION);
        }

        String path = folder;
        if (folder.startsWith(CLASSPATH_PREFIX)) {
            path = path.substring(CLASSPATH_PREFIX.length());
        }

        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        try (ScanResult scanResult = new ClassGraph().acceptPaths(path).scan()) {
            // 加载文件夹下的所有文件，不再限制扩展名
            return scanResult.getAllResources();
        }
    }

    /**
     * 从字符串中删除“第 n 次出现指定字符之前（包含该字符）”的所有内容，
     * 返回剩余的子字符串。
     *
     * <p>示例：</p>
     * <pre>
     * removeBeforeNthOccurrence("a/b/c/d", '/', 2) -> "c/d"
     * removeBeforeNthOccurrence("hello-world-test", '-', 1) -> "world-test"
     * removeBeforeNthOccurrence("abc", '/', 1) -> "abc"  // 未找到指定字符
     * </pre>
     *
     * @param input      原始字符串
     * @param targetChar 目标字符（用于查找出现位置）
     * @param n          第 n 次出现（从 1 开始计数）
     * @return 删除第 n 次出现 targetChar 之前（含该字符）后的字符串；
     * 如果 input 为 null、空字符串，或 n <= 0，则直接返回 input；
     * 如果 targetChar 出现次数不足 n 次，则返回原字符串
     */
    public static String removeBeforeNthOccurrence(String input, char targetChar, int n) {
        // 边界条件处理：
        // 1. input 为 null 或空字符串
        // 2. n 非法（<= 0）
        if ((input == null || input.isEmpty()) || n <= 0) {
            return input;
        }

        // 记录 targetChar 出现次数
        int count = 0;

        // 遍历字符串
        for (int i = 0; i < input.length(); i++) {
            // 判断当前字符是否为目标字符
            if (input.charAt(i) == targetChar) {
                count++;

                // 当达到第 n 次出现时
                if (count == n) {
                    // 返回该字符之后的子串（不包含该字符本身）
                    return input.substring(i + 1);
                }
            }
        }

        // 如果 targetChar 出现次数不足 n 次，则返回原字符串
        return input;
    }

}
