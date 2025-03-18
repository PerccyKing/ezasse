package cn.com.pism.ezasse.jdbc.model;

import cn.com.pism.ezasse.model.EzasseDataSource;
import lombok.AllArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.sql.DataSource;
import java.sql.Connection;

import static cn.com.pism.ezasse.jdbc.constants.EzasseDatabaseTypeConstants.UNKNOWN;

/**
 * jdbc 数据源
 *
 * @author PerccyKing
 * @since 25-01-16 23:36
 */
@AllArgsConstructor
public class JdbcEzasseDataSource implements EzasseDataSource {

    private static final Log log = LogFactory.getLog(JdbcEzasseDataSource.class);

    private DataSource dataSource;

    private String id;

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getDataSource() {
        return (T) dataSource;
    }

    /**
     * <p>
     * 获取数据源id
     * </p>
     * by perccyking
     *
     * @return {@link String} 数据源id
     * @since 25-01-01 00:44
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * 获取 jdbc 数据源类型
     *
     * @return 类型 eg:mysql,oracle,h2 ...
     */
    @Override
    public String getType() {
        try (Connection connection = dataSource.getConnection()) {
            return getDatabaseProductName(connection.getMetaData().getURL()).toUpperCase();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return UNKNOWN;
    }

    private String getDatabaseProductName(String jdbcUrl) {
        if (jdbcUrl == null || jdbcUrl.isEmpty()) {
            return UNKNOWN;
        }
        // 首先检查是否以 jdbc: 开头
        if (!jdbcUrl.startsWith("jdbc:")) {
            return UNKNOWN;
        }
        // 跳过 jdbc: 部分
        String remaining = jdbcUrl.substring(5);
        int index = remaining.indexOf(':');
        if (index != -1) {
            return remaining.substring(0, index);
        }
        index = remaining.indexOf('/');
        if (index != -1) {
            return remaining.substring(0, index);
        }
        return remaining;
    }


}
