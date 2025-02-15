package cn.com.pism.ezasse.jdbc.checker;


import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.jdbc.action.param.DefaultCheckActionParam;
import cn.com.pism.ezasse.model.EzasseChecker;
import cn.com.pism.ezasse.model.EzasseDataSource;

import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.DEFAULT_CHECK;

/**
 * exec
 *
 * @author PerccyKing
 * @since 24-12-14 01:00
 */
public class ExecChecker extends EzasseChecker {

    @Override
    public boolean check(EzasseDataSource dataSource, String checkContent) {

        // 通过数据源获取到执行器，具体实现的JDBC实现执行器
        DefaultCheckActionParam defaultCheckActionParam = new DefaultCheckActionParam();
        defaultCheckActionParam.setCheckContent(checkContent);
        return Boolean.TRUE.equals(getEzasseExecutor(dataSource).execute(DEFAULT_CHECK, defaultCheckActionParam, dataSource));
    }

    @Override
    public String getId() {
        return EzasseContextHolder.getContext().configManger().getConfig().getKeyWords().getExec();
    }
}
