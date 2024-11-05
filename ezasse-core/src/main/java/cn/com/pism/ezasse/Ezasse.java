package cn.com.pism.ezasse;

import cn.com.pism.ezasse.checker.DefaultKeyWordEzasseChecker;
import cn.com.pism.ezasse.checker.EzasseChecker;
import cn.com.pism.ezasse.checker.TableEzasseChecker;
import cn.com.pism.ezasse.checker.change.*;
import cn.com.pism.ezasse.exception.EzasseException;
import cn.com.pism.ezasse.executor.*;
import cn.com.pism.ezasse.model.EzasseCheckNode;
import cn.com.pism.ezasse.model.EzasseConfig;
import cn.com.pism.ezasse.model.EzasseSql;
import cn.com.pism.ezasse.util.EzasseLogUtil;
import cn.com.pism.ezasse.util.EzasseUtil;
import cn.com.pism.frc.resourcescanner.Scanner;
import cn.com.pism.frc.resourcescanner.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static cn.com.pism.ezasse.constants.EzasseConstants.*;
import static cn.com.pism.ezasse.enums.EzasseExceptionCode.UNSPECIFIED_FOLDER_EXCEPTION;
import static cn.com.pism.ezasse.enums.EzasseExceptionCode.UNSPECIFIED_GROUP_EXCEPTION;
import static cn.com.pism.ezasse.util.EzasseUtil.getResourcesFromFolder;


/**
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/04 下午 10:49
 */
@Deprecated
public class Ezasse {

    private static final Log log = LogFactory.getLog(Ezasse.class);

    /**
     * 配置
     */
    private EzasseConfig config;

    /**
     * 数据源
     */
    private final Map<String, DataSource> dataSourceMap;

    /**
     * 校验器
     */
    private final Map<String, EzasseChecker> checkerMap;

    /**
     * 执行器
     */
    private final Map<String, EzasseExecutor> executorMap;

    /**
     * 默认数据节点
     */
    private DataSource master;

    public static final String MASTER_ID = "master";


    public void setConfig(EzasseConfig config) {
        this.config = config;
    }

    public void setMaster(DataSource master) {
        this.master = master;
    }

    public Ezasse() {
        this.dataSourceMap = new HashMap<>(0);
        this.executorMap = new HashMap<>(0);
        this.checkerMap = new HashMap<>(0);
        initExecutor();
    }


