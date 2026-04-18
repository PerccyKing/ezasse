package cn.com.pism.ezasse.nacos.action;

import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.model.AbstractDoExecuteAction;
import cn.com.pism.ezasse.model.DoExecuteActionParam;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseExecutorAction;
import cn.com.pism.ezasse.nacos.constants.EzasseForNacosConstant;

/**
 * Nacos 默认执行动作 (Orchestrator)
 * <p>
 * 由于 FileEzasse 的默认执行流程硬编码传递了 DO_EXECUTE 动作，
 * 此类作为 NACOS 数据源的默认 DO_EXECUTE 处理入口，
 * 并将实际的发布或合并逻辑委托给具体的 {@link EzasseExecutorAction} (例如 {@link NacosPublishAction} 或 {@link NacosMergeToAction})。
 * </p>
 *
 * @author PerccyKing
 * @since 25-06-19 22:03
 */
public class NacosDoExecuteDefaultAction extends AbstractDoExecuteAction {

    @Override
    @SuppressWarnings("unchecked")
    public Boolean doAction(DoExecuteActionParam actionParam, EzasseDataSource dataSource) {
        // 从校验行中获取动作的 checkKey，例如 "PUBLISH" 或 "MERGE_TO"
        String checkKey = actionParam.getCheckLineContent().getCheckLine().getCheckKey();

        // 根据 checkKey 从执行器管理器中获取对应的具体动作处理类
        EzasseExecutorAction<DoExecuteActionParam, Boolean> specificAction =
                (EzasseExecutorAction<DoExecuteActionParam, Boolean>) EzasseContextHolder.getContext()
                        .executorManager()
                        .getExecutorAction(EzasseForNacosConstant.NACOS, checkKey);

        if (specificAction != null) {
            return specificAction.doAction(actionParam, dataSource);
        }

        throw new UnsupportedOperationException("No specific Nacos action found for checkKey: " + checkKey);
    }
}

