package seedu.address.model.medicalhistory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.person.Name;


public class MedicalHistoryTest {

    @Test
    public void equals() {
        MedicalHistory mh1 = new MedicalHistory(null, null, new Name("a"), new WriteUp("fever"));
        MedicalHistory mh2 = new MedicalHistory(null, null, new Name("a"), new WriteUp("sneeze"));
        MedicalHistory mh3 = new MedicalHistory(null, null, new Name("b"), new WriteUp("fever"));
        MedicalHistory mh1Copy = new MedicalHistory(null, null, new Name("a"), new WriteUp("fever"));

        // same object -> returns true
        assertTrue(mh1.equals(mh1));

        // same values -> returns true
        assertTrue(mh1.equals(mh1Copy));

        // null -> returns false
        assertFalse(mh1.equals(null));

        // different type -> returns false
        assertFalse(mh1.equals(5));

        // different writeup -> returns false
        assertFalse(mh1.equals(mh2));

        // different name -> returns false
        assertFalse(mh1.equals(mh3));
    }

}
