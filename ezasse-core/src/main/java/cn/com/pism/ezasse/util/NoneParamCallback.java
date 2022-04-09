package cn.com.pism.ezasse.util;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/09 下午 03:54
 * @since 0.0.1
 */
@FunctionalInterface
public interface NoneParamCallback<R> {
    /**
     * 回调
     *
     * @return R
     */
    R call();
}
