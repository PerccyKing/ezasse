package cn.com.pism.ezasse.jdbc.action;

import cn.com.pism.ezasse.jdbc.action.param.DefaultCheckActionParam;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseExecutorAction;
import org.springframework.dao.DataAccessException;

import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.DEFAULT_CHECK;

/**
 * <p>
 * 默认检查
 * </p>
 * <p>对定义的检查脚本进行检查，如果结果返回为0 表示检查通过</p>
 *
 * @author PerccyKing
 * @since 24-12-29 17:05
 */
public class DefaultCheckAction implements EzasseExecutorAction<DefaultCheckActionParam, Boolean> {

    @Override
    @SuppressWarnings("all")
    public Boolean doAction(DefaultCheckActionParam actionParam, EzasseDataSource dataSource) {
        Integer res;
        try {
            res = JdbcTemplateCache.get(dataSource.getId()).queryForObject(actionParam.getCheckContent(), Integer.class);
        } catch (DataAccessException e) {
            return Boolean.FALSE;
        }
        if (res == null) {
            return false;
        }
        return res <= 0;
    }

    @Override
    public String getId() {
        return DEFAULT_CHECK;
    }
}
