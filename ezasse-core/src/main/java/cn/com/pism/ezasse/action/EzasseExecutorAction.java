package cn.com.pism.ezasse.action;

/**
 * @author PerccyKing
 * @since 24-12-14 14:41
 */
public interface EzasseExecutorAction<P extends EzasseExecutorActionParam, R> {

    R doAction(P actionParam);

    String getId();
}
