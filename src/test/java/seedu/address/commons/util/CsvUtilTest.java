package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.Assert;
import seedu.address.testutil.TestUtil;

public class CsvUtilTest {
    public static final String[] TEST_STRINGS = new String[]{"ab", "bc", "cd", "de"};

    private static final Path INVALID_FILE = Paths.get("src", "test", "data", "CsvUtilTest",
        "empty.bmp");
    private static final Path TEST_FILE = Paths.get("src", "test", "data", "CsvUtilTest",
        "test.csv");
    private static final Path READ_ONLY_FILE = Paths.get("src", "test", "data", "CsvUtilTest",
        "test-readonly.csv");
    private static final Path BOM_EMPTY_FILE = Paths.get("src", "test", "data", "CsvUtilTest",
        "test-bomempty.csv");

    @Test
    public void readCsvFile_nullPath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> {
            CsvUtil.readCsvFile(null);
        });
    }

    // I can't guarantee the path is access denied since it might be OS dependent, so..
    /*
    @Test
    public void readCsvFile_accessDeniedPath_throwsAccessDeniedException() {
        final Path emptyPath = Paths.get("");
        Assert.assertThrows(AccessDeniedException.class, () -> {
            CsvUtil.readCsvFile(emptyPath);
        });
    }
    */

    @Test
    public void readCsvFile_nonExistentPath() throws IOException {
        final Path fakePath = Paths.get("doesnotexist");
        assertNull(CsvUtil.readCsvFile(fakePath));
    }

    @Test
    public void readCsvFile_invalidFile() throws IOException {
        Path path = INVALID_FILE;
        assertEquals(null, CsvUtil.readCsvFile(path));
    }

    @Test
    public void readCsvFile_emptyBomFile() throws IOException {
        Path path = BOM_EMPTY_FILE;
        assertEquals(null, CsvUtil.readCsvFile(path));
    }

    @Test
    public void readCsvFile() throws IOException {
        Path path = TEST_FILE;
        List<String[]> data = CsvUtil.readCsvFile(path);
        String[] testData = TEST_STRINGS;

        for (int i = 0; i < testData.length; i++) {
            String value = testData[i];
            for (String str : data.get(i)) {
                assertEquals(value, str);
            }
        }
    }

    @Test
    public void writeCsvFile() throws IOException {
        Path path = TestUtil.getFilePathInSandboxFolder("test_write.csv");
        List<String[]> data = Arrays.asList(TEST_STRINGS, TEST_STRINGS, TEST_STRINGS);

        assertTrue(CsvUtil.writeCsvFile(path, data));
        data = CsvUtil.readCsvFile(path);
        assertNotNull(data);
    }

    @Test
    public void writeCsvFile_emptyData() throws IOException {
        Path path = TestUtil.getFilePathInSandboxFolder("test_write_empty.csv");
        List<String[]> data = new ArrayList<>();

        assertFalse(CsvUtil.writeCsvFile(path, data));
    }

    @Test
    public void writeCsvFile_invalidFile() {
        Path path = READ_ONLY_FILE;
        File file = path.toFile();
        file.setReadOnly();
        List<String[]> data = Arrays.asList(TEST_STRINGS, TEST_STRINGS, TEST_STRINGS);

        Assert.assertThrows(AccessDeniedException.class, () -> {
            CsvUtil.writeCsvFile(path, data);
        });
    }
}
