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
    public void isValidSemesterForTakingModules() {
        // null semester number
        Assert.assertThrows(NullPointerException.class, () -> Semester.isValidSemesterForTakingModules(null));

        // invalid semester numbers
        assertFalse(Semester.isValidSemesterForTakingModules(""));
        assertFalse(Semester.isValidSemesterForTakingModules(" "));
        assertFalse(Semester.isValidSemesterForTakingModules("S2"));
        assertFalse(Semester.isValidSemesterForTakingModules("Y3S4"));
        assertFalse(Semester.isValidSemesterForTakingModules("Y6S2"));
        assertFalse(Semester.isValidSemesterForTakingModules("Y1"));
        assertFalse(Semester.isValidSemesterForTakingModules("GRAD"));

        // valid semester numbers
        assertTrue(Semester.isValidSemesterForTakingModules("Y2S1"));
        assertTrue(Semester.isValidSemesterForTakingModules("Y1S1"));
        assertTrue(Semester.isValidSemesterForTakingModules("Y4S2"));
    }

    @Test
    public void isValidSemester() {
        // null semester number
        Assert.assertThrows(NullPointerException.class, () -> Semester.isValidSemester(null));

        // invalid semester numbers
        assertFalse(Semester.isValidSemester(""));
        assertFalse(Semester.isValidSemester(" "));
        assertFalse(Semester.isValidSemester("S2"));
        assertFalse(Semester.isValidSemester("Y3S4"));
        assertFalse(Semester.isValidSemester("Y6S2"));
        assertFalse(Semester.isValidSemester("Y1"));

        // valid semester numbers
        assertTrue(Semester.isValidSemester("Y2S1"));
        assertTrue(Semester.isValidSemester("Y1S1"));
        assertTrue(Semester.isValidSemester("Y4S2"));
        assertTrue(Semester.isValidSemester("GRAD"));
    }
}
