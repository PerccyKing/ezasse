package cn.com.pism.ezasse.model;

/**
 * 执行器
 *
 * @author PerccyKing
 * @since 24-12-14 12:33
 */
public interface EzasseExecutor {

    /**
     * 执行action
     *
     * @param actionId   actionId
     * @param param      action参数
     * @param dataSource 数据源对象
     * @param <R>        响应
     * @param <P>        参数
     * @return action执行结果
     */
    <R, P extends ActionParam> R execute(String actionId, P param, EzasseDataSource dataSource);

    /**
     * 获取action
     * @param actionId actionId
     * @return 执行器action实例
     */
    @SuppressWarnings("all")
    EzasseExecutorAction<? extends ActionParam, ?> getAction(String actionId);

    /**
     * <p>
     * 获取执行器数据源类型
     * </p>
     * by perccyking
     *
     * @return {@link EzasseDataSourceType} 执行器数据源类型
     * @since 25-02-08 12:18
     */
    String getDataSourceType();

}
