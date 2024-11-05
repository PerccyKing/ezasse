package cn.com.pism.ezasse.model;

import lombok.Data;

/**
 * ezasse 文件信息
 * @author PerccyKing
 * @since 24-10-28 23:39
 */
@Data
public class EzasseFile {

    /**
     * 文件路径
     */
    private String path;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件分组
     */
    private String group;
    
    /**
     * 文件排序【000-999】
     */
    private String order = "000";

    /**
     * 校验和执行节点
     */
    private String node;

    public EzasseFile(String path) {
        this.path = path;
    }

}
