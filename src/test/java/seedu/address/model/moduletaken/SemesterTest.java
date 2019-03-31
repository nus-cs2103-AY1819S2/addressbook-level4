package seedu.address.model.moduletaken;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class SemesterTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> Semester.valueOf(null));
    }

    @Test
    public void constructor_invalidSemester_throwsIllegalArgumentException() {
        String invalidSemester = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> Semester.valueOf(invalidSemester));
    }

    @Test
    public void isValidSemester() {
        // null semester number
        Assert.assertThrows(NullPointerException.class, () -> Semester.isValidSemesterForTakingModules(null));

        // invalid semester numbers
        assertFalse(Semester.isValidSemesterForTakingModules("")); // empty string
        assertFalse(Semester.isValidSemesterForTakingModules(" ")); // spaces only
        assertFalse(Semester.isValidSemesterForTakingModules("S2")); // less than 3 numbers
        assertFalse(Semester.isValidSemesterForTakingModules("Y3S4")); // non-numeric
        assertFalse(Semester.isValidSemesterForTakingModules("Y6S2")); // alphabets within digits
        assertFalse(Semester.isValidSemesterForTakingModules("Y1")); // spaces within digits

        // valid semester numbers
        assertTrue(Semester.isValidSemesterForTakingModules("Y2S1")); // exactly 3 numbers
        assertTrue(Semester.isValidSemesterForTakingModules("Y1S1"));
        assertTrue(Semester.isValidSemesterForTakingModules("Y4S2")); // long semester numbers
    }
}
