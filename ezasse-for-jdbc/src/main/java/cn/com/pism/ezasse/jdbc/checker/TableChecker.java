package cn.com.pism.ezasse.jdbc.checker;

import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.jdbc.action.param.GetTableInfoActionParam;
import cn.com.pism.ezasse.jdbc.action.param.TableIsExistActionParam;
import cn.com.pism.ezasse.model.EzasseChecker;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseExecutor;
import cn.com.pism.ezasse.model.EzasseTableInfo;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.GET_TABLE_INFO;
import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.TABLE_EXISTS;

/**
 * <p>-- TABLE</p>
 * 检查表是否存在
 *
 * @author PerccyKing
 * @since 25-01-13 22:45
 */
public class TableChecker extends EzasseChecker {

    @Override
    public boolean check(EzasseDataSource dataSource, String checkContent) {
        //执行器
        EzasseExecutor ezasseExecutor = getEzasseExecutor(dataSource);
        if (EzasseContextHolder.getContext().executorManager().getExecutorAction(ezasseExecutor.getDataSourceType(), TABLE_EXISTS) != null) {
            Boolean executeRes = ezasseExecutor.execute(TABLE_EXISTS, TableIsExistActionParam.builder().tableName(checkContent).build(), dataSource);
            return Boolean.TRUE.equals(executeRes);
        } else {
            List<EzasseTableInfo> tableInfos = ezasseExecutor.execute(GET_TABLE_INFO, GetTableInfoActionParam.builder().tableName(checkContent).build(), dataSource);
            // 表如果存在返回false
            return CollectionUtils.isEmpty(tableInfos);
        }

    }

    @Override
    public String getId() {
        return EzasseContextHolder.getContext().configManger().getConfig().getKeyWords().getTable().getCreateTable();
    }
}
