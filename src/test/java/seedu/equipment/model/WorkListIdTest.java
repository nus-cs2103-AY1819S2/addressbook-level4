package seedu.equipment.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.equipment.testutil.Assert;

public class WorkListIdTest {

    @Test
    public void isValidWorkListId() {
        // null WorkListId
        Assert.assertThrows(NullPointerException.class, () -> WorkListId.isValidWorkListId(null));

        assertFalse(WorkListId.isValidWorkListId("")); //empty String
        assertFalse(WorkListId.isValidWorkListId("  ")); //space-only
        assertFalse(WorkListId.isValidWorkListId("id")); //non-numeric
        assertFalse(WorkListId.isValidWorkListId("999i9")); // alphabets within digits
        assertFalse(WorkListId.isValidWorkListId("98 90")); // spaces within digits

        assertTrue(WorkListId.isValidWorkListId("1")); // exactly 1 digit
        assertTrue(WorkListId.isValidWorkListId("1000"));
    }

    @Test
    public void equals() {
        WorkListId id1 = new WorkListId("1000");
        assertFalse(id1.equals(null));

        assertTrue(id1.equals(id1));
    }

    @Test
    public void getIntIdMustFollowFormat() {

        WorkListId validId = new WorkListId("1000");

        assertTrue(validId.getIntId() == 1000);
    }
}
