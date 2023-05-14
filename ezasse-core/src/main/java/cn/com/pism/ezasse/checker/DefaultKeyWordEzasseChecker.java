package cn.com.pism.ezasse.checker;

import cn.com.pism.ezasse.exception.EzasseException;
import cn.com.pism.ezasse.executor.EzasseExecutor;
import cn.com.pism.ezasse.model.EzasseConfig;

import javax.sql.DataSource;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/06 下午 02:56
 */
public class DefaultKeyWordEzasseChecker extends EzasseChecker {


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
    @Override
    public boolean needToExecute(DataSource checkDataSource, String checkContent, EzasseExecutor executor) {
        return publicCheck(checkContent, executor, () -> {
            Integer res = executor.queryForObject(checkContent, Integer.class);
            if (res == null) {
                throw new EzasseException();
            }
            return res == 0;
        });
    }

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
    @Override
    public String getId(EzasseConfig config) {
        return config.getKeyWords().getExec();
    }

}
