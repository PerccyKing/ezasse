package cn.com.pism.ezasse.model;

import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.DO_EXECUTE;

/**
 * @author PerccyKing
 * @since 25-06-19 22:26
 */
public abstract class AbstractDoExecuteAction implements EzasseExecutorAction<DoExecuteActionParam, Boolean> {

    /**
     * <p>
     * 获取动作id
     * </p>
     * by perccyking
     *
     * @return {@link String} 动作id
     * @since 25-01-01 11:19
     */
    @Override
    public String getId() {
        return DO_EXECUTE;
    }
}
