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
import static cn.com.pism.ezasse.constants.EzasseConstants.SQL_EXTENSION;
import static cn.com.pism.ezasse.util.EzasseUtil.getResourcesFromFolder;


/**
 * 读取文件 并将文件解析为EzasseSql
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

    protected EzasseFile resourceToEzasseFile(Resource resource) {


        //构建基础文件对象，使用URI转换为字符串作为绝对路径
        EzasseFile ezasseFile = new EzasseFile(resource.getURI().toString());

        //获取相对路径，ClassGraph默认以正斜杠作为路径分隔符
        String relativePath = resource.getPath();

        //提取纯文件名称，截取最后一个斜杠之后的内容
        String filename = relativePath.substring(relativePath.lastIndexOf('/') + 1);
        ezasseFile.setName(filename);

        //按照文件约定对纯文件名进行拆分
        String[] nameSplit = filename.split(MINUS_SIGN);

        //分组 按【-】拆分的第一个元素为分组，需要去掉文件后缀
        ezasseFile.setGroup(nameSplit[0].replace(SQL_EXTENSION, ""));

        //对文件名的各部分进行推断
        Arrays.asList(nameSplit).forEach(split -> {

            //清理当前分段的后缀，避免位于文件末尾的属性推断失败
            String cleanSplit = split.replace(SQL_EXTENSION, "");

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


}
