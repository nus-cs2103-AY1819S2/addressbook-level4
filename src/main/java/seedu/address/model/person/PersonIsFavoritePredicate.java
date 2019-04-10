package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.model.Model;

/**
 * Tests that a {@code Person}'s {@code Name} exists in the favorites list.
 */
public class PersonIsFavoritePredicate implements Predicate<Person> {

    private Model model;

    public PersonIsFavoritePredicate(Model model) {
        this.model = model;
    }

    @Override
    public boolean test(Person person) {
        return model.getFavoritesList().contains(person);
    }
}
