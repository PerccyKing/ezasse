package cn.com.pism.ezasse.jdbc.checker.change;

import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.model.EzasseTableInfo;

/**
 * 修改列的comment
 *
 * @author PerccyKing
 * @since 25-01-21 20:10
 */
public class ChangeFieldCommentChecker extends ChangeFieldChecker {

    @Override
    protected boolean doChangeFieldCheck(EzasseTableInfo tableInfo, String tableName, String field, String targetValue) {
        // 目标值与现有值不一致则校验通过
        return !targetValue.equals(tableInfo.getColumnComment());
    }

    @Override
    public String getId() {
        return EzasseContextHolder.getContext().configManger().getConfig().getKeyWords().getField().getChangeComment();
    }

}
