package cn.com.pism.ezasse.model;

/**
 * ezasse 数据源
 *
 * @author PerccyKing
 * @since 24-10-21 22:46
 */
public interface EzasseDataSource {

    /**
     * <p>
     * 获取数据源
     * </p>
     * by perccyking
     *
     * @return {@link T} 数据源
     * @since 25-01-01 00:44
     */
    <T> T getDataSource();

    /**
     * <p>
     * 获取数据源id
     * </p>
     * by perccyking
     *
     * @return {@link String} 数据源id
     * @since 25-01-01 00:44
     */
    String getId();

    /**
     * <p>
     * 数据源类型
     * </p>
     * by perccyking
     *
     * @return {@link String} 数据源类型
     * @since 25-01-01 00:45
     */
    String getType();

}
