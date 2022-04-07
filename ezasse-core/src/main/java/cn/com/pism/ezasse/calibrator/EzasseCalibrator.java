package cn.com.pism.ezasse.calibrator;

import cn.com.pism.ezasse.model.EzasseConfig;
import cn.com.pism.ezasse.model.EzasseDataSource;

/**
 * 校验器
 *
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/05 下午 12:22
 * @since 0.0.1
 */
public abstract class EzasseCalibrator {
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
    public abstract boolean needToExecute(EzasseDataSource checkDataSource, String checkContent);


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
    public abstract String getId(EzasseConfig config);
}
