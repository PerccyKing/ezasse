package cn.com.pism.ezasse.nacos.executor;

import cn.com.pism.ezasse.model.EzasseExecutor;
import cn.com.pism.ezasse.nacos.constants.EzasseForNacosConstant;

/**
 *
 * @author PerccyKing
 * @since 25-06-19 20:04
 */
public class NacosExecutor extends EzasseExecutor {

    @Override
    public String getDataSourceType() {
        return EzasseForNacosConstant.NACOS;
    }
}
