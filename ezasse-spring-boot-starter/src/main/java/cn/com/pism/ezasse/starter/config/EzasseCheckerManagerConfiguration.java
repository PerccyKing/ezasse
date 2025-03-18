package cn.com.pism.ezasse.starter.config;

import cn.com.pism.ezasse.manager.CheckerManager;
import cn.com.pism.ezasse.checker.EzasseChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author PerccyKing
 * @since 25-03-15 22:29
 */
@Import(EzasseCheckerManagerBeanConfiguration.class)
public class EzasseCheckerManagerConfiguration {

    @Autowired
    public void autowiredCheckers(ApplicationContext applicationContext, CheckerManager checkerManager) {
        Map<String, EzasseChecker> ezasseCheckerMap = applicationContext.getBeansOfType(EzasseChecker.class);
        if (CollectionUtils.isEmpty(ezasseCheckerMap)) {
            return;
        }

        ezasseCheckerMap.values().forEach(checkerManager::registerChecker);
    }


}
