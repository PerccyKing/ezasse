package cn.com.pism.ezasse.manager.impl;

import cn.com.pism.ezasse.manager.CheckerManager;
import cn.com.pism.ezasse.model.EzasseChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
}
