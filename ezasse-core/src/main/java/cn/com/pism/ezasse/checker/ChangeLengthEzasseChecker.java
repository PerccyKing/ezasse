package cn.com.pism.ezasse.checker;

import cn.com.pism.ezasse.database.EzasseExecutor;
import cn.com.pism.ezasse.model.EzasseConfig;
import cn.com.pism.ezasse.model.EzasseTableInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;

import javax.sql.DataSource;
import java.util.List;

import static cn.com.pism.ezasse.EzasseConstants.REGX_POINT;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/10 上午 11:16
 * @since 0.0.1
 */
public class ChangeLengthEzasseChecker extends EzasseChecker {
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
     * @date 2022/04/05 下午 12:23
     */
    @Override
    public boolean needToExecute(DataSource checkDataSource, String checkContent, EzasseExecutor executor) {
        return publicCheck(checkContent, executor, () -> {
            String[] split = checkContent.split(REGX_POINT);
            List<EzasseTableInfo> tableInfo = executor.getTableInfo(split[0], split[1]);
            if (CollectionUtils.isEmpty(tableInfo)) {
                return false;
            }
            EzasseTableInfo info = IterableUtils.find(tableInfo, t -> t.getCharacterMaximumLength().equals(split[3]));
            return info == null;
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
     * @date 2022/04/06 下午 02:45
     */
    @Override
    public String getId(EzasseConfig config) {
        return config.getChange() + "_" + config.getChangeLength();
    }
}
