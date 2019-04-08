package systemtests;

import static seedu.finance.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

import seedu.finance.model.FinanceTracker;
import seedu.finance.model.record.Record;
import seedu.finance.model.util.SampleDataUtil;
import seedu.finance.testutil.TestUtil;

public class SampleDataTest extends FinanceTrackerSystemTest {

    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */

    @Override
    protected FinanceTracker getInitialData() {
        return null;
    }


    /**
     * Returns a non-existent file location to force test app to load sample data.
     */

    @Override
    protected Path getDataFileLocation() {
        Path filePath = TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
        deleteFileIfExists(filePath);
        return filePath;
    }


    /**
     * Deletes the file at {@code filePath} if it exists.
     */

    private void deleteFileIfExists(Path filePath) {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException ioe) {
            throw new AssertionError(ioe);
        }
    }

    @Test
    public void financeTracker_dataFileDoesNotExist_loadSampleData() {
        Record[] expectedList = SampleDataUtil.getSampleRecords();
        assertListMatching(getRecordListPanel(), expectedList);
    }
}
