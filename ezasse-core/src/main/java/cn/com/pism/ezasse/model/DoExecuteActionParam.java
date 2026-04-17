package cn.com.pism.ezasse.model;

import cn.com.pism.ezasse.checker.EzasseCheckLineContent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 执行内容参数
 *
 * @author PerccyKing
 * @since 25-01-01 22:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoExecuteActionParam implements ActionParam {

    /**
     * 校验行和内容
     */
    private EzasseCheckLineContent checkLineContent;

    public String getExecuteContent() {
        return checkLineContent.getExecuteScript();
    }
}
