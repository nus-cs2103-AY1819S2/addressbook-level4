package seedu.address.model.prescription;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class MedicineTest {

    @Test
    public void equals() {
        Medicine m1 = new Medicine("Testing");
        Medicine m2 = new Medicine("Testing");
        Medicine m3 = new Medicine("NotTheSame");

        // m1 and m2 have the same name -> returns true
        assertTrue(m1.equals(m2));

        // m1 and m3 do not have the same name -> returns false
        assertFalse(m1.equals(m3));
    }
}
