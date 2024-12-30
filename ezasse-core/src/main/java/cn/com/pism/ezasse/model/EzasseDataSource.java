package cn.com.pism.ezasse.model;

/**
 * ezasse 数据源
 * @author PerccyKing
 * @since 24-10-21 22:46
 */
public interface EzasseDataSource {

    <T> T getDataSource();

    String getId();

    String getType();

}
