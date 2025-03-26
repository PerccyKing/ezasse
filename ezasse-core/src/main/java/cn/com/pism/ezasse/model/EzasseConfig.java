package cn.com.pism.ezasse.model;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 执行配置
 *
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/05 下午 12:08
 */
@Data
public class EzasseConfig {
    /**
     * 文件夹，会扫描当前文件夹下所有的sql文件,文件夹最好有三位顺序标记，没有标记将按照默认排序执行
     */
    private List<String> folders;

    /**
     * 文件列表,未指定，将校验全部的sql文件，可以是sql文件的完整名称，也可以是文件的分组名称
     */
    private List<String> fileList;

    /**
     * 分组顺序，如果存在多个分组，需要指定分组执行顺序
     */
    private List<String> groupOrder;

    /**
     * 开始限定符
     */
    private String delimiterStart;

    /**
     * 结束限定符
     */
    private String delimiterEnd;

    /**
     * 关键字
     */
    private EzasseKeyWords keyWords = new EzasseKeyWords();

    /**
     * 异步执行
     */
    private boolean async;

    /**
     * 行注释标记
     */
    private List<String> lineComment = Arrays.asList("--", "#");

    public List<String> getFolders() {
        if (CollectionUtils.isEmpty(folders)) {
            return Collections.singletonList("sql");
        }
        return folders;
    }

    public boolean isDelimiter() {
        return !StringUtils.isAnyBlank(delimiterStart, delimiterEnd);
    }
}
