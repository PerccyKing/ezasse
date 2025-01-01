package cn.com.pism.ezasse.manager;

import cn.com.pism.ezasse.model.EzasseConfig;

/**
 * 配置管理器
 * @author PerccyKing
 * @since 25-01-01 13:03
 */
public interface ConfigManager {


    /**
     * <p>
     * 设置配置
     * </p>
     * by perccyking
     *
     * @param config : ezasse配置
     * @since 24-10-22 00:10
     */
    void setConfig(EzasseConfig config);

    /**
     * <p>
     * 获取ezasse配置
     * </p>
     * by perccyking
     *
     * @return {@link EzasseConfig} ezasse配置
     * @since 24-10-22 00:11
     */
    EzasseConfig getConfig();

}
