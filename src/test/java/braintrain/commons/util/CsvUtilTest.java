package braintrain.commons.util;


import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Test;

import braintrain.testutil.Assert;

public class CsvUtilTest {
    @Test
    public void readCsvFile_nullPath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> CsvUtil.readCsvFile(null));
    }

    @Test
    public void readCsvFile_nonExistentPath_throwsIOException() {
        Path path = Paths.get("./");
        Assert.assertThrows(IOException.class, () -> CsvUtil.readCsvFile(path));
    }
}
