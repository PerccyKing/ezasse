package cn.com.pism.ezasse.calibrator;

import cn.com.pism.ezasse.database.EzasseExecutor;
import cn.com.pism.ezasse.exception.EzasseException;
import cn.com.pism.ezasse.model.EzasseConfig;
import cn.com.pism.ezasse.model.EzasseDataSource;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/06 下午 02:56
 * @since 0.0.1
 */
public class DefaultKeyWordEzasseCalibrator extends EzasseCalibrator {


    /**
     * <p>
     * 判断代码块是否需要执行
     * </p>
     *
     * @param checkDataSource :数据校验节点
     * @param checkContent    :数据校验内容
     * @return {@link boolean} true:执行代码块,false:跳过代码块
     * @author PerccyKing
     * @date 2022/04/05 下午 12:23
     */
    @Override
    public boolean needToExecute(EzasseDataSource checkDataSource, String checkContent) {
        EzasseExecutor actuator = null;
        Integer res = actuator.queryForObject(checkContent, Integer.class);
        if (res == null) {
            throw new EzasseException();
        }
        return res == 0;
    }

    /**
     * <p>
     * 语法定义id
     * </p>
     *
     * @param config : 配置
     * @return {@link String}  全局唯一id，与关键字对其
     * @author PerccyKing
     * @date 2022/04/06 下午 02:45
     */
    @Override
    public String getId(EzasseConfig config) {
        return config.getDefaultKeyWord();
    }

}
