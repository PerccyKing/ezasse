package cn.com.pism.ezasse;

import cn.com.pism.ezasse.calibrator.DefaultKeyWordEzasseCalibrator;
import cn.com.pism.ezasse.calibrator.EzasseCalibrator;
import cn.com.pism.ezasse.database.EzasseExecutor;
import cn.com.pism.ezasse.exception.EzasseException;
import cn.com.pism.ezasse.model.EzasseConfig;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseSql;
import cn.com.pism.ezasse.util.CollectionUtil;
import cn.com.pism.resourcescanner.Scanner;
import cn.com.pism.resourcescanner.*;
import cn.hutool.core.io.FileUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static cn.com.pism.ezasse.EzasseConstants.*;
import static cn.com.pism.ezasse.enums.EzasseExceptionCode.UNSPECIFIED_FOLDER_EXCEPTION;
import static cn.com.pism.ezasse.enums.EzasseExceptionCode.UNSPECIFIED_GROUP_EXCEPTION;


/**
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/04 下午 10:49
 * @since 0.0.1
 */
@Data
@Slf4j
public class Ezasse {

    private Map<String, EzasseDataSource> dataSourceMap;

    /**
     * 配置
     */
    private EzasseConfig config;

    /**
     * 校验器列表
     */
    private List<EzasseCalibrator> calibrators;

    private final String MASTER_ID = "master";

    public Ezasse() {
        this.dataSourceMap = new HashMap<>(0);
        //添加校验器
        addCalibrator(new DefaultKeyWordEzasseCalibrator());
    }

    /**
     * <p>
     * 执行脚本
     * </p>
     *
     * @author PerccyKing
     * @date 2022/04/04 下午 11:18
     */
    public void executeScript() {
        dataSourceMap.put(MASTER_ID, config.getMaster());
        //获取SQL文件列表
        List<EzasseSql> ezasseSqls = getEzasseSqlList(config);
        //对文件分组排序
        Map<String, List<EzasseSql>> ezasseSqlMap = ezasseSqls.stream().collect(Collectors.groupingBy(EzasseSql::getGroup));
        //排序
        List<String> groupOrder = config.getGroupOrder();
        if (CollectionUtils.isEmpty(groupOrder) && ezasseSqlMap.size() > 1) {
            throw new EzasseException(UNSPECIFIED_GROUP_EXCEPTION);
        }
        if (CollectionUtils.isEmpty(groupOrder)) {
            ezasseSqlMap.forEach((k, v) -> groupParsing(config, v));
        } else {
            groupOrder.forEach(order -> groupParsing(config, ezasseSqlMap.get(order)));
        }
    }

    /**
     * <p>
     * 分组解析
     * </p>
     *
     * @param config     : 配置
     * @param ezasseSqls : sql对象
     * @author PerccyKing
     * @date 2022/04/06 上午 11:04
     */
    private void groupParsing(EzasseConfig config, List<EzasseSql> ezasseSqls) {
        if (CollectionUtils.isNotEmpty(ezasseSqls)) {
            //对list进行排序
            ezasseSqls.stream().sorted((o1, o2) -> {
                String order1 = StringUtils.isNotBlank(o1.getOrder()) ? o1.getOrder() : "000";
                String order2 = StringUtils.isNotBlank(o2.getOrder()) ? o2.getOrder() : "000";
                return Integer.parseInt(order1) - Integer.parseInt(order2);
            }).forEach(sql -> doGroupParsing(config, sql));
        }
    }

    /**
     * <p>
     * 解析SQL
     * </p>
     *
     * @param config : 配置
     * @param sql    : sql脚本信息
     * @author PerccyKing
     * @date 2022/04/06 上午 11:24
     */
    private void doGroupParsing(EzasseConfig config, EzasseSql sql) {
        LinkedHashMap<String, String> scriptMap = new LinkedHashMap<>();
        //获取SQL文件
        List<String> lines = FileUtil.readLines(sql.getPath(), StandardCharsets.UTF_8);
        //标记以下行是否全部都是SQL执行体
        boolean isSqlBody = false;
        //存放当前的SQL执行体
        StringBuilder sqlLines = new StringBuilder();
        //存放当前的SQL校验行
        String checkLine = "";
        for (String line : lines) {
            //没有指定界定符
            if (StringUtils.isAnyBlank(config.getDelimiterStart(), config.getDelimiterEnd())) {
                if (isSqlBody && !isCheckLine(line)) {
                    sqlLines.append(line).append("\n");
                }
                //校验是否该结束上一个循环
                if (isSqlBody && isCheckLine(line)) {
                    //标记结束
                    isSqlBody = false;
                    //存入map，并重置标记点
                    scriptMap.put(checkLine, sqlLines.toString());
                    checkLine = "";
                    sqlLines = new StringBuilder();
                }
                // 判断当前行是否为关键字行
                if (isCheckLine(line)) {
                    isSqlBody = true;
                    checkLine = line.substring(LINE_COMMENT.length() + 1);
                }
            }
            //指定了界定符
            if (StringUtils.isNoneBlank(config.getDelimiterStart(), config.getDelimiterEnd())) {
                //当前行为结束界定符
                boolean isEndLine = isSqlBody && isCheckLine(line);
                if (line.startsWith(config.getDelimiterEnd()) || isEndLine) {
                    //标记结束
                    isSqlBody = false;
                    //存入map，并重置标记点
                    scriptMap.put(checkLine, sqlLines.toString());
                    checkLine = "";
                    sqlLines = new StringBuilder();
                }
                //当前行不是校验行，并且是sql执行体
                if (isSqlBody && !isCheckLine(line)) {
                    sqlLines.append(line).append("\n");
                }
                //当前行是否为关键字行
                if (isCheckLine(line)) {
                    checkLine = line.substring(LINE_COMMENT.length() + 1);
                }
                //当前行是开始界定符
                if (line.startsWith(config.getDelimiterStart()) && StringUtils.isNotBlank(checkLine) && StringUtils.isBlank(sqlLines.toString())) {
                    isSqlBody = true;
                }
            }
        }
        //没有指定限定符，还需要存一次map
        if (StringUtils.isAnyBlank(config.getDelimiterStart(), config.getDelimiterEnd())) {
            scriptMap.put(checkLine, sqlLines.toString());
        }
        scriptMap.forEach((k, v) -> doExecuteScript(k, v, sql));
    }

