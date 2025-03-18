package cn.com.pism.ezasse.manager.impl;

import cn.com.pism.ezasse.manager.CheckerManager;
import cn.com.pism.ezasse.checker.EzasseChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 校验器管理器默认实现
 *
 * @author PerccyKing
 * @since 25-01-01 12:34
 */
public class DefaultCheckerManager implements CheckerManager {

    /**
     * 校验器map [id,校验器]
     */
    private final Map<String, EzasseChecker> checkerMap = new ConcurrentHashMap<>(16);

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
    @Override
    public EzasseChecker getChecker(String checkerId) {
        return checkerMap.get(checkerId);
    }

    /**
     * <p>
     * 注册校验器
     * </p>
     * by perccyking
     *
     * @param checker : 校验器
     * @since 25-01-13 22:32
     */
    @Override
    public void registerChecker(EzasseChecker checker) {
        checkerMap.put(checker.getId(), checker);
    }

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
    @Override
    public void registerChecker(String checkerId, EzasseChecker checker) {
        checkerMap.put(checkerId, checker);
    }

    /**
     * <p>
     * 获取所有校验器的校验key
     * </p>
     * by perccyking
     *
     * @return 校验key
     * @since 24-12-29 17:48
     */
    @Override
    public List<String> getCheckerKeys() {
        return new ArrayList<>(checkerMap.keySet());
    }

    /**
     * <p>
     * 获取所有允许为空的校验器
     * </p>
     * by perccyking
     *
     * @return 校验器id
     * @since 25-02-02 23:10
     */
    @Override
    public List<String> getAllowEmptyCheckerKeys() {
        return checkerMap
                .values()
                .stream()
                .filter(EzasseChecker::allEmpty)
                .map(EzasseChecker::getId)
                .collect(Collectors.toList());
    }
}
