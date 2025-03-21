package cn.com.pism.ezasse.manager;

import cn.com.pism.ezasse.checker.EzasseChecker;

import java.util.List;
import java.util.ServiceLoader;

/**
 * 校验器管理器
 *
 * @author PerccyKing
 * @since 25-01-01 12:33
 */
public interface CheckerManager {

    /**
     * 注册校验器
     */
    default void registerCheckers() {
        ServiceLoader<EzasseChecker> checkers = ServiceLoader.load(EzasseChecker.class);
        checkers.forEach(this::registerChecker);
    }

    /**
     * <p>
     * 通过校验器id获取校验器实例
     * </p>
     * by perccyking
     *
     * @param checkerId : 校验器id
     * @return {@link EzasseChecker} 校验器
     * @since 24-12-29 17:47
     */
    EzasseChecker getChecker(String checkerId);

    /**
     * <p>
     * 注册校验器
     * </p>
     * by perccyking
     *
     * @param checker : 校验器
     * @since 25-01-13 22:32
     */
    void registerChecker(EzasseChecker checker);

    /**
     * <p>
     * 注册校验器
     * </p>
     * by perccyking
     *
     * @param checkerId : 校验器id
     * @param checker   : 校验器
     * @since 24-12-29 17:47
     */
    void registerChecker(String checkerId, EzasseChecker checker);

    /**
     * <p>
     * 获取所有校验器的校验key
     * </p>
     * by perccyking
     *
     * @return 校验key
     * @since 24-12-29 17:48
     */
    List<String> getCheckerKeys();

    /**
     * <p>
     * 获取所有允许为空的校验器
     * </p>
     * by perccyking
     *
     * @return 校验器id
     * @since 25-02-02 23:10
     */
    List<String> getAllowEmptyCheckerKeys();

}
