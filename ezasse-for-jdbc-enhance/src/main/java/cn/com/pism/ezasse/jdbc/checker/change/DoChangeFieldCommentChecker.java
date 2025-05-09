package cn.com.pism.ezasse.jdbc.checker.change;

import cn.com.pism.ezasse.checker.EzasseCheckLineContent;
import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.jdbc.action.param.ChangeFieldActionParam;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseExecutor;
import cn.com.pism.ezasse.util.EzasseUtil;

import static cn.com.pism.ezasse.constants.EzasseConstants.REGX_POINT;
import static cn.com.pism.ezasse.jdbc.constants.EzasseJdbcEnhanceConstants.CHANGE_FIELD;

/**
 * @author PerccyKing
 * @since 25-02-02 22:51
 */
public class DoChangeFieldCommentChecker extends ChangeFieldCommentChecker {

    @Override
    public boolean check(EzasseDataSource dataSource, EzasseCheckLineContent checkLineContent) {

        if (super.check(dataSource, checkLineContent.getCheckLine().getCheckContent())) {
            String checkContent = checkLineContent.getCheckLine().getCheckContent();
            String[] split = checkContent.split(REGX_POINT);
            // 执行通过，获取执行器
            EzasseDataSource executeDataSource = EzasseContextHolder.getContext().datasourceManager().getDataSource(checkLineContent.getCheckLine().getExecuteNode());
            EzasseExecutor ezasseExecutor = getEzasseExecutor(executeDataSource);
            ezasseExecutor.execute(CHANGE_FIELD, ChangeFieldActionParam.builder()
                    .tableName(split[0])
                    .fieldName(split[1])
                    .comment(EzasseUtil.removeBeforeNthOccurrence(checkContent, '.', 2))
                    .build(), executeDataSource);
        }

        //默认返回false
        return false;
    }

    @Override
    public String getId() {
        return "DO_CHANGE_COMMENT";
    }

    @Override
    public boolean allowEmpty() {
        return true;
    }
}
