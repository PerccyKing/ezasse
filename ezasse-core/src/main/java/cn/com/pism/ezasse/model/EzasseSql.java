package cn.com.pism.ezasse.model;

import lombok.Data;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/05 下午 12:35
 * @since 0.0.1
 */
@Data
public class EzasseSql {
    /**
     * 分组-sql文件名称第一位
     */
    private String group;
    /**
     * 序号
     */
    private String order;

    /**
     * 默认执行数据节点
     */
    private String node;

    /**
     * 名称
     */
    private String name;
}
