package quickdocs.model.consultation;

import org.junit.Test;

import quickdocs.model.medicine.Medicine;
import quickdocs.testutil.Assert;

public class PrescriptionTest {

    @Test
    public void invalidPrescription() {
        String medicine = " ";
        int quantity = 1;
        Assert.assertThrows(IllegalArgumentException.class, ()->new Prescription(new Medicine(medicine, 2),
                quantity));
        int quantity2 = -1;
        Assert.assertThrows(IllegalArgumentException.class, ()->new Prescription(new Medicine(medicine, 2),
                quantity2));
    }
}
