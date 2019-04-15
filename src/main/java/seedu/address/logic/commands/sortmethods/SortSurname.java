package seedu.address.logic.commands.sortmethods;

import java.util.Comparator;
import java.util.List;

import seedu.address.model.person.Person;


/**
 * Follows the SortMethod interface
 * Sorts all persons alphabetically by surname.
 * Sorts persons with matching surnames by first name.
 */
public class SortSurname implements SortMethod {

    private List<Person> newList;

    /**
     * alters the newList to contain persons in newly sorted order
     */
    public void execute(List<Person> lastShownList, String... type) {
        Comparator<Person> personSurnameComparator = Comparator.comparing(Person::surnameToString);
        List<Person> initialSortedList = SortUtil.sortPersons(lastShownList, personSurnameComparator);
        SortListWithDuplicates secondarySort = new SortListWithDuplicates(initialSortedList, new SortName(),
                personSurnameComparator);
        this.newList = secondarySort.getList();
    }

    public List<Person> getList() {
        return this.newList;
    }
}
