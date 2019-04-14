package systemtests;

import static seedu.address.ui.testutil.GuiTestAssert.assertHealthWorkerListMatching;
import static seedu.address.ui.testutil.GuiTestAssert.assertRequestListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

import seedu.address.model.HealthWorkerBook;
import seedu.address.model.RequestBook;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.request.Request;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.TestUtil;

public class SampleDataTest extends HealthHubSystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getHealthWorkerDataFileLocation()}.
     */
    @Override
    protected HealthWorkerBook getInitialHealthWorkerData() {
        return null;
    }

    /**
     * Returns null to force test app to load data of the file in {@code getRequestDataFileLocation()}.
     */
    @Override
    protected RequestBook getInitialRequestData() {
        return null;
    }

    /**
     * Returns a non-existent file location to force test app to load some sample data.
     */
    @Override
    protected Path getHealthWorkerDataFileLocation() {
        Path filePath = TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
        deleteFileIfExists(filePath);
        return filePath;
    }

    /**
     * Returns a non-existent file location to force test app to load some sample data.
     */
    @Override
    protected Path getRequestDataFileLocation() {
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
    public void healthWorkerBook_dataFileDoesNotExist_loadSampleData() {
        HealthWorker[] expectedList = SampleDataUtil.getSampleHealthWorkers();
        assertHealthWorkerListMatching(getHealthWorkerListPanel(), expectedList);
    }

    @Test
    public void requestBook_dataFileDoesNotExist_loadSampleData() {
        Request[] expectedList = SampleDataUtil.getSampleRequests();
        assertRequestListMatching(getRequestListPanel(), expectedList);
    }
}

