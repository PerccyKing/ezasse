package cn.com.pism.ezasse;

import cn.com.pism.ezasse.exception.EzasseException;
import cn.com.pism.ezasse.model.EzasseConfig;
import cn.com.pism.ezasse.model.EzasseNodeList;
import cn.com.pism.ezasse.model.EzasseSql;
import cn.com.pism.resourcescanner.*;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static cn.com.pism.ezasse.EzasseConstants.CLASSPATH_PREFIX;
import static cn.com.pism.ezasse.enums.EzasseExceptionCode.UNSPECIFIED_FOLDER_EXCEPTION;


/**
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/04 下午 10:49
 * @since 0.0.1
 */
@Data
public class Ezasse {

    private Map<DataSource, String> dataSourceSqlMap;

    private List<SqlGroup> sqlGroups;

    /**
     * <p>
     * 执行脚本
     * </p>
     *
     * @author PerccyKing
     * @date 2022/04/04 下午 11:18
     */
    public void executeScript(EzasseConfig config) {
        //获取SQL文件列表
        List<EzasseNodeList<EzasseSql>> ezasseNodeLists = getEzasseSqlNodeLists(config);
        //确认文件顺序
        //确认数据源
        //按文件执行SQL脚本
    }


    /**
     * <p>
     * 获取SQL的文件树
     * </p>
     *
     * @param config : 全局配置
     * @return {@link ArrayList<EzasseNodeList<EzasseSql>>}
     * @author PerccyKing
     * @date 2022/04/05 下午 12:40
     */
    private ArrayList<EzasseNodeList<EzasseSql>> getEzasseSqlNodeLists(EzasseConfig config) {
        String folder = config.getFolder();
        //没有指定文件时，抛出异常
        if (StringUtils.isBlank(folder)) {
            throw new EzasseException(UNSPECIFIED_FOLDER_EXCEPTION);
        }
        List<Location> locations = new ArrayList<>();
        if (folder.startsWith(CLASSPATH_PREFIX)) {
            locations.add(new Location(folder));
        } else {
            locations.add(new Location(CLASSPATH_PREFIX + folder));
        }
        Scanner<JavaMigration> scanner = new Scanner<>(
                JavaMigration.class,
                locations,
                Thread.currentThread().getContextClassLoader(),
                StandardCharsets.UTF_8,
                new ResourceNameCache(),
                new LocationScannerCache()
        );
        Collection<LoadableResource> resources = scanner.getResources("", ".sql");
        resources.forEach(r -> {
            String absolutePath = r.getAbsolutePath();

        });
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        Ezasse ezasse = new Ezasse();
        EzasseConfig ezasseConfig = new EzasseConfig();
        ezasseConfig.setFolder("/data");
        ezasse.executeScript(ezasseConfig);
    }

    /**
     * sql文件分组
     *
     * @author PerccyKing
     * @version 0.0.1
     * @date 2022/04/04 下午 11:19
     * @since 0.0.1
     */
    @Data
    static class SqlGroup {
        /**
         * sql文件所在目录
         */
        private String folder;

        /**
         * sql文件 名称
         * 格式：TYPE-{@code ${dataSource}}-ORDER-{@code OTHERS}<br>
         * eg:  data-master-001-users<br>
         */
        private List<String> files;

        /**
         * 顺序定义
         */
        private int order;
    }
}
