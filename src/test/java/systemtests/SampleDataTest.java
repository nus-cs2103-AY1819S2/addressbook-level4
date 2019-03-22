package systemtests;

import static seedu.travel.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

import seedu.travel.model.TravelBuddy;
import seedu.travel.model.place.Place;
import seedu.travel.model.util.SampleDataUtil;
import seedu.travel.testutil.TestUtil;

public class SampleDataTest extends TravelBuddySystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected TravelBuddy getInitialData() {
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
    public void travelBuddy_dataFileDoesNotExist_loadSampleData() {
        Place[] expectedList = SampleDataUtil.getSamplePlaces();
        assertListMatching(getPlaceListPanel(), expectedList);
    }
}
