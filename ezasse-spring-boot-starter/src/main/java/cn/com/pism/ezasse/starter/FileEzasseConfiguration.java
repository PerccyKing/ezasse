package cn.com.pism.ezasse.starter;

import cn.com.pism.ezasse.FileEzasse;
import cn.com.pism.ezasse.context.EzasseContext;
import cn.com.pism.ezasse.loader.EzasseFileResourceLoader;
import cn.com.pism.ezasse.resource.EzasseFileResource;
import cn.com.pism.ezasse.resource.EzasseFileResourceParser;
import cn.com.pism.ezasse.util.EzasseLogUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 实例化 {@link FileEzasse}
 *
 * @author PerccyKing
 * @since 25-02-19 21:19
 */
@Configuration
public class FileEzasseConfiguration {

    protected static final Log log = LogFactory.getLog(FileEzasseConfiguration.class);

    /**
     * <p>
     * 实例化ezasse 文件资源加载器
     * </p>
     * by perccyking
     *
     * @return {@link EzasseFileResourceLoader} 文件资源加载器
     * @since 25-03-13 22:29
     */
    @Bean
    @ConditionalOnMissingBean(EzasseFileResourceLoader.class)
    public EzasseFileResourceLoader ezasseFileResourceLoader(EzasseContext ezasseContext) {
        return new EzasseFileResourceLoader(ezasseContext);
    }

    /**
     * <p>
     * 实例化ezasse 文件资源解析器
     * </p>
     * by perccyking
     *
     * @return {@link EzasseFileResourceParser} 文件资源解析器
     * @since 25-03-13 22:29
     */
    @Bean
    @ConditionalOnMissingBean(EzasseFileResourceParser.class)
    public EzasseFileResourceParser ezasseFileResourceParser(EzasseContext ezasseContext) {
        return new EzasseFileResourceParser(ezasseContext);
    }

    /**
     * <p>
     * 实例化{@code FileEzasse}
     * </p>
     * by perccyking
     *
     * @param ezasseContext            : ezasse上下文
     * @param ezasseFileResourceLoader : 文件资源加载器
     * @param ezasseFileResourceParser : 文件资源解析器
     * @return {@link FileEzasse}
     * @since 25-03-13 22:30
     */
    @Bean
    @ConditionalOnMissingBean(FileEzasse.class)
    public FileEzasse fileEzasse(EzasseContext ezasseContext,
                                 EzasseFileResourceLoader ezasseFileResourceLoader,
                                 EzasseFileResourceParser ezasseFileResourceParser,
                                 EzasseProperties ezasseProperties) {

        //注册资源加载器
        ezasseContext.resourceLoaderManager()
                .registerResourceLoader(EzasseFileResource.class, ezasseFileResourceLoader);

        //注册资源解析器
        ezasseContext.resourceParserManager()
                .registerResourceParser(EzasseFileResource.class, ezasseFileResourceParser);

        FileEzasse ezasse = new FileEzasse(ezasseContext);
        EzasseLogUtil.info(log, "ezasse has been initialized");
        if (ezasseProperties.isExecute()) {
            ezasse.execute();
        }
        return ezasse;
    }

}
