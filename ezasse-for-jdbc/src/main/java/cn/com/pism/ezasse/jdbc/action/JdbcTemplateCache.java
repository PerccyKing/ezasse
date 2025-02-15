package cn.com.pism.ezasse.jdbc.action;

import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.model.EzasseDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存
 *
 * @author PerccyKing
 * @since 25-02-08 15:22
 */
public class JdbcTemplateCache {

    /**
     * jdbcTemplate缓存 数据源id-jdbcTemplate
     */
    private static final Map<String, JdbcTemplate> JDBC_TEMPLATE_MAP = new HashMap<>(16);

    private JdbcTemplateCache() {
    }

    public static JdbcTemplate get(String dataSourceId) {
        JdbcTemplate jdbcTemplate = JDBC_TEMPLATE_MAP.get(dataSourceId);
        if (jdbcTemplate == null) {
            synchronized (EzasseDataSource.class) {
                EzasseDataSource dataSource = EzasseContextHolder.getContext().datasourceManager().getDataSource(dataSourceId);
                jdbcTemplate = new JdbcTemplate(dataSource.getDataSource());
                JDBC_TEMPLATE_MAP.put(dataSourceId, jdbcTemplate);
            }
        }
        return jdbcTemplate;
    }


}
