package cn.com.pism.ezasse;

import cn.com.pism.ezasse.context.EzasseContext;
import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.loader.EzasseFileResourceLoader;
import cn.com.pism.ezasse.model.EzasseChecker;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseExecutor;
import cn.com.pism.ezasse.resource.EzasseFileResource;
import cn.com.pism.ezasse.resource.EzasseFileResourceData;
import cn.com.pism.ezasse.resource.EzasseFileResourceParser;

/**
 * @author PerccyKing
 * @since 24-10-27 00:46
 */
public class FileEzasse extends AbstractEzasse {
    public FileEzasse() {
        super(EzasseFileResource.class);
    }

    @Override
    protected void preProcess() {
        EzasseContext context = EzasseContextHolder.getContext();

        //注册资源加载器
        context
                .getResourceLoaderFactory()
                .registerResourceLoader(EzasseFileResource.class, new EzasseFileResourceLoader());

        //注册资源解析器
        context
                .getResourceParserFactory()
                .registerResourceParser(EzasseFileResource.class, EzasseFileResourceParser.class);
    }

    @Override
    protected void doExecute() {

        // context
        EzasseContext context = EzasseContextHolder.getContext();

        // 解析到的数据
        EzasseFileResourceData ezasseFileResourceData = (EzasseFileResourceData) context.getEzasseResource(EzasseFileResource.class);
        ezasseFileResourceData.getFileDataList().forEach(resourceData ->
                resourceData.getCheckLineContents().forEach(checkLineContent -> {
                    String checkNode = checkLineContent.getCheckLine().getCheckNode();
                    // 获取校验节点使用的数据源
                    EzasseDataSource dataSource = context.getDataSource(checkNode);
                    // 通过校验行获取校验器
                    EzasseChecker ezasseChecker = context.getChecker(checkLineContent.getCheckLine().getCheckKey());
                    // 校验
                    boolean check = ezasseChecker.check(dataSource, checkLineContent.getCheckLine().getCheckContent());
                    // 判断校验结果
                    if (check) {
                        // 如果校验通过，获取执行节点使用的数据源
                        EzasseDataSource executeDataSource = context.getDataSource(checkLineContent.getCheckLine().getExecuteNode());
                        EzasseExecutor executor = context.getExecutor(executeDataSource);
                        executor.execute(checkLineContent.getExecuteContent());
                    }
                }));
    }
}
