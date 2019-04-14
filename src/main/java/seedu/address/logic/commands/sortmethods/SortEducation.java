package seedu.address.logic.commands.sortmethods;

import java.util.Comparator;
import java.util.List;

import seedu.address.model.person.Person;


/**
 * Follows the SortMethod interface
 * Sorts all persons by Education and then subsequently by gpa
 */
public class SortEducation implements SortMethod {

    private List<Person> newList;

    /**
     * alters the newList to contain persons in newly sorted order
     */
    public void execute(List<Person> lastShownList, String... type) {
        Comparator<Person> personEducationComparator = Comparator.comparing(Person::educationToString);
        List<Person> initialSortedList = SortUtil.sortPersons(lastShownList, personEducationComparator);
        SortListWithDuplicates secondarySort = new SortListWithDuplicates(initialSortedList, new SortGpa(),
                personEducationComparator);
        this.newList = secondarySort.getList();
    }

    public List<Person> getList() {
        return this.newList;
    }
}
