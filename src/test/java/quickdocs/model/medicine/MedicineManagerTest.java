package quickdocs.model.medicine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import quickdocs.testutil.Assert;

public class MedicineManagerTest {

    private static MedicineManager typicalMedicineManager;
    private static final String[] directoriesNames = new String[] {"test1", "test2", "test3"};
    private static final String[] medicineNames = new String[] {"med1", "med2", "med3"};

    /**
     * initialize a typical storage
     */
    @Before
    public void initializeTypicalStorage() {
        typicalMedicineManager = new MedicineManager();
        for (String c : directoriesNames) {
            typicalMedicineManager.addDirectory(c, new String[] {"root"});
        }
    }

    @Test
    public void addMedicine_wrongPath_throwsIllegalArgumentException() {
        Assert.assertThrows(
                IllegalArgumentException.class, ()
                -> typicalMedicineManager.addMedicine(medicineNames[0], new String[]{"RRR"}, BigDecimal.valueOf(22)));
    }

    @Test
    public void addMedicine_searchByName() {
        initializeTypicalStorage();
        typicalMedicineManager.addMedicine(medicineNames[0], new String[] {"root", "test1"}, BigDecimal.valueOf(33));
        assertTrue(typicalMedicineManager.findMedicine(medicineNames[0]).get().name == medicineNames[0]);
    }

    @Test
    public void addMedicine_searchThroughWrongPath() {
        typicalMedicineManager.addMedicine(medicineNames[0], new String[] {"root", "test2"}, BigDecimal.valueOf(7));
        assertFalse(typicalMedicineManager.findMedicine(new String[] {"root", "test1", medicineNames[0]}).isPresent());
    }

    @Test
    public void addMedicine_searchThroughRightPath() {
        typicalMedicineManager.addMedicine(medicineNames[0], new String[] {"root", "test2"}, BigDecimal.valueOf(44));
        assertTrue(
                typicalMedicineManager.findMedicine(
                        new String[] {"root", "test2", medicineNames[0]}).get().name == medicineNames[0]);
    }

    @Test
    public void purchaseMedicine_viaPath() {
        typicalMedicineManager.addMedicine(
                medicineNames[0], 20, new String[] {"root", "test2"}, BigDecimal.valueOf(11));
        typicalMedicineManager.purchaseMedicine(new String[] {"root", "test2", medicineNames[0]}, 50);
        assertEquals(70, typicalMedicineManager.findMedicine(
                new String[] {"root", "test2", medicineNames[0]}).get().getQuantity());
    }

    @Test
    public void purchaseMedicine_woPath() {
        typicalMedicineManager.addMedicine(
                medicineNames[0], 20, new String[] {"root", "test2"}, BigDecimal.valueOf(22));
        typicalMedicineManager.purchaseMedicine(medicineNames[0], 50);
        assertEquals(70, typicalMedicineManager.findMedicine(medicineNames[0]).get().getQuantity());
    }
}
