package cn.com.pism.ezasse.model;

import lombok.Data;

import java.util.List;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/05 下午 12:37
 * @since 0.0.1
 */
@Data
public class EzasseNodeList<T> {
    /**
     * 当前节点数据
     */
    private T curr;

    /**
     * 子节点数据
     */
    private List<T> child;
}
