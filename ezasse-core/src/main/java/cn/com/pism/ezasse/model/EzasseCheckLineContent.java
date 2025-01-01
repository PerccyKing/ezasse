package cn.com.pism.ezasse.model;

import cn.com.pism.ezasse.resource.EzasseFileLine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author PerccyKing
 * @since 24-11-06 23:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EzasseCheckLineContent {

    /**
     * 校验行
     */
    private EzasseFileLine checkLine;

    /**
     * 对应内容
     */
    private List<EzasseFileLine> content = new ArrayList<>();

    public EzasseCheckLineContent(EzasseFileLine checkLine) {
        this.checkLine = checkLine;
    }

    public void appendContent(EzasseFileLine content) {
        this.content.add(content);
    }

    public String getExecuteScript() {
        List<EzasseFileLine> executeContent = getExecuteContent();
        if (CollectionUtils.isEmpty(executeContent)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (EzasseFileLine ezasseFileLine : executeContent) {
            sb.append(ezasseFileLine.getLine()).append("\n");
        }
        return sb.toString();
    }

    public List<EzasseFileLine> getExecuteContent() {
        if (CollectionUtils.isEmpty(content)) {
            return Collections.emptyList();
        }

        List<Integer> delimiterStartIndex = new ArrayList<>();
        List<Integer> delimiterEndIndex = new ArrayList<>();
        for (int i = 0; i < content.size(); i++) {
            EzasseFileLine ezasseFileLine = content.get(i);
            if (ezasseFileLine.isDelimiterStart()) {
                delimiterStartIndex.add(i);
            }

            if (ezasseFileLine.isDelimiterEnd()) {
                delimiterEndIndex.add(i);
            }
        }


        boolean ignoreDelimiter
                // 如果没有开始或结束标记，不做处理
                = (CollectionUtils.isEmpty(delimiterStartIndex) || CollectionUtils.isEmpty(delimiterEndIndex))
                // 如果开始或结束标记存在多个，不做处理
                || (delimiterStartIndex.size() > 1 || delimiterEndIndex.size() > 1)
                //如果开始标记位置没有小于结束标记的位置，不做处理
                || (delimiterStartIndex.get(0) >= delimiterEndIndex.get(0));

        if (ignoreDelimiter) {
            return content;
        }

        // 仅保留 delimiterStartIndex 和 delimiterEndIndex 之间的内容

        return content.subList(delimiterStartIndex.get(0) + 1, delimiterEndIndex.get(0));
    }
}
