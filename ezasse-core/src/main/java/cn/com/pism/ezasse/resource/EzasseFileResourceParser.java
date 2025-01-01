package cn.com.pism.ezasse.resource;

import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.model.EzasseCheckLineContent;
import cn.com.pism.ezasse.model.EzasseConfig;
import cn.com.pism.ezasse.model.EzasseFile;
import cn.com.pism.ezasse.util.EzasseIoUtil;
import cn.com.pism.ezasse.util.EzasseLogUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author PerccyKing
 * @since 24-11-02 23:47
 */
public class EzasseFileResourceParser extends EzasseResourceParser {

    private static final Log log = LogFactory.getLog(EzasseFileResourceParser.class);

    private final EzasseFileResource resource;

    public EzasseFileResourceParser(EzasseResource resource) {
        super(resource);
        this.resource = (EzasseFileResource) resource;
    }

    @Override
    public EzasseFileResourceData parse() {
        EzasseFileResourceData resourceData = new EzasseFileResourceData();

        List<EzasseFile> sqlFiles = resource.getFiles();

        //打印日志
        printLog(sqlFiles);

        //没有解析到文件，直接结束流程
        if (CollectionUtils.isEmpty(sqlFiles)) {
            return resourceData;
        }

        sqlFiles = filterSqlFiles(sqlFiles);

        EzasseFileResourceData groupParsedData = groupParsed(sqlFiles, resourceData);
        if (groupParsedData != null) {
            return groupParsedData;
        }

        //遍历文件
        sortedFile(sqlFiles).forEach(sqlFile -> parseSqlFile(sqlFile, resourceData));

        return resourceData;
    }

    private EzasseFileResourceData groupParsed(List<EzasseFile> sqlFiles, EzasseFileResourceData resourceData) {
        //配置
        EzasseConfig config = EzasseContextHolder.getContext().configManger().getConfig();
        List<String> groupOrder = config.getGroupOrder();

        //如果指定了分组，按分组顺序解析文件
        if (CollectionUtils.isEmpty(groupOrder)) {
            return null;
        }

        Map<String, List<EzasseFile>> groupFileMap = sqlFiles.stream().collect(Collectors.groupingBy(EzasseFile::getGroup));

        groupOrder.forEach(group -> {
            List<EzasseFile> groupFiles = groupFileMap.get(group);
            //文件排序
            sortedFile(groupFiles).forEach(sqlFile -> parseSqlFile(sqlFile, resourceData));
        });
        return resourceData;
    }

    private List<EzasseFile> filterSqlFiles(List<EzasseFile> sqlFiles) {
        //配置
        EzasseConfig config = EzasseContextHolder.getContext().configManger().getConfig();

        //过滤指定文件
        List<String> fileList = config.getFileList();
        if (!CollectionUtils.isEmpty(fileList)) {
            sqlFiles = sqlFiles.stream()
                    .filter(sqlFile -> fileList.stream().anyMatch(sqlFile.getName()::contains))
                    .collect(Collectors.toList());
        }

        //如果指定了分组
        if (!CollectionUtils.isEmpty(config.getGroupOrder())) {
            //过滤出指定分组的文件
            sqlFiles = sqlFiles.stream()
                    .filter(sqlFile -> config.getGroupOrder().contains(sqlFile.getGroup()))
                    .collect(Collectors.toList());
        }
        return sqlFiles;
    }

    private Stream<EzasseFile> sortedFile(List<EzasseFile> sqlFiles) {
        return sqlFiles.stream().sorted(Comparator.comparingInt(o -> Integer.parseInt(o.getOrder())));
    }

    private void parseSqlFile(EzasseFile sqlFile, EzasseFileResourceData resourceData) {
        //读取文件内容
        List<String> lines = readFile(sqlFile.getPath());

        resourceData.addFileData(parseFileLines(sqlFile, lines));
    }

