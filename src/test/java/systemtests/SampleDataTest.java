package systemtests;

import static seedu.hms.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

import seedu.hms.model.HotelManagementSystem;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.util.SampleDataUtil;
import seedu.hms.testutil.TestUtil;

public class SampleDataTest extends HotelManagementSystemSystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected HotelManagementSystem getInitialData() {
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
    public void hotelManagementSystem_dataFileDoesNotExist_loadSampleData() {
        Customer[] expectedList = SampleDataUtil.getSampleCustomers();
        assertListMatching(getCustomerListPanel(), expectedList);
    }
}
