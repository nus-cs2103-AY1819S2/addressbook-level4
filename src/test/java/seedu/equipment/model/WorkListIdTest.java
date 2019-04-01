package seedu.equipment.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WorkListIdTest {

    @Test
    public void equals() {
        WorkListId id1 = new WorkListId();
        assertFalse(id1.equals(null));

        assertTrue(id1.equals(id1));
    }
}
