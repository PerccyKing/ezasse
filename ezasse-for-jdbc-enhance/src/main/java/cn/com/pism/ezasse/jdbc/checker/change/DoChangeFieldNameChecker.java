package cn.com.pism.ezasse.jdbc.checker.change;

import cn.com.pism.ezasse.checker.EzasseCheckLineContent;
import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.jdbc.action.param.ChangeFieldNameActionParam;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseExecutor;
import cn.com.pism.ezasse.model.EzasseTableInfo;

import java.util.List;

import static cn.com.pism.ezasse.jdbc.constants.EzasseJdbcEnhanceConstants.CHANGE_FIELD_NAME;

/**
 * @author PerccyKing
 * @since 25-03-23 15:05
 */
public class DoChangeFieldNameChecker extends ChangeFieldNameChecker {

    private String tableName;

    private String field;

    private String newFieldName;

    @Override
    public boolean check(EzasseDataSource dataSource, EzasseCheckLineContent checkLineContent) {
        if (super.check(dataSource, checkLineContent)) {
            //获取执行器
            EzasseDataSource executeDataSource = EzasseContextHolder.getContext().datasourceManager().getDataSource(checkLineContent.getCheckLine().getExecuteNode());
            EzasseExecutor ezasseExecutor = getEzasseExecutor(executeDataSource);
            ezasseExecutor.execute(CHANGE_FIELD_NAME, ChangeFieldNameActionParam.builder()
                    .tableName(this.tableName)
                    .fieldName(this.field)
                    .newFieldName(this.newFieldName)
                    .build(), executeDataSource);
        }
        // 默认返回false
        return false;
    }

    @Override
    protected boolean doChangeFieldCheck(List<EzasseTableInfo> tableInfos, String tableName, String field) {
        this.tableName = tableName;
        this.field = field;
        this.newFieldName = field;
        return super.doChangeFieldCheck(tableInfos, tableName, field);
    }

    @Override
    public String getId() {
        return "DO_CHANGE_NAME";
    }

    @Override
    public boolean allowEmpty() {
        return true;
    }
}
