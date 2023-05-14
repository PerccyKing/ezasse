package cn.com.pism.ezasse.checker.change;

import cn.com.pism.ezasse.checker.EzasseChecker;
import cn.com.pism.ezasse.executor.EzasseExecutor;
import cn.com.pism.ezasse.model.EzasseTableInfo;
import org.apache.commons.collections4.CollectionUtils;

import javax.sql.DataSource;
import java.util.List;

import static cn.com.pism.ezasse.constants.EzasseConstants.REGX_POINT;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/10 上午 11:30
 */
public abstract class ChangeEzasseChecker extends EzasseChecker {
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
            String[] split = checkContent.split(REGX_POINT);
            List<EzasseTableInfo> tableInfo = executor.getTableInfo(split[0], split[1]);
            if (CollectionUtils.isEmpty(tableInfo)) {
                return false;
            }
            return aloneCheck(tableInfo, split);
        });
    }

    /**
     * <p>
     * 由子类实现，单独判断某个字段
     * </p>
     *
     * @param tableInfo : 表信息
     * @param split     : 解析到的关键字
     * @return {@link boolean}
     * @author PerccyKing
     * @since 2022/04/10 上午 11:33
     */
    public abstract boolean aloneCheck(List<EzasseTableInfo> tableInfo, String[] split);
}
