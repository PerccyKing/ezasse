package cn.com.pism.ezasse;

import cn.com.pism.ezasse.checker.EzasseCheckLineContent;
import cn.com.pism.ezasse.checker.EzasseChecker;
import cn.com.pism.ezasse.context.EzasseContext;
import cn.com.pism.ezasse.loader.EzasseFileResourceLoader;
import cn.com.pism.ezasse.model.DoExecuteActionParam;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseExecutor;
import cn.com.pism.ezasse.resource.EzasseFileResource;
import cn.com.pism.ezasse.resource.EzasseFileResourceData;
import cn.com.pism.ezasse.resource.EzasseFileResourceParser;
import cn.com.pism.ezasse.util.EzasseLogUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.DO_EXECUTE;

/**
 * Ezasse 从文件中读取 ezasse文件的实现
 * <br/>
 * 使用 {@link EzasseFileResourceLoader} 读取所有资源文件
 * <br/>
 * 使用 {@link EzasseFileResourceParser} 将资源文件解析为 {@link cn.com.pism.ezasse.resource.EzasseResourceData}
 * <br/>
 * {@code FileEzasse} 只对 {@link EzasseFileResourceData} 进行处理
 *
 * @author PerccyKing
 * @since 24-10-27 00:46
 */
public class FileEzasse extends AbstractEzasse {

    protected static final Log log = LogFactory.getLog(FileEzasse.class);

    public FileEzasse() {
        super(EzasseFileResource.class);

        fileEzasseInit(ezasseContext);
    }

    public FileEzasse(EzasseContext ezasseContext) {
        super(EzasseFileResource.class, ezasseContext);

        fileEzasseInit(ezasseContext);
    }

    private void fileEzasseInit(EzasseContext ezasseContext) {
        if (ezasseContext.resourceLoaderManager().getResourceLoader(resourceClass) == null) {
            //注册资源加载器
            ezasseContext.resourceLoaderManager()
                    .registerResourceLoader(resourceClass, new EzasseFileResourceLoader(ezasseContext));
        }

        if (ezasseContext.resourceParserManager().getResourceParser(resourceClass) == null) {
            //注册资源解析器
            ezasseContext.resourceParserManager()
                    .registerResourceParser(resourceClass, new EzasseFileResourceParser(ezasseContext));
        }
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
                .filter(checkLineContent -> {
                    // 允许执行内容为空的校验行
                    if (ezasseContext.checkerManager()
                            .getAllowEmptyCheckerKeys()
                            .contains(checkLineContent.getCheckLine().getCheckKey())) {
                        return true;
                    }
                    // 过滤掉执行内容为空的
                    return StringUtils.isNotBlank(checkLineContent.getExecuteScript());
                })
                .forEach(this::checkAndExecute);
    }

    private void checkAndExecute(EzasseCheckLineContent checkLineContent) {
        // 校验
        boolean checkResult = doCheck(checkLineContent);

        EzasseLogUtil.info(log, String.format("check passed:[%s],checkLine:[%s]", checkResult, checkLineContent.getCheckLine().getLine()));

        // 判断校验结果
        if (checkResult) {

            // 获取执行节点数据源
            EzasseDataSource executeDataSource = ezasseContext
                    .datasourceManager()
                    .getDataSource(checkLineContent.getCheckLine().getExecuteNode());

            // 如果校验通过，获取执行节点使用的数据源
            EzasseExecutor executor = ezasseContext
                    .executorManager()
                    .getExecutor(executeDataSource.getType());

            executor.execute(DO_EXECUTE, new DoExecuteActionParam(checkLineContent.getExecuteScript()), executeDataSource);
        }
    }

    protected boolean doCheck(EzasseCheckLineContent checkLineContent) {

        // 获取校验节点
        String checkNode = checkLineContent.getCheckLine().getCheckNode();

        // 通过校验行获取校验器
        EzasseChecker ezasseChecker = ezasseContext
                .checkerManager()
                .getChecker(checkLineContent.getCheckLine().getCheckKey());

        if (StringUtils.isBlank(checkLineContent.getCheckLine().getCheckContent())) {
            return false;
        }

        // 获取校验节点使用的数据源
        EzasseDataSource dataSource = ezasseContext.datasourceManager().getDataSource(checkNode);

        // 校验
        return ezasseChecker.check(dataSource, checkLineContent);
    }

    protected EzasseFileResourceData getEzasseResource() {
        return (EzasseFileResourceData) ezasseContext
                .resourceManager()
                .getEzasseResource(EzasseFileResource.class);
    }
}
