package cn.com.pism.ezasse.model;

import lombok.Data;

/**
 * @author PerccyKing
 * @version 0.0.1
 * @since 2022/04/20 下午 11:44
 */
@Data
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
