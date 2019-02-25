package seedu.address.model.medicine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class StorageTest {

    private static Storage typicalStorage;
    private static final String[] directoriesNames = new String[] {"test1", "test2", "test3"};
    private static final String[] medicineNames = new String[] {"med1", "med2", "med3"};

    /**
     * initialize a typical storage
     */
    private void initializeTypicalStorage() {
        typicalStorage = new Storage();
        for (String c : directoriesNames) {
            typicalStorage.addDirectory(c, new String[] {"root"});
        }
    }

    @Test
    public void addMedicine_wrongPath_throwsIllegalStateException() {
        initializeTypicalStorage();
        Assert.assertThrows(
                IllegalStateException.class, ()
                -> typicalStorage.addMedicine(medicineNames[0], new String[]{"RRR"}));
    }

    @Test
    public void addMedicine_searchByName() {
        initializeTypicalStorage();
        typicalStorage.addMedicine(medicineNames[0], new String[] {"root", "test1"});
        assertTrue(typicalStorage.findMedicine(medicineNames[0]).get().name == medicineNames[0]);
    }

    @Test
    public void addMedicine_searchThroughWrongPath() {
        initializeTypicalStorage();
        typicalStorage.addMedicine(medicineNames[0], new String[] {"root", "test2"});
        assertFalse(typicalStorage.findMedicine(new String[] {"root", "test1", medicineNames[0]}).isPresent());
    }

    @Test
    public void addMedicine_searchThroughRightPath() {
        initializeTypicalStorage();
        typicalStorage.addMedicine(medicineNames[0], new String[] {"root", "test2"});
        assertTrue(
                typicalStorage.findMedicine(
                        new String[] {"root", "test2", medicineNames[0]}).get().name == medicineNames[0]);
    }
}
