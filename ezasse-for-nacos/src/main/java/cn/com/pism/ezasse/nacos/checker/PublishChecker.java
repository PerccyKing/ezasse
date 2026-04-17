package cn.com.pism.ezasse.nacos.checker;

import cn.com.pism.ezasse.checker.EzasseChecker;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseExecutor;

/**
 * 合并配置，如果存在配置就合并，不存在就不合并
 * {@code -- MERGE_TO(GROUP,DATA-ID,[TIMEOUT)}
 *
 * @author PerccyKing
 * @since 25-06-08 01:16
 */
public class PublishChecker extends EzasseChecker {

    @Override
    public boolean check(EzasseDataSource dataSource, EzasseExecutor checkExecutor, String checkContent) {
        // 不做验证
        return true;
    }

    @Override
    public String getId() {
        return "PUBLISH";
    }
}
