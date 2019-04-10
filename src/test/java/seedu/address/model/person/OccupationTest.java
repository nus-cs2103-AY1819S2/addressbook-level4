package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class OccupationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Occupation(null));
    }

    @Test
    public void constructor_invalidOccupation_throwsIllegalArgumentException() {
        String invalidOccupation = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Occupation(invalidOccupation));
    }

    @Test
    public void isValidOccupation() {
        // null occupation
        Assert.assertThrows(NullPointerException.class, () -> Occupation.isValidOccupation(null));

        // invalid occupation
        assertFalse(Occupation.isValidOccupation("")); // empty string
        assertFalse(Occupation.isValidOccupation(" ")); // spaces only

        // valid occupation
        assertTrue(Occupation.isValidOccupation("exeutive Director"));
        assertTrue(Occupation.isValidOccupation("freelancer")); // one character
        assertTrue(Occupation.isValidOccupation("professor, CEO, expert")); // long address
    }
}
