package cn.com.pism.ezasse.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * ezasse 文件信息
 *
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
    private String order;

    /**
     * 校验和执行节点
     */
    private String node;

    public EzasseFile(String path) {
        this.path = path;
    }

    public String getOrder() {
        if (StringUtils.isBlank(this.order)) {
            return "000";
        }
        return this.order;
    }

}
