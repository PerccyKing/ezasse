package cn.com.pism.ezasse.resource;

import cn.com.pism.ezasse.model.EzasseFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author PerccyKing
 * @since 24-10-24 22:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EzasseFileResource implements EzasseResource {

    private List<EzasseFile> files;

}
