package cn.com.pism.ezasse.model;


import cn.com.pism.ezasse.action.DbCheckActionParam;
import cn.com.pism.ezasse.context.EzasseContextHolder;

import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.DEFAULT_CHECK;

/**
 * exec 只支持jdbc数据源
 *
 * @author PerccyKing
 * @since 24-12-14 01:00
 */
public class ExecChecker implements EzasseChecker {
    @Override
    public boolean check(EzasseDataSource dataSource, String checkContent) {
        // 这里使用的是jdbc数据源，需要通过数据源获取执行器
        EzasseExecutor executor = EzasseContextHolder.getContext().getExecutor(dataSource.getId());

        // 通过数据源获取到执行器，具体实现的JDBC实现执行器
        // 每个JDBC执行器都需要有默认的几个action
        DbCheckActionParam dbCheckActionParam = new DbCheckActionParam();
        dbCheckActionParam.setCheckContent(checkContent);
        return Boolean.TRUE.equals(executor.execute(DEFAULT_CHECK, dbCheckActionParam));
    }
}
