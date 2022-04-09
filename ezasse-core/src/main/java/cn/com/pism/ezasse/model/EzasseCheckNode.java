package cn.com.pism.ezasse.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.util.Map;

import static cn.com.pism.ezasse.Ezasse.MASTER_ID;
import static cn.com.pism.ezasse.EzasseConstants.*;

/**
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
        int startIndex = checkLine.indexOf("(");
        int endIndex = checkLine.lastIndexOf(")");
        String checkKeyStr = checkLine.substring(0, startIndex);
        String checkContentStr = checkLine.substring(startIndex + 1, endIndex);
        String[] checkKeySplit = checkKeyStr.split("\\.");
        String checkNodeKey = "";
        String executeNode = "";
        if (checkKeySplit.length >= TWO) {
            //最后两位为 分别为校验节点和 执行节点
            if (checkKeySplit.length == FOUR) {
                checkKeyStr = checkKeySplit[0] + "." + checkKeySplit[1];
                checkNodeKey = checkKeySplit[2];
            } else if (checkKeySplit.length == THREE) {
                //如果第二位不是数据节点，那第二位就是关键字
                if (dataSourceMap.get(checkKeySplit[ONE]) == null) {
                    checkKeyStr = checkKeySplit[0] + "." + checkKeySplit[1];
                    checkNodeKey = checkKeySplit[2];
                } else {
                    checkKeyStr = checkKeySplit[0];
                    checkNodeKey = checkKeySplit[1];
                }
            } else if (checkKeySplit.length == TWO) {
                if (dataSourceMap.get(checkKeySplit[ONE]) == null) {
                    checkKeyStr = checkKeySplit[0] + "." + checkKeySplit[1];
                    checkNodeKey = MASTER_ID;
                } else {
                    checkKeyStr = checkKeySplit[0];
                    checkNodeKey = checkKeySplit[1];
                }
            }
        }
        if (StringUtils.isBlank(checkNodeKey)) {
            checkNodeKey = ezasseSql.getNode();
        }
        if (StringUtils.isBlank(checkNodeKey)) {
            checkNodeKey = MASTER_ID;
        }
        this.checkNode = dataSourceMap.get(checkNodeKey);
        this.execNode = dataSourceMap.get(executeNode);
        this.checkKey = checkKeyStr;
        this.checkContent = checkContentStr;
    }
}
