package cn.com.pism.ezasse;

import cn.com.pism.ezasse.exception.EzasseException;
import cn.com.pism.ezasse.model.EzasseConfig;
import cn.com.pism.ezasse.model.EzasseSql;
import cn.com.pism.resourcescanner.Scanner;
import cn.com.pism.resourcescanner.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static cn.com.pism.ezasse.EzasseConstants.*;
import static cn.com.pism.ezasse.enums.EzasseExceptionCode.UNSPECIFIED_FOLDER_EXCEPTION;


/**
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/04 下午 10:49
 * @since 0.0.1
 */
@Data
@Slf4j
public class Ezasse {

    private Map<String, DataSource> dataSourceMap;

    private List<SqlGroup> sqlGroups;

    public Ezasse() {
        this.dataSourceMap = new HashMap<>(0);
    }

    /**
     * <p>
     * 执行脚本
     * </p>
     *
     * @author PerccyKing
     * @date 2022/04/04 下午 11:18
     */
    public void executeScript(EzasseConfig config) {
        //获取SQL文件列表
        List<EzasseSql> ezasseSqls = getEzasseSqlList(config);
        //确认文件顺序
        //确认数据源
        //按文件执行SQL脚本
    }

    /**
     * <p>
     * 获取文件列表
     * </p>
     *
     * @param config : 配置
     * @return {@link List<EzasseSql>}   sql文件对象集合
     * @author PerccyKing
     * @date 2022/04/06 上午 09:49
     */
    private List<EzasseSql> getEzasseSqlList(EzasseConfig config) {
        String folder = config.getFolder();
        //没有指定文件时，抛出异常
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
        Collection<LoadableResource> resources = scanner.getResources("", SQL_EXTENSION);
        //过滤一次
        List<String> fileList = config.getFileList();
        if (CollectionUtils.isNotEmpty(fileList)) {
            resources = resources.stream().filter(r -> {
                for (String file : fileList) {
                    if (r.getFilename().startsWith(file)) {
                        return true;
                    }
                }
                return false;
            }).collect(Collectors.toCollection(ArrayList::new));
        }
        List<EzasseSql> ezasseSqls = new ArrayList<>();
        resources.forEach(r -> ezasseSqls.add(absolutePathToEzasseSql(r.getAbsolutePath())));
        return ezasseSqls;
    }


    /**
     * <p>
     * 适配filePath
     * </p>
     *
     * @param folder   : 文件根路径
     * @param filePath : 文件路径
     * @return {@link String}
     * @author PerccyKing
     * @date 2022/04/05 下午 11:24
     */
    private String adaptationFilePath(String folder, String filePath) {
        if (folder.startsWith(BACK_SLASH) && !folder.endsWith(BACK_SLASH)) {
            //以 / 开始 不以 / 结尾
            filePath = BACK_SLASH + StringUtils.removeEnd(filePath, BACK_SLASH);
        }
        if (folder.startsWith(BACK_SLASH) && folder.equals(BACK_SLASH)) {
            //以 / 开始 以 / 结尾
            filePath = BACK_SLASH + filePath;
        }
        if (!folder.startsWith(BACK_SLASH) && !folder.endsWith(BACK_SLASH)) {
            filePath = StringUtils.removeEnd(filePath, BACK_SLASH);
        }
        return filePath;
    }

    /**
     * <p>
     * 将文件名转换为ezSql对象
     * </p>
     * <pre>
     *     group-datasource-order-other.sql
     *     文件名 最长为4位
     *     group:分组，不可为空
     *     datasource:执行的数据节点，可以为空
     *     order:排序，可以为空，约定为001|002|003|...|999
     *     other:可以为空，没有实际作用
     * </pre>
     *
     * @param absolutePath : 文件路径
     * @return {@link EzasseSql}
     * @author PerccyKing
     * @date 2022/04/05 下午 01:41
     */
    private EzasseSql absolutePathToEzasseSql(String absolutePath) {
        String[] absolutePathSplit = absolutePath.split(BACK_SLASH);
        String fileName = absolutePathSplit[absolutePathSplit.length - 1];
        String filePath = absolutePath.substring(0, absolutePath.lastIndexOf(BACK_SLASH) + 1);
        String[] split = fileName.split(MINUS_SIGN);
        EzasseSql ezasseSql = new EzasseSql();
        ezasseSql.setGroup(split[0]);
        ezasseSql.setName(fileName);
        ezasseSql.setParentPath(filePath);
        for (int i = 1; i < split.length; i++) {
            //推断当前节点是什么数据
            //如果当前节点为三位数字，节点为排序
            String toBeConfirmed = split[i];
            if (NumberUtils.isDigits(toBeConfirmed)) {
                ezasseSql.setOrder(toBeConfirmed);
            }
            //如果当前节点为字符串，从map里面拿一次datasource
            if (dataSourceMap.get(toBeConfirmed) != null) {
                ezasseSql.setNode(toBeConfirmed);
            }
        }
        return ezasseSql;
    }

    public static void main(String[] args) {
        Ezasse ezasse = new Ezasse();
        EzasseConfig ezasseConfig = new EzasseConfig();
        ezasseConfig.setFolder("data");
        ezasse.executeScript(ezasseConfig);
    }

    /**
     * sql文件分组
     *
     * @author PerccyKing
     * @version 0.0.1
     * @date 2022/04/04 下午 11:19
     * @since 0.0.1
     */
    @Data
    static class SqlGroup {
        /**
         * sql文件所在目录
         */
        private String folder;

        /**
         * sql文件 名称
         * 格式：TYPE-{@code ${dataSource}}-ORDER-{@code OTHERS}<br>
         * eg:  data-master-001-users<br>
         */
        private List<String> files;

        /**
         * 顺序定义
         */
        private int order;
    }
}
