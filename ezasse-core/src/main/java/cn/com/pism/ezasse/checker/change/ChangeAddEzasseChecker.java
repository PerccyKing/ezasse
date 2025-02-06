package cn.com.pism.ezasse.checker.change;

import cn.com.pism.ezasse.checker.EzasseChecker;
import cn.com.pism.ezasse.executor.v0.EzasseExecutor;
import cn.com.pism.ezasse.model.EzasseConfig;
import cn.com.pism.ezasse.model.EzasseTableInfo;
import org.apache.commons.collections4.CollectionUtils;

import javax.sql.DataSource;
import java.util.List;

import static cn.com.pism.ezasse.constants.EzasseConstants.REGX_POINT;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/09 下午 05:33
 */
public class ChangeAddEzasseChecker extends EzasseChecker {
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
            //第一位为表名，第二位为字段名
            List<EzasseTableInfo> tableInfo = executor.getTableInfo(split[0], split[1]);
            //如果集合为空，可以执行脚本
            return CollectionUtils.isEmpty(tableInfo);
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
        return config.getKeyWords().getField().getAdd();
    }
}
