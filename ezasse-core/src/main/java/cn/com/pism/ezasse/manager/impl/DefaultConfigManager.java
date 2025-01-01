package cn.com.pism.ezasse.manager.impl;

import cn.com.pism.ezasse.manager.ConfigManager;
import cn.com.pism.ezasse.model.EzasseConfig;
import lombok.Data;

/**
 * 配置管理器的默认实现
 *
 * @author PerccyKing
 * @since 25-01-01 13:03
 */
@Data
public class DefaultConfigManager implements ConfigManager {

    /**
     * 配置
     */
    private EzasseConfig config;

    /**
     * <p>
     * 设置配置
     * </p>
     * by perccyking
     *
     * @param config : ezasse配置
     * @since 24-10-22 00:10
     */
    @Override
    public void setConfig(EzasseConfig config) {
        this.config = config;
    }

    /**
     * <p>
     * 获取ezasseP诶之
     * </p>
     * by perccyking
     *
     * @return {@link EzasseConfig} ezasse配置
     * @since 24-10-22 00:11
     */
    @Override
    public EzasseConfig getConfig() {
        return config;
    }
}
