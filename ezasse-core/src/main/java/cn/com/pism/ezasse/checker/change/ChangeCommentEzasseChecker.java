package cn.com.pism.ezasse.checker.change;

import cn.com.pism.ezasse.model.EzasseConfig;
import cn.com.pism.ezasse.model.EzasseTableInfo;
import org.apache.commons.collections4.IterableUtils;

import java.util.List;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/10 上午 11:22
 */
public class ChangeCommentEzasseChecker extends ChangeEzasseChecker {

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
    @Override
    public boolean aloneCheck(List<EzasseTableInfo> tableInfo, String[] split) {
        EzasseTableInfo info = IterableUtils.find(tableInfo, t -> split[2].equalsIgnoreCase(t.getColumnComment()));
        return info == null;
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
        return config.getKeyWords().getField().getChangeComment();
    }
}
