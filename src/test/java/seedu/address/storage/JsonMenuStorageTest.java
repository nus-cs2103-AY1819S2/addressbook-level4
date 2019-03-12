package seedu.address.storage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyRestOrRant;
import seedu.address.model.RestOrRant;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPersons.*;

public class JsonMenuStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    @Test
    public void readRestOrRant_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readRestOrRant(null);
    }
    
    private java.util.Optional<ReadOnlyRestOrRant> readRestOrRant(String filePath) throws Exception {
        return new JsonRestOrRantStorage(Paths.get(filePath)).readRestOrRant(addToTestDataPathIfNotNull(filePath));
    }
    
    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                       ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                       : null;
    }
    
    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readRestOrRant("NonExistentFile.json").isPresent());
    }
    
    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {
        
        thrown.expect(DataConversionException.class);
        readRestOrRant("notJsonFormatAddressBook.json");
        
        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }
    
    @Test
    public void readRestOrRant_invalidPersonRestOrRant_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readRestOrRant("invalidPersonAddressBook.json");
    }
    
    @Test
    public void readRestOrRant_invalidAndValidPersonRestOrRant_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readRestOrRant("invalidAndValidPersonAddressBook.json");
    }
    
    @Test
    public void readAndSaveRestOrRant_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempAddressBook.json");
        RestOrRant original = getTypicalAddressBook();
        JsonRestOrRantStorage jsonRestOrRantStorage = new JsonRestOrRantStorage(filePath);
        
        // Save in new file and read back
        jsonRestOrRantStorage.saveRestOrRant(original, filePath);
        ReadOnlyRestOrRant readBack = jsonRestOrRantStorage.readRestOrRant(filePath).get();
        assertEquals(original, new RestOrRant(readBack));
        
        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonRestOrRantStorage.saveRestOrRant(original, filePath);
        readBack = jsonRestOrRantStorage.readRestOrRant(filePath).get();
        assertEquals(original, new RestOrRant(readBack));
        
        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonRestOrRantStorage.saveRestOrRant(original); // file path not specified
        readBack = jsonRestOrRantStorage.readRestOrRant().get(); // file path not specified
        assertEquals(original, new RestOrRant(readBack));
        
        jsonRestOrRantStorage.backupRestOrRant(original);
        readBack = jsonRestOrRantStorage.readRestOrRant().get();
        assertEquals(original, new RestOrRant(readBack));
        
    }
    
    @Test
    public void saveRestOrRant_nullRestOrRant_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveRestOrRant(null, "SomeFile.json");
    }
    
    /**
     * Saves {@code restOrRant} at the specified {@code filePath}.
     */
    private void saveRestOrRant(ReadOnlyRestOrRant restOrRant, String filePath) {
        try {
            new JsonRestOrRantStorage(Paths.get(filePath))
                    .saveRestOrRant(restOrRant, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }
    
    @Test
    public void saveRestOrRant_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveRestOrRant(new RestOrRant(), null);
    }
    
}
