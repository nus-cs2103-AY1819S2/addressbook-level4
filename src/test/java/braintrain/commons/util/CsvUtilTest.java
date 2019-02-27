package braintrain.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import braintrain.testutil.Assert;
import braintrain.testutil.TestUtil;

public class CsvUtilTest {
    public static final String[] TEST_STRINGS = new String[]{"ab", "bc", "cd", "de"};

    @Test
    public void readCsvFile_nullPath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> {
            CsvUtil.readCsvFile(null);
        });
    }

    @Test
    public void readCsvFile_invalidPath() {
        Path path = Paths.get("");
        assertEquals(null, CsvUtil.readCsvFile(path));
        path = Paths.get("doesnotexist");
        assertEquals(null, CsvUtil.readCsvFile(path));
    }

    @Test
    public void readCsvFile_invalidFile() {
        Path path = Paths.get("src/test/data/empty.bmp");
        assertEquals(null, CsvUtil.readCsvFile(path));
    }

    @Test
    public void readCsvFile() throws IOException {
        Path path = Paths.get("src/test/data/test.csv");
        List<String[]> data = CsvUtil.readCsvFile(path);
        String[] testData = TEST_STRINGS;

        //Extra handling of test data for UTF-8 BOM
        if (testData[0].startsWith("\uFEFF")) {
            testData[0] = testData[0].substring(1);
        }

        for (int i = 0; i < testData.length; i++) {
            String value = testData[i];
            for (String str : data.get(i)) {
                assertEquals(value, str);
            }
        }

    }

    @Test
    public void writeCsvFile() {
        Path path = TestUtil.getFilePathInSandboxFolder("test_write.csv");
        List<String[]> data = Arrays.asList(TEST_STRINGS, TEST_STRINGS, TEST_STRINGS);

        assertTrue(CsvUtil.writeCsvFile(path, data));
        data = CsvUtil.readCsvFile(path);
        assertNotNull(data);
    }

    @Test
    public void writeCsvFile_emptyData() {
        Path path = TestUtil.getFilePathInSandboxFolder("test_write_empty.csv");
        List<String[]> data = new ArrayList<>();

        assertFalse(CsvUtil.writeCsvFile(path, data));
    }

    @Test
    public void writeCsvFile_invalidFile() {
        Path path = Paths.get("src/test/data/test-readonly.csv");
        File file = path.toFile();
        file.setReadOnly();
        List<String[]> data = Arrays.asList(TEST_STRINGS, TEST_STRINGS, TEST_STRINGS);

        assertFalse(CsvUtil.writeCsvFile(path, data));
    }
}
