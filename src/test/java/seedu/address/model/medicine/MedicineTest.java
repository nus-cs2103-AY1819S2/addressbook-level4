package seedu.address.model.medicine;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class MedicineTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Medicine(null));
    }

    @Test
    public void constructorWithQuantity_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Medicine(null, 40));
    }

    @Test
    public void constructor_invalidMedicine_throwsIllegalArgumentException() {
        String invalidMedicine = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Medicine(invalidMedicine));
    }

    @Test
    public void invalidAdditionQuantity_throwsIllegalArgumentException() {
        Medicine medicineTest = new Medicine("test", 0);
        Assert.assertThrows(IllegalArgumentException.class, () -> medicineTest.addQuantity(-40));
    }

    @Test
    public void invalidSubtractionQuantity_throwsIllegalArgumentException() {
        Medicine medicineTest = new Medicine("test", 0);
        Assert.assertThrows(IllegalArgumentException.class, () -> medicineTest.subtractQuantity(-40));
    }

    @Test
    public void insufficientQuantityForSubtraction_throwsIllegalArgumentException() {
        Medicine medicineTest = new Medicine("test", 50);
        Assert.assertThrows(IllegalArgumentException.class, () -> medicineTest.subtractQuantity(60));
    }

    @Test
    public void negativeThreshold_throwsIllegalArgumentException() {
        Medicine medicineTest = new Medicine("test");
        Assert.assertThrows(IllegalArgumentException.class, () -> medicineTest.setThreshold(-60));
    }

    @Test
    public void setValidThreshold() {
        Medicine medicineTest = new Medicine("test");
        medicineTest.setThreshold(100);
        assertTrue(medicineTest.getThreshold() == 100);
    }
}
