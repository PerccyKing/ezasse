package cn.com.pism.ezasse.jdbc.checker.change;

import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.model.EzasseTableInfo;

/**
 * 字段数据类型校验器
 *
 * @author PerccyKing
 * @since 25-01-25 18:33
 */
public class ChangeFieldTypeChecker extends ChangeFieldChecker {

    @Override
    protected boolean doChangeFieldCheck(EzasseTableInfo tableInfo, String tableName, String field, String targetValue) {
        String finalTargetValue = targetValue;
        if (finalTargetValue.contains("(")) {
            finalTargetValue = finalTargetValue.substring(0, finalTargetValue.indexOf("("));
        }
        return !tableInfo.getDataType().equals(finalTargetValue);
    }

    @Override
    public String getId() {
        return EzasseContextHolder.getContext().configManager().getConfig().getKeyWords().getField().getChangeType();
    }
}
