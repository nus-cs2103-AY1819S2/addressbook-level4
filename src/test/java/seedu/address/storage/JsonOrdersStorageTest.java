package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import static seedu.address.testutil.TypicalRestOrRant.TABLE1_W09;
import static seedu.address.testutil.TypicalRestOrRant.TABLE8_W09;
import static seedu.address.testutil.TypicalRestOrRant.getTypicalOrderItems;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.order.OrderItem;
import seedu.address.model.order.Orders;
import seedu.address.model.order.ReadOnlyOrders;

public class JsonOrdersStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonOrdersStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readOrders_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readOrders(null);
    }

    private java.util.Optional<ReadOnlyOrders> readOrders(String filePath) throws Exception {
        return new JsonOrdersStorage(Paths.get(filePath)).readOrders(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void readOrders_missingFile_emptyResult() throws Exception {
        assertFalse(readOrders("NonExistentFile.json").isPresent());
    }

    @Test
    public void readOrders_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readOrders("notJsonFormatOrders.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readOrders_invalidOrderItem_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readOrders("invalidOrderItemOrders.json");
    }

    @Test
    public void readOrders_invalidAndValidOrderItem_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readOrders("invalidAndValidOrderItemOrders.json");
    }

    @Test
    public void readAndSaveOrders_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempOrders.json");
        Orders original = new Orders();
        for (OrderItem orderItem : getTypicalOrderItems()) {
            original.addOrderItem(orderItem);
        }
        JsonOrdersStorage jsonOrdersStorage = new JsonOrdersStorage(filePath);

        // Save in new file and read back
        jsonOrdersStorage.saveOrders(original, filePath);
        ReadOnlyOrders readBack = jsonOrdersStorage.readOrders(filePath).get();
        assertEquals(original, new Orders(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addOrderItem(TABLE8_W09);
        original.removeOrderItem(TABLE1_W09);
        jsonOrdersStorage.saveOrders(original, filePath);
        readBack = jsonOrdersStorage.readOrders(filePath).get();
        assertEquals(original, new Orders(readBack));

        // Save and read without specifying file path
        original.addOrderItem(TABLE1_W09);
        jsonOrdersStorage.saveOrders(original); // file path not specified
        readBack = jsonOrdersStorage.readOrders().get(); // file path not specified
        assertEquals(original, new Orders(readBack));

        jsonOrdersStorage.backupOrders(original);
        readBack = jsonOrdersStorage.readOrders().get();
        assertEquals(original, new Orders(readBack));

    }

    @Test
    public void saveOrders_nullOrders_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveOrders(null, "SomeFile.json");
    }

    /**
     * Saves {@code restOrRant} at the specified {@code filePath}.
     */
    private void saveOrders(ReadOnlyOrders orders, String filePath) {
        try {
            new JsonOrdersStorage(Paths.get(filePath))
                    .saveOrders(orders, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveOrders_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveOrders(new Orders(), null);
    }
}
