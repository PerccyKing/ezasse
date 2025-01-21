package cn.com.pism.ezasse.checker;

import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.executor.EzasseExecutor;
import cn.com.pism.ezasse.model.EzasseDataSource;

/**
 * @author PerccyKing
 * @since 24-10-22 23:06
 */
public interface EzasseChecker {

    /**
     * <p>
     * 对校验内容进行校验
     * </p>
     * by perccyking
     *
     * @param dataSource : 数据源
     * @param checkContent : 交易内容
     * @return {@link boolean} true:校验通过 false:校验不通过
     * @since 25-01-19 01:42
     */
    boolean check(EzasseDataSource dataSource, String checkContent);

    /**
     * <p>
     * 获取校验器的id
     * </p>
     * by perccyking
     *
     * @return {@link String} 校验器id
     * @since 25-01-19 01:43
     */
    String getId();

    default EzasseExecutor getEzasseExecutor(String dataSourceId) {
        return EzasseContextHolder.getContext().executorManager().getExecutor(dataSourceId);
    }
}
