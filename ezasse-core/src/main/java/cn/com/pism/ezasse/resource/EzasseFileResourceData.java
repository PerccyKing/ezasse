package cn.com.pism.ezasse.resource;

import cn.com.pism.ezasse.model.EzasseCheckLineContent;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PerccyKing
 * @since 24-11-02 01:39
 */
@Data
public class EzasseFileResourceData implements EzasseResourceData {

    private List<ResourceData> fileDataList = new ArrayList<>();

    public void addFileData(ResourceData resourceData) {
        if (resourceData == null) {
            return;
        }
        fileDataList.add(resourceData);
    }

    @Data
    public static class ResourceData {
        /**
         * 文件数据
         */
        private List<EzasseFileLine> fileLines = new ArrayList<>();

        /**
         * 所有的校验行和内容
         */
        private List<EzasseCheckLineContent> checkLineContents = new ArrayList<>();

        public void addFileLine(EzasseFileLine fileLine) {
            fileLines.add(fileLine);
        }

        public void addCheckLineContent(EzasseCheckLineContent checkLineContent) {
            checkLineContents.add(checkLineContent);
        }
    }

}
