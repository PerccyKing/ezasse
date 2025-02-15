package cn.com.pism.ezasse.model;

/**
 * 执行器动作
 *
 * <p>
 * 由{@link EzasseExecutor} 执行的动作
 * </p>
 *
 * @author PerccyKing
 * @since 24-12-14 14:41
 */
public interface EzasseExecutorAction<P extends ActionParam, R> {

    /**
     * <p>
     * 执行动作
     * </p>
     * by perccyking
     *
     * @param actionParam : 动作参数
     * @param dataSource  : 数据源
     * @return {@link R} 动作返回值
     * @since 25-01-01 11:19
     */
    R doAction(P actionParam, EzasseDataSource dataSource);

    /**
     * <p>
     * 获取动作id
     * </p>
     * by perccyking
     *
     * @return {@link String} 动作id
     * @since 25-01-01 11:19
     */
    String getId();

}
