package cn.com.pism.ezasse.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 集合扩展工具类
 *
 * @author PerccyKing
 * @since 2022/3/26 21:15
 */
public class CollectionUtil {

    private CollectionUtil() {
    }


    /**
     * <p>
     * 添加元素到list中，list为空时创建list
     * </p>
     *
     * @param list     : 集合
     * @param item     : 元素
     * @param consumer : 回调
     * @author PerccyKing
     * @since 2022/04/06 下午 02:51
     */
    public static <T> void addToList(List<T> list, T item, Consumer<List<T>> consumer) {
        boolean execConsumer = false;
        if (list == null) {
            list = new ArrayList<>();
            execConsumer = true;
        }
        list.add(item);
        if (execConsumer && consumer != null) {
            consumer.accept(list);
        }
    }

}
