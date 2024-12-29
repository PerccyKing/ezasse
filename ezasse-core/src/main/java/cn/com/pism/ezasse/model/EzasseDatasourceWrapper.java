package cn.com.pism.ezasse.model;

/**
 * 数据源包装
 * @author PerccyKing
 * @since 24-12-14 13:13
 */
public class EzasseDatasourceWrapper<T> {
    private String datasourceType = "JDBC";

    private EzasseDatasourceWrapper(){}

    private T getDataSource(){
        return null;
    }
}
