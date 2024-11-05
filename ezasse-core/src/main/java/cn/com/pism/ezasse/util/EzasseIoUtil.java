package cn.com.pism.ezasse.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author PerccyKing
 * @since 2023/12/1 10:39
 */
public class EzasseIoUtil {

    private EzasseIoUtil() {
    }

    public static List<String> readLines(InputStream in) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            return Collections.emptyList();
        }
        return lines;
    }

}