package cn.com.pism.ezasse.model;

import cn.com.pism.ezasse.util.EzasseUtil;

/**
 * jdbc 数据源
 *
 * @author PerccyKing
 * @since 25-01-16 23:36
 */
public abstract class JdbcEzasseDataSource implements EzasseDataSource {

    /**
     * 获取 jdbc 数据源类型
     * @return 类型 eg:mysql,oracle,h2 ...
     */
    @Override
    public String getType() {
        return EzasseUtil.getDatabaseTypeFromDataSource(getDataSource());
    }
}
