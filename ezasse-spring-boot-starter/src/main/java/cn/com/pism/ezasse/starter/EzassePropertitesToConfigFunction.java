package cn.com.pism.ezasse.starter;

import cn.com.pism.ezasse.model.EzasseConfig;

import java.util.function.Function;

/**
 * @author wangyihuai@meb.com
 * @since 2023/12/1 15:51
 */
public class EzassePropertitesToConfigFunction implements Function<EzasseProperties, EzasseConfig> {
    @Override
    public EzasseConfig apply(EzasseProperties ezasseProperties) {
        EzasseConfig config = new EzasseConfig();
        config.setFolder(ezasseProperties.getFolder());
        config.setFileList(ezasseProperties.getFileList());
        config.setGroupOrder(ezasseProperties.getGroupOrder());
        config.setDelimiterStart(ezasseProperties.getDelimiterStart());
        config.setDelimiterEnd(ezasseProperties.getDelimiterEnd());
        config.setKeyWords(ezasseProperties.getKeyWords());
        return config;
    }
}
