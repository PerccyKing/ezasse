package cn.com.pism.ezasse.action.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author PerccyKing
 * @since 25-01-01 22:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoExecuteParam implements ActionParam {

    /**
     * 执行内容
     */
    private String executeContent;
}
