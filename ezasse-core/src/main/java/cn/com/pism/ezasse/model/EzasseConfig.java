package cn.com.pism.ezasse.model;

import lombok.Data;

import javax.sql.DataSource;
import java.util.List;

/**
 * 执行配置
 *
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/05 下午 12:08
 * @since 0.0.1
 */
@Data
public class EzasseConfig {
    /**
     * 文件夹，会扫描当前文件夹下所有的sql文件,文件夹最好有三位顺序标记，没有标记将按照默认排序执行
     */
    private String folder;

    /**
     * 文件列表,未指定，将校验全部的sql文件，可以是sql文件的完整名称，也可以是文件的分组名称
     */
    private List<String> fileList;

    /**
     * 分组顺序
     */
    private List<String> groupOrder;

    /**
     * 默认数据节点
     */
    private DataSource master;

    /**
     * 开始限定符
     */
    private String delimiterStart;

    /**
     * 结束限定符
     */
    private String delimiterEnd;

    /**
     * 默认关键字
     */
    private String defaultKeyWord = "EXEC";

    /**
     * 修改表信息
     */
    private String table = "TABLE";

    /**
     * 修改字段信息
     */
    private String change = "CHANGE";

    /**
     * 添加字段
     */
    private String changeAdd = "ADD";

    /**
     * 修改字段名称
     */
    private String changeName = "NAME";

    /**
     * 修改字段类型
     */
    private String changeType = "TYPE";

    /**
     * 修改字段长度
     */
    private String changeLength = "LENGTH";

    /**
     * 修改字段备注
     */
    private String changeComment = "COMMENT";
}
