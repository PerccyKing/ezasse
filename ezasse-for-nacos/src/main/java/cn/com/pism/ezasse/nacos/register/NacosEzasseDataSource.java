package cn.com.pism.ezasse.nacos.register;

import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.nacos.constants.EzasseForNacosConstant;
import com.alibaba.nacos.api.config.ConfigService;
import lombok.AllArgsConstructor;

/**
 * nacos ezasse 数据源
 *
 * @author PerccyKing
 * @since 25-06-19 20:10
 */
@AllArgsConstructor
public class NacosEzasseDataSource implements EzasseDataSource {

    private ConfigService configService;

    private String id;

    @Override
    @SuppressWarnings("unchecked")
    public ConfigService getDataSource() {
        return configService;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getType() {
        return EzasseForNacosConstant.NACOS;
    }
}
