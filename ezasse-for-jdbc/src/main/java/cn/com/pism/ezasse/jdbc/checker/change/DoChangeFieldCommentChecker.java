package cn.com.pism.ezasse.jdbc.checker.change;

import cn.com.pism.ezasse.action.param.ChangeFieldCommentActionParam;
import cn.com.pism.ezasse.executor.EzasseExecutor;
import cn.com.pism.ezasse.model.EzasseCheckLineContent;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseTableInfo;

import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.CHANGE_FIELD_COMMENT;

/**
 * @author PerccyKing
 * @since 25-02-02 22:51
 */
public class DoChangeFieldCommentChecker extends ChangeFieldCommentChecker {

    private String tableName;

    private String field;

    private String comment;

    @Override
    public boolean check(EzasseDataSource dataSource, EzasseCheckLineContent checkLineContent) {

        if (super.check(dataSource, checkLineContent.getCheckLine().getCheckContent())) {
            // 执行通过，获取执行器
            EzasseExecutor ezasseExecutor = getEzasseExecutor(checkLineContent.getCheckLine().getExecuteNode());
            ezasseExecutor.execute(CHANGE_FIELD_COMMENT, ChangeFieldCommentActionParam.builder()
                    .tableName(this.tableName)
                    .fieldName(this.field)
                    .comment(this.comment)
                    .build());
        }

        //默认返回false
        return false;
    }

    @Override
    protected boolean doChangeFieldCheck(EzasseTableInfo tableInfo, String tableName, String field, String targetValue) {
        this.tableName = tableName;
        this.field = field;
        this.comment = targetValue;
        return super.doChangeFieldCheck(tableInfo, tableName, field, targetValue);
    }

    @Override
    public String getId() {
        return "DO_CHANGE_COMMENT";
    }

    @Override
    public boolean allEmpty() {
        return true;
    }
}
