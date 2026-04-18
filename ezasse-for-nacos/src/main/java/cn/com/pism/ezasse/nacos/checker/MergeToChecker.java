package cn.com.pism.ezasse.nacos.checker;

import cn.com.pism.ezasse.checker.EzasseChecker;
import cn.com.pism.ezasse.model.EzasseDataSource;
import org.apache.commons.lang3.StringUtils;

import static cn.com.pism.ezasse.nacos.constants.EzasseForNacosConstant.ACTION_MERGE_TO;

/**
 * Nacos 合并发布检查器（MERGE_TO）
 * <p>
 * 当 ezasse 脚本中使用 {@code -- MERGE_TO(GROUP,DATA-ID[,TIMEOUT])} 指令时，
 * 此检查器被触发。MERGE_TO 语义为<strong>增量合并发布</strong>，即先从 Nacos 获取
 * 原有配置，将脚本中的新内容与原有配置合并后再发布。
 * </p>
 * <p>
 * checkContent 由核心层的 CHECK_LINE_PATTERN 已解析，格式为：{@code GROUP,DATA-ID[,TIMEOUT]}
 * </p>
 *
 * @author PerccyKing
 * @since 25-06-08 01:16
 */
public class MergeToChecker extends EzasseChecker {

    /**
     * 校验合并发布参数格式是否合法
     * <p>
     * checkContent 至少需要包含 GROUP 和 DATA-ID 两个参数（以逗号分隔），
     * 可选的第三个参数为 TIMEOUT（毫秒）。
     * </p>
     *
     * @param dataSource   数据源
     * @param checkContent 校验内容，格式为 {@code GROUP,DATA-ID[,TIMEOUT]}
     * @return 参数合法时返回 true，否则返回 false
     */
    @Override
    public boolean check(EzasseDataSource dataSource, String checkContent) {
        if (StringUtils.isBlank(checkContent)) {
            return false;
        }
        // checkContent 格式: "GROUP,DATA-ID" 或 "GROUP,DATA-ID,5000"
        String[] parts = checkContent.split(",");
        // 至少需要 GROUP 和 DATA-ID 两个参数
        return parts.length >= 2
                && StringUtils.isNotBlank(parts[0])
                && StringUtils.isNotBlank(parts[1]);
    }

    /**
     * 获取检查器的唯一标识
     *
     * @return 检查器ID {@code "MERGE_TO"}
     */
    @Override
    public String getId() {
        return ACTION_MERGE_TO;
    }
}
