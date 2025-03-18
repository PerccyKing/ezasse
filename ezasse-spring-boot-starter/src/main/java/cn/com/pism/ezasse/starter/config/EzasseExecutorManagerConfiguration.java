package cn.com.pism.ezasse.starter.config;

import cn.com.pism.ezasse.manager.ExecutorManager;
import cn.com.pism.ezasse.model.EzasseExecutor;
import cn.com.pism.ezasse.model.EzasseExecutorAction;
import cn.com.pism.ezasse.starter.annotation.ExecutorAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author PerccyKing
 * @since 25-03-15 22:50
 */
@Import(EzasseExecutorManagerBeanConfiguration.class)
public class EzasseExecutorManagerConfiguration {

    @Autowired
    @SuppressWarnings("unchecked")
    public void autowiredExecutorsAndActions(ApplicationContext applicationContext, ExecutorManager executorManager) {

        // 注册执行器
        registryExecutor(applicationContext, executorManager);

        // 注册执行器动作
        applicationContext.getBeansOfType(EzasseExecutorAction.class)
                .values()
                .forEach(action -> {
                    ExecutorAction annotation = AnnotationUtils.findAnnotation(action.getClass(), ExecutorAction.class);
                    if (annotation == null) {
                        return;
                    }
                    String[] dataSourceType = annotation.dataSourceType();
                    for (String type : dataSourceType) {
                        executorManager.registerExecutorAction(type, action);
                    }
                });
    }

    private void registryExecutor(ApplicationContext applicationContext, ExecutorManager executorManager) {
        // 所有自定义执行器
        Map<String, EzasseExecutor> ezasseExecutorMap = applicationContext.getBeansOfType(EzasseExecutor.class);
        if (CollectionUtils.isEmpty(ezasseExecutorMap)) {
            return;
        }

        // 注册执行器
        ezasseExecutorMap.values().forEach(executor -> executorManager.registerExecutor(executor.getDataSourceType(), executor));
    }

}
