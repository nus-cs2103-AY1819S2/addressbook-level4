package seedu.address.model.person.predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PredicateManagerTest {

    @Test
    public void equals() {
        PredicateManager predicate = new PredicateManager();

        // same object -> returns true
        assertEquals(predicate, predicate);

        // same values -> returns true
        PredicateManager predicateCopy = new PredicateManager();
        assertEquals(predicate, predicateCopy);

        // different types -> returns false
        assertNotEquals(predicate, 1);

        // null -> returns false
        assertNotEquals(predicate, null);
    }

    @Test
    public void test_returnsTrue() {
        // always return true
        PredicateManager predicate = new PredicateManager();
        assertTrue(predicate.test(new PersonBuilder().build()));
    }

}
