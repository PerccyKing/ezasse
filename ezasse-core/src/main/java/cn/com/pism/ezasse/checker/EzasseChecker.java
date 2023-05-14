package cn.com.pism.ezasse.checker;

import cn.com.pism.ezasse.executor.EzasseExecutor;
import cn.com.pism.ezasse.model.EzasseConfig;
import cn.com.pism.ezasse.util.NoneParamCallback;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;

/**
 * 校验器
 *
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/05 下午 12:22
 */
public abstract class EzasseChecker {

    protected boolean publicCheck(String checkContent, EzasseExecutor executor, NoneParamCallback<Boolean> callback) {
        if (StringUtils.isBlank(checkContent) || executor == null) {
            return false;
        } else {
            return callback.call();
        }
    }

    /**
     * <p>
     * 判断代码块是否需要执行
     * </p>
     *
     * @param checkDataSource :数据校验节点
     * @param checkContent    :数据校验内容
     * @param executor        :执行器
     * @return {@link boolean} true:执行代码块,false:跳过代码块
     * @author PerccyKing
     * @since 2022/04/05 下午 12:23
     */
    public abstract boolean needToExecute(DataSource checkDataSource, String checkContent, EzasseExecutor executor);


    /**
     * <p>
     * 语法定义id
     * </p>
     *
     * @param config : 配置
     * @return {@link String}  全局唯一id，与关键字对其
     * @author PerccyKing
     * @since 2022/04/06 下午 02:45
     */
    public abstract String getId(EzasseConfig config);
}
