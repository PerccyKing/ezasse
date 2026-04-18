package cn.com.pism.ezasse.nacos.action;

import cn.com.pism.ezasse.checker.EzasseCheckLineContent;
import cn.com.pism.ezasse.exception.EzasseException;
import cn.com.pism.ezasse.model.DoExecuteActionParam;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseExecutorAction;
import cn.com.pism.ezasse.nacos.constants.EzasseForNacosConstant;
import cn.com.pism.ezasse.resource.EzasseFileLine;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Nacos 覆盖发布动作
 *
 * @author PerccyKing
 * @since 25-06-19 22:03
 */
public class NacosPublishAction implements EzasseExecutorAction<DoExecuteActionParam, Boolean> {

    private static final Log log = LogFactory.getLog(NacosPublishAction.class);

    @Override
    public Boolean doAction(DoExecuteActionParam actionParam, EzasseDataSource dataSource) {
        ConfigService configService = dataSource.getDataSource();
        EzasseCheckLineContent checkLineContent = actionParam.getCheckLineContent();
        EzasseFileLine checkLine = checkLineContent.getCheckLine();

        String checkContent = checkLine.getCheckContent();
        String executeContent = actionParam.getExecuteContent();

        NacosPublishParam param = NacosPublishParam.parse(checkContent);

        try {
            boolean result = configService.publishConfig(param.getDataId(), param.getGroup(), executeContent);
            if (log.isDebugEnabled()) {
                log.debug(String.format("Published config to Nacos [%s/%s]: %s", param.getGroup(), param.getDataId(), result));
            }
            return result;
        } catch (NacosException e) {
            throw new EzasseException(String.format("Failed to publish config to Nacos: dataId=%s, group=%s", param.getDataId(), param.getGroup()), e);
        }
    }

    @Override
    public String getId() {
        return EzasseForNacosConstant.ACTION_PUBLISH;
    }
}
