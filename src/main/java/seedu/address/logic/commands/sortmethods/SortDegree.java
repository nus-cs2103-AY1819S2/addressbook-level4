package seedu.address.logic.commands.sortmethods;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import seedu.address.model.person.Person;


/**
 * Follows the SortMethod interface
 * Sorts all persons by Degree and then subsequently by Gpa
 */
public class SortDegree implements SortMethod {

    private List<Person> newList;

    /**
     * alters the newList to contain persons in newly sorted order
     */
    public void execute(List<Person> lastShownList, String... type) {
        Comparator<Person> personDegreeComparator = Comparator.comparing(Person::degreeToValue);
        List<Person> initialSortedList = SortUtil.sortPersons(lastShownList, personDegreeComparator);
        Collections.reverse(initialSortedList);
        SortListWithDuplicates secondarySort = new SortListWithDuplicates(initialSortedList, new SortGpa(),
                personDegreeComparator);
        this.newList = secondarySort.getList();
    }

    public List<Person> getList() {
        return this.newList;
    }
}
