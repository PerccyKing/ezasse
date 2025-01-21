package cn.com.pism.ezasse.action.param;

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
     * 执行内容
     */
    private String executeContent;
}
