package cn.com.pism.ezasse.loader;

import cn.com.pism.ezasse.context.EzasseContext;
import cn.com.pism.ezasse.model.EzasseConfig;
import cn.com.pism.ezasse.model.EzasseFile;
import cn.com.pism.ezasse.resource.EzasseFileResource;
import io.github.classgraph.Resource;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static cn.com.pism.ezasse.constants.EzasseConstants.MINUS_SIGN;
import static cn.com.pism.ezasse.util.EzasseUtil.getResourcesFromFolder;


/**
 * 读取文件并将文件解析为 {@link EzasseFile}
 * <p>
 * 支持加载任意扩展名的文件（如 .sql、.yaml、.properties、.json 等），
 * 文件名解析时会自动识别并去除扩展名。
 * </p>
 *
 * @author PerccyKing
 * @since 24-10-27 01:18
 */
public class EzasseFileResourceLoader implements EzasseResourceLoader<EzasseFileResource> {

    private final EzasseContext ezasseContext;


    public EzasseFileResourceLoader(EzasseContext ezasseContext) {
        this.ezasseContext = ezasseContext;
    }

    @Override
    public EzasseFileResource load() {
        //配置
        EzasseConfig config = ezasseContext.configManager().getConfig();

        List<EzasseFile> ezasseFiles = new ArrayList<>();

        //脚本文件位置
        List<String> folders = config.getFolders();

        folders.forEach(folder -> {

            //从配置的目录下获取所有资源
            Collection<Resource> resources = getResourcesFromFolder(folder);

            resources.forEach(resource -> ezasseFiles.add(resourceToEzasseFile(resource)));

        });

        return new EzasseFileResource(ezasseFiles);
    }

    /**
     * 将 ClassGraph 资源转换为 EzasseFile 对象
     * <p>
     * 按照文件命名约定解析文件名的各个部分（分组、排序、数据源节点），
     * 支持任意扩展名的文件。
     * </p>
     *
     * @param resource ClassGraph 扫描到的资源
     * @return 解析后的 EzasseFile 对象
     */
    protected EzasseFile resourceToEzasseFile(Resource resource) {

        //构建基础文件对象，使用URI转换为字符串作为绝对路径
        EzasseFile ezasseFile = new EzasseFile(resource.getURI().toString());

        //获取相对路径，ClassGraph默认以正斜杠作为路径分隔符
        String relativePath = resource.getPath();

        //提取纯文件名称，截取最后一个斜杠之后的内容
        String filename = relativePath.substring(relativePath.lastIndexOf('/') + 1);
        ezasseFile.setName(filename);

        // 获取文件扩展名（如 ".sql"、".yaml"），用于后续去除扩展名
        String fileExtension = getFileExtension(filename);

        //按照文件约定对纯文件名进行拆分
        String[] nameSplit = filename.split(MINUS_SIGN);

        //分组 按【-】拆分的第一个元素为分组，需要去掉文件后缀
        ezasseFile.setGroup(stripExtension(nameSplit[0], fileExtension));

        //对文件名的各部分进行推断
        Arrays.asList(nameSplit).forEach(split -> {

            //清理当前分段的后缀，避免位于文件末尾的属性推断失败
            String cleanSplit = stripExtension(split, fileExtension);

            //如果当前分段为纯数字，可以确定为排序
            if (NumberUtils.isDigits(cleanSplit)) {
                ezasseFile.setOrder(cleanSplit);
            }

            //如果分段能匹配到数据源，则认为当前分段为数据源
            if (ezasseContext.datasourceManager().getDataSource(cleanSplit) != null) {
                ezasseFile.setNode(cleanSplit);
            }
        });

        return ezasseFile;
    }

    /**
     * 获取文件扩展名（包含 "."）
     * <p>
     * 例如：{@code "config.yaml"} 返回 {@code ".yaml"}，
     * {@code "script"} 返回 {@code ""}
     * </p>
     *
     * @param filename 文件名
     * @return 文件扩展名（包含 "."），如果没有扩展名则返回空字符串
     */
    private String getFileExtension(String filename) {
        int lastDot = filename.lastIndexOf('.');
        if (lastDot > 0) {
            return filename.substring(lastDot);
        }
        return "";
    }

    /**
     * 从字符串中移除指定的文件扩展名
     *
     * @param str       原始字符串
     * @param extension 要移除的扩展名（包含 "."）
     * @return 移除扩展名后的字符串
     */
    private String stripExtension(String str, String extension) {
        if (extension.isEmpty()) {
            return str;
        }
        if (str.endsWith(extension)) {
            return str.substring(0, str.length() - extension.length());
        }
        return str;
    }

}
