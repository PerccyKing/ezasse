package cn.com.pism.ezasse.model;

import cn.com.pism.ezasse.exception.EzasseException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.util.Map;

import static cn.com.pism.ezasse.Ezasse.MASTER_ID;
import static cn.com.pism.ezasse.constants.EzasseConstants.LEFT_BRACKET;
import static cn.com.pism.ezasse.constants.EzasseConstants.RIGHT_BRACKET;
import static cn.com.pism.ezasse.enums.EzasseExceptionCode.SYNTAX_ERROR_EXCEPTION;

/**
 * 关键字[_次关键字].[校验节点].[执行节点]
 * EXEC()
 * EXEC.slave()
 * EXEC.slave.master()
 * CHANGE_ADD()
 * CHANGE_ADD.master()
 * CHANGE_ADD.master.slave()
 *
 * @author PerccyKing
 * @version 0.0.1
 * @date 2022/04/07 下午 04:23
 * @since 0.0.1
 */
@Data
public class EzasseCheckNode {
    /**
     * 校验节点
     */
    private DataSource checkNode;

    /**
     * 执行节点
     */
    private DataSource execNode;
    /**
     * 关键字
     */
    private String checkKey;

    /**
     * 校验内容
     */
    private String checkContent;

    public EzasseCheckNode(String checkLine, EzasseSql ezasseSql, Map<String, DataSource> dataSourceMap) {
        int startIndex = checkLine.indexOf(LEFT_BRACKET);
        int endIndex = checkLine.lastIndexOf(RIGHT_BRACKET);
        //获取校验字符串
        String checkKeyStr = checkLine.substring(0, startIndex);
        //校验内容
        this.checkContent = checkLine.substring(startIndex + 1, endIndex);
        //按.分割
        String[] checkKeySplit = checkKeyStr.split("\\.");
        int checkLength = checkKeySplit.length;
        //默认执行节点
        String defaultExecNode = ezasseSql.getNode();
        //如果未指定执行节点，默认为master节点
        if (StringUtils.isBlank(defaultExecNode)) {
            defaultExecNode = MASTER_ID;
        }
        switch (checkLength) {
            case 1:
                //校验关键字的长度为1，未指定执行节点和校验节点
                this.checkKey = checkKeySplit[0];
                this.checkNode = dataSourceMap.get(defaultExecNode);
                this.execNode = dataSourceMap.get(defaultExecNode);
                break;
            case 2:
                //校验关键字的长度为2，指定执行节点未指定校验节点
                this.checkKey = checkKeySplit[0];
                this.checkNode = dataSourceMap.get(checkKeySplit[1]);
                this.execNode = dataSourceMap.get(defaultExecNode);
                break;
            case 3:
                //校验关键字的长度为2，指定执行节点和校验节点
                this.checkKey = checkKeySplit[0];
                this.checkNode = dataSourceMap.get(checkKeySplit[1]);
                this.execNode = dataSourceMap.get(checkKeySplit[2]);
                break;
            default:
                throw new EzasseException(SYNTAX_ERROR_EXCEPTION);
        }

    }
}
