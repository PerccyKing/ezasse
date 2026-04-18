package cn.com.pism.ezasse.nacos.action;

import cn.com.pism.ezasse.nacos.constants.EzasseForNacosConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Nacos 发布参数内部封装类
 *
 * @author PerccyKing
 * @since 25-06-19 22:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NacosPublishParam {

    private static final Log log = LogFactory.getLog(NacosPublishParam.class);

    /**
     * Nacos 配置分组
     */
    private String group;

    /**
     * Nacos 配置ID
     */
    private String dataId;

    /**
     * 获取配置的超时时间（毫秒）
     */
    private long timeoutMs;

    /**
     * 解析 checkContent 中的 Nacos 发布参数
     * <p>
     * 支持的格式：
     * <ul>
     *     <li>{@code GROUP,DATA-ID} - 使用默认超时时间</li>
     *     <li>{@code GROUP,DATA-ID,5000} - 指定超时时间为 5000ms</li>
     * </ul>
     * </p>
     *
     * @param checkContent 校验内容，格式为 {@code GROUP,DATA-ID[,TIMEOUT]}
     * @return 解析后的 Nacos 发布参数
     * @throws IllegalArgumentException 如果参数格式不合法
     */
    public static NacosPublishParam parse(String checkContent) {
        if (StringUtils.isBlank(checkContent)) {
            throw new IllegalArgumentException("Nacos publish checkContent cannot be blank");
        }

        String[] parts = checkContent.split(",");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid Nacos publish checkContent format: " + checkContent
                    + ". Expected: GROUP,DATA-ID[,TIMEOUT]");
        }

        String group = parts[0].trim();
        String dataId = parts[1].trim();
        long timeoutMs = EzasseForNacosConstant.DEFAULT_TIMEOUT_MS;

        // 可选的第三个参数为超时时间
        if (parts.length >= 3 && StringUtils.isNotBlank(parts[2])) {
            try {
                timeoutMs = Long.parseLong(parts[2].trim());
            } catch (NumberFormatException e) {
                log.warn("Invalid timeout value: " + parts[2] + ", using default: " + EzasseForNacosConstant.DEFAULT_TIMEOUT_MS);
            }
        }

        return new NacosPublishParam(group, dataId, timeoutMs);
    }
}
