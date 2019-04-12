package seedu.address.model.prescription;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.medicalhistory.ValidDate;
import seedu.address.model.person.PersonId;


public class PrescriptionTest {
    @Test

    public void equals() {
        Prescription p1 = new Prescription(new PersonId("1"), new PersonId("2"),
                new ValidDate("2018-05-13"), new Medicine("M1"), new Description("Testing"));
        Prescription p2 = new Prescription(new PersonId("1"), new PersonId("2"),
                new ValidDate("2018-05-13"), new Medicine("M1"), new Description("Testing"));
        Prescription p3 = new Prescription(new PersonId("1"), new PersonId("2"),
                new ValidDate("2018-05-13"), new Medicine("M1"), new Description("Not the same"));
        Prescription p4 = new Prescription(new PersonId("1"), new PersonId("2"),
                new ValidDate("2018-05-14"), new Medicine("M1"), new Description("Testing"));
        Prescription p5 = new Prescription(new PersonId("1"), new PersonId("2"),
                new ValidDate("2018-05-13"), new Medicine("M2"), new Description("Testing"));
        Prescription p6 = new Prescription(new PersonId("1"), new PersonId("3"),
                new ValidDate("2018-05-13"), new Medicine("M1"), new Description("Testing"));
        Prescription p7 = new Prescription(new PersonId("4"), new PersonId("2"),
                new ValidDate("2018-05-13"), new Medicine("M1"), new Description("Testing"));


        // p1 and p2 have exactly the same content -> returns true
        assertTrue(p1.equals(p2));

        // p1 and p3 do not have the same description -> returns false
        assertFalse(p1.equals(p3));

        // p1 and p4 do not have the same date -> returns false
        assertFalse(p1.equals(p4));

        // p1 and p5 do not have the same medicine name -> returns false
        assertFalse(p1.equals(p5));

        // p1 and p6 do not have the same doctor id -> returns false
        assertFalse(p1.equals(p6));

        // p1 and p7 do not have the same patient id -> returns false
        assertFalse(p1.equals(p7));

        // p1 is not the same as null -> returns false
        assertFalse(p1.equals(null));
    }
}
