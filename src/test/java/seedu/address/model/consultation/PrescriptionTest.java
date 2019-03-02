package seedu.address.model.consultation;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PrescriptionTest {

    @Test
    public void invalidPrescription() {
        String medicine = " ";
        int quantity = 1;
        Assert.assertThrows(IllegalArgumentException.class, ()->new Prescription(medicine, quantity));
        Assert.assertThrows(IllegalArgumentException.class, ()->new Prescription(medicine, -1));
    }
}
