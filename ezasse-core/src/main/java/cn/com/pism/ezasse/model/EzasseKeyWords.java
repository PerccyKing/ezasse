package cn.com.pism.ezasse.model;

import lombok.Data;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/20 下午 11:44
 */
public class EzasseKeyWords {

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
