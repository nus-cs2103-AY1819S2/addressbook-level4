package seedu.address.model.person.predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class FilterTest {

    @Test
    public void equals() {
        Filter firstFilter = new Filter("first");
        Filter secondFilter = new Filter("second");
        // same object -> returns true
        assertEquals(firstFilter, firstFilter);

        // same values -> returns true
        Filter firstFilterCopy = new Filter("first");
        assertEquals(firstFilter, firstFilterCopy);

        // different types -> returns false
        assertNotEquals(firstFilter, 1);

        // null -> returns false
        assertNotEquals(firstFilter, null);

        // different -> returns false
        assertNotEquals(firstFilter, secondFilter);
    }

}
