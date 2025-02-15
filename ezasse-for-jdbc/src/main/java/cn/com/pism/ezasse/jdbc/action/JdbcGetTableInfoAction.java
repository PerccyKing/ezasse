package cn.com.pism.ezasse.jdbc.action;

import cn.com.pism.ezasse.jdbc.action.param.GetTableInfoActionParam;
import cn.com.pism.ezasse.model.EzasseExecutorAction;
import cn.com.pism.ezasse.model.EzasseTableInfo;

import java.util.List;

import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.GET_TABLE_INFO;

/**
 * @author PerccyKing
 * @since 25-01-27 15:30
 */
public abstract class JdbcGetTableInfoAction implements EzasseExecutorAction<GetTableInfoActionParam, List<EzasseTableInfo>> {

    @Override
    public String getId() {
        return GET_TABLE_INFO;
    }

}
