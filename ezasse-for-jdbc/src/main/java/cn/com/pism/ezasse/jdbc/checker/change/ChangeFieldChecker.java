package cn.com.pism.ezasse.jdbc.checker.change;

import cn.com.pism.ezasse.jdbc.action.param.GetTableInfoActionParam;
import cn.com.pism.ezasse.model.EzasseChecker;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseTableInfo;
import cn.com.pism.ezasse.util.EzasseUtil;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static cn.com.pism.ezasse.constants.EzasseConstants.NUM3;
import static cn.com.pism.ezasse.constants.EzasseConstants.REGX_POINT;
import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.GET_TABLE_INFO;

/**
 * 修改字段属性的校验器，change相关的语法有相似性
 * {@code -- change_xx(table.field.[?])}
 * <p>校验内容至少有三段，少于三段拒绝执行</p>
 *
 * @author PerccyKing
 * @since 25-01-24 11:31
 */
public abstract class ChangeFieldChecker extends EzasseChecker {

    @Override
    public boolean check(EzasseDataSource dataSource, String checkContent) {

        // 对校验类型进行分割
        String[] split = checkContent.split(REGX_POINT);

        if (!isSyntaxValid(checkContent)) {
            return false;
        }

        if (!isSyntaxValid(split)) {
            return false;
        }

        // 获取表字段信息 0：表名 1：列名
        List<EzasseTableInfo> tableInfos = getEzasseExecutor(dataSource)
                .execute(GET_TABLE_INFO, GetTableInfoActionParam.builder()
                        .tableName(split[0])
                        .columnName(split[1])
                        .build(), dataSource);

        // 如果没有表信息 表里面没有需要修改的目标字段 返回false
        if (CollectionUtils.isEmpty(tableInfos)) {
            return false;
        }

        EzasseTableInfo tableInfo;
        if (checkTableFieldExists()) {
            // 如果 tableInfos 不为空，则表存在 查找目标字段信息
            tableInfo = IterableUtils.find(tableInfos, info -> info.getColumnName().equals(split[1]));
            if (tableInfo == null) {
                //没找到字段信息，不允许执行
                return false;
            }
            return doChangeFieldCheck(tableInfo, split[0], split[1], EzasseUtil.removeBeforeNthOccurrence(checkContent, '.', 2));
        } else {
            return doChangeFieldCheck(tableInfos, split[0], split[1]);
        }


    }

    protected boolean isSyntaxValid(String checkContent) {
        return StringUtils.isNotBlank(checkContent);
    }

    /**
     * 是否检查表字段是否存在
     *
     * @return true:检查，false：不检查
     */
    protected boolean checkTableFieldExists() {
        return true;
    }

    protected boolean isSyntaxValid(String[] split) {
        // 校验类容少于三段 表达式错误，不允许执行
        return split.length >= NUM3;
    }

    /**
     * 执行修改字段属性校验
     *
     * @param tableInfo   表字段信息
     * @param tableName   表名
     * @param field       列名、字段名
     * @param targetValue 目标属性值
     * @return true：校验通过，false：校验不通过
     */
    protected boolean doChangeFieldCheck(EzasseTableInfo tableInfo, String tableName, String field, String targetValue) {
        return false;
    }

    /**
     * 修改表字段属性的检查
     *
     * @param tableInfos 表信息
     * @param tableName  表名
     * @param field      列名、字段名
     * @return true：校验通过，false：校验不通过
     */
    protected boolean doChangeFieldCheck(List<EzasseTableInfo> tableInfos, String tableName, String field) {
        return false;
    }
}
