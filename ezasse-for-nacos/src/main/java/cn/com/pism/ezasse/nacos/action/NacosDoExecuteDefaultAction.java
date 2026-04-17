package cn.com.pism.ezasse.nacos.action;

import cn.com.pism.ezasse.checker.EzasseCheckLineContent;
import cn.com.pism.ezasse.model.AbstractDoExecuteAction;
import cn.com.pism.ezasse.model.DoExecuteActionParam;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.resource.EzasseFileLine;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;

/**
 * @author PerccyKing
 * @since 25-06-19 22:03
 */
public class NacosDoExecuteDefaultAction extends AbstractDoExecuteAction {

    @Override
    public Boolean doAction(DoExecuteActionParam actionParam, EzasseDataSource dataSource) {
        ConfigService configService = dataSource.getDataSource();
        EzasseCheckLineContent checkLineContent = actionParam.getCheckLineContent();

        EzasseFileLine checkLine = checkLineContent.getCheckLine();

        String checkContent = checkLine.getCheckContent();
        String executeContent = actionParam.getExecuteContent();
        String config = null;
        try {
            config = configService.getConfig("", "", 200L);
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }

        try {
            configService.publishConfig("","",merge(config,executeContent));
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private String merge(String config, String executeContent) {
        // TEXT
        // JSON
        // XML
        // YAML
        // HTML
        // Properties
        return  "";
    }
}
