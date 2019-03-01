package seedu.address.model.medicine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class MedicineManagerTest {

    private static MedicineManager typicalMedicineManager;
    private static final String[] directoriesNames = new String[] {"test1", "test2", "test3"};
    private static final String[] medicineNames = new String[] {"med1", "med2", "med3"};

    /**
     * initialize a typical storage
     */
    private void initializeTypicalStorage() {
        typicalMedicineManager = new MedicineManager();
        for (String c : directoriesNames) {
            typicalMedicineManager.addDirectory(c, new String[] {"root"});
        }
    }

    @Test
    public void addMedicine_wrongPath_throwsIllegalStateException() {
        initializeTypicalStorage();
        Assert.assertThrows(
                IllegalStateException.class, ()
                -> typicalMedicineManager.addMedicine(medicineNames[0], new String[]{"RRR"}));
    }

    @Test
    public void addMedicine_searchByName() {
        initializeTypicalStorage();
        typicalMedicineManager.addMedicine(medicineNames[0], new String[] {"root", "test1"});
        assertTrue(typicalMedicineManager.findMedicine(medicineNames[0]).get().name == medicineNames[0]);
    }

    @Test
    public void addMedicine_searchThroughWrongPath() {
        initializeTypicalStorage();
        typicalMedicineManager.addMedicine(medicineNames[0], new String[] {"root", "test2"});
        assertFalse(typicalMedicineManager.findMedicine(new String[] {"root", "test1", medicineNames[0]}).isPresent());
    }

    @Test
    public void addMedicine_searchThroughRightPath() {
        initializeTypicalStorage();
        typicalMedicineManager.addMedicine(medicineNames[0], new String[] {"root", "test2"});
        assertTrue(
                typicalMedicineManager.findMedicine(
                        new String[] {"root", "test2", medicineNames[0]}).get().name == medicineNames[0]);
    }
}
