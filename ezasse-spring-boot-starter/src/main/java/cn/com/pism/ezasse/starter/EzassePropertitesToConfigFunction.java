package cn.com.pism.ezasse.starter;

import cn.com.pism.ezasse.model.EzasseConfig;
import cn.com.pism.ezasse.model.EzasseKeyWords;

import java.util.function.Function;

/**
 * @author wangyihuai@meb.com
 * @since 2023/12/1 15:51
 */
public class EzassePropertitesToConfigFunction implements Function<EzasseProperties, EzasseConfig> {
    @Override
    public EzasseConfig apply(EzasseProperties ezasseProperties) {
        EzasseConfig config = new EzasseConfig();
        config.setFolder(ezasseProperties.getFolder());
        config.setFileList(ezasseProperties.getFileList());
        config.setGroupOrder(ezasseProperties.getGroupOrder());
        config.setDelimiterStart(ezasseProperties.getDelimiterStart());
        config.setDelimiterEnd(ezasseProperties.getDelimiterEnd());
        EzasseProperties.EzasseKeyWords keyWords = ezasseProperties.getKeyWords();
        EzasseKeyWords ezasseKeyWords = buildKeyWords(keyWords);
        config.setKeyWords(ezasseKeyWords);
        return config;
    }

    private EzasseKeyWords buildKeyWords(EzasseProperties.EzasseKeyWords keyWords) {
        EzasseKeyWords ezasseKeyWords = new EzasseKeyWords();
        ezasseKeyWords.setExec(keyWords.getExec());

        EzasseProperties.EzasseKeyWords.Field field = keyWords.getField();
        EzasseKeyWords.Field wField = new EzasseKeyWords.Field();
        wField.setAdd(field.getAdd());
        wField.setChangeName(field.getChangeName());
        wField.setChangeType(field.getChangeType());
        wField.setChangeLength(field.getChangeLength());
        wField.setChangeComment(field.getChangeComment());
        ezasseKeyWords.setField(wField);

        EzasseProperties.EzasseKeyWords.Table table = keyWords.getTable();
        EzasseKeyWords.Table wTable = new EzasseKeyWords.Table();
        wTable.setCreateTable(table.getCreateTable());
        ezasseKeyWords.setTable(wTable);
        return ezasseKeyWords;
    }
}
