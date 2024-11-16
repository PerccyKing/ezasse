package cn.com.pism.ezasse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String checkLine;

    /**
     * 校验关键字
     */
    private String checkKey;

    /**
     * 校验内容
     */
    private String checkContent;

    /**
     * 对应内容
     */
    private StringBuilder content = new StringBuilder();

    public void appendContent(String content) {
        this.content.append(content).append("\n");
    }
}
