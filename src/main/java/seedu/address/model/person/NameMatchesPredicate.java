package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Name} matches the exact name passed in.
 */

public class NameMatchesPredicate implements Predicate<Person> {

    private final String name;

    public NameMatchesPredicate(List<String> keywords) {
        if (keywords.size() == 0) {
            name = "";
        } else if (keywords.size() == 1) {
            name = keywords.get(0);
        } else {
            name = keywords.get(0) + " " + keywords.get(1);
        }
    }

    @Override
    public boolean test(Person person) {
        return name.equals(person.getName().fullName);
    }

}
