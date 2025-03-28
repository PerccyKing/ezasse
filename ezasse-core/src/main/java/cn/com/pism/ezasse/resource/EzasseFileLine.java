package cn.com.pism.ezasse.resource;

import cn.com.pism.ezasse.context.EzasseContext;
import cn.com.pism.ezasse.model.EzasseConfig;
import cn.com.pism.ezasse.util.CollUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.com.pism.ezasse.constants.EzasseConstants.CHECK_LINE_PATTERN;
import static cn.com.pism.ezasse.constants.EzasseConstants.MASTER;

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

    /**
     * 校验节点
     */
    @Setter
    private String checkNode;

    /**
     * 执行节点
     */
    @Setter
    private String executeNode;

    public EzasseFileLine(EzasseContext ezasseContext, String line) {
        this.line = line;

        this.emptyLine = StringUtils.isBlank(line);

        List<String> checkerKeys = ezasseContext.checkerManager().getCheckerKeys();

        if (!this.emptyLine) {
            EzasseConfig config = ezasseContext.configManager().getConfig();

            //如果开起了限定符，则判断是否是限定符开始或结束行
            if (config.isDelimiter()) {
                this.delimiterStart = line.startsWith(config.getDelimiterStart());
                this.delimiterEnd = line.endsWith(config.getDelimiterEnd());
            }
            if (!CollUtils.isEmpty(checkerKeys)) {
                checkLineParse(line, config, checkerKeys);
            }
        }


    }

    private void checkLineParse(String line, EzasseConfig config, List<String> checkerKeys) {
        Matcher matcher = Pattern.compile(String.format(CHECK_LINE_PATTERN, String.join("|", config.getLineComment()), String.join("|", checkerKeys))).matcher(line);
        boolean matches = matcher.matches();
        if (matches) {
            this.checkLine = true;
            this.checkKey = matcher.group(2);
            String group = matcher.group(3);
            if (StringUtils.isNotBlank(group)) {
                String[] nodes = group.split("\\.");
                if (nodes.length > 1) {
                    this.checkNode = nodes[2];
                }
                if (nodes.length > 2) {
                    this.executeNode = nodes[3];
                }

            }
            this.checkContent = matcher.group(4);
        }
        if (StringUtils.isBlank(this.checkNode)) {
            this.checkNode = MASTER;
        }
        if (StringUtils.isBlank(this.executeNode)) {
            this.executeNode = MASTER;
        }
    }

}
