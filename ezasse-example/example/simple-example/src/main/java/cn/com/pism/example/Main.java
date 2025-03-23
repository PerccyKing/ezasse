package cn.com.pism.example;

import cn.com.pism.ezasse.FileEzasse;
import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.jdbc.register.JdbcEzasseDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

import javax.sql.DataSource;

/**
 * @author PerccyKing
 * @since 25-03-18 23:12
 */
public class Main {

    public static void main(String[] args) {
        // 动态设置日志级别为 INFO
        Configurator.setAllLevels("cn.com.pism.ezasse", Level.TRACE);

        // 第一步：创建ezasse
        FileEzasse ezasse = new FileEzasse();
        // 第二步：添加数据源
        String password = "数据库密码，需要根据实际情况修改";
        EzasseContextHolder.getContext().datasourceManager().registerDataSource(new JdbcEzasseDataSource(createDatasource(password), "master"));
        // 第二步：执行
        ezasse.execute();
    }

    public static DataSource createDatasource(String password) {
        HikariConfig config = new HikariConfig();
        // 数据库连接 URL，需要根据实际情况修改
        config.setJdbcUrl("jdbc:mysql://localhost:36666/ezasse");
        // 数据库用户名，需要根据实际情况修改
        config.setUsername("root");
        // 数据库密码，需要根据实际情况修改
        config.setPassword(password);
        // 省略其他配置

        return new HikariDataSource(config);
    }
}
