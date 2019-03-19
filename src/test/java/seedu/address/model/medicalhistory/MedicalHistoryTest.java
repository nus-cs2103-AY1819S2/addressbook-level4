package seedu.address.model.medicalhistory;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
;
import seedu.address.testutil.Assert;
import seedu.address.model.person.Patient;
import seedu.address.model.person.Name;


public class MedicalHistoryTest {

    @Test
    public void equals() {
        MedicalHistory MH1 = new MedicalHistory(null, new Name("a"), new WriteUp("fever"));
        MedicalHistory MH2 = new MedicalHistory(null, new Name("a"), new WriteUp("sneeze"));
        MedicalHistory MH3 = new MedicalHistory(null, new Name("b"), new WriteUp("fever"));
        MedicalHistory MH1Copy = new MedicalHistory(null, new Name("a"), new WriteUp("fever"));

        // same object -> returns true
        assertTrue(MH1.equals(MH1));

        // same values -> returns true
        assertTrue(MH1.equals(MH1Copy));

        // null -> returns false
        assertFalse(MH1.equals(null));

        // different type -> returns false
        assertFalse(MH1.equals(5));

        // different writeup -> returns false
        assertFalse(MH1.equals(MH2));

        // different name -> returns false
        assertFalse(MH1.equals(MH3));
    }

}
