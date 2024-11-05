package cn.com.pism.ezasse.resource;

import cn.com.pism.ezasse.model.EzasseFile;
import cn.com.pism.ezasse.util.EzasseIoUtil;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.List;

/**
 * @author PerccyKing
 * @since 24-11-02 23:47
 */
public class EzasseFileResourceParser extends EzasseResourceParser {

    private final EzasseFileResource resource;

    public EzasseFileResourceParser(EzasseResource resource) {
        super(resource);
        this.resource = (EzasseFileResource) resource;
    }

    @Override
    public EzasseFileResourceData parse() {
        EzasseFileResourceData resourceData = new EzasseFileResourceData();
        List<EzasseFile> sqlFiles = resource.getSqls();
        if (!CollectionUtils.isEmpty(sqlFiles)) {
            sqlFiles.forEach(sqlFile -> {
                //读取文件内容
                List<String> strings = readFile(sqlFile.getPath());
                //对文件内容逐行解析

            });
        }
        //读取数据
        return resourceData;
    }

    public List<String> readFile(String path){
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
        return EzasseIoUtil.readLines(inputStream);
    }
}
