package cn.com.pism.ezasse.model;

import cn.com.pism.ezasse.enums.EzasseDatabaseType;
import lombok.Data;

import javax.sql.DataSource;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/06 下午 08:38
 * @since 0.0.1
 */
@Data
public class EzasseDataSource {
    /**
     * id
     */
    private String id;

    /**
     * 数据库类型
     */
    private EzasseDatabaseType type;

    /**
     * 数据源
     */
    private DataSource datasource;
}
