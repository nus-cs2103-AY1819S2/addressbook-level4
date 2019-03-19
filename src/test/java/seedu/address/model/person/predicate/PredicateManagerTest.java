package seedu.address.model.person.predicate;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PredicateManagerTest {


    @Test
    public void test_returnsTrue() {
        // always return true
        PredicateManager predicate = new  PredicateManager();
        assertTrue(predicate.test(new PersonBuilder().build()));
    }
}
