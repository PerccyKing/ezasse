package cn.com.pism.ezasse.checker.change;

import cn.com.pism.ezasse.model.EzasseConfig;
import cn.com.pism.ezasse.model.EzasseTableInfo;
import org.apache.commons.collections4.IterableUtils;

import java.util.List;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/09 下午 06:12
 * @since 0.0.1
 */
public class ChangeTypeEzasseChecker extends ChangeEzasseChecker {
    /**
     * <p>
     * 由子类实现，单独判断某个字段
     * </p>
     *
     * @param tableInfo : 表信息
     * @param split     : 解析到的关键字
     * @return {@link boolean}
     * @author PerccyKing
     * @date 2022/04/10 上午 11:33
     */
    @Override
    public boolean aloneCheck(List<EzasseTableInfo> tableInfo, String[] split) {
        //第三位为字段的目标类型
        EzasseTableInfo ezasseTableInfo = IterableUtils.find(tableInfo, info -> split[3].equals(info.getDataType()));
        return ezasseTableInfo == null;
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
        return config.getChange() + "_" + config.getChangeType();
    }
}
