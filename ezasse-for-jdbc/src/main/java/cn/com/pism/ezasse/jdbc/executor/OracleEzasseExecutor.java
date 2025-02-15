package cn.com.pism.ezasse.jdbc.executor;

/**
 * @author PerccyKing
 * @since 25-01-27 15:20
 */
public class OracleEzasseExecutor extends JdbcTemplateExecutor {

    public String getDataSourceType(){
        return "ORACLE";
    }
}
