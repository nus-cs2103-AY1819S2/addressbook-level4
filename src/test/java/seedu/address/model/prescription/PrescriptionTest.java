package seedu.address.model.prescription;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.person.PersonId;


public class PrescriptionTest {
    @Test

    public void equals() {
        Prescription p1 = new Prescription(new PersonId("1"), new PersonId("2"),
                new Medicine("M1"), new Description("Testing"));
        Prescription p2 = new Prescription(new PersonId("1"), new PersonId("2"),
                new Medicine("M1"), new Description("Testing"));
        Prescription p3 = new Prescription(new PersonId("1"), new PersonId("2"),
                new Medicine("M1"), new Description("Not the same"));



        // p1 and p2 have same description -> returns true
        assertTrue(p1.equals(p2));

        // p1 and p3 do not have same description -> returns false
        assertFalse(p1.equals(p3));
    }
}
