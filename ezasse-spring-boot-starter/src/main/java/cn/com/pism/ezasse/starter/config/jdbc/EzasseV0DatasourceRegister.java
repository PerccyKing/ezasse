package cn.com.pism.ezasse.starter.config.jdbc;

import cn.com.pism.ezasse.jdbc.model.JdbcEzasseDataSource;
import cn.com.pism.ezasse.manager.DatasourceManager;
import cn.com.pism.ezasse.starter.EzasseDatasource;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.sql.DataSource;

import static cn.com.pism.ezasse.constants.EzasseConstants.MASTER;

/**
 * 0.x 数据源兼容，将在后续的版本中陆续删除
 * @author PerccyKing
 * @since 25-03-13 22:55
 */
public class EzasseV0DatasourceRegister implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @SuppressWarnings("all")
    public void register(DatasourceManager datasourceManager) {
        String[] beanNamesForType = applicationContext.getBeanNamesForType(EzasseDatasource.class);
        if (ArrayUtils.isEmpty(beanNamesForType)) {
            return;
        }
        EzasseDatasource ezasseDatasource = applicationContext.getBean(beanNamesForType[0], EzasseDatasource.class);

        DataSource master = ezasseDatasource.getMaster();
        if (master != null) {
            datasourceManager.registerMasterDataSource(new JdbcEzasseDataSource(master, MASTER));
        }

        ezasseDatasource.getDataSource()
                .forEach((name, dataSource) ->
                        datasourceManager.registerDataSource(new JdbcEzasseDataSource(dataSource, name)));


    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
