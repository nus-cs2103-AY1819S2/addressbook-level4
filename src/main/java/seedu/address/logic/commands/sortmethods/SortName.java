package seedu.address.logic.commands.sortmethods;

import java.util.Comparator;
import java.util.List;

import seedu.address.model.person.Person;


/**
 * Follows the SortMethod interface
 * Sorts all persons by name, starting with first name.
 * No secondary sorting is required since duplicate persons are not allowed in the address book.
 */
public class SortName implements SortMethod {

    private List<Person> newList;

    /**
     * alters the newList to contain persons in newly sorted order
     */
    public void execute(List<Person> lastShownList, String... type) {
        Comparator<Person> personNameComparator = Comparator.comparing(Person::nameToString);
        this.newList = SortUtil.sortPersons(lastShownList, personNameComparator);
    }

    public List<Person> getList() {
        return this.newList;
    }
}
