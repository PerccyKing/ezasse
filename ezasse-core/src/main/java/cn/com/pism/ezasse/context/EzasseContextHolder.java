package cn.com.pism.ezasse.context;

import cn.com.pism.ezasse.model.DefaultEzasseContext;

import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author PerccyKing
 * @since 24-10-22 23:24
 */
public class EzasseContextHolder {

    private EzasseContextHolder() {
        throw new UnsupportedOperationException("Cannot instantiate " + getClass().getName());
    }

    private static final AtomicReference<EzasseContext> ezasseContext = new AtomicReference<>();

    public static EzasseContext getContext() {
        EzasseContext context = ezasseContext.get();
        if (context == null) {
            synchronized (EzasseContextHolder.class) {
                context = ezasseContext.get();
                if (context == null) {
                    context = new DefaultEzasseContext();
                    ezasseContext.set(context);
                }
            }
        }
        return context;
    }


    public static void setEzasseContext(EzasseContext context) {
        ezasseContext.set(context);
    }
}
