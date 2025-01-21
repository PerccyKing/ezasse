package cn.com.pism.ezasse.loader;

import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.model.EzasseConfig;
import cn.com.pism.ezasse.model.EzasseFile;
import cn.com.pism.ezasse.resource.EzasseFileResource;
import cn.com.pism.frc.resourcescanner.LoadableResource;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static cn.com.pism.ezasse.constants.EzasseConstants.*;
import static cn.com.pism.ezasse.util.EzasseUtil.getResourcesFromFolder;

/**
 * 读取文件 并将文件解析为EzasseSql
 *
 * @author PerccyKing
 * @since 24-10-27 01:18
 */
public class EzasseFileResourceLoader implements EzasseResourceLoader<EzasseFileResource> {

    @Override
    public EzasseFileResource load() {
        //配置
        EzasseConfig config = EzasseContextHolder.getContext().configManger().getConfig();

        //脚本文件位置
        String folder = config.getFolder();

        //从配置的目录下获取所有资源
        Collection<LoadableResource> resources = getResourcesFromFolder(folder);

        List<EzasseFile> ezasseFiles = new ArrayList<>();

        resources.forEach(resource -> ezasseFiles.add(resourceToEzasseFile(resource)));

        return new EzasseFileResource(ezasseFiles);
    }

    protected EzasseFile resourceToEzasseFile(LoadableResource resource) {

        EzasseFile ezasseFile = new EzasseFile(resource.getAbsolutePath());

        //将路径分隔
        String[] pathSplit = resource.getRelativePath().split(BACK_SLASH);

        //文件名称 路径分隔的最后一个元素为文件的名称
        ezasseFile.setName(pathSplit[pathSplit.length - 1]);

        //按照文件约定对文件名进行拆分
        String[] nameSplit = resource.getRelativePath().split(MINUS_SIGN);

        //分组 按【-】拆分的第一个元素为分组，需要去掉文件后缀
        ezasseFile.setGroup(nameSplit[0].replace(SQL_EXTENSION, ""));

        //对文件名的各部分进行推断
        Arrays.asList(nameSplit).forEach(split -> {

            //如果当前分段为三个数字，可以确定为排序
            if (NumberUtils.isDigits(split)) {
                ezasseFile.setOrder(split);
            }

            //如果分段能匹配到数据源，则认为当前分段为数据源
            if (EzasseContextHolder.getContext().datasourceManager().getDataSource(split) != null) {
                ezasseFile.setNode(split);
            }
        });
        return ezasseFile;
    }


}
