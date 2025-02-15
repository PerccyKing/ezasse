package cn.com.pism.ezasse.jdbc.action.param;

import cn.com.pism.ezasse.model.ActionParam;
import lombok.Data;

/**
 * @author PerccyKing
 * @since 24-12-29 17:06
 */
@Data
public class DefaultCheckActionParam implements ActionParam {

    private String checkContent;
}
