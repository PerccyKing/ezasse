package cn.com.pism.ezasse.jdbc.util;

import cn.com.pism.ezasse.util.EzasseLogUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author PerccyKing
 * @since 25-03-24 23:56
 */
public class SqlTypeUtil {

    private static final Log log = LogFactory.getLog(SqlTypeUtil.class);

    private static final Map<String, Map<String, Boolean>> DATABASE_TYPE_PARAMS_EDITABLE_MAP = new HashMap<>(16);


    private SqlTypeUtil() {
    }

    public static Boolean isParamsEditable(String databaseType, String type) {
        Map<String, Boolean> typeParamsEditableMap = DATABASE_TYPE_PARAMS_EDITABLE_MAP.get(databaseType);
        if (CollectionUtils.isEmpty(typeParamsEditableMap)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE.equals(typeParamsEditableMap.get(type.toUpperCase()));
    }

    public static void init(String databaseType, DataSource dataSource) {
        if (DATABASE_TYPE_PARAMS_EDITABLE_MAP.containsKey(databaseType)) {
            return;
        }

        Map<String, Boolean> typeParamsEditableMap = new HashMap<>(16);
        try {
            DatabaseMetaData metaData;
            try (Connection connection = dataSource.getConnection()) {
                metaData = connection.getMetaData();
            }
            ResultSet typeInfo = metaData.getTypeInfo();

            while (typeInfo.next()) {
                String typeName = typeInfo.getString("TYPE_NAME");
                String createParams = typeInfo.getString("CREATE_PARAMS");
                if (StringUtils.isNotBlank(createParams)) {
                    typeParamsEditableMap.put(typeName.toUpperCase(), createParams.contains("(") && createParams.contains(")"));
                }
            }
            DATABASE_TYPE_PARAMS_EDITABLE_MAP.put(databaseType, typeParamsEditableMap);
        } catch (SQLException e) {
            EzasseLogUtil.error(log, e.getMessage(), e);
        }
    }
}
