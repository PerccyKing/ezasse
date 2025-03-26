package cn.com.pism.ezasse.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/10 下午 11:09
 */
@ConfigurationProperties(prefix = "spring.ezasse")
@Component
public class EzasseProperties {
    /**
     * 文件夹，会扫描当前文件夹下所有的sql文件,文件夹最好有三位顺序标记，没有标记将按照默认排序执行
     */
    private List<String> folders;

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
        public static class Table {
            /**
             * 创建表关键字
             */
            private String createTable = "TABLE";

            public String getCreateTable() {
                return createTable;
            }

            public void setCreateTable(String createTable) {
                this.createTable = createTable;
            }
        }

        /**
         * <p>
         * 表字段相关
         * </p>
         *
         * @author PerccyKing
         * @since 2022/04/20 下午 11:51
         */
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

            public String getAdd() {
                return add;
            }

            public void setAdd(String add) {
                this.add = add;
            }

            public String getChangeName() {
                return changeName;
            }

            public void setChangeName(String changeName) {
                this.changeName = changeName;
            }

            public String getChangeType() {
                return changeType;
            }

            public void setChangeType(String changeType) {
                this.changeType = changeType;
            }

            public String getChangeLength() {
                return changeLength;
            }

            public void setChangeLength(String changeLength) {
                this.changeLength = changeLength;
            }

            public String getChangeComment() {
                return changeComment;
            }

            public void setChangeComment(String changeComment) {
                this.changeComment = changeComment;
            }
        }

        public String getExec() {
            return exec;
        }

        public void setExec(String exec) {
            this.exec = exec;
        }

        public Table getTable() {
            return table;
        }

        public void setTable(Table table) {
            this.table = table;
        }

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }
    }


    public List<String> getFolders() {
        if (CollectionUtils.isEmpty(folders)) {
            return Collections.singletonList("sql");
        }
        return folders;
    }

    public void setFolders(List<String> folders) {
        this.folders = folders;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }

    public List<String> getGroupOrder() {
        return groupOrder;
    }

    public void setGroupOrder(List<String> groupOrder) {
        this.groupOrder = groupOrder;
    }

    public String getDelimiterStart() {
        return delimiterStart;
    }

    public void setDelimiterStart(String delimiterStart) {
        this.delimiterStart = delimiterStart;
    }

    public String getDelimiterEnd() {
        return delimiterEnd;
    }

    public void setDelimiterEnd(String delimiterEnd) {
        this.delimiterEnd = delimiterEnd;
    }

    public EzasseKeyWords getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(EzasseKeyWords keyWords) {
        this.keyWords = keyWords;
    }
}
