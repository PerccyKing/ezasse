package cn.com.pism.ezasse.action;

import lombok.Data;

/**
 * @author PerccyKing
 * @since 24-12-29 17:06
 */
@Data
public class DbCheckActionParam implements EzasseExecutorActionParam{

    private String checkContent;
}
