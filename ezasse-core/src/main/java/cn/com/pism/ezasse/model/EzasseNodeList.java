package cn.com.pism.ezasse.model;

import lombok.Data;

import java.util.List;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/05 下午 12:37
 */
public class EzasseNodeList<T> {
    /**
     * 当前节点
     */
    private String curr;

    /**
     * 子节点数据
     */
    private List<T> child;

    public String getCurr() {
        return curr;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }

    public List<T> getChild() {
        return child;
    }

    public void setChild(List<T> child) {
        this.child = child;
    }
}
