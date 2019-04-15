package seedu.address.logic.commands.sortmethods;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import seedu.address.model.person.Person;


/**
 * Follows the SortMethod interface
 * Sorts all persons by GPA and then subsequently alphabetically by name (first name first)
 */
public class SortGpa implements SortMethod {

    private List<Person> newList;

    /**
     * alters the newList to contain persons in newly sorted order
     */
    public void execute(List<Person> lastShownList, String... type) {
        Comparator<Person> personGpaComparator = Comparator.comparing(Person::gpaToString);
        List<Person> initialSortedList = SortUtil.sortPersons(lastShownList, personGpaComparator);
        Collections.reverse(initialSortedList);
        SortListWithDuplicates secondarySort = new SortListWithDuplicates(initialSortedList, new SortName(),
                personGpaComparator);
        this.newList = secondarySort.getList();
    }

    public List<Person> getList() {
        return this.newList;
    }
}
