package cn.com.pism.ezasse.resource;

import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.model.EzasseConfig;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.com.pism.ezasse.constants.EzasseConstants.CHECK_LINE_PATTERN;

/**
 * @author PerccyKing
 * @since 24-11-14 22:59
 */
@Getter
public class EzasseFileLine {

    private final String line;

    /**
     * 空行
     */
    private final boolean emptyLine;

    /**
     * 校验行
     */
    private boolean checkLine;

    /**
     * 限定符开始行
     */
    private boolean delimiterStart;

    /**
     * 限定符结束行
     */
    private boolean delimiterEnd;

    /**
     * 校验关键字
     */
    private String checkKey;

    /**
     * 校验内容
     */
    private String checkContent;

    public EzasseFileLine(String line) {
        this.line = line;

        this.emptyLine = StringUtils.isBlank(line);

        List<String> checkerKeys = EzasseContextHolder.getContext().getCheckerKeys();

        if (!this.emptyLine) {
            EzasseConfig config = EzasseContextHolder.getContext().getConfig();

            //如果开起了限定符，则判断是否是限定符开始或结束行
            if (config.isDelimiter()) {
                this.delimiterStart = line.startsWith(config.getDelimiterStart());
                this.delimiterEnd = line.endsWith(config.getDelimiterEnd());
            }
            if (!CollectionUtils.isEmpty(checkerKeys)) {
                Matcher matcher = Pattern.compile(String.format(CHECK_LINE_PATTERN, config.getLineComment(), String.join("|", checkerKeys))).matcher(line);
                if (matcher.matches()) {
                    this.checkLine = true;
                    this.checkKey = matcher.group(1);
                    this.checkContent = matcher.group(2);
                }
            }
        }


    }

}
