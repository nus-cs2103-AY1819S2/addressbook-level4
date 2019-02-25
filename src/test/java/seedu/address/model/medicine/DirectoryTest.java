package seedu.address.model.medicine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;


public class DirectoryTest {

    private static String[] typicalNames = new String[] {"test1", "test2", "test3"};
    private static String duplicateDirectoryName = "test1";
    private static String duplicateMedicineName = "testMedicine";
    private static Medicine medicineTest = new Medicine(duplicateMedicineName);
    private Directory typicalDirectory;

    /**
     * Initialize a typical directory for testing
     */
    private void initializeTypicalDirectory() {
        typicalDirectory = new Directory("test");
        for (String name : typicalNames) {
            typicalDirectory.addDirectory(name);
        }
        typicalDirectory.addMedicine(medicineTest);
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Directory(null));
    }

    @Test
    public void addDirectory_null_throwsNullPointerException() {
        initializeTypicalDirectory();
        Assert.assertThrows(NullPointerException.class, () -> typicalDirectory.addDirectory(null));
    }

    @Test
    public void addDirectory_duplicate_throwsIllegalArgumentException() {
        initializeTypicalDirectory();
        Assert.assertThrows(
                IllegalArgumentException.class, () -> typicalDirectory.addDirectory(duplicateDirectoryName));
    }

    @Test
    public void addMedicine_null_throwsNullPointerException() {
        initializeTypicalDirectory();
        Assert.assertThrows(NullPointerException.class, () -> typicalDirectory.addMedicine(null));
    }

    @Test
    public void addMedicine_duplicateName_throwsIllegalArgumentException() {
        initializeTypicalDirectory();
        Assert.assertThrows(
                IllegalArgumentException.class, ()
                -> typicalDirectory.addMedicine(new Medicine(duplicateMedicineName)));
    }

    @Test
    public void findMedicine_nonMatchingDirectoryName_throwsIllegalStateException() {
        initializeTypicalDirectory();
        Assert.assertThrows(
                IllegalStateException.class, () ->
                        typicalDirectory.findMedicine(new String[] {"testt", duplicateMedicineName}, 0));
    }

    @Test
    public void findMedicine_nonMatchingMedicineName_returnsOptionalEmpty() {
        initializeTypicalDirectory();
        assertFalse(typicalDirectory.findMedicine(new String[] {"test", "tt"}, 0).isPresent());
    }

    @Test
    public void findDirectory_nonMatchingDirectoryName_throwsIllegalStateException() {
        initializeTypicalDirectory();
        Assert.assertThrows(
                IllegalStateException.class, () ->
                        typicalDirectory.findDirectory(new String[] {"testt", duplicateDirectoryName}, 0));
    }

    @Test
    public void findDirectory_nonExistingDirectoryName_returnsOptionalEmpty() {
        initializeTypicalDirectory();
        assertFalse(typicalDirectory.findDirectory(new String[] {"test", "tt"}, 0).isPresent());
    }

    @Test
    public void findMedicine_returnsSameMedicine() {
        initializeTypicalDirectory();
        assertTrue(typicalDirectory.findMedicine(
                new String[] {"test", duplicateMedicineName}, 0).get().equals(medicineTest));
    }
}
