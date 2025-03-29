package cn.com.pism.ezasse.jdbc.checker.change;

import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseTableInfo;
import cn.com.pism.ezasse.util.IterableUtils;

import java.util.List;

import static cn.com.pism.ezasse.constants.EzasseConstants.NUM2;
import static cn.com.pism.ezasse.constants.EzasseConstants.REGX_POINT;

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
    protected boolean doChangeFieldCheck(EzasseTableInfo tableInfo, String tableName, String field, String targetValue) {
        return !targetValue.equals(field);
    }

    @Override
    protected boolean checkTableFieldExists(EzasseDataSource dataSource, String checkContent) {
        String[] split = checkContent.split(REGX_POINT);
        return split.length > NUM2;
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
