package quickdocs.model.consultation;

import org.junit.Test;

import quickdocs.model.medicine.Medicine;
import quickdocs.testutil.Assert;

public class PrescriptionTest {

    @Test
    public void constructor_negativeIndex_throwsIllegalArgumentException() {
        String medicine = "Ibuprofen";
        int quantity2 = -1;
        Assert.assertThrows(IllegalArgumentException.class, ()->new Prescription(new Medicine(medicine, 2),
                quantity2));
    }

    @Test
    public void constructor_medicineNotDefined_throwsIllegalArgumentException() {
        String medicine = " ";
        int quantity = 1;
        Assert.assertThrows(IllegalArgumentException.class, ()->new Prescription(new Medicine(medicine, 2),
                quantity));

    }

    @Test
    public void equals() {
        Medicine med1 = new Medicine("Ibuprofen", 2);
        Medicine med2 = new Medicine("Morphine", 2);
        Prescription p1 = new Prescription(med1, 1);

        org.junit.Assert.assertTrue(p1.equals(p1));

        org.junit.Assert.assertFalse(p1.equals(1));

        Prescription p2 = new Prescription(med1, 1);
        org.junit.Assert.assertTrue(p1.equals(p2));

        Prescription p3 = new Prescription(med1, 2);
        org.junit.Assert.assertFalse(p1.equals(p3));

        Prescription p4 = new Prescription(med2, 1);
        org.junit.Assert.assertFalse(p1.equals(p4));
    }
}
