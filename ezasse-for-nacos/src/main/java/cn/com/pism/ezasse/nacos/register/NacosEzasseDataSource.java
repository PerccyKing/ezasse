package cn.com.pism.ezasse.nacos.register;

import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.nacos.constants.EzasseForNacosConstant;
import com.alibaba.nacos.api.config.ConfigService;
import lombok.AllArgsConstructor;

/**
 * Nacos Ezasse 数据源实现
 * <p>
 * 封装了 Nacos 的 {@link ConfigService} 作为底层数据源，
 * 提供对 Nacos 配置中心的统一访问能力。
 * </p>
 *
 * @author PerccyKing
 * @since 25-06-19 20:10
 */
@AllArgsConstructor
public class NacosEzasseDataSource implements EzasseDataSource {

    private ConfigService configService;

    private String id;

    /**
     * 获取底层的 Nacos ConfigService 数据源
     *
     * @return Nacos 的 ConfigService 实例
     */
    @Override
    @SuppressWarnings("unchecked")
    public ConfigService getDataSource() {
        return configService;
    }

    /**
     * 获取该数据源的唯一标识
     *
     * @return 数据源ID
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * 获取数据源类型
     *
     * @return 返回 {@value EzasseForNacosConstant#NACOS} 表示是 Nacos 类型的数据源
     */
    @Override
    public String getType() {
        return EzasseForNacosConstant.NACOS;
    }
}
