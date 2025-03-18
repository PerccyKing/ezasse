package cn.com.pism.ezasse.checker;

import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseExecutor;
import lombok.Setter;

/**
 * @author PerccyKing
 * @since 24-10-22 23:06
 */
@Setter
public abstract class EzasseChecker {

    /**
     * <p>
     * 对校验内容进行校验
     * </p>
     * by perccyking
     *
     * @param dataSource   : 数据源
     * @param checkContent : 交易内容
     * @return {@link boolean} true:校验通过 false:校验不通过
     * @since 25-01-19 01:42
     */
    public boolean check(EzasseDataSource dataSource, String checkContent) {
        return false;
    }

    public boolean check(EzasseDataSource dataSource, EzasseCheckLineContent checkLineContent) {
        return check(dataSource, checkLineContent.getCheckLine().getCheckContent());
    }

    /**
     * <p>
     * 获取校验器的id
     * </p>
     * by perccyking
     *
     * @return {@link String} 校验器id
     * @since 25-01-19 01:43
     */
    public abstract String getId();

    protected EzasseExecutor getEzasseExecutor(EzasseDataSource dataSource) {
        return EzasseContextHolder.getContext().executorManager().getExecutor(dataSource.getType());
    }

    /**
     * 是否允许空内容
     *
     * @return true:允许，false:不允许
     */
    public boolean allEmpty() {
        return false;
    }
}