    /**
     * <p>
     * 执行脚本
     * </p>
     *
     * @author PerccyKing
     * @since 2022/04/04 下午 11:18
     */
    public void executeScript() {
        //初始化master数据源
        dataSourceMap.put(MASTER_ID, master);
        //获取SQL文件列表
        List<EzasseSql> ezasseSqls = getEzasseSqlList(config);

        printLog(ezasseSqls);
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

    private void printLog(List<EzasseSql> ezasseSqls) {
        EzasseLogUtil.info(log, "Ezasse - Identified file list");
        EzasseLogUtil.info(log, String.format("%-8s %-8s %-8s %-20s %-20s", "group", "order", "node", "name", "path"));

        // 输出表格内容
        for (EzasseSql ezasseSql : ezasseSqls) {
            EzasseLogUtil.info(log, String.format("%-8s %-8s %-8s %-20s %-20s",
                    ezasseSql.getGroup(),
                    ezasseSql.getOrder(),
                    ezasseSql.getNode(),
                    ezasseSql.getName(),
                    ezasseSql.getPath()));
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
     * @since 2022/04/06 上午 11:04
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
     * @since 2022/04/06 上午 11:24
     */
    private void doGroupParsing(EzasseConfig config, EzasseSql sql) {
        EzasseGroupParser parser = new EzasseGroupParser(config, sql, checkerMap.keySet());
        LinkedHashMap<String, String> scriptMap = parser.parser();
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
     * @since 2022/04/06 下午 03:29
     */
    private void doExecuteScript(String checkLine, String sqlLine, EzasseSql ezasseSql) {
        if (StringUtils.isNotBlank(sqlLine)) {

            EzasseCheckNode ezasseCheckNode = new EzasseCheckNode(checkLine, ezasseSql, dataSourceMap);
            EzasseChecker ezasseChecker = checkerMap.get(ezasseCheckNode.getCheckKey());

            EzasseExecutor checkEzasseExecutor = getExecutorByDatasource(ezasseCheckNode.getCheckNode());
            checkEzasseExecutor.setDataSource(ezasseCheckNode.getCheckNode());
            if (ezasseChecker.needToExecute(ezasseCheckNode.getCheckNode(), ezasseCheckNode.getCheckContent(), checkEzasseExecutor)) {
                EzasseLogUtil.debug(log, "Ezasse - execute code block :" + checkLine);
                //获取执行器并执行SQL
                EzasseExecutor ezasseExecutor = getExecutorByDatasource(ezasseCheckNode.getExecNode());
                ezasseExecutor.setDataSource(ezasseCheckNode.getExecNode());
                ezasseExecutor.execute(sqlLine);
            }
        }
    }

    /**
     * <p>
     * 获取文件列表
     * </p>
     *
     * @param config : 配置
     * @return {@link List<EzasseSql>}   sql文件对象集合
     * @author PerccyKing
     * @since 2022/04/06 上午 09:49
     */
    private List<EzasseSql> getEzasseSqlList(EzasseConfig config) {
        String folder = config.getFolder();
        Collection<LoadableResource> resources = getResourcesFromFolder(folder);
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
     * @since 2022/04/05 下午 01:41
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

    /**
     * <p>
     * 添加校验器
     * </p>
     *
     * @param ezasseChecker : 校验器
     * @author PerccyKing
     * @since 2022/04/06 下午 02:53
     */
    public void addChecker(EzasseChecker ezasseChecker) {
        String id = ezasseChecker.getId(config);
        checkerMap.remove(id);
        checkerMap.put(id, ezasseChecker);
    }

    /**
     * <p>
     * 添加数据源
     * </p>
     *
     * @param id         : 数据源id
     * @param dataSource : 数据源
     * @author PerccyKing
     * @since 2022/04/09 下午 12:36
     */
    public void addDataSource(String id, DataSource dataSource) {
        dataSourceMap.remove(id);
        dataSourceMap.put(id, dataSource);
    }

    /**
     * <p>
     * 添加执行器
     * </p>
     *
     * @param executor : 执行器
     * @author PerccyKing
     * @since 2022/04/09 下午 12:38
     */
    public void addExecutor(EzasseExecutor executor) {
        executorMap.remove(executor.getId());
        executorMap.put(executor.getId(), executor);
    }

    public EzasseExecutor getExecutorByDatasource(DataSource dataSource) {
        return executorMap.get(EzasseUtil.getDatabaseTypeFromDataSource(dataSource));
    }

    /**
     * <p>
     * 初始化默认校验器，在实例化Ezasse，添加了Config后调用，在添加自定义校验器之前调用！！！
     * </p>
     *
     * @author PerccyKing
     * @since 2022/04/09 下午 04:40
     */
    public void initChecker() {
        //添加校验器
        addChecker(new DefaultKeyWordEzasseChecker());
        addChecker(new TableEzasseChecker());
        addChecker(new ChangeAddEzasseChecker());
        addChecker(new ChangeNameEzasseChecker());
        addChecker(new ChangeTypeEzasseChecker());
        addChecker(new ChangeLengthEzasseChecker());
        addChecker(new ChangeCommentEzasseChecker());
    }

    /**
     * <p>
     * 初始化执行器
     * </p>
     *
     * @author PerccyKing
     * @since 2022/04/10 上午 11:48
     */
    private void initExecutor() {
        //添加执行器
        addExecutor(new MysqlEzasseExecutor());
        addExecutor(new OracleEzasseExecutor());
        addExecutor(new H2EzasseExecutor());
        addExecutor(new MariaDbEzasseExecutor());
        addExecutor(new HsqlDbExecutor());
    }
}
