package seedu.address.model.medicalhistory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.person.PersonId;

public class MedicalHistoryTest {

    @Test
    public void equals() {
        MedicalHistory mh1 = new MedicalHistory(
                new PersonId("1"), new PersonId("1"), new ValidDate("2018-05-05"), new WriteUp("testWriteUp"));
        MedicalHistory mh2 = new MedicalHistory(
                new PersonId("1"), new PersonId("2"), new ValidDate("2018-05-05"), new WriteUp("testWriteUp"));
        MedicalHistory mh3 = new MedicalHistory(
                new PersonId("1"), new PersonId("1"), new ValidDate("2018-05-06"), new WriteUp("testWriteUp"));
        MedicalHistory mh1Copy = new MedicalHistory(
                new PersonId("1"), new PersonId("1"), new ValidDate("2018-05-05"), new WriteUp("testWriteUp"));

        // same object -> returns true
        assertTrue(mh1.equals(mh1));

        // same values -> returns true
        assertTrue(mh1.equals(mh1Copy));

        // null -> returns false
        assertFalse(mh1.equals(null));

        // different type -> returns false
        assertFalse(mh1.equals(5));

        // different id -> returns false
        assertFalse(mh1.equals(mh2));

        // different date -> returns false
        assertFalse(mh1.equals(mh3));
    }

}