    /**
     * <p>
     * 解析文件内容，先按照校验行进行拆分
     * </p>
     * by perccyking
     *
     * @param sqlFile : 文件对象
     * @param lines   : 文件内容所有行
     * @return {@link EzasseFileResourceData.ResourceData}
     * @since 24-11-17 00:09
     */
    private EzasseFileResourceData.ResourceData parseFileLines(EzasseFile sqlFile, List<String> lines) {
        // 空文件不做处理
        if (CollectionUtils.isEmpty(lines)) {
            return null;
        }

        // 创建数据对象，一个文件一个对象
        EzasseFileResourceData.ResourceData fileData = new EzasseFileResourceData.ResourceData();

        // 当前解析到的校验行
        EzasseFileLine currentCheckLine = null;
        EzasseCheckLineContent currentCheckLineContent = null;

        for (String line : lines) {
            EzasseFileLine ezasseFileLine = new EzasseFileLine(line);
            fileData.addFileLine(ezasseFileLine);

            // 如果当前行校验行
            if (ezasseFileLine.isCheckLine()) {
                // 设置当前行为当前解析过程中的校验行
                currentCheckLine = ezasseFileLine;

                // 添加节点信息
                populateNodeInfo(sqlFile, currentCheckLine);

                // 创建clc
                currentCheckLineContent = new EzasseCheckLineContent(currentCheckLine);

                // 将当前clc对象添加到文件数据中
                fileData.addCheckLineContent(currentCheckLineContent);
            } else {

                // 当前行不是校验行
                // 如果解析中的校验行为空，不做处理
                // 如果解析中的校验行不为空，将当前行加入到clc中
                if (currentCheckLine != null) {
                    currentCheckLineContent.appendContent(ezasseFileLine);
                }
            }

        }

        return fileData;
    }

    private void populateNodeInfo(EzasseFile sqlFile, EzasseFileLine currentCheckLine) {
        // 如果校验行没有指定校验节点和执行节点，并且文件指定了校验和执行节点，将文件节点赋值给校验行
        String fileNode = sqlFile.getNode();
        if (StringUtils.isNotBlank(fileNode)) {
            if (StringUtils.isBlank(currentCheckLine.getCheckNode())) {
                currentCheckLine.setCheckNode(fileNode);
            }
            if (StringUtils.isBlank(currentCheckLine.getExecuteNode())) {
                currentCheckLine.setExecuteNode(fileNode);
            }
        }
    }


    /**
     * <p>
     * 读取文件内容
     * </p>
     * by perccyking
     *
     * @param path : 文件的绝对路径
     * @return 文件内容列表
     * @since 24-11-17 00:39
     */
    public List<String> readFile(String path) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
        return EzasseIoUtil.readLines(inputStream);
    }

    private static final int MIN_WIDTH = 5;

    /**
     * <p>
     * 打印日志
     * </p>
     * by perccyking
     *
     * @param ezasseFiles : 文件对象
     * @since 24-11-17 00:10
     */
    public void printLog(List<EzasseFile> ezasseFiles) {
        if (!log.isInfoEnabled()) {
            return;
        }

        int[] width = calculateColumnWidths(ezasseFiles);
        String format = generateFormatString(width);

        EzasseLogUtil.info(log, "Ezasse - Identified file list");
        EzasseLogUtil.info(log, String.format(format, "group", "order", "node", "name", "path"));

        for (EzasseFile ezasseFile : ezasseFiles) {
            EzasseLogUtil.info(log, String.format(format,
                    ezasseFile.getGroup(),
                    ezasseFile.getOrder(),
                    ezasseFile.getNode(),
                    ezasseFile.getName(),
                    ezasseFile.getPath()));
        }
    }

    private int[] calculateColumnWidths(List<EzasseFile> ezasseFiles) {
        int[] width = new int[5];
        for (EzasseFile ezasseFile : ezasseFiles) {
            width[0] = Math.max(width[0], lengthOrDefault(ezasseFile.getGroup()));
            width[1] = Math.max(width[1], lengthOrDefault(ezasseFile.getOrder()));
            width[2] = Math.max(width[2], lengthOrDefault(ezasseFile.getNode()));
            width[3] = Math.max(width[3], lengthOrDefault(ezasseFile.getName()));
            width[4] = Math.max(width[4], lengthOrDefault(ezasseFile.getPath()));
        }
        return width;
    }

    private String generateFormatString(int[] width) {
        StringBuilder formatBuilder = new StringBuilder();
        for (int w : width) {
            if (w < MIN_WIDTH) {
                w = MIN_WIDTH;
            }
            formatBuilder.append("|\t%-").append(w + 3).append("s ");
        }
        return formatBuilder.toString().trim();
    }

    private static int lengthOrDefault(String str) {
        return str != null ? str.length() : 5;
    }
}
