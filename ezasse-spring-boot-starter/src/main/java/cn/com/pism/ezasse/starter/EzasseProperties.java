package cn.com.pism.ezasse.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/10 下午 11:09
 
 */
@ConfigurationProperties(prefix = "spring.ezasse")
@Data
@Component
public class EzasseProperties {
    /**
     * 文件夹，会扫描当前文件夹下所有的sql文件,文件夹最好有三位顺序标记，没有标记将按照默认排序执行
     */
    private String folder = "sql";

    /**
     * 文件列表,未指定，将校验全部的sql文件，可以是sql文件的完整名称，也可以是文件的分组名称
     */
    private List<String> fileList;

    /**
     * 分组顺序，如果存在多个分组，需要指定分组执行顺序
     */
    private List<String> groupOrder;

    /**
     * 开始限定符
     */
    private String delimiterStart;

    /**
     * 结束限定符
     */
    private String delimiterEnd;

    /**
     * 关键字
     */
    private EzasseKeyWords keyWords = new EzasseKeyWords();

    @Data
    public static class EzasseKeyWords {

        /**
         * 默认校验关键字 校验语句，返回0时，执行SQL
         */
        private String exec = "EXEC";

        /**
         * 表操作关键字
         */
        private Table table = new Table();

        /**
         * 表字段操作关键字
         */
        private Field field = new Field();

        /**
         * <p>
         * 表操作相关
         * </p>
         *
         * @author PerccyKing
         * @since 2022/04/20 下午 11:50
         */
        @Data
        public static class Table {
            /**
             * 创建表关键字
             */
            private String createTable = "TABLE";
        }

        /**
         * <p>
         * 表字段相关
         * </p>
         *
         * @author PerccyKing
         * @since 2022/04/20 下午 11:51
         */
        @Data
        public static class Field {
            /**
             * 添加字段
             */
            private String add = "ADD";

            /**
             * 修改字段名称
             */
            private String changeName = "CHANGE_NAME";

            /**
             * 修改字段类型
             */
            private String changeType = "CHANGE_TYPE";

            /**
             * 修改字段长度
             */
            private String changeLength = "CHANGE_LENGTH";

            /**
             * 修改字段备注
             */
            private String changeComment = "CHANGE_COMMENT";
        }
    }
}
