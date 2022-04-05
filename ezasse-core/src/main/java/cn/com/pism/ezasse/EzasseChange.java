package cn.com.pism.ezasse;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/05 下午 12:22
 * @since 0.0.1
 */
public interface EzasseChange {
    /**
     * <p>
     * 判断代码块是否需要执行
     * </p>
     *
     * @param tableName : 表名（不会为空）
     * @param fieldName : 字段名 （可能为空）
     * @param extend    : 扩展字段（可能为空）
     * @return {@link boolean} true:执行代码块,false:跳过代码块
     * @author PerccyKing
     * @date 2022/04/05 下午 12:23
     */
    boolean needToExecute(String tableName, String fieldName, Object extend);
}
