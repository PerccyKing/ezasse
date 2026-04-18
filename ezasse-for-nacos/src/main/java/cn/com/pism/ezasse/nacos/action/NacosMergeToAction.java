package cn.com.pism.ezasse.nacos.action;

import cn.com.pism.ezasse.checker.EzasseCheckLineContent;
import cn.com.pism.ezasse.exception.EzasseException;
import cn.com.pism.ezasse.model.DoExecuteActionParam;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseExecutorAction;
import cn.com.pism.ezasse.nacos.constants.EzasseForNacosConstant;
import cn.com.pism.ezasse.resource.EzasseFileLine;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Nacos 增量合并发布动作
 *
 * @author PerccyKing
 * @since 25-06-19 22:03
 */
public class NacosMergeToAction implements EzasseExecutorAction<DoExecuteActionParam, Boolean> {

    private static final Log log = LogFactory.getLog(NacosMergeToAction.class);

    @Override
    public Boolean doAction(DoExecuteActionParam actionParam, EzasseDataSource dataSource) {
        ConfigService configService = dataSource.getDataSource();
        EzasseCheckLineContent checkLineContent = actionParam.getCheckLineContent();
        EzasseFileLine checkLine = checkLineContent.getCheckLine();

        String checkContent = checkLine.getCheckContent();
        String executeContent = actionParam.getExecuteContent();

        NacosPublishParam param = NacosPublishParam.parse(checkContent);

        String oldConfig = getExistingConfig(configService, param);
        String finalConfig = executeContent;

        if (StringUtils.isNotBlank(oldConfig)) {
            finalConfig = merge(param.getDataId(), oldConfig, executeContent);
        }

        try {
            boolean result = configService.publishConfig(param.getDataId(), param.getGroup(), finalConfig);
            if (log.isDebugEnabled()) {
                log.debug(String.format("Published merged config to Nacos [%s/%s]: %s", param.getGroup(), param.getDataId(), result));
            }
            return result;
        } catch (NacosException e) {
            throw new EzasseException(
                    String.format("Failed to publish config to Nacos: dataId=%s, group=%s", param.getDataId(), param.getGroup()), e);
        }
    }

    private String getExistingConfig(ConfigService configService, NacosPublishParam param) {
        try {
            return configService.getConfig(param.getDataId(), param.getGroup(), param.getTimeoutMs());
        } catch (NacosException e) {
            log.warn(String.format("Failed to get existing config from Nacos: dataId=%s, group=%s",
                    param.getDataId(), param.getGroup()), e);
            return null;
        }
    }

    private String merge(String dataId, String oldConfig, String executeContent) {
        if (StringUtils.isBlank(oldConfig)) {
            return executeContent;
        }
        if (StringUtils.isBlank(executeContent)) {
            return oldConfig;
        }

        if (dataId.endsWith(EzasseForNacosConstant.EXT_PROPERTIES)) {
            return mergeProperties(oldConfig, executeContent);
        } else if (dataId.endsWith(EzasseForNacosConstant.EXT_YAML) || dataId.endsWith(EzasseForNacosConstant.EXT_YML)) {
            return mergeYaml(oldConfig, executeContent);
        }

        return oldConfig + "\n" + executeContent;
    }

    private String mergeProperties(String oldConfig, String newConfig) {
        try {
            Properties oldProps = new Properties();
            oldProps.load(new StringReader(oldConfig));

            Properties newProps = new Properties();
            newProps.load(new StringReader(newConfig));

            oldProps.putAll(newProps);

            StringWriter writer = new StringWriter();
            oldProps.store(writer, "Merged by ezasse");
            return writer.toString();
        } catch (Exception e) {
            log.warn("Failed to merge properties, falling back to text append", e);
            return oldConfig + "\n" + newConfig;
        }
    }

    @SuppressWarnings("unchecked")
    private String mergeYaml(String oldConfig, String newConfig) {
        try {
            Yaml yaml = new Yaml();
            Map<String, Object> oldMap = yaml.load(oldConfig);
            Map<String, Object> newMap = yaml.load(newConfig);

            if (oldMap == null) oldMap = new LinkedHashMap<>();
            if (newMap == null) newMap = new LinkedHashMap<>();

            Map<String, Object> mergedMap = deepMergeMaps(oldMap, newMap);

            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            options.setPrettyFlow(true);
            Yaml dumper = new Yaml(options);
            return dumper.dump(mergedMap);
        } catch (Exception e) {
            log.warn("Failed to merge YAML, falling back to text append", e);
            return oldConfig + "\n" + newConfig;
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> deepMergeMaps(Map<String, Object> original, Map<String, Object> newMap) {
        for (Map.Entry<String, Object> entry : newMap.entrySet()) {
            String key = entry.getKey();
            Object newValue = entry.getValue();

            if (original.containsKey(key)) {
                Object originalValue = original.get(key);
                if (originalValue instanceof Map && newValue instanceof Map) {
                    deepMergeMaps((Map<String, Object>) originalValue, (Map<String, Object>) newValue);
                } else {
                    original.put(key, newValue);
                }
            } else {
                original.put(key, newValue);
            }
        }
        return original;
    }

    @Override
    public String getId() {
        return EzasseForNacosConstant.ACTION_MERGE_TO;
    }
}
