package seedu.address.model.person.predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILTERNAME;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class FilterTest {

    @Test
    public void equals() {
        Filter firstFilter = new Filter("first");
        Filter secondFilter = new Filter("second");
        // same object -> returns true
        assertEquals(firstFilter,firstFilter);

        // same values -> returns true
        Filter firstFilterCopy = new Filter("first");
        assertEquals(firstFilter,firstFilterCopy);

        // different types -> returns false
        assertNotEquals(firstFilter,1);

        // null -> returns false
        assertNotEquals(firstFilter,null);

        // different -> returns false
        assertNotEquals(firstFilter,secondFilter);
    }

}
