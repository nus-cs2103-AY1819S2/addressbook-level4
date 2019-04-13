package seedu.address.logic.commands.sortmethods;

import seedu.address.model.person.Person;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Follows the SortMethod interface
 * Sorts all persons by Degree and then subsequently by Education
 */
public class SortDegree implements SortMethod {

    private List<Person> newList;

    public void execute(List<Person> lastShownList, String... type) {
//        Comparator<Person> personDegreeComparator = SortUtil.degreeComparator.comparing(Person::degreeToString);
//        List<Person> initialSortedList = SortUtil.sortPersons(lastShownList, personDegreeComparator);
//        Collections.reverse(initialSortedList);
//        SortListWithDuplicates secondarySort = new SortListWithDuplicates(initialSortedList, new SortEducation(),
//                personDegreeComparator);
//        this.newList = secondarySort.getList();
    }

    public List<Person> getList() {
        return this.newList;
    }
}
