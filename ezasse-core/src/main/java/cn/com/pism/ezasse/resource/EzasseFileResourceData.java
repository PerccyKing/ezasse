package cn.com.pism.ezasse.resource;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PerccyKing
 * @since 24-11-02 01:39
 */
@Data
public class EzasseFileResourceData implements EzasseResourceData {

    private List<ResourceData> fileData = new ArrayList<>();

    public void addFileData(ResourceData resourceData) {
        fileData.add(resourceData);
    }

    @Data
    public static class ResourceData {
        /**
         * 文件数据
         */
        private List<String> data;

        /**
         * 检查行
         */
        private String checkLine;

        /**
         * 内容
         */
        private String content;
    }

}
