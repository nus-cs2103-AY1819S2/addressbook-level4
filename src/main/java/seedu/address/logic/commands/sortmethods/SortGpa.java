package seedu.address.logic.commands.sortmethods;

import java.util.Comparator;
import java.util.List;

import seedu.address.model.person.Person;


/**
 * Sorts all persons by GPA.
 */
public class SortGpa implements SortMethod {

    private List<Person> newList;

    public void execute(List<Person> lastShownList, String... type) {
        Comparator<Person> personGpaComparator = Comparator.comparing(Person::gpaToString);
        this.newList = SortUtil.sortPersons(lastShownList, personGpaComparator);
    }

    public List<Person> getList() {
        return this.newList;
    }
}
