package cn.com.pism.ezasse.model;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/05 下午 12:35
 */
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

    /**
     * 父节点路径
     */
    private String parentPath;

    /**
     * 路径
     */
    private String path;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
