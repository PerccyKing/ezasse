package cn.com.pism.ezasse.model;

import cn.com.pism.ezasse.action.EzasseExecutorAction;
import cn.com.pism.ezasse.action.EzasseExecutorActionParam;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author PerccyKing
 * @since 24-12-14 12:33
 */
public abstract class EzasseExecutor {

    protected EzasseDataSource ezasseDataSource;

    protected Map<String, EzasseExecutorAction<? extends EzasseExecutorActionParam, ?>> ezasseExecutorActionMap = new ConcurrentHashMap<>(16);

    protected EzasseExecutor(EzasseDataSource ezasseDataSource) {
        this.ezasseDataSource = ezasseDataSource;
    }

    @SuppressWarnings("unchecked")
    public <R, P extends EzasseExecutorActionParam> R execute(String action, P param) {
        EzasseExecutorAction<P, ?> ezasseExecutorAction = (EzasseExecutorAction<P, ?>) getEzasseExecutorAction(action);
        return (R) ezasseExecutorAction.doAction(param);
    }


    public void execute(Object content) {
        execute("", null);
    }

    public EzasseExecutorAction<? extends EzasseExecutorActionParam, ?> getEzasseExecutorAction(String action) {
        return ezasseExecutorActionMap.get(action);
    }

    public void registerAction(EzasseExecutorAction<? extends EzasseExecutorActionParam, ?> ezasseExecutorAction) {
        registerAction(ezasseExecutorAction.getId(), ezasseExecutorAction);
    }

    public void registerAction(String action, EzasseExecutorAction<? extends EzasseExecutorActionParam, ?> ezasseExecutorAction) {
        ezasseExecutorActionMap.put(action, ezasseExecutorAction);
    }

}
