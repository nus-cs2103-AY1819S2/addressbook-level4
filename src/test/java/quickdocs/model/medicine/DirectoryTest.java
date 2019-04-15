package quickdocs.model.medicine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import quickdocs.testutil.Assert;

public class DirectoryTest {

    private static String[] typicalNames = new String[] {"test1", "test2", "test3"};
    private static String duplicateDirectoryName = "test1";
    private static String duplicateMedicineName = "testMedicine";
    private static Medicine medicineTest = new Medicine(duplicateMedicineName);
    private Directory typicalDirectory;

    /**
     * Initialize a typical directory for testing
     */
    @Before
    public void initializeTypicalDirectory() {
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
        Assert.assertThrows(NullPointerException.class, () -> typicalDirectory.addDirectory((Directory) null));
    }

    @Test
    public void addDirectory_duplicate_throwsIllegalArgumentException() {
        Assert.assertThrows(
                IllegalArgumentException.class, () -> typicalDirectory.addDirectory(duplicateDirectoryName));
    }

    @Test
    public void addMedicine_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> typicalDirectory.addMedicine(null));
    }

    @Test
    public void addMedicine_duplicateName_throwsIllegalArgumentException() {
        Assert.assertThrows(
                IllegalArgumentException.class, ()
                -> typicalDirectory.addMedicine(new Medicine(duplicateMedicineName)));
    }

    @Test
    public void findMedicine_nonMatchingDirectoryName_returnsOptionalEmpty() {
        assertFalse(typicalDirectory.findMedicine(new String[] {"tss", duplicateMedicineName}, 0).isPresent());
    }

    @Test
    public void findMedicine_nonMatchingMedicineName_returnsOptionalEmpty() {
        assertFalse(typicalDirectory.findMedicine(new String[] {"test", "tt"}, 0).isPresent());
    }

    @Test
    public void findDirectory_nonMatchingDirectoryName_returnsOptionalEmpty() {
        assertFalse(typicalDirectory.findDirectory(new String[] {"ttest", duplicateMedicineName}, 0).isPresent());
    }

    @Test
    public void findDirectory_nonExistingDirectoryName_returnsOptionalEmpty() {
        assertFalse(typicalDirectory.findDirectory(new String[] {"test", "tt"}, 0).isPresent());
    }

    @Test
    public void findMedicine_returnsSameMedicine() {
        assertTrue(typicalDirectory.findMedicine(
                new String[] {"test", duplicateMedicineName}, 0).get().equals(medicineTest));
    }

    @Test
    public void setThresholdForDirectory() {
        typicalDirectory.findDirectory(
                new String[]{"test", "test1"}, 0).get().addMedicine(new Medicine("tt"));
        typicalDirectory.findDirectory(
                new String[]{"test", "test2"}, 0).get().addMedicine(new Medicine("ttt"));
        typicalDirectory.setThreshold(10);
        assertTrue(typicalDirectory.getThreshold().equals(Optional.of(10)));
    }
}
