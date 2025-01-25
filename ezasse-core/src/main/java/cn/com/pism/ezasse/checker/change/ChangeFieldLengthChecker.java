package cn.com.pism.ezasse.checker.change;

import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.model.EzasseTableInfo;

/**
 * 修改列的length
 *
 * @author PerccyKing
 * @since 25-01-24 20:57
 */
public class ChangeFieldLengthChecker extends ChangeFieldChecker {

    @Override
    protected boolean doChangeFieldCheck(EzasseTableInfo tableInfo, String tableName, String field, String targetValue) {
        // 目标值与现有值不一致则校验通过
        return targetValue.equals(tableInfo.getCharacterMaximumLength());
    }


    @Override
    public String getId() {
        return EzasseContextHolder.getContext().configManger().getConfig().getKeyWords().getField().getChangeLength();
    }
}
