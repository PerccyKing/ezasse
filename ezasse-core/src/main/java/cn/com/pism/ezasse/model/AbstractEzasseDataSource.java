package cn.com.pism.ezasse.model;

/**
 * ezasse 数据源
 * @author PerccyKing
 * @since 24-10-21 22:46
 */
@FunctionalInterface
public interface AbstractEzasseDataSource<T> {

    T getDataSource();

}
