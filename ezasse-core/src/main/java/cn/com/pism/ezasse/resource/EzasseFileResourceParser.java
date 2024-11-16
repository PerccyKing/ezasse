package cn.com.pism.ezasse.resource;

import cn.com.pism.ezasse.model.EzasseCheckLineContent;
import cn.com.pism.ezasse.model.EzasseFile;
import cn.com.pism.ezasse.util.EzasseIoUtil;
import cn.com.pism.ezasse.util.EzasseLogUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.List;

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

        //遍历文件
        sqlFiles.forEach(sqlFile -> parseSqlFile(sqlFile, resourceData));
        //读取数据
        return resourceData;
    }

    private void parseSqlFile(EzasseFile sqlFile, EzasseFileResourceData resourceData) {
        //读取文件内容
        List<String> lines = readFile(sqlFile.getPath());

        resourceData.addFileData(parseFileLines(lines));
    }

    /**
     * <p>
     * 解析文件内容
     * </p>
     * by perccyking
     *
     * @param lines : 文件内容所有行
     * @return {@link EzasseFileResourceData.ResourceData}
     * @since 24-11-17 00:09
     */
    private EzasseFileResourceData.ResourceData parseFileLines(List<String> lines) {

        //空文件不做处理
        if (CollectionUtils.isEmpty(lines)) {
            return null;
        }

        //创建数据对象，一个文件一个对象
        EzasseFileResourceData.ResourceData fileData = new EzasseFileResourceData.ResourceData();

        //遍历中 当前的校验行
        EzasseFileLine currentCheckLine = null;

        //校验行对应的内容
        EzasseCheckLineContent checkLineContent = null;

        //遍历文件内容
        for (String line : lines) {
            EzasseFileLine ezasseFileLine = new EzasseFileLine(line);
            fileData.addFileLine(ezasseFileLine);

            //当前行为校验行
            if (ezasseFileLine.isCheckLine()) {

                //当前循环中的校验行不为空，并且当前行为校验行，标示当前校验行对应的内容已经完成解析
                //需要进行下一个校验行对应内容的解析
                if (currentCheckLine != null) {
                    fileData.addCheckLineContent(checkLineContent);
                }

                //重置当前的校验行 和 校验行对应的内容
                currentCheckLine = ezasseFileLine;
                checkLineContent = createCheckLineContent(currentCheckLine);
            } else {

                //当前行不是校验行 并且当前校验行不为空，添加到checkLineContent
                if (currentCheckLine != null) {

                    //将当前行数据追加到 checkLineContent
                    checkLineContent.appendContent(line);
                }

            }

        }
        return fileData;
    }

    private EzasseCheckLineContent createCheckLineContent(EzasseFileLine currentCheckLine) {
        EzasseCheckLineContent checkLineContent;
        checkLineContent = new EzasseCheckLineContent();
        checkLineContent.setCheckKey(currentCheckLine.getCheckKey());
        checkLineContent.setCheckContent(currentCheckLine.getCheckContent());
        checkLineContent.setCheckLine(currentCheckLine.getLine());
        return checkLineContent;
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
