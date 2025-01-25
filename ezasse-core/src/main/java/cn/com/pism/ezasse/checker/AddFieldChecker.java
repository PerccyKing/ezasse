package cn.com.pism.ezasse.checker;

import cn.com.pism.ezasse.action.param.GetTableInfoActionParam;
import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.executor.EzasseExecutor;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseTableInfo;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static cn.com.pism.ezasse.constants.EzasseConstants.REGX_POINT;
import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.GET_TABLE_INFO;

/**
 * 添加字段，判断表中是否存在字段
 *
 * @author PerccyKing
 * @since 25-01-14 23:02
 */
public class AddFieldChecker implements EzasseChecker {

    @Override
    public boolean check(EzasseDataSource dataSource, String checkContent) {
        // 第一位为表名，第二位为列名
        String[] split = checkContent.split(REGX_POINT);
        EzasseExecutor ezasseExecutor = getEzasseExecutor(dataSource.getId());
        List<EzasseTableInfo> tableInfos = ezasseExecutor.execute(GET_TABLE_INFO,
                GetTableInfoActionParam.builder()
                        .tableName(split[0])
                        .columnName(split[1])
                        .build());
        if (CollectionUtils.isEmpty(tableInfos)) {
            return true;
        }

        // 查找是否存在于目标列名相同的列信息
        long count = tableInfos.stream().filter(tableInfo -> tableInfo.getColumnName().equals(split[1])).count();

        // 未找到列返回true
        return count == 0;
    }

    @Override
    public String getId() {
        return EzasseContextHolder.getContext().configManger().getConfig().getKeyWords().getField().getAdd();
    }
}
