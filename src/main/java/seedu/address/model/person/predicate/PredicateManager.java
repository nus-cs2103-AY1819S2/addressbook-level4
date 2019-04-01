package seedu.address.model.person.predicate;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Phone} matches any of the keywords given.
 */
public class PredicateManager implements Predicate<Person> {

    @Override
    public boolean test(Person person) {
        return true;
    }


}
