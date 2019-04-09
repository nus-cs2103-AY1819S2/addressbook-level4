package seedu.address.model.person.predicate;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Phone} matches any of the keywords given.
 */
public class PredicateManager implements Predicate<Person> {
    private final String predicateName;

    public PredicateManager(String predicateName) {
        this.predicateName = predicateName;
    }

    @Override
    public boolean test(Person person) {
        return true;
    }

    public String getPredicateName() {
        return predicateName;
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePredicateManager(PredicateManager otherPredicateManager) {
        if (otherPredicateManager == this) {
            return true;
        }

        return otherPredicateManager != null
            && otherPredicateManager.getPredicateName().equals(this.getPredicateName());
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof PredicateManager // instanceof handles nulls
            && predicateName.equals(((PredicateManager) other).predicateName)); // state check
    }


}
