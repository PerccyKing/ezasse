package cn.com.pism.ezasse;

import cn.com.pism.ezasse.model.EzasseConfig;
import cn.com.pism.ezasse.model.EzasseSql;
import cn.com.pism.ezasse.util.EzasseIoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import static cn.com.pism.ezasse.constants.EzasseConstants.LINE_COMMENT;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/07 下午 04:55
 */
@Slf4j
public class EzasseGroupParser {
    private final LinkedHashMap<String, String> scriptMap = new LinkedHashMap<>();
    private final EzasseConfig config;
    private final EzasseSql sql;

    private final Set<String> checkKeyWord;

    public EzasseGroupParser(EzasseConfig config, EzasseSql sql, Set<String> checkKeyWord) {
        this.config = config;
        this.sql = sql;
        this.checkKeyWord = checkKeyWord;
    }

    LinkedHashMap<String, String> parser() {
        log.debug("Ezasse - group parser path : {}", sql.getPath());
        //获取SQL文件
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(sql.getPath());
        List<String> lines = EzasseIoUtil.readLines(inputStream);
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
        return scriptMap;
    }


    /**
     * <p>
     * 校验字符串是否为校验行
     * </p>
     *
     * @param line : 处理行信息
     * @return {@link boolean} true:是校验行，false：不是校验行
     * @author PerccyKing
     * @since 2022/04/06 下午 01:53
     */
    private boolean isCheckLine(String line) {
        if (line.startsWith(LINE_COMMENT)) {
            //以-- 开头，并且包含各个校验关键字
            String checkLine = line.substring(LINE_COMMENT.length() + 1);
            return StringUtils.startsWithAny(checkLine, checkKeyWord.toArray(new String[0]));
        }
        return false;
    }
}