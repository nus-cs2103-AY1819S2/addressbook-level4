package braintrain.commons.util;


import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import braintrain.testutil.Assert;

public class CsvUtilTest {
    public static final String[] TEST_STRINGS = new String[]{"ab","bc","cd","de"};

    @Test
    public void readCsvFile_nullPath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> {
            CsvUtil.readCsvFile(null);
        });
    }

    @Test
    public void readCsvFile_invalidPath_throwsIOException() throws IOException {
        Path path = Paths.get("");
        assertEquals(null, CsvUtil.readCsvFile(path));
        path = Paths.get("doesnotexist");
        assertEquals(null, CsvUtil.readCsvFile(path));
    }

    @Test
    public void readCsvFile_invalidFile() throws IOException {
        Path path = Paths.get("src/test/data/empty.bmp");
        assertEquals(null, CsvUtil.readCsvFile(path));
    }

    @Test
    public void readCsvFile() throws IOException {
        Path path = Paths.get("src/test/data/test.csv");
        List<String[]> data = CsvUtil.readCsvFile(path);

        //Extra handling of first line for UTF-8 BOM.
        String[] firstLine = data.get(0);
        if(firstLine[0].startsWith("\uFEFF")) {
            firstLine[0] = firstLine[0].substring(1);
        }
        data.set(0, firstLine);

        for (int i = 0; i < TEST_STRINGS.length; i++) {
            String value = TEST_STRINGS[i];
            for (String str: data.get(i)) {
                assertEquals(value, str);
            }
        }

    }

}
