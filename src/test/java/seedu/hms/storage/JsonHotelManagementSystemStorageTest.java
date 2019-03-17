package seedu.hms.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.hms.testutil.TypicalCustomers.ALICE;
import static seedu.hms.testutil.TypicalCustomers.HOON;
import static seedu.hms.testutil.TypicalCustomers.IDA;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.hms.commons.exceptions.DataConversionException;
import seedu.hms.model.HotelManagementSystem;
import seedu.hms.model.ReadOnlyHotelManagementSystem;

public class JsonHotelManagementSystemStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
        "JsonHotelManagementSystemStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readHotelManagementSystem_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readHotelManagementSystem(null);
    }

    private java.util.Optional<ReadOnlyHotelManagementSystem> readHotelManagementSystem(String filePath)
        throws Exception {
        return new JsonHotelManagementSystemStorage(Paths.get(filePath))
            .readHotelManagementSystem(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
            ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
            : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readHotelManagementSystem("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readHotelManagementSystem("notJsonFormatHotelManagementSystem.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readHotelManagementSystem_invalidCustomerHotelManagementSystem_throwDataConversionException()
        throws Exception {
        thrown.expect(DataConversionException.class);
        readHotelManagementSystem("invalidCustomerHotelManagementSystem.json");
    }

    @Test
    public void readHotelManagementSystem_invalidAndValidCustomerHotelManagementSystem_throwDataConversionException()
        throws Exception {
        thrown.expect(DataConversionException.class);
        readHotelManagementSystem("invalidAndValidCustomerHotelManagementSystem.json");
    }

    @Test
    public void readAndSaveHotelManagementSystem_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempHotelManagementSystem.json");
        HotelManagementSystem original = getTypicalHotelManagementSystem();
        JsonHotelManagementSystemStorage jsonHotelManagementSystemStorage =
            new JsonHotelManagementSystemStorage(filePath);

        // Save in new file and read back
        jsonHotelManagementSystemStorage.saveHotelManagementSystem(original, filePath);
        ReadOnlyHotelManagementSystem readBack =
            jsonHotelManagementSystemStorage.readHotelManagementSystem(filePath).get();
        assertEquals(original, new HotelManagementSystem(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addCustomer(HOON);
        original.removeCustomer(ALICE);
        jsonHotelManagementSystemStorage.saveHotelManagementSystem(original, filePath);
        readBack = jsonHotelManagementSystemStorage.readHotelManagementSystem(filePath).get();
        assertEquals(original, new HotelManagementSystem(readBack));

        // Save and read without specifying file path
        original.addCustomer(IDA);
        jsonHotelManagementSystemStorage.saveHotelManagementSystem(original); // file path not specified
        readBack = jsonHotelManagementSystemStorage.readHotelManagementSystem().get(); // file path not specified
        assertEquals(original, new HotelManagementSystem(readBack));
    }

    @Test
    public void saveHotelManagementSystem_nullHotelManagementSystem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveHotelManagementSystem(null, "SomeFile.json");
    }

    /**
     * Saves {@code hotelManagementSystem} at the specified {@code filePath}.
     */
    private void saveHotelManagementSystem(ReadOnlyHotelManagementSystem hotelManagementSystem, String filePath) {
        try {
            new JsonHotelManagementSystemStorage(Paths.get(filePath))
                .saveHotelManagementSystem(hotelManagementSystem, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveHotelManagementSystem_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveHotelManagementSystem(new HotelManagementSystem(), null);
    }
}