    /**
     * <p>
     * 执行脚本
     * </p>
     *
     * @param checkLine : 校验行 关键字.[次关键字].[校验节点].[执行节点](校验SQL/关键字)
     * @param sqlLine   : SQL行
     * @param ezasseSql : SQL文件信息
     * @author PerccyKing
     * @date 2022/04/06 下午 03:29
     */
    private void doExecuteScript(String checkLine, String sqlLine, EzasseSql ezasseSql) {
        if (StringUtils.isNotBlank(sqlLine)) {

            int startIndex = checkLine.indexOf("(");
            int endIndex = checkLine.lastIndexOf(")");
            String checkKey = checkLine.substring(0, startIndex);
            String checkContent = checkLine.substring(startIndex + 1, endIndex);
            String[] checkKeySplit = checkKey.split("\\.");
            String checkNode = "";
            String executeNode = "";
            if (checkKeySplit.length >= TWO) {
                //最后两位为 分别为校验节点和 执行节点
                if (checkKeySplit.length == FOUR) {
                    checkKey = checkKeySplit[0] + "." + checkKeySplit[1];
                    checkNode = checkKeySplit[2];
                } else if (checkKeySplit.length == THREE) {
                    //如果第二位不是数据节点，那第二位就是关键字
                    if (dataSourceMap.get(checkKeySplit[ONE]) == null) {
                        checkKey = checkKeySplit[0] + "." + checkKeySplit[1];
                        checkNode = checkKeySplit[2];
                    } else {
                        checkKey = checkKeySplit[0];
                        checkNode = checkKeySplit[1];
                    }
                } else if (checkKeySplit.length == TWO) {
                    if (dataSourceMap.get(checkKeySplit[ONE]) == null) {
                        checkKey = checkKeySplit[0] + "." + checkKeySplit[1];
                        checkNode = MASTER_ID;
                    } else {
                        checkKey = checkKeySplit[0];
                        checkNode = checkKeySplit[1];
                    }
                }
            }
            String finalCheckKey = checkKey;
            EzasseCalibrator ezasseCalibrator = IterableUtils.find(calibrators, s -> s.getId(config).equals(finalCheckKey));
            if (StringUtils.isBlank(checkNode)) {
                checkNode = ezasseSql.getNode();
            }
            if (StringUtils.isBlank(checkNode)) {
                checkNode = MASTER_ID;
            }
            log.info("{}={}", checkNode, checkKey);
            if (ezasseCalibrator.needToExecute(dataSourceMap.get(checkNode), checkContent)) {
                //获取执行器并执行SQL
                EzasseExecutor ezasseExecutor = null;
                ezasseExecutor.execute(sqlLine);
            }
        }
    }

    /**
     * <p>
     * 校验字符串是否为校验行
     * </p>
     *
     * @param line : 处理行信息
     * @return {@link boolean} true:是校验行，false：不是校验行
     * @author PerccyKing
     * @date 2022/04/06 下午 01:53
     */
    private boolean isCheckLine(String line) {
        if (line.startsWith(LINE_COMMENT)) {
            //以-- 开头，并且包含各个校验关键字
            String checkLine = line.substring(LINE_COMMENT.length() + 1);
            return StringUtils.startsWithAny(checkLine, config.getTable(), config.getChange(), config.getDefaultKeyWord());
        }
        return false;
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
        ezasseSql.setGroup(split[0].replace(SQL_EXTENSION, ""));
        ezasseSql.setName(fileName);
        ezasseSql.setParentPath(filePath);
        ezasseSql.setPath(absolutePath);
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
        ezasseConfig.setGroupOrder(Arrays.asList("data", "file"));
        ezasse.setConfig(ezasseConfig);
        ezasse.executeScript();
    }

    /**
     * <p>
     * 添加校验器
     * </p>
     *
     * @param ezasseCalibrator : 校验器
     * @author PerccyKing
     * @date 2022/04/06 下午 02:53
     */
    public void addCalibrator(EzasseCalibrator ezasseCalibrator) {
        //删除同id的数据
        EzasseCalibrator exit = IterableUtils.find(this.calibrators, c -> c.getId(config).equals(ezasseCalibrator.getId(config)));
        if (exit != null) {
            this.calibrators.remove(exit);
        }
        CollectionUtil.addToList(this.calibrators, ezasseCalibrator, l -> this.calibrators = l);
    }
}
