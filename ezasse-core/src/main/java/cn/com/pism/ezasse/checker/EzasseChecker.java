package cn.com.pism.ezasse.checker;

import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseExecutor;
import cn.com.pism.ezasse.util.EzasseLogUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author PerccyKing
 * @since 24-10-22 23:06
 */
public abstract class EzasseChecker {

    private static final Log log = LogFactory.getLog(EzasseChecker.class);

    /**
     * <p>
     * 对校验内容进行校验
     * </p>
     * by perccyking
     *
     * @param dataSource   : 数据源
     * @param checkContent : 校验内容
     * @return true:校验通过 false:校验不通过
     * @since 25-01-19 01:42
     */
    public boolean check(EzasseDataSource dataSource, String checkContent) {
        return false;
    }

    /**
     * <p>
     * 对校验内容进行校验
     * </p>
     * by perccyking
     *
     * @param dataSource       : 数据源
     * @param checkLineContent : 校验行对象
     * @return true:校验通过 false:校验不通过
     * @since 25-01-19 01:42
     */
    public boolean check(EzasseDataSource dataSource, EzasseCheckLineContent checkLineContent) {
        EzasseLogUtil.trace(log, String.format("checker:%s,check on the datasource:%s,content:%s",
                getId(), dataSource.getId(), checkLineContent.getCheckLine().getLine()));
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
    public boolean allowEmpty() {
        return false;
    }
}
