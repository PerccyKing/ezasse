package cn.com.pism.ezasse;

import cn.com.pism.ezasse.action.param.DoExecuteActionParam;
import cn.com.pism.ezasse.model.EzasseChecker;
import cn.com.pism.ezasse.context.EzasseContext;
import cn.com.pism.ezasse.context.EzasseContextHolder;
import cn.com.pism.ezasse.executor.EzasseExecutor;
import cn.com.pism.ezasse.loader.EzasseFileResourceLoader;
import cn.com.pism.ezasse.model.EzasseCheckLineContent;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.resource.EzasseFileResource;
import cn.com.pism.ezasse.resource.EzasseFileResourceData;
import cn.com.pism.ezasse.resource.EzasseFileResourceParser;
import org.apache.commons.lang3.StringUtils;

import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.DO_EXECUTE;

/**
 * @author PerccyKing
 * @since 24-10-27 00:46
 */
public class FileEzasse extends AbstractEzasse {

    public FileEzasse() {
        super(EzasseFileResource.class);

        EzasseContext context = EzasseContextHolder.getContext();

        //注册资源加载器
        context.resourceLoaderManager()
                .registerResourceLoader(EzasseFileResource.class, new EzasseFileResourceLoader());

        //注册资源解析器
        context.resourceParserManger()
                .registerResourceParser(EzasseFileResource.class, new EzasseFileResourceParser());
    }


    @Override
    protected void doExecute() {

        // 解析到的数据
        getEzasseResource().getFileDataList().forEach(this::executeResourceData);
    }

    protected void executeResourceData(EzasseFileResourceData.ResourceData resourceData) {

        resourceData
                .getCheckLineContents()
                .stream()
                // 过滤掉执行内容为空的数据
                .filter(checkLineContent -> {
                    if (EzasseContextHolder.getContext().checkerManager()
                            .getAllowEmptyCheckerKeys()
                            .contains(checkLineContent.getCheckLine().getCheckKey())) {
                        return true;
                    }
                    return StringUtils.isNotBlank(checkLineContent.getExecuteScript());
                })
                .forEach(this::checkAndExecute);
    }

    private void checkAndExecute(EzasseCheckLineContent checkLineContent) {
        // 校验
        boolean checkResult = doCheck(checkLineContent);
        // 判断校验结果
        if (checkResult) {
            // 如果校验通过，获取执行节点使用的数据源
            EzasseExecutor executor = EzasseContextHolder
                    .getContext()
                    .executorManager()
                    .getExecutor(checkLineContent.getCheckLine().getExecuteNode());

            executor.execute(DO_EXECUTE, new DoExecuteActionParam(checkLineContent.getExecuteScript()));
        }
    }

    protected boolean doCheck(EzasseCheckLineContent checkLineContent) {

        // 获取校验节点
        String checkNode = checkLineContent.getCheckLine().getCheckNode();

        // 通过校验行获取校验器
        EzasseChecker ezasseChecker = EzasseContextHolder
                .getContext()
                .checkerManager()
                .getChecker(checkLineContent.getCheckLine().getCheckKey());

        if (StringUtils.isBlank(checkLineContent.getCheckLine().getCheckContent())) {
            return false;
        }

        // 获取校验节点使用的数据源
        EzasseDataSource dataSource = EzasseContextHolder.getContext().datasourceManager().getDataSource(checkNode);

        // 校验
        return ezasseChecker.check(dataSource, checkLineContent);
    }

    protected EzasseFileResourceData getEzasseResource() {
        return (EzasseFileResourceData) EzasseContextHolder
                .getContext()
                .resourceManger()
                .getEzasseResource(EzasseFileResource.class);
    }
}
