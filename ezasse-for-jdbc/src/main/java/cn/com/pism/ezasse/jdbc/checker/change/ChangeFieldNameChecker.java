package cn.com.pism.ezasse.jdbc.checker.change;

import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.model.EzasseTableInfo;
import org.apache.commons.collections4.IterableUtils;

import java.util.List;

import static cn.com.pism.ezasse.constants.EzasseConstants.NUM2;

/**
 * 修改字段名/列名
 *
 * @author PerccyKing
 * @since 25-01-24 13:23
 */
public class ChangeFieldNameChecker extends ChangeFieldChecker {

    @Override
    protected boolean doChangeFieldCheck(List<EzasseTableInfo> tableInfos, String tableName, String field) {
        // 检查表信息中是否含有字段名
        return IterableUtils.find(tableInfos, info -> info.getColumnName().equals(field)) != null;
    }

    @Override
    protected boolean checkTableFieldExists() {
        //需要修改字段名称，不检查表字段是否存在
        return false;
    }

    @Override
    protected boolean isSyntaxValid(String[] split) {
        // CHANGE_NAME(table.targetFieldName)
        return split.length >= NUM2;
    }

    @Override
    public String getId() {
        return EzasseContextHolder.getContext().configManager().getConfig().getKeyWords().getField().getChangeName();
    }
}
